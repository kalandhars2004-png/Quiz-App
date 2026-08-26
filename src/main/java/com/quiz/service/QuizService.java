package com.quiz.service;

import com.quiz.dto.*;
import com.quiz.model.Question;
import com.quiz.model.QuestionOption;
import com.quiz.model.Quiz;
import com.quiz.model.QuizResponse;
import com.quiz.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuestionOptionRepository questionOptionRepository;
    private final QuizResponseRepository quizResponseRepository;
    private final ObjectMapper objectMapper;

    public List<QuizSummaryDTO> getQuizzesByTopic(TopicRequest request) {
        List<Quiz> quizzes;
        if (request.getTopic() != null && !request.getTopic().isEmpty()) {
            quizzes = quizRepository.findByTopic(request.getTopic());
        } else {
            quizzes = quizRepository.findAll();
        }
        List<QuizSummaryDTO> result = new ArrayList<>();
        for (Quiz quiz : quizzes) {
            int qCount = questionRepository.findByQuizId(quiz.getId()).size();
            result.add(QuizSummaryDTO.builder()
                    .quizId(quiz.getId())
                    .title(quiz.getTitle())
                    .questionCount(qCount)
                    .build());
        }
        return result;
    }

    public List<QuizSummaryDTO> getAllQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();
        List<QuizSummaryDTO> result = new ArrayList<>();
        for (Quiz quiz : quizzes) {
            int qCount = questionRepository.findByQuizId(quiz.getId()).size();
            result.add(QuizSummaryDTO.builder()
                    .quizId(quiz.getId())
                    .title(quiz.getTitle())
                    .questionCount(qCount)
                    .build());
        }
        return result;
    }

    public QuizDetailDTO getQuiz(GetQuizRequest request) {
        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + request.getQuizId()));

        List<Question> questions = questionRepository.findByQuizId(quiz.getId());
        List<QuestionResponseDTO> questionDTOs = new ArrayList<>();

        for (Question q : questions) {
            List<QuestionOption> options = questionOptionRepository
                    .findByQuestionIdOrderByOptionNumber(q.getId());

            List<OptionResponseDTO> optionDTOs = new ArrayList<>();
            for (QuestionOption opt : options) {
                optionDTOs.add(OptionResponseDTO.builder()
                        .optionNumber(opt.getOptionNumber())
                        .text(opt.getOptionText())
                        .build());
            }

            questionDTOs.add(QuestionResponseDTO.builder()
                    .questionId(q.getId())
                    .questionText(q.getQuestionText())
                    .options(optionDTOs)
                    .build());
        }

        return QuizDetailDTO.builder()
                .quizId(quiz.getId())
                .title(quiz.getTitle())
                .topic(quiz.getTopic())
                .questions(questionDTOs)
                .build();
    }

    public ResultDTO submitQuiz(SubmitQuizRequest request) {
        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + request.getQuizId()));

        try {
            String answersJson = objectMapper.writeValueAsString(request.getAnswers());
            QuizResponse response = QuizResponse.builder()
                    .quiz(quiz)
                    .userId(request.getUserId())
                    .answers(answersJson)
                    .build();
            quizResponseRepository.save(response);
        } catch (Exception e) {
            throw new RuntimeException("Error saving answers: " + e.getMessage());
        }

        return calculateResult(request.getQuizId(), request.getUserId(), request.getAnswers());
    }

    public ResultDTO getQuizResult(GetQuizResultRequest request) {
        QuizResponse response = quizResponseRepository
                .findByQuizIdAndUserId(request.getQuizId(), request.getUserId())
                .orElseThrow(() -> new RuntimeException("No response found for quizId: " + request.getQuizId() + " and userId: " + request.getUserId()));

        try {
            Map<Long, Integer> answers = objectMapper.readValue(
                    response.getAnswers(),
                    objectMapper.getTypeFactory().constructMapType(Map.class, Long.class, Integer.class)
            );
            return calculateResult(request.getQuizId(), request.getUserId(), answers);
        } catch (Exception e) {
            throw new RuntimeException("Error reading answers: " + e.getMessage());
        }
    }

    public List<ResultDTO> getResult(GetResultRequest request) {
        Long userId = request.getUserId();
        List<QuizResponse> responses = quizResponseRepository.findByUserId(userId);

        if (responses.isEmpty()) {
            throw new RuntimeException("No results found for userId: " + userId);
        }

        List<ResultDTO> results = new ArrayList<>();
        for (QuizResponse response : responses) {
            try {
                Map<Long, Integer> answers = objectMapper.readValue(
                        response.getAnswers(),
                        objectMapper.getTypeFactory().constructMapType(Map.class, Long.class, Integer.class)
                );
                results.add(calculateResult(response.getQuiz().getId(), userId, answers));
            } catch (Exception e) {
                continue;
            }
        }
        return results;
    }

    public List<UserQuizResultDTO> getUserQuizResults(Long userId) {
        List<QuizResponse> responses = quizResponseRepository.findByUserId(userId);
        List<UserQuizResultDTO> result = new ArrayList<>();

        for (QuizResponse response : responses) {
            try {
                Map<Long, Integer> answers = objectMapper.readValue(
                        response.getAnswers(),
                        objectMapper.getTypeFactory().constructMapType(Map.class, Long.class, Integer.class)
                );
                ResultDTO fullResult = calculateResult(response.getQuiz().getId(), userId, answers);
                result.add(UserQuizResultDTO.builder()
                        .quizId(fullResult.getQuizId())
                        .title(fullResult.getTitle())
                        .score(fullResult.getScore())
                        .total(fullResult.getTotal())
                        .percentage(fullResult.getPercentage())
                        .build());
            } catch (Exception e) {
                continue;
            }
        }
        return result;
    }

    private ResultDTO calculateResult(Long quizId, Long userId, Map<Long, Integer> answers) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        List<Question> questions = questionRepository.findByQuizId(quizId);
        int score = 0;
        List<QuestionResultDTO> results = new ArrayList<>();

        for (Question q : questions) {
            Integer userAnswer = answers.get(q.getId());
            QuestionOption correctOpt = q.getCorrectOption();
            int correctNumber = correctOpt.getOptionNumber();

            String correctText = correctOpt.getOptionText();
            String userAnswerText = "";
            boolean isCorrect = false;

            if (userAnswer != null) {
                List<QuestionOption> allOptions = questionOptionRepository
                        .findByQuestionIdOrderByOptionNumber(q.getId());
                for (QuestionOption opt : allOptions) {
                    if (opt.getOptionNumber().equals(userAnswer)) {
                        userAnswerText = opt.getOptionText();
                        break;
                    }
                }
                isCorrect = userAnswer.equals(correctNumber);
                if (isCorrect) {
                    score++;
                }
            }

            results.add(QuestionResultDTO.builder()
                    .questionId(q.getId())
                    .questionText(q.getQuestionText())
                    .yourAnswer(userAnswer)
                    .yourAnswerText(userAnswerText)
                    .correctAnswer(correctNumber)
                    .correctAnswerText(correctText)
                    .isCorrect(isCorrect)
                    .build());
        }

        int total = questions.size();
        double percentage = total > 0 ? (score * 100.0 / total) : 0.0;

        return ResultDTO.builder()
                .quizId(quizId)
                .title(quiz.getTitle())
                .userId(userId)
                .score(score)
                .total(total)
                .percentage(Math.round(percentage * 100.0) / 100.0)
                .results(results)
                .build();
    }
}
