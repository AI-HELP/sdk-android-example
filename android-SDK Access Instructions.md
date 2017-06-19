# HIGHLIGHTS<br />
1.Remember to initialize, otherwise the user can not enter Elva intelligent customer service system.<br />
2.<div>
    <table border="0">
      <tr>
        <th>Method</th>
        <th>showElva</th>
        <th>showConversation</th>
        <th>showFAQs</th>
        <th>showFAQSection</th>
        <th>showSingleFAQ</th>
      </tr>
      <tr>
        <td>Purpose</td>
        <td>Start the main robot interface</td>
        <td>Open the manual CS interface</td>
        <td>Show FAQ list</td>
        <td>Show Section</td>
        <td>Show a single FAQ</td>
      </tr>
    </table>
</div>
  
  
# Android SDK Access Instructions
## 1，Here are two ways to access a budget SDK, one way is imported after download, another way is from jcenter is introduced.
## One way :
### Ⅰ. Download Android SDK.
Click the button "Clone or download" in the top right corner to download Android SDK and then unzip the file.

### Ⅱ. Import elvachatservice into project
Copy the elvachatservice folder to your main directory

### Ⅲ. Import Google App Indexing into project
Import play-services-appindexing into your project(IF the item google service appindexing exists, this step can be ignored).

### Ⅳ.Import Android appcompact into project
Import Android appcompact from Android_libs into your project(If the item exsit,this step can be ignored). <br />
If you use Gradle: <br />
Modify the build. Gradle, and add the following section.
> compile 'com.android.support:appcompat-v7:23.4.0' <br />
    compile 'com.android.support:design:23.4.0' <br />
    compile 'com.android.support:recyclerview-v7:23.4.0' <br />
    compile 'com.android.support:cardview-v7:23.4.0' <br />

## Another way
Note: only available on Android Studio or other Gradle -based projects, can be directly modify configuration to increase the introduction of Elva SDK.
 ### Ⅰ. Add the following allprojects to your build.gradle file inside the project section.
 >  <pre> allprojects   {   <br />
 repositories   {   <br />
 jcenter  (  )  }   <br />
        } 

### Ⅱ.Add the following dependencies to your build.gradle file inside the depencencies section.
> dependencies {  <br />
    compile 'net.aihelp:elva:1.0.0'  <br />
    compile 'org.fusesource.mqtt-client:mqtt-client:1.12'  <br />
    compile 'com.android.support:appcompat-v7:23.4.0'  <br />
    compile 'com.android.support:design:23.4.0'  <br />
    compile 'com.android.support:recyclerview-v7:23.4.0'  <br />
    compile 'com.android.support:cardview-v7:23.4.0'  <br />
}  <br />


## 2. Access Project Configuration
Modify the AndroidManifest.xml in elvachatservice folder to add the required configuration:
### Ⅰ. Add the required permissions:
    <Uses-permission android: name = "android.permission.INTERNET" />
    <Uses-permission android: name = "android.permission.ACCESS_NETWORK_STATE" />
    <Uses-permission android: name = "android.permission.WRITE_EXTERNAL_STORAGE" />
    <Uses-permission android: name = "android.permission.READ_EXTERNAL_STORAGE" />
### Ⅱ. Add activity:
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
### Ⅲ、Add meta
      <meta-data
          android:name="com.google.android.gms.version"
          android:value="@integer/google_play_services_version" />

## 3.Interface Call Instructions
### 1. SDK initialization. <br />
Create a JNI environment and the application in the Activity: (must be called at the beginning of the game)<br />
<br />
> call initialization interface in onCreate of the main Activity. then call:<br />
ElvaChatServiceHelper.init (Activity activity, String appKey, String domain, String appId)<br />
> * Parameter Description:<br />
activity: the current operation of the action, parameter "this" can be. <br />
app Key: The app key, obtained from the Web management system.<br />
domain: app Domain name, obtained from the Web management system.<br />
appId: app Unique identifier, obtained from the Web management system.<br />
Note: The latter three parameters, please use the registered email address to login https://aihelp.net/elva. View in the Settings Applications page. Initial use, please register on the official website http://aihelp.net/preview/index.html.
> 

