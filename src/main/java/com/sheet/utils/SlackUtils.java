package com.sheet.utils;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
@Slf4j
public class SlackUtils {

    private Slack slack = Slack.getInstance();

    @Value("${coffeespace.slack.token}")
    private String slackToken;

    @Async
    public void send(String message, String channelName, Boolean isBlockString) {
        try {
            log.info("Invoke send method.");
            MethodsClient methods = slack.methods(slackToken);
            ChatPostMessageRequest chatPostMessageRequest = ChatPostMessageRequest.builder()
                    .channel(channelName)
                    .build();
            if (isBlockString) {
                chatPostMessageRequest.setBlocksAsString(message);
            } else {
                chatPostMessageRequest.setText(message);
            }
            methods.chatPostMessage(chatPostMessageRequest);
        } catch (Exception ex) {
            log.error("cannot send slack notification with error: {} message: {}", ex.getLocalizedMessage(), message);
        }
    }
}
