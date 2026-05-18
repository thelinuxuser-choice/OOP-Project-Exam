package model;

/**
 * ExamFeedback class demonstrating inheritance from Feedback.
 */
public class ExamFeedback extends Feedback {

    public ExamFeedback() {
        super();
        this.setType("EXAM");
    }

    public ExamFeedback(String feedbackId, String studentId, String examId, int rating, String comment, String status, String createdDate) {
        super(feedbackId, studentId, examId, "EXAM", rating, comment, status, createdDate);
    }

    /**
     * Overriding display method for polymorphism.
     */
    @Override
    public String display() {
        return "[EXAM] " + super.display() + " for Exam: " + getExamId();
    }
}
