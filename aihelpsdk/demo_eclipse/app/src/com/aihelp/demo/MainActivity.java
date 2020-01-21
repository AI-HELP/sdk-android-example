package com.aihelp.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ljoy.chatbot.sdk.ELvaChatServiceSdk;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity {

    EditText et_sw;
    String parseId = "";
    String userId = "u123";
    String userName = "kebi";
    String serverId = "s123";
    String gameName = "AIHelp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setInitCallback();
        et_sw = (EditText) findViewById(R.id.et_sw);
        ELvaChatServiceSdk.setEvaluateStar(5);
        ELvaChatServiceSdk.setName(gameName);
        ELvaChatServiceSdk.setUserId(userId);
        ELvaChatServiceSdk.setUserName(userName);
        ELvaChatServiceSdk.setServerId(serverId);
        ELvaChatServiceSdk.setSDKLanguage("zh_CN");
        ELvaChatServiceSdk.init(this
                ,"input your app key at here"
                ,"input your app domain at here"
                ,"input your app id at here");
    }

    public void onSWLanClick(View view) {//切换语言
        String lan = et_sw.getText().toString();
        ELvaChatServiceSdk.setSDKLanguage(lan);
    }

    public void onStartClick(View view) {
        //机器人界面
        HashMap<String, Object> config = getCustomData();
        ELvaChatServiceSdk.showElvaChatService(gameName, userName, userId, parseId, serverId, "1", config);
    }

    public void onOpSecondClick(View view) {
        //机器人界面
        HashMap<String, Object> config = getCustomData();
        ELvaChatServiceSdk.showElvaChatService(gameName, userName, userId, parseId, serverId, "0", config);
    }

    public void onOpClick(View view) {//运营界面(显示)
        HashMap<String, Object> config = getCustomData();
        ELvaChatServiceSdk.showOPList(gameName, userName, userId, parseId, serverId, "1", config, 999);
    }

    public void onOpClick2(View view) {//运营界面(不显示)
        HashMap<String, Object> config = getCustomData();
        ELvaChatServiceSdk.showOPList(gameName, userName, userId, parseId, serverId, "0", config, 999);
    }

    public void onVipChatClick(View view) {//人工界面
        HashMap<String, Object> config = getCustomData();
        ELvaChatServiceSdk.setUserName(userName);
        ELvaChatServiceSdk.showConversation(userId, serverId, config);
    }

    public void onShowFAQlist(View view) {//faq列表(机器人右上角显示)
        HashMap<String, Object> config = getCustomData();
        config.put("showContactButtonFlag", 1);//加入此参数，其中key是不可变的 FAQ右上角显示 (如果不想显示 需要删除此参数)
        config.put("showConversationFlag", 1);//加入此参数，其中key是不可变的 点击FAQ右上角后 进入机器人界面右上角是否显示 (如果不想显示 需要删除此参数)
        ELvaChatServiceSdk.setUserName(userName);
        ELvaChatServiceSdk.setUserId(userId);
        ELvaChatServiceSdk.setServerId(serverId);
        ELvaChatServiceSdk.showFAQs(config);
    }

    public void onShowFAQlist3(View view) {//faq列表(机器人右上角不显示)
        HashMap config = getCustomData();
        //加入此参数,其中key是不可变的 优先级最高 加上后faq右上角则永不显示
        //(如果想显示 需要删除此参数 并加入 config.put("showContactButtonFlag", 1);
//        config.put("hideContactButtonFlag", 1);
        //加入此参数，其中key是不可变的 FAQ右上角显示 (如果不想显示 需要删除此参数)
//        config.put("showContactButtonFlag", 1);
        ELvaChatServiceSdk.setUserName(userName);
        ELvaChatServiceSdk.setUserId(userId);
        ELvaChatServiceSdk.setServerId(serverId);
        ELvaChatServiceSdk.showFAQs(userName, userId, config);
    }

    public void onShowFAQlist2(View view) {//faq列表(人工)
        HashMap<String, Object> config = getCustomData();
        //加入此参数,其中key是不可变的 优先级最高 加上后faq右上角则永不显示
        //(如果想显示 需要删除此参数 并加入 config.put("showContactButtonFlag", 1);
//        config.put("hideContactButtonFlag", 1);
        config.put("showContactButtonFlag", 1);//加入此参数，其中key是不可变的 FAQ右上角显示 (如果不想显示 需要删除此参数)
        config.put("directConversation", 1);//加入此参数，其中key是不可变的 点击FAQ右上角后 直接会进入到人工客服页面(如果不加默认进入机器人界面)
        ELvaChatServiceSdk.setUserName(userName);
        ELvaChatServiceSdk.setUserId(userId);
        ELvaChatServiceSdk.setServerId(serverId);
        ELvaChatServiceSdk.showFAQs(userName, userId, config);
    }

    public void onShowFAQClick(View view) {//单条faq(显示)
        HashMap<String, Object> config = getCustomData();
        config.put("showContactButtonFlag", 1);//加入此参数，其中key是不可变的 FAQ右上角显示 (如果不想显示 需要删除此参数)
        config.put("showConversationFlag", 1);//加入此参数，其中key是不可变的 点击FAQ右上角后 进入机器人界面右上角是否显示 (如果不想显示 需要删除此参数)
        ELvaChatServiceSdk.setUserName(userName);
        ELvaChatServiceSdk.setUserId(userId);
        ELvaChatServiceSdk.setServerId(serverId);
        ELvaChatServiceSdk.showSingleFAQ("158", config);
    }

    public void onShowFAQClick2(View view) {//单条faq(不显示)
        HashMap<String, Object> config = getCustomData();
        config.put("hideContactButtonFlag", 1);//加入此参数，其中key是不可变的 FAQ右上角永不显示 (如果想显示 需要删除此参数)
        ELvaChatServiceSdk.setUserName(userName);
        ELvaChatServiceSdk.setUserId(userId);
        ELvaChatServiceSdk.setServerId(serverId);
        ELvaChatServiceSdk.showSingleFAQ("158", config);
    }

    public void onShowUrlBtnClick(View view) {//showUrl
        ELvaChatServiceSdk.showURL("https://www.aihelp.net/");
    }

    private HashMap<String, Object> getCustomData() {
        HashMap<String, Object> config = new HashMap<String, Object>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        ArrayList<String> tags = new ArrayList<String>();
        tags.add("vip1");//第一种方式 自定义 需要和后台保持一致(针对key形式)
        map.put("elva-tags", tags);
        map.put("udid", "123456789");//第二种方式自定义 不需要去后台配置(针对key-value形式)
        config.put("elva-custom-metadata", map);
        return config;
    }

    // 在调用初始化init方法之前，设置初始化回调函数
    public void setInitCallback() {
        ELvaChatServiceSdk.setOnInitializedCallback(new ELvaChatServiceSdk.OnInitializationCallback() {
            @Override
            public void onInitialized() {
                toast();
            }
        });
    }

    private void toast() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "Elva SDK init finish.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}