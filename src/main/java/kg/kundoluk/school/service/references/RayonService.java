package kg.kundoluk.school.service.references;

import kg.kundoluk.school.constants.CacheService;
import kg.kundoluk.school.model.location.Rayon;
import kg.kundoluk.school.repository.location.RayonRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RayonService {

    private final RayonRepository rayonRepository;

    public RayonService(RayonRepository rayonRepository) {
        this.rayonRepository = rayonRepository;
    }

    //@Cacheable(value = CacheService.RAYON)
    public List<Rayon> list() {
        return rayonRepository.findAll();
    }
}
