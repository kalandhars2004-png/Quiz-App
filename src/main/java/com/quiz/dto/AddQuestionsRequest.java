package com.quiz.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddQuestionsRequest {
    private Long quizId;
    private List<QuestionRequest> questions;
}
