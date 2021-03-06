package com.yukon.whys.tester;

import android.content.Context;
import android.util.Log;

import com.umeng.message.PushAgent;
import com.umeng.message.tag.TagManager;

public class UPushAlias {

    private static final String TAG = "UPushAlias";

    public static void add(Context context, String alias, String type) {
        PushAgent.getInstance(context).addAlias(alias, type, (success, message) -> {
            Log.i(TAG, "add success:" + success + " msg:" + message);
        });
    }

    public static void delete(Context context, String alias, String type) {
        PushAgent.getInstance(context).deleteAlias(alias, type, (success, message) -> {
            Log.i(TAG, "delete success:" + success + " msg:" + message);
        });
    }

    public static void set(Context context, String alias, String type) {
        PushAgent.getInstance(context).setAlias(alias, type, (success, message) -> {
            Log.i(TAG, "set success:" + success + " msg:" + message);
        });
    }

    public static void test(Context context) {
        add(context, "aa", "bb");
        delete(context, "aa", "bb");
        set(context, "aa1", "bb1");
        delete(context, "aa1", "bb1");

        TagManager tagManager = PushAgent.getInstance(context).getTagManager();
        tagManager.addTags((success, result) -> {
            Log.i(TAG, "addTags " + success + " " + result);
        }, "abc", "aa", "bb", "bcd");

        tagManager.deleteTags((success, result) -> {
            Log.i(TAG, "deleteTags " + success + " " + result);
        }, "aa");

        tagManager.getTags((success, result) -> {
            Log.i(TAG, "getTags " + success + " " + result);
        });
    }
}
