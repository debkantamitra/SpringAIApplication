package debkanta.ai.SpringAIApplication.controller;

import debkanta.ai.SpringAIApplication.model.ChatAskRequestModel;
import debkanta.ai.SpringAIApplication.service.ChatService;
import debkanta.ai.SpringAIApplication.service.ImageService;
import debkanta.ai.SpringAIApplication.service.RecipeService;
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
    private final RecipeService recipeService;

    public ChatController(ChatService chatService, ImageService imageService, RecipeService recipeService) {
        this.chatService = chatService;
        this.imageService = imageService;
        this.recipeService = recipeService;
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
    public List<String> getGeneratedImages(@RequestParam String prompt,
                                           @RequestParam(defaultValue = "hd") String quality,
                                           @RequestParam(defaultValue = "1") int n,
                                           @RequestParam(defaultValue = "1024") int width,
                                           @RequestParam(defaultValue = "1024") int height) {
        long startTime = System.currentTimeMillis();
        ImageResponse imageResponse = imageService.generateImagesWithOptions(prompt,
                quality, width, height, n);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // Logging the time taken
        System.out.println("Time taken to generate images: " + duration + " ms");

        return imageResponse.getResults().stream()
                .map(result -> result.getOutput().getUrl())
                .toList();
    }

    @GetMapping("generate-recipe")
    public String getGeneratedRecipe(@RequestParam String ingredients,
                                     @RequestParam(defaultValue = "any") String cuisine,
                                     @RequestParam(defaultValue = "") String dietaryRestrictions) {

        return recipeService.generateRecipe(ingredients, cuisine, dietaryRestrictions);
    }
}
