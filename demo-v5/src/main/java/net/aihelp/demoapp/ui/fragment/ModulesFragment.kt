package net.aihelp.demoapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import net.aihelp.config.ApiConfig
import net.aihelp.config.ConversationConfig
import net.aihelp.config.FaqConfig
import net.aihelp.config.OperationConfig
import net.aihelp.config.enums.ConversationIntent
import net.aihelp.config.enums.ShowConversationMoment
import net.aihelp.demoapp.R
import net.aihelp.event.EventType
import net.aihelp.init.AIHelpSupport
import org.json.JSONObject

class ModulesFragment : Fragment(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AIHelpSupport.registerAsyncEventListener(
            EventType.SESSION_OPEN
        ) { _, _ ->
            activity?.runOnUiThread {
                Toast.makeText(context, "AIHelp now is showing", Toast.LENGTH_SHORT).show()
            }
            AIHelpSupport.unregisterAsyncEventListener(EventType.SESSION_OPEN)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_modules, container, false)
        root.findViewById<View>(R.id.btn_show_with_id).setOnClickListener(this)
        root.findViewById<View>(R.id.btn_show_with_config).setOnClickListener(this)
        return root
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_show_with_id -> showWithEntranceId()
            R.id.btn_show_with_config -> showWithApiConfig()
        }
    }

    private fun showWithEntranceId() {
        AIHelpSupport.show("This should be your entrance id")
    }

    private fun showWithApiConfig() {
        val apiConfig = ApiConfig.Builder()
            .setEntranceId("This should be your entrance id")
            .setWelcomeMessage("You can configure special welcome message for your end users at here.")
            .build()
        AIHelpSupport.show(apiConfig)
    }

}