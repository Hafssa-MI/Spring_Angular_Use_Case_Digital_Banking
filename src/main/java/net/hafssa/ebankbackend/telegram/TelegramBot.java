package net.hafssa.ebankbackend.telegram;

import jakarta.annotation.PostConstruct;
import net.hafssa.ebankbackend.agents.AIAgent;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TelegramBot extends TelegramLongPollingBot {
    @Value("${telegram.api.key}")
    private String telegramBotToken;
    private AIAgent aiAgent;
    public TelegramBot(AIAgent aiAgent){
        this.aiAgent=aiAgent;
    }
    @PostConstruct
    public void registerTelegrambot(){
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(this);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void onUpdateReceived(Update telegramRequest) {
        if(!telegramRequest.hasMessage()) return;
        String messageText=telegramRequest.getMessage().getText();
        try {
            sendTypingQuestion(telegramRequest.getMessage().getChatId());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        String answer =aiAgent.askAgent(messageText);
        try {
            sendTextMessage(telegramRequest.getMessage().getChatId(),answer);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return "esnetAIavg_bot";
    }

    @Override
    public String getBotToken() {
        return telegramBotToken;
    }
    private void sendTextMessage(long chatId,String text) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId),text);
        execute(sendMessage);
    }
    private void sendTypingQuestion(long chatId) throws TelegramApiException {
        SendChatAction sendChatAction= new SendChatAction();
        sendChatAction.setChatId(String.valueOf(chatId));
        sendChatAction.setAction(ActionType.TYPING);
        execute(sendChatAction);
    }
}
