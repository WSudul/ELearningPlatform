package pl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.model.Course;
import pl.model.Tag;
import pl.repository.CourseRepository;
import pl.repository.TagRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TagRepository tagRepository;

//    @Autowired
//    public void setCourseRepository(CourseRepository courseRepository) {
//        this.courseRepository = courseRepository;
//    }
//
//    @Autowired
//    public void setTagRepository(TagRepository tagRepository) {
//        this.tagRepository = tagRepository;
//    }

    public boolean addNewCourse(Course course) {


        if (null != course && validateCourse(course)) {
            courseRepository.save(course);
            return true;
        } else
            return false;

    }

    public boolean addTagsToCourse(long courseId, Collection<Tag> tags) {
        Optional<Course> course = courseRepository.findById(courseId);

        if (course.isPresent())
            return addTagsToCourse(course.get(), tags);
        else
            return false;

    }

    public boolean addTagsToCourse(Course course, Collection<Tag> tags) {
        tagRepository.saveAll(tags);

        Set<Tag> tagSet = course.getTagSet();
        tagSet.addAll(tags);

        courseRepository.save(course);

        return true;

    }

    public List<Course> findAllCourses() {
        return courseRepository.findAll();

    }

    public Course findCourseById(Long courseId) {
        return courseRepository.getOne(courseId);

    }

    public void removeCourse(Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);

        if (course.isPresent()) {
            long id = course.get().getId();
            courseRepository.delete(course.get());
            System.out.println("Course " + course + " has been deleted");
        }

    }

    private boolean validateCourse(Course course) {
        //todo verify course
        boolean validCourse = true;

        if (course.getName() == null || course.getName().isEmpty()) {
            System.out.println("Course name is empty.");
            validCourse = false;
        }

        if (course.getName() == null || course.getDescription().isEmpty()) {
            System.out.println("Course description is empty.");
            validCourse = false;
        }

        return validCourse;
    }

}
