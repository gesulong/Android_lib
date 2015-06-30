package com.bjanch.www.kinmentv.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bjanch.www.kinmentv.R;
import com.bjanch.www.kinmentv.util.ToastTools;

public class LiveEnCodeDialog extends Dialog implements View.OnClickListener {

    private TextView content_tv;

    private EditText code_et;

    private Button btn_ok, btn_cancel;

    private String str = "";

    private LiveEnCodeDialogCallback callback;

    private Context context;

    private MHandler handler;

    public static final String CODE = "jinmen110";

    public LiveEnCodeDialog(Context context, int theme, LiveEnCodeDialogCallback callback) {
        super(context, theme);

        this.context = context;
        handler = new MHandler();
        this.callback = callback;

        setCanceledOnTouchOutside(false);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.live_encode_dialog_layout);

        content_tv = (TextView) findViewById(R.id.live_dialog_content_tv);
        code_et = (EditText) findViewById(R.id.live_dialog_code_et);
        btn_ok = (Button) findViewById(R.id.live_dialog_ok_btn);
        btn_cancel = (Button) findViewById(R.id.live_dialog_cancel_btn);
        btn_ok.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == btn_ok) {
            enCoding();
        } else {
            dismiss();
        }
    }

    public void enCoding() {
        str = code_et.getText().toString();

        if (str.equals("")) {
            ToastTools.defaultToast(context, "请输入密码!!");
            return;
        }

        handler.sendEmptyMessage(1);

    }

    public interface LiveEnCodeDialogCallback {
        void encodingOK();
    }

    class MHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    content_tv.setText("验证中。。。");
                    if (str.equals(CODE)) {
                        handler.sendEmptyMessageDelayed(2, 2000);
                    } else {
                        handler.sendEmptyMessageDelayed(4, 2000);
                    }
                    break;
                case 2:
                    content_tv.setText("验证成功！！");
                    callback.encodingOK();
                    handler.sendEmptyMessageDelayed(3,1000);
                    break;
                case 3:
                    dismiss();
                    break;
                case 4:
                    content_tv.setText("验证失败，请重新输入密码");
                    break;
                default:
                    break;
            }

            super.handleMessage(msg);
        }
    }
}
