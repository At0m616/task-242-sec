package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import java.util.List;


@Controller
//@RequestMapping("/users")
public class UsersController {
    private final UserService userService;
    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String index(){
        return "index";
    }
    @GetMapping("/users")
    public String getAllUsers(Model model){
        List<User> list = userService.getAllUsers();
        model.addAttribute("listU", list);

        return "main-users";
    }

    @GetMapping("/users/new")
    public String newUser(@ModelAttribute("user") User user){
        return "new";
    }
    @PostMapping("/users/new")
    public String create(@ModelAttribute("user") User user){
        userService.addUser(user);
        return "redirect:/users";
    }


    @GetMapping("/users/{id}/delete")
    public String removeUser(@PathVariable("id") int id){
        userService.removeUser(id);
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/edit")
    public String editUser(@PathVariable("id") int id, Model model){
        model.addAttribute("getUserById", userService.getUserById(id));
        return "edit";
    }
    @PostMapping("/users/{id}/edit")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") int id){

        userService.updateUser(user);
        return "redirect:/users";
    }

    @GetMapping("/users/{id}")
    public String userData(@PathVariable("id") int id, Model model){
        model.addAttribute("user", userService.getUserById(id));
        return "show-one";
    }

}
