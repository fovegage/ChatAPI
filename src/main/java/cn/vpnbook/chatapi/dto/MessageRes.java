package cn.vpnbook.chatapi.dto;

import cn.vpnbook.chatapi.core.enmus.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageRes {
    private MessageType messageType;
    private String message;
    private Boolean end;
}
