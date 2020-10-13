## 前言
## 接入说明
**有两种方式可以接入AIHelp智能客服系统的Android版SDK。如果您使用了Android Studio等可以支持Gradle的环境，推荐使用第一种方式导入：**

### Android Studio 导入, 方式一：通过gradle自动导入 AIHelp Android SDK：

#### 1. 在 Project 级别的 build.gradle 中加入:

	allprojects {
		repositories {
			jcenter()
		}
	}

#### 2. 在使用 AIHelp SDK 的 app 或 module 级别的 build.gradle 中加入依赖：

	dependencies {
	 ...
	    implementation 'net.aihelp:elva:1.7.4.2'
	 ...
	}
	
	注:
	SDK 需要使用 "android-support-v4.jar"，鉴于这个 jar 文件版本太多，如果您的项目本来就有的话，可能会对接入造成困扰，所以 aar 中没有带入该 jar 文件。
	如果您的项目里没有 "android-support-v4.jar"，请复制该jar（aihelpsdk/libs/android-support-v4.jar）到您项目对应的libs文件下。
	如果您的项目里已经存在 "android-support-v4.jar" 则无需再做额外操作。
	如果您用到了 appcompat-v7，因为 appcompat-v7 其实包含了 android-support-v4，所以也无需再做额外操作。

请确保 build.gradle 同步成功: 在 Android Studio 的 External Libraries 下面能够看到 "Gradle: net.aihelp:elva:1.7.4.2@aar" 相关内容。
如果无法自动加载，请采用第二种导入方式：

### Android Studio 导入, 方式二： 下载 AIHelp Android SDK 进行本地接入：

#### 1. 手动下载最新的 SDK 版本 (aar 文件在 aihelpsdk 目录中，没有其他需求的话请选择最新版本接入)

#### 2. 把下载的 aar 文件放入项目的 libs 目录中，没有 libs 目录的话，请在 src 同级目录新建一个 libs 目录

#### 3. 打开使用 AIHelp SDK 的 app 或 module 级别的 build.gradle 修改 dependencies 节点，如下:
    将 
        implementation fileTree(include: ['*.jar'], dir: 'libs')
    修改为（没有上面这行就直接追加下面这行即可）
        implementation fileTree(include: ['*.jar','*.aar'], dir: 'libs')
    
    注:
    SDK 需要使用 "android-support-v4.jar"，鉴于这个 jar 文件版本太多，如果您的项目本来就有的话，可能会对接入造成困扰，所以 aar 中没有带入该 jar 文件。
    如果您的项目里没有 "android-support-v4.jar"，请复制该jar（aihelpsdk/libs/android-support-v4.jar）到您项目对应的libs文件下。
    如果您的项目里已经存在 "android-support-v4.jar" 则无需再做额外操作。
    如果您用到了 appcompat-v7，因为 appcompat-v7 其实包含了 android-support-v4，所以也无需再做额外操作。

### eclipse 导入方式： 下载 AIHelp Android SDK 进行本地接入：

#### 1. 手动下载最新的 SDK 版本 (aar 文件在 aihelpsdk 目录中，没有其他需求的话请选择最新版本 aar )

#### 2. 把下载的 aar 文件修改后缀为 zip，然后新建一个文件夹，把该zip的内容全部提取出来放入文件夹中
    由于 aar 本身是为 Android Studio 准备的，所以，eclipse需要手动处理一下：
        1，删掉 R.txt
        2，把 classes.jar 放入 libs 目录中
        3，如果您的项目里没有 "android-support-v4.jar"，请复制该jar（aihelpsdk/libs/android-support-v4.jar）到 libs 文件下。
           如果您的项目里已经存在 "android-support-v4.jar" 则无需再做额外操作。
           如果您用到了 appcompat-v7，因为 appcompat-v7 其实包含了 android-support-v4，所以也无需再做额外操作。

