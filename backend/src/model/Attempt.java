package com.rashmi.onlineexam.model;

public class Attempt {

    private String attemptId;
    private String studentId;
    private String studentName;
    private String examId;
    private String startTime;
    private String endTime;
    private int score;
    private String status;

    public Attempt() {}

    public Attempt(String attemptId, String studentId, String studentName,
                   String examId, String startTime, String endTime,
                   int score, String status) {
        this.attemptId = attemptId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.examId = examId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.score = score;
        this.status = status;
    }

    // Getters and Setters
    public String getAttemptId() { return attemptId; }
    public void setAttemptId(String attemptId) { this.attemptId = attemptId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getExamId() { return examId; }
    public void setExamId(String examId) { this.examId = examId; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String toFileString() {
        return attemptId + "|" + studentId + "|" + studentName + "|" + examId + "|" +
                startTime + "|" + (endTime != null ? endTime : "") + "|" + score + "|" + status;
    }
}