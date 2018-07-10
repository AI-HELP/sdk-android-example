## 接入说明
**有两种方式可以接入AIHelp智能客服系统的Android版SDK。如果您使用了Android Studio等可以支持Gradle的环境，推荐使用第一种方式导入：**
### 导入方式一： 无需手动下载AIHelp Android SDK，通过gradle自动导入：

#### 1. 在Project级别的build.gradle中加入:

	allprojects {
		repositories {
			jcenter()
		}
	}
	
#### 2. 在使用AIHelp SDK的app或module级别的build.gradle中加入依赖：

	dependencies {
	 ...
	    compile 'net.aihelp:elva:1.4.0.5'
	    compile 'com.android.support:appcompat-v7:23.4.0'
	    compile 'com.android.support:design:23.4.0'
	    compile 'com.android.support:recyclerview-v7:23.4.0'
	    compile 'com.android.support:cardview-v7:23.4.0'
    ...
    }

确保build.gradle同步成功: 在Android Studio的External Libraries下面能够看到加载成功elva-1.4.0.1文件夹以及上述依赖包。如果无法自动加载，请采用第二种导入方式：

### 导入方式二： 下载AIHelp Android SDK：
点击页面右上角的"Clone or Download”按钮下载Android SDK，下载完成后解压文件。
AIHelp-Android-SDK文件夹包含：

| 文件夹 | 说明 |
|:------------- |:---------------|
| **aars**    | AIHelp Android SDK 所需的依赖包|
| **aihelpsdk**    | AIHelp Android SDK文件|
#### 把AIHelp SDK 放入您的Android 工程：
**a. 把aihelpsdk中libs子文件夹中全部包拷贝到您的工程app中的libs文件夹下**

**b. 把aihelpsdk中的res子文件夹中全部包拷贝到您的工程app中的的res文件夹下**

**c. 把aars所需要的依赖包导入您工程：**

如果您的项目已经包含了某些依赖包，只需导入其他的。如果您使用了Gradle，那么只需要修改build.gradle，增加如下依赖(如果您的build.gradle没有类似的依赖关系)

    compile 'com.android.support:appcompat-v7:23.4.0'
    compile 'com.android.support:design:23.4.0'
    compile 'com.android.support:recyclerview-v7:23.4.0'
    compile 'com.android.support:cardview-v7:23.4.0'

如果您使用了Eclipse，并没有用Gradle，您需要把依赖包导入到您的工程中作为library，并且增加依赖关系给AIHelp SDK。具体依赖关系: AIHelpsdk依赖于design，后者依赖于appcompat，recyclerview 和cardview。
 
## 接入工程配置
在您的Android工程的AndroidManifest.xml，增加需要的配置：  
  
**1. 打包版本要求**

AIHelp SDK 要求android sdk最低版本为14，目标最低版本为23：

	<uses-sdk android:minSdkVersion="14" android:targetSdkVersion="23"/>
     
**2. 增加需要的权限**

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
**3. 增加activity:**

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
    
    
关于横竖屏显示：上述配置中
**android:screenOrientation="sensor"** 代表AIHelp的UI会根据手机屏幕方向自动调整横竖屏。如果您要固定AIHelp屏幕展示，请做如下修改：

横屏展示：

	android:screenOrientation="landscape"
竖屏展示：

	android:screenOrientation="portrait"


## 在您的工程中初始化AIHelp SDK

	注意：
	在您的APP初始化时调用 ELvaChatServiceSdk.init(...)，传入必要的参数。
	
	ELvaChatServiceSdk.init(
				Activity activity,
				String appKey,
				String domain,
				String appId
				);
					
	
	

* 参数说明：

| 参数 | 说明 |
|:------------- |:---------------|
| activity    | 应用的Activty|
| appKey    | app唯一密钥，从[AIHelp 客服后台][1]获取|
| domain     | 您的AIHelp域名，从[AIHelp 客服后台][1]获取，例如foo.AIHelp.NET|
| appId     | app唯一标识，从[AIHelp 客服后台][1]获取| 

注：请使用注册邮箱登录 [AIHelp 客服后台][1]。在设置菜单应用页面查看。初次使用，需登录[AIHelp 官网注册][2]自助注册。


