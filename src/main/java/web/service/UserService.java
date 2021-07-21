package web.service;

import web.model.User;

import java.util.List;


public interface UserService {
    boolean addUser(User user);

    User getUserById(long id);

    User getUserByName(String name);

    void updateUser(User user);

    void removeUser(long id);

    List<User> getAllUsers();



}
