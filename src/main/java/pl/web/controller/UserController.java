package pl.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

}
