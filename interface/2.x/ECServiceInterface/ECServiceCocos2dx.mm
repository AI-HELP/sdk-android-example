//
//  ECServiceCocos2dx.m
//  SanguoCOK
//
//  Created by zhangwei on 16/4/12.
//
//

#import "ECServiceCocos2dx.h"
#import <ElvaChatServiceSDK/MessageViewController.h>
#import <ElvaChatServiceSDK/GetServerIP.h>
#import <ElvaChatServiceSDK/ShowWebViewController.h>
#import <ElvaChatServiceSDK/ChatMessageViewController.h>
#import <ElvaChatServiceSDK/ShowFAQListController.h>
#import <ElvaChatServiceSDK/ShowFAQSectionController.h>
#import <ElvaChatServiceSDK/ELVADBManager.h>

static void addCCObjectToNSDict(const char * key, cocos2d::CCObject* object, NSMutableDictionary *dict)
{
    if(key == NULL || object == NULL) {
        return;
    }
    NSString *keyStr = [NSString stringWithCString:key encoding:NSUTF8StringEncoding];
    
    if (cocos2d::CCDictionary *ccDict = dynamic_cast<cocos2d::CCDictionary *>(object)) {
        NSMutableDictionary *dictElement = [NSMutableDictionary dictionary];
        #if ((COCOS2D_VERSION & 0x00FF0000) > 0x20000)
                cocos2d::DictElement *element = NULL;
        #else
                cocos2d::CCDictElement *element = NULL;
        #endif
        cocos2d::CCDICT_FOREACH(ccDict, element)
        {
            addCCObjectToNSDict(element->getStrKey(), element->getObject(), dictElement);
        }
        [dict setObject:dictElement forKey:keyStr];
    } else if (cocos2d::CCString *element = dynamic_cast<cocos2d::CCString *>(object)) {
        NSString *strElement = [NSString stringWithCString:element->getCString() encoding:NSUTF8StringEncoding];
        [dict setObject:strElement forKey:keyStr];
    } else if (cocos2d::CCArray *ccArray = dynamic_cast<cocos2d::CCArray *>(object)) {
        if ((ccArray) && (ccArray)->data->num > 0) {
            NSMutableString * nValue  = [[NSMutableString alloc]init];
            cocos2d::CCObject *element = NULL;
            int count = 0;
            CCARRAY_FOREACH(ccArray, element){
                if (cocos2d::CCString *ccString = dynamic_cast<cocos2d::CCString *>(element)) {
                    if (count > 0) {
                        [nValue appendString:@","];
                    }
                    NSString *strElement = [NSString stringWithCString:ccString->getCString() encoding:NSUTF8StringEncoding];
                    [nValue appendString:strElement];
                    ++count;
                }
                
            }
            [dict setObject:nValue forKey:keyStr];
        }
    }
}


static NSString*  elvaParseConfig(cocos2d::CCDictionary *config){
    if(config == NULL){
        return @"";
    }
    if (cocos2d::CCDictionary *ccDict = dynamic_cast<cocos2d::CCDictionary *>(object)) {
        NSMutableDictionary *dictionary = [NSMutableDictionary dictionary];
        cocos2d::CCDictionary* customData = dynamic_cast<cocos2d::CCDictionary*>(config->objectForKey("hs-custom-metadata"));
        if(customData == NULL){
            return @"";
        }
    #if ((COCOS2D_VERSION & 0x00FF0000) > 0x20000)
        cocos2d::DictElement *element = NULL;
    #else
        cocos2d::CCDictElement *element = NULL;
    #endif
        cocos2d::CCDICT_FOREACH(customData, element)
        {
            addCCObjectToNSDict(element->getStrKey(), element->getObject(), dictionary);
        }
        NSMutableDictionary *map = [[NSMutableDictionary  alloc]initWithCapacity:0];
        [map setObject:dictionary forKey:@"hs-custom-metadata"];
        NSData *jsonData = [NSJSONSerialization dataWithJSONObject:map
                                                           options:0
                                                             error:nil];
        //把json 转成 nsstring
        NSString * jsonString = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
        return jsonString;
        
    }else{
        return @"";
    }
}

