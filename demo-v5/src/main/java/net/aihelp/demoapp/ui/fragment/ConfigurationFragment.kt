package net.aihelp.demoapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import net.aihelp.config.LoginConfig
import net.aihelp.config.UserConfig
import net.aihelp.demoapp.R
import net.aihelp.event.Acknowledgment
import net.aihelp.event.AsyncEventListener
import net.aihelp.event.EventType
import net.aihelp.init.AIHelpSupport
import org.json.JSONObject
import java.util.UUID

class ConfigurationFragment : Fragment(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AIHelpSupport.registerAsyncEventListener(
            EventType.USER_LOGIN
        ) { eventData, _ ->
            activity?.runOnUiThread {
                val msgData = JSONObject(eventData)
                val message = msgData.optString("message")
                Toast.makeText(context, "Login Result: $message", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_configuration, container, false)
        root.findViewById<View>(R.id.btn_login).setOnClickListener(this)
        root.findViewById<View>(R.id.btn_logout).setOnClickListener(this)
        root.findViewById<View>(R.id.btn_update_lan).setOnClickListener(this)
        return root
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_login -> updateUserInfo()
            R.id.btn_logout -> AIHelpSupport.resetUserInfo()
            R.id.btn_update_lan -> AIHelpSupport.updateSDKLanguage("ja")
        }
    }

    private fun updateUserInfo() {
        val customData = JSONObject()
        try {
            customData.put("level", 34)
            customData.put("total_recharge", 300)
            customData.put("remaining", 56)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val userConfig = UserConfig.Builder()
            .setServerId("SERVER ID")
            .setUserName("USER NAME")
            .setUserTags("pay1,s1,vip2")
            .setCustomData(customData.toString())
            .build()

        val loginConfig = LoginConfig.Builder()
            .setUserId(UUID.randomUUID().toString())
            .setUserConfig(userConfig)
            .build()
        AIHelpSupport.login(loginConfig)
    }
}