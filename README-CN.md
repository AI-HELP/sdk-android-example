Android SDK 接入说明
=====
一、下载android sdk
------
  点击上一个页面右上角的“Clone or download”按钮下载Android SDK，下载完成后解压文件。
二、cocos2dx接口清单
------
  把interface文件夹下的ECServiceCocos2dx.h、ECServiceCocos2dx.cpp放入您的Classes文件夹。
三、elvachatservice导入到项目
------
  把elvachatservice文件夹拷贝到您的主目录下。
四、Google App Indexing导入到项目
------
  导入play-services-appindexing到您的项目中(如果项目存在google service 可忽略该步)。
五、接入工程配置
------
  修改elvachatservice文件夹里的AndroidManifest.xml，增加需要的配置：     
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
       android:screenOrientation="portrait">
       <intent-filter android:label="@string/app_name">
          <action android:name="android.intent.action.VIEW" />
          <category android:name="android.intent.category.DEFAULT" />
          <category android:name="android.intent.category.BROWSABLE" />
          <data android:scheme="https"
                android:host="cs30.net"
                android:pathPrefix="/elvaFAQ" />
       </intent-filter>
    </activity>
#### 3、增加meta        
    <meta-data
       android:name="com.google.android.gms.version"
       android:value="@integer/google_play_services_version" />
六、接口调用说明
------
#### 1、sdk初始化
   创建一个在JNI环境和Activity中传递的应用：（必须在游戏开始阶段调用）<br />
> a.如果是在主Activity的onCreate中调用初始化接口init，则：<br />
    ElvaChatServiceHelper.init(Activity activity,String appKey,String domain,String appId); <br />
* 其中：<br />
activity:当前运行的action，传this即可。<br />
App Key:app密钥，从Web管理系统获取。<br />
domain:app域名，从Web管理系统获取。<br />
AppId:app唯一标识，从Web管理系统获取。<br />
注：后面这三个参数，请使用注册时的邮箱地址作为登录名登录 [智能客服后台](https://cs30.net/elva)。在Settings菜单Applications页面查看。初次使用，请先登录[智能客服官网](http://cs30.net/index.html)自助注册。<br />
> 
> b.如果需要延迟调用，则：<br />
在activity.java中调用：SetActivity(this)<br />
在Cocos2dx中调用：ECServiceCocos2dx::init(string appKey,string domain,string appId)<br />   
          
#### 2、接口调用方法
> 1) 智能客服主界面启动，调用`showElva`方法，启动机器人界面<br />
ECServiceCocos2dx:: showElva (string playerName , string playerUid, int serverId, string playerParseId, string showConversationFlag,cocos2d::ValueMap& config); <br />
* 参数说明：<br />
              playerName:游戏中玩家名称。 <br />
              playerUid:玩家在游戏里的唯一标示id。 <br />
              serverId:玩家所在的服务器编号。 <br />
              playerParseId:推送传token。 <br />
              showConversationFlag(0或1):是否为vip, 0:标示非VIP；1:表示：VIP。此处为1时，将在机器人的聊天界面右上角，提供人工聊天的入口。如下图。<br />
              config:可选，自定义ValueMap信息。可以在此处设置特定的Tag信息。<br />
![showElva](https://github.com/CS30-NET/Pictures/blob/master/showElva-CN-Android.png "showElva")<br />
* 参数示例:   
     
    ECServiceCocos2dx:: showElva (“elvaTestName”,“12349303258”,1, “es234-3dfs-d42f-342sfe3s3”,”1”,
     { 
       hs-custom-metadata＝｛
       hs-tags＝’军队，充值’，说明：hs-tags对应的值为vector类型，此处传入自定义的Tag，需要在Web管理配置同名称的Tag才能生效。
       VersionCode＝’3’
       ｝
     }
    );
> 
> 2)展示单条FAQ，调用`showSingleFAQ`方法<br />
    ECServiceCocos2dx:: showSingleFAQ (string faqId,cocos2d::ValueMap& config);<br />
