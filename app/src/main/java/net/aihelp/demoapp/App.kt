package net.aihelp.demoapp

import android.app.Application
import android.widget.Toast
import net.aihelp.init.AIHelpSupport
import net.aihelp.ui.listener.OnAIHelpInitializedCallback

class App : Application(), OnAIHelpInitializedCallback {
    override fun onCreate() {
        super.onCreate()
        AIHelpSupport.init(this,
                "THIS IS YOUR APP KEY",
                "THIS IS YOUR APP DOMAIN",
                "THIS IS YOUR APP ID",
        "THIS IS YOUR LANGUAGE(OPTIONAL)")
        AIHelpSupport.setOnAIHelpInitializedCallback(this)
        Toast.makeText(this, "Be patient, loading AIHelp...", Toast.LENGTH_SHORT).show()
    }

    override fun onAIHelpInitialized() {
        Toast.makeText(applicationContext, "Everything is prepared now.", Toast.LENGTH_SHORT).show()
    }
}