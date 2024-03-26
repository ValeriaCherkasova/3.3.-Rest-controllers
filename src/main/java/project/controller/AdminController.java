package project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import project.model.Role;
import project.model.User;
import project.service.RoleServiceImp;
import project.service.UserServiceImp;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImp userService;
    private final RoleServiceImp roleService;

    public AdminController(UserServiceImp userService, RoleServiceImp roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/get_all_users")
    public String getAllUsers(ModelMap model, Principal principal) {
        List<User> users = userService.getAllUsers();
        User admin = userService.findByUsername(principal.getName());
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("users", users);
        model.addAttribute("user", admin);
        model.addAttribute("roles", roles);
        return "admin";
    }

    @PostMapping(value = "/delete_user")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/get_all_users";
    }

    @PostMapping(value = "/save_user")
    public String saveUser(@ModelAttribute("user") User user,
                           @RequestParam(value = "roles") String[] roles) {
        if (Arrays.asList(roles).contains("all")) {
            user.setRoleList(roleService.getAllRoles());
        } else {
            user.setRoleList(roleService.getListOfRoles(roles));
        }
        userService.saveUser(user);
        return "redirect:/admin/get_all_users";
    }

    @PostMapping(value = "/update_user")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "roles") String[] roles) {
        if (Arrays.asList(roles).contains("all")) {
            user.setRoleList(roleService.getAllRoles());
        } else {
            user.setRoleList(roleService.getListOfRoles(roles));
        }

        userService.updateUser(user);
        return "redirect:/admin/get_all_users";
    }

    @GetMapping(value = "/get_user")
    public String getUser(ModelMap model, @RequestParam("id") Long id) {
        Optional<User> user = userService.findById(id);
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("user", user.get());
        return "update_user";
    }

    @GetMapping(value = "/get_roles")
    public String getRoles(ModelMap model) {
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        return "add_user";
    }
}
