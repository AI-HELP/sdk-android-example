package net.cs30.elva.chatservice;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.ljoy.chatbot.ChatMainActivity;
import com.ljoy.chatbot.FAQActivity;
import com.ljoy.chatbot.controller.ElvaServiceController;
import com.ljoy.chatbot.controller.NetController;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ElvaChatServiceHelper {

    private static Activity hostActivity = null;

    private static final String[] DISPLAY_KEY= {"hideSelferviceInterface","hideContactCustomer","showConversationFlag"};

    /*把hs-tags对应的改为字符串*/
    private static HashMap parseConfigDictionary (HashMap config) {
        if(config == null) {
            return new HashMap();
        }
//        //showSingleFAQ
//        if (config.get("hideSelferviceInterface") != null) {
//            if(config.get("hideSelferviceInterface").equals("yes")) {
//                config.put("hideSelferviceInterface", true);
//            } else {
//                config.put("hideSelfServers", false);
//            }
//        }
//        //showSingleFAQ、showFAQs
//        if (config.get("hideContactCustomer") != null) {
//            if(config.get("hideContactCustomer").equals("yes")) {
//                config.put("hideContactCustomer", true);
//            } else {
//                config.put("hideContactCustomer", false);
//            }
//        }
        HashMap customMeta = (HashMap) (config.get("hs-custom-metadata"));
        if(customMeta != null) {
            ArrayList<String> tags = (ArrayList<String>) customMeta.get("hs-tags");
            if(tags != null && tags.size() > 0) {
                String[] tagsArray = (String[])tags.toArray(new String[tags.size()]);
                StringBuilder sb = new StringBuilder();
                for(int i=0;i<tagsArray.length;i++){
                    if(i>0){
                        sb.append(",");
                    }
                    sb.append(tagsArray[i]);
                }
                String tagsStr = sb.toString();
                // String tagsStr = StringUtils.join(tagsArray, ",");
                customMeta.put("hs-tags", tagsStr);
                // System.out.println("tagsStr:"+tagsStr);
            }
            config.put("hs-custom-metadata",new JSONObject(customMeta));
        }
        return config;
    }

    /*提供给AppActivity.java调用，java初始化的时候使用该方法*/
    public static void setHostActivity(Activity activity){
        hostActivity = activity;
    }
    /*初始化部分，提供给.cpp调用,无Activity(当前运行的action)对象时调用
        参数说明如下:
            appSecret:注册后由我方提供
            domain:使用我方域名im30.cs30.net
            appId:注册后由我方提供*/
    public static void init(String appSecret,String domain,String appId) {
        // System.out.println("ElvaChatServiceHelper init hostActivity is null");
        ElvaChatServiceHelper.init(null,appSecret,domain,appId);
    }

    /*初始化部分,在主AppActivity.java的onCreate中调用
        参数说明如下:
            a:当前运行的action
            appSecret:注册后由我方提供
            domain:使用我方域名im30.cs30.net
            appId:注册后由我方提供*/
    public static void init(Activity a,String appSecret,String domain,String appId) {
        if(a != null){
            hostActivity = a;
        }
        if ("".equals(appId) || appId == null) {
            System.out.println("ElvaChatServiceHelper init_appId is null");
            return;
        }
        final String fappSecret = appSecret;
        final String fdomain = domain;
        final String fappId = appId;
        setAppId(fappId);
        if(null == hostActivity) {
            System.out.println("ElvaChatServiceHelper init_hostActivity is null");
            return;
        }
        hostActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String hostPackageName = "unknown";
                String hostAppVersion = "0.0.0";
                String hostApplicationName = "unknown";
                ApplicationInfo ai = null;
                try {
                    hostPackageName = hostActivity.getPackageName();
                    PackageManager pm = hostActivity.getPackageManager();
                    ai = pm.getApplicationInfo(hostPackageName, 0);
                    PackageInfo p = pm.getPackageInfo(hostPackageName, 0);
                    hostAppVersion = p.versionName;
                    hostApplicationName = (String) (ai != null ? pm.getApplicationLabel(ai) : "(unknown)");
                } catch (PackageManager.NameNotFoundException e) {
                    ai = null;
                    // System.out.println("init start intent error");
                    e.printStackTrace();
                }
                Map<String, String> params = new HashMap<String, String>();
                params.put("appSecret", fappSecret);
                params.put("domain", fdomain);
                params.put("appId", fappId);
                // params.put("customName", fappId);
                params.put("hostPackageName", hostPackageName);
                params.put("hostAppVersion", hostAppVersion);
                params.put("hostApplicationName", hostApplicationName);
                ElvaServiceController.getInstance().init(hostActivity, params);
                ElvaServiceController.getInstance().sendInitRequest();
                // System.out.println("AliceChatServiceHelper initAliceChatService start 4");
            }
        });
    }

    /*init 初始化后，从hostActivity跳转到聊天主界面ChatMainActivity.class,并向ChatMainActivity传递参数：不带config*/
    public static void showElvaChatServiceFrom2dx(String npcName,String userName,String uid,String parseId,int serverId,String showConversationFlag) {
        // System.out.println("showElva start");
        ElvaChatServiceHelper.showElvaChatServiceFrom2dx(npcName,userName,uid,parseId,serverId,showConversationFlag,new HashMap());
    }
    public static void showElvaChatServiceFrom2dx(String npcName,String userName,String uid,String parseId,int serverId,String showConversationFlag,HashMap customData) {
        // System.out.println("showElva HashMap start");
        boolean showMap = false;
        String customDataStr = "";
        final String temNpcName = npcName;
        final String tempName = userName;
        final String tempParseId = parseId;
        final String tempUid = uid;
        final int tempServerId = serverId;
        final String tempShowConversationFlag = showConversationFlag;
        if(customData.size()>0){
            showMap = true;
            cleanConfigData(customData);
            customData =  parseConfigDictionary(customData);
            // final JSONObject metaJSON = JSONObject.fromObject(customData);
            // customDataStr = metaJSON.toString();

            // customDataStr = new Gson().toJson(customData);
            JSONObject jsonObject = new JSONObject(customData);
            customDataStr = jsonObject.toString();
        }else{
            customDataStr = "{\"serverId\":\""+Integer.toString(tempServerId)+"\"}";
        }
        final String customDataStrF = customDataStr;
        // System.out.println("showElvaChatServiceFrom2dx 带config 解析后的json字符串: customDataStrF-- " + customDataStrF);
        if (hostActivity != null) {
            hostActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Intent intent = new Intent(hostActivity, ChatMainActivity.class);
                        intent.putExtra("showType", 0);				////展示聊天主界面 ChatMainActivity
                        intent.putExtra("npcName", temNpcName);
                        intent.putExtra("userName", tempName);
                        intent.putExtra("userId", tempUid);
                        intent.putExtra("serverId", Integer.toString(tempServerId));
                        intent.putExtra("showConversationFlag",tempShowConversationFlag);
                        intent.putExtra("parseId", tempParseId);
                        intent.putExtra("customData", customDataStrF);
                        Intent i = intent != null ? intent : new Intent(hostActivity, ChatMainActivity.class);
                        hostActivity.startActivity(i);
                    } catch (Exception e) {
                        System.out.println("showElvaChatServiceFrom2dx start intent error");
                        e.printStackTrace();
                    }
                }
            });
        }
        else {
            System.out.println("showElvaChatServiceFrom2dx hostActivity null");
        }
    }


    /*将废弃*/
    public static void showElvaChatServiceFrom2dx(String npcName, String userName,String uid,int serverId) {
        final String temNpcName = npcName;
        final String tempName = userName;
        final String tempUid = uid;
        final int tempServerId = serverId;
        if (hostActivity != null) {
            hostActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Intent intent = new Intent(hostActivity, ChatMainActivity.class);
                        intent.putExtra("showType", 0);
                        intent.putExtra("npcName", temNpcName);
                        intent.putExtra("userName", tempName);
                        intent.putExtra("userId", tempUid);
                        intent.putExtra("serverId", Integer.toString(tempServerId));
                        intent.putExtra("parseId", "");
                        String packageName = hostActivity.getPackageName();
                        intent.putExtra("hostPackageName", packageName);
                        PackageManager pm = hostActivity.getPackageManager();
                        ApplicationInfo ai = null;
                        String appVersion = null;
                        try
                        {
                            ai = pm.getApplicationInfo(hostActivity.getPackageName(), 0);
                            PackageInfo p = pm.getPackageInfo(packageName, 0);
                            appVersion = p.versionName;
                        } catch (PackageManager.NameNotFoundException e) {
                            ai = null;
                            appVersion = null;
                            e.printStackTrace();
                        }
                        String applicationName = (String)(ai != null ? pm.getApplicationLabel(ai) : "(unknown)");
                        intent.putExtra("appName", applicationName);
                        if(null != appVersion) {
                            intent.putExtra("appVersion", appVersion);
                        }
                        Intent i = intent != null ? intent : new Intent(hostActivity, ChatMainActivity.class);
                        hostActivity.startActivity(i);
                    } catch (Exception e) {
                        System.out.println("showElvaChatServiceFrom2dx start intent error");
                        e.printStackTrace();
                    }
                }
            });
        }
        else {
            System.out.println("showElvaChatServiceFrom2dx hostActivity null");
        }
    }


    /*init 初始化后，从hostActivity跳转到FAQ主界面FAQActivity.class,并向FAQActivity传递参数：不带config*/
    public static void showFAQ(String faqId) {
        // System.out.println("showFAQ start");
        ElvaChatServiceHelper.showFAQ(faqId,new HashMap());
    }
    /*init 初始化后，从hostActivity跳转到FAQ主界面FAQActivity.class,并向FAQActivity传递参数：带config*/
    public static void showFAQ(String faqId,HashMap customData){
        // System.out.println("showFAQ HashMap start");
        boolean showMap = false;
        GetSSIStateRequest(faqId);
        final String tempFaqId = faqId;
        String customDataStr = "";
        final Map<String,Boolean> displayMap = cleanConfigData(customData);
        if(customData.size()>0){
            // System.out.println("showFAQ handle customData start");
            showMap = true;
            customData =  parseConfigDictionary(customData);
            //    		final JSONObject metaJSON = JSONObject.fromObject(customData);
            // customDataStr = metaJSON.toString();
            // customDataStr = new Gson().toJson(customData);
            JSONObject jsonObject = new JSONObject(customData);
            customDataStr = jsonObject.toString();
            // System.out.println("showFAQ 带config 解析后的json字符串: customDataStrF-- " + customDataStr);
        }
        final String customDataStrF = customDataStr;
        if (hostActivity != null) {
            final boolean tempShowMap = showMap;
            hostActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Intent intent = new Intent(hostActivity, FAQActivity.class);
                        intent.putExtra("showType", 1);   //跳转单独faq页面
                        intent.putExtra("faqId", tempFaqId);
                        if(tempShowMap){
                            intent.putExtra("customData", customDataStrF);
                        }
                        putData(displayMap,intent);
                        Intent i = intent != null ? intent : new Intent(hostActivity, FAQActivity.class);
                        hostActivity.startActivity(i);
                    } catch (Exception e) {
                        System.out.println("showFAQ HashMap start intent error");
                        e.printStackTrace();
                    }
                }
            });
        }
        else {
            System.out.println("showFAQ hostActivity null");
        }
    }

    /*init 初始化后，从hostActivity跳转到FAQList主界面,并向FAQActivity传递参数：不带config*/
    public static void showFAQList() {
        // System.out.println("showFAQList start");
        ElvaChatServiceHelper.showFAQList(new HashMap());
    }
    /*带config*/
    public static void showFAQList(HashMap customData) {
        // System.out.println("showFAQList HashMap start");
        boolean showMap = false;
        String customDataStr = "";
        final Map<String,Boolean> displayMap = cleanConfigData(customData);
        if(customData.size()>0){
            // System.out.println("showFAQList handle customData start");
            showMap = true;
            customData =  parseConfigDictionary(customData);
            // 		final JSONObject metaJSON = JSONObject.fromObject(customData);
            // customDataStr = metaJSON.toString();
            // customDataStr = new Gson().toJson(customData);
            JSONObject jsonObject = new JSONObject(customData);
            customDataStr = jsonObject.toString();
            // System.out.println("showFAQList 带config 解析后的json字符串: customDataStr-- " + customDataStr);
        }
        ElvaServiceController.getInstance().SSIFlag = false;
        ElvaServiceController.getInstance().SSIUrl="";
        final String customDataStrF = customDataStr;
        if (hostActivity != null) {
            final boolean tempShowMap = showMap;
            hostActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Intent intent = new Intent(hostActivity, FAQActivity.class);
                        intent.putExtra("showType", 2);
                        if(tempShowMap){
                            intent.putExtra("customData", customDataStrF);
                        }
                        putData(displayMap,intent);
                        Intent i = intent != null ? intent : new Intent(hostActivity, FAQActivity.class);
                        hostActivity.startActivity(i);
                    } catch (Exception e) {
                        System.out.println("showFAQList HashMap start intent error");
                        e.printStackTrace();
                    }
                }
            });
        }
        else {
            System.out.println("showFAQList hostActivity null");
        }
    }

    public static void setName(String game_name) {
        final String tempGame_name = game_name;
        ElvaServiceController.getInstance().setGameInfo(tempGame_name);
    }

    public static void GetSSIStateRequest(String faqId) {
        ElvaServiceController.getInstance().SSIFlag=false;
        ElvaServiceController.getInstance().SSIUrl="";
        if(hostActivity != null) {
            NetController.getInstance().getUSIStateRequest(hostActivity,faqId);
        }
    }

    public static void registerDeviceToken(String deviceToken) {
        final String tempdeviceToken = deviceToken;
        if(tempdeviceToken.equals("") || tempdeviceToken == null){
            ElvaServiceController.getInstance().getUserInfo().setDeviceToken("");
        }
        else{
            ElvaServiceController.getInstance().getUserInfo().setDeviceToken(tempdeviceToken);
        }
    }

    public static void setUserId(String uid) {
        final String tempuid = uid;
        ElvaServiceController.getInstance().getUserInfo().setUserId(tempuid);
    }

    public static void setUserName(String userName) {
        final String tempuserName = userName;
        ElvaServiceController.getInstance().getUserInfo().setUserName(tempuserName);
    }

    public static void setServerId(int serverId) {
        final String tempserverId = Integer.toString(serverId);
        ElvaServiceController.getInstance().getUserInfo().setServerId(tempserverId);
    }

    public static void setAppId(String appid) {
        final String tempappid = appid;
        ElvaServiceController.getInstance().setAppId(tempappid);
    }

    /*VIP_CHAT*/
    public static void showConversation(String uid,int serverId){
        // System.out.println("showConversation start");
        ElvaChatServiceHelper.showConversation(uid,serverId,new HashMap());
    }
    public static void showConversation(String uid,int serverId,HashMap customData) {
        boolean showMap = false;
        final String tempUid = uid;
        final int tempServerId = serverId;
        String customDataStr = "";
        if(customData.size()>0){
            showMap = true;
            // System.out.println("showConversation customData start");
            cleanConfigData(customData);
            customData =  parseConfigDictionary(customData);
            // final JSONObject metaJSON = JSONObject.fromObject(customData);
            // customDataStr = metaJSON.toString();
            // customDataStr = new Gson().toJson(customData);
            JSONObject jsonObject = new JSONObject(customData);
            customDataStr = jsonObject.toString();
        }
        final String customDataStrF = customDataStr;
        if(showMap){
            // System.out.println("showConversation 带config 解析后的json字符串: customDataStrF-- " + customDataStrF);
        }
        if (hostActivity != null) {
            final boolean tempShowMap = showMap;
            hostActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Intent intent = new Intent(hostActivity, ChatMainActivity.class);
                        intent.putExtra("showType", 3);
                        intent.putExtra("userId", tempUid);
                        intent.putExtra("serverId", Integer.toString(tempServerId));
                        if(tempShowMap){
                            intent.putExtra("customData", customDataStrF);
                        }
                        Intent i = intent != null ? intent : new Intent(hostActivity, ChatMainActivity.class);
                        hostActivity.startActivity(i);
                    } catch (Exception e) {
                        System.out.println("showConversation start intent error");
                        e.printStackTrace();
                    }
                }
            });
        }
        else {
            // System.out.println("showConversation hostActivity null");
        }
    }

    /*init 初始化后，从hostActivity跳转到FAQ主界面FAQActivity.class,并向showFAQSection传递参数：不带config*/
    public static void showFAQSection(String sectionPublishId) {
        // System.out.println("showFAQSection start");
        ElvaChatServiceHelper.showFAQSection(sectionPublishId,new HashMap());
    }
    /*init 初始化后，从hostActivity跳转到FAQ主界面FAQActivity.class,并向showFAQSection传递参数：带config*/
    public static void showFAQSection(String sectionPublishId,HashMap customData){
        boolean showMap = false;
        GetSSIStateRequest(sectionPublishId);
        final String tempSectionPublishId = sectionPublishId;
        String customDataStr = "";
        final Map<String,Boolean> displayMap = cleanConfigData(customData);
        if(customData.size()>0){
            // System.out.println("showFAQSection handle customData start");
            showMap = true;
            customData =  parseConfigDictionary(customData);
            //    		final JSONObject metaJSON = JSONObject.fromObject(customData);
            // customDataStr = metaJSON.toString();
            // customDataStr = new Gson().toJson(customData);
            JSONObject jsonObject = new JSONObject(customData);
            customDataStr = jsonObject.toString();
            // System.out.println("showFAQSection 带config 解析后的json字符串: customDataStrF-- " + customDataStr);
        }
        final String customDataStrF = customDataStr;
        if (hostActivity != null) {
            final boolean tempShowMap = showMap;
            hostActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Intent intent = new Intent(hostActivity, FAQActivity.class);
                        intent.putExtra("showType", 2);
                        intent.putExtra("sectionPublishId", tempSectionPublishId);
                        if(tempShowMap){
                            intent.putExtra("customData", customDataStrF);
                        }
                        putData(displayMap,intent);
                        Intent i = intent != null ? intent : new Intent(hostActivity, FAQActivity.class);
                        hostActivity.startActivity(i);
                    } catch (Exception e) {
                        System.out.println("showFAQSection HashMap start intent error");
                        e.printStackTrace();
                    }
                }
            });
        }
        else {
            System.out.println("showFAQSection hostActivity null");
        }
    }

    private static Map<String,Boolean> cleanConfigData(HashMap config){
        if(config == null || config.size()==0){
            return null;
        }
        Map<String,Boolean> map = new HashMap();
        for(String key:DISPLAY_KEY){
            if(config.get(key) != null){
                map.put(key,true);
                config.remove(key);
            }
        }
        return map;
    }

    private static void putData(Map<String,Boolean> config, Intent intent){
        if(config == null || config.size() ==0 || intent == null){
            return;
        }
        for(Map.Entry<String,Boolean> map:config.entrySet()){
            intent.putExtra(map.getKey(),map.getValue());
        }
    }
}