package project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.model.Role;
import project.model.User;
import project.service.RoleService;
import project.service.UserService;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminApiController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminApiController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value= "/get_roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping(value = "/get_all_users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
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

    @PostMapping(value = "/save_user")
    public ResponseEntity<?> saveUser(@RequestBody User user, @RequestParam(value = "roles") String[] roles) {
        if (Arrays.asList(roles).contains("all")) {
            user.setRoleList(roleService.getAllRoles());
        } else {
            user.setRoleList(roleService.getListOfRoles(roles));
        }

        if (userService.saveUser(user)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }
    }

    @PutMapping(value = "/update_user")
    public HttpStatus updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return HttpStatus.OK;
    }

    @DeleteMapping(value = "/delete_user/{id}")
    public HttpStatus deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return HttpStatus.OK;
    }

}
