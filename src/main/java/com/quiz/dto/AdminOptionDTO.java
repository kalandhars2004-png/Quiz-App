package com.quiz.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminOptionDTO {
    private Integer optionNumber;
    private String text;
    private Boolean isCorrect;
}
