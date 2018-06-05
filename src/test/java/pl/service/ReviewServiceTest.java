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
import pl.model.Review;
import pl.model.User;
import pl.model.Usercourses;
import pl.repository.ReviewRepository;
import pl.repository.UserCoursesRepository;
import pl.service.config.TestConfig;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
public class ReviewServiceTest {

    private final String review_text = "review_text";
    private final Long score = 9L;


    @Autowired
    private ReviewService reviewService;

    @MockBean
    private ReviewRepository reviewRepository;
    @MockBean
    private UserCoursesRepository userCoursesRepository;

    private Course course = new Course();
    private User user = new User();
    private Usercourses usercourses = new Usercourses();

    public ReviewServiceTest() {
    }

    @Before
    public void setUp() throws Exception {
        course.setName("Course_name_123");
        user.setEmail("foobar@bar.com");
        usercourses.setCourse(course);
        usercourses.setUser(user);
    }

    @After
    public void tearDown() throws Exception {

        verifyNoMoreInteractions(userCoursesRepository, reviewRepository);
    }


    @Test
    public void addNewReview() throws Exception {

        Review newReview = new Review();
        newReview.setUsercourses(usercourses);
        newReview.setScore(score);
        usercourses.getReview().add(newReview);

        given(this.userCoursesRepository.findOneByCourseAndUser(course, user)).willReturn(
                Optional.ofNullable(usercourses));
        given(this.reviewRepository.save(notNull())).willReturn(newReview);
        given(this.userCoursesRepository.save(usercourses)).willReturn(usercourses);

        assertTrue(reviewService.addReviewToCourse(review_text, score, course, user));

        verify(userCoursesRepository, Mockito.times(1)).save(usercourses);
        verify(reviewRepository, Mockito.times(1)).save(notNull());
        verify(userCoursesRepository, Mockito.times(1)).findOneByCourseAndUser(course, user);
    }

    @Test
    public void addNewReview_fails_user_not_on_course() throws Exception {
        given(this.userCoursesRepository.findOneByCourseAndUser(course, user)).willReturn(Optional.empty());


        assertFalse(reviewService.addReviewToCourse(review_text, score, course, user));

        verify(userCoursesRepository, Mockito.times(1)).findOneByCourseAndUser(course, user);
    }

    @Test
    public void addNewReview_fails_no_text() throws Exception {

        assertFalse(reviewService.addReviewToCourse("", score, course, user));

        verify(userCoursesRepository, Mockito.times(0)).findOneByCourseAndUser(course, user);
    }

    @Test
    public void addNewReview_accepts_no_score() throws Exception {
        Review newReview = new Review();
        newReview.setUsercourses(usercourses);
        usercourses.getReview().add(newReview);

        given(this.userCoursesRepository.findOneByCourseAndUser(course, user)).willReturn(
                Optional.ofNullable(usercourses));
        given(this.reviewRepository.save(notNull())).willReturn(newReview);
        given(this.userCoursesRepository.save(usercourses)).willReturn(usercourses);

        assertTrue(reviewService.addReviewToCourse(review_text, null, course, user));

        verify(userCoursesRepository, Mockito.times(1)).save(usercourses);
        verify(reviewRepository, Mockito.times(1)).save(notNull());
        verify(userCoursesRepository, Mockito.times(1)).findOneByCourseAndUser(course, user);
    }

}