#### 3. 把第2步解压得到的目录，作为 Android Library 导入到 eclipse 中
    1,导入操作
        File -> Import -> Android -> Existing Android Code Into Workspace
    2,导入进来之后设定 Build Target 为 Android 6 以上（API Level >= 23）
    3,修改项目属性为Library（属性页勾选 Is Library 即可） 
    4,为您的项目添加对本SDK的依赖关系

### 接入工程配置 
### (如果您的 apk 最终会经过代码混淆，请在 proguard 配置文件中加入以下代码：)
    -keep class com.lioy.** {*;}
    -keep class bitoflife.** {*;}
    -keep class org.fusesource.** {*;}
    -keep class com.nostra13.** {*;}
    -keep class com.facebook.** {*;}

在您的Android工程的AndroidManifest.xml，增加需要的配置：  

**1. 打包版本要求**

AIHelp SDK 要求android sdk最低版本 >= 14，目标版本 >= 23：

	<uses-sdk android:minSdkVersion="14" android:targetSdkVersion="28"/>

**2. 增加需要的权限**

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <!-- 上传表单图片的时候需要此权限 -->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <!-- 上传表单图片的时候需要此权限 -->
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    
    <!--需要的刘海屏适配 -->
    <!--google刘海适配-->
    <meta-data
        android:name="android.max_aspect"
        android:value="2.1" />
    <!--小米手机开启刘海适配-->
    <meta-data
        android:name="notch.config"
        android:value="portrait|landscape" />
        
    <!--
    备注：关于 WRITE_EXTERNAL_STORAGE 和 READ_EXTERNAL_STORAGE 权限
        1.Android 6.0以下系统，在安装本版本游戏时，会提示授予读取和写入权限
        2.Android 6.0以上系统，目前只有在玩家客诉上传图片时才会提示授予读权限设置
    -->

**3. 增加activity:**
    

	<!--需要的Activity-->
	<activity
		android:name="com.ljoy.chatbot.ChatMainActivity"
		android:configChanges="keyboardHidden|orientation|screenSize"
		android:screenOrientation="portrait"
		android:theme="@style/Theme.AppCompat.Light.NoActionBar"
		android:windowSoftInputMode="adjustResize|stateHidden" />
	<activity
		android:name="com.ljoy.chatbot.OPActivity"
		android:configChanges="keyboardHidden|orientation|screenSize"
		android:screenOrientation="portrait"
		android:theme="@style/Theme.AppCompat.Light.NoActionBar"
		android:windowSoftInputMode="adjustResize|stateHidden" />
	<activity
		android:name="com.ljoy.chatbot.FAQActivity"
		android:configChanges="keyboardHidden|orientation|screenSize"
		android:screenOrientation="portrait"
		android:theme="@style/Theme.AppCompat.Light.NoActionBar"
		android:windowSoftInputMode="adjustResize|stateHidden" />
	<activity
		android:name="com.ljoy.chatbot.WebViewActivity"
		android:configChanges="keyboardHidden|orientation|screenSize"
		android:screenOrientation="portrait"
		android:theme="@style/Theme.AppCompat.Light.NoActionBar"
		android:windowSoftInputMode="adjustResize|stateHidden" />
	<activity
		android:name="com.ljoy.chatbot.QAWebActivity"
		android:configChanges="keyboardHidden|orientation|screenSize"
		android:screenOrientation="portrait"
		android:theme="@style/Theme.AppCompat.Light.NoActionBar"
		android:windowSoftInputMode="adjustResize|stateHidden" />
	<!--需要的Activity -->

**4. 其他**

如果您的应用基于 API29 构建，那么为了可以正常访问外部存储，您需要在主工程的 application 节点加入如下配置：

```xml
<application
    android:requestLegacyExternalStorage="true"
    android:usesCleartextTraffic="true">
</application>
```



