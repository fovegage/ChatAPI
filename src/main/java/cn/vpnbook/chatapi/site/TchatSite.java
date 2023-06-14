package cn.vpnbook.chatapi.site;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;

@Component
public class TchatSite {
    /*
        http://vip.tchat.asia:666
     */

    private WebClient webClient;

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
    }

    public String buildBody(String prompt, String systemMessage) {
        JSONObject params = new JSONObject();
        params.put("prompt", prompt);

        JSONObject options = new JSONObject();
        options.put("conversationId", java.util.UUID.randomUUID());
        options.put("parentMessageId", java.util.UUID.randomUUID());
        params.put("options", options);

        JSONObject config = new JSONObject();
        config.put("temperature", 0.8);
        config.put("topP", 1);
        config.put("apiType", 0);
        config.put("model", "gpt-3.5-turbo");
        config.put("maxContextCount", 3);
        config.put("online", false);
        params.put("config", config);

        params.put("systemMessage", "You are ChatGPT, a large language model trained by OpenAI. Follow the user's instructions carefully. Respond using markdown.");

        return params.toJSONString();
    }

    public Flux<String> getResponseStream(String prompt, String systemMessage) {
        String apiKey = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJsb2dpblR5cGUiOiJsb2dpbiIsImxvZ2luSWQiOiJ0X2NoYXRfdXNlcjoxNjY4NTMyOTMwNDA0MjcwMDgyIiwicm5TdHIiOiJjdGpPbkNWcUxYUUR6U29QbHN5NzZvRlZoNWFPMW5pNyIsInVzZXJJZCI6MTY2ODUzMjkzMDQwNDI3MDA4Mn0.puvzCFzpjgdI37u7lCAIgug8YPp5hS7MlwIsJtArrY0";
        String uri = "https://api.t-chat.cn:1500/api/chat-process";
        String body = buildBody(prompt, systemMessage);
        return webClient.post()
                .uri(uri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .bodyValue(body)
                .retrieve()
                .bodyToFlux(String.class)
                .doOnError(throwable -> {
                    System.err.println("An error occurred during subscription: " + throwable.getMessage());
                })
                .onErrorResume(throwable -> {
                    System.err.println("An error occurred: " + throwable.getMessage());
                    return Flux.empty();
                });
    }
}
