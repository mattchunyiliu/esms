package kg.kundoluk.school.service.references;

import kg.kundoluk.school.model.references.ChronicleYear;
import kg.kundoluk.school.repository.ChronicleRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ChronicleService {
    private final ChronicleRepository chronicleRepository;

    public ChronicleService(ChronicleRepository chronicleRepository) {
        this.chronicleRepository = chronicleRepository;
    }

    public ChronicleYear create(ChronicleYear chronicleYear) {
        return chronicleRepository.save(chronicleYear);
    }


    public ChronicleYear edit(ChronicleYear src, ChronicleYear dst) {
        return chronicleRepository.save(dst.setActive(src.getActive()).setStartYear(src.getStartYear()).setEndYear(src.getEndYear()));
    }

    public List<ChronicleYear> list() {
        return chronicleRepository.findAll();
    }

    public ChronicleYear findById(Long id){
        return chronicleRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public ChronicleYear getActive(){
        return chronicleRepository.getByActiveIsTrue();
    }
}