关于横竖屏显示：上述配置中
**android:screenOrientation="portrait"**
代表AIHelp的UI会根据手机屏幕方向自动调整横竖屏。如果您要固定AIHelp屏幕展示，请做如下修改：

    例如:
    <activity
        android:name="com.ljoy.chatbot.ChatMainActivity"
        android:configChanges="keyboardHidden|orientation|screenSize"
        android:windowSoftInputMode="adjustResize|stateHidden" 
        android:screenOrientation="portrait"/>
    
    竖屏展示：
    android:screenOrientation="portrait"(推荐使用:竖屏显示效果比较好)
    
    横屏展示：
    android:screenOrientation="landscape"
    	
    手机物理传感器展示：
    android:screenOrientation="sensor"


### 4.SDK初始化（必须在应用启动阶段调用）

**甲方有义务按照乙方接入文档说明的正常接入方式和调用方式使用乙方服务，如甲方通过技术手段影响乙方计费，乙方有权在通知甲方的同时立即单方面终止服务，并要求甲方承担责任。<br />
在您的应用启动的时候 就调用 ELvaChatServiceSdk.init(...)，传入必要的参数。**
	
```java
	ELvaChatServiceSdk.init(
				Activity activity,
				String appKey,
				String domain,
				String appId
				);
```


* 参数说明：

| 参数 | 说明 |
|:------------- |:---------------|
| activity    | 应用的Activty|
| appKey    | app唯一密钥，从[AIHelp 客服后台][1]获取|
| domain     | 您的AIHelp域名，从[AIHelp 客服后台][1]获取，例如foo.AIHelp.NET|
| appId     | app唯一标识，从[AIHelp 客服后台][1]获取|

注：请使用注册邮箱登录 [AIHelp 客服后台][1]。在设置菜单应用页面查看。初次使用，需登录[AIHelp 官网注册][2]自助注册。


**初始化代码示例：（必须在应用启动阶段调用）** <br />
**甲方有义务按照乙方接入文档说明的正常接入方式和调用方式使用乙方服务，
如甲方通过技术手段影响乙方计费，乙方有权在通知甲方的同时立即单方面终止服务，并要求甲方承担责任。**

```java
import com.ljoy.chatbot.sdk.ELvaChatServiceSdk;

public class MyActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
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


### 5. 使用AIHelp智能客服接口


### 1. 接口说明

| 接口名 | 作用 |备注|
|:------------- |:---------------|:---------------|
|建议调用的接口:|
| **[setName](#setName)** | 设置在AIHelp智能客服系统中所展示的游戏名称 | 初始化之后调用，且只需调用一次，不调用此接口则默认显示包名 |
| **[setUserName](#UserName)** | 设置(用户)名称 | 初始化之后调用，且只需调用一次  如果拿不到username，就传入空字符串""，会使用默认昵称"anonymous" |
| **[setUserId](#UserId)** | 设置(用户)的唯一ID | 初始化之后调用，且只需调用一次  如果拿不到userid，就传入空字符串""，系统会生成一个唯一设备id  |
| **[setServerId](#ServerId)** | 设置(用户)所在的服务器ID | 如果游戏方拿不到数据 就传空字符串"" |
| **[setSDKLanguage](#setSDKLanguage)** | 设置SDK的语言 | 初始化之后调用，且只需调用一次 注:默认是使用手机系统语言设置，设置后可以调用应用内的设置语言 |
|可选调用的接口:|
| **[showElva](#showElva)** | 启动机器人客服聊天界面 |
| **[showConversation](#showConversation)** |启动人工客服聊天界面 |
| **[showElvaOP](#showElvaOP)** | 启动运营界面 | 需在智能客服后台配置运营模块 |
| **[showFAQs](#showFAQs)** | 展示全部FAQ菜单 |
| **[showFAQSection](#showFAQSection)** | 展示某一分类里的所有FAQ |
| **[showSingleFAQ](#showSingleFAQ)** | 展示单条FAQ |


#### 注：您并不需要调用以上所有接口，尤其当您的游戏/应用只设置一个客服入口时，有的接口所展示的界面包含了其他接口，详情见下：
---
### <h4 id="showElva">2.调用`showElva`接口，启动机器人客服聊天界面</h4> 

```java
	ELvaChatServiceSdk.showElva(
				String playerName,
				String playerUid,
				String serverId,
				String showConversationFlag);
