package LordsGPTbot.bot;

import LordsGPTbot.chatgpt.ChatGptController;
import LordsGPTbot.config.BotConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@Component
public class LordsBot extends TelegramLongPollingBot {

    @Autowired
    final BotConfig config;
    @Autowired
    private ChatGptController chatgpt;

    public LordsBot(BotConfig config) {
        this.config = config;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText())  {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String request = update.getMessage().getText();

            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/clear":
                    chatgpt.clearDialog();
                    sendMessage(chatId, "Диалог удален");
                    break;
                default:
                    try {
                        sendMessage(chatId, chatgpt.chatWithGpt(request));
                        break;
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
            }
        }

    }
    public void startCommandReceived(long chatId, String name) {
        String answer = "Привет, " + name + ", приятно познакомится! Меня создал Сергей К. чтобы я помогал тебе путник" +
                "\nДля сброса диалога введи: /clear";
        sendMessage(chatId, answer);
    }
    public void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String getBotUsername() {
        return config.getBotName();
    }
    @Override
    public String getBotToken() {
        return config.getToken();
    }

}
