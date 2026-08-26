package com.quiz.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminQuizDetailDTO {
    private Long quizId;
    private String title;
    private String topic;
    private List<AdminQuestionDTO> questions;
}