```

或
```java
	ELvaChatServiceSdk.showElva(
				String playerName,
				String playerUid,
				String serverId,
				String showConversationFlag,
				HashMap<String,Object> customData);
```

**代码示例：**
```java
	HashMap<String,Object> map = new HashMap();
	ArrayList<String> tags = new ArrayList();
	// the tag names are variables
	tags.add("pay1");
	tags.add("s1");
	tags.add("vip2");
	
	// "elva-tags" 是key值 不可以变 
	map.put("elva-tags",tags); 
	
	HashMap<String,Object> config = new HashMap();
	
	// "elva-custom-metadata" 是key值 不可以变 
	config.put("elva-custom-metadata",map);
	
	ELvaChatServiceSdk.showElva(
				"user_name",
				"user_id",
				"server_id",
				"1",
				config);
```
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

```java
	ELvaChatServiceSdk.showConversation(
					String uid,
					String serverId);
```
或
```java
	ELvaChatServiceSdk.showConversation(
						String uid,
						String serverId,
						HashMap customData);
```
**代码示例：**
```java
	HashMap<String,Object> map = new HashMap();
	ArrayList<String> tags = new ArrayList();
	// the tag names are variables
	tags.add("pay1");
	tags.add("s1");
	tags.add("vip2");
	
	// "elva-tags" 是key值 不可以变 
	map.put("elva-tags",tags); 
	
	HashMap<String,Object> config = new HashMap();
	
	// "elva-custom-metadata" 是key值 不可以变 
	config.put("elva-custom-metadata",map);
	
	ELvaChatServiceSdk.showConversation(
				"user_id",
				"server_id",
				config);
```
**参数说明：**

| 参数名 | 类型 | 说明 |
|:------------- |:---------------|:---------------|
| user_id | String | 用户在游戏/应用里的唯一标识 |
| serverId | String | 用户所在的服务器编号 |
| customData | HashMap | 可选参数，自定义HashMap信息。可以在此处设置特定的Tag信息。说明：elva-tags对应的值为ArrayList类型，此处传入自定义的Tag，需要在[AIHelp 客服后台][1]配置同名称的Tag才能生效。 |

人工客服界面示例图:<br>
![人工客服界面][showConversation-CN-Android]

**最佳实践：**

> 1. 通常您不需要调用这个接口，除非您想在应用里设置触发点，让用户有机会直接进入人工客服聊天界面。

---

### <h4 id="showElvaOP">4. 调用`showElvaOP`方法，启动运营模块界面</h4>
```java
	ELvaChatServiceSdk.showElvaOP(
				String playerName,
				String playerUid,
				String serverId,
				String showConversationFlag);
```
或
```java
	ELvaChatServiceSdk.showElvaOP(
				String playerName,
				String playerUid,
				String serverId,
				String showConversationFlag,
				HashMap<String,Object> config);
```
或
```java
	ELvaChatServiceSdk.showElvaOP(
				String playerName,
				String playerUid,
				String serverId,
				String showConversationFlag,
				HashMap<String,Object> config,
				int defaultTabIndex);
```

**代码示例：**

```java
	HashMap<String,Object> map = new HashMap();
	ArrayList<String> tags = new ArrayList();
	// the tag names are variables
	tags.add("pay1");
	tags.add("s1");
	tags.add("vip2");
	
	// "elva-tags" 是key值 不可以变 
	map.put("elva-tags",tags); 
	
	HashMap<String,Object> config = new HashMap();
	
	// "elva-custom-metadata" 是key值 不可以变  
	config.put("elva-custom-metadata",map);
	
	ELvaChatServiceSdk.showElvaOP(
				"user_name",
				"user_id",
				"server_id",
				"1",
				config);
