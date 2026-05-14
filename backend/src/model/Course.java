package model;

/**
 * Model class representing a Course in the examination system.
 */
public class Course {
    private String courseCode;
    private String courseName;

    public Course() {
    }

    public Course(String courseCode, String courseName) {
        this.courseCode = courseCode;
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * Converts the course object into a string format suitable for file storage.
     * Format: courseCode|courseName
     */
    public String toFileString() {
        return courseCode + "|" + courseName;
    }
}
