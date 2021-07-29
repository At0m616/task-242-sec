package web.dao;

import org.springframework.stereotype.Repository;
import web.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
     public void addRoleAdmin() {
        Role admin = new Role(1L, "ROLE_ADMIN");
        entityManager.persist(admin);
    }

    @Override
    public void addRoleUser() {
        Role user = new Role(2L, "ROLE_USER");
        entityManager.persist(user);
    }

    @Override
    public Role findRoleById(Long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public Set<Role> findRolesSetById(Long[] id) {
        Set<Role> roleSet = new HashSet<>();
        for (Long i : id) {
            roleSet.add(findRoleById(i));
        }
        return roleSet;
    }

    @Override
    public Role findRoleByName(String name) {
        TypedQuery<Role> query = entityManager
                .createQuery("select r from Role r where  r.name =:name", Role.class)
                .setParameter("name", name);
        return query.getResultList().stream().findAny().orElse(null);
    }

    @Override
    public Set<Role> findRoleSetByName(String[] names) {
        Set<Role> roleSet = new HashSet<>();
        for (String s : names) {
            roleSet.add(findRoleByName(s));
        }
        return roleSet;
    }

    @Override
    public List<Role> getAllRoles() {
        TypedQuery<Role> typedQuery = entityManager.createQuery("SELECT r FROM  Role r", Role.class);
        return typedQuery.getResultList();
    }
}