```
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

### <h4 id="showFAQs">5. 展示FAQ列表，调用`showFAQs `方法
    (必须确保调用以下两个接口,设置用户名称信息和设置用户唯一id信息)
    [setUserName](#UserName) 如果拿不到username，就传入空字符串""，会使用默认昵称"anonymous"
    [setUserId](#UserId) 如果拿不到userid，就传入空字符串""，系统会生成一个唯一设备id 	
    </h4>
```java
	ELvaChatServiceSdk.showFAQs();
```
或
```java
	ELvaChatServiceSdk.showFAQs(HashMap config)
```
**代码示例：**
```java
	public void ShowFAQs(){
	
		HashMap<String, Object> config = new HashMap();
		HashMap<String, Object> map = new HashMap();
		ArrayList<String> tags = new ArrayList();
		tags.add("vip1");//第一种方式自定义 需要和后台保持一致(针对key形式)	
		
		// "elva-tags" 是key值 不可以变 
		map.put("elva-tags", tags);	
		
		map.put("udid", "123456789");//第二种方式自定义 不需要去后台配置(针对key-value形式)	
		
		// "elva-custom-metadata" 是key值 不可以变 
		config.put("elva-custom-metadata", map);
		
		// 加入此参数,其中key是不可变的 优先级最高 加上后faq右上角则永不显示
		// (如果想显示 需要删除此参数 并加入 config.put("showContactButtonFlag", "1");
		config.put("hideContactButtonFlag", "1");
			
		// 显示可以从FAQ列表右上角进入机器人客服(如果不想显示 需要删除此参数)
		config.put("showContactButtonFlag", "1"); 
		
		// 点击FAQ右上角后 进入机器人界面右上角是否显示 (如果不想显示 需要删除此参数)
		config.put("showConversationFlag", "1"); 
		
		// 点击FAQ右上角后 直接会进入到人工客服页面(不加默认进入机器人界面 如果不需要则删除此参数)
		config.put("directConversation", "1");
		
		// 设置用户名 如果拿不到username，就传入空字符串""，会使用默认昵称"anonymous"
		ELvaChatServiceSdk.setUserName("user_name"); 
		
		// 设置用户ID 如果拿不到userid，就传入空字符串""，系统会生成一个唯一设备id 
		ELvaChatServiceSdk.setUserId("user_id"); 
		
		// 设置服务ID
		ELvaChatServiceSdk.setServerId("server_id"); 

		ELvaChatServiceSdk.showFAQs(config);
	}
```
**参数说明：**

| 参数名 | 类型 | 说明 |
|:------------- |:---------------|:---------------|
| config | HashMap | 可选参数，自定义HashMap信息。可以在此处设置特定的Tag信息。说明：elva-tags对应的值为ArrayList类型，此处传入自定义的Tag，需要在[AIHelp 客服后台][1]配置同名称的Tag才能生效。 |

FAQ界面示例图:<br>
![FAQ界面][showFAQs-CN-Android]

**最佳实践：**

> 1. 在您应用的FAQ主入口触发这个接口的调用。在[AIHelp 客服后台][1]页面配置并分类FAQ，如果您的FAQ较多，可以增加一个父级分类。

---

#### <h4 id="showFAQSection">6. 展示某一分类里的所有FAQ，调用`showFAQSection`方法
	(必须确保调用以下两个接口,设置用户名称信息和设置用户唯一id信息)
	[setUserName](#UserName) 如果拿不到username，就传入空字符串""，会使用默认昵称"anonymous"
	[setUserId](#UserId) 如果拿不到userid，就传入空字符串""，系统会生成一个唯一设备id 	
	</h4>
```java
	ELvaChatServiceSdk.showFAQSection(String sectionPublishId); 
```
或
```java
	ELvaChatServiceSdk.showFAQSection(String sectionPublishId,HashMap customData);
