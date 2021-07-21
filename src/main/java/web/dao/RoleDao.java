package web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import web.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleDao {

    Role findRoleById(Long id);

    Set<Role> findRolesSetById(Long[] id);

    Role findRoleByName(String name);

    Set<Role> findRoleSetByName(String[] names);

    List<Role> getAllRoles();
}
