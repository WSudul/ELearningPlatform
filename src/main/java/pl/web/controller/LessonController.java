package pl.web.controller;

import ch.qos.logback.core.CoreConstants;
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

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
public class LessonController {

    private String currentIdCourse;
    private Course currentCourse;
    private UserRole currentAccessRole;
    private Quiz currentQuiz;
    private int questionNr = 0;
    private List<String> answers = new ArrayList<>();
    private String studentEmail;
    private final long ROLE_USER_ID = 1;
    private final long ROLE_STUDENT_ID = 4;

    private UserService userService;
    private CourseService courseService;
    private LessonService lessonService;
    private AccessService accessService;

    private RequestAccessService requestAccessService;
    private CourseGradeService courseGradeService;
    private MessageService messageService;
    //private StudentGradeService studentGradeService;


    public String getCurrentIdCourse() {
        return currentIdCourse;
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
    public void setCourseGradeService(CourseGradeService courseGradeService) {
        this.courseGradeService = courseGradeService;
    }

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    /*
    @Autowired
    public void setStudentGradeService(StudentGradeService studentGradeService) {
        this.studentGradeService = studentGradeService;
    } */
    /*
    @GetMapping("/user/promoteToTeacher")
    public String promoteUserToTeacher(@RequestParam(defaultValue="1") String idUser, Model model) {
        User user = userService.findUserById(Long.parseLong(idUser, 10));
        userService.deleteUserByID(Long.parseLong(idUser, 10));
        user.getRoles().clear();
//		Set<UserRole> userRole = new TreeSet<>();
//		userRole.add(userService.findRoleById(202L));
//		user.setRoles(userRole);
        userService.addWithTeacherRole(user);
        List <User> allUsers = userService.findAllUser();
        model.addAttribute("allUsers", allUsers);
        return "user/home";
    }
*/
    @GetMapping("/user/allLessons")
    public String showLessonsInCourse(@RequestParam(defaultValue = "1") String idCourse, Model model, Principal principal) {
        currentIdCourse = idCourse;

        System.out.println(principal.getName());
        Optional<User> userOpt = userService.findUserByEmail(principal.getName());
        User user = userOpt.get();
        List<Access> access = accessService.findAccess(user.getId(), Long.parseLong(currentIdCourse, 10));
        System.out.println("access:" + access.get(0).getRoleid());
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

    @GetMapping("/user/addUserToCourse")
    public String addUserToCourse(@RequestParam(defaultValue = "1") String idUser, Model model, Principal principal) {
        User user = userService.findUserById(Long.parseLong(idUser, 10));
        List<Access> accesses = accessService.findAccess(user.getId(), currentCourse.getId());
        if (accesses.isEmpty()) { //oznacza że rola to ROLE_USER - zarejestrowany uzytkownik bez specjalnych praw
            Access access = new Access(user.getId(), ROLE_STUDENT_ID, currentCourse.getId());
            accessService.addNewAccess(access);
        } else {
            System.out.println("ROLA Z WYZSZYM DOSTEPEM");
        }
        return "user/home";
    }


    @GetMapping("/user/addNewLesson")
    public String addLesson(Model model) {
        System.out.println(currentIdCourse);
        model.addAttribute("lesson", new Lesson());
        model.addAttribute("idCourse", currentIdCourse);
        return "user/addNewLesson";
    }

    @PostMapping("/user/addNewLesson")
    public String addLesson(
            @ModelAttribute @Valid Lesson lesson, Model model,
            BindingResult bindResult, Principal principal) {
        if (bindResult.hasErrors())
            return "user/addNewLesson";
        else {
            lesson.setCourse(currentCourse);
            System.out.println("Lekcja: nazwa :" + lesson.getName() + "zawartosc:" + lesson.getContent() + " " + lesson.getCourse().getName());
            model.addAttribute("currentCourse", courseService.findCourseById(Long.parseLong(currentIdCourse, 10)));
            lessonService.addNewLessons(lesson);
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

    @GetMapping("/user/addUsersToCourse")
    public String addUsersToCourse(Model model) {
        List<RequestAccess> accesses = requestAccessService.findAccessWithUsers(ROLE_STUDENT_ID, Long.parseLong(currentIdCourse, 10));
        List<User> allStudentsJoiningCourse = new LinkedList<>();
        for (RequestAccess access : accesses) {
            System.out.println(access.getUserid());
            allStudentsJoiningCourse.add(userService.findUserById(access.getUserid()));
        }
        model.addAttribute("allUsers", allStudentsJoiningCourse);
        System.out.println(currentIdCourse);
        model.addAttribute("idCourse", currentIdCourse);
        return "user/addUsersToCourse";
    }

    @GetMapping("/user/lesson")
    public String showLesson(@RequestParam(defaultValue = "1") String idLesson, Model model) {
        model.addAttribute("currentLesson", lessonService.findLessonById(Long.parseLong(idLesson, 10)).get());
        model.addAttribute("idCourse", currentIdCourse);
        return "user/lesson";
    }
}