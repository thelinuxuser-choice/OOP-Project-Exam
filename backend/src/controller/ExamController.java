package controller;

import model.Exam;
import service.ExamService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class to expose Exam management operations via HTTP endpoints.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/exams")
public class ExamController {

    private final ExamService examService;

    @Autowired
    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addExam(@RequestBody java.util.Map<String, Object> payload) {
        try {
            String id = "EX" + System.currentTimeMillis(); // Simple ID generation
            if (payload.containsKey("id") && !((String)payload.get("id")).isEmpty()) {
                id = (String) payload.get("id"); // Use provided ID if available
            }
            String title = (String) payload.get("title");
            String courseCode = (String) payload.get("courseCode");
            int duration = Integer.parseInt(payload.get("duration").toString());
            int marks = Integer.parseInt(payload.get("marks").toString());
            String date = (String) payload.get("date");
            String status = (String) payload.get("status");
            String instructions = (String) payload.getOrDefault("instructions", "None");

            Exam exam = new Exam(id, title, courseCode, duration, marks, date, status, instructions);
            examService.addExam(exam);
            return ResponseEntity.ok("Exam added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid exam format");
        }
    }

    @GetMapping
    public ResponseEntity<List<Exam>> getAllExams() {
        List<Exam> exams = examService.getAllExams();
        return ResponseEntity.ok(exams);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exam> getExamById(@PathVariable("id") String id) {
        Exam e = examService.getExamById(id);
        if (e != null) {
            return ResponseEntity.ok(e);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateExam(@PathVariable("id") String id, @RequestBody java.util.Map<String, Object> payload) {
        try {
            String title = (String) payload.get("title");
            String courseCode = (String) payload.get("courseCode");
            int duration = Integer.parseInt(payload.get("duration").toString());
            int marks = Integer.parseInt(payload.get("marks").toString());
            String date = (String) payload.get("date");
            String status = (String) payload.get("status");
            String instructions = (String) payload.getOrDefault("instructions", "None");

            Exam updatedExam = new Exam(id, title, courseCode, duration, marks, date, status, instructions);
            boolean success = examService.updateExam(id, updatedExam);
            
            if (success) {
                return ResponseEntity.ok("Exam " + id + " updated successfully.");
            }
            return ResponseEntity.status(404).body("Exam ID not found.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid exam format");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteExam(@PathVariable("id") String id) {
        boolean success = examService.deleteExam(id);
        if (success) {
            return ResponseEntity.ok("Exam " + id + " deleted successfully.");
        }
        return ResponseEntity.status(404).body("Exam ID not found.");
    }
}