**初始化代码示例：**


注意： 一定要在应用启动时进行初始化init操作，不然可能无法进入AIHelp智能客服系统。

可以采用设置初始化回调函数来确认初始化是否成功，只有初始化成功之后才调用其他接口

```
// 在调用初始化init方法之前，设置初始化回调函数
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
          // 设置初始化回调函数
          setInitCallback();
          // 初始化AIHelpSDK
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

## 使用AIHelp智能客服接口


### 1. 接口说明

| 接口名 | 作用 |备注|
|:------------- |:---------------|:---------------|
| **[showElva](#showElva)** | 启动机器人客服聊天界面 |
| **[showConversation](#showConversation)** |启动人工客服聊天界面 |
| **[showElvaOP](#showElvaOP)** | 启动运营界面 | 需在智能客服后台配置运营模块 |
| **[showFAQs](#showFAQs)** | 展示全部FAQ菜单 |
| **[showFAQSection](#showFAQSection)** | 展示某一分类里的所有FAQ |
| **[showSingleFAQ](#showSingleFAQ)** | 展示单条FAQ |
| **[setName](#setName)** | 设置在AIHelp智能客服系统中所展示的游戏名称 | 初始化之后调用，且只需调用一次，不调用此接口则默认显示包名 |
| **[setUserName](#UserName)** | 设置玩家(用户)名称 | 初始化之后调用，且只需调用一次 |
| **[setUserId](#UserId)** | 设置玩家(用户)的唯一ID | 初始化之后调用，且只需调用一次 |
| **[setServerId](#ServerId)** | 设置玩家(用户)所在的服务器ID |
| **[setSDKLanguage](#setSDKLanguage)** | 设置SDK的语言 |


#### 注：您并不需要调用以上所有接口，尤其当您的游戏/应用只设置一个客服入口时，有的接口所展示的界面包含了其他接口，详情见下：
---
### <h4 id="showElva">2.调用`showElva`接口，启动机器人客服聊天界面</h4> 


	ELvaChatServiceSdk.showElva(
				String playerName,
				String playerUid,
				String serverId,
				String showConversationFlag);

或

	ELvaChatServiceSdk.showElva(
				String playerName,
				String playerUid,
				String serverId,
				String showConversationFlag,
				HashMap<String,Object> customData);

**代码示例：**

	HashMap<String,Object> map = new HashMap();
	ArrayList<String> tags = new ArrayList();
	// the tag names are variables
	tags.add("pay1");
	tags.add("s1");
	tags.add("vip2");
	
	// "elva-tags" is the key name, not a variable. 
	map.put("elva-tags",tags); 
	
	HashMap<String,Object> config = new HashMap();
	
	// "elva-custom-metadata" is the key name, not a variable. 
	config.put("elva-custom-metadata",map);
	
	ELvaChatServiceSdk.showElva(
				"user_name",
				"user_id",
				"server_id",
				"1",
				config);

**参数说明：**

| 参数名 | 类型 | 说明 |
|:------------- |:---------------|:---------------|
| playerName | String | 用户在游戏/应用中的名称 |
| playerUid | String | 用户在游戏/应用里的唯一标识 |
| serverId | String | 用户所在的服务器编号 |
| showConversationFlag | String | 参数的值是"0"或"1"，标识是否开启人工入口.为"1"时，将在机器人客服聊天界面右上角，提供人工客服聊天的入口。如下图。 | 
| customData | HashMap\<String,Object> | 可选参数，自定义HashMap信息。可以在此处设置特定的Tag信息。说明：elva-tags对应的值为ArrayList类型，此处传入自定义的Tag，需要在[AIHelp 客服后台][1]配置同名称的Tag才能生效。 |

机器人客服界面示例图:<br>
![机器人客服界面][showElva-CN-Android]
	
**最佳实践：**

> 1. 在您应用的客服主入口触发这个接口的调用。
在[AIHelp 客服后台][1]配置个性化的机器人欢迎语，以及更多机器人对话故事线，引导用户反馈并得到回答。
> 2. 打开人工客服入口，用户可以在机器人客服界面右上角进入人工客服进行聊天，您也可以设置条件只让一部分用户看到这个入口。

---

### <h4 id="showConversation">3. 调用`showConversation`方法，启动人工客服界面</h4>

	ELvaChatServiceSdk.showConversation(
					String uid,
					String serverId);

或

	ELvaChatServiceSdk.showConversation(
						String uid,
						String serverId,
						HashMap customData);
						
**代码示例：**

	HashMap<String,Object> map = new HashMap();
	ArrayList<String> tags = new ArrayList();
	// the tag names are variables
	tags.add("pay1");
	tags.add("s1");
	tags.add("vip2");
	
	// "elva-tags" is the key name, not a variable. 
	map.put("elva-tags",tags); 
	
	HashMap<String,Object> config = new HashMap();
	
	// "elva-custom-metadata" is the key name, not a variable. 
	config.put("elva-custom-metadata",map);
	
	ELvaChatServiceSdk.showConversation(
				"user_id",
				"server_id",
				config);

**参数说明：**

| 参数名 | 类型 | 说明 |
|:------------- |:---------------|:---------------|
| uid | String | 用户在游戏/应用里的唯一标识 |
| serverId | String | 用户所在的服务器编号 |
| customData | HashMap | 可选参数，自定义HashMap信息。可以在此处设置特定的Tag信息。说明：elva-tags对应的值为ArrayList类型，此处传入自定义的Tag，需要在[AIHelp 客服后台][1]配置同名称的Tag才能生效。 |

人工客服界面示例图:<br>
![人工客服界面][showConversation-CN-Android]

**最佳实践：**
> 1. 通常您不需要调用这个接口，除非您想在应用里设置触发点，让用户有机会直接进入人工客服聊天界面。

---

### <h4 id="showElvaOP">4. 调用`showElvaOP`方法，启动运营模块界面</h4>

	ELvaChatServiceSdk.showElvaOP(
				String playerName,
				String playerUid,
				String serverId,
				String showConversationFlag);

或

	ELvaChatServiceSdk.showElvaOP(
				String playerName,
				String playerUid,
				String serverId,
				String showConversationFlag,
				HashMap<String,Object> config);

或

	ELvaChatServiceSdk.showElvaOP(
				String playerName,
				String playerUid,
				String serverId,
				String showConversationFlag,
				HashMap<String,Object> config,
				int defaultTabIndex);


**代码示例：**

	HashMap<String,Object> map = new HashMap();
	ArrayList<String> tags = new ArrayList();
	// the tag names are variables
	tags.add("pay1");
	tags.add("s1");
	tags.add("vip2");
	
	// "elva-tags" is the key name, not a variable. 
	map.put("elva-tags",tags); 
	
	HashMap<String,Object> config = new HashMap();
	
	// "elva-custom-metadata" is the key name, not a variable. 
	config.put("elva-custom-metadata",map);
	
	ELvaChatServiceSdk.showElvaOP(
				"user_name",
				"user_id",
				"server_id",
				"1",
				config);

**参数说明：**

| 参数名 | 类型 | 说明 |
|:------------- |:---------------|:---------------|
| playerName | String | 用户在游戏/应用中的名称 |
| playerUid | String | 用户在游戏/应用里的唯一标识 |
| serverId | String | 用户所在的服务器编号 |
| showConversationFlag | String | 参数的值是"0"或"1"，标识是否开启人工入口.为"1"时，将在机器人客服聊天界面右上角，提供人工客服聊天的入口。如下图。 | 
| customData | HashMap\<String,Object> | 可选参数，自定义HashMap信息。可以在此处设置特定的Tag信息。说明：elva-tags对应的值为ArrayList类型，此处传入自定义的Tag，需要在[AIHelp 客服后台][1]配置同名称的Tag才能生效。 |
| defaultTabIndex | int | 可选参数，进入运营界面时默认展示的tab页的编号。默认值为0，默认为第一个tab页，若需默认展示客服界面tab页，设置值为999。| 

运营模块界面示例图:<br>
![运营模块界面][showElvaOP-CN-Android]

**最佳实践：**
> 1. 在您应用的运营入口触发这个接口的调用。
在[AIHelp 客服后台][1]配置运营分页（tab)并且发布跟应用相关的运营公告内容。就可以通过AIHelp智能客服系统的运营模块界面展示这些内容给用户。运营界面的最后一个tab分页总是机器人客服聊天界面。
> 2. 在tab页面，用户可以在页面右上角进入FAQ页面查看；在机器人客服页面（Help页面），用户可以在页面右上角进入人工客服界面，此人工客服入口可以通过showConversationFlag参数设置是否开启，根据条件打开或关闭，您可以只让一部分用户看到这个入口。

---

### <h4 id="showFAQs">5. 展示FAQ列表，调用`showFAQs `方法(必须确保设置玩家名称信息 [setUserName](#UserName) 和设置玩家唯一id信息 [setUserId](#UserId) 已经调用)</h4>

	ELvaChatServiceSdk.showFAQs();

或

	ELvaChatServiceSdk.showFAQs(HashMap config)

**代码示例：**

	HashMap<String,Object> map = new HashMap();
	ArrayList<String> tags = new ArrayList();
	// the tag names are variables
	tags.add("pay1");
	tags.add("s1");
	tags.add("vip2");
	
	// "elva-tags" is the key name, not a variable. 
	map.put("elva-tags",tags); 
	
	HashMap<String,Object> config = new HashMap();
	
	// "elva-custom-metadata" is the key name, not a variable. 
	config.put("elva-custom-metadata",map);
	
	ELvaChatServiceSdk.showFAQs(config);

**参数说明：**

| 参数名 | 类型 | 说明 |
|:------------- |:---------------|:---------------|
| config | HashMap | 可选参数，自定义HashMap信息。可以在此处设置特定的Tag信息。说明：elva-tags对应的值为ArrayList类型，此处传入自定义的Tag，需要在[AIHelp 客服后台][1]配置同名称的Tag才能生效。 |
	
FAQ界面示例图:<br>
![FAQ界面][showFAQs-CN-Android]

**最佳实践：**
> 1. 在您应用的FAQ主入口触发这个接口的调用。在[AIHelp 客服后台][1]页面配置并分类FAQ，如果您的FAQ较多，可以增加一个父级分类。

---

### <h4 id="showFAQSection">6. 展示某一分类里的所有FAQ，调用`showFAQSection`方法(必须确保设置玩家名称信息 [setUserName](#UserName) 和设置玩家唯一id信息 [setUserId](#UserId) 已经调用)</h4>

	ELvaChatServiceSdk.showFAQSection(String sectionPublishId); 

或

	ELvaChatServiceSdk.showFAQSection(String sectionPublishId,HashMap customData);

**代码示例：**

	HashMap<String,Object> map = new HashMap();
	ArrayList<String> tags = new ArrayList();
	// the tag names are variables
	tags.add("pay1");
	tags.add("s1");
	tags.add("vip2");
	
	// "elva-tags" is the key name, not a variable. 
	map.put("elva-tags",tags); 
	
	HashMap<String,Object> config = new HashMap();
	
	// "elva-custom-metadata" is the key name, not a variable. 
	config.put("elva-custom-metadata",map);
	
	ELvaChatServiceSdk.showFAQSection("1234",config);

**参数说明：**

| 参数名 | 类型 | 说明 |
|:------------- |:---------------|:---------------|
| sectionPublishId | String | FAQ分类的分类编号。打开[AIHelp 客服后台][1]，在**机器人→常见问题→[分类]**页面下找到指定FAQ类别的分类编号。注意：此sectionPublishId不能填写客服后台未存在的分类编号。 |
| config | HashMap | 可选参数，自定义HashMap信息。可以在此处设置特定的Tag信息。说明：elva-tags对应的值为ArrayList类型，此处传入自定义的Tag，需要在[AIHelp 客服后台][1]配置同名称的Tag才能生效。 |
	
分类下所有FAQ界面示例图:<br>
![分类下所有FAQ界面][showFAQSection-CN-Android]

**最佳实践：**
> 1. 在您应用的FAQ分入口，触发这个接口的调用。例如您在[AIHelp 客服后台][1]页面配置了有关商城或充值的分类FAQ，在您的商城界面或充值界面调用这个接口后，这一分类的所有FAQ将被展示。

---

### <h4 id="showSingleFAQ">7. 展示单条FAQ，调用`showSingleFAQ`方法(必须确保设置玩家名称信息 [setUserName](#UserName) 和设置玩家唯一id信息 [setUserId](#UserId) 已经调用)</h4>

	ELvaChatServiceSdk.showSingleFAQ(String faqId);

或

	ELvaChatServiceSdk.showSingleFAQ(String faqId,HashMap customData);

**代码示例：**

	HashMap<String,Object> map = new HashMap();
	ArrayList<String> tags = new ArrayList();
	// the tag names are variables
	tags.add("pay1");
	tags.add("s1");
	tags.add("vip2");
	
	// "elva-tags" is the key name, not a variable. 
	map.put("elva-tags",tags); 
	
	HashMap<String,Object> config = new HashMap();
	
	// "elva-custom-metadata" is the key name, not a variable. 
	config.put("elva-custom-metadata",map);
	
	ELvaChatServiceSdk.showSingleFAQ("2345",config);
        

**参数说明：**

| 参数名 | 类型 | 说明 |
|:------------- |:---------------|:---------------|
| faqId | String | FAQ的编号，打开[AIHelp 客服后台][1]，在**机器人→常见问题**页面下找到指定FAQ的FAQ编号，注意：此faqId不能填写客服后台未存在的FAQ编号。 |
| config | HashMap | 可选参数，自定义HashMap信息。可以在此处设置特定的Tag信息。说明：elva-tags对应的值为ArrayList类型，此处传入自定义的Tag，需要在[AIHelp 客服后台][1]配置同名称的Tag才能生效。 |

单条FAQ界面示例图:<br>
![单条FAQ界面][showSingleFAQ-CN-Android]

**最佳实践：**
> 1. 在您应用的特定功能入口触发这个接口的调用，可以方便用户了解具体功能相关的FAQ。

---

### <h4 id="setName">8. 设置游戏名称信息，调用`setName`方法(初始化AIHelp智能客服之后调用，且只需调用一次，不调用此接口则默认显示包名)</h4>

	ELvaChatServiceSdk.setName(String game_name);

**代码示例：**

	ELvaChatServiceSdk.setName("Your Game");

**参数说明：**

| 参数名 | 类型 | 说明 |
|:------------- |:---------------|:---------------|
| game_name | String | 您想在AIHelp智能客服系统中展示的您的游戏名称 |

**最佳实践：**
> 1. 在初始化后调用该接口设置游戏名称，将在AIHelp智能客服系统相关界面的标题栏显示您设置的游戏名称。

---

### <h4 id="UserId">9. 设置玩家(用户)的唯一ID，调用`setUserId`方法(初始化AIHelp智能客服之后调用，且只需调用一次)</h4>

	ELvaChatServiceSdk.setUserId(String uid);

**代码示例：**

	ELvaChatServiceSdk.setUserId("123ABC567DEF");

**参数说明：**

| 参数名 | 类型 | 说明 |
|:------------- |:---------------|:---------------|
| uid | String | 玩家(用户)的唯一ID |

**最佳实践：**
> 1. 在初始化后调用该接口设置玩家(用户)的唯一ID。

---

### <h4 id="UserName">10. 设置玩家(用户)的昵称，调用`setUserName`方法(初始化AIHelp智能客服之后调用，且只需调用一次)</h4>

	ELvaChatServiceSdk.setUserName (String userName);

**代码示例：**

	ELvaChatServiceSdk.setUserName ("player_name");

**参数说明：**

| 参数名 | 类型 | 说明 |
|:------------- |:---------------|:---------------|
| userName | String | 玩家(用户)的名称 |

**最佳实践：**
> 1. 在初始化后调用该接口设置玩家(用户)的昵称，这样在后台客户服务页面会展示用户的应用内名称，便于客服在服务用户时个性化称呼对方。

### <h4 id="ServerId">11. 设置玩家(用户)所在的服务器ID，调用`setServerId`方法</h4>

	ELvaChatServiceSdk.setServerId(String serverId);

**代码示例：**

	ELvaChatServiceSdk.setServerId("server_id");

**参数说明：**

| 参数名 | 类型 | 说明 |
|:------------- |:---------------|:---------------|
| serverId | String | 玩家(用户)所在服务器的ID |

---

### <h4 id="setSDKLanguage">12. 设置AIHelp智能客服系统的语言，调用`setSDKLanguage`方法</h4>

	ELvaChatServiceSdk.setSDKLanguage(String language);
	
**代码示例：**

	String languageAlias = "zh_CN";
	ELvaChatServiceSdk.setSDKLanguage(languageAlias);

**参数说明：**

| 参数名 | 类型 | 说明 |
|:------------- |:---------------|:---------------|
| language | String | 语言简称。如英语为en，简体中文为zh_CN。更多语言简称参见[AIHelp 客服后台][1]，**设置→语言→Alias**一列。部分语言简称可参见下图:|

部分语言简称图:<br>
![部分语言简称][language]

**最佳实践：**
> 1. 通常AIHelp智能客服系统的语言会使用手机的系统语言设置，如果您的应用使用跟手机设置不一样的语言，那么您需要在AIHelp智能客服系统初始化之后调用此接口修改默认语言。
> 2. 如果您的应用允许用户更改语言，那么每次更改语言之后，也需要调用此接口重新设置AIHelp智能客服系统的语言。

### 13. 设置机器人客服界面的另一个欢迎语。

如果您设置了进入机器人客服界面的不同入口，希望用户从不同的入口进入机器人客服界面时显示不同的欢迎语，进入不同故事线，可以通过设置config参数来实现： 

	// "anotherWelcomeText" is the key name, it's value is the usersay variable
	map.put("anotherWelcomeText","usersay");

	
**代码示例：**

	HashMap<String,Object> map = new HashMap();
	ArrayList<String> tags = new ArrayList();
	// the tag names are variables
	tags.add("pay1");
	tags.add("s1");
	tags.add("vip2");
	
	// "elva-tags" is the key name, not a variable. 
	map.put("elva-tags",tags); 
	
	// "anotherWelcomeText" is the key name, it's value is the usersay variable
	map.put("anotherWelcomeText","usersay");
	
	HashMap<String,Object> config = new HashMap();
	
	// "elva-custom-metadata" is the key name, not a variable. 
	config.put("elva-custom-metadata",map);
	
	// 调用showElva接口进入机器人客服界面
	ELvaChatServiceSdk.showElva(
				"user_name",
				"user_id",
				"server_id",
				"1",
				config);
或

	// 调用showElvaOP接口进入运营模块界面
	ELvaChatServiceSdk.showElvaOP(
				"user_name",
				"user_id",
				"server_id",
				"1",
				config);


**参数说明：**

`("anotherWelcomeText","usersay")`是一对键值对，key是:"anotherWelcomeText"，不可变，value是对应故事线的**User Say**属性，在[AIHelp 客服后台][1]**机器人→发布→故事线→查看**中点击查看某一条故事线，故事线页面中每一个标签（Story）都有一个**User Say**的属性。如果您想在机器人界面显示其他故事线，键值对的value的值就应该是对应的**User Say**属性的值。

**最佳实践：**
> 1. 引导玩家从不同入口看到不同的故事线欢迎语以提供不同的服务。

[1]:https://AIHelp.net/elva "AIHelp 客服后台"
[2]:https://AIHelp.net/register "AIHelp 官网注册"
[showElva-CN-Android]:https://github.com/AI-HELP/Docs-Screenshots/blob/master/showElva-CN-Android.png "机器人客服界面"
[showConversation-CN-Android]:https://github.com/AI-HELP/Docs-Screenshots/blob/master/showConversation-CN-Android.png "人工客服界面"
[showElvaOP-CN-Android]:https://github.com/AI-HELP/Docs-Screenshots/blob/master/showElvaOP-CN-Android.png "运营模块界面"
[showFAQs-CN-Android]:https://github.com/AI-HELP/Docs-Screenshots/blob/master/showFAQs-CN-Android.png "FAQ界面"
[showFAQSection-CN-Android]:https://github.com/AI-HELP/Docs-Screenshots/blob/master/showFAQSection-CN-Android.png "分类下所有FAQ界面"
[showSingleFAQ-CN-Android]:https://github.com/AI-HELP/Docs-Screenshots/blob/master/showSingleFAQ-CN-Android.png "单条FAQ界面"
[language]:https://github.com/AI-HELP/Docs-Screenshots/blob/master/Language-alias.png "部分语言简称"