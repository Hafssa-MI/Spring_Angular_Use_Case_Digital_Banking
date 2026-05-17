package net.hafssa.ebankbackend.web;

import net.hafssa.ebankbackend.agents.AIAgent;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController

public class ChatController {
    private AIAgent aiAgent;
    //Avec ChatClient qu'on peut communiquer avec les llms
    public ChatController(AIAgent aiAgent){
        this.aiAgent=aiAgent;
    }
    @GetMapping("/chat")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public String chat (String query){
        return aiAgent.askAgent(query);
    }
}
