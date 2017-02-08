using UnityEngine;
#if UNITY_ANDROID
public class ElvaChatServiceSDKAndroid
{
    private AndroidJavaClass sdk;

    private static ElvaChatServiceSDKAndroid instance;

    public static ElvaChatServiceSDKAndroid getInstance()
    {
        if(instance == null){
            instance = new ElvaChatServiceSDKAndroid();
        }
        return instance;
    }
    public ElvaChatServiceSDKAndroid()
    {
        sdk = new AndroidJavaClass("com.ljoy.chatbot.sdk.ELvaChatServiceSdk");
        sdk.CallStatic("init",activity,"","im30.cs30.net","slots_platform_2803a29c-e53c-435b-a527-56eab1adfa93");
    }

    public void init(string appKey,string domain,string appId){
        AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        AndroidJavaObject activity = jc.GetStatic<AndroidJavaObject>("currentActivity");
        sdk.CallStatic("init",activity,appKey,domain,appId);
    }

    public void showElva(string playerName,string playerUid,string serverId,string playerParseId,string showConversationFlag){
        sdk.CallStatic("showElvaChatService","elva",playerName,playerUid,serverId,playerParseId,showConversationFlag);
    }

    public void ShowFAQList()
    {
        sdk.CallStatic("showFAQList");
    }

    public void showFAQSection(string sectionPublishId){
        sdk.CallStatic("showFAQSection",sectionPublishId);
    }

    public void showSingleFAQ(string faqId){
        sdk.CallStatic("showSingleFAQ",faqId);
    }


    public void setName(string gameName)
    {
        sdk.CallStatic("setName",gameName);
    }

    public void setUserId(string playerUid)
    {
        sdk.CallStatic("setUserId",playerUid);
    }

    public void setUserName(string userName)
    {
        sdk.CallStatic("setUserName",userName);
    }

    public void setServerId(string serverId)
    {
        sdk.CallStatic("setServerId",serverId);
    }
    public void ShowConversation(string uid,string serverId)
    {
        sdk.CallStatic("showConversation",uid,serverId);
    }
}
#endif