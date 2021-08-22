package com.yukon.whys.helper;

/**
 * Sample相关的常量定义类
 * deviceToken --> AqqPX6qAxyhiQohlvn91357fhHouIz6GxFSPflNBtJRv   umeng demo
 * deviceToken --> AqqPX6qAxyhiQohlvn91357zajOzkcHdywVCjkddGvrG   yukon fh
 */
public class PushConstants {
    /**
     * 应用申请的Appkey
     */
//    public static final String APP_KEY = "应用申请的Appkey";
//    public static final String APP_KEY = "6108fffd6aac3162c76a6a8d";  //"5b960fb5a40fa3332e000082";

    public static String getAppKey(){
        if(Global.isYokon){
            return "6108fffd6aac3162c76a6a8d";
        }else {
            return "610902a06aac3162c76a71e3";
        }
    }

    /**
     * 应用申请的UmengMessageSecret
     */
//    public static final String MESSAGE_SECRET = "应用申请的UmengMessageSecret";
//    public static final String MESSAGE_SECRET = "86587773a1f4cfe4cb171cc17bc2322d"; // "de1a9295ad7c8a23d3adcf5daac38c3f";

    public static String getMsgSecret(){
        if(Global.isYokon){
            return "86587773a1f4cfe4cb171cc17bc2322d";
        }else {
            return "f91bca61975c314a6e5d9448afb541e6";//"de1a9295ad7c8a23d3adcf5daac38c3f";  //
        }
    }
    /**
     * 后台加密消息的密码（仅Demo用，请勿将此密码泄漏）
     */
//    public static final String APP_MASTER_SECRET = "krpt7yleqvzcstbriuxrwadsfotzarwp"; // "6vnajkupxywhpgf60ndh73pbotyd8mfn";

    public static String getMasterSecret(){
        if(Global.isYokon){
            return "krpt7yleqvzcstbriuxrwadsfotzarwp";
        }else {
            return "soqnebw9lmosp6mnwkfvept9pss7m1kl";//""6vnajkupxywhpgf60ndh73pbotyd8mfn";
        }
    }
    /**
     * 渠道名称，修改为您App的发布渠道名称
     */
    public static final String CHANNEL = "channel";

    /**
     * 小米后台APP对应的xiaomi id
     * 承运端:
     *  AppID：2882303761518969148 
     * AppKey：5691896936148 
     * AppSecret：J3Z6Wru8kmzq+oM40gHXvw== 
     *
     * 发货端:
     * AppID：2882303761518968414 
     * AppKey：5491896837414 
     * AppSecret：kkicPrA1FjbildGpasJECg==
     */
//    public static final String MI_ID = "填写您在小米后台APP对应的xiaomi id";
//    public static final String MI_ID = "2882303761517875511";

    public static String getMiId(){
        if(Global.isYokon){
            return "2882303761518968414";
        }else {
            return "2882303761518969148";
        }
    }
    /**
     * 小米后台APP对应的xiaomi key
     */
//    public static final String MI_KEY = "填写您在小米后台APP对应的xiaomi key";
//    public static final String MI_KEY = "5961787531511";

    public static String getMiKey(){
        if(Global.isYokon){
            return "5491896837414";
        }else {
            return "5691896936148";
        }
    }

    /**
     * 魅族后台APP对应的xiaomi id
     */
//    public static final String MEI_ZU_ID = "填写您在魅族后台APP对应的app id";
    public static final String MEI_ZU_ID = "3352624"; //"116090";

    /**
     * 魅族后台APP对应的xiaomi key
     */
//    public static final String MEI_ZU_KEY = "填写您在魅族后台APP对应的app key";
    public static final String MEI_ZU_KEY = "NAK2yzTzFyuh2CXEbXg8kyhmEsFoWIze";//"9413f9968d654df092afecf4d3782391";

    /**
     * OPPO后台APP对应的app key
     */
//    public static final String OPPO_KEY = "填写您在OPPO后台APP对应的app key";
    public static final String OPPO_KEY = "c39dd561327e484bb1228b5459063022";

    /**
     * OPPO后台APP对应的app secret
     */
//    public static final String OPPO_SECRET = "填写您在OPPO后台APP对应的app secret";
    public static final String OPPO_SECRET = "a915f817673a45f08065a2236dfe85a7";
}
