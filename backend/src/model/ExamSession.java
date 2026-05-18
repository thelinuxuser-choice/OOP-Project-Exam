package model;

import java.time.LocalDateTime;

public abstract class ExamSession {
    private String sessionId;
    private String examId;
    private String examTitle;
    private String courseCode;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int durationMinutes;
    private String status; // UPCOMING, ACTIVE, CLOSED

    public ExamSession() {}

    public ExamSession(String sessionId, String examId, String examTitle, String courseCode, 
                       LocalDateTime startTime, LocalDateTime endTime, int durationMinutes, String status) {
        this.sessionId = sessionId;
        this.examId = examId;
        this.examTitle = examTitle;
        this.courseCode = courseCode;
        this.startTime = startTime;
        this.endTime = endTime;
        this.durationMinutes = durationMinutes;
        this.status = status;
    }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getExamId() { return examId; }
    public void setExamId(String examId) { this.examId = examId; }

    public String getExamTitle() { return examTitle; }
    public void setExamTitle(String examTitle) { this.examTitle = examTitle; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public abstract boolean isExpired();
    
    public abstract String getSessionType();
}
