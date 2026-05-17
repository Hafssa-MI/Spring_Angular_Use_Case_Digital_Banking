package net.hafssa.ebankbackend.agents;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.ToolCallbacks;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Arrays;

@Component
public class AIAgent {
    private ChatClient chatClient;
    //Avec ChatClient qu'on peut communiquer avec les llms
    public AIAgent(ChatClient.Builder builder, ChatMemory memory, ToolCallbackProvider tools){
        this.chatClient=builder
                .defaultSystem("Vous etes un assistant qui se charge de repondre qux questions des tilisateurs en fonction du context fourni , si aucun contexte est fourni reponds avec je ne sais pas")
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(memory).build(),
                        new SimpleLoggerAdvisor()
                )
                .defaultTools(tools)
                .build();
    }
    public String askAgent ( String query){
        return chatClient.prompt()
                .user(query)
                .call().content();
    }
    public Flux<String> askAgent2 ( String query){
        return chatClient.prompt()
                .user(query)
                .stream().content();
    }
    public Flux<String> askAgent2(String query, SimpleVectorStore vectorStore){
        return chatClient.prompt()
                .user(query)
                .advisors(new QuestionAnswerAdvisor(vectorStore))
                .stream().content();
    }
}
