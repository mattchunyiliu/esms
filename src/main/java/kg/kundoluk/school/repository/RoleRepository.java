package kg.kundoluk.school.repository;

import kg.kundoluk.school.model.references.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByCode(String code);

    Set<Role> findAllByIdIn(List<Long> ids);

    Boolean existsByCode(String code);
}