### 2. The interface call method<br />
1) Start smart customer service main interface, call `showElva` method, start the robot interface.<br />
ELvaChatServiceSdk.showElvaChatService(String npcName,String userName,String uid,String parseId,String serverId,String showConversationFlag,HashMap<String,Object> customData);<br />
> * Parameter Description:<br />
playerName: The name of the player in the game.<br />
playerUid: The player's unique id in the game.<br />
serverId: The server ID of the player.<br />
playerParseId: Null.<br />
showConversationFlag (0 or 1): whether VIP, 0: marked non-VIP; 1: VIP. Here is 1, will be in the upper right corner of the robot chat interface, to provide artificial chat entry function.<br />
config: Optional, custom ValueMap information. You can set specific Tag information here.<br />
![showElva](https://github.com/CS30-NET/Pictures/blob/master/showElva-EN-Android.png "showElva")<br />

> * Parameter Example:    <br />
ArrayList tags = new ArrayList();    <br />
// Description: the hs - tags corresponding value is an ArrayList, incoming custom Tag here, need in the Web management configuration with the same name of the Tag    <br />
tags.add("pay1");    <br />
tags.add("s1");    <br />
tags.add("elvaTestTag");    <br />
HashMap<String,Object> map = new HashMap();    <br />
map.put("hs-tags",tags);    <br />
HashMap<String,Object> config = new HashMap();    <br />
config.put("hs-custom-metadata",map);    <br />
ELvaChatServiceSdk.showElvaChatService("elvaTestName","12349303258",1, "","1",config);    <br />
> 
2) Show a single FAQ, call `showSingleFAQ` method<br />
showSingleFAQ(String faqId,HashMap<String,Object> config);<br />
> * Parameter Description:<br />
faqId: FAQ's PublishID, in the Web background https://aihelp.net/elva, from the FAQs menu to find the specified FAQ, view PublishID.<br />
config: Optional, custom ValueMap information. Refer to 1) intelligent customer service main interface starts.<br />
Note: If the SelfServiceInterface is configured in the web administration background, and the SDK is configured with related parameters, the FAQ will be displayed and the function menu will be provided in the upper right corner to call up the related self-service.<br />
![showSingleFAQ](https://github.com/CS30-NET/Pictures/blob/master/showSingleFAQ-EN-Android.png "showSingleFAQ")<br />
> 
3) Show the relevant part of the FAQ, call `showFAQSection` method<br />
showFAQSection(String sectionPublishId,HashMap<String,Object> config); <br />
> * Parameter Description:<br />
sectionPublishId: PublishID of the FAQ Section (PublishID can be viewed from the [Section] menu in the FAQs menu at https://aihelp.net/elva).<br />
config: Optional, custom ValueMap information. Refer to 1) intelligent customer service main interface starts.
![showFAQSection](https://github.com/CS30-NET/Pictures/blob/master/showFAQSection-EN-Android.png "showFAQSection")<br />
> 
4) Show the FAQ list, call `showFAQs` method<br />
showFAQList(HashMap<String,Object> config)<br />
> * Parameter Description:
config: Optional, custom ValueMap information. Refer to 1) intelligent customer service main interface starts.<br />
![showFAQs](https://github.com/CS30-NET/Pictures/blob/master/showFAQs-EN-Android.png "showFAQs")<br />
> 
5) set the game name information, call `setName` method (It is recommended to call this method after calling init)       
setName(String gameName);<br />
Parameter Description:<br />
game_name: The name of the game, which will be displayed in the title bar of the relevant interface in the SDK.<br />
> 
6) Set Token, use google push, call `registerDeviceToken` method (no)<br />
（no）;<br />
> * Parameter Description:<br />
deviceToken: The device Token.<br />
> 
7) Set the user id information, call the `setUserId` method (using self-service must call, see 2) show a single FAQ). Call<br />
setUserId(String playerUid) before showSingleFAQ;<br />
> * Parameter Description:<br />
playerUid: The player unique ID.<br />
> 
8) Set the server number information, call `setServerId` method (using self-service must call, see 2) show a single FAQ). Call<br />
setServerId(String serverId) before showSingleFAQ;<br />
> * Parameter Description:<br />
serverId: Server ID.<br />
> 
9) Set the player name information, call `setUserName` method (It is recommended to call this method after calling init)<br />
setUserName(String userName);<br />
> * Parameter Description:<br />
playerName: The player name.<br />
> 
10) Direct vip_chat artificial customer service chat, call `showConversation` method (must ensure that setUserName in 9) set the
      player name information has been called)<br />
