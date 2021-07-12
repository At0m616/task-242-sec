package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.model.Role;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class JpaUserDaoImpl implements UserDao {

    @Autowired
    private RoleDao roleDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(User user) {
        Set<Role> roles = new HashSet<>();
        if(user.getRoles().contains(new Role("ROLE_ADMIN"))){
            roles.add(roleDao.findRoleById(1L));
//            user.setRoles(Collections.singleton(new Role(1L, "ROLE_ADMIN")));
        }
        roles.add(roleDao.findRoleById(2L));
//        user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));

        entityManager.persist(user);
    }

    @Override
    public User getUserById(long id) {
        TypedQuery<User> query = entityManager
                .createQuery("select u from User u where u.id = :id", User.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public User getUserByName(String name) {
        TypedQuery<User> query = entityManager
                .createQuery("select u from User u where  u.username =:name", User.class)
                .setParameter("name", name);

        return query.getResultList().stream().findAny().orElse(null);

    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery
                ("select u from User u", User.class)
                .getResultList();
    }

    @Override
    public void updateUser(User user) {
        User userUpdated = getUserById(user.getId());
        userUpdated.setUsername(user.getUsername());
        userUpdated.setPassword(user.getPassword());
//        userUpdated.setRoles(user.getRoles());
    }

    @Override
    public void removeUser(long id) {
        entityManager.remove(getUserById(id));
    }
}
