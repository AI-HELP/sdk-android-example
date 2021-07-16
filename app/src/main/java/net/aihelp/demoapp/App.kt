package net.aihelp.demoapp

import android.app.Application
import android.widget.Toast
import androidx.multidex.MultiDexApplication
import net.aihelp.init.AIHelpSupport
import net.aihelp.ui.listener.OnAIHelpInitializedCallback

class App : MultiDexApplication(), OnAIHelpInitializedCallback {
    override fun onCreate() {
        super.onCreate()

        AIHelpSupport.enableLogging(true)
        AIHelpSupport.init(
                this,
                BuildConfig.THIS_IS_YOUR_APP_KEY,
                BuildConfig.THIS_IS_YOUR_DOMAIN,
                BuildConfig.THIS_IS_YOUR_APP_ID
        )
        AIHelpSupport.setOnAIHelpInitializedCallback(this)
        Toast.makeText(this, "Be patient, loading AIHelp...", Toast.LENGTH_SHORT).show()
    }

    override fun onAIHelpInitialized() {
        Toast.makeText(applicationContext, "Everything is prepared now.", Toast.LENGTH_SHORT).show()
        AIHelpSupport.enableLogging(true)

    }
}