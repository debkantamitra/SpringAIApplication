package debkanta.ai.SpringAIApplication.controller;

import debkanta.ai.SpringAIApplication.model.ChatAskRequestModel;
import debkanta.ai.SpringAIApplication.service.ChatService;
import debkanta.ai.SpringAIApplication.service.ImageService;
import org.springframework.ai.image.ImageResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;
    private final ImageService imageService;

    public ChatController(ChatService chatService, ImageService imageService) {
        this.chatService = chatService;
        this.imageService = imageService;
    }

    @PostMapping("ask")
    public String getChatResponse(@RequestBody ChatAskRequestModel chatAskRequestModel) {
        return chatService.getChatResponseWithOptions(chatAskRequestModel.getPrompt());
    }

    @GetMapping("generate-image")
    public String getGeneratedImage(@RequestParam String prompt) {
        return imageService.generateImage(prompt).getResult().getOutput().getUrl();
    }
}
