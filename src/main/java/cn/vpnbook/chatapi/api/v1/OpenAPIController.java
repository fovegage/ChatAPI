package cn.vpnbook.chatapi.api.v1;

import cn.vpnbook.chatapi.core.enmus.MessageType;
import cn.vpnbook.chatapi.listener.OpenAISubscriber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping(value = "v1")
public class OpenAPIController {
    @GetMapping(value = "chat-process", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamCompletions() {
        log.info("start");
        return Flux.range(1, 100000)
                .map(n -> "Number: " + n);
//        return Flux.create(emitter ->{
//            OpenAISubscriber openAISubscriber = new OpenAISubscriber(emitter, "", null, null);
//        });
    }
}
