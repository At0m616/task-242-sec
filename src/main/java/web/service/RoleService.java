package web.service;

import org.springframework.stereotype.Service;
import web.model.Role;

import java.util.List;
import java.util.Set;

@Service
public interface RoleService {
    Role findRolesById(Long id);

    Role findRoleByName(String name);

    Set<Role> findRolesSetById(Long[] id);

    List<Role> getAllRoles();
}
