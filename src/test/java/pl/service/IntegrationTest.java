package pl.service;


import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import pl.model.*;
import pl.repository.LessonRepository;
import pl.repository.TagRepository;
import pl.repository.UserRoleRepository;
import pl.service.config.TestConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
public class IntegrationTest {

    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    LessonRepository lessonRepository;

    @Test
    public void userLookup() throws Exception {
        Optional<User> user = userService.findUserByEmail("jo@gmail.com");
        assertTrue(user.isPresent());
        assertEquals("Jan", user.get().getFirstName());
        assertEquals("Nowak", user.get().getLastName());


        Set<UserRole> roles = user.get().getRoles();
        assertFalse(roles.isEmpty());
        List<UserRole> all_roles = userRoleRepository.findAll();
        assertFalse(all_roles.isEmpty());
        assertEquals(roles.size(), all_roles.size());
        System.out.println("\n" + roles.toString());
        System.out.println("\n" + all_roles.toString());
        assertThat(Lists.newArrayList(roles), containsInAnyOrder(all_roles.toArray()));
    }

    @Test
    public void courseLookup() throws Exception {
        List<Course> courses = courseService.findAllCourses();

        assertFalse(courses.isEmpty());

        assertEquals(2, courses.size());

        //Verify Course 1 contents
        Course course_1 = courses.get(0);
        {
            assertEquals("Course 1", course_1.getName());
            assertEquals("Long text descrfiption course 1", course_1.getDescription());

            assertFalse(course_1.getTagSet().isEmpty());
            List<Long> course_tag_ids = new ArrayList<>();
            course_1.getTagSet().forEach(t -> course_tag_ids.add(t.getId()));

            List<Tag> tags = tagRepository.findAllById(course_tag_ids);
            assertEquals(course_1.getTagSet().size(), tags.size());
            assertThat(Lists.newArrayList(course_1.getTagSet()), containsInAnyOrder(tags.toArray()));

            List<Lesson> course_1_lessons = lessonRepository.findAllByCourse(course_1);
            assertEquals(course_1.getLessons().size(), course_1_lessons.size());

            assertEquals("Lesson 1", course_1_lessons.get(0).getName());
            assertEquals("Lesson 2", course_1_lessons.get(1).getName());

            assertEquals(course_1.getId(), course_1_lessons.get(0).getCourse().getId());
            assertEquals(course_1.getId(), course_1_lessons.get(1).getCourse().getId());


        }
        //Verify Course 2 contents
        Course course_2 = courses.get(1);
        {
            assertEquals("Course 2", course_2.getName());
            assertEquals("Long text descrfiption course 2", course_2.getDescription());

            assertFalse(course_2.getTagSet().isEmpty());
            List<Long> course_tag_ids = new ArrayList<>();
            course_2.getTagSet().forEach(t -> course_tag_ids.add(t.getId()));

            List<Tag> tags = tagRepository.findAllById(course_tag_ids);
            assertEquals(course_2.getTagSet().size(), tags.size());
            assertThat(Lists.newArrayList(course_2.getTagSet()), containsInAnyOrder(tags.toArray()));

            List<Lesson> course_2_lessons = lessonRepository.findAllByCourse(course_2);
            assertTrue(course_2_lessons.isEmpty());
            assertTrue(course_2.getLessons().isEmpty());
        }


    }


}