static NSString* elvaParseCString(const char *cstring) {
    if (cstring == NULL) {
        return NULL;
    }
    NSString * nsstring = [[NSString alloc] initWithBytes:cstring
                                                   length:strlen(cstring)
                                                 encoding:NSUTF8StringEncoding];
    return [nsstring autorelease];
}

#pragma mark - 初始化init
void ECServiceCocos2dx::init(string appSecret,string domain,string appId) {
    NSString* NSAppId = elvaParseCString(appId.c_str());
    NSString* NSAppSecret = elvaParseCString(appSecret.c_str());
    NSString* NSDomain = elvaParseCString(domain.c_str());
    
    [GetServerIP getServerMsgWithAppId:NSAppSecret
                                Domain:NSDomain
                                 appId:NSAppId
     ];
}
#pragma mark - show 不带参数config
void ECServiceCocos2dx::showElva(string playerName,string playerUid,int serverId,string playerParseId,string playershowConversationFlag){
    
    NSString* NSuserName = elvaParseCString(playerName.c_str());
    
    NSString* NSuserId = elvaParseCString(playerUid.c_str());
    
    NSString* parseId = elvaParseCString(playerParseId.c_str());
    
    NSString* conversationFlag = elvaParseCString(playershowConversationFlag.c_str());
    
    [UIApplication sharedApplication].keyWindow.backgroundColor = [UIColor whiteColor];
    //初始化KCMainViewController
    MessageViewController *mainController=[MessageViewController getMessageData];
    
    //vipchat
    mainController.vipChat =conversationFlag;
    
    GetServerIP* faqUrl = [GetServerIP getFaqService];
    //playerParseId
    faqUrl.playerParseId = parseId;
    
    //serverId
    faqUrl.serverId =serverId;
    
    [mainController initParamsWithUserName:NSuserName  UserId:NSuserId Title:@"ElvaChatService"];
    //设置自定义控制器的大小和window相同，位置为（0，0）
    mainController.view.frame=[UIApplication sharedApplication].keyWindow.bounds;
    //设置此控制器为window的根控制器
    
    [[UIApplication sharedApplication].keyWindow.rootViewController presentViewController:mainController animated:YES completion:^{
        nil;
    }];
    
}

#pragma mark - show 带参数config
void ECServiceCocos2dx::showElva(string playerName,string playerUid,int serverId,string playerParseId,string playershowConversationFlag,cocos2d::CCDictionary *config) {
    
    NSString* NSuserName = elvaParseCString(playerName.c_str());
    
    NSString* NSuserId = elvaParseCString(playerUid.c_str());
    
    NSString* parseId = elvaParseCString(playerParseId.c_str());
    
    NSString *conversationFlag =elvaParseCString(playershowConversationFlag.c_str());
    

    //把json 转成 nsstring
    NSString * jsonString = elvaParseConfig(config);
    
    GetServerIP* faqUrl = [GetServerIP getFaqService];
    faqUrl.customerData = jsonString;
    
    //playerParseId
    faqUrl.playerParseId = parseId;
    
    //serverId
    faqUrl.serverId =serverId;
    
    [UIApplication sharedApplication].keyWindow.backgroundColor = [UIColor whiteColor];
    
    //初始化KCMainViewController
    MessageViewController *mainController=[MessageViewController getMessageData];
    //vipchat
    mainController.vipChat =conversationFlag;
    
    [mainController initParamsWithUserName:NSuserName UserId:NSuserId Title:@"ElvaChatService"];
    //设置自定义控制器的大小和window相同，位置为（0，0）
    mainController.view.frame=[UIApplication sharedApplication].keyWindow.bounds;
    //设置此控制器为window的根控制器
    [[UIApplication sharedApplication].keyWindow.rootViewController presentViewController:mainController animated:YES completion:^{
        nil;
    }];
}



