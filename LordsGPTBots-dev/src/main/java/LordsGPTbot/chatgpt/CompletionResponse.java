package LordsGPTbot.chatgpt;

import java.util.List;
import java.util.Optional;

public record CompletionResponse(
        String id,
        String object,
        Long created,
        String model,
        UsageStats usage,
        List<Choice> choices
        ) {

public Optional<String> firstAnswer() {
        if (choices == null || choices.get(0).message.content.isEmpty())
        return Optional.of("Извините на данный момент бот перегружен, повторите попытку позже1");
        return Optional.of(choices.get(0).message.content);
        }
        record UsageStats(
        int prompt_tokens,
        int completion_tokens,
        int total_tokens
        ) {}
        record Choice(
        Message message,
        String finish_reason,
        int index
        ) {}
        record Message(String role, String content) {}

        }

