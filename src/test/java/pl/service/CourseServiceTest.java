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
import pl.model.Tag;
import pl.repository.CourseRepository;
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
    private Set<Tag> tagSet_1 = new HashSet<>();
    private Course newCourse = new Course();

    public CourseServiceTest() {
        tagSet_1.add(new Tag(tag_1));
        tagSet_1.add(new Tag(tag_2));

        newCourse.setDescription("course_descirtipion_foo_bar bar bar");
        newCourse.setName("Course_name_123");
        newCourse.setTagSet(tagSet_1);
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void addNewCourse() throws Exception {

        given(this.courseRepository.save(newCourse)).willReturn(newCourse);

        assertTrue(courseService.addNewCourse(newCourse));
        Mockito.verify(courseRepository, Mockito.times(1)).save(newCourse);
    }

    @Test
    public void addNewCourse_fail_empty_description() throws Exception {

        newCourse.setDescription(new String());

        assertFalse(courseService.addNewCourse(newCourse));
        Mockito.verify(courseRepository, Mockito.times(0)).save(newCourse);
    }

    @Test
    public void addNewCourse_fail_empty_name() throws Exception {

        newCourse.setName(new String());

        assertFalse(courseService.addNewCourse(newCourse));
        Mockito.verify(courseRepository, Mockito.times(0)).save(newCourse);
    }

    @Test
    public void addTagsToCourse() throws Exception {
        long courseId = 123;
        Set<Tag> newTags = new HashSet<>();
        newTags.add(new Tag("new_tag_1"));
        newTags.add(new Tag("new_tag_2"));

        List<Tag> savedTags = new ArrayList<>(newTags);

        given(this.courseRepository.findById(courseId)).willReturn(Optional.of(newCourse));
        given(this.courseRepository.save(newCourse)).willReturn(newCourse);
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

}