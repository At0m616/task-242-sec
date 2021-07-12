package web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import web.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleDao {

    Role findRoleById(Long id);

    Role findRoleByName(String name);

    Set<Role> findRolesSetById(Long[] id);

    List<Role> getAllRoles();
}
