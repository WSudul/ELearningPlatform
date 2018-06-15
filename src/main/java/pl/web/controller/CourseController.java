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

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class CourseController {

    private CourseService courseService;
    private UserService userService;
    private AccessService accessService;
    //private LessonService lessonService;
    private CourseGradeService courseGradeService;
    private RequestAccessService requestAccessService;
    private final long ROLE_TEACHER_ID = 2;
    private final long ROLE_STUDENT_ID = 4;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setSubjectService(CourseService courseService) {
        this.courseService = courseService;
    }

    @Autowired
    public void setAccessService(AccessService accessService) {
        this.accessService = accessService;
    }

    @Autowired
    public void setCourseGradeService(CourseGradeService courseGradeService) {
        this.courseGradeService = courseGradeService;
    }

    @Autowired
    public void setRequestAccessService(RequestAccessService requestAccessService) {
        this.requestAccessService = requestAccessService;
    }

    @GetMapping("/user/addCourse")
    public String addNewSubject(Model model) {
        model.addAttribute("course", new Course());
        return "user/addNewCourse";
    }

    @PostMapping("/user/addCourse")
    public String addUser(@ModelAttribute @Valid Course course,
                          BindingResult bindResult, Principal principal, Model model) {
        if(bindResult.hasErrors())
            return "user/addNewCourse";
        else {
            /*
            String tag_1 = "tag_1";
            tagSet_1.add(new Tag(tag_1));*/
            Set<Tag> tagSet = new HashSet<>();
            System.out.println(course.getTags());
            String[] courseTags = course.getTags().split(",");
            for(int i = 0; i< courseTags.length; i++) {
                tagSet.add(new Tag(courseTags[0].trim()));
            }
            System.out.println(tagSet.toString());
            course.setTagSet(tagSet);
            courseService.addNewCourse(course);
            Access access = new Access(userService.findUserByEmail(principal.getName()).get().getId(), ROLE_TEACHER_ID, course.getId());
            accessService.addNewAccess(access);

            Optional<User> userOpt = userService.findUserByEmail(principal.getName());
            User user = userOpt.get();
            String role = "";
            Set<UserRole> userRoles = user.getRoles();
            for(UserRole userRole : userRoles) {
                role = userRole.getRole();
            }
            model.addAttribute("role", role);
            return "user/courses";
        }
    }

    @GetMapping("/user/allCourses")
    public String showAllSubjects(Model model) {
        List<Course> allCourses = courseService.findAllCourses();
        model.addAttribute("courseGradeService", courseGradeService);
        model.addAttribute("allCourses", allCourses);
        model.addAttribute("chosenCourse", new Course());
        return "user/allCourses";
    }

    /*@GetMapping("/user/manageUsers")
    public String manageUsers(Model model) {
        List<User> allUsers = userService.findAllUser();
        model.addAttribute("allUsers", allUsers);

        return "user/manageUsers";
    }*/

    /*@GetMapping("/user/deleteUser")
    public String removeUser(@RequestParam(defaultValue="1") String idUser, Model model) {
        userService.deleteUserByID(Long.parseLong(idUser, 10));
        List <User> allUsers = userService.findAllUser();
        model.addAttribute("allUsers", allUsers);
        return "user/manageUsers";
    }



    @GetMapping("/user/deleteSubjects")
    public String displaySubject(Model model) {
        List<Subject> allSubjects = subjectService.findAllSubjects();
        model.addAttribute("allSubjects", allSubjects);
        return "user/deleteSubjects";
    }

    @PostMapping("/user/deleteSubjects")
    public String removeSubject(@RequestParam(defaultValue="1") String idSubject, Model model) {
        subjectService.deleteSubjectByID(Long.parseLong(idSubject, 10));
        List<Subject> allSubjects = subjectService.findAllSubjects();
        model.addAttribute("allSubjects", allSubjects);
        return "user/deleteSubjects";
    }*/

    @GetMapping("/user/userRequestToCourse")
    public String userRequestToCourse(@RequestParam(defaultValue="1") String idCourse, Model model, Principal principal) {
        User user =userService.findUserByEmail(principal.getName()).get();
        //List<Access> accesses = accessService.findAccess(user.getId(), Long.parseLong(idSubject, 10));
        List<RequestAccess> requestedAccesses = requestAccessService.findRequestAccess(user.getId(), Long.parseLong(idCourse, 10));
        if(requestedAccesses.isEmpty()) { //oznacza że rola to ROLE_USER - zarejestrowany uzytkownik bez specjalnych praw
            RequestAccess access = new RequestAccess(user.getId(),ROLE_STUDENT_ID,Long.parseLong(idCourse, 10));
            requestAccessService.addNewAccess(access);
            //dodanie do access(userid,roleid=204Taa,subjectid)
        } else {
            System.out.println("ROLA Z WYZSZYM DOSTEPEM");
        }
        return "user/home";
    }

}
