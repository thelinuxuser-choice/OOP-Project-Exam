package controller;

import model.Course;
import service.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class to expose Course management operations via HTTP endpoints.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCourse(@RequestBody java.util.Map<String, String> payload) {
        try {
            String courseCode = payload.get("courseCode");
            String courseName = payload.get("courseName");

            if (courseCode == null || courseName == null) {
                return ResponseEntity.badRequest().body("Course code and name are required.");
            }

            Course course = new Course(courseCode, courseName);
            courseService.addCourse(course);
            return ResponseEntity.ok("Course added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid course format");
        }
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @DeleteMapping("/delete/{code}")
    public ResponseEntity<String> deleteCourse(@PathVariable("code") String code) {
        boolean success = courseService.deleteCourse(code);
        if (success) {
            return ResponseEntity.ok("Course " + code + " deleted successfully.");
        }
        return ResponseEntity.status(404).body("Course not found.");
    }
}
