package cn.vpnbook.chatapi.service.impl;

import cn.vpnbook.chatapi.core.enmus.MessageType;
import cn.vpnbook.chatapi.core.enmus.UserType;
import cn.vpnbook.chatapi.dto.Message;
import cn.vpnbook.chatapi.listener.OpenAISubscriber;
import cn.vpnbook.chatapi.service.ChatService;
import cn.vpnbook.chatapi.site.TchatSite;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private TchatSite tchatSite;

    @Override
    public void completed(Message questions, String sessionId, String response) {
        // 结果回调，存储数据
        log.info("openai 处理成功 sessionId:{}, response:{}", sessionId, response);
    }

    @Override
    public void fail(String sessionId) {
        log.error("openai 处理失败 sessionId:{}", sessionId);
    }

    @Override
    public Flux<String> send(String prompt) {
        Message userMessage = new Message(MessageType.TEXT, UserType.USER, "");
        // todo: 策略模式  动态的判断  根据 check 结果
        return Flux.create(emitter -> {
            OpenAISubscriber openAISubscriber = new OpenAISubscriber(emitter, "", this, userMessage);
            Flux<String> stringFlux = tchatSite.getResponseStream(prompt, "");
            stringFlux.subscribe(openAISubscriber);
            emitter.onDispose(openAISubscriber);
        });
    }
}
