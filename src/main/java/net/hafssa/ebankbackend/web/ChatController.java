package net.hafssa.ebankbackend.web;

import net.hafssa.ebankbackend.agents.AIAgent;
import net.hafssa.ebankbackend.rag.DocumentIndexor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import javax.swing.text.Document;
import java.awt.*;

@RestController
@CrossOrigin("*")
public class ChatController {
    private AIAgent aiAgent;
    private DocumentIndexor indexor;
    private SimpleVectorStore vectorStore;
    //Avec ChatClient qu'on peut communiquer avec les llms
    public ChatController(AIAgent aiAgent, DocumentIndexor indexor){
        this.aiAgent=aiAgent;
        this.indexor = indexor;
    }
    @GetMapping("/chat")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public Flux<String> chat(@RequestParam(defaultValue = "hello") String query){
        if(vectorStore != null){
            return aiAgent.askAgent2(query, vectorStore);
        }
        return aiAgent.askAgent2(query);
    }

    @PostMapping(value = "/loadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public void loadFile(@RequestPart("file") MultipartFile file){
        this.vectorStore = indexor.loadFile(file);
    }
}
