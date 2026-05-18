package controller;

import model.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.FeedbackService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        List<Feedback> feedbackList = feedbackService.getAllFeedbacks();
        return ResponseEntity.ok(feedbackList);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addFeedback(
            @RequestParam("studentId") String studentId,
            @RequestParam("examId") String examId,
            @RequestParam("type") String type,
            @RequestParam("rating") int rating,
            @RequestParam("comment") String comment) {

        boolean success = feedbackService.addFeedback(studentId, examId, type, rating, comment);
        if (!success) {
            return ResponseEntity.badRequest().body("Failed to add feedback");
        }
        return ResponseEntity.ok("Feedback added successfully");
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<String> updateFeedback(
            @PathVariable("id") String id,
            @RequestParam("rating") int rating,
            @RequestParam("comment") String comment) {

        boolean success = feedbackService.updateFeedback(id, rating, comment);
        if (!success) {
            return ResponseEntity.status(404).body("Feedback not found");
        }
        return ResponseEntity.ok("Feedback updated successfully");
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteFeedback(@PathVariable("id") String id) {
        feedbackService.deleteFeedback(id);
        return ResponseEntity.ok("Feedback deleted successfully");
    }

    @GetMapping("/review/{id}")
    public ResponseEntity<String> reviewFeedback(@PathVariable("id") String id) {
        feedbackService.reviewFeedback(id);
        return ResponseEntity.ok("Feedback reviewed successfully");
    }
}
