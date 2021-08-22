package com.umeng.message.demo;

import android.app.Application;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.demo.helper.PushHelper;

/**
 * 应用程序类
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //日志开关
        UMConfigure.setLogEnabled(true);
        //预初始化
        PushHelper.preInit(this);
        //正式初始化
        initPushSDK();
    }

    /**
     * 初始化推送SDK，在用户隐私政策协议同意后，再做初始化
     */
    private void initPushSDK() {
        /*
         * 判断用户是否已同意隐私政策
         * 当同意时，直接进行初始化；
         * 当未同意时，待用户同意后，通过PushHelper.init(...)方法进行初始化。
         */
        boolean agreed = MyPreferences.getInstance(this).hasAgreePrivacyAgreement();
        if (agreed && PushHelper.isMainProcess(this)) {
            //建议在线程中执行初始化
            new Thread(new Runnable() {
                @Override
                public void run() {
                    PushHelper.init(getApplicationContext());
                }
            }).start();
        }
    }

}
