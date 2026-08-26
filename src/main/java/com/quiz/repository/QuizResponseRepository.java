package com.quiz.repository;

import com.quiz.model.QuizResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizResponseRepository extends JpaRepository<QuizResponse, Long> {
    Optional<QuizResponse> findByQuizIdAndUserId(Long quizId, Long userId);
    List<QuizResponse> findByUserId(Long userId);
}
