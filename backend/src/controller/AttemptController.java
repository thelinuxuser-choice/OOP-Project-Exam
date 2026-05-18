package controller;

import model.ExamAttempt;
import model.Result;
import service.AttemptService;
import service.ResultService;
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
    private final ResultService resultService;

    @Autowired
    public AttemptController(AttemptService attemptService, ResultService resultService) {
        this.attemptService = attemptService;
        this.resultService = resultService;
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

    @PostMapping("/attempts/submit")
    public ResponseEntity<?> submitDirect(@RequestBody Map<String, Object> payload) {
        try {
            String sessionId = (String) payload.get("sessionId");
            String examId = (String) payload.get("examId");
            String studentId = (String) payload.get("studentId");
            String studentName = (String) payload.getOrDefault("studentName", "");
            String examTitle = (String) payload.getOrDefault("examTitle", "Unknown Exam");
            String courseCode = (String) payload.getOrDefault("courseCode", "Unknown");
            
            // Handle numeric values that might be Double from JSON
            Number totalQuestionsNum = (Number) payload.getOrDefault("totalQuestions", 0);
            Number correctAnswersNum = (Number) payload.getOrDefault("correctAnswers", 0);
            Number totalMarksNum = (Number) payload.getOrDefault("totalMarks", 100);
            
            int totalQuestions = totalQuestionsNum != null ? totalQuestionsNum.intValue() : 0;
            int correctAnswers = correctAnswersNum != null ? correctAnswersNum.intValue() : 0;
            int totalMarks = totalMarksNum != null ? totalMarksNum.intValue() : 100;
            
            @SuppressWarnings("unchecked")
            Map<String, String> answers = (Map<String, String>) payload.get("answers");
            
            if (studentId == null || sessionId == null) {
                return ResponseEntity.badRequest().body("sessionId and studentId are required");
            }
            
            // Start and immediately submit attempt
            ExamAttempt attempt = attemptService.startAttempt(sessionId, studentId);
            String answersJson = answers != null ? answers.toString() : "";
            attemptService.submitAttempt(attempt.getAttemptId(), answersJson);
            
            // Process result with correct data
            Result result = resultService.processExamSubmission(
                studentId, studentName, examId, examTitle, courseCode, sessionId,
                totalQuestions, correctAnswers, totalMarks,
                attempt.getSubmitTime()
            );
            
            return ResponseEntity.ok(Map.of(
                "message", "Attempt submitted successfully",
                "result", result
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to submit: " + e.getMessage());
        }
    }
}