```
**代码示例：**
```java
	HashMap<String, Object> config = new HashMap();
	HashMap<String, Object> map = new HashMap();
	ArrayList<String> tags = new ArrayList();
	tags.add("vip1");//第一种方式自定义 需要和后台保持一致(针对key形式)	
	
	// "elva-tags" 是key值 不可以变 
	map.put("elva-tags", tags);	
	
	map.put("udid", "123456789");//第二种方式自定义 不需要去后台配置(针对key-value形式)	
	
	// "elva-custom-metadata" 是key值 不可以变 
	config.put("elva-custom-metadata", map);
	
	// 加入此参数,其中key是不可变的 优先级最高 加上后faq右上角则永不显示
	// (如果想显示 需要删除此参数 并加入 config.put("showContactButtonFlag", "1");
	config.put("hideContactButtonFlag", "1");
		
	// 显示可以从FAQ列表右上角进入机器人客服(如果不想显示 需要删除此参数)
	config.put("showContactButtonFlag", "1"); 
	
	// 点击FAQ右上角后 进入机器人界面右上角是否显示 (如果不想显示 需要删除此参数)
	config.put("showConversationFlag", "1"); 
	
	// 点击FAQ右上角后 直接会进入到人工客服页面(不加默认进入机器人界面 如果不需要则删除此参数)
	config.put("directConversation", "1");
	
	// 设置用户名 如果拿不到username，就传入空字符串""，会使用默认昵称"anonymous"
	ELvaChatServiceSdk.setUserName("user_name"); 
	
	// 设置用户ID 如果拿不到userid，就传入空字符串""，系统会生成一个唯一设备id 
    ELvaChatServiceSdk.setUserId("user_id"); 
	
	// 设置服务ID
    ELvaChatServiceSdk.setServerId("server_id"); 

	ELvaChatServiceSdk.showFAQSection("1234",config);
```
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

### <h4 id="showSingleFAQ">7. 展示单条FAQ，调用`showSingleFAQ`方法
	(必须确保调用以下两个接口,设置用户名称信息和设置用户唯一id信息)
	[setUserName](#UserName) 如果拿不到username，就传入空字符串""，会使用默认昵称"anonymous"
	[setUserId](#UserId) 如果拿不到userid，就传入空字符串""，系统会生成一个唯一设备id 
	</h4>
```java
	ELvaChatServiceSdk.showSingleFAQ(String faqId);
```
或
```java
	ELvaChatServiceSdk.showSingleFAQ(String faqId,HashMap customData);
```
**代码示例：**
```java
	HashMap<String, Object> config = new HashMap();
	HashMap<String, Object> map = new HashMap();
	ArrayList<String> tags = new ArrayList();
	tags.add("vip1");//第一种方式自定义 需要和后台保持一致(针对key形式)	
	
	// "elva-tags" 是key值 不可以变 
	map.put("elva-tags", tags);	
	
	map.put("udid", "123456789");//第二种方式自定义 不需要去后台配置(针对key-value形式)	
	
	// "elva-custom-metadata" 是key值 不可以变 
	config.put("elva-custom-metadata", map);
	
	// 加入此参数,其中key是不可变的 优先级最高 加上后faq右上角则永不显示
	// (如果想显示 需要删除此参数 并加入 config.put("showContactButtonFlag", "1");
	config.put("hideContactButtonFlag", "1");
		
	// 显示可以从FAQ列表右上角进入机器人客服(如果不想显示 需要删除此参数)
	config.put("showContactButtonFlag", "1"); 
	
	// 点击FAQ右上角后 进入机器人界面右上角是否显示 (如果不想显示 需要删除此参数)
	config.put("showConversationFlag", "1"); 
	
	// 点击FAQ右上角后 直接会进入到人工客服页面(不加默认进入机器人界面 如果不需要则删除此参数)
	config.put("directConversation", "1");
	
	// 设置用户名 如果拿不到username，就传入空字符串""，会使用默认昵称"anonymous"
	ELvaChatServiceSdk.setUserName("user_name"); 
	
	// 设置用户ID 如果拿不到userid，就传入空字符串""，系统会生成一个唯一设备id 
    ELvaChatServiceSdk.setUserId("user_id"); 
	
	// 设置服务ID
    ELvaChatServiceSdk.setServerId("server_id"); 

	ELvaChatServiceSdk.showSingleFAQ("2345",config);
        
