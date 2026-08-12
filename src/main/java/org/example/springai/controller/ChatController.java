package org.example.springai.controller;

import lombok.RequiredArgsConstructor;
import org.example.springai.service.RagChatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final RagChatService ragChatService;

    @GetMapping("/")
    public String index() {
        return "chat";
    }

    @PostMapping("/chat")
    public String chat(@RequestParam String question, Model model) {
        model.addAttribute("question", question);
        model.addAttribute("answer", ragChatService.ask(question));
        return "chat";
    }
}
