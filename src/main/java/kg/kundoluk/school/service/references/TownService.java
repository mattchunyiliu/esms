package kg.kundoluk.school.service.references;

import kg.kundoluk.school.dto.reference.TownDto;
import kg.kundoluk.school.model.location.Town;
import kg.kundoluk.school.repository.location.RayonRepository;
import kg.kundoluk.school.repository.location.TownRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TownService {

    private final TownRepository townRepository;
    private final RayonRepository rayonRepository;

    public TownService(TownRepository townRepository, RayonRepository rayonRepository) {
        this.townRepository = townRepository;
        this.rayonRepository = rayonRepository;
    }

    public List<Town> list() {
        return townRepository.findAll();
    }

    public List<Town> listByRayon(Long rayonId) {
        return townRepository.findAllByRayonId(rayonId);
    }

    public Town create(TownDto townDto){
        return townRepository.save(new Town().setTitle(townDto.getTitle()).setRayon(rayonRepository.getOne(townDto.getRayonId())));
    }

    public Town edit(TownDto townDto, Town town){
        return townRepository.save(town.setTitle(townDto.getTitle()).setRayon(rayonRepository.getOne(townDto.getRayonId())));
    }
}
