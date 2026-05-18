package com.rashmi.onlineexam.controller;

import com.rashmi.onlineexam.model.Attempt;
import com.rashmi.onlineexam.model.ScheduledExam;
import com.rashmi.onlineexam.repository.ExamRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/exam")
public class ExamController {

    private final ExamRepository examRepository;

    public ExamController(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    // LIST ALL EXAMS
    @GetMapping("/list")
    public String listExams(Model model) {
        model.addAttribute("exams", examRepository.getAllExams());
        return "exams-list";
    }

    // SHOW SCHEDULE FORM
    @GetMapping("/schedule")
    public String showScheduleForm(Model model) {
        model.addAttribute("exam", new ScheduledExam());
        return "schedule-exam";
    }

    // SAVE NEW EXAM
    @PostMapping("/schedule")
    public String saveExam(@ModelAttribute ScheduledExam exam) {
        exam.setExamId("EX" + System.currentTimeMillis() % 100000);
        if (exam.getStatus() == null || exam.getStatus().trim().isEmpty()) {
            exam.setStatus("Upcoming");
        }
        examRepository.saveExam(exam);
        return "redirect:/exam/list";
    }

    // SHOW EDIT FORM
    @GetMapping("/edit/{examId}")
    public String showEditForm(@PathVariable String examId, Model model) {
        ScheduledExam exam = examRepository.getExamById(examId);
        model.addAttribute("exam", exam);
        return "edit-exam";
    }

    // UPDATE EXAM
    @PostMapping("/edit/{examId}")
    public String updateExam(@PathVariable String examId,
                             @ModelAttribute ScheduledExam exam) {
        exam.setExamId(examId);
        examRepository.updateExam(exam);
        return "redirect:/exam/list";
    }

    // DELETE EXAM
    @GetMapping("/delete/{examId}")
    public String deleteExam(@PathVariable String examId) {
        examRepository.deleteExam(examId);
        return "redirect:/exam/list";
    }

    // LIST ALL ATTEMPTS
    @GetMapping("/attempts")
    public String listAttempts(Model model) {
        model.addAttribute("attempts", examRepository.getAllAttempts());
        model.addAttribute("exams", examRepository.getAllExams());
        return "attempts-list";
    }

    // SHOW ADD ATTEMPT FORM
    @GetMapping("/attempts/add")
    public String showAttemptForm(Model model) {
        model.addAttribute("attempt", new Attempt());
        model.addAttribute("exams", examRepository.getAllExams());
        return "add-attempt";
    }

    // SAVE ATTEMPT
    @PostMapping("/attempts/add")
    public String saveAttempt(@ModelAttribute Attempt attempt) {
        attempt.setAttemptId("AT" + System.currentTimeMillis() % 100000);
        if (attempt.getStatus() == null || attempt.getStatus().trim().isEmpty()) {
            attempt.setStatus("Completed");
        }
        examRepository.saveAttempt(attempt);
        return "redirect:/exam/attempts";
    }

    // DELETE ATTEMPT
    @GetMapping("/attempts/delete/{attemptId}")
    public String deleteAttempt(@PathVariable String attemptId) {
        examRepository.deleteAttempt(attemptId);
        return "redirect:/exam/attempts";
    }
}