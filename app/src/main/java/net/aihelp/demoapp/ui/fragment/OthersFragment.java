package net.aihelp.demoapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.aihelp.config.enums.PushPlatform;
import net.aihelp.demoapp.R;
import net.aihelp.init.AIHelpSupport;
import net.aihelp.ui.listener.OnMessageCountArrivedCallback;
import net.aihelp.ui.listener.OnNetworkCheckResultCallback;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class OthersFragment extends Fragment implements View.OnClickListener {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_others, container, false);

        root.findViewById(R.id.btn_unread_msg).setOnClickListener(this);
        root.findViewById(R.id.btn_push).setOnClickListener(this);
        root.findViewById(R.id.btn_network_check).setOnClickListener(this);
        root.findViewById(R.id.btn_upload_log).setOnClickListener(this);
        root.findViewById(R.id.btn_enable_logging).setOnClickListener(this);
        root.findViewById(R.id.btn_sdk_version).setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_unread_msg:
                AIHelpSupport.startUnreadMessageCountPolling(new OnMessageCountArrivedCallback() {
                    @Override
                    public void onMessageCountArrived(int msgCount) {
                        Toast.makeText(getContext(), String.format("You have %s unread messages.", msgCount), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.btn_push:
                AIHelpSupport.setPushTokenAndPlatform("YOUR_PUSH_TOKEN", PushPlatform.FIREBASE);
                break;
            case R.id.btn_network_check:
                AIHelpSupport.setNetworkCheckHostAddress("aihelp.net", new OnNetworkCheckResultCallback() {
                    @Override
                    public void onNetworkCheckResult(String netLog) {
                        Toast.makeText(getContext(), String.format("Check Result: %s", netLog), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.btn_upload_log:
                AIHelpSupport.setUploadLogPath("YOUR LOG PATH");
                break;
            case R.id.btn_enable_logging:
                AIHelpSupport.enableLogging(true);
                break;
            case R.id.btn_sdk_version:
                Toast.makeText(getContext(), String.format("AIHelp Version: %s", AIHelpSupport.getSDKVersion()), Toast.LENGTH_SHORT).show();
                break;

        }
    }

}
