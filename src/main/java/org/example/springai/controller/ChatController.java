package org.example.springai.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.example.springai.config.PiiRedactor;
import org.example.springai.service.RagChatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Slf4j
@Controller
public class ChatController {

    private final RagChatService ragChatService;

    public ChatController(RagChatService ragChatService) {
        this.ragChatService = ragChatService;
    }

    @GetMapping("/")
    public String index() {
        return "chat";
    }

    @PostMapping("/chat")
    public String chat(@RequestParam String question, Model model, HttpSession session) {

        String conversationId = (String) session.getAttribute("conversationId");

        if (conversationId == null) {
            conversationId = UUID.randomUUID().toString();
            session.setAttribute("conversationId", conversationId);
        }

        String redactedQuestion = PiiRedactor.redact(question);

        log.info("Original question:  {}", question);
        log.info("Redacted question:  {}", redactedQuestion);

        model.addAttribute("question", question);
        model.addAttribute("answer", ragChatService.ask(PiiRedactor.redact(question), conversationId));
        // model.addAttribute("answer", ragChatService.ask(question, conversationId)); // before reduct
        return "chat";
    }

        @GetMapping("/summary")
    public String summary(Model model) {
        model.addAttribute("summary", ragChatService.getStructuredSummary());
        return "summary";
    }
}