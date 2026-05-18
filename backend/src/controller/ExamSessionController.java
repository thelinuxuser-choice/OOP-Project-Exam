package controller;

import model.ExamSession;
import model.PracticeSession;
import model.TimedSession;
import service.ExamSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Controller exposing REST endpoints for Exam Sessions.
 */
@CrossOrigin(origins = "*")
@RestController
public class ExamSessionController {

    private final ExamSessionService examSessionService;

    @Autowired
    public ExamSessionController(ExamSessionService examSessionService) {
        this.examSessionService = examSessionService;
    }

    // --- STUDENT ENDPOINTS ---

    @GetMapping("/student/exam-sessions")
    public ResponseEntity<List<ExamSession>> getActiveSessionsForStudent() {
        return ResponseEntity.ok(examSessionService.getActiveSessions());
    }

    // --- ADMIN ENDPOINTS ---

    @GetMapping("/admin/sessions")
    public ResponseEntity<List<ExamSession>> getAllSessions() {
        return ResponseEntity.ok(examSessionService.getAllSessions());
    }

    @PostMapping("/admin/sessions/create")
    public ResponseEntity<ExamSession> createSession(@RequestBody Map<String, String> payload) {
        try {
            String examId = payload.get("examId");
            String examTitle = payload.get("examTitle");
            String courseCode = payload.get("courseCode");
            LocalDateTime startTime = LocalDateTime.parse(payload.get("startTime"));
            LocalDateTime endTime = LocalDateTime.parse(payload.get("endTime"));
            int durationMinutes = Integer.parseInt(payload.get("durationMinutes"));
            String status = payload.getOrDefault("status", "UPCOMING");
            String sessionType = payload.getOrDefault("sessionType", "TIMED");

            ExamSession session;
            if ("PRACTICE".equalsIgnoreCase(sessionType)) {
                session = new PracticeSession(null, examId, examTitle, courseCode, startTime, endTime, durationMinutes, status);
            } else {
                session = new TimedSession(null, examId, examTitle, courseCode, startTime, endTime, durationMinutes, status);
            }

            ExamSession created = examSessionService.createSession(session);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/admin/sessions/update/{sessionId}")
    public ResponseEntity<String> updateSession(@PathVariable("sessionId") String sessionId, @RequestBody Map<String, String> payload) {
        try {
            ExamSession existing = examSessionService.getSessionById(sessionId);
            if (existing == null) {
                return ResponseEntity.status(404).body("Session not found");
            }

            if (payload.containsKey("examId")) existing.setExamId(payload.get("examId"));
            if (payload.containsKey("examTitle")) existing.setExamTitle(payload.get("examTitle"));
            if (payload.containsKey("courseCode")) existing.setCourseCode(payload.get("courseCode"));
            if (payload.containsKey("startTime")) existing.setStartTime(LocalDateTime.parse(payload.get("startTime")));
            if (payload.containsKey("endTime")) existing.setEndTime(LocalDateTime.parse(payload.get("endTime")));
            if (payload.containsKey("durationMinutes")) existing.setDurationMinutes(Integer.parseInt(payload.get("durationMinutes")));
            if (payload.containsKey("status")) existing.setStatus(payload.get("status"));

            boolean success = examSessionService.updateSession(existing);
            return success ? ResponseEntity.ok("Updated successfully") : ResponseEntity.status(500).body("Failed to update");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid payload");
        }
    }

    @PostMapping("/admin/sessions/delete/{sessionId}")
    public ResponseEntity<String> deleteSession(@PathVariable("sessionId") String sessionId) {
        boolean success = examSessionService.deleteSession(sessionId);
        if (success) {
            return ResponseEntity.ok("Session deleted successfully");
        }
        return ResponseEntity.status(404).body("Session not found");
    }
}
