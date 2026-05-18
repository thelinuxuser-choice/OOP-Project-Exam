package model;

import java.time.LocalDateTime;

/**
 * OOP Concept: Inheritance
 * PracticeSession 'is-a' ExamSession. It inherits all properties from the parent class.
 */
public class PracticeSession extends ExamSession {

    public PracticeSession() {
        super();
    }

    public PracticeSession(String sessionId, String examId, String examTitle, String courseCode, 
                           LocalDateTime startTime, LocalDateTime endTime, int durationMinutes, String status) {
        super(sessionId, examId, examTitle, courseCode, startTime, endTime, durationMinutes, status);
    }

    /**
     * OOP Concept: Polymorphism (Method Overriding)
     * Practice sessions do not strictly expire based on time, they only expire when manually CLOSED.
     */
    @Override
    public boolean isExpired() {
        return "CLOSED".equalsIgnoreCase(getStatus());
    }

    @Override
    public String getSessionType() {
        return "PRACTICE";
    }
}
