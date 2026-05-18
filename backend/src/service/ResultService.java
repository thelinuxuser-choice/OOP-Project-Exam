package service;

import model.Result;
import model.ExamAttempt;
import model.Question;
import util.FileHandler;
import util.IdGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResultService {

    private static final String FILE_PATH = "backend/results.txt";

    public List<Result> getAllResults() {
        List<String> lines = FileHandler.readFile(FILE_PATH);
        List<Result> results = new ArrayList<>();
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            Result r = Result.fromFileString(line);
            if (r != null) results.add(r);
        }
        return results;
    }

    public List<Result> getResultsByStudent(String studentId) {
        List<Result> all = getAllResults();
        List<Result> studentResults = new ArrayList<>();
        for (Result r : all) {
            if (r.getStudentId().equals(studentId)) {
                studentResults.add(r);
            }
        }
        return studentResults;
    }

    public List<Result> getResultsByExam(String examId) {
        List<Result> all = getAllResults();
        List<Result> examResults = new ArrayList<>();
        for (Result r : all) {
            if (r.getExamId().equals(examId)) {
                examResults.add(r);
            }
        }
        return examResults;
    }

    public List<Result> getResultsByCourse(String courseCode) {
        List<Result> all = getAllResults();
        List<Result> courseResults = new ArrayList<>();
        for (Result r : all) {
            if (r.getCourseCode().equals(courseCode)) {
                courseResults.add(r);
            }
        }
        return courseResults;
    }

    public Result getResultById(String resultId) {
        List<Result> all = getAllResults();
        for (Result r : all) {
            if (r.getResultId().equals(resultId)) {
                return r;
            }
        }
        return null;
    }

    public Result getResultByStudentAndSession(String studentId, String sessionId) {
        List<Result> all = getAllResults();
        for (Result r : all) {
            if (r.getStudentId().equals(studentId) && r.getSessionId().equals(sessionId)) {
                return r;
            }
        }
        return null;
    }

    public Result processExamSubmission(String studentId, String studentName, String examId, String examTitle,
                                        String courseCode, String sessionId, int totalQuestions, int correctAnswers,
                                        int totalMarks, LocalDateTime submittedAt) {
        int obtainedMarks = (int) Math.round((double) correctAnswers / totalQuestions * totalMarks);
        double percentage = totalQuestions > 0 ? (double) correctAnswers / totalQuestions * 100 : 0;
        String grade = Result.calculateGrade(percentage);
        String status = percentage >= 40 ? "PASS" : "FAIL";

        String resultId = IdGenerator.generateId("RES", FILE_PATH);
        Result result = new Result(resultId, studentId, studentName, examId, examTitle, courseCode, sessionId,
                totalQuestions, correctAnswers, totalMarks, obtainedMarks, percentage, grade, status,
                submittedAt, "");

        FileHandler.appendFile(FILE_PATH, result.toFileString());
        return result;
    }

    public Result processAttempt(ExamAttempt attempt, String examTitle, String courseCode, int totalMarks) {
        String[] answerParts = attempt.getAnswers().split(",");
        int correctCount = 0;
        for (String ans : answerParts) {
            if (ans.contains("=")) {
                String[] parts = ans.split("=");
                if (parts.length == 2) {
                    // Simplified grading - in real scenario would check against question correct answer
                    correctCount++;
                }
            }
        }

        int totalQuestions = answerParts.length;
        int obtainedMarks = (int) Math.round((double) correctCount / totalQuestions * totalMarks);
        double percentage = totalQuestions > 0 ? (double) correctCount / totalQuestions * 100 : 0;
        String grade = Result.calculateGrade(percentage);
        String status = percentage >= 40 ? "PASS" : "FAIL";

        String resultId = IdGenerator.generateId("RES", FILE_PATH);
        Result result = new Result(resultId, attempt.getStudentId(), "", attempt.getExamId(), examTitle,
                courseCode, attempt.getSessionId(), totalQuestions, correctCount, totalMarks, obtainedMarks,
                percentage, grade, status, attempt.getSubmitTime(), "");

        FileHandler.appendFile(FILE_PATH, result.toFileString());
        return result;
    }

    public boolean updateResultGrade(String resultId, String newGrade, String feedback) {
        List<Result> all = getAllResults();
        boolean found = false;

        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getResultId().equals(resultId)) {
                all.get(i).setGrade(newGrade);
                if (feedback != null) {
                    all.get(i).setFeedback(feedback);
                }
                found = true;
                break;
            }
        }

        if (found) {
            List<String> lines = new ArrayList<>();
            for (Result r : all) {
                lines.add(r.toFileString());
            }
            FileHandler.writeFile(FILE_PATH, lines);
        }

        return found;
    }

    public boolean deleteResult(String resultId) {
        List<Result> all = getAllResults();
        boolean removed = all.removeIf(r -> r.getResultId().equals(resultId));

        if (removed) {
            List<String> lines = new ArrayList<>();
            for (Result r : all) {
                lines.add(r.toFileString());
            }
            FileHandler.writeFile(FILE_PATH, lines);
        }

        return removed;
    }

    public double getClassAverage(String examId) {
        List<Result> examResults = getResultsByExam(examId);
        if (examResults.isEmpty()) return 0;

        double total = 0;
        for (Result r : examResults) {
            total += r.getPercentage();
        }
        return total / examResults.size();
    }

    public int[] getGradeDistribution(String examId) {
        List<Result> examResults = getResultsByExam(examId);
        int[] distribution = new int[12]; // A+, A, A-, B+, B, B-, C+, C, C-, D+, D, F

        for (Result r : examResults) {
            String grade = r.getGrade();
            switch (grade) {
                case "A+": distribution[0]++; break;
                case "A": distribution[1]++; break;
                case "A-": distribution[2]++; break;
                case "B+": distribution[3]++; break;
                case "B": distribution[4]++; break;
                case "B-": distribution[5]++; break;
                case "C+": distribution[6]++; break;
                case "C": distribution[7]++; break;
                case "C-": distribution[8]++; break;
                case "D+": distribution[9]++; break;
                case "D": distribution[10]++; break;
                case "F": distribution[11]++; break;
            }
        }
        return distribution;
    }
}
