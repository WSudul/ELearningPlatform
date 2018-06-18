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
import java.util.List;
import java.util.Optional;

@Controller
public class QuizController {
    private String currentIdCourse;
    private Course currentCourse;
    private CourseService courseService;
    private QuizService quizService;
    private UserService userService;
    private LessonService lessonService;
    private AccessService accessService;
    private RequestAccessService requestAccessService;
    private CourseGradeService courseGradeService;
    private MessageService messageService;
    private UserRole currentAccessRole;
    private Quiz currentQuiz;
    private final long ROLE_USER_ID = 1;


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @Autowired
    public void setLessonService(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @Autowired
    public void setAccessService(AccessService accessService) {
        this.accessService = accessService;
    }

    @Autowired
    public void setRequestAccessService(RequestAccessService requestAccessService) {
        this.requestAccessService = requestAccessService;
    }

    @Autowired
    public void setQuizService(QuizService quizService) {
        this.quizService = quizService;
    }

    @Autowired
    public void setCourseGradeService(CourseGradeService courseGradeService) {
        this.courseGradeService = courseGradeService;
    }

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
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

            return "user/allLessons";
        }
    }

    @GetMapping("/user/quiz")
    public String showQuiz(@RequestParam(defaultValue="1") String idQuiz, Model model) {
        currentQuiz = quizService.findQuizById(Long.valueOf(idQuiz)).get();

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
        if(bindResult.hasErrors() || question.getQuest().equals("")) {
            return "user/addNewQuestions";
        }
        System.out.println("sdd2");
        question.setQuiz(currentQuiz);
        //quiz.setSubject(currentSubject);
        model.addAttribute("currentSubject", courseService.findCourseById(Long.parseLong(currentIdCourse, 10)).get());

        courseService.addQuestionToQuiz(currentQuiz.getIdQuiz(),
                question.getAnswer1(), question.getAnswer2(),
                question.getAnswer3(), question.getAnswer4(),
                question.getCorrect_answer(), question.getQuest());
        model.addAttribute("idCourse", currentIdCourse);
        return "user/addNewQuestions";
    }
}