#pragma mark - faq参数为faqID
void ECServiceCocos2dx::showSingleFAQ(string faqId) {
    GetServerIP* faqUrl = [GetServerIP getFaqService];
    NSString *showfaqs = faqUrl.showUrl;
    NSString *faqid = elvaParseCString(faqId.c_str());
    ShowWebViewController *showWebView = [[ShowWebViewController alloc]init];
    
    NSString * appId = faqUrl.appId;
    showWebView.isShowManue = true;//首次打开显示菜单
    //判断是否存在userID，没有就不显示自助服务
    if(faqUrl.userId == nil || [faqUrl.userId isEqualToString:@""]){
        showWebView.isShowUserSelfBtn = true;
    }else{
        showWebView.isShowUserSelfBtn = false;
    }
    [showWebView showSelfInterface:faqid];
    
    ELVADBManager *db = [ELVADBManager getSharedInstance];
    NSString *faqContent =  [db getFaqByPublishId:faqid];
    if(faqContent){
        showWebView.isShowFaqList = true;
        showWebView.contentStr = faqContent;
    }else{
        //获取本地语言
        NSUserDefaults* defs = [NSUserDefaults standardUserDefaults];
        NSArray  *languages = [defs objectForKey:@"AppleLanguages"];
        NSString *preferredLang = [languages objectAtIndex:0];
        NSString * type = @"3";
        NSString* url =[NSString stringWithFormat:@"%@?type=%@&appid=%@&l=%@&faqid=%@",showfaqs,type,appId,preferredLang,faqid];
        showWebView.url = [NSURL URLWithString:url];
    }
    [[UIApplication sharedApplication].keyWindow.rootViewController presentViewController:showWebView animated:YES completion:^{
        nil;
    }];
}

#pragma mark - faq参数为faqID 带config
void ECServiceCocos2dx::showSingleFAQ(string faqId,cocos2d::CCDictionary *config) {
    //TODO 解析config转换为NSMutableDictionary
    
    
    //把json 转成 nsstring
    NSString * jsonString = elvaParseConfig(config);
    
    GetServerIP* faqUrl = [GetServerIP getFaqService];
    faqUrl.customerData = jsonString;
    ShowWebViewController *showWebView = [[ShowWebViewController alloc]init];
    NSString *faqid = elvaParseCString(faqId.c_str());
    
    NSString * appId = faqUrl.appId;
    showWebView.isShowManue = true;//首次打开显示菜单
    //判断是否存在userID，没有就不显示自助服务
    if(faqUrl.userId == nil || [faqUrl.userId isEqualToString:@""]){
        showWebView.isShowUserSelfBtn = true;
    }else{
        showWebView.isShowUserSelfBtn = false;
    }
    [showWebView showSelfInterface:faqid];//展示自助服务
    
    ELVADBManager *db = [ELVADBManager getSharedInstance];
    NSString *faqContent =  [db getFaqByPublishId:faqid];
    if(faqContent){//默认查询本地数据库，如果查询不到就从服务器查询
        showWebView.isShowFaqList = true;
        showWebView.contentStr = faqContent;
    }else{
        NSString *showfaqs = faqUrl.showUrl;
        //获取本地语言
        NSUserDefaults* defs = [NSUserDefaults standardUserDefaults];
        NSArray  *languages = [defs objectForKey:@"AppleLanguages"];
        NSString *preferredLang = [languages objectAtIndex:0];
        NSString * type = @"3";
        NSString* url =[NSString stringWithFormat:@"%@?type=%@&appid=%@&l=%@&faqid=%@",showfaqs,type,appId,preferredLang,faqid];
        showWebView.url = [NSURL URLWithString:url];
    }
    [[UIApplication sharedApplication].keyWindow.rootViewController presentViewController:showWebView animated:YES completion:^{
        nil;
    }];
    
}

