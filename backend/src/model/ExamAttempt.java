package model;

import java.time.LocalDateTime;

public class ExamAttempt {
    private String attemptId;
    private String sessionId;
    private String examId;
    private String studentId;
    private LocalDateTime startTime;
    private LocalDateTime submitTime;
    private String answers; // Example format: "Q1=A,Q2=C"
    private String status; // IN_PROGRESS, SUBMITTED, TIMEOUT

    public ExamAttempt() {}

    public ExamAttempt(String attemptId, String sessionId, String examId, String studentId, 
                   LocalDateTime startTime, LocalDateTime submitTime, String answers, String status) {
        this.attemptId = attemptId;
        this.sessionId = sessionId;
        this.examId = examId;
        this.studentId = studentId;
        this.startTime = startTime;
        this.submitTime = submitTime;
        this.answers = answers;
        this.status = status;
    }

    // Getters and Setters
    public String getAttemptId() { return attemptId; }
    public void setAttemptId(String attemptId) { this.attemptId = attemptId; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getExamId() { return examId; }
    public void setExamId(String examId) { this.examId = examId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getSubmitTime() { return submitTime; }
    public void setSubmitTime(LocalDateTime submitTime) { this.submitTime = submitTime; }

    public String getAnswers() { return answers; }
    public void setAnswers(String answers) { this.answers = answers; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
