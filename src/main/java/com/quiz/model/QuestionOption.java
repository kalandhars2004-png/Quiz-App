package com.quiz.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "question_option")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    private String optionText;

    private Integer optionNumber;
}
