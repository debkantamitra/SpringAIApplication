package debkanta.ai.SpringAIApplication.controller;

import debkanta.ai.SpringAIApplication.model.ChatAskRequestModel;
import debkanta.ai.SpringAIApplication.service.ChatService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("ask")
    public String getChatResponse(@RequestBody ChatAskRequestModel request) {
        return chatService.getChatResponse(request.getPrompt());
    }
}
