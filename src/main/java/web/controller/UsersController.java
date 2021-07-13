package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Set;


@Controller
public class UsersController {
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

    @GetMapping("/registration")
    public String newUser(Model model) {
        User user = new User();
        model.addAttribute(user);
        model.addAttribute("roles", userService.getAllRoles());
        return "registration";
    }

    @PostMapping("/registration")
    public String createNewUser(@ModelAttribute("user") @Valid User userForm,
                                @RequestParam(required = false, name = "roles") Long[] role,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        Set<Role> roleSet = userService.findRolesSetById(role);
        userForm.setRoles(roleSet);

        userService.addUser(userForm);
        return "redirect:/";
    }

    @GetMapping("/admin")
    public String getAllUsers(Model model) {
        model.addAttribute("listU", userService.getAllUsers());
        return "admin-page";
    }
    @DeleteMapping ("/admin/{id}/delete")
    public String removeUser(@PathVariable("id") int id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/edit")
    public String editUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("getUserById", userService.getUserById(id));
        model.addAttribute("roles", userService.getAllRoles());
        return "edit";
    }

    @PatchMapping("/admin/{id}/edit")
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

    @GetMapping("/userInfo")
    public String userData(Principal principal, Model model) {
        User user = userService.getUserByName(principal.getName());
        model.addAttribute("user", user);
        return "user-info";
    }

}
