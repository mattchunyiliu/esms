package kg.kundoluk.school.service.references;

import kg.kundoluk.school.constants.CacheService;
import kg.kundoluk.school.model.references.Role;
import kg.kundoluk.school.repository.RoleRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Cacheable(value = CacheService.ROLE)
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    public Role findByCode(String code) {
        return roleRepository.findByCode(code);
    }

    public Role findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    public Set<Role> findAllByIdIn(List<Long> ids) {
        return roleRepository.findAllByIdIn(ids);
    }

    public boolean existByCode(String code) {
        return roleRepository.existsByCode(code);
    }

    public void delete(Long id) {
        roleRepository.deleteById(id);
    }
}
