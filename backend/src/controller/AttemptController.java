package controller;

import model.ExamAttempt;
import service.AttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller exposing REST endpoints for Student Attempts.
 */
@CrossOrigin(origins = "*")
@RestController
public class AttemptController {

    private final AttemptService attemptService;

    @Autowired
    public AttemptController(AttemptService attemptService) {
        this.attemptService = attemptService;
    }

    @PostMapping("/student/exam-sessions/{sessionId}/start")
    public ResponseEntity<?> startAttempt(
            @PathVariable("sessionId") String sessionId,
            @RequestBody Map<String, String> payload) {
        try {
            String studentId = payload.get("studentId");
            if (studentId == null || studentId.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("studentId is required");
            }
            
            ExamAttempt attempt = attemptService.startAttempt(sessionId, studentId);
            return ResponseEntity.ok(attempt);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while starting the attempt.");
        }
    }

    @GetMapping("/student/attempts/{attemptId}")
    public ResponseEntity<?> getAttempt(@PathVariable("attemptId") String attemptId) {
        ExamAttempt attempt = attemptService.getAttemptById(attemptId);
        if (attempt != null) {
            return ResponseEntity.ok(attempt);
        }
        return ResponseEntity.status(404).body("Attempt not found");
    }

    @PostMapping("/student/attempts/{attemptId}/save")
    public ResponseEntity<?> saveAttempt(
            @PathVariable("attemptId") String attemptId,
            @RequestBody Map<String, String> payload) {
        try {
            String answers = payload.getOrDefault("answers", "");
            boolean success = attemptService.saveAttempt(attemptId, answers);
            if (success) {
                return ResponseEntity.ok("Progress saved successfully.");
            }
            return ResponseEntity.status(404).body("Attempt not found.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/student/attempts/{attemptId}/submit")
    public ResponseEntity<?> submitAttempt(
            @PathVariable("attemptId") String attemptId,
            @RequestBody Map<String, String> payload) {
        try {
            String answers = payload.getOrDefault("answers", "");
            boolean success = attemptService.submitAttempt(attemptId, answers);
            if (success) {
                return ResponseEntity.ok("Attempt submitted successfully.");
            }
            return ResponseEntity.status(404).body("Attempt not found.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/student/attempt-history")
    public ResponseEntity<List<ExamAttempt>> getAttemptHistory(@RequestParam("studentId") String studentId) {
        List<ExamAttempt> history = attemptService.getStudentHistory(studentId);
        return ResponseEntity.ok(history);
    }
}
