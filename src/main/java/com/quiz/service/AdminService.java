package com.quiz.service;

import com.quiz.dto.*;
import com.quiz.model.Question;
import com.quiz.model.QuestionOption;
import com.quiz.model.Quiz;
import com.quiz.repository.QuestionOptionRepository;
import com.quiz.repository.QuestionRepository;
import com.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuestionOptionRepository questionOptionRepository;

    public MessageResponse createQuiz(CreateQuizRequest request) {
        Quiz quiz = Quiz.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .topic(request.getTopic())
                .build();
        quizRepository.save(quiz);
        return MessageResponse.builder()
                .message("Quiz created successfully")
                .quizId(quiz.getId())
                .build();
    }

    public MessageResponse addQuestions(AddQuestionsRequest request) {
        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + request.getQuizId()));

        int count = 0;
        for (QuestionRequest qr : request.getQuestions()) {
            Question question = Question.builder()
                    .quiz(quiz)
                    .questionText(qr.getQuestionText())
                    .build();
            questionRepository.save(question);

            QuestionOption correctOption = null;
            List<QuestionOption> options = new ArrayList<>();

            for (OptionRequest or : qr.getOptions()) {
                QuestionOption option = QuestionOption.builder()
                        .question(question)
                        .optionText(or.getText())
                        .optionNumber(or.getOptionNumber())
                        .build();
                questionOptionRepository.save(option);
                options.add(option);

                if (or.getOptionNumber().equals(qr.getCorrectOptionNumber())) {
                    correctOption = option;
                }
            }

            if (correctOption == null) {
                throw new RuntimeException("correctOptionNumber " + qr.getCorrectOptionNumber() + " not found in options for question: " + qr.getQuestionText());
            }

            question.setCorrectOption(correctOption);
            questionRepository.save(question);
            count++;
        }

        return MessageResponse.builder()
                .message(count + " questions added successfully")
                .quizId(request.getQuizId())
                .build();
    }

    public List<QuizSummaryDTO> getAllQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();
        List<QuizSummaryDTO> result = new ArrayList<>();
        for (Quiz quiz : quizzes) {
            int qCount = questionRepository.findByQuizId(quiz.getId()).size();
            result.add(QuizSummaryDTO.builder()
                    .quizId(quiz.getId())
                    .title(quiz.getTitle())
                    .topic(quiz.getTopic())
                    .questionCount(qCount)
                    .build());
        }
        return result;
    }

    public AdminQuizDetailDTO getQuizWithAnswers(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + quizId));

        List<Question> questions = questionRepository.findByQuizId(quizId);
        List<AdminQuestionDTO> questionDTOs = new ArrayList<>();

        for (Question q : questions) {
            List<QuestionOption> options = questionOptionRepository
                    .findByQuestionIdOrderByOptionNumber(q.getId());

            Long correctOptionId = q.getCorrectOption() != null ? q.getCorrectOption().getId() : null;

            List<AdminOptionDTO> optionDTOs = new ArrayList<>();
            for (QuestionOption opt : options) {
                optionDTOs.add(AdminOptionDTO.builder()
                        .optionNumber(opt.getOptionNumber())
                        .text(opt.getOptionText())
                        .isCorrect(opt.getId().equals(correctOptionId))
                        .build());
            }

            questionDTOs.add(AdminQuestionDTO.builder()
                    .questionId(q.getId())
                    .questionText(q.getQuestionText())
                    .options(optionDTOs)
                    .build());
        }

        return AdminQuizDetailDTO.builder()
                .quizId(quiz.getId())
                .title(quiz.getTitle())
                .topic(quiz.getTopic())
                .questions(questionDTOs)
                .build();
    }
}
