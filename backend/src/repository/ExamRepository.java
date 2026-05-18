package com.rashmi.onlineexam.repository;

import com.rashmi.onlineexam.model.Attempt;
import com.rashmi.onlineexam.model.ScheduledExam;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.*;
import java.util.*;

@Repository
public class ExamRepository {

    private static final String DATA_FOLDER = "data/";
    private static final String EXAMS_FILE = DATA_FOLDER + "exams.txt";
    private static final String ATTEMPTS_FILE = DATA_FOLDER + "attempts.txt";

    public ExamRepository() {
        try {
            Files.createDirectories(Paths.get(DATA_FOLDER));
            Files.createDirectories(Paths.get(DATA_FOLDER));
            if (!Files.exists(Paths.get(EXAMS_FILE)))
                Files.createFile(Paths.get(EXAMS_FILE));
            if (!Files.exists(Paths.get(ATTEMPTS_FILE)))
                Files.createFile(Paths.get(ATTEMPTS_FILE));
        } catch (IOException e) {
            System.out.println("Could not create data folder: " + e.getMessage());
        }
    }

    // ==================== EXAM CRUD ====================

    // CREATE
    public boolean saveExam(ScheduledExam exam) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(EXAMS_FILE, true))) {
            bw.write(exam.toFileString());
            bw.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ ALL
    public List<ScheduledExam> getAllExams() {
        List<ScheduledExam> exams = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(EXAMS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] p = line.split("\\|");
                if (p.length >= 8) {
                    exams.add(new ScheduledExam(p[0], p[1], p[2],
                            Integer.parseInt(p[3]), p[4], p[5],
                            Integer.parseInt(p[6]), p[7]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exams;
    }

    // READ ONE
    public ScheduledExam getExamById(String examId) {
        return getAllExams().stream()
                .filter(e -> e.getExamId().equals(examId))
                .findFirst().orElse(null);
    }

    // UPDATE
    public boolean updateExam(ScheduledExam updated) {
        List<ScheduledExam> exams = getAllExams();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(EXAMS_FILE, false))) {
            for (ScheduledExam e : exams) {
                if (e.getExamId().equals(updated.getExamId())) {
                    bw.write(updated.toFileString());
                } else {
                    bw.write(e.toFileString());
                }
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE
    public boolean deleteExam(String examId) {
        List<ScheduledExam> exams = getAllExams();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(EXAMS_FILE, false))) {
            for (ScheduledExam e : exams) {
                if (!e.getExamId().equals(examId)) {
                    bw.write(e.toFileString());
                    bw.newLine();
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ==================== ATTEMPT CRUD ====================

    // CREATE
    public boolean saveAttempt(Attempt attempt) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ATTEMPTS_FILE, true))) {
            bw.write(attempt.toFileString());
            bw.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ ALL
    public List<Attempt> getAllAttempts() {
        List<Attempt> attempts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ATTEMPTS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] p = line.split("\\|");
                if (p.length >= 8) {
                    attempts.add(new Attempt(p[0], p[1], p[2], p[3],
                            p[4], p[5], Integer.parseInt(p[6]), p[7]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return attempts;
    }

    // READ BY EXAM
    public List<Attempt> getAttemptsByExamId(String examId) {
        List<Attempt> all = getAllAttempts();
        all.removeIf(a -> !a.getExamId().equals(examId));
        return all;
    }

    // DELETE
    public boolean deleteAttempt(String attemptId) {
        List<Attempt> attempts = getAllAttempts();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ATTEMPTS_FILE, false))) {
            for (Attempt a : attempts) {
                if (!a.getAttemptId().equals(attemptId)) {
                    bw.write(a.toFileString());
                    bw.newLine();
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}