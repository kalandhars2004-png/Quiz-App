package com.quiz.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultDTO {
    private Long quizId;
    private String title;
    private Long userId;
    private Integer score;
    private Integer total;
    private Double percentage;
    private List<QuestionResultDTO> results;
}