#pragma mark - showFAQSection
void ECServiceCocos2dx::showFAQSection(string sectionPublishId){
    
    GetServerIP* faqUrl = [GetServerIP getFaqService];
    NSString *sectionId = elvaParseCString(sectionPublishId.c_str());
    
    ELVADBManager *db = [ELVADBManager getSharedInstance];
    NSMutableArray *faqsArray = [db getFaqsBySectionId:sectionId];
    if(nil != faqsArray)
    {
        ShowFAQSectionController *showWebView = [[ShowFAQSectionController alloc]init];
        
        showWebView.isShowManue = true;//首次打开显示菜单
        //判断是否存在userID，没有就不显示自助服务
        if(faqUrl.userId == nil || [faqUrl.userId isEqualToString:@""]){
            showWebView.isShowUserSelfBtn = true;
        }else{
            showWebView.isShowUserSelfBtn = false;
        }
        showWebView.sectionId =sectionId;
        [[UIApplication sharedApplication].keyWindow.rootViewController presentViewController:showWebView animated:YES completion:^{
            nil;
        }];
    }else{
        ShowWebViewController *showWebView = [[ShowWebViewController alloc]init];
        NSString *showfaqs = faqUrl.showUrl;//获取url
        showWebView.isShowManue = true;//首次打开显示菜单
        //判断是否存在userID，没有就不显示自助服务
        if(faqUrl.userId == nil || [faqUrl.userId isEqualToString:@""]){
            showWebView.isShowUserSelfBtn = true;
        }else{
            showWebView.isShowUserSelfBtn = false;
        }
        //获取本地语言
        NSUserDefaults* defs = [NSUserDefaults standardUserDefaults];
        NSArray  *languages = [defs objectForKey:@"AppleLanguages"];
        NSString *preferredLang = [languages objectAtIndex:0];
        NSString *appid = faqUrl.appId;
        NSString *type = @"2";
        NSString* url =[NSString stringWithFormat:@"%@?type=%@&appid=%@&l=%@&sectionid=%@",showfaqs,type,appid,preferredLang,sectionId];
        showWebView.url = [NSURL URLWithString:url];
        showWebView.loadingBarTintColor = [UIColor blueColor];
        [[UIApplication sharedApplication].keyWindow.rootViewController presentViewController:showWebView animated:YES completion:^{
            nil;
        }];
        
    }
    
    
}

#pragma mark - showFAQSection(带config)
void ECServiceCocos2dx::showFAQSection(string sectionPublishId,cocos2d::CCDictionary *config){
    GetServerIP* faqUrl = [GetServerIP getFaqService];
    NSString *sectionId = elvaParseCString(sectionPublishId.c_str());
    
    
    
    //把json 转成 nsstring
    NSString * jsonString = elvaParseConfig(config);
    faqUrl.customerData = jsonString;
    
    
    ELVADBManager *db = [ELVADBManager getSharedInstance];
    NSMutableArray *faqsArray = [db getFaqsBySectionId:sectionId];
    if(nil != faqsArray)
    {
        ShowFAQSectionController *showWebView = [[ShowFAQSectionController alloc]init];
        showWebView.isShowManue = true;//首次打开显示菜单
        //判断是否存在userID，没有就不显示自助服务
        if(faqUrl.userId == nil || [faqUrl.userId isEqualToString:@""]){
            showWebView.isShowUserSelfBtn = true;
        }else{
            showWebView.isShowUserSelfBtn = false;
        }
        showWebView.sectionId = sectionId;
        [[UIApplication sharedApplication].keyWindow.rootViewController presentViewController:showWebView animated:YES completion:^{
            nil;
        }];
        
    }else{
        ShowWebViewController *webView = [[ShowWebViewController alloc]init];
        NSString *showfaqs = faqUrl.showUrl;
        webView.isShowManue = true;//首次打开显示菜单
        //判断是否存在userID，没有就不显示自助服务
        if(faqUrl.userId == nil || [faqUrl.userId isEqualToString:@""]){
            webView.isShowUserSelfBtn = true;
        }else{
            webView.isShowUserSelfBtn = false;
        }
        //获取本地语言
        NSUserDefaults* defs = [NSUserDefaults standardUserDefaults];
        NSArray  *languages = [defs objectForKey:@"AppleLanguages"];
        NSString *preferredLang = [languages objectAtIndex:0];
        NSString *appid = faqUrl.appId;
        NSString *type = @"2";
        NSString* url =[NSString stringWithFormat:@"%@?type=%@&appid=%@&l=%@&sectionid=%@",showfaqs,type,appid,preferredLang,sectionId];
        
        webView.url = [NSURL URLWithString:url];
        webView.loadingBarTintColor = [UIColor blueColor];
        [[UIApplication sharedApplication].keyWindow.rootViewController presentViewController:webView animated:YES completion:^{
            nil;
        }];
    }
    
}


