package project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.model.User;
import project.service.UserServiceImp;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UserApiController {
    private final UserServiceImp userService;

    public UserApiController(UserServiceImp userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/get_user", produces = "application/json")
    public ResponseEntity<User> getUserByUsername(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
