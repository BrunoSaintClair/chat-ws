package dev.chat_ws.Chat;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter @Setter
@NoArgsConstructor
public class MessageModel {
    private String sender;
    private String content;

    public MessageModel(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }
}
