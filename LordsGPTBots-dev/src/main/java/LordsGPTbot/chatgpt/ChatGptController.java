package LordsGPTbot.chatgpt;

import com.fasterxml.jackson.databind.ObjectMapper;
import LordsGPTbot.config.OpenAiApiClient;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class ChatGptController {
    @Autowired
    private ObjectMapper jsonMapper;
    @Autowired
    private OpenAiApiClient client;

    private List<CompletionRequest.Message> jsonList = new ArrayList<>();

    private long sizeDialog = 0;

    public enum OpenAiService {
        GPT_3, GPT_3_5_TURBO;
    }
    public enum Role{
        user,
        assistant,
        system,
    }

    public String chatWithGpt(@NotEmpty String message)
            throws IOException, InterruptedException {

        jsonList.add(new CompletionRequest.Message(Role.user.toString(), message));

        var json = new ObjectMapper()
                .writeValueAsString(new CompletionRequest("gpt-3.5-turbo", jsonList));
        System.out.println(json);
        var responseBody = client.postToOpenAiApi(String.join(",", json),
                OpenAiApiClient.OpenAiService.GPT_3_5_TURBO);
        System.out.println(responseBody);
        var completionResponse = jsonMapper
                .readValue(responseBody, CompletionResponse.class);
        System.out.println(completionResponse);
        var answer = completionResponse.firstAnswer().orElseThrow().trim();
        System.out.println(answer);
        jsonList.add(new CompletionRequest.Message(Role.assistant.toString(), answer));
        sizeDialog++;
        return answer;
    }
    public void clearDialog() {
        if (jsonList != null)
            jsonList.clear();
    }
    
}
