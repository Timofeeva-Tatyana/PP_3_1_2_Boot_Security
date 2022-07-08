package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    public Set<Role> getRoles(String[] roles) {
        Set<Role> roleSet = new HashSet<>();
        for (String role : roles){
            roleSet.add(roleService.findRoleByName(role));
        }
        return roleSet;
    }

    @GetMapping("/admin/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "all_users";
    }

    @GetMapping("/admin/users/new")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("role", new ArrayList<Role>());
        return "new_user";
    }

    @PostMapping("/admin/users")
    public String createUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "role") String[] roles) {
        user.setRoles(getRoles(roles));
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/{id}/edit")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "edit";
    }

    @PatchMapping("/admin/users/{id}")
    public String updateUser(@ModelAttribute("user") User user,@PathVariable("id") Long id,
                             @RequestParam(value = "role") String[] roles) {
        user.setRoles(getRoles(roles));
        userService.updateUser(id, user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/admin/users/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/{id}")
    public String showUserPage(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "users";
    }
}

