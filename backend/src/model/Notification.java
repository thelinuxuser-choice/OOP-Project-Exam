package model;

public abstract class Notification {
    private String notificationId;
    private String title;
    private String message;
    private String targetRole;
    private String status;
    private String createdDate;

    public Notification() {}

    public Notification(String notificationId, String title, String message, String targetRole, String status, String createdDate) {
        this.notificationId = notificationId;
        this.title = title;
        this.message = message;
        this.targetRole = targetRole;
        this.status = status;
        this.createdDate = createdDate;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTargetRole() {
        return targetRole;
    }

    public void setTargetRole(String targetRole) {
        this.targetRole = targetRole;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public abstract void send();
    public abstract void dismiss();
}
