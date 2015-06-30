package com.bjanch.www.kinmentv.util;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import org.apache.http.util.EncodingUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;


public class FileUtils {

    public static final String FileTempDir = Environment.getExternalStorageDirectory().getPath() + "/kinmentv/"; // sd卡根目录下
    public static final String FileDb = FileTempDir + "DB/";
    public static final String FileErrorLog = FileTempDir + "Error/"; // 错误日志存储路径
    public static final String FileTempVideo = FileTempDir + "Movies/"; // 视频下载路径
    public static final String FileTempLawsText = FileTempDir + "LawsText/";// 法律法规图书下载路径
    public static final String FileTempHealthText = FileTempDir + "HealthText/";// 职业健康图书下载路径
    public static final String FileTempSafeClassText = FileTempDir + "SafeClassText/";// 安全课堂图书下载路径
    public static final String FileTempEnterpriseSystemText = FileTempDir + "EnterpriseSystemText/";// 企业制度图书下载路径
    public static final String FileNoticeText = FileTempDir + "NoticeText/";//公告存放路径
    public static final String FileHomePic = FileTempDir + "HomePic/";
    public static final String UPDATE_SAVEDIRNAME = FileTempDir + "Update/";//更新APK文件存放的目录名
    public static final String UPDATE_SAVENAME = UPDATE_SAVEDIRNAME + "kinmentv.apk";//更新APK文件名


    /**
     * 写入文件到Text文本
     *
     * @param content    写入内容
     * @param folderPath 文件夹路径
     * @param fileName   文件名称
     */
    public static void writeFile(String content, String folderPath, String fileName) {
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File targetFile = new File(folderPath);
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }
                //以指定文件创建RandomAccessFile对象
                RandomAccessFile raf = new RandomAccessFile(targetFile + fileName, "rw");
                //将文件记录指针移动到最后
                raf.seek(targetFile.length());
                //输出文件内容
                raf.write(content.getBytes());
                raf.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取Text文本字符串
     *
     * @param filePath 文件路径
     * @return
     */
    public static String readFile(String filePath) {
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                //获取指定文件对应的输入流
                FileInputStream fis = new FileInputStream(filePath);
                //将指定输入流包装成BufferReader
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                StringBuilder sb = new StringBuilder();
                String line = null;
                //循环读取文件内容
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                return sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String read(Context mContext,String filePath) throws IOException {
        FileInputStream inStream = mContext.openFileInput(filePath);
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        int len = 0;
        while((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
//		String mString=new String(data);
        String mString="";
        mString = EncodingUtils.getString(data, "UTF-8");
        System.out.println("------mString--"+mString);
        return mString;
    }

    /**
     * // 判断SdCard存在并且是可用的
     */
    public static boolean isSDCanUse() {

        String state = Environment.getExternalStorageState();
        // 判断SdCard是否存在并且是可用的
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            if (Environment.getExternalStorageDirectory().canWrite()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测SD卡是否是read-only模式，是否可读
     *
     * @return
     */
    public static boolean isSDCardMountedReadOnly() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED_READ_ONLY);
    }

    /**
     * 创建一个文件夹
     *
     * @param path
     * @return
     */
    public static boolean createFolder(String path) {
        File localFile = new File(path);
        return ((isSDCardMounted()) || (isSDCardMountedReadOnly())) && (!localFile.exists()) && localFile.mkdirs();
    }


    /**
     * SD卡剩余空间
     *
     * @return
     */
    public static long getAvailableStorage() {
        String storageDirectory = null;
        storageDirectory = Environment.getExternalStorageDirectory().toString();
        try {
            StatFs stat = new StatFs(storageDirectory);
            return ((long) stat.getAvailableBlocks() * (long) stat
                    .getBlockSize());
        } catch (RuntimeException ex) {
            return 0;
        }
    }

    /**
     * 检测文件是否存在
     *
     * @param path 文件路径
     * @return
     */
    public static boolean isFileexist(String path) {

        try {
            return new File(path).exists();
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return false;
    }

    /**
     * 检测SD卡状态
     *
     * @return
     */
    public static boolean isSDCardMounted() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

}
