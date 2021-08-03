package com.tl.cemetery.onituary;

import com.tl.cemetery.grave.Grave;
import com.tl.cemetery.grave.GraveRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class ObituaryService {

    private ModelMapper modelMapper;

    private ObituaryRepository repository;
    private GraveRepository graveRepository;

    @Transactional
    public ObituaryDTO createObituary(CreateObituaryCommand command) {
        Obituary obituaryTemplate = new Obituary(command);
        Grave graveTemplate = graveRepository.findById(command.getGrave().getId()).orElseThrow(() -> new IllegalArgumentException("Grave not found"));
        if (repository.findAll().stream().noneMatch(o -> o.getName().equalsIgnoreCase(command.getName())
            && o.getNameOfMother().equalsIgnoreCase(command.getNameOfMother())
            && o.getDateOfBirth().equals(command.getDateOfBirth()))) {
            repository.save(obituaryTemplate);

        }
        graveTemplate.addObituary(obituaryTemplate);
        return modelMapper.map(obituaryTemplate, ObituaryDTO.class);
    }

    public ObituaryDTO findObituaryById(Long id) {
        return modelMapper.map(repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Obituary not found!")), ObituaryDTO.class);
    }


}
