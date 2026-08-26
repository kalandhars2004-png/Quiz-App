package com.quiz.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminQuestionDTO {
    private Long questionId;
    private String questionText;
    private List<AdminOptionDTO> options;
}
