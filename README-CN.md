# Android SDK 接入说明
# 一、下载android sdk
    点击页面右上角的“Clone or download”按钮进行下载，下载完成后解压文件。
# 二、cocos2dx接口清单
    把interface文件夹下的ECServiceCocos2dx.h、ECServiceCocos2dx.cpp放入您的Classes文件夹
# 三、elvachatservice导入到项目
    把elvachatservice文件夹拷贝到您的主目录下
# 四、接入工程配置
    修改elvachatservice文件夹里的AndroidManifest.xml，增加需要的配置：
      1、增加需要的权限
        <uses-permission android:name="android.permission.INTERNET" />
        <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
        <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
        <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
        <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS" />
     2、增加activity:
        <activity
            android:name="com.ljoy.chatbot.ChatMainActivity"
            android:configChanges="orientation|screenSize|locale"
            android:screenOrientation="portrait">
        </activity>
        <activity
            android:name="com.ljoy.chatbot.FAQActivity"
            android:configChanges="orientation|screenSize|locale"
            android:screenOrientation="portrait">
        </activity>
# 五、接口调用说明
      1、sdk初始化。创建一个在JNI环境和Activity中传递的应用：（必须在游戏开始阶段调用）
        a、如果是在主Activity的onCreate中调用初始化接口init，则：
          ElvaChatServiceHelper.init(Activity activity,String appKey,String domain,String appId)
          其中：
          activity:当前运行的action，传this即可
          App Key:app密钥，从Web管理系统获取。
          domain:app域名，从Web管理系统获取。
          AppId:app唯一标识，从Web管理系统获取。
          
          注：后面这三个参数，请使用注册时的邮箱地址作为登录名登录https://cs30.net/elva。在Settings菜单Applications页面查看。初次使用，请先登录官网自助注册,地址为www.cs30.net/cn/pricing.html。
        b、如果需要延迟调用，则：
          在activity.java中调用：SetActivity(this);
          在Cocos2dx中调用：ECServiceCocos2dx::init(string appKey,string domain,string appId)
      2、接口调用方法
          1) 智能客服主界面启动，调用showElva方法，启动机器人界面
            ECServiceCocos2dx:: showElva (string playerName , string playerUid , int serverId,string playerParseId, string showConversationFlag,cocos2d::ValueMap& config);
            参数说明:
              playerName: 游戏中玩家名称。
              playerUid:玩家在游戏里的唯一标示id。
              serverId:玩家所在的服务器编号。
              playerParseId:推送传token。
              showConversationFlag(0或1):是否为vip, 0:标示非VIP；1:表示：VIP。此处为1时，将在机器人的聊天界面右上角，提供人工聊天的入口功能。
              config : 可选，自定义ValueMap信息。可以在此处设置特定的Tag信息。
            		
            参数示例:
              ECServiceCocos2dx:: showElva (“elvaTestName”,“12349303258”,1, “es234-3dfs-d42f-342sfe3s3”,”1”,
              { 
                hs-custom-metadata＝｛
                  hs-tags＝’军队，充值’，说明：hs-tags对应的值为vector类型，此处传入自定义的Tag，需要在Web管理配置同名称的Tag才能生效。
                  VersionCode＝’3’
            	  ｝
              }
            );
          2)展示单条FAQ，调用showSingleFAQ方法
            ECServiceCocos2dx:: showSingleFAQ (string faqId,cocos2d::ValueMap& config);
            参数说明：
              faqId：FAQ的PublishID,可以在Web后台https://cs30.net/elva中，从FAQs菜单下找到指定FAQ，查看PublishID。
              config : 可选，自定义ValueMap信息。参照 1)智能客服主界面启动
              
            注：如果在web管理后台配置了FAQ的SelfServiceInterface，并且SDK配置了相关参数，将在显示FAQ的同时，右上角提供功能菜单，可以对相关的自助服务进行调用
          3)展示相关部分FAQ，调用showFAQSection方法
            ECServiceCocos2dx:: showFAQSection (string sectionPublishId,cocos2d::ValueMap& config);
            参数说明：
              sectionPublishId：FAQ  Section的PublishID（可以在Web后台https://cs30.net/elva中，从FAQs菜单下[Section]菜单，查看PublishID）
              config : 可选，自定义ValueMap信息。参照 1)智能客服主界面启动
          4)展示FAQ列表，调用showFAQs方法
            ECServiceCocos2dx:: showFAQs (cocos2d::ValueMap& config)
            参数说明：
              config : 可选，自定义ValueMap信息。参照 1)智能客服主界面启动
          5)设置游戏名称信息，调用setName方法(建议游戏刚进入，调用Init之后就默认调用)
            ECServiceCocos2dx:: setName (string game_name);
            参数说明:
              game_name：游戏名称，设置后将显示在SDK中相关界面标题栏
          6)设置Token，使用google推送，调用registerDeviceToken方法（暂无）
            ECServiceCocos2dx:: registerDeviceToken(string deviceToken);
            参数说明:
              deviceToken：设备Token
          7)设置用户id信息，调用setUserId方法(使用自助服务必须调用，参见 2)展示单条FAQ)
            在showSingleFAQ之前调用：ECServiceCocos2dx:: setUserId(string playerUid);
            参数说明:
              playerUid：玩家唯一ID。
          8)设置服务器编号信息，调用setServerId方法(使用自助服务必须调用，参见 2)展示单条FAQ)
            在showSingleFAQ之前调用：ECServiceCocos2dx:: setServerId (int serverId);
            参数说明:
              serverId:服务器ID。
          9)设置玩家名称信息，调用setUserName方法(建议游戏刚进入，调用Init之后就默认调用)
            ECServiceCocos2dx:: setUserName (string playerName);
            参数说明:
              playerName:玩家名称。
          10)直接进行vip_chat人工客服聊天，调用showConversation方法(必须确保9）设置玩家名称信息setUserName 已经调用)
            ECServiceCocos2dx:: showConversation (string playerUid,int serverId,cocos2d::ValueMap& config);
            参数说明:
              playerUid:玩家在游戏里的唯一标示id
              serverId:玩家所在的服务器编号
              config : 可选，自定义ValueMap信息。参照 1)智能客服主界面启动
