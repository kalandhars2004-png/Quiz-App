package com.quiz.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizSummaryDTO {
    private Long quizId;
    private String title;
    private String topic;
    private int questionCount;
}
