package com.yukon.whfh;

import android.app.Application;
import android.util.Log;

import com.umeng.commonsdk.UMConfigure;
import com.yukon.whfh.helper.PushHelper;

import java.lang.reflect.Field;

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

//        Class c = getClasss("com.umeng.message.PushAgent");
//        Log.d("YiJiangTong","className:"+c.getName());
//        boolean f = checkShareSdk(c);
//        if (c != null && !checkShareSdk(c)) {
//            Log.d("YiJiangTong","打印");
//        }else{
//            Log.d("YiJiangTong","不打印");
//        }
//
//        Class c1 = getClasss("com.umeng.socialize.UMShareAPI");
//        Log.d("YiJiangTong","className:"+c1.getName());
//        if (c1 != null && !checkShareSdk(c1)) {
//            Log.d("YiJiangTong","打印"+checkShareSdk(c1));
//        }else{
//            Log.d("YiJiangTong","不打印"+checkShareSdk(c1));
//        }
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
        Log.d("YiJiangTong","initPushSDK agreed"+agreed);
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

    private static Class<?> getClasss(String var0) {
        Class var1 = null;

        try {
            var1 = Class.forName(var0);
        } catch (ClassNotFoundException var3) {
        }

        return var1;
    }


    private static boolean checkShareSdk(Class<?> var0) {
        boolean var1 = false;

        try {
            Field var2 = var0.getDeclaredField("isZyb");
            if (var2 != null) {
                var1 = true;
            }
        } catch (Throwable var3) {
        }

        return var1;
    }
}
