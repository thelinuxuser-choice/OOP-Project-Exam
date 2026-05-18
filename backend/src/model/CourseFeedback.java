package model;

/**
 * CourseFeedback class demonstrating inheritance from Feedback.
 */
public class CourseFeedback extends Feedback {

    public CourseFeedback() {
        super();
        this.setType("COURSE");
    }

    public CourseFeedback(String feedbackId, String studentId, String courseId, int rating, String comment, String status, String createdDate) {
        super(feedbackId, studentId, courseId, "COURSE", rating, comment, status, createdDate);
    }

    /**
     * Overriding display method for polymorphism.
     */
    @Override
    public String display() {
        return "[COURSE] " + super.display() + " for Course: " + getExamId();
    }
}
