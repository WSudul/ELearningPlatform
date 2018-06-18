package pl.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.model.*;
import pl.service.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class QuizController {
    private String currentIdCourse;
    private Course currentCourse;
    private CourseService courseService;
    private QuizService quizService;
    private UserService userService;
    private LessonController lessonController;
    private int questionNr = 0;
    private String currentIdCourseTemp;
    private AccessService accessService;
    private List<String> answers = new ArrayList<>();
    private UserRole currentAccessRole;
    private Quiz currentQuiz;
    private final long ROLE_USER_ID = 1;
    private StudentGradeService studentGradeService;

    @Autowired
    public void setStudentGradeService(StudentGradeService studentGradeService) {
        this.studentGradeService = studentGradeService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @Autowired
    public void setLessonController(LessonController lessonController) {
        this.lessonController = lessonController;
    }

    @Autowired
    public void setAccessService(AccessService accessService) {
        this.accessService = accessService;
    }


    @Autowired
    public void setQuizService(QuizService quizService) {
        this.quizService = quizService;
    }


    @GetMapping("/user/addNewQuiz")
    public String addQuiz(@RequestParam(defaultValue = "1") String idCourse, Model model) {
        currentIdCourse = idCourse;
        System.out.println(currentIdCourse);
        model.addAttribute("quiz", new Quiz());
        model.addAttribute("idCourse", currentIdCourse);

        return "user/addNewQuiz";
    }

    @PostMapping("/user/addNewQuiz")
    public String addQuiz(@ModelAttribute Quiz quiz, Model model, BindingResult bindResult, Principal principal) {
        if (bindResult.hasErrors() || quiz.getName().equals("")) {
            return "user/addNewQuiz";
        } else {
            System.out.println("sdd2");
            quiz.setCourse(currentCourse);
            model.addAttribute("currentCourse", courseService.findCourseById(Long.parseLong(currentIdCourse, 10)));

            courseService.addQuizToCourse(Long.valueOf(currentIdCourse), quiz.getName());
            model.addAttribute("idCourse", currentIdCourse);

            User user = userService.findUserByEmail(principal.getName()).get();

            List<Access> access = accessService.findAccess(user.getId(), Long.parseLong(currentIdCourse, 10));
            if (!access.isEmpty()) {
                currentAccessRole = userService.findRoleById(access.get(0).getRoleid()).get();
            } else {
                currentAccessRole = userService.findRoleById(ROLE_USER_ID).get();
            }

            model.addAttribute("idCourse", currentIdCourse);
            model.addAttribute("currentAccessRole", currentAccessRole.getRole());
            currentCourse = courseService.findCourseById(Long.parseLong(currentIdCourse, 10)).get();
            model.addAttribute("currentCourse", currentCourse);
            System.out.println(currentAccessRole);
            return "user/allLessons";
        }
    }

    @GetMapping("/user/quiz")
    public String showQuiz(@RequestParam(defaultValue = "1") String idQuiz, Model model, Principal principal) {
        currentQuiz = quizService.findQuizById(Long.valueOf(idQuiz)).get();
        currentIdCourse = lessonController.getCurrentIdCourse();

        Optional<User> userOpt = userService.findUserByEmail(principal.getName());
        User user = userOpt.get();

        List<Access> access = accessService.findAccess(user.getId(), Long.parseLong(currentIdCourse, 10));
        System.out.println("access:" + access.get(0).getRoleid());
        if (!access.isEmpty()) {

            currentAccessRole = userService.findRoleById(access.get(0).getRoleid()).get();
        } else {
            currentAccessRole = userService.findRoleById(ROLE_USER_ID).get();
        }

        model.addAttribute("currentQuiz", currentQuiz);
        model.addAttribute("idCourse", currentIdCourse);
        model.addAttribute("currentAccessRole", currentAccessRole.getRole());
        return "user/quiz";
    }

    @GetMapping("/user/addNewQuestions")
    public String addQuestions(Model model) {
        model.addAttribute("question", new Question());
        model.addAttribute("idCourse", currentIdCourse);
        return "user/addNewQuestions";
    }

    @PostMapping("/user/addNewQuestions")
    public String addQuestionToDataBase(@ModelAttribute Question question, Model model, BindingResult bindResult) {
        if (bindResult.hasErrors() || question.getQuest().equals("")) {
            return "user/addNewQuestions";
        }
        System.out.println("sdd2");
        question.setQuiz(currentQuiz);

        model.addAttribute("currentSubject", courseService.findCourseById(Long.parseLong(currentIdCourse, 10)).get());

        courseService.addQuestionToQuiz(currentQuiz.getIdQuiz(),
                question.getAnswer1(), question.getAnswer2(),
                question.getAnswer3(), question.getAnswer4(),
                question.getCorrect_answer(), question.getQuest());

        model.addAttribute("idCourse", currentIdCourse);
        return "user/addNewQuestions";
    }

    @GetMapping("/user/takeAnQuiz")
    public String takeAnQuiz(@RequestParam(defaultValue = "1") String idCourse, Model model, Principal principal) {
        //DODANIE ZNALEZIENIA ROLI UZYTKOWNIKA W DANYM PRZEDMIOCIE
        currentIdCourse = idCourse;

        model.addAttribute("currentQuiz", currentQuiz);

        //ZABEZPIECZYĆ TO BO MOŻE NIE BYĆ PYTAŃ
        model.addAttribute("question", currentQuiz.getQuestions().get(questionNr));
        model.addAttribute("idCourse", currentIdCourse);
        model.addAttribute("answers", new String());
        currentCourse = courseService.findCourseById(Long.parseLong(currentIdCourse, 10)).get();
        model.addAttribute("currentCourse", currentCourse);
        return "user/takeAnQuiz";
    }


    @PostMapping("/user/takeAnQuiz")
    public String sendAnQuiz(@RequestParam("answer") String answer, Model model, Principal principal) {
        //DODANIE ZNALEZIENIA ROLI UZYTKOWNIKA W DANYM PRZEDMIOCIE
//		currentIdSubject = idSubject;
        answers.add(answer);
        System.out.println("Odpowiedzi " + answers.get(questionNr) + " " + questionNr);
        questionNr++;
        if (questionNr == currentQuiz.getQuestions().size()) {
            List<Question> questions = currentQuiz.getQuestions();
            int correctAnswers = 0;
            for (int i = 0; i < questionNr; i++) {
                if (questions.get(i).getCorrect_answer().equals(Long.parseLong(answers.get(i)))) {
                    correctAnswers++;
                }
            }
            System.out.println("Poprawnych odpowiedzi " + correctAnswers);
            double grade = 0;
            questionNr = 0;
            int userResultInPercentage = (correctAnswers * 100) / questions.size();
            StudentGrade studentGrade = new StudentGrade();
            if (userResultInPercentage > 91) {
                studentGrade.setGrade(5.0);
                grade = 5;
            } else if (userResultInPercentage > 81) {
                studentGrade.setGrade(4.5);
                grade = 4.5;
            } else if (userResultInPercentage > 71) {
                studentGrade.setGrade(4.0);
                grade = 4;
            } else if (userResultInPercentage > 61) {
                studentGrade.setGrade(3.5);
                grade = 3.5;
            } else if (userResultInPercentage > 51) {
                studentGrade.setGrade(3.0);
                grade = 3;
            } else {
                studentGrade.setGrade(2.0);
                grade = 2;
            }
            answers = new ArrayList<>();
            model.addAttribute("idCourse", currentIdCourse);
            model.addAttribute("userResult", correctAnswers);
            model.addAttribute("bestResult", questions.size());
            model.addAttribute("userResultInPercentage", userResultInPercentage);
            model.addAttribute("grade", grade);


            studentGrade.setCourse(currentCourse);
            studentGrade.setUserid((userService.findUserByEmail(principal.getName()).get().getId()));
            studentGradeService.addNewStudentGrade(studentGrade);
            return "user/quizResult";
        }
//		model.addAttribute("currentQuiz", currentQuiz);
//		model.addAttribute("idSubject", currentIdSubject);
//		currentSubject = subjectService.findSubjectByID(Long.parseLong(currentIdSubject, 10));
//		model.addAttribute("currentSubject", currentSubject);
        model.addAttribute("currentQuiz", currentQuiz);
        model.addAttribute("idCourse", currentIdCourse);
        model.addAttribute("question", currentQuiz.getQuestions().get(questionNr));

        return "user/takeAnQuiz";
    }

}
