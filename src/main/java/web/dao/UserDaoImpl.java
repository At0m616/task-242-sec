package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public User getUserById(long id) {
        TypedQuery<User> query = entityManager
                .createQuery("select distinct u from User u JOIN FETCH u.roles where u.id = :id", User.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public User getUserByName(String name) {
        TypedQuery<User> query = entityManager
                .createQuery("select distinct u from User u JOIN FETCH u.roles where u.username =:name ", User.class)
                .setParameter("name", name);

        return query.getResultList().stream().findAny().orElse(null);

    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery
                ("select distinct u from User u join fetch u.roles order by u.username", User.class)
                .getResultList();
    }

    @Override
    public void updateUser(User user) {
       entityManager.merge(user);
    }

    @Override
    public void removeUser(long id) {
        entityManager.remove(getUserById(id));
    }
}
