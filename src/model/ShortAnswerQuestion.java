package model;

/**
 * Represents a Short Answer Question.
 * Demonstrates Inheritance by extending Question.
 */
public class ShortAnswerQuestion extends Question {
    private String correctAnswer;

    public ShortAnswerQuestion(String questionId, String examId, String questionText, int marks, String correctAnswer) {
        super(questionId, examId, questionText, marks);
        this.correctAnswer = correctAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toFileString() {
        // Format: questionId|examId|questionText|SHORT|NULL|correctAnswer|marks
        return getQuestionId() + "|" + getExamId() + "|" + getQuestionText() + "|SHORT|NULL|" + correctAnswer + "|" + getMarks();
    }

    @Override
    public void display() {
        System.out.println("Question ID: " + getQuestionId() + " | Exam ID: " + getExamId());
        System.out.println("Q: " + getQuestionText() + " (" + getMarks() + " marks)");
        System.out.println("Type: Short Answer");
    }

    @Override
    public boolean grade(String studentAnswer) {
        if (studentAnswer == null || studentAnswer.trim().isEmpty()) {
            return false;
        }
        
        // Keyword match for short answers
        // We split the correct answer into keywords and verify they all appear in the student's answer
        String[] keywords = correctAnswer.toLowerCase().split("\\s+");
        String lowerAnswer = studentAnswer.toLowerCase();
        
        for (String keyword : keywords) {
            if (!lowerAnswer.contains(keyword)) {
                return false; // Returns false if any keyword is missing
            }
        }
        return true;
    }
}
