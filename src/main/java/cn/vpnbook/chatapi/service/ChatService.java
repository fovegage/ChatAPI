package cn.vpnbook.chatapi.service;

import cn.vpnbook.chatapi.listener.CompletedCallBack;
import reactor.core.publisher.Flux;

public interface ChatService extends CompletedCallBack {
    Flux<String> send(String prompt);
}
