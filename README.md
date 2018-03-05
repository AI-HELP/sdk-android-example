[查看中文接入文档](https://github.com/AI-HELP/AIhelp-Android-SDK/blob/master/README_CN.md)

## AIHelp SDK Integration Guide
There are two methods to integrate AIHelp Android SDK to your APP's project. If you use Android Studio or another development platform which supports Gradle. We recommand you use method #1: 
### Method #1. Use Gradle to integrate AIHelp SDK online: 

#### 1. Add Below Lines in your Project's build.gradle:

	allprojects {
		repositories {
			jcenter()
	}
	
#### 2. Add Below Dependencies in the build.gradle of your APP or Module which intracts with AIHelp sdk:

	dependencies {
	 ...
	    compile 'net.aihelp:elva:1.3.6.2'
	    compile 'com.android.support:appcompat-v7:23.4.0'
	    compile 'com.android.support:design:23.4.0'
	    compile 'com.android.support:recyclerview-v7:23.4.0'
	    compile 'com.android.support:cardview-v7:23.4.0'
    ...
    }

Wait until the build.gradle sync completion and make sure there is no error during sync: Under the "External Libraries" folder of Android Studio Project sturcture view you should be able to find the folder "elva-1.3.6" and other dependencies specified above. If there is an error during sync or you can not find elva folder. Use the Method #2 below:

### Method #2. Download The AIHelp Android SDK：
Select "Clone or download" to download Android SDK in the github page, unzip the downloaded file.

**AIHelp-Android-SDK** Contains:

| Subfolder name | Description |
|:------------- |:---------------|
| **android-libs**    | AIHelp Android SDK dependencies|
| **aihelpsdk**    | AIHelp Android SDK core files|
 

#### Add AIHelp to your Android project:
**a.** Copy files under folder *aihelpsdk/libs* to the the libs folder of your APP's project.

**b.** Copy files under folder * aihelpsdk/res* to the res folder of your APP's project.

**c.** Import dependencies under *android-libs* to your Project：

If your project has already imported some of the dependencies, just import those you do not have. If you use Gradle，all you need to do is add the below dependencies in your _build.gradle_:

    compile 'com.android.support:appcompat-v7:23.4.0'
    compile 'com.android.support:design:23.4.0'
    compile 'com.android.support:recyclerview-v7:23.4.0'
    compile 'com.android.support:cardview-v7:23.4.0'
    # add this if using appindexing:
    compile 'com.google.android.gms:play-services-appindexing:8.1.0'

If you use __Eclipse__ that does not use Gradle, you need to import each of the dependencies into your project as a library. You also need to explicitly add dependency relationship between the AIHelp SDK and the libraries:  
__elvachatservice__ depends on __design__, which depends on __appcompat__, __recyclerview__ and __cardview__.
 
### 3. Configure your Android Manifest
  In the AndroidManifest.xml of your project, add the below information：     
**a. Add Required Permissions**

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
**b. Add AIHelp Activities:**

    <activity
       android:name="com.ljoy.chatbot.ChatMainActivity"
       android:configChanges="orientation|screenSize|locale"
       android:screenOrientation="sensor">
    </activity>
    <activity
       android:name="com.ljoy.chatbot.FAQActivity"
       android:configChanges="orientation|screenSize|locale"
       android:screenOrientation="sensor"
       android:theme="@android:style/Theme.Holo.Light.DarkActionBar">
       <intent-filter android:label="@string/app_name">
          <action android:name="android.intent.action.VIEW" />
          <category android:name="android.intent.category.DEFAULT" />
          <category android:name="android.intent.category.BROWSABLE" />
          <data android:scheme="https"
                android:host="cs30.net"
                android:pathPrefix="/elvaFAQ" />
       </intent-filter>
    </activity>
    <activity
       android:name="com.ljoy.chatbot.OPActivity"
       android:configChanges="orientation|screenSize|locale"
       android:screenOrientation="sensor"
       android:theme="@style/Theme.AppCompat.Light.NoActionBar">
    </activity>
    <activity 
       android:name="com.ljoy.chatbot.WebViewActivity"
       android:screenOrientation="sensor"
       android:configChanges="orientation|screenSize|locale"
       android:theme="@android:style/Theme.Holo.Light.DarkActionBar">
       <intent-filter android:label="@string/app_name">
          <action android:name="android.intent.action.VIEW" />
          <category android:name="android.intent.category.DEFAULT" />
          <category android:name="android.intent.category.BROWSABLE" />
       </intent-filter>
    </activity>
    
About the screen orientations: 
**android:screenOrientation="sensor"** means AIHelp User Interface will adjust display orientation according to the mobile's screen orientation, if you intend to fixate AIHelp UI display, use below setting:

Landscape Display:

	android:screenOrientation="landscape"
Portrait Display:

	android:screenOrientation="portrait"
 

### 4. Initialize AIHelp SDK in your Project

```
Note：
You must use ELvaChatServiceSdk.init(...) in your APP's Initialization Code, otherwise you can't use AIHelp service properly. 

```

```
ELvaChatServiceSdk.init(
				Activity activity,
				String appKey,
				String domain,
				String appId
				)
```
	


**About Parameters：**

| Parameters | Description |
|:------------- |:---------------|
| activity    | Your App's Activty|
| appKey    | Your Unique Developer API Key|
| domain     | Your AIHelp Domain Name. For Example: foo.AIHELP.NET|
| appId     | A Unique ID Assigned to your App.| 

Note: Please log in [AIHelp Web Console](https://console.aihelp.net/elva) with your registration email account to find the __appKey__, __domain__ and __appId__ In the _Application_ page of the _Settings_ Menu. 
If your company does not have an account, you need to register an account at [AIHelp Website](http://aihelp.net/index.html)


**Coding Example：**

```
// Must be called during application/game initialization, otherwise you can't use AIHelp properly

import com.ljoy.chatbot.sdk.ELvaChatServiceSdk;

public class MyActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
          ELvaChatServiceSdk.init(this,
                                  "YOUR_API_KEY",
                                  "YOUR_DOMAIN_NAME",
                                  "YOUR_APP_ID");
        } catch (InstallException e) {
          Log.e(TAG, "invalid init params : ", e);
        }
    }
}
```

---

### 5. Start using AIHelp

#### 1. API Summary

| Method Name| Purpose |Prerequisites|
|:------------- |:---------------|:---------------|
| **[showElva](#showElva)**      | Launch AI Conversation Interface| 
| **[showElvaOP](#showElvaOP)** | Launch Operation Interface| Need to configure Operation Sections|
| **[showFAQs](#showFAQs)** | Show all FAQs by Sections|Need to configure FAQs|
|**[showConversation](#showConversation)**|Launch VIP Conversation Interface| Need to setUserName and setUserId |
| **[showSingleFAQ](#showSingleFAQ)** | Show Single FAQ|Need to Configure FAQ|
| **[setName](#setName)** | Set APP/Game Name|Use it after Initialization|
| **[setUserName](#UserName)** | Set User In-App name|
| **[setUserId](#UserId)** | Set Unique User ID|
| **[setSDKLanguage](#setSDKLanguage)** | Set SDK Language|


**Note：It is not necessary for you to use all the APIs, especially when you only have one user interface for the customer service in your application. Some APIs already contains entry to other APIs, see below for details：**

#### <h4 id="showElva">2. Launch the AI Conversation Interface，use `showElva`</h4>


	ELvaChatServiceSdk.showElva(
				String playerName,
				String playerUid,
				String serverId,
				String showConversationFlag);
			
or

	ELvaChatServiceSdk.showElva(
				String playerName,
				String playerUid,
				String serverId,
				String showConversationFlag,
				HashMap<String,Object> customData);

**Coding Example：**

	// Presenting AI Help Converation with your customers

	HashMap<String,Object> map = new HashMap();
	ArrayList<String> tags = new ArrayList();
	tags.add("pay1");
	tags.add("s1");
	tags.add("elvaTestTag");
	map.put("elva-tags",tags);
	// other data
	HashMap<String,Object> config = new HashMap();
	config.put("elva-custom-metadata",map);
	
	ELvaChatServiceSdk.setName("APP_NAME"); // set APP name
	ELvaChatServiceSdk.showElva(
				"USER_NAME",
				"USER_ID",
				"Server_ID",
				"1", // show conversation entry
				config);

**About Parameters：**

- __playerName__: In-App User Name
- __playerUid__: In-App Unique User ID
- __serverId__: Server ID
- __playerParseId__: Can be empty string, can NOT be NULL
- __showConversationFlag__: Should be "0" or "1". If set at "1", the VIP conversation entry will be displayed in the upper right of the AI conversation interface.
- __config__: Optional parameterseters for custom HashMap information. You can pass specific Tag information using ArrayList elva-tags, see the above coding example. Please note that you also need to configure the same tag information in the Web console so that each conversation can be correctly tagged.
	
![showElva](https://github.com/AIHELP-NET/Pictures/blob/master/showElva-EN-Android.png "showElva")
	
**Best Practice：**

> 1. Use this method to launch your APP's customer service. Configure specific welcome texts and AI story lines in the AIHelp Web Console to better the customer support experiences.
> 2. Enable VIP Conversation Entry to allow user to chat with your human support team with parameters "__showConversationFlag__" setting to "__1__", you may use this method for any user or as a privilege for some users only.

#### <h4 id="showElvaOP">3. Launch The Operation Interface, use `showElvaOP`</h4>

The operation module is useful when you want to present updates, news, articles or any background information about your APP/Game to users. The AI Help

	ELvaChatServiceSdk.showElvaOP(
				String playerName,
				String playerUid,
				String serverId,
				String showConversationFlag);


	ELvaChatServiceSdk.showElvaOP(
				String playerName,
				String playerUid,
				String serverId,
				String showConversationFlag,
				HashMap<String,Object> config);



	ELvaChatServiceSdk.showElvaOP(
				String playerName,
				String playerUid,
				String serverId,
				String showConversationFlag,
				HashMap<String,Object> config,
				int defaultTabIndex);

**Coding Example：**

	// Presenting Operation Info to your customers
	HashMap<String,Object> map = new HashMap();
	ArrayList<String> tags = new ArrayList();
	tags.add("pay1");
	tags.add("s1");
	tags.add("elvaTestTag");
	map.put("elva-tags",tags);
	// other data
	HashMap<String,Object> config = new HashMap();
	config.put("elva-custom-metadata",map);
	
	ELvaChatServiceSdk.setName("APP_NAME"); // set APP name
	ELvaChatServiceSdk.showElvaOP(
				"USER_NAME",
				"USER_ID",
				"Server_ID",
				"1", // show conversation entry
				config);

**About Parameters：**

- __playerName__: User Name in Game/APP
- __playerUid__: Unique User ID
- __serverId__: The Server ID
- __playerParseId__: Can be empty string, can NOT be NULL
- __showConversationFlag__: Should be "0" or "1". If set at "1", the VIP conversation entry will be shown in the top right corner of the AI conversation interface.
- __config__: Optional parameters for custom HashMap information. You can pass specific Tag information using ArrayList elva-tags, see the above coding example. Please note that you also need to configure the same tag information in the Web console so that each conversation can be correctly tagged.
- __defaultTabIndex__: Optional. The index of the first tab to be shown when entering the operation interface. Default value is the left-most tab，if you would like to show the AI conversation interface(the right-most) set it to 999.
	
![showElva](https://github.com/AI-HELP/Docs-Screenshots/blob/master/showElvaOP_Android.png "showElvaOP")

**Best Practice：**
> 1. Use this API to present news, announcements, articles or any useful information to users/players. Configure and publish the information in the AIHelp web console. 

#### <h4 id="showFAQs">4. Display FAQs, use `showFAQs `</h4>

	ELvaChatServiceSdk.showFAQs();

	ELvaChatServiceSdk.showFAQs (HashMap config)

**Coding Example：**

	// Presenting FAQs to your customers
	HashMap<String,Object> map = new HashMap();
	config.put("showConversationFlag","1");//show conversation entry in the top right corner	
	
	ELvaChatServiceSdk.setUserName("USER_NAME"); // set User Name
	ELvaChatServiceSdk.setUserId("USER_ID"); // set User Id
	ELvaChatServiceSdk.setServerId("SERVER_ID"); // set Serve Id
	
	ELvaChatServiceSdk.showFAQs(config);

**About Parameters：**

- __config__: Optional parameters for custom HashMap information. You can pass specific Tag information using the ArrayList elva-tags, see the above coding example. Please note that you also need to configure the same tag information in the Web console so that each conversation can be correctly tagged.
	
![showElva](https://github.com/AI-HELP/Docs-Screenshots/blob/master/showFAQs-EN-Android.png "showFAQs")

**Best Practice：**
> 1. Use this method to show FAQs about your APP/Game properly. Configure FAQs in AIHelp Web Console. Each FAQ can be categroized into a section. If the FAQs are many, you can also add Parent Sections to categorize sections to make things clear and organized. 

#### <h4 id="showSingleFAQ">5. Show A Specific FAQ，use `showSingleFAQ`
</h4>

	ELvaChatServiceSdk.showSingleFAQ(String faqId);

	ELvaChatServiceSdk.showSingleFAQ(String faqId,HashMap config);

**Coding Example：**

	// Presenting Single FAQ to your customers
	
	ELvaChatServiceSdk.setUserName("USER_NAME"); // set User Name
	ELvaChatServiceSdk.setUserId("USER_ID"); // set User Id
	ELvaChatServiceSdk.setServerId("SERVER_ID"); // set Server Id
	
	HashMap<String,Object> map = new HashMap();
	ArrayList<String> tags = new ArrayList();
	tags.add("pay1");
	tags.add("s1");
	tags.add("elvaTestTag");
	map.put("elva-tags",tags);
	// other data
	HashMap<String,Object> config = new HashMap();
	config.put("elva-custom-metadata",map);
	config.put("showConversationFlag","1");// show conversation entry in the top right corner.
	
	ELvaChatServiceSdk.showSingleFAQ("23",config);

**About Parameters：**

- __faqId__: The PublishID of the FAQ item, you can check it at [AIHelp Web Console](https://aihelp.net/elva): Find the FAQ in the FAQ menu and copy its PublishID.
- __config__: Optional parameters for custom HashMap information. You can pass specific Tag information using ArrayList elva-tags, see the above coding example. Please note that you also need to configure the same tag information in the Web console so that each conversation can be correctly tagged.
	
![showSingleFAQ](https://github.com/AIHELP-NET/Pictures/blob/master/showSingleFAQ-EN-Android.png "showSingleFAQ")

<h4 id="selfservice"></h4>

**Self-Service：**

If you configure the "self-service" link in the FAQ's configuration, and set [UserId](#UserId), [UserName](#UserName), [ServerId](#ServerId) in the SDK，then the FAQ shows a functional menu in the right-top cornor.


**Best Practice：**
> 1. Use this method when you want to show a specific FAQ in a proper location of your APP/Game.

#### <h4 id="setName">6. Set Your APP's name for AIHelp SDK to display，use `setName`</h4>

	ELvaChatServiceSdk.setName(String game_name);

**Coding Example：**

	ELvaChatServiceSdk.setName("Your Game");

**About Parameters：**

- __game_name__: APP/Game Name

**Best Practice：**
> 1. Use this method after SDK initialization.The App's name will be displayed in the title bar of the customer service interface.

#### <h4 id="UserId">7. Set the Unique User ID, use `setUserId`</h4>


	ELvaChatServiceSdk.setUserId(String playerUid);

**Coding Example：**

	ELvaChatServiceSdk.setUserId("123ABC567DEF");

**About Parameters：**

- __playerUid__:Unique User ID

**Best Practice：**
> 1. Normally you do not need to use this method if you have passed the user ID in another method. However, if you want to use the FAQ's [Self-Service](#selfservice), then you must set the User Id first.

#### <h4 id="UserName">8. Set User Name，use `setUserName`</h4>

	ELvaChatServiceSdk.setUserName (String playerName);

**Coding Example：**

	ELvaChatServiceSdk.setUserName ("PLAYER_NAME");

**About Parameters：**

- __playerName:__ User/Player Name

**Best Practice：**
> 1. Use this method to set the user name, which will be shown in the web console's conversation page for the user. You can address the user with this name during the chat.
> 2. Normally you do not need to use this method if you have passed the user name in another method. However, if you want to use FAQ's [Self-Service](#selfservice), then you must set the User Id first.

#### <h4 id="ServerId">9. Set Unique Server ID，use `setServerId`
</h4>

	ELvaChatServiceSdk.setServerId(String serverId);

**Coding Example：**

	ELvaChatServiceSdk.setServerId("SERVER_ID");

**About Parameters：**

- __serverId:__ The Unique Server ID

**Best Practice：**
> 1. Normally you do not need to use this method if you have passed the server ID in other method. However, if you want to use the FAQ's [Self-Service](#selfservice), then you must set the User Id first.

#### <h4 id="showConversation">10. Launch VIP Chat Console, use `showConversation`(need to set [UserName](#UserName))</h4>

	ELvaChatServiceSdk.showConversation(
					String playerUid,
					String serverId);

or

	ELvaChatServiceSdk.showConversation(
					String playerUid,
					String serverId,
					HashMap config);
	
**Coding Example：**

	HashMap<String,Object> map = new HashMap();
	ArrayList<String> tags = new ArrayList();
	tags.add("pay1");
	tags.add("s1");
	tags.add("elvaTestTag");
	map.put("elva-tags",tags);
	//other data
	HashMap<String,Object> config = new HashMap();
	config.put("elva-custom-metadata",map);
	
	ELvaChatServiceSdk.setUserName("USER_NAME");
	ELvaChatServiceSdk.showConversation("USER_ID","SERVER_ID",config);

**About Parameters：**

- __playerUid__:Unique User ID
- __serverId:__ The Unique Server ID
- __config__: Optional parameters for custom HashMap information. You can pass specific Tag information using the ArrayList elva-tags, see the above coding example. Please note that you also need to configure the same tag information in the Web console so that each conversation can be correctly tagged.

**Best Practice：**
> 1. Normally you do not need to use this method unless you intend to allow users to enter VIP conversations without engaging with the AI chat. You may use this method as a privilege for some users.

![showConversation](https://github.com/AIHELP-NET/Pictures/blob/master/showConversation-EN-Android.png "showConversation")


#### <h4 id="setSDKLanguage">11. Set SDK Lanague，use `setSDKLanguage`
</h4>
Setting the SDK Language will change the FAQs, Operational information, AI Chat and SDK display language. 

	ELvaChatServiceSdk.setSDKLanguage(string language);
	
**Coding Example：**

	ELvaChatServiceSdk.setSDKLanguage("en");

**About Parameters：**

- __language:__ Standard Language Alias. For example: en is for English, zh_CN is for Simplified Chinese. More language label can be selected through AIHelp Web Console:"Settings"-->"Language"->Alias.

![language](https://github.com/AI-HELP/Docs-Screenshots/blob/master/Language-alias.png "Language Alias")

**Best Practice：**
> 1. Normally AIHelp will use the mobile's lanaguge configuration by default. If you intend to make a different language setting, you need to use this method right after the SDK initialization.
> 2. If your allow users to change the APP language, then you need to call this method to make AIHelp the same lanague with your APP.

#### 12. Set a Different Greeting Story Line.

If your APP provides multiple entries to AIHelp, and you intend to introduce different AI welcome texts and story lines to users from different entries, you can set the config parameters in [showElva](#showElva) or [showElvaOP](#showElvaOP)： 

	map.put("anotherWelcomeText","usersay");

**Coding Example：**


	HashMap<String,Object> map = new HashMap();
	ArrayList<String> tags = new ArrayList();
	tags.add("pay1");
	tags.add("s1");
	tags.add("elvaTestTag");
	map.put("elva-tags",tags);
	// note：anotherWelcomeText is key，should be unchanged.
	// you need to change usersay according to the "User Say" in your new 
	// story line
	map.put("anotherWelcomeText","usersay");
	HashMap<String,Object> config = new HashMap();
	config.put("elva-custom-metadata",map);
	
	ELvaChatServiceSdk.setName("APP_NAME"); // set APP Name
	
	// Enter show AI support
	ELvaChatServiceSdk.showElva(
				"USER_NAME",
				"USER_ID",
				"Server_ID",
				"1", // show conversation entry
				config);
Or

	// Enter operational module
	ELvaChatServiceSdk.showElvaOP(
				"USER_NAME",
				"USER_ID",
				"Server_ID",
				"1", // show conversation entry
				config);


**Best Practice：**
> 1. Introduce different story lines to users from different sources.