```
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
```java
	ELvaChatServiceSdk.setName(String game_name);
```
**代码示例：**
```java
	ELvaChatServiceSdk.setName("Your Game");
```
**参数说明：**

| 参数名 | 类型 | 说明 |
|:------------- |:---------------|:---------------|
| game_name | String | 您想在AIHelp智能客服系统中展示的您的游戏名称 |

**最佳实践：**
> 1. 在初始化后调用该接口设置游戏名称，将在AIHelp智能客服系统相关界面的标题栏显示您设置的游戏名称。

---

### <h4 id="UserId">9. 设置(用户)的唯一ID，调用`setUserId`方法(初始化AIHelp智能客服之后调用，且只需调用一次)</h4>
```java
	ELvaChatServiceSdk.setUserId(String uid); //如果拿不到userid，就传入空字符串""，系统会生成一个唯一设备id 
```

**代码示例：**
```java
	ELvaChatServiceSdk.setUserId("123ABC567DEF"); //如果拿不到userid，就传入空字符串""，系统会生成一个唯一设备id 
```
**参数说明：**

| 参数名 | 类型 | 说明 |
|:------------- |:---------------|:---------------|
| uid | String | (用户)的唯一ID |

**最佳实践：**

> 1. 在初始化后调用该接口设置(用户)的唯一ID。

---

### <h4 id="UserName">10. 设置(用户)的昵称，调用`setUserName`方法(初始化AIHelp智能客服之后调用，且只需调用一次)</h4>
```java
	ELvaChatServiceSdk.setUserName (String userName); //如果拿不到username，就传入空字符串""，会使用默认昵称"anonymous"
```
**代码示例：**
```java
	ELvaChatServiceSdk.setUserName ("player_name"); //如果拿不到username，就传入空字符串""，会使用默认昵称"anonymous"
```
**参数说明：**

| 参数名 | 类型 | 说明 |
|:------------- |:---------------|:---------------|
| userName | String | (用户)的名称 |

**最佳实践：**

> 1. 在初始化后调用该接口设置(用户)的昵称，这样在后台客户服务页面会展示用户的应用内名称，便于客服在服务用户时个性化称呼对方。

### <h4 id="ServerId">11. 设置(用户)所在的服务器ID，调用`setServerId`方法</h4>
```java
	ELvaChatServiceSdk.setServerId(String serverId);
```
**代码示例：**
```java
	ELvaChatServiceSdk.setServerId("server_id");
```
**参数说明：**

| 参数名 | 类型 | 说明 |
|:------------- |:---------------|:---------------|
| serverId | String | (用户)所在服务器的ID |

---

### <h4 id="setSDKLanguage">12. 设置AIHelp智能客服系统的语言，调用`setSDKLanguage`方法</h4>
```java
	ELvaChatServiceSdk.setSDKLanguage(String language);
```
**代码示例：**
```java
	String languageAlias = "zh_CN";
	ELvaChatServiceSdk.setSDKLanguage(languageAlias);
```
**参数说明：**

| 参数名 | 类型 | 说明 |
|:------------- |:---------------|:---------------|
| language | String | 语言简称。如英语为en，简体中文为zh_CN。更多语言简称参见[AIHelp 客服后台][1]，**设置→语言→Alias**一列。部分语言简称可参见下图:|

部分语言简称图:<br>
![部分语言简称][language]

**最佳实践：**

> 1. 通常AIHelp智能客服系统的语言会使用手机的系统语言设置，如果您的应用使用跟手机设置不一样的语言，那么您需要在AIHelp智能客服系统初始化之后调用此接口修改默认语言。
> 2. 如果您的应用允许用户更改语言，那么每次更改语言之后，也需要调用此接口重新设置AIHelp智能客服系统的语言。

#### 12. 设置机器人客服界面的另一个欢迎语。

如果您设置了进入机器人客服界面的不同入口，希望用户从不同的入口进入机器人客服界面时显示不同的欢迎语，进入不同故事线，可以通过设置config参数来实现： 
```java
	// "anotherWelcomeText" is the key name, it's value is the usersay variable
	map.put("anotherWelcomeText","usersay");
