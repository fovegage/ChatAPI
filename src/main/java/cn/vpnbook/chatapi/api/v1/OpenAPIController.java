package cn.vpnbook.chatapi.api.v1;

import cn.vpnbook.chatapi.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping(value = "v1")
public class OpenAPIController {

    @Autowired
    private ChatService chatService;

    @GetMapping(value = "chat-process", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamCompletions(@RequestParam("prompt") String prompt) {
        log.info(prompt);
        return chatService.send(prompt);
    }
}
