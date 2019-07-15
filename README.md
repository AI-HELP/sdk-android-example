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
	    compile 'net.aihelp:elva:1.4.4.5'
	    compile 'com.android.support:appcompat-v7:23.4.0'
	    compile 'com.android.support:design:23.4.0'
	    compile 'com.android.support:recyclerview-v7:23.4.0'
	    compile 'com.android.support:cardview-v7:23.4.0'
    ...
    }

Wait until the build.gradle sync completion and make sure there is no error during sync: Under the "External Libraries" folder of Android Studio Project sturcture view you should be able to find the folder "elva-1.4.4" and other dependencies specified above. If there is an error during sync or you can not find elva folder. Use the Method #2 below:

### Method #2. Download The AIHelp Android SDK：
Click "Clone or download" to download Android SDK in the github page, unzip the downloaded file.

**AIHelp-Android-SDK** Contains:

| Subfolder name | Description |
|:------------- |:---------------|
| **android-libs**    | AIHelp Android SDK dependencies|
| **aihelpsdk**    | AIHelp Android SDK core files|
 

#### Add AIHelp to your Android project:
**a.** Copy files under folder *aihelpsdk/libs* to the the libs folder of your APP's project.

**b.** Copy files under folder * aihelpsdk/res* to the res folder of your APP's project.

**c.** Import dependencies under *android-libs* to your Project：

If your project has already imported some of the dependencies, just import those you do not have. If you use Gradle, all you need to do is add the below dependencies in your _build.gradle_:

    compile 'com.android.support:appcompat-v7:23.4.0'
    compile 'com.android.support:design:23.4.0'
    compile 'com.android.support:recyclerview-v7:23.4.0'
    compile 'com.android.support:cardview-v7:23.4.0'

If you use __Eclipse__ that does not use Gradle, you need to import each of the dependencies into your project as a library. You also need to explicitly add dependency relationship between the AIHelp SDK and the libraries:  
__elvachatservice__ depends on __design__, which depends on __appcompat__, __recyclerview__ and __cardview__.
 
### 3. Configure your Android Manifest
  In the AndroidManifest.xml of your project, add the below information：     
  <uses-sdk android:minSdkVersion="14" android:targetSdkVersion="23"/>
**a. Add Required Permissions**

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />//This permission is required when uploading a form image
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />//This permission is required when uploading a form image
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
    <activity
            android:name="com.ljoy.chatbot.QAWebActivity"
            android:configChanges="orientation|screenSize|locale" >
    </activity>
    
About the screen orientations: 
**android:screenOrientation="sensor"** means AIHelp User Interface will adjust display orientation according to the mobile's screen orientation, if you intend to fixate AIHelp UI display, use below setting:

Landscape Display:

	android:screenOrientation="landscape"
Portrait Display:

	android:screenOrientation="portrait"
 

### 4. Initialize AIHelp SDK in your Project
	**Note：
	Party A is obliged to use Party B's services according to the correct plug-in method and calling method described by Party B's documents. 
	If Party A uses any technical method to influence Party B's billing, 
    Party B will have the right to notify Party A while unilaterally terminating the service immediately 
    And ask Party A to assume responsibility for infulencing the billing of Party B.
    When activiting your app, You must use ELvaChatServiceSdk.init(...) , otherwise you can't use AIHelp service properly. **	
	
	ELvaChatServiceSdk.init(
				Activity activity,
				String appKey,
				String domain,
				String appId
				);

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
// Must be called during application/game initialization, otherwise you can't use AIHelp APIs properly.
// You can set initialiation callback to track if AIHelp SDK initialization is completed.

// Before Init, set initializaiton callback method
    public void setInitCallback() {
        ELvaChatServiceSdk.setOnInitializedCallback(new ELvaChatServiceSdk.OnInitializationCallback() {
            @Override
            public void onInitialized() {
                System.out.println("AIHelp elva Initialization Done!");
            }
        });
    }

