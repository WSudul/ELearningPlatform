package pl.web.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.model.User;
import pl.service.CourseService;
import pl.service.SuggestionService;
import pl.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    SuggestionService suggestionService;


    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "registerForm";
    }

    @PostMapping("/register")
    public String addUser(@ModelAttribute @Valid User user,
                          BindingResult bindResult) {
        if(bindResult.hasErrors() || !userService.verifyName(user.getFirstName()) || !userService.verifyName(user.getLastName()) || !userService.verifyMail(user.getEmail()))
            return "registerForm";
        else {
            userService.addWithDefaultRole(user);
            return "registerSuccess";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user/changeEmail")
    public String changeEmail(Model model) {
        User user = new User();
        user.setPassword("testowe");
        model.addAttribute("user", new User());
        return "user/changeEmail";
    }

    @PostMapping("/user/changeEmail")
    public String changeEmail(Model model, @ModelAttribute User user,
                              Principal principal) {
        if( principal.getName().equals(user.getEmail()) || ! userService.verifyMail(user.getEmail())
                || userService.findUserByEmail(user.getEmail()).isPresent())
            return "user/changeEmail";
        else {
            userService.changeEmail(user.getEmail(), principal.getName());
            return "redirect:/logout";
        }

    }

    @GetMapping("/user/deleteAccount")
    public String deleteAccount(Model model, Principal principal) {
        //User user=userService.findUserByEmail(principal.getName());
        //userService.deleteUser(user);
        return "redirect:/logout";

    }

}
