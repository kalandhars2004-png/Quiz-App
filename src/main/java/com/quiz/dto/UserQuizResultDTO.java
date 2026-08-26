package com.quiz.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserQuizResultDTO {
    private Long quizId;
    private String title;
    private Integer score;
    private Integer total;
    private Double percentage;
}
