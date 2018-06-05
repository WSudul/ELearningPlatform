package pl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.model.Course;
import pl.model.Review;
import pl.model.User;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByUsercourses_Course(Course course);

    List<Review> findAllByUsercourses_CourseAndUsercourses_CompletedIsTrue(Course course);

    List<Review> findAllByUsercourses_User(User user);


}
