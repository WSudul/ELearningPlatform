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
import pl.model.*;
import pl.repository.*;
import pl.service.config.TestConfig;

import java.util.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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
    @MockBean
    private LessonRepository lessonRepository;
    @MockBean
    private QuizRepository quizRepository;
    @MockBean
    private QuestionRepository questionRepository;

    private Set<Tag> tagSet_1 = new HashSet<>();
    private Course course = new Course();

    public CourseServiceTest() {
        tagSet_1.add(new Tag(tag_1));
        tagSet_1.add(new Tag(tag_2));

        course.setDescription("course_descirtipion_foo_bar bar bar");
        course.setName("Course_name_123");
        course.setTagSet(tagSet_1);
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void addNewCourse() throws Exception {

        given(this.courseRepository.save(course)).willReturn(course);

        assertTrue(courseService.addNewCourse(course));
        Mockito.verify(courseRepository, Mockito.times(1)).save(course);
    }

    @Test
    public void addNewCourse_fail_empty_description() throws Exception {

        course.setDescription("");

        assertFalse(courseService.addNewCourse(course));
        Mockito.verify(courseRepository, Mockito.times(0)).save(course);
    }

    @Test
    public void addNewCourse_fail_empty_name() throws Exception {

        course.setName("");

        assertFalse(courseService.addNewCourse(course));
        Mockito.verify(courseRepository, Mockito.times(0)).save(course);
    }

    @Test
    public void addTagsToCourse() throws Exception {
        long courseId = 123;
        Set<Tag> newTags = new HashSet<>();
        newTags.add(new Tag("new_tag_1"));
        newTags.add(new Tag("new_tag_2"));

        List<Tag> savedTags = new ArrayList<>(newTags);

        given(this.courseRepository.findById(courseId)).willReturn(Optional.of(course));
        given(this.courseRepository.save(course)).willReturn(course);
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

    @Test
    public void addLessonToCourse() throws Exception {
        String lessonName = "Lesson_1";
        String lessonContent = "foo-bar-bla";
        Lesson lesson = new Lesson();
        lesson.setName(lessonName);
        lesson.setContent(lessonContent);
        long courseId = 123;

        given(this.courseRepository.findById(courseId)).willReturn(Optional.of(course));
        given(this.lessonRepository.save(lesson)).willReturn(lesson);
        given(this.courseRepository.save(course)).willReturn((course));

        assertTrue(courseService.addLessonToCourse(courseId, lessonName, lessonContent));
    }

    @Test
    public void addLessonToCourse_fails_on_empty_name() throws Exception {
        String lessonName = "";
        String lessonContent = "foo-bar-bla";
        long courseId = 123;

        assertFalse(courseService.addLessonToCourse(courseId, lessonName, lessonContent));
    }

    @Test
    public void addLessonToCourse_fails_on_empty_content() throws Exception {
        String lessonName = "Lesson_1";
        String lessonContent = "foo-bar-bla";
        long courseId = 123;

        assertFalse(courseService.addLessonToCourse(courseId, lessonName, lessonContent));
    }

    @Test
    public void addLessonToCourse_fails_on_non_existing_course() throws Exception {
        String lessonName = "Lesson_1";
        String lessonContent = "";
        long courseId = 123;
        given(this.courseRepository.findById(courseId)).willReturn(Optional.empty());
        assertFalse(courseService.addLessonToCourse(courseId, lessonName, lessonContent));
    }

    @Test
    public void removeLessonFromCourse() throws Exception {
        long lessonId = 42;
        long courseId = 123;
        Lesson lesson = new Lesson();
        course.getLessons().add(lesson);


        given(this.courseRepository.findById(courseId)).willReturn(Optional.of(course));
        given(this.lessonRepository.findById(lessonId)).willReturn(Optional.of(lesson));

        assertTrue(courseService.removeLessonFromCourse(courseId, lessonId));

    }

    @Test
    public void removeLessonFromCourse_fails_no_course() throws Exception {
        long lessonId = 42;
        long courseId = 123;
        Lesson lesson = new Lesson();

        given(this.courseRepository.findById(courseId)).willReturn(Optional.empty());
        given(this.lessonRepository.findById(lessonId)).willReturn(Optional.of(lesson));

        assertFalse(courseService.removeLessonFromCourse(courseId, lessonId));
    }

    @Test
    public void removeLessonFromCourse_fails_no_lesson() throws Exception {
        long lessonId = 42;
        long courseId = 123;

        given(this.courseRepository.findById(courseId)).willReturn(Optional.of(course));
        given(this.lessonRepository.findById(lessonId)).willReturn(Optional.empty());

        assertFalse(courseService.removeLessonFromCourse(courseId, lessonId));
    }

    @Test
    public void removeLessonFromCourse_fails_lesson_not_in_course() throws Exception {
        long lessonId = 42;
        long courseId = 123;
        Lesson lesson = new Lesson();
        lesson.setIdLesson(lessonId);
        {
            Lesson exisitingLesson1 = new Lesson();
            exisitingLesson1.setIdLesson(1L);
            Lesson exisitingLesson2 = new Lesson();
            exisitingLesson2.setIdLesson(2L);
            course.getLessons().add(exisitingLesson1);
            course.getLessons().add(exisitingLesson2);
        }
        given(this.courseRepository.findById(courseId)).willReturn(Optional.of(course));
        given(this.lessonRepository.findById(lessonId)).willReturn(Optional.of(lesson));

        assertFalse(courseService.removeLessonFromCourse(courseId, lessonId));
    }


    private final String newCourseName = "NewCourseName";
    private final String newCourseDescription = "NewCourseDescription";
    private final long updatedCourseId = 12345;

    @Test
    public void updateCourse() throws Exception {

        given(this.courseRepository.findById(updatedCourseId)).willReturn(Optional.of(course));

        assertTrue(courseService.updateCourse(updatedCourseId, newCourseName, newCourseDescription));

    }

    @Test
    public void updateCourse_fail_no_course() throws Exception {

        given(this.courseRepository.findById(updatedCourseId)).willReturn(Optional.empty());

        assertFalse(courseService.updateCourse(updatedCourseId, newCourseName, newCourseDescription));

    }

    @Test
    public void updateCourseName() throws Exception {
        given(this.courseRepository.findById(updatedCourseId)).willReturn(Optional.of(course));

        assertTrue(courseService.updateCourseName(updatedCourseId, newCourseName));
    }

    @Test
    public void updateCourseName_fail_on_empty() throws Exception {
        given(this.courseRepository.findById(updatedCourseId)).willReturn(Optional.of(course));

        assertFalse(courseService.updateCourseName(updatedCourseId, ""));
    }

    @Test
    public void updateCourseDescription() throws Exception {
        given(this.courseRepository.findById(updatedCourseId)).willReturn(Optional.of(course));

        assertTrue(courseService.updateCourseDescription(updatedCourseId, newCourseDescription));
    }

    @Test
    public void updateCourseDescription_fail_on_empty() throws Exception {
        given(this.courseRepository.findById(updatedCourseId)).willReturn(Optional.of(course));

        assertFalse(courseService.updateCourseDescription(updatedCourseId, ""));
    }

    @Test
    public void addQuizToCourse() throws Exception {
        long courseId = 123;
        String quizName = "tacticalName";
        Quiz quiz = new Quiz();
        quiz.setName(quizName);

        given(this.courseRepository.findById(courseId)).willReturn(Optional.of(course));
        given(this.quizRepository.save(quiz)).willReturn(quiz);
        given(this.courseRepository.save(course)).willReturn((course));
        assertTrue(courseService.addQuizToCourse(courseId, quizName));

    }

    @Test
    public void addQuizToCourse_fails_on_empty_name() {
        long courseId = 123;
        String quizName = "";

        assertFalse(courseService.addQuizToCourse(courseId, quizName));
    }

    @Test
    public void addQuizToCourse_fails_on_non_existing_course() {
        long courseId = 123;
        String quizName = "";

        given(this.courseRepository.findById(courseId)).willReturn(Optional.empty());
        assertFalse(courseService.addQuizToCourse(courseId, quizName));
    }

    @Test
    public void removeQuizFromCourse() {
        long courseId = 123;
        long quizId = 666;

        Quiz quiz = new Quiz();
        course.getQuizes().add(quiz);

        given(this.courseRepository.findById(courseId)).willReturn(Optional.of(course));
        given(this.quizRepository.findById(quizId)).willReturn(Optional.of(quiz));

        assertTrue(courseService.removeQuiz(courseId, quizId));
    }

    @Test
    public void removeQuizFromCourse_fails_no_course() {
        long courseId = 123;
        long quizId = 666;

        Quiz quiz = new Quiz();

        given(this.courseRepository.findById(courseId)).willReturn(Optional.empty());
        given(this.quizRepository.findById(quizId)).willReturn(Optional.of(quiz));
        assertFalse(courseService.removeQuiz(courseId, quizId));
    }

    @Test
    public void removeQuizFromCourse_fails_no_quiz() {
        long courseId = 123;
        long quizId = 666;


        given(this.courseRepository.findById(courseId)).willReturn(Optional.of(course));
        given(this.quizRepository.findById(quizId)).willReturn(Optional.empty());
        assertFalse(courseService.removeQuiz(courseId, quizId));
    }

    private Quiz quiz = new Quiz();
    private final long quizId = 123;
    private final String quizName = "tacticalName";
    private String questionArg = "W ktorym roku byla bitwa pod grunwaldem?";
    private String ans1 = "666";
    private String ans2 = "1410";
    private String ans3 = "966";
    private String ans4 = "1527";
    private Long correctAnsw = 2L;

    @Test
    public void addQuestionToQuiz() {
        Question question = new Question();
        question.setQuest(questionArg);
        question.setAnswer1(ans1);
        question.setAnswer2(ans2);
        question.setAnswer3(ans3);
        question.setAnswer4(ans4);
        question.setCorrect_answer(correctAnsw);

        quiz.setName(quizName);
        quiz.setCourse(course);
        quiz.getQuestions().add(question);

        given(this.quizRepository.findById(quizId)).willReturn(Optional.of(quiz));
        given(this.questionRepository.save(notNull())).willReturn(question);
        given(this.quizRepository.save(quiz)).willReturn(quiz);

        assertTrue(courseService.addQuestionToQuiz(quizId, ans1, ans2, ans3, ans4, correctAnsw, questionArg));

        verify(quizRepository, Mockito.times(1)).save(quiz);
        verify(questionRepository, Mockito.times(1)).save(notNull());
        verify(quizRepository, Mockito.times(1)).findById(quizId);
    }

    @Test
    public void addQuestionToQuiz_fails_no_answer() {
        given(this.quizRepository.findById(quizId)).willReturn(Optional.of(quiz));
        assertFalse(courseService.addQuestionToQuiz(quizId, "", "", "", "", correctAnsw, questionArg));

        verify(quizRepository, Mockito.times(1)).findById(quizId);
    }

    @Test
    public void addQuestionToQuiz_fails_no_question() {
        given(this.quizRepository.findById(quizId)).willReturn(Optional.of(quiz));
        assertFalse(courseService.addQuestionToQuiz(quizId, ans1, ans2, ans3, ans4, correctAnsw, ""));

        verify(quizRepository, Mockito.times(1)).findById(quizId);
    }

    @Test
    public void addQuestionToQuiz_fails_no_quiz() {
        assertFalse(courseService.addQuestionToQuiz(quizId, ans1, ans2, ans3, ans4, correctAnsw, questionArg));
    }

    @Test
    public void removeQuestionFromQuiz() {
        long questionId = 321;

        Question question = new Question();
        quiz.getQuestions().add(question);

        given(this.quizRepository.findById(quizId)).willReturn(Optional.of(quiz));
        given(this.questionRepository.findById(questionId)).willReturn(Optional.of(question));

        assertTrue(courseService.removeQuestionFromQuiz(quizId, questionId));
    }

    @Test
    public void removeQuestionFromQuiz_fails_no_quiz() {
        long questionId = 321;
        Question question = new Question();

        given(this.quizRepository.findById(quizId)).willReturn(Optional.empty());
        given(this.questionRepository.findById(questionId)).willReturn(Optional.of(question));

        assertFalse(courseService.removeQuestionFromQuiz(quizId, questionId));
    }

    @Test
    public void removeQuestionFromQuiz_fails_no_question() {
        long questionId = 321;

        given(this.quizRepository.findById(quizId)).willReturn(Optional.of(quiz));
        given(this.questionRepository.findById(questionId)).willReturn(Optional.empty());

        assertFalse(courseService.removeQuestionFromQuiz(quizId, questionId));
    }
}