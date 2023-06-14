package cn.vpnbook.chatapi.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class tchat {
    public static void main(String[] args) throws IOException {
        // http://vip.tchat.asia:666/index/1686643315760
        String apiEndpoint = "https://api.t-chat.cn:1500/api/chat-process";
        String apiKey = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJsb2dpblR5cGUiOiJsb2dpbiIsImxvZ2luSWQiOiJ0X2NoYXRfdXNlcjoxNjY4NTMyOTMwNDA0MjcwMDgyIiwicm5TdHIiOiJjdGpPbkNWcUxYUUR6U29QbHN5NzZvRlZoNWFPMW5pNyIsInVzZXJJZCI6MTY2ODUzMjkzMDQwNDI3MDA4Mn0.puvzCFzpjgdI37u7lCAIgug8YPp5hS7MlwIsJtArrY0";

        URL url = new URL(apiEndpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + apiKey);
        connection.setDoOutput(true);
        // conversationId   parentMessageId  使用该ID进行上下文
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

        connection.getOutputStream().write(requestBody.getBytes("UTF-8"));

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            // 在这里处理响应数据
        }
        reader.close();

        connection.disconnect();
    }
}
