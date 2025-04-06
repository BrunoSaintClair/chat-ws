package dev.chat_ws.Chat;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/sendMessage/{roomTitle}") // url que o client vai usar para enviar a mensagem -> /app/sendMessage/{roomTitle}
    @SendTo("/topic/messages/{roomTitle}") // o retorno dessa função irá para todos os clients presentes nessa url
    public MessageModel sendMessage(@DestinationVariable String roomTitle, MessageModel msg){
        return msg;
    }

}
