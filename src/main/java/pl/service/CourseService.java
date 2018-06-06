package pl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import pl.model.*;
import pl.repository.CourseRepository;
import pl.repository.LessonRepository;
import pl.repository.QuizRepository;
import pl.repository.TagRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private CourseRepository courseRepository;
    private TagRepository tagRepository;
    private LessonRepository lessonRepository;
    private QuizRepository quizRepository;

    @Autowired
    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Autowired
    public void setTagRepository(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Autowired
    public void setLessonRepository(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Autowired
    public void setQuizRepository(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public boolean addNewCourse(Course course) {

        if (null != course && validateCourse(course)) {
            try {
                SetValidTagsInCourse(course);
                courseRepository.save(course);
                return true;
            } catch (DataAccessException exception) {
                System.out.println(exception.getMessage());
                return false;
            }
        } else
            return false;

    }

    private void SetValidTagsInCourse(Course course) {
        Set<String> tagNames = extractTagNames(course.getTagSet());

        Set<Tag> existingTags = tagRepository.findAllByNameLikeIgnoreCase(tagNames);
        Set<String> existingNames = extractTagNames(existingTags);

        tagNames.removeAll(existingNames); //avoid duplications

        course.setTagSet(existingTags);
        addStringTagsToCourse(tagNames, course);
    }

    private void addStringTagsToCourse(Set<String> tagNames, Course course) {
        if (!tagNames.isEmpty()) {
            Set<Tag> newTags
                    = tagNames.stream().map(name -> {
                Tag tag = new Tag(name.toLowerCase());
                tag.getCourseSet().add(course);
                return tag;
            }).collect(Collectors.toSet());
            course.getTagSet().addAll(newTags);
        }
    }

    public boolean addTagsToCourse(long courseId, Set<Tag> tags) {
        Optional<Course> course = courseRepository.findById(courseId);

        if (course.isPresent() && (tags != null) && (!tags.isEmpty()))
            return addTagsToCourse(course.get(), tags);
        else
            return false;

    }

    public boolean addTagsToCourse(Course course, Set<Tag> tags) {

        Set<String> names = extractTagNames(tags); //extract string names of provided tags

        Set<Tag> existingTags = tagRepository.findAllByNameLikeIgnoreCase(names); //find already existing tags
        Set<String> existingNames = extractTagNames(existingTags);

        names.removeAll(existingNames); //avoid duplications

        if (!existingTags.isEmpty()) {
            course.getTagSet().addAll(existingTags);
            for (Tag tag : existingTags) {
                if (!tag.getCourseSet().add(course))
                    System.out.println("Tag " + tag.getName() + " already exists in course: " + course.getName());
            }
        }
        addStringTagsToCourse(names, course);

        courseRepository.save(course);

        return true;
    }

    public boolean removeTagsFromCourse(Course course, Set<Tag> tags) {


        Set<Tag> tagset = course.getTagSet();
        if (tagset.removeAll(tags)) {
            for (Tag tag : tags) {
                tag.getCourseSet().remove(course);
            }
            courseRepository.save(course);
            tagRepository.saveAll(tags);
            return true;
        } else
            return false;
    }


    public List<Course> findCoursesByName(String name) {
        return courseRepository.findByNameContaining(name);
    }

    public List<Course> findCoursesByTags(Set<Tag> tags) {
        Set<String> names = extractTagNames(tags);
        return courseRepository.findByTagSetName(names);
    }

    public List<Course> findAllCourses() {
        return courseRepository.findAll();

    }

    public Optional<Course> findCourseById(long courseId) {
        return Optional.of(courseRepository.getOne(courseId));

    }

    public boolean removeCourse(long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);

        if (course.isPresent()) {
            long id = course.get().getId();
            courseRepository.delete(course.get());
            System.out.println("Course " + course + " has been deleted");
            return true;
        } else {
            System.out.println("No course exists for id: " + courseId);
            return false;
        }

    }

    private boolean validateCourse(Course course) {
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

    private Set<String> extractTagNames(Set<Tag> tags) {
        return tags.stream().map(Tag::getName).collect(Collectors.toSet());
    }

    public boolean addLessonToCourse(long courseId, String lessonName, String lessonContent) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (!course.isPresent()) {
            System.out.println("No Course exists with id: " + courseId);
            return false;
        } else if (lessonName.isEmpty() || lessonContent.isEmpty()) {
            System.out.println("Course name or content cannot be empty!");
            return false;
        }


        Lesson lesson = new Lesson();
        lesson.setContent(lessonName);
        lesson.setContent(lessonContent);
        lesson.setCourse(course.get());

        try {
            lessonRepository.save(lesson);
            course.get().getLessons().add(lesson);
            courseRepository.save(course.get());
        } catch (DataAccessException ex) {
            System.out.println("Exception caught while saving lesson: " + ex.getMessage());
            return false;
        }

        return true;
    }

    public boolean removeLessonFromCourse(long courseId, long lessonId) {
        Optional<Course> course = courseRepository.findById(courseId);
        Optional<Lesson> lesson = lessonRepository.findById(lessonId);
        if (!course.isPresent()) {
            System.out.println("No Course exists with id: " + courseId);
            return false;
        } else if (!lesson.isPresent()) {
            System.out.println("No Lesson exists with id: " + lessonId);
            return false;
        }

        List<Lesson> lessons = course.get().getLessons();
        if (lessons.contains(lesson.get())) {
            try {
                lessonRepository.delete(lesson.get());
            } catch (DataAccessException ex) {
                System.out.println("Exception caught while deleting lesson: " + ex.getMessage());
                return false;
            }
            return true;
        } else {
            System.out.println("Lesson : " + lessonId + " does not belong to course : " + courseId);
            System.out.println("Exisitng courses: " + lessons.toString());
            return false;
        }

    }

    public boolean updateCourseName(long courseId, String newName) {
        return updateCourse(courseId, newName, null);
    }

    public boolean updateCourseDescription(long courseId, String newDescription) {
        return updateCourse(courseId, null, newDescription);
    }

    public boolean updateCourse(long courseId, String newName, String newDescription) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);

        if (!courseOptional.isPresent())
            return false;
        Course course = courseOptional.get();

        if (newName != null) {
            if (newName.isEmpty()) {
                System.out.println("Name cannot be empty");
                return false;
            } else {
                course.setName(newName);
            }
        }

        if (newDescription != null) {
            if (newDescription.isEmpty()) {
                System.out.println("Name cannot be empty");
                return false;
            } else {
                course.setDescription(newDescription);
            }
        }

        try {
            courseRepository.save(course);
        } catch (DataAccessException e) {
            System.out.println("Exception caught when saving course: " + e.getMessage());
            return false;
        }

        return true;

    }

    public boolean addQuizToCourse(long courseId, String quizName) {
        Optional<Course> course = courseRepository.findById(courseId);

        if (!course.isPresent()) {
            System.out.println("No Course exists with id: " + courseId);
            return false;
        } else if (quizName.isEmpty()) {
            System.out.println("Quiz name cannot be empty!");
            return false;
        }

        Quiz quiz = new Quiz();
        quiz.setName(quizName);
        quiz.setCourse(course.get());

        try {
            quizRepository.save(quiz);
            course.get().getQuizes().add(quiz);
            courseRepository.save(course.get());

        } catch (DataAccessException exception) {
            System.out.println("Exception during saving quiz: " + exception.getMessage());
        }
        return true;
    }

    public boolean removeQuiz(long courseId, long quizId) {
        Optional<Course> course = courseRepository.findById(courseId);
        Optional<Quiz> quiz = quizRepository.findById(quizId);

        //todo : delete questions along with quiz

        if (!course.isPresent()) {
            System.out.println("No Course exists with id: " + courseId);
            return false;
        } else if (!quiz.isPresent()) {
            System.out.println("No Quiz exists with id: " + quizId);
            return false;
        }

        List<Quiz> quizList = course.get().getQuizes();

        if (quizList.contains(quiz.get())) {
            try {
                quizRepository.delete(quiz.get());
            } catch (DataAccessException exception) {
                System.out.println("Exception during deleting quiz: " + exception.getMessage());
                return false;
            }

        } else {
            System.out.println("Quiz : " + quizId + " does not belong to course : " + courseId);
            return false;
        }
        return true;
    }
}
