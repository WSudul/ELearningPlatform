package pl.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import pl.model.Course;
import pl.model.Lesson;
import pl.model.Tag;
import pl.repository.CourseRepository;
import pl.repository.LessonRepository;
import pl.repository.TagRepository;
import pl.service.config.TestConfig;

import java.util.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
public class CourseServiceTest {


    private final String tag_1 = "tag_1";
    private final String tag_2 = "tag_2";
    @Autowired
    private CourseService courseService;
    @MockBean
    private TagRepository tagRepository;
    @MockBean
    private CourseRepository courseRepository;
    @MockBean
    private LessonRepository lessonRepository;

    private Set<Tag> tagSet_1 = new HashSet<>();
    private Course course = new Course();

    public CourseServiceTest() {
        tagSet_1.add(new Tag(tag_1));
        tagSet_1.add(new Tag(tag_2));

        course.setDescription("course_descirtipion_foo_bar bar bar");
        course.setName("Course_name_123");
        course.setTagSet(tagSet_1);
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void addNewCourse() throws Exception {

        given(this.courseRepository.save(course)).willReturn(course);

        assertTrue(courseService.addNewCourse(course));
        Mockito.verify(courseRepository, Mockito.times(1)).save(course);
    }

    @Test
    public void addNewCourse_fail_empty_description() throws Exception {

        course.setDescription("");

        assertFalse(courseService.addNewCourse(course));
        Mockito.verify(courseRepository, Mockito.times(0)).save(course);
    }

    @Test
    public void addNewCourse_fail_empty_name() throws Exception {

        course.setName("");

        assertFalse(courseService.addNewCourse(course));
        Mockito.verify(courseRepository, Mockito.times(0)).save(course);
    }

    @Test
    public void addTagsToCourse() throws Exception {
        long courseId = 123;
        Set<Tag> newTags = new HashSet<>();
        newTags.add(new Tag("new_tag_1"));
        newTags.add(new Tag("new_tag_2"));

        List<Tag> savedTags = new ArrayList<>(newTags);

        given(this.courseRepository.findById(courseId)).willReturn(Optional.of(course));
        given(this.courseRepository.save(course)).willReturn(course);
        given(this.tagRepository.saveAll(newTags)).willReturn(savedTags);

        assertTrue(courseService.addTagsToCourse(courseId, newTags));

    }

    @Test
    public void addTagsToCourse_fail_no_course() throws Exception {
        long invalidCourseId = 123;
        Set<Tag> newTags = new HashSet<>();

        given(this.courseRepository.findById(invalidCourseId)).willReturn(Optional.empty());

        assertFalse(courseService.addTagsToCourse(invalidCourseId, newTags));
    }

    @Test
    public void findAllCourses() throws Exception {
        //todo
    }

    @Test
    public void findCourseById() throws Exception {
        //todo
    }

    @Test
    public void removeCourse() throws Exception {
        //todo
    }

    @Test
    public void addLessonToCourse() throws Exception {
        String lessonName = "Lesson_1";
        String lessonContent = "foo-bar-bla";
        Lesson lesson = new Lesson();
        lesson.setName(lessonName);
        lesson.setContent(lessonContent);
        long courseId = 123;

        given(this.courseRepository.findById(courseId)).willReturn(Optional.of(course));
        given(this.lessonRepository.save(lesson)).willReturn(lesson);
        given(this.courseRepository.save(course)).willReturn((course));

        assertTrue(courseService.addLessonToCourse(courseId, lessonName, lessonContent));
    }

    @Test
    public void addLessonToCourse_fails_on_empty_name() throws Exception {
        String lessonName = "";
        String lessonContent = "foo-bar-bla";
        long courseId = 123;

        assertFalse(courseService.addLessonToCourse(courseId, lessonName, lessonContent));
    }

    @Test
    public void addLessonToCourse_fails_on_empty_content() throws Exception {
        String lessonName = "Lesson_1";
        String lessonContent = "foo-bar-bla";
        long courseId = 123;

        assertFalse(courseService.addLessonToCourse(courseId, lessonName, lessonContent));
    }

    @Test
    public void addLessonToCourse_fails_on_non_existing_course() throws Exception {
        String lessonName = "Lesson_1";
        String lessonContent = "";
        long courseId = 123;
        given(this.courseRepository.findById(courseId)).willReturn(Optional.empty());
        assertFalse(courseService.addLessonToCourse(courseId, lessonName, lessonContent));
    }

    @Test
    public void removeLessonFromCourse() throws Exception {
        long lessonId = 42;
        long courseId = 123;
        Lesson lesson = new Lesson();
        course.getLessons().add(lesson);


        given(this.courseRepository.findById(courseId)).willReturn(Optional.of(course));
        given(this.lessonRepository.findById(lessonId)).willReturn(Optional.of(lesson));

        assertTrue(courseService.removeLessonFromCourse(courseId, lessonId));

    }

    @Test
    public void removeLessonFromCourse_fails_no_course() throws Exception {
        long lessonId = 42;
        long courseId = 123;
        Lesson lesson = new Lesson();

        given(this.courseRepository.findById(courseId)).willReturn(Optional.empty());
        given(this.lessonRepository.findById(lessonId)).willReturn(Optional.of(lesson));

        assertFalse(courseService.removeLessonFromCourse(courseId, lessonId));
    }

    @Test
    public void removeLessonFromCourse_fails_no_lesson() throws Exception {
        long lessonId = 42;
        long courseId = 123;

        given(this.courseRepository.findById(courseId)).willReturn(Optional.of(course));
        given(this.lessonRepository.findById(lessonId)).willReturn(Optional.empty());

        assertFalse(courseService.removeLessonFromCourse(courseId, lessonId));
    }

    @Test
    public void removeLessonFromCourse_fails_lesson_not_in_course() throws Exception {
        long lessonId = 42;
        long courseId = 123;
        Lesson lesson = new Lesson();
        lesson.setIdLesson(lessonId);
        {
            Lesson exisitingLesson1 = new Lesson();
            exisitingLesson1.setIdLesson(1L);
            Lesson exisitingLesson2 = new Lesson();
            exisitingLesson2.setIdLesson(2L);
            course.getLessons().add(exisitingLesson1);
            course.getLessons().add(exisitingLesson2);
        }
        given(this.courseRepository.findById(courseId)).willReturn(Optional.of(course));
        given(this.lessonRepository.findById(lessonId)).willReturn(Optional.of(lesson));

        assertFalse(courseService.removeLessonFromCourse(courseId, lessonId));
    }
}