* 参数说明：<br />
faqId:FAQ的PublishID,可以在[智能客服后台](https://cs30.net/elva)中，从FAQs菜单下找到指定FAQ，查看PublishID。<br />
config:可选，自定义ValueMap信息。参照 1)智能客服主界面启动。<br />
![showSingleFAQ](https://github.com/CS30-NET/Pictures/blob/master/showSingleFAQ-CN-Android.png "showSingleFAQ")<br />
注：如果在web管理后台配置了FAQ的SelfServiceInterface，并且SDK配置了相关参数，将在显示FAQ的同时，右上角提供功能菜单，可以对相关的自助服务进行调用。<br />
> 
> 3)展示相关部分FAQ，调用`showFAQSection`方法<br />
    ECServiceCocos2dx:: showFAQSection (string sectionPublishId,cocos2d::ValueMap& config);<br />
* 参数说明：<br />
sectionPublishId:FAQ Section 的PublishID（可以在[智能客服后台](https://cs30.net/elva) 中，从FAQs菜单下[Section]菜单，查看PublishID）<br />
config:可选，自定义ValueMap信息。参照 1)智能客服主界面启动。<br />
![showFAQSection](https://github.com/CS30-NET/Pictures/blob/master/showFAQSection-CN-Android.png "showFAQSection")<br />
> 
> 4)展示FAQ列表，调用`showFAQs`方法<br />
    ECServiceCocos2dx:: showFAQs (cocos2d::ValueMap& config)<br />
* 参数说明：<br />
config:可选，自定义ValueMap信息。参照 1)智能客服主界面启动。<br />
![showFAQs](https://github.com/CS30-NET/Pictures/blob/master/showFAQs-CN-Android.png "showFAQs")<br />
> 
> 5)设置游戏名称信息，调用`setName`方法(建议游戏刚进入，调用Init之后就默认调用)<br />
    ECServiceCocos2dx:: setName (string game_name);<br />
* 参数说明:<br />
game_name:游戏名称，设置后将显示在SDK中相关界面标题栏。<br />
> 
> 6)设置Token，使用google推送，调用`registerDeviceToken`方法（暂无）<br />
    ECServiceCocos2dx:: registerDeviceToken(string deviceToken);<br />
* 参数说明:<br />
deviceToken:设备Token。<br />
> 
> 7)设置用户id信息，调用`setUserId`方法(使用自助服务必须调用，参见 2)展示单条FAQ)<br />
    在showSingleFAQ之前调用：ECServiceCocos2dx:: setUserId(string playerUid);<br />
* 参数说明:<br />
playerUid:玩家唯一ID。<br />
> 
> 8)设置服务器编号信息，调用`setServerId`方法(使用自助服务必须调用，参见 2)展示单条FAQ)<br />
    在showSingleFAQ之前调用：ECServiceCocos2dx:: setServerId (int serverId);<br />
* 参数说明:<br />
serverId:服务器ID。<br />
> 
> 9)设置玩家名称信息，调用`setUserName`方法(建议游戏刚进入，调用Init之后就默认调用)<br />
    ECServiceCocos2dx:: setUserName (string playerName);<br />
* 参数说明:<br />
playerName:玩家名称。<br />
> 
> 10)直接进行vip_chat人工客服聊天，调用`showConversation`方法(必须确保9）设置玩家名称信息setUserName 已经调用)<br />
    ECServiceCocos2dx:: showConversation (string playerUid,int serverId,cocos2d::ValueMap& config);<br />
* 参数说明:<br />
playerUid:玩家在游戏里的唯一标示id。<br />
serverId:玩家所在的服务器编号。<br />
config:可选，自定义ValueMap信息。参照 1)智能客服主界面启动。<br />
![showConversation](https://github.com/CS30-NET/Pictures/blob/master/showConversation-CN-Android.png "showConversation")
