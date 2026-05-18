package model;

import java.time.LocalDateTime;

/**
 * OOP Concept: Inheritance
 * TimedSession 'is-a' ExamSession. It inherits all properties from the parent class.
 */
public class TimedSession extends ExamSession {

    public TimedSession() {
        super();
    }

    public TimedSession(String sessionId, String examId, String examTitle, String courseCode, 
                        LocalDateTime startTime, LocalDateTime endTime, int durationMinutes, String status) {
        // OOP Concept: Constructor chaining
        super(sessionId, examId, examTitle, courseCode, startTime, endTime, durationMinutes, status);
    }

    /**
     * OOP Concept: Polymorphism (Method Overriding)
     * Timed sessions strictly expire after the end time.
     */
    @Override
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(getEndTime());
    }

    @Override
    public String getSessionType() {
        return "TIMED";
    }
}
