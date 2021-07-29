package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.RoleDao;
import web.dao.UserDao;
import web.model.Role;
import web.model.User;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final RoleDao roleDao;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    @Override
    public User addUser(User user, Long[] roles) {
        User userFindDB = userDao.getUserByName(user.getUsername());

        if (userFindDB != null) {
            return userFindDB;
        }
        if ((roleDao.findRoleById(1L) == null)
                || (roleDao.findRoleById(2L) == null)) {
            roleDao.addRoleAdmin();
            roleDao.addRoleUser();
        }
        Set<Role> role = roleDao.findRolesSetById(roles);

        user.setRoles(role);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.addUser(user);
        return user;
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserById(long id) {
        return userDao.getUserById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    @Transactional
    @Override
    public void updateUser(User user, Long[] roles) {
        User modifyUser = userDao.getUserById(user.getId());

        Set<Role> roleSet = roleDao.findRolesSetById(roles);
        modifyUser.setRoles(roleSet);

        if (!user.getPassword().equals(modifyUser.getPassword())) {
            modifyUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        userDao.updateUser(modifyUser);
    }

    @Transactional
    @Override
    public void removeUser(long id) {
        userDao.removeUser(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

}
