package com.quiz.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetQuizResultRequest {
    private Long quizId;
    private Long userId;
}
