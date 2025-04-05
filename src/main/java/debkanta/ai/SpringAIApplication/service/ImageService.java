package debkanta.ai.SpringAIApplication.service;

import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    private final OpenAiImageModel openAiImageModel;

    public ImageService(OpenAiImageModel openAiImageModel) {
        this.openAiImageModel = openAiImageModel;
    }

    public ImageResponse generateImage(String prompt) {
        return openAiImageModel.call(
                new ImagePrompt(prompt));
    }

    public ImageResponse generateImagesWithOptions(String prompt,
                                                   String quality,
                                                   int width,
                                                   int height,
                                                   int n) {
        return openAiImageModel.call(
                new ImagePrompt(prompt, OpenAiImageOptions.builder()
                        .withModel("dall-e-2")
                        .withN(n)
                        .withWidth(width)
                        .withHeight(height)
                        .withQuality(quality)
                        .style("natural")
                        .build())
        );
    }
}