```

```

import com.ljoy.chatbot.sdk.ELvaChatServiceSdk;

public class MyActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
          // Set Init Callback
          setInitCallback();
          // Init AIHelp SDK
          ELvaChatServiceSdk.init(this,
                                  "your_app_key",
                                  "your_domain_name",
                                  "your_app_id");
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
| **[showFAQs](#showFAQs)** | Show all FAQs by Sections|Need to configure FAQs,Need to [setUserName](#UserName) and [setUserId](#UserId)|
|**[showConversation](#showConversation)**|Launch VIP Conversation Interface| Need to [setUserName](#UserName) and [setUserId](#UserId) |
| **[showSingleFAQ](#showSingleFAQ)** | Show Single FAQ|Need to Configure FAQ,Need to [setUserName](#UserName) and [setUserId](#UserId)|
| **[setName](#setName)** | Set APP/Game Name|Use it after Initialization|
| **[setUserName](#UserName)** | Set User In-App name| If the username is not obtained, the empty string "" is passed, and the default nickname "anonymous" is used.
| **[setUserId](#UserId)** | Set Unique User ID| If the userid is not available, the empty string "" is passed, and a unique device id is generated.
| **[setSDKLanguage](#setSDKLanguage)** | Set SDK Language|


**Note：It is not necessary for you to use all the APIs, especially when you only have one user interface for the customer service in your application. Some APIs already contains entry to other APIs, see below for details：**

#### <h4 id="showElva">2. Launch the AI Conversation Interface, use `showElva`</h4>


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
	// the tag names are variables
	tags.add("pay1");
	tags.add("s1");
	tags.add("vip2");
	
	// “elva-tags" is the key name, not a variable. 
	map.put("elva-tags",tags); 
	
	HashMap<String,Object> config = new HashMap();
	
	// “elva-custom-metadata" is the key name, not a variable. 
	config.put("elva-custom-metadata",map);
	
	ELvaChatServiceSdk.setName("app_name"); // set APP name
	ELvaChatServiceSdk.showElva(
				"user_name",
				"user_id",
				"server_id",
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
> 2. Enable VIP Conversation Entry to allow user to chat with your customer support team with parameters "__showConversationFlag__" setting to "__1__", you may use this method for any user or as a privilege for some users only.

#### <h4 id="showConversation">3. Call the `showConversation` method to start the manual customer service interface.</h4>

	ELvaChatServiceSdk.showConversation(
					String uid,
					String serverId);

or

	ELvaChatServiceSdk.showConversation(
						String uid,
						String serverId,
						HashMap customData);
						
**Coding Example：**

	HashMap<String,Object> map = new HashMap();
	ArrayList<String> tags = new ArrayList();
	// the tag names are variables
	tags.add("pay1");
	tags.add("s1");
	tags.add("vip2");
	
	// “elva-tags" is the key name, not a variable. 
	map.put("elva-tags",tags); 
	
	HashMap<String,Object> config = new HashMap();
	
	// "elva-custom-metadata" is the key name, not a variable. 
	config.put("elva-custom-metadata",map);
	
	ELvaChatServiceSdk.showConversation(
				"user_id",
				"server_id",
				config);

**About Parameters：**


- __user_id__: Unique User ID
- __serverId__: The Server ID
- __config__: Optional parameters for custom HashMap information. You can pass specific Tag information using ArrayList elva-tags, see the above coding example. Please note that you also need to configure the same tag information in the Web console so that each conversation can be correctly tagged.


Manual customer service interface example diagram:<br>
![Manual customer service interface][showConversation-CN-Android]

**Best Practices：**
> 1. Usually you don't need to call this interface unless you want to set a trigger point in the app, giving the user a chance to go directly to the manual chat interface.

---


#### <h4 id="showElvaOP">4. Launch The Operation Interface, use `showElvaOP`</h4>

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
	// the tag names are variables
	tags.add("pay1");
	tags.add("s1");
	tags.add("vip2");
	
	// “elva-tags" is the key name, not a variable. 
	map.put("elva-tags",tags); 
	
	HashMap<String,Object> config = new HashMap();
	
	// “elva-custom-metadata" is the key name, not a variable. 
	config.put("elva-custom-metadata",map);
	
	ELvaChatServiceSdk.setName("app_id"); // set APP name
	ELvaChatServiceSdk.showElvaOP(
				"user_name",
				"user_id",
				"server_id",
				"1", // show conversation entry
				config);

**About Parameters：**

- __playerName__: User Name in Game/APP
- __playerUid__: Unique User ID
- __serverId__: The Server ID
- __playerParseId__: Can be empty string, can NOT be NULL
- __showConversationFlag__: Should be "0" or "1". If set at "1", the VIP conversation entry will be shown in the top right corner of the AI conversation interface.
- __config__: Optional parameters for custom HashMap information. You can pass specific Tag information using ArrayList elva-tags, see the above coding example. Please note that you also need to configure the same tag information in the Web console so that each conversation can be correctly tagged.
- __defaultTabIndex__: Optional. The index of the first tab to be shown when entering the operation interface. Default value is 0, default value is the left-most tab, if you would like to show the AI conversation interface(the right-most) set it to 999.
	
![showElva](https://github.com/AI-HELP/Docs-Screenshots/blob/master/showElvaOP_Android.png "showElvaOP")

**Best Practice：**
> 1. Use this API to present news, announcements, articles or any useful information to users/players. Configure and publish the information in the AIHelp web console. 

#### <h4 id="showFAQs">5. Display FAQs, use `showFAQs ` 
	(need to set [`setUserName`](#UserName) and [`setUserId`](#UserId))
	If the username is not obtained, the empty string "" is passed, and the default nickname "anonymous" is used.
	If the userid is not available, the empty string "" is passed, and a unique device id is generated.
	</h4>

	ELvaChatServiceSdk.showFAQs();

	ELvaChatServiceSdk.showFAQs (HashMap config)

**Coding Example：**

	HashMap<String,Object> map = new HashMap();
	ArrayList<String> tags = new ArrayList();
	// the tag names are variables
	tags.add("pay1");
	tags.add("s1");
	tags.add("vip2");
	
	// "elva-tags" is the key name, not a variable
	map.put("elva-tags",tags); 
	
	HashMap<String,Object> config = new HashMap();
	
	// "elva-custom-metadata" is the key name, not a variable
	config.put("elva-custom-metadata",map);	
	config.put("showContactButtonFlag", "1"); // The display can be accessed from the upper right corner of the FAQ list (if you do not want to display it, you need to delete this parameter)
	config.put("showConversationFlag", "1"); // Click on the upper right corner of the FAQ to enter the upper right corner of the robot interface. (If you do not want to display, you need to delete this parameter.)
	config.put("directConversation", "1");// Click on the upper right corner of the FAQ and you will be taken to the manual customer service page (without adding the default to the robot interface. If you don't need it, delete this parameter)
	
	ELvaChatServiceSdk.setUserName("user_name"); // set User Name If the username is not obtained, the empty string "" is passed, and the default nickname "anonymous" is used.
    ELvaChatServiceSdk.setUserId("user_id"); // set User Id If the userid is not available, the empty string "" is passed, and a unique device id is generated.
    ELvaChatServiceSdk.setServerId("server_id"); // set Serve Id

	ELvaChatServiceSdk.showFAQs(config);

**About Parameters：**

- __config__: Optional parameters for custom HashMap information. You can pass specific Tag information using the ArrayList elva-tags, see the above coding example. Please note that you also need to configure the same tag information in the Web console so that each conversation can be correctly tagged.
	
![showElva](https://github.com/AI-HELP/Docs-Screenshots/blob/master/showFAQs-EN-Android.png "showFAQs")

**Best Practice：**
> 1. Use this method to show FAQs about your APP/Game properly. Configure FAQs in AIHelp Web Console. Each FAQ can be categroized into a section. If the FAQs are many, you can also add Parent Sections to categorize sections to make things clear and organized. 

#### <h4 id="showFAQSection">6. Show all the FAQs in a category, call the `showFAQSection` method 
    (must make sure to set the player name information [setUserName](#UserName) And setting the player's unique id information [setUserId](#UserId) Already called)
	If the username is not obtained, the empty string "" is passed, and the default nickname "anonymous" is used.
	If the userid is not available, the empty string "" is passed, and a unique device id is generated.
	</h4>

	ELvaChatServiceSdk.showFAQSection(String sectionPublishId); 

or

	ELvaChatServiceSdk.showFAQSection(String sectionPublishId,HashMap customData);

**Coding Example：**

	HashMap<String,Object> map = new HashMap();
	ArrayList<String> tags = new ArrayList();
	// the tag names are variables
	tags.add("pay1");
	tags.add("s1");
	tags.add("vip2");
	
	// "elva-tags" is the key name, not a variable
	map.put("elva-tags",tags); 
	
	HashMap<String,Object> config = new HashMap();
	
	// "elva-custom-metadata" is the key name, not a variable
	config.put("elva-custom-metadata",map);	
	config.put("showContactButtonFlag", "1"); // The display can be accessed from the upper right corner of the FAQ list (if you do not want to display it, you need to delete this parameter)
	config.put("showConversationFlag", "1"); // Click on the upper right corner of the FAQ to enter the upper right corner of the robot interface. (If you do not want to display, you need to delete this parameter.)
	config.put("directConversation", "1");// Click on the upper right corner of the FAQ and you will be taken to the manual customer service page (without adding the default to the robot interface. If you don't need it, delete this parameter)
	
	ELvaChatServiceSdk.setUserName("user_name"); // set User Name If the username is not obtained, the empty string "" is passed, and the default nickname "anonymous" is used.
    ELvaChatServiceSdk.setUserId("user_id"); // set User Id If the userid is not available, the empty string "" is passed, and a unique device id is generated.
    ELvaChatServiceSdk.setServerId("server_id"); // set Serve Id

	ELvaChatServiceSdk.showFAQSection("1234",config);

**About Parameters：**

| sectionPublishId | String | The classification number of the FAQ classification. Open [AIHelp Customer Service Background][1] and find the category number of the specified FAQ category under **Robot→Frequently Asked Questions→[Classification]**. Note: This sectionPublishId cannot fill in the category number that does not exist in the customer service background.

- __config__: Optional parameters for custom HashMap information. You can pass specific Tag information using the ArrayList elva-tags, see the above coding example. Please note that you also need to configure the same tag information in the Web console so that each conversation can be correctly tagged.
	
Example map of all FAQ interfaces under the classification:<br>
![All FAQ screens under category][showFAQSection-CN-Android]

**Best Practices:**
> 1. In the FAQ entry of your application, trigger the call of this interface. For example, you have configured the category FAQ about the mall or recharge in the [AIHelp Customer Support] [1] page. After you call this interface in your store interface or recharge interface, all the FAQs in this category will be displayed.

---

#### <h4 id="showSingleFAQ">7. Show A Specific FAQ, use `showSingleFAQ` 
	(need to set [`setUserName`](#UserName) and [`setUserId`](#UserId))
	If the username is not obtained, the empty string "" is passed, and the default nickname "anonymous" is used.
	If the userid is not available, the empty string "" is passed, and a unique device id is generated.
	</h4>

	ELvaChatServiceSdk.showSingleFAQ(String faqId);

	ELvaChatServiceSdk.showSingleFAQ(String faqId,HashMap config);

**Coding Example：**

	// Presenting Single FAQ to your customers
	
	ELvaChatServiceSdk.setUserName("user_name"); // set User Name If the username is not obtained, the empty string "" is passed, and the default nickname "anonymous" is used.
	ELvaChatServiceSdk.setUserId("user_id"); // set User Id If the userid is not available, the empty string "" is passed, and a unique device id is generated.
	ELvaChatServiceSdk.setServerId("server_id"); // set Server Id
	
	HashMap<String,Object> map = new HashMap();
	ArrayList<String> tags = new ArrayList();
	// the tag names are variables
	tags.add("pay1");
	tags.add("s1");
	tags.add("vip2");
	
	// “elva-tags" is the key name, not a variable. 
	map.put("elva-tags",tags); 
	
	HashMap<String,Object> config = new HashMap();
	config.put("elva-custom-metadata",map);	
	config.put("showContactButtonFlag", "1"); // The display can be accessed from the upper right corner of the FAQ list (if you do not want to display it, you need to delete this parameter)
	config.put("showConversationFlag", "1"); // Click on the upper right corner of the FAQ to enter the upper right corner of the robot interface. (If you do not want to display, you need to delete this parameter.)
	config.put("directConversation", "1");// Click on the upper right corner of the FAQ and you will be taken to the manual customer service page (without adding the default to the robot interface. If you don't need it, delete this parameter)
	
	ELvaChatServiceSdk.setUserName("user_name"); // set User Name If the username is not obtained, the empty string "" is passed, and the default nickname "anonymous" is used.
    ELvaChatServiceSdk.setUserId("user_id"); // set User Id If the userid is not available, the empty string "" is passed, and a unique device id is generated.
    ELvaChatServiceSdk.setServerId("server_id"); // set Serve Id

	ELvaChatServiceSdk.showSingleFAQ("2345",config);

**About Parameters：**

- __faqId__: The PublishID of the FAQ item, you can check it at [AIHelp Web Console](https://aihelp.net/elva): Find the FAQ in the FAQ menu and copy its PublishID.
- __config__: Optional parameters for custom HashMap information. You can pass specific Tag information using ArrayList elva-tags, see the above coding example. Please note that you also need to configure the same tag information in the Web console so that each conversation can be correctly tagged.
	
![showSingleFAQ](https://github.com/AIHELP-NET/Pictures/blob/master/showSingleFAQ-EN-Android.png "showSingleFAQ")

<h4 id="selfservice"></h4>

**Self-Service：**

If you configure the "self-service" link in the FAQ's configuration, and set [UserId](#UserId), [UserName](#UserName), [ServerId](#ServerId) in the SDK, then the FAQ shows a functional menu in the right-top cornor.


**Best Practice：**
> 1. Use this method when you want to show a specific FAQ in a proper location of your APP/Game.

#### <h4 id="setName">6. Set Your APP's name for AIHelp SDK to display, use `setName`</h4>

	ELvaChatServiceSdk.setName(String game_name);

**Coding Example：**

	ELvaChatServiceSdk.setName("Your Game");

**About Parameters：**

- __game_name__: APP/Game Name

**Best Practice：**
> 1. Use this method after SDK initialization.The App's name will be displayed in the title bar of the customer service interface.

#### <h4 id="UserId">7. Set the Unique User ID, use `setUserId`</h4>


	ELvaChatServiceSdk.setUserId(String playerUid); If the userid is not available, the empty string "" is passed, and a unique device id is generated.

**Coding Example：**

	ELvaChatServiceSdk.setUserId("123Abc567DEF"); If the userid is not available, the empty string "" is passed, and a unique device id is generated.

**About Parameters：**

- __playerUid__:Unique User ID

**Best Practice：**
> 1. Normally you do not need to use this method if you have passed the user ID in another method. However, if you want to use the FAQ's [Self-Service](#selfservice), then you must set the User Id first.

#### <h4 id="UserName">8. Set User Name, use `setUserName`</h4>

	ELvaChatServiceSdk.setUserName (String playerName); If the username is not obtained, the empty string "" is passed, and the default nickname "anonymous" is used.

**Coding Example：**

	ELvaChatServiceSdk.setUserName ("player_name"); If the username is not obtained, the empty string "" is passed, and the default nickname "anonymous" is used.

**About Parameters：**

- __playerName:__ User/Player Name

**Best Practice：**
> 1. Use this method to set the user name, which will be shown in the web console's conversation page for the user. You can address the user with this name during the chat.
> 2. Normally you do not need to use this method if you have passed the user name in another method. However, if you want to use FAQ's [Self-Service](#selfservice), then you must set the User Id first.

#### <h4 id="ServerId">9. Set Unique Server ID, use `setServerId`
</h4>

	ELvaChatServiceSdk.setServerId(String serverId);

**Coding Example：**

	ELvaChatServiceSdk.setServerId("server_id");

**About Parameters：**

- __serverId:__ The Unique Server ID

**Best Practice：**
> 1. Normally you do not need to use this method if you have passed the server ID in other method. However, if you want to use the FAQ's [Self-Service](#selfservice), then you must set the User Id first.

#### <h4 id="showConversation">10. Launch VIP Chat Console, use `showConversation`
	(need to set [`setUserName`](#UserName))
	If the username is not obtained, the empty string "" is passed, and the default nickname "anonymous" is used.	
	</h4>

	ELvaChatServiceSdk.showConversation(
					String playerUid,
					String serverId);

or

	ELvaChatServiceSdk.showConversation(
					String playerUid,
					String serverId,
					HashMap config);
	
**Coding Example：**

	// Presenting Single FAQ to your customers
	HashMap<String,Object> map = new HashMap();
	ArrayList<String> tags = new ArrayList();
	// the tag names are variables
	tags.add("pay1");
	tags.add("s1");
	tags.add("vip2");
	
	// “elva-tags" is the key name, not a variable. 
	map.put("elva-tags",tags); 
	
	HashMap<String,Object> config = new HashMap();
	
	// “elva-custom-metadata" is the key name, not a variable. 
	config.put("elva-custom-metadata",map);
	
	ELvaChatServiceSdk.setUserName("user_name"); If the username is not obtained, the empty string "" is passed, and the default nickname "anonymous" is used.
	ELvaChatServiceSdk.showConversation("user_id","server_id",config);

**About Parameters：**

- __playerUid__:Unique User ID
- __serverId:__ The Unique Server ID
- __config__: Optional parameters for custom HashMap information. You can pass specific Tag information using the ArrayList elva-tags, see the above coding example. Please note that you also need to configure the same tag information in the Web console so that each conversation can be correctly tagged.

**Best Practice：**
> 1. Normally you do not need to use this method unless you intend to allow users to enter VIP conversations without engaging with the AI chat. You may use this method as a privilege for some users.

![showConversation](https://github.com/AIHELP-NET/Pictures/blob/master/showConversation-EN-Android.png "showConversation")


#### <h4 id="setSDKLanguage">11. Set SDK Lanague, use `setSDKLanguage`
</h4>
Setting the SDK Language will change the FAQs, Operational information, AI Chat and SDK display language. 

	ELvaChatServiceSdk.setSDKLanguage(string language);
	
**Coding Example：**

	string languageAlias = "zh_CN"
	ELvaChatServiceSdk.setSDKLanguage(languageAlias);

**About Parameters：**

- __language:__ Standard Language Alias. For example: en is for English, zh_CN is for Simplified Chinese. More language label can be selected through AIHelp Web Console:"Settings"-->"Language"->Alias.

![language](https://github.com/AI-HELP/Docs-Screenshots/blob/master/Language-alias.png "Language Alias")

**Best Practice：**
> 1. Normally AIHelp will use the mobile's lanaguge configuration by default. If you intend to make a different language setting, you need to use this method right after the SDK initialization.
> 2. If your allow users to change the APP language, then you need to call this method to make AIHelp the same lanague with your APP.

#### 12. Set a Different Greeting Story Line.

If your APP provides multiple entries to AIHelp, and you intend to introduce different AI welcome texts and story lines to users from different entries, you can set the config parameters in [showElva](#showElva) or [showElvaOP](#showElvaOP)： 

	// "anotherWelcomeText" is the key name, it's value is the usersay variable
	map.put("anotherWelcomeText","usersay");

**Coding Example：**


	// Presenting Single FAQ to your customers
	HashMap<String,Object> map = new HashMap();
	ArrayList<String> tags = new ArrayList();
	// the tag names are variables
	tags.add("pay1");
	tags.add("s1");
	tags.add("vip2");
	
	// “elva-tags" is the key name, not a variable. 
	map.put("elva-tags",tags); 
	
	// "anotherWelcomeText" is the key name, it's value is the usersay variable
	map.put("anotherWelcomeText","usersay");
	
	HashMap<String,Object> config = new HashMap();
	
	// “elva-custom-metadata" is the key name, not a variable. 
	config.put("elva-custom-metadata",map);
	
	ELvaChatServiceSdk.setName("app_name"); // set APP Name
	
	// Enter show AI support
	ELvaChatServiceSdk.showElva(
				"user_name",
				"user_id",
				"server_id",
				"1", // show conversation entry
				config);
Or

	// Enter operational module
	ELvaChatServiceSdk.showElvaOP(
				"user_name",
				"user_id",
				"server_id",
				"1", // show conversation entry
				config);
				
#### 13. Want to customize the welcome message of manual customer service
###If you want to customize the welcome message of the manual customer service, 
you need to pass a new pair of keys in the configuration parameters of the corresponding interface.
The key is: "private_welcome_str", valued for the customized content you want
###code example:
	HashMap <String,Object> map = new HashMap();

	//"private_welcome_str" is the key, the value is the custom content you want, the type is a string
	Map.put( "private_welcome_str", "usersay");
	HashMap <String,Object> config = new HashMap();

	//"elva-custom-metadata" is a key value that cannot be changed
	Config.put ("Xiao Yaxuan Custom Metadata", map);

	//Call showElva interface to enter the robot customer service interface
	ElvaChatServiceSdk.showElva(
				"user_name",
				"user_id",
				"server_id",
				"1",
				config);


**Best Practice：**
> 1. Introduce different story lines to users from different sources.

[1]: https://AIHelp.net/elva "AIHelp Customer Service Backstage"
[2]: https://AIHelp.net/register "AIHelp official website registration"
[showElva-CN-Android]: https://github.com/AI-HELP/Docs-Screenshots/blob/master/showElva-CN-Android.png "Robot Customer Service Interface"
[showConversation-CN-Android]: https://github.com/AI-HELP/Docs-Screenshots/blob/master/showConversation-CN-Android.png "Manual Customer Service Interface"
[showElvaOP-CN-Android]: https://github.com/AI-HELP/Docs-Screenshots/blob/master/showElvaOP-CN-Android.png "Operation Module Interface"
[showFAQs-CN-Android]: https://github.com/AI-HELP/Docs-Screenshots/blob/master/showFAQs-CN-Android.png "FAQ Interface"
[showFAQSection-CN-Android]: https://github.com/AI-HELP/Docs-Screenshots/blob/master/showFAQSection-CN-Android.png "All FAQ Interfaces under Category"
[showSingleFAQ-CN-Android]: https://github.com/AI-HELP/Docs-Screenshots/blob/master/showSingleFAQ-CN-Android.png "Single FAQ Interface"
[language]: https://github.com/AI-HELP/Docs-Screenshots/blob/master/Language-alias.png "Partial Language Short Name"

