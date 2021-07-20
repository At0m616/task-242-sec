package web.service;

import org.springframework.stereotype.Service;
import web.model.Role;
import web.model.User;

import java.util.List;
import java.util.Set;

@Service
public interface UserService {
    boolean addUser(User user);

    User getUserById(long id);

    User getUserByName(String name);

    void updateUser(User user);

    void removeUser(long id);

    List<User> getAllUsers();



}
