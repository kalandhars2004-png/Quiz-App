package com.quiz.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequest {
    private String questionText;
    private List<OptionRequest> options;
    private Integer correctOptionNumber;
}
