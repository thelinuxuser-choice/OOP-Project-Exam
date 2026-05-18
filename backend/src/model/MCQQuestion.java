package model;

import java.util.List;

public class MCQQuestion extends Question {
    private List<String> options;
    private String correctAnswer;

    public MCQQuestion(String questionId, String examId, String questionText, int marks, List<String> options, String correctAnswer) {
        super(questionId, examId, questionText, marks);
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toFileString() {
        // Format: questionId|examId|questionText|MCQ|options(comma separated)|correctAnswer|marks
        String optionsStr = String.join(",", options);
        return getQuestionId() + "|" + getExamId() + "|" + getQuestionText() + "|MCQ|" + optionsStr + "|" + correctAnswer + "|" + getMarks();
    }

    @Override
    public void display() {
        System.out.println("Question ID: " + getQuestionId() + " | Exam ID: " + getExamId());
        System.out.println("Q: " + getQuestionText() + " (" + getMarks() + " marks)");
        System.out.println("Options: ");
        for (int i = 0; i < options.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + options.get(i));
        }
    }

    @Override
    public boolean grade(String studentAnswer) {
        // Exact match required for MCQs (case-insensitive)
        return studentAnswer != null && studentAnswer.trim().equalsIgnoreCase(this.correctAnswer.trim());
    }
}
