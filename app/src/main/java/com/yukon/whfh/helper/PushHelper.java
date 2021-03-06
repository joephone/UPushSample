package com.yukon.whfh.helper;

import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

import com.taobao.accs.ACCSClient;
import com.taobao.accs.AccsClientConfig;
import com.taobao.agoo.TaobaoRegister;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.yukon.whys.R;

import org.android.agoo.huawei.HuaWeiRegister;
import org.android.agoo.mezu.MeizuRegister;
import org.android.agoo.oppo.OppoRegister;
import org.android.agoo.vivo.VivoRegister;
import org.android.agoo.xiaomi.MiPushRegistar;

/**
 * PushSDK集成帮助类
 */
public class PushHelper {

    private static final String TAG = "YiJiangTong";

    /**
     * 预初始化，已添加子进程中初始化sdk。
     * 使用场景：用户未同意隐私政策协议授权时，延迟初始化
     *
     * @param context 应用上下文
     */
    public static void preInit(Context context) {
        Log.d("YiJiangTong","helper preInit");
        try {
            //解决推送消息显示乱码的问题
            AccsClientConfig.Builder builder = new AccsClientConfig.Builder();
            builder.setAppKey("umeng:" + PushConstants.getAppKey());
            builder.setAppSecret(PushConstants.getMsgSecret());
            builder.setTag(AccsClientConfig.DEFAULT_CONFIGTAG);
            ACCSClient.init(context, builder.build());
            TaobaoRegister.setAccsConfigTag(context, AccsClientConfig.DEFAULT_CONFIGTAG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        UMConfigure.preInit(context, PushConstants.getAppKey(), PushConstants.CHANNEL);
        if (!isMainProcess(context)) {
            init(context);
        }
    }

    /**
     * 初始化。
     * 场景：用户已同意隐私政策协议授权时
     *
     * @param context 应用上下文
     */
    public static void init(Context context) {
        // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
        // 参数一：当前上下文context；
        // 参数二：应用申请的Appkey；
        // 参数三：渠道名称；
        // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
        // 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息
        UMConfigure.init(
                context,
                PushConstants.getAppKey(),
                PushConstants.CHANNEL,
                UMConfigure.DEVICE_TYPE_PHONE,
                PushConstants.getMsgSecret()
        );

        //获取消息推送实例
        final PushAgent pushAgent = PushAgent.getInstance(context);

        pushAdvancedFunction(context);

        //注册推送服务，每次调用register方法都会回调该接口
        pushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.i(TAG, "deviceToken --> " + deviceToken);

                //可设置别名，推送时使用别名推送
                String type = "uid";
                String alias = "123456";
                pushAgent.setAlias(alias, type, new UTrack.ICallBack() {
                    @Override
                    public void onMessage(boolean success, String message) {
                        Log.i(TAG, "fh setAlias " + success + " msg:" + message);
                    }
                });
            }

            @Override
            public void onFailure(String errCode, String errDesc) {
                Log.e(TAG, "fh register failure：--> " + "code:" + errCode + ",desc:" + errDesc);
            }
        });
        if (isMainProcess(context)) {
            registerDeviceChannel(context);
        }

//        pushAgent.setPushIntentServiceClass(UmengPushIntentService.class);
    }

    /**
     * 注册设备推送通道（小米、华为等设备的推送）
     *
     * @param context 应用上下文
     */
    private static void registerDeviceChannel(Context context) {
        //小米通道，填写您在小米后台APP对应的xiaomi id和key
        MiPushRegistar.register(context, PushConstants.getMiId(), PushConstants.getMiKey());
        //华为，注意华为通道的初始化参数在minifest中配置
        HuaWeiRegister.register((Application) context.getApplicationContext());
        //魅族，填写您在魅族后台APP对应的app id和key
        MeizuRegister.register(context, PushConstants.MEI_ZU_ID, PushConstants.MEI_ZU_KEY);
        //OPPO，填写您在OPPO后台APP对应的app key和secret
        OppoRegister.register(context, PushConstants.OPPO_KEY, PushConstants.OPPO_SECRET);
        //vivo，注意vivo通道的初始化参数在minifest中配置
        VivoRegister.register(context);
    }

    /**
     * 是否运行在主进程
     *
     * @param context 应用上下文
     * @return true: 主进程；false: 子进程
     */
    public static boolean isMainProcess(Context context) {
        return UMUtils.isMainProgress(context);
    }

    //推送高级功能集成说明
    private static void pushAdvancedFunction(Context context) {
        PushAgent pushAgent = PushAgent.getInstance(context);

        //设置通知栏显示通知的最大个数（0～10），0：不限制个数
        pushAgent.setDisplayNotificationNumber(0);

        //推送消息处理
        UmengMessageHandler msgHandler = new UmengMessageHandler() {
            //处理通知栏消息
//            @Override
//            public Notification getNotification(Context context, UMessage msg) {
//                Log.d(TAG,"helper getNotification"+msg.builder_id);
////                if (msg.builder_id == 1) {
//                    Notification.Builder builder = new Notification.Builder(context);
//                    RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.upush_notification);
//                    remoteViews.setTextViewText(R.id.notification_title, msg.title);
//                    remoteViews.setTextViewText(R.id.notification_text, msg.text);
//                    remoteViews.setImageViewBitmap(R.id.notification_large_icon1, getLargeIcon(context, msg));
//                    remoteViews.setImageViewResource(R.id.notification_large_icon2, getSmallIconId(context, msg));
//                    builder.setContent(remoteViews)
//                            .setSmallIcon(getSmallIconId(context, msg))
//                            .setTicker(msg.ticker)
//                            .setAutoCancel(true);
//                return builder.getNotification();
////                }
//                //默认为0，若填写的builder_id并不存在，也使用默认。
////                return super.getNotification(context, msg);
//            }


            //处理通知栏消息
            @Override
            public void dealWithNotificationMessage(Context context, UMessage msg) {
                super.dealWithNotificationMessage(context, msg);
                Log.i(TAG, "fh notification receiver:" + msg.getRaw().toString());
            }

            //处理透传消息
            @Override
            public void dealWithCustomMessage(Context context, UMessage msg) {
                super.dealWithCustomMessage(context, msg);
                Log.i(TAG, "fh custom receiver:" + msg.getRaw().toString());
            }
        };

        pushAgent.setMessageHandler(msgHandler);

        //推送消息点击处理
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void openActivity(Context context, UMessage msg) {
                super.openActivity(context, msg);
                Log.i(TAG, "fh click openActivity: " + msg.getRaw().toString());

            }

            @Override
            public void launchApp(Context context, UMessage msg) {
                super.launchApp(context, msg);
                Log.i(TAG, "fh click launchApp: " + msg.getRaw().toString());
            }

            @Override
            public void dismissNotification(Context context, UMessage msg) {
                super.dismissNotification(context, msg);
                Log.i(TAG, "fh click dismissNotification: " + msg.getRaw().toString());
            }
        };
        pushAgent.setNotificationClickHandler(notificationClickHandler);
    }

}
