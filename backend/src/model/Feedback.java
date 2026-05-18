package model;

public class Feedback {
    private String feedbackId;
    private String studentId;
    private String examId;
    private String type;
    private int rating;
    private String comment;
    private String status;
    private String createdDate;

    public Feedback() {}

    public Feedback(String feedbackId, String studentId, String examId, String type, int rating, String comment, String status, String createdDate) {
        this.feedbackId = feedbackId;
        this.studentId = studentId;
        this.examId = examId;
        this.type = type;
        this.rating = rating;
        this.comment = comment;
        this.status = status;
        this.createdDate = createdDate;
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String display() {
        return "Feedback ID: " + feedbackId + ", Student: " + studentId + ", Rating: " + rating;
    }
}
