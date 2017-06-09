# 重点提醒：<br />
1.一定要进行初始化init操作，不然会无法进入Elva智能客服系统。<br />
2.<div>
    <table border="0">
      <tr>
        <th>方法</th>
        <th>showElva</th>
        <th>showConversation</th>
        <th>showFAQs</th>
        <th>showFAQSection</th>
        <th>showSingleFAQ</th>
      </tr>
      <tr>
        <td>作用</td>
        <td>启动机器人界面</td>
        <td>调用人工客服入口</td>
        <td>展示FAQ列表</td>
        <td>展示Section</td>
        <td>展示单条FAQ</td>
      </tr>
    </table>
</div>

# Android SDK 接入具体说明
## 一、下载android sdk
  点击上一个页面右上角的“Clone or download”按钮下载Android SDK，下载完成后解压文件。
## 二、elvachatservice导入到项目
  把elvachatservice文件夹拷贝到项目下导入。
## 三、Google App Indexing导入到项目
  导入play-services-appindexing到您的项目中(如果项目包含google service appindexing可忽略该步)。
## 四、Android Appcompact相关包导入到项目	
导入android_libs下Android Appcompact到您的项目中(如果项目已经包含该包，全部包含或者部分包含，请不要重复导入，只需要导入项目中未包含的)。
如果您使用Gradle：<br />
  修改build.gradle,增加以下部分。根据需要，可以修改相关版本：<br />
    compile 'com.android.support:appcompat-v7:23.4.0' <br />
    compile 'com.android.support:design:23.4.0' <br />
    compile 'com.android.support:recyclerview-v7:23.4.0' <br />
    compile 'com.android.support:cardview-v7:23.4.0' <br />
## 五、接入工程配置
  在AndroidManifest.xml，增加需要的配置：     
#### 1、增加需要的权限
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
#### 2、增加activity:
    <activity
       android:name="com.ljoy.chatbot.ChatMainActivity"
       android:configChanges="orientation|screenSize|locale"
       android:screenOrientation="portrait">
    </activity>
    <activity
       android:name="com.ljoy.chatbot.FAQActivity"
       android:configChanges="orientation|screenSize|locale"
       android:screenOrientation="portrait"
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
            android:screenOrientation="portrait"
            android:theme="@style/Theme.AppCompat.Light.NoActionBar"
            >
    </activity>
#### 3、增加meta    
   <pre><meta-data
        android:name="com.google.android.gms.version"
        android:value="@integer/google_play_services_version" />
   </pre>

## 六、接口调用说明
#### 1、sdk初始化
   创建Activity中传递的应用：（必须在游戏开始阶段调用）<br />
> 在主Activity的onCreate中调用初始化接口init，则：<br />
    ELvaChatServiceSdk.init(Activity a, final String appSecret, final String domain, final String appId); <br />
> * 其中：<br />
activity:当前运行的action，传this即可。<br />
App Key:app密钥，从Web管理系统获取。<br />
domain:app域名，从Web管理系统获取。<br />
AppId:app唯一标识，从Web管理系统获取。<br />
注：后面这三个参数，请使用注册邮箱登录 [Elva AI 后台](https://aihelp.net/elva)。在Settings菜单Applications页面查看。初次使用，请先登录[Elva AI 官网](http://aihelp.net/index.html)自助注册。<br />   
          
#### 2、接口调用方法
1) 智能客服主界面启动，调用`showElva`方法，启动机器人界面<br />
ELvaChatServiceSdk.showElvaChatService(String npcName,String userName,String uid,String parseId,String serverId,String showConversationFlag,HashMap\<String,Object> customData); <br />
> * 参数说明：<br />
playerName:游戏中玩家名称。 <br />
playerUid:玩家在游戏里的唯一标示id。 <br />
serverId:玩家所在的服务器编号。 <br />
playerParseId:空。 <br />
showConversationFlag(0或1):是否开启人工入口。此处为1时，将在机器人的聊天界面右上角，提供人工聊天的入口。如下图。<br />
config:可选，自定义ValueMap信息。可以在此处设置特定的Tag信息。<br />
![showElva](https://github.com/CS30-NET/Pictures/blob/master/showElva-CN-Android.png "showElva")<br />

> * 参数示例:    
    <pre>  
ArrayList<String> tags = new ArrayList();
// 说明：hs-tags对应的值为ArrayList类型，此处传入自定义的Tag，需要在Web管理配置同名称的Tag才能生效
tags.add("pay1");
tags.add("s1");
tags.add("elvaTestTag");
HashMap<String,Object> map = new HashMap();
map.put("hs-tags",tags);
HashMap<String,Object> config = new HashMap();
config.put("hs-custom-metadata",map);
ELvaChatServiceSdk.showElvaChatService(“elvaTestName”,“12349303258”,1, “”,”1”,config);
   
	
2) 展示单条FAQ，调用`showSingleFAQ`方法<br />
    showSingleFAQ(String faqId,HashMap\<String,Object> config);<br />
