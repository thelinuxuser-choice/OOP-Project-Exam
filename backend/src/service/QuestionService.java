package service;

import model.MCQQuestion;
import model.Question;
import model.ShortAnswerQuestion;
import util.FileHandler;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Service class for handling CRUD operations on the Question Bank.
 */
@Service
public class QuestionService {
    private static final String FILE_PATH = "backend/questions.txt";
    private List<Question> questionList;

    public QuestionService() {
        this.questionList = new ArrayList<>();
        loadQuestionsFromFile();
    }

    /**
     * Reads file lines and parses them into a list of Question objects.
     */
    private void loadQuestionsFromFile() {
        questionList.clear();
        List<String> lines = FileHandler.readFile(FILE_PATH);
        
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            
            String[] parts = line.split("\\|");
            if (parts.length >= 7) {
                String qId = parts[0];
                String eId = parts[1];
                String text = parts[2];
                String type = parts[3];
                String optionsStr = parts[4];
                String correctAns = parts[5];
                int marks = Integer.parseInt(parts[6]);

                if ("MCQ".equalsIgnoreCase(type)) {
                    List<String> options = Arrays.asList(optionsStr.split(","));
                    questionList.add(new MCQQuestion(qId, eId, text, marks, options, correctAns));
                } else if ("SHORT".equalsIgnoreCase(type)) {
                    questionList.add(new ShortAnswerQuestion(qId, eId, text, marks, correctAns));
                }
            }
        }
    }

    /**
     * Syncs the in-memory list to the file by rewriting it.
     */
    private void saveAllToFile() {
        List<String> data = new ArrayList<>();
        for (Question q : questionList) {
            data.add(q.toFileString());
        }
        FileHandler.writeFile(FILE_PATH, data);
    }

    /**
     * Adds a new question to the list and appends it to the file.
     * @param question The question object to add
     */
    public void addQuestion(Question question) {
        questionList.add(question);
        FileHandler.appendFile(FILE_PATH, question.toFileString());
    }

    /**
     * Retrieves all stored questions.
     * @return List of all questions
     */
    public List<Question> getAllQuestions() {
        return new ArrayList<>(questionList); // Return a copy
    }

    /**
     * Retrieves questions belonging to a specific exam.
     * @param examId The exam identifier
     * @return List of questions mapped to the exam
     */
    public List<Question> getQuestionsByExam(String examId) {
        List<Question> resultList = new ArrayList<>();
        for (Question q : questionList) {
            if (q.getExamId().equalsIgnoreCase(examId)) {
                resultList.add(q);
            }
        }
        return resultList;
    }

    /**
     * Alias for getQuestionsByExam for controller compatibility.
     */
    public List<Question> getQuestionsByExamId(String examId) {
        return getQuestionsByExam(examId);
    }

    /**
     * Retrieves a question by its unique ID.
     * @param questionId The question identifier
     * @return The matched Question object, or null if not found
     */
    public Question getQuestionById(String questionId) {
        for (Question q : questionList) {
            if (q.getQuestionId().equalsIgnoreCase(questionId)) {
                return q;
            }
        }
        return null;
    }

    /**
     * Updates an existing question by ID.
     * @param questionId ID of the question to replace
     * @param updatedQuestion The new question contents
     * @return true if updated successfully, false if the ID was not found
     */
    public boolean updateQuestion(String questionId, Question updatedQuestion) {
        for (int i = 0; i < questionList.size(); i++) {
            if (questionList.get(i).getQuestionId().equalsIgnoreCase(questionId)) {
                updatedQuestion.setQuestionId(questionId); // ensure ID immutability
                questionList.set(i, updatedQuestion);
                saveAllToFile(); // write changes to text file
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a question by ID and updates the file.
     * @param questionId The question identifier
     * @return true if successfully removed, false if not found
     */
    public boolean deleteQuestion(String questionId) {
        boolean removed = questionList.removeIf(q -> q.getQuestionId().equalsIgnoreCase(questionId));
        if (removed) {
            saveAllToFile(); // write changes to text file
        }
        return removed;
    }
}
