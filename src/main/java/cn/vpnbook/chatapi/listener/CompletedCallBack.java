package cn.vpnbook.chatapi.listener;


import cn.vpnbook.chatapi.dto.Message;


public interface CompletedCallBack {

    /**
     * 完成回掉
     *
     * @param questions
     * @param sessionId
     * @param response
     */
    void completed(Message questions, String sessionId, String response);

    void fail(String sessionId);

}
