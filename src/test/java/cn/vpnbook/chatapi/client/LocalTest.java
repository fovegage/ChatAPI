package cn.vpnbook.chatapi.client;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Collections;

public class LocalTest {
    private WebClient webClient;

    public void client() {
        this.webClient = WebClient.builder()
//                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
    }

    public void streamChatGPTData(String name) {
        String uri = "http://127.0.0.1:8080/v1/chat-process";

        webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(String.class)
                .doOnError(throwable -> {
                    // 错误处理逻辑
                    System.err.println("An error occurred during subscription: " + throwable.getMessage());
                })
                .onErrorResume(throwable -> {
                    // 在这里处理错误，并返回一个备用的Flux或默认值
                    System.err.println("An error occurred: " + throwable.getMessage());
                    return Flux.empty(); // 返回一个空的Flux作为备用
                })

                .subscribe(response -> {
                    // 处理正常响应
                    System.out.println("Response: " + response);
                });
    }

    public static void main(String[] args) {
        LocalTest client = new LocalTest();
        client.client();
        client.streamChatGPTData("");
    }
}