> * 参数说明：<br />
faqId:FAQ的PublishID,可以在[Elva AI 后台](https://aihelp.net/elva)中，从FAQs菜单下找到指定FAQ，查看PublishID。<br />
config:可选，自定义ValueMap信息。参照 1)智能客服主界面启动。<br />
![showSingleFAQ](https://github.com/CS30-NET/Pictures/blob/master/showSingleFAQ-CN-Android.png "showSingleFAQ")<br />
注：如果在web管理后台配置了FAQ的SelfServiceInterface，并且SDK配置了相关参数，将在显示FAQ的同时，右上角提供功能菜单，可以对相关的自助服务进行调用。<br />

3) 展示相关部分FAQ，调用`showFAQSection`方法<br />
    showFAQSection(String sectionPublishId,HashMap\<String,Object> config);<br />
> * 参数说明：<br />
sectionPublishId:FAQ Section 的PublishID（可以在[Elva AI 后台](https://aihelp.net/elva) 中，从FAQs菜单下[Section]菜单，查看PublishID）<br />
config:可选，自定义ValueMap信息。参照 1)智能客服主界面启动。<br />
![showFAQSection](https://github.com/CS30-NET/Pictures/blob/master/showFAQSection-CN-Android.png "showFAQSection")<br />
> 
4) 展示FAQ列表，调用`showFAQs`方法<br />
    showFAQList(HashMap\<String,Object> config)<br />
> * 参数说明：<br />
config:可选，自定义ValueMap信息。参照 1)Elva AI 主界面启动。<br />
![showFAQs](https://github.com/CS30-NET/Pictures/blob/master/showFAQs-CN-Android.png "showFAQs")<br />
> 
5) 设置游戏名称信息，调用`setName`方法(建议游戏刚进入，调用Init之后就默认调用)<br />
    setName(String gameName);<br />
> * 参数说明:<br />
gameName:游戏名称，设置后将显示在SDK中相关界面标题栏。<br />
> 
6) 设置Token，使用google推送，调用`registerDeviceToken`方法（暂无）<br />
    暂无;<br />
> * 参数说明:<br />
deviceToken:设备Token。<br />
> 
7) 设置用户id信息，调用`setUserId`方法(使用自助服务必须调用，参见 2)展示单条FAQ)<br />
    在showSingleFAQ之前调用：setUserId(String playerUid);<br />
> * 参数说明:<br />
playerUid:玩家唯一ID。<br />
> 
8) 设置服务器编号信息，调用`setServerId`方法(使用自助服务必须调用，参见 2)展示单条FAQ)<br />
    在showSingleFAQ之前调用：setServerId(String serverId);<br />
> * 参数说明:<br />
serverId:服务器ID。<br />
> 
9) 设置玩家名称信息，调用`setUserName`方法(建议游戏刚进入，调用Init之后就默认调用)<br />
    setUserName(String userName);<br />
> * 参数说明:<br />
userName:玩家名称。<br />
> 
10) 直接进行vip_chat人工客服聊天，调用`showConversation`方法(必须确保9）设置玩家名称信息setUserName 已经调用)<br />
    showConversation(String uid,String serverId,HashMap\<String,Object> config);<br />
> * 参数说明:<br />
playerUid:玩家在游戏里的唯一标示id。<br />
serverId:玩家所在的服务器编号。<br />
config:可选，自定义ValueMap信息。参照 1)Elva AI 主界面启动。<br />
![showConversation](https://github.com/CS30-NET/Pictures/blob/master/showConversation-CN-Android.png "showConversation")
 > 
 11) 智能客服运营模块主界面启动，调用`showElvaOP`方法，启动运营模块界面<br />
showElvaOP(String npcName,String userName,String uid,String parseId,String serverId,String showConversationFlag,HashMap\<String,Object> customData,int defaultTabIndex); <br />
> * 参数说明：<br />
              playerName:游戏中玩家名称。 <br />
              playerUid:玩家在游戏里的唯一标示id。 <br />
              serverId:玩家所在的服务器编号。 <br />
              playerParseId:空。 <br />
              showConversationFlag(0或1):是否开启人工入口。此处为1时，将在机器人的聊天界面右上角，提供人工聊天的入口。如下图。<br />
              config:可选，自定义ValueMap信息。可以在此处设置特定的Tag信息。<br />
	      defaultTabIndex:可选，设置默认打开的Tab页index（从0开始，如需默认打开Elva，可设置为999）。<br />	
	      
> * 参数示例:       
        <pre>
ArrayList<String> tags = new ArrayList();
// 说明：hs-tags对应的值为ArrayList类型，此处传入自定义的Tag，需要在Web管理配置同名称的Tag才能生效
        tags.add("pay1");
        tags.add("s1");
        tags.add("elvaTestTag");
        HashMap<String,Object> map = new HashMap();
        map.put("hs-tags",tags);
        HashMap<String,Object> config = new HashMap();
        config.put("hs-custom-metadata",map);
ELvaChatServiceSdk.showElvaOP("elvaTestName","12349303258",1, "","1",config,0);


12) 设置语言，调用`setSDKLanguage`方法(Elva默认使用手机语言适配，如需修改，可在初始化之后调用，并在切换App语言后再次调用。)<br />
    setSDKLanguage(String language);<br />
> * 参数说明:<br />
language:语言名称。如英语为en,简体中文为zh_CN。更多语言简称参见Elva后台，"设置"-->"语言"的Alias列。<br />
> 
