package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.UserService;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("listU", userService.getAllUsers());
        return "admin-page";
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("getUserById", userService.getUserById(id));
        return "edit";
    }

    @PatchMapping("{id}/edit")
    public String updateUser(@ModelAttribute("user") @Valid User user,
                             @RequestParam(required = false, name = "roles") Long[] roles,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin";
        }
        Set<Role> roleSet = userService.findRolesSetById(roles);
        user.setRoles(roleSet);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}/delete")
    public String removeUser(@PathVariable("id") int id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }

    @GetMapping("{id}")
    public String userInfo(@PathVariable("id")int id, Model model){
        model.addAttribute("user", userService.getUserById(id));
        return "user-info";
    }
}
