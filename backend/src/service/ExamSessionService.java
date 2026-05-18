package service;

import model.ExamSession;
import model.PracticeSession;
import model.TimedSession;
import util.FileHandler;
import util.IdGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for ExamSession management.
 * Handles business logic and file operations.
 */
@Service
public class ExamSessionService {

    private static final String FILE_PATH = "sessions.txt";

    /**
     * Reads all sessions from the file and converts them to ExamSession objects.
     */
    public List<ExamSession> getAllSessions() {
        List<String> lines = FileHandler.readFile(FILE_PATH);
        List<ExamSession> sessions = new ArrayList<>();

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split("\\|");
            
            // Format: sessionId|examId|examTitle|courseCode|startTime|endTime|durationMinutes|status|[sessionType]
            if (parts.length >= 8) {
                String sessionId = parts[0];
                String examId = parts[1];
                String examTitle = parts[2];
                String courseCode = parts[3];
                LocalDateTime startTime = LocalDateTime.parse(parts[4]);
                LocalDateTime endTime = LocalDateTime.parse(parts[5]);
                int durationMinutes = Integer.parseInt(parts[6]);
                String status = parts[7];
                
                String sessionType = (parts.length >= 9) ? parts[8] : "TIMED";

                ExamSession session;
                // Polymorphism in instantiation
                if ("PRACTICE".equalsIgnoreCase(sessionType)) {
                    session = new PracticeSession(sessionId, examId, examTitle, courseCode, startTime, endTime, durationMinutes, status);
                } else {
                    session = new TimedSession(sessionId, examId, examTitle, courseCode, startTime, endTime, durationMinutes, status);
                }
                sessions.add(session);
            }
        }
        return sessions;
    }

    /**
     * Gets all active sessions for students.
     */
    public List<ExamSession> getActiveSessions() {
        List<ExamSession> allSessions = getAllSessions();
        List<ExamSession> activeSessions = new ArrayList<>();
        
        for (ExamSession session : allSessions) {
            if ("ACTIVE".equalsIgnoreCase(session.getStatus()) && !session.isExpired()) {
                activeSessions.add(session);
            }
        }
        return activeSessions;
    }

    /**
     * Finds a specific session by its ID.
     */
    public ExamSession getSessionById(String sessionId) {
        return getAllSessions().stream()
                .filter(s -> s.getSessionId().equals(sessionId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Creates a new session and saves it to the file.
     */
    public ExamSession createSession(ExamSession session) {
        String newId = IdGenerator.generateId("S", FILE_PATH);
        session.setSessionId(newId);
        
        String line = formatSessionToLine(session);
        FileHandler.appendFile(FILE_PATH, line);
        
        return session;
    }

    /**
     * Updates an existing session.
     */
    public boolean updateSession(ExamSession updatedSession) {
        List<ExamSession> sessions = getAllSessions();
        boolean found = false;
        
        for (int i = 0; i < sessions.size(); i++) {
            if (sessions.get(i).getSessionId().equals(updatedSession.getSessionId())) {
                sessions.set(i, updatedSession);
                found = true;
                break;
            }
        }

        if (found) {
            saveAllSessions(sessions);
        }
        return found;
    }

    /**
     * Deletes a session by ID.
     */
    public boolean deleteSession(String sessionId) {
        List<ExamSession> sessions = getAllSessions();
        boolean removed = sessions.removeIf(s -> s.getSessionId().equals(sessionId));
        
        if (removed) {
            saveAllSessions(sessions);
        }
        return removed;
    }

    private void saveAllSessions(List<ExamSession> sessions) {
        List<String> lines = new ArrayList<>();
        for (ExamSession s : sessions) {
            lines.add(formatSessionToLine(s));
        }
        FileHandler.writeFile(FILE_PATH, lines);
    }

    private String formatSessionToLine(ExamSession s) {
        return String.join("|",
                s.getSessionId(),
                s.getExamId(),
                s.getExamTitle(),
                s.getCourseCode(),
                s.getStartTime() != null ? s.getStartTime().toString() : "null",
                s.getEndTime() != null ? s.getEndTime().toString() : "null",
                String.valueOf(s.getDurationMinutes()),
                s.getStatus(),
                s.getSessionType()
        );
    }
}
