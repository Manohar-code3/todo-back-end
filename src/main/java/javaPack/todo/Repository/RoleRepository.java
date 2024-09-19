package javaPack.todo.Repository;

import javaPack.todo.Entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Roles,Long> {
    
    Roles findByName(String name);

}
