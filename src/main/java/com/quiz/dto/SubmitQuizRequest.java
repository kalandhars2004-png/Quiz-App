package com.quiz.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubmitQuizRequest {
    private Long quizId;
    private Long userId;
    private Map<Long, Integer> answers;
}