showConversation(String uid,String serverId,HashMap<String,Object> config);<br />

> * Parameter Description:<br />
playerUid: The player's unique id in the game.<br />
serverId: The server ID of the player.<br />
config: Optional, custom ValueMap information. Refer to 1) intelligent customer service main interface starts.<br />
![showConversation](https://github.com/CS30-NET/Pictures/blob/master/showConversation-EN-Android.png "showConversation")
11) Operation Module UI. call `showElvaOP` method to start the operation module ui.<br />
showElvaOP(string playerName, string playerUid, string serverId, string playerParseId, string showConversationFlag, Dictionary\<string,object> config, int defaultTabIndex);
<br />

> * Parameter Description:<br />
              playerName:The player name. <br />
              playerUid:The player's unique id in the game. <br />
              serverId:The server ID of the player. <br />
              playerParseId:Null. <br />
              showConversationFlag (0 or 1): whether VIP, 0: marked non-VIP; 1: VIP. Here is 1, will be in the upper right corner of the robot chat interface, to provide artificial chat entry function.<br />
config: Optional, custom ValueMap information. You can set specific Tag information here.<br />
              defaultTabIndex:Optional，Set the default tab index.（start with 0，if you want to set Elva tab as default，just set it to 999）.<br />	
<br />

> * Parameter Example:      
   <pre>ArrayList<String> tags = new ArrayList();<br />
//Description: the hs - tags corresponding value is an ArrayList, incoming custom Tag here, need in the Web management configuration with the name of the Tag.<br />
tags.add("pay1");<br />
        tags.add("s1");<br />
        tags.add("elvaTestTag");<br />
        HashMap<String,Object> map = new HashMap();<br />
        map.put("hs-tags",tags);<br />
        HashMap<String,Object> config = new HashMap();<br />
        config.put("hs-custom-metadata",map);<br />
ELvaChatServiceSdk.showElvaOP("elvaTestName","12349303258",1, "","1",config,0);<br /><pre />
 
12） different entrance into the different stories. <br />
Use map.put("anotherWelcomeText","heroText");to enable different entrance into the different stories.
> * Parameter Example:      
        <pre>
  ArrayList<String> tags = new ArrayList();
        tags.add("pay1");
        tags.add("s1");
        tags.add("elvaTestTag");
	HashMap<String,Object> map = new HashMap();
        map.put("hs-tags",tags);
//note："heroText" must be the same with "User Say" in the stories。
map.put("anotherWelcomeText","heroText");
HashMap config = new HashMap();
config.put("hs-custom-metadata",map);

 
13)  Set the SDK language，call `setSDKLanguage` method(Elva use the language of the phone by default.Call this method if after init ,and after the language of App has changed if nessary.)<br />
setSDKLanguage (String language);<br />
> * Parameter Description:<br />
language:language alias,eg:en for english,zh_CN for simplified Chinese.For more alias ,see alias in Elva page "settings"-->"language".<br />
> 
