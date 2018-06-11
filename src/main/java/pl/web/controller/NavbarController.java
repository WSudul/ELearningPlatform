package pl.web.controller;


import java.security.Principal;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import pl.model.User;
import pl.model.UserRole;
import pl.repository.EmailSender;
import pl.service.UserService;
//import pl.model.Email;
//import pl.service.EmailSenderService;

@Controller
public class NavbarController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /*private EmailSender emailSender;

    @Autowired
    public void setEmailService(EmailSenderService emailSender) {
        this.emailSender = emailSender;
    }*/

    @RequestMapping("/user/courses")
    public String courses(Principal principal, Model model) {
        Optional<User> userOpt = userService.findUserByEmail(principal.getName());
        User user = userOpt.get();
        String role = "";
        Set <UserRole> userRoles = user.getRoles();
        for(UserRole userRole : userRoles) {
            role = userRole.getRole();
        }
        model.addAttribute("role", role);
        return "user/courses";
    }
    /*
    @RequestMapping("/user/contact")
    public String contact(Model model) {
        Email email = new Email();
        model.addAttribute("email", email);
        return "user/contact";
    } */
    @RequestMapping("/user/account")
    public String account(Principal principal, Model model) {
        Optional<User> userOpt = userService.findUserByEmail(principal.getName());
        User user = userOpt.get();
        model.addAttribute("user", user);
        return "user/account";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
}
