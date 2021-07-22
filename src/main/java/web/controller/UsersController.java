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
import java.security.Principal;
import java.util.List;
import java.util.Set;


@Controller
public class UsersController {
    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public UsersController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/registration")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("listRoles", roleService.getAllRoles());
        return "registration";
    }
    @PostMapping("/registration")
    public String createNewUser(@ModelAttribute("user") @Valid User userForm, BindingResult bindingResult,
                                @RequestParam(required = false, name = "roles") Long[] role) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        Set<Role> roleSet = roleService.findRolesSetById(role);
        userForm.setRoles(roleSet);
        userService.addUser(userForm);
        return "redirect:/";
    }



    @GetMapping("/userInfo")
    public String userData(Principal principal, Model model) {
        User user = userService.getUserByName(principal.getName());
        model.addAttribute("user", user);
        return "user-info";
    }

}
