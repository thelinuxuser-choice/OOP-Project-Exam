package controller;

import model.Exam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ExamService;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for Exam management operations.
 * This provides JSON endpoints for the frontend to consume.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/exams")
public class ExamRestController {

    private final ExamService examService;

    @Autowired
    public ExamRestController(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping
    public ResponseEntity<List<Exam>> getAllExams() {
        List<Exam> exams = examService.getAllExams();
        return ResponseEntity.ok(exams);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addExam(@RequestBody Map<String, String> payload) {
        try {
            String title = payload.get("title");
            String courseCode = payload.get("courseCode");
            String duration = payload.get("duration");
            String marks = payload.get("marks");
            String date = payload.get("date");
            String status = payload.get("status");
            String instructions = payload.get("instructions");

            if (title == null || courseCode == null || duration == null || marks == null) {
                return ResponseEntity.badRequest().body("Missing required fields");
            }

            boolean success = examService.addExam(title, courseCode, duration, marks, date, status, instructions);
            if (success) {
                return ResponseEntity.ok("Exam added successfully");
            }
            return ResponseEntity.badRequest().body("Failed to add exam");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid exam format: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateExam(@PathVariable("id") String id, @RequestBody Map<String, String> payload) {
        try {
            String title = payload.get("title");
            String courseCode = payload.get("courseCode");
            String duration = payload.get("duration");
            String marks = payload.get("marks");
            String date = payload.get("date");
            String status = payload.get("status");
            String instructions = payload.get("instructions");

            boolean success = examService.updateExam(id, title, courseCode, duration, marks, date, status, instructions);
            if (success) {
                return ResponseEntity.ok("Exam updated successfully");
            }
            return ResponseEntity.status(404).body("Exam not found");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid exam format: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteExam(@PathVariable("id") String id) {
        boolean success = examService.deleteExam(id);
        if (success) {
            return ResponseEntity.ok("Exam deleted successfully");
        }
        return ResponseEntity.status(404).body("Exam not found");
    }
}
