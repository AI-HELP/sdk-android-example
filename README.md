  Android SDK access instructions for English: https://github.com/CS30-NET/android-sdk-stable/blob/master/README.md <br />
  中文版Android SDK接入说明：https://github.com/CS30-NET/android-sdk-stable/blob/master/README-CN.md
Android SDK Access Instructions
=====
Ⅰ. Download Android SDK.
------
Click the button "Clone or download" in the top right corner to download Android SDK and then unzip the file.
Ⅱ. cocos2dx Interface List
------
Put ECServiceCocos2dx.h, ECServiceCocos2dx.cpp in the interface folder in your Classes folder.
Ⅲ. Import elvachatservice into project
------
Copy the elvachatservice folder to your main directory
Ⅳ. Import Google App Indexing into project
------
Import play-services-appindexing into your project(IF the item google service exists, this step can be ignored).
Ⅴ. Access Project Configuration
------
Modify the AndroidManifest.xml in elvachatservice folder to add the required configuration:
### 1. Add the required permissions:
    <Uses-permission android: name = "android.permission.INTERNET" />
    <Uses-permission android: name = "android.permission.ACCESS_NETWORK_STATE" />
    <Uses-permission android: name = "android.permission.WRITE_EXTERNAL_STORAGE" />
    <Uses-permission android: name = "android.permission.READ_EXTERNAL_STORAGE" />
### 2. Add activity:
    <Activity
        Android: name = "com.ljoy.chatbot.ChatMainActivity"
        Android: configChanges = "orientation | screenSize | locale"
        Android: screenOrientation = "portrait">
    </ Activity>
    <Activity
        Android: name = "com.ljoy.chatbot.FAQActivity"
        Android: configChanges = "orientation | screenSize | locale"
        Android: screenOrientation = "portrait"> 
        <intent-filter android:label="@string/app_name">
            <action android:name="android.intent.action.VIEW" />
            <category android:name="android.intent.category.DEFAULT" />
            <category android:name="android.intent.category.BROWSABLE" />
            <data android:scheme="https"
                android:host="cs30.net"
                android:pathPrefix="/elvaFAQ" />
        </intent-filter>
    </ Activity>
### 3、Add meta
      <meta-data
          android:name="com.google.android.gms.version"
          android:value="@integer/google_play_services_version" />
Ⅵ.Interface Call Instructions
------
#### 1. SDK initialization. <br />
Create a JNI environment and the application in the Activity: (must be called at the beginning of the game)<br />
> a.If you call initialization interface in onCreate of the main Activity. then call:<br />
ElvaChatServiceHelper.init (Activity activity, String appKey, String domain, String appId)<br />
* Parameter Description:<br />
activity: the current operation of the action, parameter "this" can be. <br />
app Key: The app key, obtained from the Web management system.<br />
domain: app Domain name, obtained from the Web management system.<br />
appId: app Unique identifier, obtained from the Web management system.<br />
Note: The latter three parameters, please use the registered email address to login https://cs30.net/elva. View in the Settings Applications page. Initial use, please register on the official website http://cs30.net/preview/index.html.
> 
> b. If you need to delay the call, then，<br />
In activity.java call SetActivity (this);<br />
In Cocos2dx call ECServiceCocos2dx :: init (string appKey, string domain, string appId);<br />

