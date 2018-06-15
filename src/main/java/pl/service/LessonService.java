package pl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.model.Course;
import pl.model.Lesson;
import pl.repository.LessonRepository;


import java.util.List;
import java.util.Optional;

@Service
public class LessonService {

    private LessonRepository lessonRepository;

    @Autowired
    public void setLessonRepository(LessonRepository lessonRepository) { this.lessonRepository = lessonRepository; }

    public List<Lesson> findAllLessons() {
        return lessonRepository.findAll();
    }

    public Optional<Lesson> findLessonById(long idLesson) {
        return Optional.of(lessonRepository.getOne(idLesson));
    }

    public List<Lesson> findLessonByCourse(Course course) {
        return lessonRepository.findAllByCourse(course);
    }

    public void addNewLessons(Lesson lesson) {
        lessonRepository.save(lesson);
    }

}
