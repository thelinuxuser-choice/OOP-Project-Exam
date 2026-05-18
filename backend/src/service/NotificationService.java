package service;

import model.AnnouncementNotification;
import model.Notification;
import org.springframework.stereotype.Service;
import util.FileHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NotificationService {

    private static final String FILE_PATH = "notifications.txt";

    public List<Notification> getAllNotifications() {
        List<String> lines = FileHandler.readFile(FILE_PATH);
        List<Notification> notifications = new ArrayList<>();
        
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split("\\|");
            if (parts.length >= 6) {
                Notification notif = parseNotification(parts);
                notifications.add(notif);
            }
        }
        return notifications;
    }

    /**
     * Get active notifications by role (or ALL)
     */
    public List<Notification> getActiveNotificationsByRole(String role) {
        List<Notification> all = getAllNotifications();
        List<Notification> result = new ArrayList<>();
        for (Notification notif : all) {
            if ("ACTIVE".equalsIgnoreCase(notif.getStatus()) && 
               ("ALL".equalsIgnoreCase(notif.getTargetRole()) || role.equalsIgnoreCase(notif.getTargetRole()))) {
                result.add(notif);
            }
        }
        return result;
    }
    
    public Notification getNotificationById(String id) {
        List<Notification> all = getAllNotifications();
        for (Notification notif : all) {
            if (notif.getNotificationId().equals(id)) {
                return notif;
            }
        }
        return null;
    }

    /**
     * Add new notification
     */
    public boolean addNotification(String title, String message, String targetRole) {
        if (title == null || title.trim().isEmpty() ||
            message == null || message.trim().isEmpty() ||
            targetRole == null || targetRole.trim().isEmpty()) {
            return false;
        }

        String newId = generateNextId();
        String createdDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String status = "ACTIVE";
        
        Notification notif = new AnnouncementNotification(newId, title, message, targetRole, status, createdDate);
        notif.send();
        
        String line = String.join("|", 
            notif.getNotificationId(), 
            notif.getTitle(), 
            notif.getMessage(), 
            notif.getTargetRole(), 
            notif.getStatus(), 
            notif.getCreatedDate()
        );
        FileHandler.appendFile(FILE_PATH, line);
        return true;
    }

    /**
     * Update existing notification
     */
    public boolean updateNotification(String notificationId, String title, String message, String status) {
        if (title == null || title.trim().isEmpty() || message == null || message.trim().isEmpty()) {
            return false;
        }

        List<Notification> all = getAllNotifications();
        boolean updated = false;

        for (Notification notif : all) {
            if (notif.getNotificationId().equals(notificationId)) {
                notif.setTitle(title);
                notif.setMessage(message);
                if (status != null && !status.trim().isEmpty()) {
                    notif.setStatus(status);
                }
                updated = true;
                break;
            }
        }

        if (updated) {
            saveAllNotifications(all);
            return true;
        }
        return false;
    }

    /**
     * Admin expire notification
     */
    public boolean expireNotification(String notificationId) {
        List<Notification> all = getAllNotifications();
        boolean updated = false;

        for (Notification notif : all) {
            if (notif.getNotificationId().equals(notificationId)) {
                notif.dismiss();
                updated = true;
                break;
            }
        }

        if (updated) {
            saveAllNotifications(all);
            return true;
        }
        return false;
    }

    /**
     * Admin delete notification
     */
    public boolean deleteNotification(String notificationId) {
        List<Notification> all = getAllNotifications();
        boolean removed = all.removeIf(notif -> notif.getNotificationId().equals(notificationId));
        
        if (removed) {
            saveAllNotifications(all);
            return true;
        }
        return false;
    }

    private void saveAllNotifications(List<Notification> notifications) {
        List<String> lines = new ArrayList<>();
        for (Notification notif : notifications) {
            String line = String.join("|", 
                notif.getNotificationId(), 
                notif.getTitle(), 
                notif.getMessage(), 
                notif.getTargetRole(), 
                notif.getStatus(), 
                notif.getCreatedDate()
            );
            lines.add(line);
        }
        FileHandler.writeFile(FILE_PATH, lines);
    }

    private Notification parseNotification(String[] parts) {
        return new AnnouncementNotification(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
    }

    private String generateNextId() {
        List<Notification> all = getAllNotifications();
        if (all.isEmpty()) {
            return "NT001";
        }
        
        int maxId = 0;
        for (Notification notif : all) {
            String idStr = notif.getNotificationId().replace("NT", "");
            try {
                int id = Integer.parseInt(idStr);
                if (id > maxId) {
                    maxId = id;
                }
            } catch (NumberFormatException ignored) {}
        }
        
        return String.format("NT%03d", maxId + 1);
    }
}
