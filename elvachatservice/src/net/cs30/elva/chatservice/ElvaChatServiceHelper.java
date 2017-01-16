package net.cs30.elva.chatservice;

import android.app.Activity;

import com.ljoy.chatbot.sdk.ELvaChatServiceSdk;

import java.util.HashMap;

public class ElvaChatServiceHelper {

    /*提供给AppActivity.java调用，java初始化的时候使用该方法*/
    public static void setHostActivity(Activity activity){
        ELvaChatServiceSdk.setHostActivity(activity);
    }
    /*初始化部分，提供给.cpp调用,无Activity(当前运行的action)对象时调用
        参数说明如下:
            appSecret:注册后由我方提供
            domain:使用我方域名im30.cs30.net
            appId:注册后由我方提供*/
    public static void init(String appSecret,String domain,String appId) {
        ELvaChatServiceSdk.init(appSecret,domain,appId);
    }

    /*初始化部分,在主AppActivity.java的onCreate中调用
        参数说明如下:
            a:当前运行的action
            appSecret:注册后由我方提供
            domain:使用我方域名im30.cs30.net
            appId:注册后由我方提供*/
    public static void init(Activity a,String appSecret,String domain,String appId) {
        ELvaChatServiceSdk.init(a,appSecret,domain,appId);
    }

    /*init 初始化后，从hostActivity跳转到聊天主界面ChatMainActivity.class,并向ChatMainActivity传递参数：不带config*/
    public static void showElvaChatServiceFrom2dx(String npcName,String userName,String uid,String parseId,int serverId,String showConversationFlag) {
        ELvaChatServiceSdk.showElvaChatService(npcName,userName,uid,parseId,String.valueOf(serverId),showConversationFlag);
    }
    public static void showElvaChatServiceFrom2dx(String npcName,String userName,String uid,String parseId,int serverId,String showConversationFlag,HashMap customData) {
        ELvaChatServiceSdk.showElvaChatService(npcName,userName,uid,parseId,String.valueOf(serverId),showConversationFlag,customData);
    }

    /*init 初始化后，从hostActivity跳转到FAQ主界面FAQActivity.class,并向FAQActivity传递参数：不带config*/
    public static void showFAQ(String faqId) {
        ELvaChatServiceSdk.showSingleFAQ(faqId);
    }
    /*init 初始化后，从hostActivity跳转到FAQ主界面FAQActivity.class,并向FAQActivity传递参数：带config*/
    public static void showFAQ(String faqId,HashMap customData){
        ELvaChatServiceSdk.showSingleFAQ(faqId,customData);
    }

    /*init 初始化后，从hostActivity跳转到FAQList主界面,并向FAQActivity传递参数：不带config*/
    public static void showFAQList() {
        ELvaChatServiceSdk.showFAQList();
    }
    /*带config*/
    public static void showFAQList(HashMap customData) {
        ELvaChatServiceSdk.showFAQList(customData);
    }

    public static void setName(String game_name) {
        ELvaChatServiceSdk.setName(game_name);
    }

    public static void registerDeviceToken(String deviceToken) {
        ELvaChatServiceSdk.registerDeviceToken(deviceToken);
    }

    public static void setUserId(String uid) {
        ELvaChatServiceSdk.setUserId(uid);
    }

    public static void setUserName(String userName) {
        ELvaChatServiceSdk.setUserName(userName);
    }

    public static void setServerId(int serverId) {
        ELvaChatServiceSdk.setServerId(String.valueOf(serverId));
    }

    public static void setAppId(String appid) {
        ELvaChatServiceSdk.setAppId(appid);
    }

    /*manul*/
    public static void showConversation(String uid,int serverId){
        ELvaChatServiceSdk.showConversation(uid,String.valueOf(serverId));
    }
    public static void showConversation(String uid,int serverId,HashMap customData) {
        ELvaChatServiceSdk.showConversation(uid,String.valueOf(serverId),customData);
    }

    /*init 初始化后，从hostActivity跳转到FAQ主界面FAQActivity.class,并向showFAQSection传递参数：不带config*/
    public static void showFAQSection(String sectionPublishId) {
        ELvaChatServiceSdk.showFAQSection(sectionPublishId);
    }
    /*init 初始化后，从hostActivity跳转到FAQ主界面FAQActivity.class,并向showFAQSection传递参数：带config*/
    public static void showFAQSection(String sectionPublishId,HashMap customData){
        ELvaChatServiceSdk.showFAQSection(sectionPublishId,customData);
    }

    public static void setSDKLanguage(String language){
        ELvaChatServiceSdk.setSDKLanguage(language);
    }

    public static void useDevice(){
        ELvaChatServiceSdk.setUseDevice();
    }

    public static void setEvaluateStar(int star) {
        ELvaChatServiceSdk.setEvaluateStar(star);
    }


}
