package net.aihelp.demoapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import net.aihelp.common.Const
import net.aihelp.config.ConversationConfig
import net.aihelp.config.FaqConfig
import net.aihelp.config.OperationConfig
import net.aihelp.config.enums.ConversationIntent
import net.aihelp.config.enums.ShowConversationMoment
import net.aihelp.demoapp.R
import net.aihelp.init.AIHelpSupport


class ModulesFragment : Fragment(), View.OnClickListener {
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_modules, container, false)
        root.findViewById<View>(R.id.btn_robot).setOnClickListener(this)
        root.findViewById<View>(R.id.btn_manual).setOnClickListener(this)
        root.findViewById<View>(R.id.btn_all_sections).setOnClickListener(this)
        root.findViewById<View>(R.id.btn_single_section).setOnClickListener(this)
        root.findViewById<View>(R.id.btn_single_faq).setOnClickListener(this)
        root.findViewById<View>(R.id.btn_operation).setOnClickListener(this)
        return root
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_robot -> showBotSupport()
            R.id.btn_manual -> showManualSupport()
            R.id.btn_all_sections -> showAllFAQSections()
            R.id.btn_single_section -> showSingleSection()
            R.id.btn_single_faq -> showSingleFAQ()
            R.id.btn_operation -> showOperation()
        }
    }

    private fun showBotSupport() {
        val config = ConversationConfig.Builder()
                .setConversationIntent(ConversationIntent.BOT_SUPPORT)
                .setAlwaysShowHumanSupportButtonInBotPage(true)
                .build()
        AIHelpSupport.showConversation(config)
    }

    private fun showManualSupport() {
        val config = ConversationConfig.Builder()
                .setConversationIntent(ConversationIntent.HUMAN_SUPPORT)
                .setWelcomeMessage("You can configure special welcome message for your end users at here.")
                .build()
        AIHelpSupport.showConversation(config)
    }

    private fun showAllFAQSections() {
        val faqConversationConfig = ConversationConfig.Builder()
                .setConversationIntent(ConversationIntent.BOT_SUPPORT)
                .setAlwaysShowHumanSupportButtonInBotPage(true)
                .setWelcomeMessage("This is special configured welcome message for FAQ entrance.")
                .build()
        val config = FaqConfig.Builder()
                .setShowConversationMoment(ShowConversationMoment.AFTER_MARKING_UNHELPFUL)
                .setConversationConfig(faqConversationConfig)
                .build()
        AIHelpSupport.showAllFAQSections(config)
    }

    private fun showSingleSection() {
        val faqConversationConfig = ConversationConfig.Builder()
                .setConversationIntent(ConversationIntent.HUMAN_SUPPORT)
                .setWelcomeMessage("Hi, there, is there anything I can do for you?")
                .build()
        val config = FaqConfig.Builder()
                .setShowConversationMoment(ShowConversationMoment.ALWAYS)
                .setConversationConfig(faqConversationConfig)
                .build()
        AIHelpSupport.showFAQSection("Section ID", config)
    }

    private fun showSingleFAQ() {
        val faqConversationConfig = ConversationConfig.Builder()
                .setConversationIntent(ConversationIntent.HUMAN_SUPPORT)
                .setWelcomeMessage("Hi, there, is there anything I can do for you?")
                .build()
        val config = FaqConfig.Builder()
                .setShowConversationMoment(ShowConversationMoment.ONLY_IN_ANSWER_PAGE)
                .setConversationConfig(faqConversationConfig)
                .build()
        AIHelpSupport.showSingleFAQ("FAQ ID", config)
    }

    private fun showOperation() {
        val operationConversationConfig = ConversationConfig.Builder()
                .setAlwaysShowHumanSupportButtonInBotPage(true)
                .setWelcomeMessage("This is special configured welcome message for operation entrance.")
                .build()
        val config = OperationConfig.Builder()
                .setConversationTitle("Hi, there")
                .setSelectIndex(2)
                .setConversationConfig(operationConversationConfig)
                .build()
        AIHelpSupport.showOperation(config)

        val opConfigBuilder = OperationConfig.Builder()
        val conversationBuilder = ConversationConfig.Builder()
        conversationBuilder.setConversationIntent(ConversationIntent.HUMAN_SUPPORT)
        opConfigBuilder.setSelectIndex(Int.MAX_VALUE)
                .setConversationTitle("")
                .setConversationConfig(conversationBuilder.build())
    }
}