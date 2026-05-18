package controller;

import model.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.FeedbackService;

import java.util.List;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping
    public ModelAndView viewFeedback() {
        List<Feedback> feedbackList = feedbackService.getAllFeedbacks();
        ModelAndView mav = new ModelAndView("feedback");
        mav.addObject("feedbackList", feedbackList);
        return mav;
    }

    @GetMapping("/add")
    public String showAddFeedbackForm(Model model) {
        return "add-feedback";
    }

    @PostMapping("/add")
    public String addFeedback(
            @RequestParam("studentId") String studentId,
            @RequestParam("examId") String examId,
            @RequestParam("type") String type,
            @RequestParam("rating") int rating,
            @RequestParam("comment") String comment,
            Model model) {

        boolean success = feedbackService.addFeedback(studentId, examId, type, rating, comment);
        if (!success) {
            return "redirect:/feedback?error=addFailed";
        }
        return "redirect:/feedback";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditFeedbackForm(@PathVariable("id") String id) {
        Feedback feedback = feedbackService.getFeedbackById(id);
        if (feedback == null) {
            return new ModelAndView("redirect:/feedback");
        }
        ModelAndView mav = new ModelAndView("edit-feedback");
        mav.addObject("feedback", feedback);
        return mav;
    }

    @PostMapping("/update/{id}")
    public String updateFeedback(
            @PathVariable("id") String id,
            @RequestParam("rating") int rating,
            @RequestParam("comment") String comment,
            Model model) {

        boolean success = feedbackService.updateFeedback(id, rating, comment);
        if (!success) {
            return "redirect:/feedback?error=updateFailed";
        }
        return "redirect:/feedback";
    }

    @GetMapping("/delete/{id}")
    public String deleteFeedback(@PathVariable("id") String id) {
        feedbackService.deleteFeedback(id);
        return "redirect:/feedback";
    }

    @GetMapping("/review/{id}")
    public String reviewFeedback(@PathVariable("id") String id) {
        feedbackService.reviewFeedback(id);
        return "redirect:/feedback";
    }
}
