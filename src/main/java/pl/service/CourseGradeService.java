package pl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.model.Course;
import pl.model.CourseGrade;
import pl.repository.CourseGradeRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CourseGradeService {
    private CourseGradeRepository courseGradeRepository;

    @Autowired
    public void setCourseGradeRepository(CourseGradeRepository courseGradeRepository) {
        this.courseGradeRepository = courseGradeRepository;
    }

    public void addNewCourseGrade(CourseGrade courseGrade) {
        courseGradeRepository.save(courseGrade);
    }

    public List<CourseGrade> findIfYouRatedCourse(Long userid, Course course) {// :D
        return courseGradeRepository.findAllByUseridAndCourse(userid, course);
    }

    public String findAverageGradeInCourse(Course course) {
        List<CourseGrade> grades = courseGradeRepository.findAllByCourse(course);
        Double average = 0.0;
        if (!grades.isEmpty()) {
            for (CourseGrade grade : grades) {
                average += grade.getGrade();
            }
            average = average / grades.size();
            average = round(average, 2);
        }
        else
            return "Brak ocen";

        return average.toString();
    }

    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}

