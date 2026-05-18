package model;

/**
 * AnnouncementNotification extending Notification.
 */
public class AnnouncementNotification extends Notification {

    public AnnouncementNotification() {
        super();
    }

    public AnnouncementNotification(String notificationId, String title, String message, String targetRole, String status, String createdDate) {
        super(notificationId, title, message, targetRole, status, createdDate);
    }

    @Override
    public void send() {
        System.out.println("Sending Announcement: " + getTitle() + " to " + getTargetRole());
        this.setStatus("ACTIVE");
    }

    @Override
    public void dismiss() {
        System.out.println("Dismissing Announcement: " + getTitle());
        this.setStatus("EXPIRED");
    }
}
