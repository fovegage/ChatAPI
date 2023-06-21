package cn.vpnbook.chatapi.listener;

import cn.vpnbook.chatapi.core.enmus.MessageType;
import cn.vpnbook.chatapi.dto.Message;
import cn.vpnbook.chatapi.dto.MessageRes;
import cn.vpnbook.chatapi.utils.R;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.Disposable;
import reactor.core.publisher.FluxSink;


@Slf4j
public class OpenAISubscriber implements Subscriber<String>, Disposable {
    private final FluxSink<String> emitter;
    private Subscription subscription;
    private final String sessionId;
    private final CompletedCallBack completedCallBack;
    private final StringBuilder sb;
    private final Message questions;
    private final MessageType messageType;

    public OpenAISubscriber(FluxSink<String> emitter, String sessionId, CompletedCallBack completedCallBack, Message questions) {
        this.emitter = emitter;
        this.sessionId = sessionId;
        this.completedCallBack = completedCallBack;
        this.questions = questions;
        this.sb = new StringBuilder();
        this.messageType = questions.getMessageType();
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(String data) {
        // -^&^- 如何处理特定的  拦截器
        log.info("OpenAI返回数据：{}", data);
        MessageRes res = MessageRes.builder().message("")
                .end(Boolean.FALSE)
                .messageType(messageType).build();
        res.setMessage(data);
        emitter.next(JSON.toJSONString(R.success(res)));
        sb.append(data);
        // 请求下一个数据项
        subscription.request(1);
    }

    @Override
    public void onError(Throwable t) {
        log.error("OpenAI返回数据异常：{}", t.getMessage());
        emitter.next(JSON.toJSONString(R.fail(t.getMessage())));
        emitter.error(t);
        completedCallBack.fail(sessionId);
    }

    @Override
    public void onComplete() {
        log.info("OpenAI返回数据完成");
        // 将根据该标志判断 sse.close()
        MessageRes res = MessageRes.builder().message("")
                .end(Boolean.TRUE)
                .messageType(messageType).build();
        emitter.next(JSON.toJSONString(R.success(res)));

        // 存储数据
        completedCallBack.completed(questions, sessionId, sb.toString());
        emitter.complete();
    }

    @Override
    public void dispose() {
        log.warn("OpenAI返回数据关闭");
        emitter.complete();
    }
}