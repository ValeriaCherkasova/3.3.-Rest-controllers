package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.model.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r WHERE r.name IN :name")
    List<Role> findByName(String[] name);

    @Query("SELECT r FROM Role r")
    List<Role> getAllRoles();
}
