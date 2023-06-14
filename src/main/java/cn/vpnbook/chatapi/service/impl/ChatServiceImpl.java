package cn.vpnbook.chatapi.service.impl;

import cn.vpnbook.chatapi.core.enmus.MessageType;
import cn.vpnbook.chatapi.core.enmus.UserType;
import cn.vpnbook.chatapi.dto.Message;
import cn.vpnbook.chatapi.listener.OpenAISubscriber;
import cn.vpnbook.chatapi.service.ChatService;
import cn.vpnbook.chatapi.site.TchatSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private TchatSite tchatSite;

    @Override
    public void completed(Message questions, String sessionId, String response) {

    }

    @Override
    public void fail(String sessionId) {

    }

    @Override
    public Flux<String> send(String prompt) {
        Message userMessage = new Message(MessageType.TEXT, UserType.USER, "");

        return Flux.create(emitter -> {
            OpenAISubscriber openAISubscriber = new OpenAISubscriber(emitter, "", this, userMessage);
            Flux<String> stringFlux = tchatSite.getResponseStream(prompt, "");
            stringFlux.subscribe(openAISubscriber);
            emitter.onDispose(openAISubscriber);
        });
    }
}
