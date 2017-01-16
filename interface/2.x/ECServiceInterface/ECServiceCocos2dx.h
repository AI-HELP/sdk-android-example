//
//  ECServiceCocos2dx.h
//  ElvaChatService Cocos2dx SDK
//

#ifndef ECServiceCocos2dx_h
#define ECServiceCocos2dx_h

#include "cocos2d.h"
#include <string>
#define ES_WITH_TAGS_MATCHING "withTagsMatching"

using namespace std;

class ECServiceCocos2dx
{
public:
//#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
public:
    static void init(string appSecret,string domain,string appId);
	static void showElva(string playerName,string playerUid,int serverId,string playerParseId,string playershowConversationFlag);
    static void showElva(string playerName,string playerUid,int serverId,string playerParseId,string playershowConversationFlag,cocos2d::CCDictionary *config);
    static void showSingleFAQ(string faqId);
    static void showSingleFAQ(string faqId,cocos2d::CCDictionary *config);
    static void showFAQSection(string sectionPublishId);
    static void showFAQSection(string sectionPublishId,cocos2d::CCDictionary *config);
    static void showFAQs();
    static void showFAQs(cocos2d::CCDictionary *config);
    static void setName(string game_name);
    static void registerDeviceToken(string deviceToken);
    static void setUserId(string playerUid);//自助服务，在showFAQ之前调用
    static void setServerId(int serverId);//自助服务，在showFAQ之前调用
    static void setUserName(string playerName);//在需要的接口之前调用，建议游戏刚进入就默认调用
    static void showConversation(string playerUid,int serverId);//请优先实现setUserName接口
    static void showConversation(string playerUid,int serverId,cocos2d::CCDictionary *config);
    static bool setSDKLanguage(const char* locale);
    static void useDevice();
    static void setEvaluateStar(int star);//评价默认星星数量
    
};

#endif