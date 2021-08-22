//package com.yukon.whfh.service;
//
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.BitmapFactory;
//import android.os.Build;
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.umeng.message.UmengMessageService;
//import com.umeng.message.entity.UMessage;
//import com.yukon.whfh.R;
//import com.yukon.whfh.receiver.NotificationClickReceiver;
//
//import org.json.JSONObject;
//
//public class UmengPushIntentService extends UmengMessageService {
//    NotificationManager manager;
//    int id;
//
//    public void getNotification(Context context, String title, String msg, String msgBody) {
//        Log.i("YiJiangTong", "getNotification");
//
//        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        id = (int) (System.currentTimeMillis() / 1000);
//        //点击和取消的监听事件
//        Intent intentClick = new Intent(this, NotificationClickReceiver.class);
//        intentClick.putExtra("msg", msgBody);
//        intentClick.setAction(NotificationClickReceiver.DOWN_ACTION); //点击
//        PendingIntent pendingIntentClick = PendingIntent.getBroadcast(this, id, intentClick, PendingIntent.FLAG_ONE_SHOT);
//
//        Intent intentCancel = new Intent(this, NotificationClickReceiver.class);
//        intentCancel.setAction(NotificationClickReceiver.CLOSE_ACTION);//取消
//        PendingIntent pendingIntentCancel = PendingIntent.getBroadcast(this, id, intentCancel, PendingIntent.FLAG_ONE_SHOT);
//
//        //判断8.0，若为8.0型号的手机进行创下一下的通知栏
//        if (Build.VERSION.SDK_INT >= 26) {
//            NotificationChannel channel = new NotificationChannel("channel_id", "channel_name", NotificationManager.IMPORTANCE_HIGH);
//            if (manager != null) {
//                manager.createNotificationChannel(channel);
//            }
//            Notification.Builder builder = new Notification.Builder(context, "channel_id");
//            builder.setSmallIcon(R.mipmap.ic_launcher)
//                    .setWhen(System.currentTimeMillis())
//                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
//                    .setContentTitle(title)
//                    .setContentText(msg)
//                    .setAutoCancel(true)
//                    .setContentIntent(pendingIntentClick)
//                    .setDeleteIntent(pendingIntentCancel);
//            manager.notify(id, builder.build());
//        } else {
//            Notification.Builder builder = new Notification.Builder(context);
//            builder.setSmallIcon(R.mipmap.ic_launcher)
//                    .setWhen(System.currentTimeMillis())
//                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
//                    .setContentTitle(title)
//                    .setContentText(msg)
//                    .setAutoCancel(true)
//                    .setContentIntent(pendingIntentClick)
//                    .setDeleteIntent(pendingIntentCancel);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                manager.notify(id, builder.build());
//            }
//        }
//    }
//
//    //取消通知
//    public void cancelNotify() {
//        manager.cancel(id);
//    }
//
//    /*{"display_type":"notification","extra":{"type":"1"},
//    "body":{"after_open":"go_custom","ticker":"月底提现高峰930","custom":"{“url”：“www”}","title":"月底提现高峰930","play_sound":"true","play_lights":"false","play_vibrate":"false","text":"关系到您的钱 这里一定要看一下！188"},"msg_id":"uabnxim160238004614211"}
//
//
//        {"display_type":"notification","extra":{"type":"1"},"
//        body":{"after_open":"go_app","ticker":"月底提现高峰930","title":"月底提现高峰930","play_sound":"true","play_lights":"false","play_vibrate":"false","text":"关系到您的钱 这里一定要看一下！188"},"msg_id":"uabo0r0160238055233711"}
//    */
//    @Override
//    public void onMessage(Context context, Intent intent) {
//        try {
////            Intent data = new Intent(intent);
////            data.setClass(context, DialogActivity.class);
////            data.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//需为Intent添加Flag：Intent.FLAG_ACTIVITY_NEW_TASK，否则无法启动Activity。
////            context.startActivity(data);
//            //可以通过MESSAGE_BODY取得消息体
//            Log.i("YiJiangTong", "onMessage");
//
//            final String message = intent.getStringExtra("body");
//            if (TextUtils.isEmpty(message)) {
//                return;
//            }
//            final UMessage msg = new UMessage(new JSONObject(message));
//            getNotification(context, msg.title, msg.text, message);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//}
