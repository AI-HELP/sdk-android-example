package net.aihelp.demoapp

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.multidex.MultiDexApplication
import net.aihelp.event.Acknowledgment
import net.aihelp.event.AsyncEventListener
import net.aihelp.event.EventType
import net.aihelp.init.AIHelpSupport
import org.json.JSONObject

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        AIHelpSupport.enableLogging(true)
        AIHelpSupport.initialize(
            this,
            BuildConfig.THIS_IS_YOUR_DOMAIN,
            BuildConfig.THIS_IS_YOUR_APP_ID
        )
        AIHelpSupport.registerAsyncEventListener(
            EventType.INITIALIZATION
        ) { eventData, _ ->
            Handler(Looper.getMainLooper()).post {
                val initResult = JSONObject(eventData!!)
                if (initResult.optBoolean("isSuccess")) {
                    AIHelpSupport.enableLogging(true)
                    Toast.makeText(applicationContext, "Everything is prepared now.", Toast.LENGTH_SHORT).show()
                } else {
                    val message = initResult.optString("message")
                    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        Toast.makeText(this, "Be patient, loading AIHelp...", Toast.LENGTH_SHORT).show()
    }

}