package web.dao;

import web.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleDao {

    void addRoleAdmin();

    void addRoleUser();

    Role findRoleById(Long id);

    Set<Role> findRolesSetById(Long[] id);

    Role findRoleByName(String name);

    Set<Role> findRoleSetByName(String[] names);

    List<Role> getAllRoles();
}
