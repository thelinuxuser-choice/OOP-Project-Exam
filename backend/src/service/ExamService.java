package service;

import model.Exam;
import util.FileHandler;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for handling CRUD operations on Exams.
 */
@Service
public class ExamService {
    private static final String FILE_PATH = "exams.txt";
    private List<Exam> examList;

    public ExamService() {
        this.examList = new ArrayList<>();
        loadExamsFromFile();
    }

    private void loadExamsFromFile() {
        examList.clear();
        List<String> lines = FileHandler.readFile(FILE_PATH);
        
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            
            String[] parts = line.split("\\|");
            if (parts.length >= 8) {
                String id = parts[0];
                String title = parts[1];
                String courseCode = parts[2];
                int duration = Integer.parseInt(parts[3]);
                int marks = Integer.parseInt(parts[4]);
                String date = parts[5];
                String status = parts[6];
                String instructions = parts[7];
                
                examList.add(new Exam(id, title, courseCode, duration, marks, date, status, instructions));
            }
        }
    }

    private void saveAllToFile() {
        List<String> data = new ArrayList<>();
        for (Exam e : examList) {
            data.add(e.toFileString());
        }
        FileHandler.writeFile(FILE_PATH, data);
    }

    public void addExam(Exam exam) {
        examList.add(exam);
        FileHandler.appendFile(FILE_PATH, exam.toFileString());
    }

    public List<Exam> getAllExams() {
        return new ArrayList<>(examList);
    }

    public Exam getExamById(String id) {
        for (Exam e : examList) {
            if (e.getId().equalsIgnoreCase(id)) {
                return e;
            }
        }
        return null;
    }

    public boolean updateExam(String id, Exam updatedExam) {
        for (int i = 0; i < examList.size(); i++) {
            if (examList.get(i).getId().equalsIgnoreCase(id)) {
                updatedExam.setId(id); // Ensure ID is preserved
                examList.set(i, updatedExam);
                saveAllToFile();
                return true;
            }
        }
        return false;
    }

    public boolean deleteExam(String id) {
        boolean removed = examList.removeIf(e -> e.getId().equalsIgnoreCase(id));
        if (removed) {
            saveAllToFile();
        }
        return removed;
    }
}