#### 2. The interface call method<br />
> 1) Start smart customer service main interface, call `showElva` method, start the robot interface.<br />
ECServiceCocos2dx :: showElva (string playerName, string playerUid, int serverId, string playerParseId, string showConversationFlag, cocos2d :: ValueMap & config);<br />
* Parameter Description:<br />
playerName: The name of the player in the game.<br />
playerUid: The player's unique id in the game.<br />
serverId: The server ID of the player.<br />
playerParseId: Push token.<br />
showConversationFlag (0 or 1): whether VIP, 0: marked non-VIP; 1: VIP. Here is 1, will be in the upper right corner of the robot chat interface, to provide artificial chat entry function.<br />
config: Optional, custom ValueMap information. You can set specific Tag information here.<br />
![showElva](https://github.com/CS30-NET/Pictures/blob/master/showElva-EN-Android.png "showElva")<br />
* Parameter Example:    

    ECServiceCocos2dx :: showElva ( "elvaTestName", "12349303258", 1, "es234-3dfs-d42f-342sfe3s3", "1"
     {
       Hs-custom-metadata = {
       Hs-tags = 'army, recharge'. //Note: hs-tags value is vector type, where the incoming custom Tag,   
       need to configure the same name in the Web management Tag to take effect.
       VersionCode = '3'
       } 
     }
    );
> 
> 2) Show a single FAQ, call `showSingleFAQ` method<br />
ECServiceCocos2dx :: showSingleFAQ (string faqId, cocos2d :: ValueMap & config);<br />
* Parameter Description:<br />
faqId: FAQ's PublishID, in the Web background https://cs30.net/elva, from the FAQs menu to find the specified FAQ, view PublishID.<br />
config: Optional, custom ValueMap information. Refer to 1) intelligent customer service main interface starts.<br />
Note: If the SelfServiceInterface is configured in the web administration background, and the SDK is configured with related parameters, the FAQ will be displayed and the function menu will be provided in the upper right corner to call up the related self-service.<br />
![showSingleFAQ](https://github.com/CS30-NET/Pictures/blob/master/showSingleFAQ-EN-Android.png "showSingleFAQ")<br />
> 
> 3) Show the relevant part of the FAQ, call `showFAQSection` method<br />
ECServiceCocos2dx :: showFAQSection (string sectionPublishId, cocos2d :: ValueMap & config);<br />
* Parameter Description:<br />
sectionPublishId: PublishID of the FAQ Section (PublishID can be viewed from the [Section] menu in the FAQs menu at https://cs30.net/elva).<br />
config: Optional, custom ValueMap information. Refer to 1) intelligent customer service main interface starts.
![showFAQSection](https://github.com/CS30-NET/Pictures/blob/master/showFAQSection-EN-Android.png "showFAQSection")<br />
> 
> 4) Show the FAQ list, call `showFAQs` method<br />
ECServiceCocos2dx :: showFAQs (cocos2d :: ValueMap & config);<br />
* Parameter Description:
config: Optional, custom ValueMap information. Refer to 1) intelligent customer service main interface starts.<br />
![showFAQs](https://github.com/CS30-NET/Pictures/blob/master/showFAQs-EN-Android.png "showFAQs")<br />
> 
> 5) set the game name information, call `setName` method (It is recommended to call this method after calling init)       
ECServiceCocos2dx :: setName (string game_name);<br />
Parameter Description:<br />
game_name: The name of the game, which will be displayed in the title bar of the relevant interface in the SDK.<br />
> 
> 6) Set Token, use google push, call `registerDeviceToken` method (no)<br />
ECServiceCocos2dx :: registerDeviceToken (string deviceToken);<br />
* Parameter Description:<br />
deviceToken: The device Token.<br />
> 
> 7) Set the user id information, call the `setUserId` method (using self-service must call, see 2) show a single FAQ). Call<br />
ECServiceCocos2dx :: setUserId (string playerUid) before showSingleFAQ;<br />
* Parameter Description:<br />
playerUid: The player unique ID.<br />
> 
> 8) Set the server number information, call `setServerId` method (using self-service must call, see 2) show a single FAQ). Call<br />
ECServiceCocos2dx :: setServerId (int serverId) before showSingleFAQ;<br />
* Parameter Description:<br />
serverId: Server ID.<br />
> 
> 9) Set the player name information, call `setUserName` method (It is recommended to call this method after calling init)<br />
ECServiceCocos2dx :: setUserName (string playerName);<br />
* Parameter Description:<br />
playerName: The player name.<br />
> 
> 10) Direct vip_chat artificial customer service chat, call `showConversation` method (must ensure that setUserName in 9) set the
      player name information has been called)<br />
ECServiceCocos2dx :: showConversation (string playerUid, int serverId, cocos2d :: ValueMap & config);<br />
* Parameter Description:<br />
playerUid: The player's unique id in the game.<br />
serverId: The server ID of the player.<br />
config: Optional, custom ValueMap information. Refer to 1) intelligent customer service main interface starts.<br />
![showConversation](https://github.com/CS30-NET/Pictures/blob/master/showConversation-EN-Android.png "showConversation")
