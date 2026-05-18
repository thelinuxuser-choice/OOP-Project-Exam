package model;

import java.time.LocalDateTime;

public class Result {
    private String resultId;
    private String studentId;
    private String studentName;
    private String examId;
    private String examTitle;
    private String courseCode;
    private String sessionId;
    private int totalQuestions;
    private int correctAnswers;
    private int totalMarks;
    private int obtainedMarks;
    private double percentage;
    private String grade;
    private String status;
    private LocalDateTime submittedAt;
    private String feedback;

    public Result() {}

    public Result(String resultId, String studentId, String studentName, String examId, String examTitle,
                  String courseCode, String sessionId, int totalQuestions, int correctAnswers,
                  int totalMarks, int obtainedMarks, double percentage, String grade, String status,
                  LocalDateTime submittedAt, String feedback) {
        this.resultId = resultId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.examId = examId;
        this.examTitle = examTitle;
        this.courseCode = courseCode;
        this.sessionId = sessionId;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.totalMarks = totalMarks;
        this.obtainedMarks = obtainedMarks;
        this.percentage = percentage;
        this.grade = grade;
        this.status = status;
        this.submittedAt = submittedAt;
        this.feedback = feedback;
    }

    public String getResultId() { return resultId; }
    public void setResultId(String resultId) { this.resultId = resultId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getExamId() { return examId; }
    public void setExamId(String examId) { this.examId = examId; }

    public String getExamTitle() { return examTitle; }
    public void setExamTitle(String examTitle) { this.examTitle = examTitle; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }

    public int getCorrectAnswers() { return correctAnswers; }
    public void setCorrectAnswers(int correctAnswers) { this.correctAnswers = correctAnswers; }

    public int getTotalMarks() { return totalMarks; }
    public void setTotalMarks(int totalMarks) { this.totalMarks = totalMarks; }

    public int getObtainedMarks() { return obtainedMarks; }
    public void setObtainedMarks(int obtainedMarks) { this.obtainedMarks = obtainedMarks; }

    public double getPercentage() { return percentage; }
    public void setPercentage(double percentage) { this.percentage = percentage; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }

    public String toFileString() {
        String dateStr = submittedAt != null ? submittedAt.toString() : LocalDateTime.now().toString();
        return resultId + "|" + studentId + "|" + studentName + "|" + examId + "|" + examTitle + "|" +
               courseCode + "|" + sessionId + "|" + totalQuestions + "|" + correctAnswers + "|" +
               totalMarks + "|" + obtainedMarks + "|" + percentage + "|" + grade + "|" + status + "|" +
               dateStr + "|" + feedback;
    }

    public static Result fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length >= 15) {
            LocalDateTime submittedAt = null;
            try {
                if (parts[14] != null && !parts[14].equals("null") && !parts[14].isEmpty()) {
                    submittedAt = LocalDateTime.parse(parts[14]);
                }
            } catch (Exception e) {
                submittedAt = null;
            }
            return new Result(
                parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6],
                Integer.parseInt(parts[7]), Integer.parseInt(parts[8]),
                Integer.parseInt(parts[9]), Integer.parseInt(parts[10]),
                Double.parseDouble(parts[11]), parts[12], parts[13],
                submittedAt, parts.length > 15 ? parts[15] : ""
            );
        }
        return null;
    }

    public static String calculateGrade(double percentage) {
        if (percentage >= 85) return "A+";
        if (percentage >= 80) return "A";
        if (percentage >= 75) return "A-";
        if (percentage >= 70) return "B+";
        if (percentage >= 65) return "B";
        if (percentage >= 60) return "B-";
        if (percentage >= 55) return "C+";
        if (percentage >= 50) return "C";
        if (percentage >= 45) return "C-";
        if (percentage >= 40) return "D+";
        if (percentage >= 35) return "D";
        return "F";
    }
}
