package pl.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import pl.model.Course;
import pl.model.Review;
import pl.model.User;
import pl.model.Usercourses;
import pl.repository.ReviewRepository;
import pl.repository.UserCoursesRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserCoursesRepository userCoursesRepository;

    public List<Review> findUserReviews(User user) {
        return reviewRepository.findAllByUsercourses_User(user);
    }

    public List<Review> findCourseReviews(Course course) {
        return reviewRepository.findAllByUsercourses_Course(course);
    }

    public boolean addReviewToCourse(String text, Long score, Course course, User user) {
        if (null == text || text.isEmpty()) {
            return false;
        }

        Optional<Usercourses> usercoursesOptional = userCoursesRepository.findOneByCourseAndUser(course, user);
        if (!usercoursesOptional.isPresent()) {
            return false;
        }

        Usercourses usercourse = usercoursesOptional.get();

        Review review = new Review();
        review.setText(text);
        review.setDateAdded(new Date());
        if (null != score)
            review.setScore(score.longValue());

        review.setUsercourses(usercourse);
        usercourse.getReview().add(review);

        try {
            reviewRepository.save(review);
            userCoursesRepository.save(usercourse);
        } catch (DataAccessException e) {
            System.out.println("Exception caught while saving review for Course: " + course.getName() + " from User: " +
                    user.getEmail()
                    + " exception: " + e.getMessage());
            return false;
        }
        return true;
    }

}
