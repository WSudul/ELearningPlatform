package pl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.model.Course;
import pl.model.StudentGrade;
import pl.repository.StudentGradeRepository;


import java.util.List;

@Service
public class StudentGradeService{

    private StudentGradeRepository studentGradeRepository;

    @Autowired
    public void setStudentGradeRepository(StudentGradeRepository studentGradeRepository) {
        this.studentGradeRepository = studentGradeRepository;
    }

    public void addNewStudentGrade(StudentGrade studentGrade) {
        studentGradeRepository.save(studentGrade);
    }

    public List<StudentGrade> findStudentGrades(Long userid, Course course) {
        return studentGradeRepository.findAllGradesByUseridAndCourse(userid, course);
    }
}
