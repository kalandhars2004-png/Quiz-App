package com.quiz.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OptionRequest {
    private Integer optionNumber;
    private String text;
}
