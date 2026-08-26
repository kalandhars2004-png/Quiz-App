package com.quiz.repository;

import com.quiz.model.QuizResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizResponseRepository extends JpaRepository<QuizResponse, Long> {
    List<QuizResponse> findByQuizIdAndUserIdOrderByIdDesc(Long quizId, Long userId);
    List<QuizResponse> findByUserId(Long userId);
}
