package model;

import java.time.LocalDateTime;

public class PracticeSession extends ExamSession {

    public PracticeSession() {
        super();
    }

    public PracticeSession(String sessionId, String examId, String examTitle, String courseCode, 
                           LocalDateTime startTime, LocalDateTime endTime, int durationMinutes, String status) {
        super(sessionId, examId, examTitle, courseCode, startTime, endTime, durationMinutes, status);
    }

    @Override
    public boolean isExpired() {
        return "CLOSED".equalsIgnoreCase(getStatus());
    }

    @Override
    public String getSessionType() {
        return "PRACTICE";
    }
}
