package com.tl.cemetery.leaseholder;

import com.tl.cemetery.grave.Grave;
import com.tl.cemetery.grave.GraveRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class LeaseholderService {

    private ModelMapper modelMapper;

    private LeaseholderRepository repository;
    private GraveRepository graveRepository;

    @Transactional
    public LeaseholderDTO createLeaseholder(CreateLeaseholderCommand command) {
        Leaseholder leaseholderTemlpate = new Leaseholder(command);
        Grave graveTemplate = graveRepository.findById(command.getGraveId()).orElseThrow(() -> new IllegalArgumentException("Grave with id(" + command.getGraveId() + ") not found!"));
        if (repository.findAll().stream().noneMatch(l -> l.getName().equalsIgnoreCase(leaseholderTemlpate.getName())
                && l.getAddress().equalsIgnoreCase(leaseholderTemlpate.getAddress()))) {
            repository.save(leaseholderTemlpate);
        }
        leaseholderTemlpate.addGrave(graveTemplate);
        return modelMapper.map(leaseholderTemlpate, LeaseholderDTO.class);
    }
}
