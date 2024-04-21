package project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AdminController {
    public AdminController() {
    }

    @GetMapping(value = "/admin")
    public String adminPage() {
        return "admin";
    }
}
