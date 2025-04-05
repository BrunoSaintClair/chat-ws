package dev.chat_ws.Chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/sendMessage") // url que o client vai usar para enviar a mensagem -> /app/sendMessage
    @SendTo("/topic/messages") // o retorno dessa função irá para todos os clients presentes nessa url
    public MessageModel sendMessage(MessageModel msg){
        return msg;
    }

}
