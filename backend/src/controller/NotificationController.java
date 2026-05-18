package controller;

import model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.NotificationService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        List<Notification> notificationList = notificationService.getAllNotifications();
        return ResponseEntity.ok(notificationList);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addNotification(
            @RequestParam("title") String title,
            @RequestParam("message") String message,
            @RequestParam("targetRole") String targetRole) {

        boolean success = notificationService.addNotification(title, message, targetRole);
        if (!success) {
            return ResponseEntity.badRequest().body("Failed to add notification");
        }
        return ResponseEntity.ok("Notification added successfully");
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<String> updateNotification(
            @PathVariable("id") String id,
            @RequestParam("title") String title,
            @RequestParam("message") String message,
            @RequestParam("status") String status) {

        boolean success = notificationService.updateNotification(id, title, message, status);
        if (!success) {
            return ResponseEntity.status(404).body("Notification not found");
        }
        return ResponseEntity.ok("Notification updated successfully");
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable("id") String id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok("Notification deleted successfully");
    }

    @GetMapping("/expire/{id}")
    public ResponseEntity<String> expireNotification(@PathVariable("id") String id) {
        notificationService.expireNotification(id);
        return ResponseEntity.ok("Notification expired successfully");
    }
}
