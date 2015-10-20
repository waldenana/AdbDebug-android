package com.softdream.adbdebug;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dd.CircularProgressButton;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ActivityMainActivity extends AppCompatActivity {

    private CircularProgressButton btnWithText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnWithText = (CircularProgressButton) findViewById(R.id.btnWithText);
        btnWithText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnWithText.getProgress() == 100) {

                    try {
                        btnWithText.setProgress(10);
                        execShell("setprop service.adb.tcp.port -1");
                        btnWithText.setProgress(30);
                        execShell("stop adbd");
                        btnWithText.setProgress(90);
                        execShell("start adbd");
                        btnWithText.setProgress(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (btnWithText.getProgress() == 0) {

                    try {
                        btnWithText.setProgress(10);
                        execShell("setprop service.adb.tcp.port 5555");
                        btnWithText.setProgress(30);
                        execShell("stop adbd");
                        btnWithText.setProgress(90);
                        execShell("start adbd");
                        new Thread(runnable).start();
                        btnWithText.setProgress(100);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                UDP.sendIp();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnWithText.setProgress(100);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnWithText.setProgress(-1);
                    }
                });
            }
        }
    };

    public void execShell(String str) throws IOException {
            // 权限设置
            Process p = Runtime.getRuntime().exec("su ");
            // 获取输出流
            OutputStream outputStream = p.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(
                    outputStream);
            // 将命令写入
            dataOutputStream.writeBytes(str);
            dataOutputStream.writeChar('\n');
            // 提交命令
            dataOutputStream.flush();
            // 关闭流操作
            dataOutputStream.close();
            outputStream.close();
    }
}
