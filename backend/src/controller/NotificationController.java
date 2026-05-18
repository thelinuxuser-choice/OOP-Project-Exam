package controller;

import model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.NotificationService;

import java.util.List;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public ModelAndView viewNotifications() {
        List<Notification> notificationList = notificationService.getAllNotifications();
        ModelAndView mav = new ModelAndView("notifications");
        mav.addObject("notificationList", notificationList);
        return mav;
    }

    @GetMapping("/add")
    public String showAddNotificationForm(Model model) {
        return "add-notification";
    }

    @PostMapping("/add")
    public String addNotification(
            @RequestParam("title") String title,
            @RequestParam("message") String message,
            @RequestParam("targetRole") String targetRole,
            Model model) {

        boolean success = notificationService.addNotification(title, message, targetRole);
        if (!success) {
            return "redirect:/notifications?error=addFailed";
        }
        return "redirect:/notifications";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditNotificationForm(@PathVariable("id") String id) {
        Notification notification = notificationService.getNotificationById(id);
        if (notification == null) {
            return new ModelAndView("redirect:/notifications");
        }
        ModelAndView mav = new ModelAndView("edit-notification");
        mav.addObject("notification", notification);
        return mav;
    }

    @PostMapping("/update/{id}")
    public String updateNotification(
            @PathVariable("id") String id,
            @RequestParam("title") String title,
            @RequestParam("message") String message,
            @RequestParam("status") String status,
            Model model) {

        boolean success = notificationService.updateNotification(id, title, message, status);
        if (!success) {
            return "redirect:/notifications?error=updateFailed";
        }
        return "redirect:/notifications";
    }

    @GetMapping("/delete/{id}")
    public String deleteNotification(@PathVariable("id") String id) {
        notificationService.deleteNotification(id);
        return "redirect:/notifications";
    }

    @GetMapping("/expire/{id}")
    public String expireNotification(@PathVariable("id") String id) {
        notificationService.expireNotification(id);
        return "redirect:/notifications";
    }
}
