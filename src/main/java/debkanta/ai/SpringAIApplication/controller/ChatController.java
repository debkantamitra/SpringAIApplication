package debkanta.ai.SpringAIApplication.controller;

import debkanta.ai.SpringAIApplication.model.ChatAskRequestModel;
import debkanta.ai.SpringAIApplication.service.ChatService;
import debkanta.ai.SpringAIApplication.service.ImageService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.image.ImageResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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
    public void getGeneratedImage(HttpServletResponse response, @RequestParam String prompt) throws IOException {
       ImageResponse imageResponse = imageService.generateImage(prompt);
       String imgUrl = imageResponse.getResult().getOutput().getUrl();

       response.sendRedirect(imgUrl);
    }

    @GetMapping("generate-images")
    public List<String> getGeneratedImages(@RequestParam String prompt) {
        ImageResponse imageResponse = imageService.generateImagesWithOptions(prompt);

        return imageResponse.getResults().stream()
                .map(result -> result.getOutput().getUrl())
                .toList();
    }
}
