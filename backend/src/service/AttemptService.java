package service;

import model.ExamAttempt;
import model.ExamSession;
import util.FileHandler;
import util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for handling Student Exam Attempts.
 */
@Service
public class AttemptService {

    private static final String FILE_PATH = "backend/attempts.txt";

    private final ExamSessionService examSessionService;

    @Autowired
    public AttemptService(ExamSessionService examSessionService) {
        this.examSessionService = examSessionService;
    }

    /**
     * Reads all attempts from the file.
     */
    public List<ExamAttempt> getAllAttempts() {
        List<String> lines = FileHandler.readFile(FILE_PATH);
        List<ExamAttempt> attempts = new ArrayList<>();

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split("\\|");
            
            // Format: attemptId|sessionId|examId|studentId|startTime|submitTime|answers|status
            if (parts.length >= 8) {
                ExamAttempt attempt = new ExamAttempt(
                    parts[0],
                    parts[1],
                    parts[2],
                    parts[3],
                    LocalDateTime.parse(parts[4]),
                    parts[5].equals("null") ? null : LocalDateTime.parse(parts[5]),
                    parts[6],
                    parts[7]
                );
                attempts.add(attempt);
            }
        }
        return attempts;
    }

    /**
     * Starts a new attempt for a student in a specific session.
     */
    public ExamAttempt startAttempt(String sessionId, String studentId) {
        ExamSession session = examSessionService.getSessionById(sessionId);
        
        if (session == null || !"ACTIVE".equalsIgnoreCase(session.getStatus()) || session.isExpired()) {
            throw new IllegalStateException("Session is not active or has expired.");
        }

        // Prevent duplicate active attempts
        List<ExamAttempt> allAttempts = getAllAttempts();
        for (ExamAttempt existing : allAttempts) {
            if (existing.getSessionId().equals(sessionId) && 
                existing.getStudentId().equals(studentId) && 
                "IN_PROGRESS".equalsIgnoreCase(existing.getStatus())) {
                throw new IllegalStateException("An active attempt already exists for this session.");
            }
        }

        ExamAttempt newAttempt = new ExamAttempt();
        newAttempt.setAttemptId(IdGenerator.generateId("A", FILE_PATH));
        newAttempt.setSessionId(sessionId);
        newAttempt.setExamId(session.getExamId());
        newAttempt.setStudentId(studentId);
        newAttempt.setStartTime(LocalDateTime.now());
        newAttempt.setSubmitTime(null);
        newAttempt.setAnswers("");
        newAttempt.setStatus("IN_PROGRESS");

        FileHandler.appendFile(FILE_PATH, formatAttemptToLine(newAttempt));
        return newAttempt;
    }

    public ExamAttempt getAttemptById(String attemptId) {
        return getAllAttempts().stream()
                .filter(a -> a.getAttemptId().equals(attemptId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Saves progress of an active attempt.
     */
    public boolean saveAttempt(String attemptId, String answers) {
        List<ExamAttempt> attempts = getAllAttempts();
        boolean found = false;

        for (int i = 0; i < attempts.size(); i++) {
            ExamAttempt attempt = attempts.get(i);
            if (attempt.getAttemptId().equals(attemptId)) {
                if (!"IN_PROGRESS".equalsIgnoreCase(attempt.getStatus())) {
                    throw new IllegalStateException("Cannot save a submitted or timed out attempt.");
                }
                
                // Auto-detect timeout
                ExamSession session = examSessionService.getSessionById(attempt.getSessionId());
                if (session != null && session.isExpired()) {
                    attempt.setStatus("TIMEOUT");
                    attempt.setSubmitTime(LocalDateTime.now());
                } else {
                    attempt.setAnswers(answers);
                }
                
                attempts.set(i, attempt);
                found = true;
                break;
            }
        }

        if (found) {
            saveAllAttempts(attempts);
        }
        return found;
    }

    /**
     * Submits an attempt.
     */
    public boolean submitAttempt(String attemptId, String answers) {
        List<ExamAttempt> attempts = getAllAttempts();
        boolean found = false;

        for (int i = 0; i < attempts.size(); i++) {
            ExamAttempt attempt = attempts.get(i);
            if (attempt.getAttemptId().equals(attemptId)) {
                if (!"IN_PROGRESS".equalsIgnoreCase(attempt.getStatus())) {
                    throw new IllegalStateException("Attempt is already submitted or timed out.");
                }
                
                ExamSession session = examSessionService.getSessionById(attempt.getSessionId());
                attempt.setAnswers(answers);
                attempt.setSubmitTime(LocalDateTime.now());
                
                if (session != null && session.isExpired()) {
                    attempt.setStatus("TIMEOUT");
                } else {
                    attempt.setStatus("SUBMITTED");
                }

                attempts.set(i, attempt);
                found = true;
                break;
            }
        }

        if (found) {
            saveAllAttempts(attempts);
        }
        return found;
    }

    public List<ExamAttempt> getStudentHistory(String studentId) {
        List<ExamAttempt> history = new ArrayList<>();
        for (ExamAttempt a : getAllAttempts()) {
            if (a.getStudentId().equals(studentId)) {
                history.add(a);
            }
        }
        return history;
    }

    private void saveAllAttempts(List<ExamAttempt> attempts) {
        List<String> lines = new ArrayList<>();
        for (ExamAttempt a : attempts) {
            lines.add(formatAttemptToLine(a));
        }
        FileHandler.writeFile(FILE_PATH, lines);
    }

    private String formatAttemptToLine(ExamAttempt a) {
        return String.join("|",
                a.getAttemptId(),
                a.getSessionId(),
                a.getExamId(),
                a.getStudentId(),
                a.getStartTime() != null ? a.getStartTime().toString() : "null",
                a.getSubmitTime() != null ? a.getSubmitTime().toString() : "null",
                a.getAnswers() != null ? a.getAnswers() : "",
                a.getStatus()
        );
    }
}
