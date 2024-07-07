package net.aihelp.demoapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import net.aihelp.config.enums.PushPlatform
import net.aihelp.demoapp.R
import net.aihelp.demoapp.util.InitUtil
import net.aihelp.event.Acknowledgment
import net.aihelp.event.AsyncEventListener
import net.aihelp.event.EventType
import net.aihelp.init.AIHelpSupport
import org.json.JSONObject
import java.io.File

class OthersFragment : Fragment(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AIHelpSupport.registerAsyncEventListener(EventType.MESSAGE_ARRIVAL, object : AsyncEventListener {
            override fun onAsyncEventReceived(eventData: String, ack: Acknowledgment) {
                activity?.runOnUiThread {
                    val msgData = JSONObject(eventData)
                    val msgCount = msgData.optInt("msgCount")
                    Toast.makeText(
                        context,
                        "You have $msgCount unread messages.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_others, container, false)
        root.findViewById<View>(R.id.btn_unread_msg).setOnClickListener(this)
        root.findViewById<View>(R.id.btn_push).setOnClickListener(this)
        root.findViewById<View>(R.id.btn_network_check).setOnClickListener(this)
        root.findViewById<View>(R.id.btn_upload_log).setOnClickListener(this)
        root.findViewById<View>(R.id.btn_enable_logging).setOnClickListener(this)
        root.findViewById<View>(R.id.btn_sdk_version).setOnClickListener(this)
        return root
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_unread_msg -> {
                AIHelpSupport.fetchUnreadMessageCount()
            }

            R.id.btn_push -> AIHelpSupport.setPushTokenAndPlatform("YOUR_PUSH_TOKEN", PushPlatform.FIREBASE)

            R.id.btn_upload_log -> {
                Thread {
                    context?.let {
                        InitUtil.copyAssetAndWrite(it, "crash.log")
                        val dataFile = File(it.cacheDir, "crash.log")
                        AIHelpSupport.setUploadLogPath(dataFile.absolutePath)
                    }
                }.start()
            }

            R.id.btn_enable_logging -> AIHelpSupport.enableLogging(true)
            R.id.btn_sdk_version -> Toast.makeText(
                context,
                "AIHelp Version: ${AIHelpSupport.getSDKVersion()}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}