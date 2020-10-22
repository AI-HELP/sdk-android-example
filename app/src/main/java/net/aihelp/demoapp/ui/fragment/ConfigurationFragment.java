package net.aihelp.demoapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.aihelp.config.UserConfig;
import net.aihelp.demoapp.R;
import net.aihelp.init.AIHelpSupport;

import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ConfigurationFragment extends Fragment implements View.OnClickListener {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_configuration, container, false);

        root.findViewById(R.id.btn_login).setOnClickListener(this);
        root.findViewById(R.id.btn_logout).setOnClickListener(this);

        root.findViewById(R.id.btn_update_lan).setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                updateUserInfo();
                break;
            case R.id.btn_logout:
                AIHelpSupport.resetUserInfo();
                break;

            case R.id.btn_update_lan:
                AIHelpSupport.updateSDKLanguage("ja");
                break;
        }
    }

    private void updateUserInfo() {
        JSONObject customData = new JSONObject();
        try {
            customData.put("level", 34);
            customData.put("total_recharge", 300);
            customData.put("remaining", 56);
        } catch (Exception e) {
            e.printStackTrace();
        }

        UserConfig userConfig = new UserConfig.Builder()
                .setUserId("UID")
                .setServerId("SERVER ID")
                .setUserName("USER NAME")
                .setUserTags("pay1,s1,vip2")
                .setCustomData(customData.toString())
                .build();
        AIHelpSupport.updateUserInfo(userConfig);
    }

}