```

**代码示例：**
```java
	HashMap<String,Object> map = new HashMap();
	ArrayList<String> tags = new ArrayList();
	// the tag names are variables
	tags.add("pay1");
	tags.add("s1");
	tags.add("vip2");
	
	// "elva-tags" 是key值 不可以变 
	map.put("elva-tags",tags); 
	
	// "anotherWelcomeText" is the key name, it's value is the usersay variable
	map.put("anotherWelcomeText","usersay");
	
	HashMap<String,Object> config = new HashMap();
	
	// "elva-custom-metadata" 是key值 不可以变 
	config.put("elva-custom-metadata",map);
	
	// 调用showElva接口进入机器人客服界面
	ELvaChatServiceSdk.showElva(
				"user_name",
				"user_id",
				"server_id",
				"1",
				config);
```
或
```java
	// 调用showElvaOP接口进入运营模块界面
	ELvaChatServiceSdk.showElvaOP(
				"user_name",
				"user_id",
				"server_id",
				"1",
				config);
```

**参数说明：**

`("anotherWelcomeText","usersay")`是一对键值对，key是:"anotherWelcomeText"，不可变，value是对应故事线的**User Say**属性，在[AIHelp 客服后台][1]**机器人→发布→故事线→查看**中点击查看某一条故事线，故事线页面中每一个标签（Story）都有一个**User Say**的属性。如果您想在机器人界面显示其他故事线，键值对的value的值就应该是对应的**User Say**属性的值。



#### 13.想定制人工客服的欢迎语
### 如果您想定制人工客服的欢迎语,您需要在调用对应接口的config参数里传入一对新的key,value
key是:"private_welcome_str",value为您想要的定制的内容

### 代码示例:
```java
	HashMap<String,Object> map = new HashMap();
  
	//"private_welcome_str" 是key,value是您想要的定制的内容,类型是String
	map.put("private_welcome_str","usersay");
	HashMap<String,Object> config = new HashMap();
  
	//"elva-custom-metadata" 是key值 不可以变
	config.put("elva-custom-metadata",map);
  
	//调用showElva接口进入机器人客服界面
	ElvaChatServiceSdk.showElva(
				"user_name",
				"user_id",
				"server_id",
				"1",
				config);
```

**最佳实践：**

> 1. 引导用户从不同入口看到不同的故事线欢迎语以提供不同的服务。

[1]:https://AIHelp.net/elva "AIHelp 客服后台"
[2]:https://AIHelp.net/register "AIHelp 官网注册"
[showElva-CN-Android]:https://github.com/AI-HELP/Docs-Screenshots/blob/master/showElva-CN-Android.png "机器人客服界面"
[showConversation-CN-Android]:https://github.com/AI-HELP/Docs-Screenshots/blob/master/showConversation-CN-Android.png "人工客服界面"
[showElvaOP-CN-Android]:https://github.com/AI-HELP/Docs-Screenshots/blob/master/showElvaOP-CN-Android.png "运营模块界面"
[showFAQs-CN-Android]:https://github.com/AI-HELP/Docs-Screenshots/blob/master/showFAQs-CN-Android.png "FAQ界面"
[showFAQSection-CN-Android]:https://github.com/AI-HELP/Docs-Screenshots/blob/master/showFAQSection-CN-Android.png "分类下所有FAQ界面"
[showSingleFAQ-CN-Android]:https://github.com/AI-HELP/Docs-Screenshots/blob/master/showSingleFAQ-CN-Android.png "单条FAQ界面"
[language]:https://github.com/AI-HELP/Docs-Screenshots/blob/master/Language-alias.png "部分语言简称"

## LICENSE

```
Copyright 2015 ShareFun Network Limited.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```