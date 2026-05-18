package com.rashmi.onlineexam.model;

public class ScheduledExam {

    private String examId;
    private String title;
    private String subject;
    private int durationMinutes;
    private String scheduledDate;
    private String scheduledTime;
    private int maxAttempts;
    private String status;

    public ScheduledExam() {}

    public ScheduledExam(String examId, String title, String subject,
                         int durationMinutes, String scheduledDate,
                         String scheduledTime, int maxAttempts, String status) {
        this.examId = examId;
        this.title = title;
        this.subject = subject;
        this.durationMinutes = durationMinutes;
        this.scheduledDate = scheduledDate;
        this.scheduledTime = scheduledTime;
        this.maxAttempts = maxAttempts;
        this.status = status;
    }

    // Getters and Setters
    public String getExamId() { return examId; }
    public void setExamId(String examId) { this.examId = examId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public String getScheduledDate() { return scheduledDate; }
    public void setScheduledDate(String scheduledDate) { this.scheduledDate = scheduledDate; }

    public String getScheduledTime() { return scheduledTime; }
    public void setScheduledTime(String scheduledTime) { this.scheduledTime = scheduledTime; }

    public int getMaxAttempts() { return maxAttempts; }
    public void setMaxAttempts(int maxAttempts) { this.maxAttempts = maxAttempts; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String toFileString() {
        return examId + "|" + title + "|" + subject + "|" + durationMinutes + "|" +
                scheduledDate + "|" + scheduledTime + "|" + maxAttempts + "|" + status;
    }
}