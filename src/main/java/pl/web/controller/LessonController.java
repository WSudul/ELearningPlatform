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
    private final long ROLE_TEACHER_ID = 2;
    private final long ROLE_STUDENT_ID = 4;

    private UserService userService;
    private CourseService courseService;
    private LessonService lessonService;
    private AccessService accessService;

    private RequestAccessService requestAccessService;
    private CourseGradeService courseGradeService;
    private MessageService messageService;
    //private StudentGradeService studentGradeService;

    private StudentGradeService studentGradeService;

    @Autowired
    public void setStudentGradeService(StudentGradeService studentGradeService) {
        this.studentGradeService = studentGradeService;
    }

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

    @GetMapping("/user/promoteToTeacher")
    public String promoteUserToTeacher(@RequestParam(defaultValue="1") String idUser, Model model) {
        User user = userService.findUserById(Long.parseLong(idUser, 10));
        userService.deleteUserByID(Long.parseLong(idUser, 10));
        user.getRoles().clear();

        userService.addWithTeacherRole(user);
        List <User> allUsers = userService.findAllUser();
        model.addAttribute("allUsers", allUsers);
        return "user/home";
    }

    @GetMapping("/user/allLessons")
    public String showLessonsInCourse(@RequestParam(defaultValue = "1") String idCourse, Model model, Principal principal) {
        currentIdCourse = idCourse;

        System.out.println(principal.getName());
        Optional<User> userOpt = userService.findUserByEmail(principal.getName());
        User user = userOpt.get();
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

    @GetMapping("/user/checkGrades")
    public String checkingGrades(Model model, Principal principal) {
        List <StudentGrade> allGrades = studentGradeService.findStudentGrades(userService.findUserByEmail(principal.getName()).get().getId(),currentCourse);
        model.addAttribute("allGrades", allGrades);
        model.addAttribute("currentCourse", currentCourse);
        model.addAttribute("idCourse", currentIdCourse);
        String info="";
        if (allGrades.isEmpty())
            info="Jeszcze nie dostałeś/aś żadnej oceny.";
        model.addAttribute("info", info);
        return "user/checkGrades";
    }

    @GetMapping("/user/statistics")
    public String statistics(Model model, Principal principal) {
        List<Access> accesses = accessService.findAccessWithUsers(ROLE_STUDENT_ID, Long.parseLong(currentIdCourse, 10));
        model.addAttribute("numberOfLessons", currentCourse.getLessons().size());
        model.addAttribute("numberOfQuizes", currentCourse.getQuizes().size());
        model.addAttribute("idCourse", currentIdCourse);
        model.addAttribute("numberOfStudents", accesses.size());
        return "user/statistics";
    }

    @GetMapping("/user/sendMessage")
    public String sendMessage(Model model) {
        model.addAttribute("message", new Message());
        model.addAttribute("idCourse", currentIdCourse);
        return "user/sendMessage";
    }

    @PostMapping("/user/sendMessage")
    public String sendMessage(@ModelAttribute Message message, Model model,BindingResult bindResult, Principal principal) {
        if(bindResult.hasErrors()) {
            return "user/sendUser";
        }
        List<Access> accesses = accessService.findAccessWithUsers(ROLE_TEACHER_ID, Long.parseLong(currentIdCourse, 10));
        String teacherEmail = userService.findUserById(accesses.get(0).getUserid()).getEmail();
        System.out.println("email nauczyciela " + teacherEmail+ " " + message.getTitle());
        message.setTo(teacherEmail);
        message.setFrom(principal.getName());
        messageService.sendMessage(message);
        model.addAttribute("idCourse", currentIdCourse);
        return "user/sendMessage";
    }

    @GetMapping("/user/receiveMessageStudent")
    public String receiveMessageStudent(Model model, Principal principal) {
        List<Message> allMessages=messageService.findAllMessages(principal.getName());
        model.addAttribute("allMessages", allMessages);
        model.addAttribute("idCourse", currentIdCourse);
        return "user/receiveMessageStudent";
    }

    @GetMapping("/user/displayMessageStudent")
    public String displayMessageStudent(@RequestParam(defaultValue="1") String idMessage, Model model, Principal principal) {
        System.out.println(idMessage);
        model.addAttribute("idCourse", currentIdCourse);
        Message msg=messageService.findMessageById(Long.parseLong(idMessage, 10)).get();
        model.addAttribute("message", msg);
        studentEmail = msg.getFrom();
        return "user/displayMessageStudent";
    }

    @GetMapping("/user/sendMessageToStudent")
    public String sendMessageToStudent(Model model) {
        model.addAttribute("message", new Message());
        model.addAttribute("idCourse", currentIdCourse);
        return "user/sendMessageToStudent";
    }

    @PostMapping("/user/sendMessageToStudent")
    public String sendMessageToStudent(@ModelAttribute Message message, Model model,BindingResult bindResult, Principal principal) {
        if(bindResult.hasErrors()) {
            return "user/sendUser";
        }
        List<Access> accesses = accessService.findAccessWithUsers(ROLE_TEACHER_ID, Long.parseLong(currentIdCourse, 10));
        String teacherEmail = userService.findUserById(accesses.get(0).getUserid()).getEmail();
        System.out.println("email nauczyciela " + teacherEmail+ " " + message.getTitle());
        message.setTo(studentEmail); // ZMIENIC
        message.setFrom(teacherEmail);
        messageService.sendMessage(message);
        model.addAttribute("idCourse", currentIdCourse);
        return "user/sendMessageToStudent";
    }

    @GetMapping("/user/receiveMessage")
    public String receiveMessage(Model model, Principal principal) {
        List<Message> allMessages=messageService.findAllMessages(principal.getName());
        model.addAttribute("allMessages", allMessages);
        model.addAttribute("idCourse", currentIdCourse);
        return "user/receiveMessage";
    }

    @GetMapping("/user/displayMessage")
    public String displayMessage(@RequestParam(defaultValue="1") String idMessage, Model model, Principal principal) {
        System.out.println(idMessage);
        model.addAttribute("idCourse", currentIdCourse);
        Message msg=messageService.findMessageById(Long.parseLong(idMessage, 10)).get();
        model.addAttribute("message", msg);
        studentEmail = msg.getFrom();
        return "user/displayMessage";
    }

    @GetMapping("/user/insertGrade")
    public String insertingGrades(Model model, Principal principal) {
        List<Access> accesses = accessService.findAccessWithUsers(ROLE_STUDENT_ID, Long.parseLong(currentIdCourse, 10));
        List<User> allStudentsInCourse = new LinkedList<>();
        for(Access access : accesses) {
            allStudentsInCourse.add(userService.findUserById(access.getUserid()));
        }

        model.addAttribute("studentGradeService", studentGradeService);
        model.addAttribute("currentCourse", currentCourse);
        model.addAttribute("allUsers", allStudentsInCourse );
        model.addAttribute("studentGrade", new StudentGrade());
        model.addAttribute("idCourse", currentIdCourse);

        return "user/insertGrade";
    }

    @PostMapping("/user/insertGrade")
    public String insertingGradesToDatabase(@RequestParam(defaultValue="1") String idUser, @ModelAttribute StudentGrade studentGrade, Model model, Principal principal, BindingResult bindResult) {
        List<Access> accesses = accessService.findAccessWithUsers(ROLE_STUDENT_ID, Long.parseLong(currentIdCourse, 10));
        List<User> allStudentsInCourse = new LinkedList<>();
        for(Access access : accesses) {
            allStudentsInCourse.add(userService.findUserById(access.getUserid()));
        }
        model.addAttribute("studentGradeService", studentGradeService);
        model.addAttribute("currentCourse", currentCourse);
        model.addAttribute("allUsers", allStudentsInCourse );
        model.addAttribute("idCourse", currentIdCourse);
        model.addAttribute("studentGrade", new StudentGrade());
        if (studentGrade.getGrade()==null)
            return "user/insertGrade";
        if(bindResult.hasErrors() || (studentGrade.getGrade()!=2 && studentGrade.getGrade()!=3 && studentGrade.getGrade()!=3.5 && studentGrade.getGrade()!=4 && studentGrade.getGrade()!=4.5 && studentGrade.getGrade()!=5)) {
            System.out.println("blad");
            return "user/insertGradeError";
        }

        studentGrade.setCourse(currentCourse);
        studentGrade.setUserid(Long.parseLong(idUser, 10));

        studentGradeService.addNewStudentGrade(studentGrade);
        System.out.println(userService.findUserByEmail(principal.getName()).get().getId());
        return "user/insertGrade";
    }

    @GetMapping("/user/usersOfCourse")
    public String manageUsersInCourse(Model model) {
        List<Access> accesses = accessService.findAccessWithUsers(ROLE_STUDENT_ID, Long.parseLong(currentIdCourse, 10));
        List<User> allStudentsInCourse = new LinkedList<>();
        for(Access access : accesses) {
            allStudentsInCourse.add(userService.findUserById(access.getUserid()));
        }
        model.addAttribute("allUsers", allStudentsInCourse );
        model.addAttribute("idCourse", currentIdCourse);
        return "user/usersOfCourse";
    }

    @GetMapping("/user/removeUserFromCourse")
    public String removeUser(@RequestParam(defaultValue="1") String idUser, Model model) {
        List<Access> accesses = accessService.findAccess(Long.parseLong(idUser, 10), Long.parseLong(currentIdCourse, 10));
        if(!accesses.isEmpty()) {
            accessService.removeUserFromSubject(accesses.get(0).getIdaccess());
        }

        model.addAttribute("idCourse", currentIdCourse);
        return "user/usersOfCourse";
    }

}