package com.quiz.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizDetailDTO {
    private Long quizId;
    private String title;
    private String topic;
    private List<QuestionResponseDTO> questions;
}
