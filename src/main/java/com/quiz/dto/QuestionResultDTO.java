package com.quiz.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionResultDTO {
    private Long questionId;
    private String questionText;
    private Integer yourAnswer;
    private String yourAnswerText;
    private Integer correctAnswer;
    private String correctAnswerText;
    private Boolean isCorrect;
}