#pragma mark - faqList无参数
void ECServiceCocos2dx::showFAQs() {
    GetServerIP* faqUrl = [GetServerIP getFaqService];
    
    ELVADBManager *db = [ELVADBManager getSharedInstance];
    NSMutableArray * sectionsArray = [db getAllSections];
    if(nil != sectionsArray)
    {
        ShowFAQListController *show = [[ShowFAQListController alloc]init];
        show.isShowManue = true;//首次打开显示菜单
        //判断是否存在userID，没有就不显示自助服务
        if(faqUrl.userId == nil || [faqUrl.userId isEqualToString:@""]){
            show.isShowUserSelfBtn = true;
        }else{
            show.isShowUserSelfBtn = false;
        }
        
        [[UIApplication sharedApplication].keyWindow.rootViewController presentViewController:show animated:YES completion:^{
            nil;
        }];
        
    }else{
        NSString *showfaqs = faqUrl.showfaqlist;
        NSString *appId = faqUrl.appId;
        
        //获取本地语言
        NSUserDefaults* defs = [NSUserDefaults standardUserDefaults];
        NSArray* languages = [defs objectForKey:@"AppleLanguages"];
        NSString* preferredLang = [languages objectAtIndex:0];
        
        ShowWebViewController *showWebView = [[ShowWebViewController alloc]init];
        NSString * type = @"1";
        NSString* url =[NSString stringWithFormat:@"%@?type=%@&appid=%@&l=%@",showfaqs,type,appId,preferredLang];
        //    NSString* url =[NSString stringWithFormat:@"%@?AppID=%@&l=%@",showfaqs,appId,preferredLang];
        
        NSURLCache *urlCache = [NSURLCache sharedURLCache];
        /* 设置缓存的大小为1M*/
        [urlCache setMemoryCapacity:1*1024*1024];
        //创建一个nsurl
        NSURL *cacheUrls = [NSURL URLWithString:url];
        //        showWebView.url = [NSURL URLWithString:url];
        showWebView.url =cacheUrls;
        
        //创建一个请求
        NSMutableURLRequest *request =
        [NSMutableURLRequest
         requestWithURL: showWebView.url
         cachePolicy:NSURLRequestReloadRevalidatingCacheData
         timeoutInterval:60.0f];
        //从请求中获取缓存输出
        NSCachedURLResponse *response =
        [urlCache cachedResponseForRequest:request];
        //判断是否有缓存
        if (response != nil){
            //        NSLog(@"如果有缓存输出，从缓存中获取数据");
            [request setCachePolicy:NSURLRequestReloadRevalidatingCacheData];
        }
        
        showWebView.isShowManue = true;//首次打开显示菜单
        //判断是否存在userID，没有就不显示自助服务
        if(faqUrl.userId == nil || [faqUrl.userId isEqualToString:@""]){
            showWebView.isShowUserSelfBtn = true;
        }else{
            showWebView.isShowUserSelfBtn = false;
        }
        showWebView.loadingBarTintColor = [UIColor blueColor];
        [[UIApplication sharedApplication].keyWindow.rootViewController presentViewController:showWebView animated:YES completion:^{
            nil;
        }];
        
    }
    
}

