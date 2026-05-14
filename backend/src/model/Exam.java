package model;

/**
 * Model class representing an Exam in the examination system.
 */
public class Exam {
    private String id;
    private String title;
    private String courseCode;
    private int duration; // in minutes
    private int marks;
    private String date; // YYYY-MM-DD
    private String status;
    private String instructions;

    public Exam() {
    }

    public Exam(String id, String title, String courseCode, int duration, int marks, String date, String status, String instructions) {
        this.id = id;
        this.title = title;
        this.courseCode = courseCode;
        this.duration = duration;
        this.marks = marks;
        this.date = date;
        this.status = status;
        this.instructions = instructions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    /**
     * Converts the exam object into a string format suitable for file storage.
     * Format: id|title|courseCode|duration|marks|date|status|instructions
     */
    public String toFileString() {
        return id + "|" + title + "|" + courseCode + "|" + duration + "|" + marks + "|" + date + "|" + status + "|" + instructions;
    }
}
