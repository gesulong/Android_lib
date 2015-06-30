package com.bjanch.www.kinmentv.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;

import com.bjanch.www.kinmentv.KinmenTVApplication;
import com.bjanch.www.kinmentv.http.Urls;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Properties;
import java.util.TreeSet;

/**
 * 收集异常信息，发送错误报告
 *
 * @author Joker
 * 2015-03-06
 */
public class CrashHandlerUtils implements UncaughtExceptionHandler {

    /** 是否开启日志输出, 在Debug状态下开启, 在Release状态下关闭以提升程序性能 */
    private static final boolean DEBUG = true;
    /** CrashHandler实例 */
    private static CrashHandlerUtils INSTANCE;
    /** 程序的Context对象 */
    private KinmenTVApplication kinmenTVApplication;
    /** 系统默认的UncaughtException处理类 */
    private UncaughtExceptionHandler mDefaultHandler;

    /** 使用Properties来保存设备的信息和错误堆栈信息 */
    private final Properties mDeviceCrashInfo = new Properties();
    private static final String VERSION_NAME = "versionName";
    private static final String VERSION_CODE = "versionCode";
    private static final String STACK_TRACE = "STACK_TRACE";
    /** 错误报告文件的扩展名 */
    private static final String CRASH_REPORTER_EXTENSION = ".log";

    private HttpHelper httpHelper;

    /** 保证只有一个CrashHandler实例 */
    private CrashHandlerUtils() {
    }

    /** 获取CrashHandler实例 ,单例模式 */
    public static CrashHandlerUtils getInstance() {
        if (INSTANCE == null)
            INSTANCE = new CrashHandlerUtils();
        return INSTANCE;
    }

    /**
     * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
     *
     * @param kinmentvapplication 实例
     */
    public void init(KinmenTVApplication kinmentvapplication) {
        kinmenTVApplication = kinmentvapplication;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        httpHelper = new HttpHelper(kinmentvapplication);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            // Sleep一会后结束程序
            // 来让线程停止一会是为了显示Toast信息给用户，然后Kill程序
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                LogUtils.e("Error : " + e);
            }
            KinmenTVApplication.getInstance().SystemExit();
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return true;
        }
        final String msg = ex.getMessage();
        if(msg!=null) {
            LogUtils.d(msg);
        }
        // 收集设备信息
        collectCrashDeviceInfo(kinmenTVApplication);
        // 保存错误报告文件
        String crashFileName = saveCrashInfoToFile(ex);
        // 发送错误报告到服务器
        sendCrashReportsToServer(kinmenTVApplication);
        return true;
    }

    /**
     * 收集程序崩溃的设备信息
     *
     * @param ctx
     */
    private void collectCrashDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                mDeviceCrashInfo.put(VERSION_NAME,
                        pi.versionName == null ? "not set" : pi.versionName);
                mDeviceCrashInfo.put(VERSION_CODE,
                        String.valueOf(pi.versionCode));
            }
        } catch (NameNotFoundException e) {
            LogUtils.e("Error while collect package info" + e);
        }
        // 使用反射来收集设备信息.在Build类中包含各种设备信息,
        // 例如: 系统版本号,设备生产商 等帮助调试程序的有用信息
        // 返回 Field 对象的一个数组，这些对象反映此 Class 对象所表示的类或接口所声明的所有字段
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                // setAccessible(boolean flag)
                // 将此对象的 accessible 标志设置为指示的布尔值。
                // 通过设置Accessible属性为true,才能对私有变量进行访问，不然会得到一个IllegalAccessException的异常
                field.setAccessible(true);
                mDeviceCrashInfo.put(field.getName(), field.get(null) + "");
                //TODO 输出设备信息
//				if (DEBUG) {
//					LogUtil.d(field.getName() + " : " + field.get(null));
//				}
            } catch (Exception e) {
                LogUtils.e("Error while collect crash info" + e);
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return
     */
    private String saveCrashInfoToFile(Throwable ex) {
        FileUtils.createFolder(FileUtils.FileErrorLog);
        Writer info = new StringWriter();
        PrintWriter printWriter = new PrintWriter(info);
        // printStackTrace(PrintWriter s)
        // 将此 throwable 及其追踪输出到指定的 PrintWriter
        ex.printStackTrace(printWriter);

        // getCause() 返回此 throwable 的 cause；如果 cause 不存在或未知，则返回 null。
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }

        // toString() 以字符串的形式返回该缓冲区的当前值。
        String result = info.toString();
        if(DEBUG)
            LogUtils.e(result);
        printWriter.close();
        mDeviceCrashInfo.put(STACK_TRACE, result);
        try {
            long timestamp = System.currentTimeMillis();
            String fileName = FileUtils.FileErrorLog + "crash-" + timestamp
                    + CRASH_REPORTER_EXTENSION;
            FileOutputStream trace = new FileOutputStream(fileName, false);
            mDeviceCrashInfo.store(trace, null);
            trace.flush();
            trace.close();
            return fileName;
        } catch (Exception e) {
            LogUtils.d("an error occured while writing report file..." + e);
        }
        return null;
    }

    /**
     * 把错误报告发送给服务器,包含新产生的和以前没发送的.
     *
     * @param ctx
     */
    private void sendCrashReportsToServer(Context ctx) {
        String[] crFiles = getCrashReportFiles(ctx);
        if (crFiles != null && crFiles.length > 0) {
            TreeSet<String> sortedFiles = new TreeSet<>();
            sortedFiles.addAll(Arrays.asList(crFiles));

            for (String fileName : sortedFiles) {
                File cr = new File(FileUtils.FileErrorLog, fileName);
                //TODO  post成功之后才会删除 日志文件
//				postReport(cr, ctx);
                cr.delete();// 删除已发送的报告
            }
        }
    }

    /**
     * 获取错误报告文件名
     *
     * @param ctx
     * @return
     */
    private String[] getCrashReportFiles(Context ctx) {
        File filesDir = new File(FileUtils.FileErrorLog);
        // 实现FilenameFilter接口的类实例可用于过滤器文件名
        FilenameFilter filter = new FilenameFilter() {
            // accept(File dir, String name)
            // 测试指定文件是否应该包含在某一文件列表中。
            @SuppressWarnings("UnnecessaryLocalVariable")
            public boolean accept(File dir, String name) {
                return name.endsWith(CRASH_REPORTER_EXTENSION);
            }
        };
        // list(FilenameFilter filter)
        // 返回一个字符串数组，这些字符串指定此抽象路径名表示的目录中满足指定过滤器的文件和目录
        return filesDir.list(filter);
    }

    private void postReport(File file, Context ctx) {
        // TODO 使用HTTP Post 发送错误报告到服务器
        // 这里不再详述,开发者可以根据OPhoneSDN上的其他网络操作
        // 教程来提交错误报告
        RequestParams params = new RequestParams();

        try {
            params.addBodyParameter("string",file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        httpHelper.postData(Urls.getInstance().ERROR, params,
                new RequestCallBack() {
                    @Override
                    public void onSuccess(ResponseInfo responseInfo) {

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {

                    }
                });
    }

    /**
     * 在程序启动时候, 可以调用该函数来发送以前没有发送的报告
     */
    public void sendPreviousReportsToServer() {
        sendCrashReportsToServer(kinmenTVApplication);
    }

}
