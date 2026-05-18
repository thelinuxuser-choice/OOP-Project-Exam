package model;

import java.time.LocalDateTime;

public class TimedSession extends ExamSession {

    public TimedSession() {
        super();
    }

    public TimedSession(String sessionId, String examId, String examTitle, String courseCode, 
                        LocalDateTime startTime, LocalDateTime endTime, int durationMinutes, String status) {
        super(sessionId, examId, examTitle, courseCode, startTime, endTime, durationMinutes, status);
    }

    @Override
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(getEndTime());
    }

    @Override
    public String getSessionType() {
        return "TIMED";
    }
}
