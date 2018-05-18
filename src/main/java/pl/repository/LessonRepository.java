package pl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.model.Course;
import pl.model.Lesson;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findByCourse(Course Course);

    @Modifying
    @Transactional
    @Query("delete from Lesson l where l.idLesson = ?1")
    void deleteByIdLesson(Long idLesson);

    List<Lesson> findAllByCourse(Course course_1);
}
