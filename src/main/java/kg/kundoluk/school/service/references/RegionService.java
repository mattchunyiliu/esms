package kg.kundoluk.school.service.references;

import kg.kundoluk.school.constants.CacheService;
import kg.kundoluk.school.model.location.Region;
import kg.kundoluk.school.repository.location.RegionRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {

    private final RegionRepository regionRepository;

    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @Cacheable(value = CacheService.REGION)
    public List<Region> list() {
        return regionRepository.findAll();
    }
}
