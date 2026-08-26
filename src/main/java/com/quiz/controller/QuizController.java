package com.quiz.controller;

import com.quiz.dto.*;
import com.quiz.model.User;
import com.quiz.repository.UserRepository;
import com.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;
    private final UserRepository userRepository;

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest request) {
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .build();
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getQuizzes")
    public ResponseEntity<List<QuizSummaryDTO>> getQuizzes(@RequestParam(required = false) String topic) {
        TopicRequest request = new TopicRequest();
        request.setTopic(topic);
        return ResponseEntity.ok(quizService.getQuizzesByTopic(request));
    }

    @PostMapping("/getQuiz")
    public ResponseEntity<QuizDetailDTO> getQuiz(@RequestBody GetQuizRequest request) {
        return ResponseEntity.ok(quizService.getQuiz(request));
    }

    @PostMapping("/submitQuiz")
    public ResponseEntity<ResultDTO> submitQuiz(@RequestBody SubmitQuizRequest request) {
        return ResponseEntity.ok(quizService.submitQuiz(request));
    }

    @PostMapping("/getResult")
    public ResponseEntity<List<ResultDTO>> getResult(@RequestBody GetResultRequest request) {
        return ResponseEntity.ok(quizService.getResult(request));
    }

    @PostMapping("/getQuizResult")
    public ResponseEntity<ResultDTO> getQuizResult(@RequestBody GetQuizResultRequest request) {
        return ResponseEntity.ok(quizService.getQuizResult(request));
    }

    @PostMapping("/getUserQuizResults")
    public ResponseEntity<List<UserQuizResultDTO>> getUserQuizResults(@RequestBody UserIdRequest request) {
        return ResponseEntity.ok(quizService.getUserQuizResults(request.getUserId()));
    }
}
