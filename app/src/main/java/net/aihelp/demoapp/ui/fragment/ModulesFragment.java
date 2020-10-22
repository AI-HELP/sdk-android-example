package net.aihelp.demoapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.aihelp.config.ConversationConfig;
import net.aihelp.config.FaqConfig;
import net.aihelp.config.OperationConfig;
import net.aihelp.config.enums.ConversationIntent;
import net.aihelp.config.enums.ShowContactUsMoment;
import net.aihelp.demoapp.R;
import net.aihelp.init.AIHelpSupport;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ModulesFragment extends Fragment implements View.OnClickListener {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_modules, container, false);

        root.findViewById(R.id.btn_robot).setOnClickListener(this);
        root.findViewById(R.id.btn_manual).setOnClickListener(this);

        root.findViewById(R.id.btn_all_sections).setOnClickListener(this);
        root.findViewById(R.id.btn_single_section).setOnClickListener(this);
        root.findViewById(R.id.btn_single_faq).setOnClickListener(this);

        root.findViewById(R.id.btn_operation).setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_robot:
                showBotSupport();
                break;
            case R.id.btn_manual:
                showManualSupport();
                break;

            case R.id.btn_all_sections:
                showAllFAQSections();
                break;
            case R.id.btn_single_section:
                showSingleSection();
                break;
            case R.id.btn_single_faq:
                showSingleFAQ();
                break;

            case R.id.btn_operation:
                showOperation();
                break;
        }
    }

    private void showBotSupport() {
        ConversationConfig config = new ConversationConfig.Builder()
                .setConversationIntent(ConversationIntent.BOT_SUPPORT)
                .setAlwaysShowHumanSupportButtonInBotPage(true)
                .build();
        AIHelpSupport.showConversation(config);
    }

    private void showManualSupport() {
        ConversationConfig config = new ConversationConfig.Builder()
                .setConversationIntent(ConversationIntent.HUMAN_SUPPORT)
                .setWelcomeMessage("You can configure special welcome message for your end users at here.")
                .build();
        AIHelpSupport.showConversation(config);
    }

    private void showAllFAQSections() {
        ConversationConfig faqConversationConfig = new ConversationConfig.Builder()
                .setConversationIntent(ConversationIntent.BOT_SUPPORT)
                .setAlwaysShowHumanSupportButtonInBotPage(true)
                .setWelcomeMessage("This is special configured welcome message for FAQ entrance.")
                .build();
        FaqConfig config = new FaqConfig.Builder()
                .setShowContactUsMoment(ShowContactUsMoment.AFTER_MARKING_UNHELPFUL)
                .setConversationConfig(faqConversationConfig)
                .build();
        AIHelpSupport.showAllFAQSections(config);
    }

    private void showSingleSection() {
        ConversationConfig faqConversationConfig = new ConversationConfig.Builder()
                .setConversationIntent(ConversationIntent.HUMAN_SUPPORT)
                .setWelcomeMessage("Hi, there, is there anything I can do for you?")
                .build();
        FaqConfig config = new FaqConfig.Builder()
                .setShowContactUsMoment(ShowContactUsMoment.ALWAYS)
                .setConversationConfig(faqConversationConfig)
                .build();
        AIHelpSupport.showFAQSection("Section ID", config);
    }

    private void showSingleFAQ() {
        ConversationConfig faqConversationConfig = new ConversationConfig.Builder()
                .setConversationIntent(ConversationIntent.HUMAN_SUPPORT)
                .setWelcomeMessage("Hi, there, is there anything I can do for you?")
                .build();
        FaqConfig config = new FaqConfig.Builder()
                .setShowContactUsMoment(ShowContactUsMoment.ONLY_IN_ANSWER_PAGE)
                .setConversationConfig(faqConversationConfig)
                .build();
        AIHelpSupport.showSingleFAQ("FAQ ID", config);
    }

    private void showOperation() {
        ConversationConfig operationConversationConfig = new ConversationConfig.Builder()
                .setAlwaysShowHumanSupportButtonInBotPage(true)
                .setWelcomeMessage("This is special configured welcome message for operation entrance.")
                .build();
        OperationConfig config = new OperationConfig.Builder()
                .setConversationTitle("Hi, there")
                .setSelectIndex(2)
                .setConversationConfig(operationConversationConfig)
                .build();
        AIHelpSupport.showOperation(config);
    }

}
