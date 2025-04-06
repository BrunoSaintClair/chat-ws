package dev.chat_ws.Chat;

import lombok.*;

@Data
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class MessageModel {
    private String sender;
    private String content;
}