#pragma mark - showFAQList 带参数config
void ECServiceCocos2dx::showFAQs(cocos2d::CCDictionary *config) {
    
    
    
    NSString * jsonString = elvaParseConfig(config);
    GetServerIP* faqUrl = [GetServerIP getFaqService];
    faqUrl.customerData = jsonString;
    
    ELVADBManager *db = [ELVADBManager getSharedInstance];
    NSMutableArray * sectionsArray = [db getAllSections];
    if(nil != sectionsArray)
    {
        ShowFAQListController *show = [[ShowFAQListController alloc]init];
        show.isShowManue = true;//首次打开显示菜单
        //判断是否存在userID，没有就不显示自助服务
        if(faqUrl.userId == nil || [faqUrl.userId isEqualToString:@""]){
            show.isShowUserSelfBtn = true;
        }else{
            show.isShowUserSelfBtn = false;
        }
        
        [[UIApplication sharedApplication].keyWindow.rootViewController presentViewController:show animated:YES completion:^{
            nil;
        }];
    }else{
        
        NSString *showfaqs = faqUrl.showfaqlist;
        NSString *appId = faqUrl.appId;
        
        //获取本地语言
        NSUserDefaults* defs = [NSUserDefaults standardUserDefaults];
        NSArray* languages = [defs objectForKey:@"AppleLanguages"];
        NSString* preferredLang = [languages objectAtIndex:0];
        
        ShowWebViewController *showWebView = [[ShowWebViewController alloc]init];
        NSString * type = @"1";
        NSString* url =[NSString stringWithFormat:@"%@?type=%@&appid=%@&l=%@",showfaqs,type,appId,preferredLang];
        //    NSString* url =[NSString stringWithFormat:@"%@?AppID=%@&l=%@",showfaqs,appId,preferredLang];
        
        NSURLCache *urlCache = [NSURLCache sharedURLCache];
        /* 设置缓存的大小为1M*/
        [urlCache setMemoryCapacity:1*1024*1024];
        //创建一个nsurl
        NSURL *cacheUrls = [NSURL URLWithString:url];
        //        showWebView.url = [NSURL URLWithString:url];
        showWebView.url =cacheUrls;
        
        //创建一个请求
        NSMutableURLRequest *request =
        [NSMutableURLRequest
         requestWithURL: showWebView.url
         cachePolicy:NSURLRequestReloadRevalidatingCacheData
         timeoutInterval:60.0f];
        //从请求中获取缓存输出
        NSCachedURLResponse *response =
        [urlCache cachedResponseForRequest:request];
        //判断是否有缓存
        if (response != nil){
            // NSLog(@"如果有缓存输出，从缓存中获取数据");
            [request setCachePolicy:NSURLRequestReloadRevalidatingCacheData];
        }
        showWebView.isShowManue = true;//首次打开显示菜单
        //判断是否存在userID，没有就不显示自助服务
        if(faqUrl.userId == nil || [faqUrl.userId isEqualToString:@""]){
            showWebView.isShowUserSelfBtn = true;
        }else{
            showWebView.isShowUserSelfBtn = false;
        }
        
        showWebView.loadingBarTintColor = [UIColor blueColor];
        [[UIApplication sharedApplication].keyWindow.rootViewController presentViewController:showWebView animated:YES completion:^{
            nil;
        }];
        
    }
    
    
    
    
}

