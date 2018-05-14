package pl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.model.Course;

import java.util.List;
import java.util.Set;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByNameContaining(String name);

    List<Course> findByTagSetName(Set<String> names);
}
