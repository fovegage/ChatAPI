package cn.vpnbook.chatapi.client;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Slf4j
@SpringBootApplication
public class FluxTest {

    private WebClient webClient;

    public void client() {
//        SslContext sslContext = null;
//        try {
//            sslContext = SslContextBuilder
//                    .forClient()
//                    .trustManager(InsecureTrustManagerFactory.INSTANCE)
//                    .build();
//        } catch (SSLException e) {
//            throw new RuntimeException(e);
//        }
//        // 创建HttpClient对象，并设置代理
//        SslContext finalSslContext = sslContext;
//        HttpClient httpClient = HttpClient.create()
//                .secure(sslContextSpec -> sslContextSpec.sslContext(finalSslContext))
//                .tcpConfiguration(tcpClient -> tcpClient.proxy(proxy ->
//                        proxy.type(ProxyProvider.Proxy.HTTP).host("127.0.0.1").port(7890)));
//
//        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);
        this.webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
    }

    public void test1() {
        WebClient.Builder webClientBuilder = WebClient.builder();
        WebClient webClientTest = webClientBuilder.baseUrl("https://jsonplaceholder.typicode.com").build();
        webClientTest.get().uri("/users/1")
                .retrieve()
                .bodyToFlux(String.class)
                .subscribe(res -> {
                    System.out.println("result" + res);
                });
    }


    public void streamChatGPTData(String name) {
        JSONObject params = new JSONObject();
        params.put("prompt", "设计模式");

        JSONObject options = new JSONObject();
        options.put("conversationId", java.util.UUID.randomUUID());
        options.put("parentMessageId", java.util.UUID.randomUUID());
        params.put("options", Collections.singleton(options));

        JSONObject config = new JSONObject();
        config.put("temperature", 0.8);
        config.put("topP", 1);
        config.put("apiType", 0);
        config.put("model", "gpt-3.5-turbo");
        config.put("maxContextCount", 3);
        config.put("online", false);
        params.put("config", Collections.singleton(config));

        params.put("systemMessage", "You are ChatGPT, a large language model trained by OpenAI. Follow the user's instructions carefully. Respond using markdown.");

        String apiKey = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJsb2dpblR5cGUiOiJsb2dpbiIsImxvZ2luSWQiOiJ0X2NoYXRfdXNlcjoxNjY4NTMyOTMwNDA0MjcwMDgyIiwicm5TdHIiOiJjdGpPbkNWcUxYUUR6U29QbHN5NzZvRlZoNWFPMW5pNyIsInVzZXJJZCI6MTY2ODUzMjkzMDQwNDI3MDA4Mn0.puvzCFzpjgdI37u7lCAIgug8YPp5hS7MlwIsJtArrY0";
        String uri = "https://api.t-chat.cn:1500/api/chat-process";
        String requestBody = "{\n" +
                "    \"prompt\": \"设计模式\",\n" +
                "    \"options\": {\n" +
                "        \"conversationId\": \"" + java.util.UUID.randomUUID().toString() + "\",\n" +
                "        \"parentMessageId\": \"" + java.util.UUID.randomUUID().toString() + "\"\n" +
                "    },\n" +
                "    \"config\": {\n" +
                "        \"temperature\": 0.8,\n" +
                "        \"topP\": 1,\n" +
                "        \"apiType\": 0,\n" +
                "        \"model\": \"gpt-3.5-turbo\",\n" +
                "        \"maxContextCount\": 3,\n" +
                "        \"online\": false\n" +
                "    },\n" +
                "    \"systemMessage\": \"You are ChatGPT, a large language model trained by OpenAI. Follow the user's instructions carefully. Respond using markdown.\"\n" +
                "}";
        System.out.println(params.toJSONString());
        webClient.post()
                .uri(uri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
//                .accept(MediaType.APPLICATION_STREAM_JSON) // 设置接受流式JSON数据
                .bodyValue(requestBody)
                .retrieve()
                .bodyToFlux(String.class)
//                .onErrorResume(WebClientResponseException.class, ex -> {
//                    log.error("akkkkkk");
//                    HttpStatus status = ex.getStatusCode();
//                    String res = ex.getResponseBodyAsString();
//                    log.error("OpenAI API error: {} {}", status, res);
//                    return Mono.error(new RuntimeException(res));
//                })
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

    public Flux<String> streamChatGPTData() {
        String requestBody = "{\n" +
                "    \"prompt\": \"设计模式\",\n" +
                "    \"options\": {\n" +
                "        \"conversationId\": \"" + java.util.UUID.randomUUID().toString() + "\",\n" +
                "        \"parentMessageId\": \"" + java.util.UUID.randomUUID().toString() + "\"\n" +
                "    },\n" +
                "    \"config\": {\n" +
                "        \"temperature\": 0.8,\n" +
                "        \"topP\": 1,\n" +
                "        \"apiType\": 0,\n" +
                "        \"model\": \"gpt-3.5-turbo\",\n" +
                "        \"maxContextCount\": 3,\n" +
                "        \"online\": false\n" +
                "    },\n" +
                "    \"systemMessage\": \"You are ChatGPT, a large language model trained by OpenAI. Follow the user's instructions carefully. Respond using markdown.\"\n" +
                "}";
        WebClient webClient = WebClient.create();
        String apiKey = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJsb2dpblR5cGUiOiJsb2dpbiIsImxvZ2luSWQiOiJ0X2NoYXRfdXNlcjoxNjY4NTMyOTMwNDA0MjcwMDgyIiwicm5TdHIiOiJjdGpPbkNWcUxYUUR6U29QbHN5NzZvRlZoNWFPMW5pNyIsInVzZXJJZCI6MTY2ODUzMjkzMDQwNDI3MDA4Mn0.puvzCFzpjgdI37u7lCAIgug8YPp5hS7MlwIsJtArrY0";
        String uri = "https://api.t-chat.cn:1500/api/chat-process";
        return webClient.post()
                .uri(uri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .accept(MediaType.APPLICATION_STREAM_JSON) // 设置接受流式JSON数据
                .bodyValue(requestBody)
                .retrieve()
                .bodyToFlux(String.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    HttpStatus status = (HttpStatus) ex.getStatusCode();
                    String res = ex.getResponseBodyAsString();
                    log.error("OpenAI API error: {} {}", status, res);
                    return Mono.error(new RuntimeException(res));
                });
    }

    public static void main(String[] args) {
        SpringApplication.run(FluxTest.class, args);
        FluxTest client = new FluxTest();
//        client.test1();
        client.client();
        client.streamChatGPTData("");
//        log.info("test");
//        log.info(chatGPTDataStream.toString());
//        chatGPTDataStream.subscribe(data -> {
//            // 处理接收到的数据
//            System.out.println("Received data: " + data);
//        });
    }
}
