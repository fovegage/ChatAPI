package cn.vpnbook.chatapi.listener;


import cn.vpnbook.chatapi.dto.Message;


public interface CompletedCallBack {
    void completed(Message questions, String sessionId, String response);

    void fail(String sessionId);

}
