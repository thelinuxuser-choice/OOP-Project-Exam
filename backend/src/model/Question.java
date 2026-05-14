package model;

/**
 * Abstract base class representing a Question in the examination system.
 * Demonstrates Abstraction and Encapsulation.
 */
public abstract class Question {
    private String questionId;
    private String examId;
    private String questionText;
    private int marks;

    public Question(String questionId, String examId, String questionText, int marks) {
        this.questionId = questionId;
        this.examId = examId;
        this.questionText = questionText;
        this.marks = marks;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    /**
     * Converts the question object into a string format suitable for file storage.
     * @return A delimited string representing the question
     */
    public abstract String toFileString();

    /**
     * Displays the question details.
     */
    public abstract void display();

    /**
     * Grades the student's answer against the correct answer.
     * @param studentAnswer The answer provided by the student
     * @return true if the answer is correct, false otherwise
     */
    public abstract boolean grade(String studentAnswer);
}
