package com.quiz.controller;

import com.quiz.dto.*;
import com.quiz.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/createQuiz")
    public ResponseEntity<MessageResponse> createQuiz(@RequestBody CreateQuizRequest request) {
        return ResponseEntity.ok(adminService.createQuiz(request));
    }

    @PostMapping("/addQuestions")
    public ResponseEntity<MessageResponse> addQuestions(@RequestBody AddQuestionsRequest request) {
        return ResponseEntity.ok(adminService.addQuestions(request));
    }

    @PostMapping("/getQuizWithAnswers")
    public ResponseEntity<AdminQuizDetailDTO> getQuizWithAnswers(@RequestBody GetQuizRequest request) {
        return ResponseEntity.ok(adminService.getQuizWithAnswers(request.getQuizId()));
    }

    @GetMapping("/getAllQuizzes")
    public ResponseEntity<List<QuizSummaryDTO>> getAllQuizzes() {
        return ResponseEntity.ok(adminService.getAllQuizzes());
    }
}
