package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    private final RoleService roleService;


    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        List<User> userList =userService.getAllUsers();
        model.addAttribute("listU", userList);
        return "admin-page";
    }

    @GetMapping("/new-user")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("listRoles", roleService.getAllRoles());
        return "registration";
    }
    @PostMapping("/new-user")
    public String createNewUser(@ModelAttribute("user") @Valid User userForm, BindingResult bindingResult,
                                @RequestParam(required = false, name = "roles") Long[] rolesId) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.addUser(userForm, rolesId);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("getUserById", user);
        model.addAttribute("listRoles", roles );
        return "edit";
    }

    @PatchMapping("/edit")
    public String updateUser(@ModelAttribute("user") @Valid User user,
                             @RequestParam(required = false, name = "roles") Long[] roles) {
        userService.updateUser(user, roles);
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
