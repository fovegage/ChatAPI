package cn.vpnbook.chatapi.site;

import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;

public abstract class AbstractSite {
    protected WebClient webClient;

    protected abstract void configureWebclient(WebClient.Builder builder);

    @PostConstruct
    public void init() {
        WebClient.Builder builder = WebClient.builder().defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        configureWebclient(builder);
        this.webClient = builder.build();
    }
}
