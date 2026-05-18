package service;

import model.CourseFeedback;
import model.ExamFeedback;
import model.Feedback;
import org.springframework.stereotype.Service;
import util.FileHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FeedbackService {

    private static final String FILE_PATH = "backend/feedback.txt";

    public List<Feedback> getAllFeedbacks() {
        List<String> lines = FileHandler.readFile(FILE_PATH);
        List<Feedback> feedbacks = new ArrayList<>();
        
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split("\\|");
            if (parts.length >= 8) {
                Feedback fb = parseFeedback(parts);
                feedbacks.add(fb);
            }
        }
        return feedbacks;
    }

    public List<Feedback> getFeedbacksByStudent(String studentId) {
        List<Feedback> all = getAllFeedbacks();
        List<Feedback> result = new ArrayList<>();
        for (Feedback fb : all) {
            if (fb.getStudentId().equals(studentId)) {
                result.add(fb);
            }
        }
        return result;
    }
    
    public List<Feedback> getFeedbacksByReference(String refId) {
        List<Feedback> all = getAllFeedbacks();
        List<Feedback> result = new ArrayList<>();
        for (Feedback fb : all) {
            if (fb.getExamId().equals(refId)) {
                result.add(fb);
            }
        }
        return result;
    }
    
    public Feedback getFeedbackById(String id) {
        List<Feedback> all = getAllFeedbacks();
        for (Feedback fb : all) {
            if (fb.getFeedbackId().equals(id)) {
                return fb;
            }
        }
        return null;
    }

    public boolean addFeedback(String studentId, String examId, String type, int rating, String comment) {
        if (studentId == null || studentId.trim().isEmpty() ||
            examId == null || examId.trim().isEmpty() ||
            comment == null || comment.trim().isEmpty()) {
            return false;
        }
        if (rating < 1 || rating > 5) {
            return false;
        }

        String newId = generateNextId();
        String createdDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String status = "PENDING";
        
        String line = String.join("|", newId, studentId, examId, type, String.valueOf(rating), comment, status, createdDate);
        FileHandler.appendFile(FILE_PATH, line);
        return true;
    }

    public boolean updateFeedback(String feedbackId, int rating, String comment) {
        if (rating < 1 || rating > 5 || comment == null || comment.trim().isEmpty()) {
            return false;
        }

        List<Feedback> all = getAllFeedbacks();
        boolean updated = false;

        for (Feedback fb : all) {
            if (fb.getFeedbackId().equals(feedbackId)) {
                fb.setRating(rating);
                fb.setComment(comment);
                updated = true;
                break;
            }
        }

        if (updated) {
            saveAllFeedbacks(all);
            return true;
        }
        return false;
    }

    public boolean reviewFeedback(String feedbackId) {
        List<Feedback> all = getAllFeedbacks();
        boolean updated = false;

        for (Feedback fb : all) {
            if (fb.getFeedbackId().equals(feedbackId)) {
                fb.setStatus("REVIEWED");
                updated = true;
                break;
            }
        }

        if (updated) {
            saveAllFeedbacks(all);
            return true;
        }
        return false;
    }

    public boolean deleteFeedback(String feedbackId) {
        List<Feedback> all = getAllFeedbacks();
        boolean removed = all.removeIf(fb -> fb.getFeedbackId().equals(feedbackId));
        
        if (removed) {
            saveAllFeedbacks(all);
            return true;
        }
        return false;
    }

    private void saveAllFeedbacks(List<Feedback> feedbacks) {
        List<String> lines = new ArrayList<>();
        for (Feedback fb : feedbacks) {
            String line = String.join("|", 
                fb.getFeedbackId(), 
                fb.getStudentId(), 
                fb.getExamId(), 
                fb.getType(), 
                String.valueOf(fb.getRating()), 
                fb.getComment(), 
                fb.getStatus(), 
                fb.getCreatedDate()
            );
            lines.add(line);
        }
        FileHandler.writeFile(FILE_PATH, lines);
    }

    private Feedback parseFeedback(String[] parts) {
        String type = parts[3];
        if ("EXAM".equalsIgnoreCase(type)) {
            return new ExamFeedback(parts[0], parts[1], parts[2], Integer.parseInt(parts[4]), parts[5], parts[6], parts[7]);
        } else if ("COURSE".equalsIgnoreCase(type)) {
            return new CourseFeedback(parts[0], parts[1], parts[2], Integer.parseInt(parts[4]), parts[5], parts[6], parts[7]);
        } else {
            return new Feedback(parts[0], parts[1], parts[2], type, Integer.parseInt(parts[4]), parts[5], parts[6], parts[7]);
        }
    }

    private String generateNextId() {
        List<Feedback> all = getAllFeedbacks();
        if (all.isEmpty()) {
            return "FB001";
        }
        
        int maxId = 0;
        for (Feedback fb : all) {
            String idStr = fb.getFeedbackId().replace("FB", "");
            try {
                int id = Integer.parseInt(idStr);
                if (id > maxId) {
                    maxId = id;
                }
            } catch (NumberFormatException ignored) {}
        }
        
        return String.format("FB%03d", maxId + 1);
    }
}
