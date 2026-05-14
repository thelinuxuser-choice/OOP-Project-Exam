package service;

import model.Course;
import util.FileHandler;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for handling CRUD operations on Courses.
 */
@Service
public class CourseService {
    private static final String FILE_PATH = "courses.txt";
    private List<Course> courseList;

    public CourseService() {
        this.courseList = new ArrayList<>();
        loadCoursesFromFile();
    }

    private void loadCoursesFromFile() {
        courseList.clear();
        List<String> lines = FileHandler.readFile(FILE_PATH);
        
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            
            String[] parts = line.split("\\|");
            if (parts.length >= 2) {
                String code = parts[0];
                String name = parts[1];
                courseList.add(new Course(code, name));
            }
        }
    }

    private void saveAllToFile() {
        List<String> data = new ArrayList<>();
        for (Course c : courseList) {
            data.add(c.toFileString());
        }
        FileHandler.writeFile(FILE_PATH, data);
    }

    public void addCourse(Course course) {
        // Prevent duplicates
        for (Course c : courseList) {
            if (c.getCourseCode().equalsIgnoreCase(course.getCourseCode())) {
                return; // Already exists
            }
        }
        courseList.add(course);
        FileHandler.appendFile(FILE_PATH, course.toFileString());
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courseList);
    }

    public boolean deleteCourse(String courseCode) {
        boolean removed = courseList.removeIf(c -> c.getCourseCode().equalsIgnoreCase(courseCode));
        if (removed) {
            saveAllToFile();
        }
        return removed;
    }
}
