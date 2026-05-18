package controller;

import model.Question;
import service.QuestionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class to expose Question Bank CRUD operations via HTTP endpoints.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * POST /questions/add
     * Adds a new question.
     */
    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody java.util.Map<String, Object> payload) {
        try {
            String questionId = (String) payload.get("questionId");
            String examId = (String) payload.get("examId");
            String questionText = (String) payload.get("questionText");
            int marks = (Integer) payload.get("marks");
            String correctAnswer = (String) payload.get("correctAnswer");

            model.Question question;
            if (payload.containsKey("options")) {
                java.util.List<String> options = (java.util.List<String>) payload.get("options");
                question = new model.MCQQuestion(questionId, examId, questionText, marks, options, correctAnswer);
            } else {
                question = new model.ShortAnswerQuestion(questionId, examId, questionText, marks, correctAnswer);
            }

            questionService.addQuestion(question);
            return ResponseEntity.ok("Question added successfully to the file.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid question format");
        }
    }

    /**
     * GET /questions
     * Retrieves all available questions or filters by examId if provided.
     */
    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions(@RequestParam(required = false) String examId) {
        List<Question> questions;
        if (examId != null && !examId.isEmpty()) {
            questions = questionService.getQuestionsByExamId(examId);
        } else {
            questions = questionService.getAllQuestions();
        }
        return ResponseEntity.ok(questions);
    }

    /**
     * GET /questions/{id}
     * Retrieves a specific question using its ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable("id") String id) {
        Question q = questionService.getQuestionById(id);
        if (q != null) {
            return ResponseEntity.ok(q);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * PUT /questions/update/{id}
     * Updates an existing question.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateQuestion(@PathVariable("id") String id, @RequestBody java.util.Map<String, Object> payload) {
        try {
            String questionId = (String) payload.getOrDefault("questionId", id);
            String examId = (String) payload.get("examId");
            String questionText = (String) payload.get("questionText");
            int marks = (Integer) payload.get("marks");
            String correctAnswer = (String) payload.get("correctAnswer");

            model.Question updatedQuestion;
            if (payload.containsKey("options")) {
                java.util.List<String> options = (java.util.List<String>) payload.get("options");
                updatedQuestion = new model.MCQQuestion(questionId, examId, questionText, marks, options, correctAnswer);
            } else {
                updatedQuestion = new model.ShortAnswerQuestion(questionId, examId, questionText, marks, correctAnswer);
            }

            boolean success = questionService.updateQuestion(id, updatedQuestion);
            if (success) {
                return ResponseEntity.ok("Question " + id + " updated successfully.");
            }
            return ResponseEntity.status(404).body("Question ID not found.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid question format");
        }
    }

    /**
     * DELETE /questions/delete/{id}
     * Deletes an existing question.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable("id") String id) {
        boolean success = questionService.deleteQuestion(id);
        if (success) {
            return ResponseEntity.ok("Question " + id + " deleted successfully.");
        }
        return ResponseEntity.status(404).body("Question ID not found.");
    }
}
