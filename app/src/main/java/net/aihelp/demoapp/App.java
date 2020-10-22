package net.aihelp.demoapp;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import net.aihelp.init.AIHelpSupport;
import net.aihelp.ui.listener.OnAIHelpInitializedCallback;

public class App extends Application implements OnAIHelpInitializedCallback {

    @Override
    public void onCreate() {
        super.onCreate();

        AIHelpSupport.init(this,
                "THIS IS YOUR APP KEY",
                "THIS IS YOUR APP DOMAIN",
                "THIS IS YOUR APP ID",
                "THIS IS YOUR DEFAULT LANGUAGE(OPTIONAL)");
        AIHelpSupport.setOnAIHelpInitializedCallback(this);

        Toast.makeText(this, "Be patient, loading AIHelp...", Toast.LENGTH_SHORT).show();

    }

    public void onAIHelpInitialized() {
        Toast.makeText(getApplicationContext(), "Everything is prepared now.", Toast.LENGTH_SHORT).show();
    }

}