#pragma mark - 设置游戏名称
void ECServiceCocos2dx::setName(string game_name){
    GetServerIP* faqUrl = [GetServerIP getFaqService];
    NSString* gameName  = elvaParseCString(game_name.c_str());
    faqUrl.gameName = gameName;
}

#pragma mark - 设置deviceToken
void ECServiceCocos2dx::registerDeviceToken(string deviceToken) {
    
    GetServerIP* faqUrl = [GetServerIP getFaqService];
    NSString* token = elvaParseCString(deviceToken.c_str());
    faqUrl.isToken = true;
    faqUrl.deviceToken =token;
    
}
#pragma mark - 设置UserId
void ECServiceCocos2dx::setUserId(string playerUid)
{
    GetServerIP* faqUrl = [GetServerIP getFaqService];
    NSString* userId = elvaParseCString(playerUid.c_str());
    faqUrl.userId = userId;
    
}
#pragma mark - 设置ServerId
void ECServiceCocos2dx::setServerId(int serverId)
{
    GetServerIP* faqUrl = [GetServerIP getFaqService];
    faqUrl.serverId = serverId;
    
}

#pragma mark - 设置userName
void ECServiceCocos2dx::setUserName(string playerName)
{
    GetServerIP* faqUrl = [GetServerIP getFaqService];
    NSString* userName = elvaParseCString(playerName.c_str());
    faqUrl.userName = userName;
    
}
#pragma mark - 设置showConversation
void ECServiceCocos2dx::showConversation(string playerUid,int serverId){
    
    [UIApplication sharedApplication].keyWindow.backgroundColor = [UIColor whiteColor];
    //初始化KCMainViewController
    ChatMessageViewController *messageView = [[ChatMessageViewController alloc] init];
    
    GetServerIP* faqUrl = [GetServerIP getFaqService];
    //serverId
    faqUrl.serverId =serverId;
    messageView.type = @"1";
    NSString* NSuserId = elvaParseCString(playerUid.c_str());
    NSString *userName = faqUrl.userName;
    if(userName != nil){
        [messageView initParamsWithUserName:userName UserId:NSuserId Title:@"ElvaChatService"];
    }else{
        [messageView initParamsWithUserName:@"anonymous" UserId:NSuserId Title:@"ElvaChatService"];
    }
    //设置自定义控制器的大小和window相同，位置为（0，0）
    messageView.view.frame=[UIApplication sharedApplication].keyWindow.bounds;
    //设置此控制器为window的根控制器
    [[UIApplication sharedApplication].keyWindow.rootViewController presentViewController:messageView animated:YES completion:^{
        nil;
    }];
    
    
}
#pragma mark - 设置showConversation带config
void ECServiceCocos2dx::showConversation(string playerUid,int serverId,cocos2d::CCDictionary *config){
    //把json 转成 nsstring
    NSString * jsonString = elvaParseConfig(config);
    GetServerIP* faqUrl = [GetServerIP getFaqService];
    faqUrl.customerData = jsonString;
    //serverId
    faqUrl.serverId =serverId;
    
    [UIApplication sharedApplication].keyWindow.backgroundColor = [UIColor whiteColor];
    //初始化KCMainViewController
    ChatMessageViewController *messageView = [[ChatMessageViewController alloc] init];
    messageView.type = @"1";
    NSString* NSuserId = elvaParseCString(playerUid.c_str());
    NSString *userName = faqUrl.userName;
    if(userName != nil){
        [messageView initParamsWithUserName:userName UserId:NSuserId Title:@"ElvaChatService"];
    }else{
        [messageView initParamsWithUserName:@"anonymous" UserId:NSuserId Title:@"ElvaChatService"];
    }
    
    //设置自定义控制器的大小和window相同，位置为（0，0）
    messageView.view.frame=[UIApplication sharedApplication].keyWindow.bounds;
    //设置此控制器为window的根控制器
    [[UIApplication sharedApplication].keyWindow.rootViewController presentViewController:messageView animated:YES completion:^{
        nil;
    }];
    
}
