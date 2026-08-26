package com.quiz.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionResponseDTO {
    private Long questionId;
    private String questionText;
    private List<OptionResponseDTO> options;
}
