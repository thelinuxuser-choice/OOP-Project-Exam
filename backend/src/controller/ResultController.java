package controller;

import model.Result;
import service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/results")
public class ResultController {

    private final ResultService resultService;

    @Autowired
    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping
    public ResponseEntity<List<Result>> getAllResults() {
        return ResponseEntity.ok(resultService.getAllResults());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Result>> getStudentResults(@PathVariable String studentId) {
        return ResponseEntity.ok(resultService.getResultsByStudent(studentId));
    }

    @GetMapping("/exam/{examId}")
    public ResponseEntity<List<Result>> getExamResults(@PathVariable String examId) {
        return ResponseEntity.ok(resultService.getResultsByExam(examId));
    }

    @GetMapping("/course/{courseCode}")
    public ResponseEntity<List<Result>> getCourseResults(@PathVariable String courseCode) {
        return ResponseEntity.ok(resultService.getResultsByCourse(courseCode));
    }

    @GetMapping("/id/{resultId}")
    public ResponseEntity<Result> getResultById(@PathVariable String resultId) {
        Result r = resultService.getResultById(resultId);
        if (r != null) {
            return ResponseEntity.ok(r);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/student/{studentId}/session/{sessionId}")
    public ResponseEntity<Result> getStudentSessionResult(@PathVariable String studentId, @PathVariable String sessionId) {
        Result r = resultService.getResultByStudentAndSession(studentId, sessionId);
        if (r != null) {
            return ResponseEntity.ok(r);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/process")
    public ResponseEntity<?> processSubmission(@RequestBody Map<String, Object> payload) {
        try {
            String studentId = (String) payload.get("studentId");
            String studentName = (String) payload.getOrDefault("studentName", "");
            String examId = (String) payload.get("examId");
            String examTitle = (String) payload.getOrDefault("examTitle", "Unknown Exam");
            String courseCode = (String) payload.getOrDefault("courseCode", "Unknown");
            String sessionId = (String) payload.get("sessionId");
            int totalQuestions = (Integer) payload.getOrDefault("totalQuestions", 0);
            int correctAnswers = (Integer) payload.getOrDefault("correctAnswers", 0);
            int totalMarks = (Integer) payload.getOrDefault("totalMarks", 100);
            LocalDateTime submittedAt = LocalDateTime.now();

            Result result = resultService.processExamSubmission(studentId, studentName, examId, examTitle,
                    courseCode, sessionId, totalQuestions, correctAnswers, totalMarks, submittedAt);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to process result: " + e.getMessage());
        }
    }

    @PutMapping("/{resultId}/grade")
    public ResponseEntity<String> updateGrade(@PathVariable String resultId, @RequestBody Map<String, String> payload) {
        String newGrade = payload.get("grade");
        String feedback = payload.get("feedback");

        if (newGrade == null) {
            return ResponseEntity.badRequest().body("Grade is required");
        }

        boolean success = resultService.updateResultGrade(resultId, newGrade, feedback);
        if (success) {
            return ResponseEntity.ok("Grade updated successfully");
        }
        return ResponseEntity.status(404).body("Result not found");
    }

    @DeleteMapping("/{resultId}")
    public ResponseEntity<String> deleteResult(@PathVariable String resultId) {
        boolean success = resultService.deleteResult(resultId);
        if (success) {
            return ResponseEntity.ok("Result deleted successfully");
        }
        return ResponseEntity.status(404).body("Result not found");
    }

    @GetMapping("/exam/{examId}/statistics")
    public ResponseEntity<Map<String, Object>> getExamStatistics(@PathVariable String examId) {
        double average = resultService.getClassAverage(examId);
        int[] distribution = resultService.getGradeDistribution(examId);

        Map<String, Integer> gradeDist = new java.util.HashMap<>();
        gradeDist.put("A+", distribution[0]);
        gradeDist.put("A", distribution[1]);
        gradeDist.put("A-", distribution[2]);
        gradeDist.put("B+", distribution[3]);
        gradeDist.put("B", distribution[4]);
        gradeDist.put("B-", distribution[5]);
        gradeDist.put("C+", distribution[6]);
        gradeDist.put("C", distribution[7]);
        gradeDist.put("C-", distribution[8]);
        gradeDist.put("D+", distribution[9]);
        gradeDist.put("D", distribution[10]);
        gradeDist.put("F", distribution[11]);

        Map<String, Object> stats = new java.util.HashMap<>();
        stats.put("average", average);
        stats.put("gradeDistribution", gradeDist);

        return ResponseEntity.ok(stats);
    }
}
