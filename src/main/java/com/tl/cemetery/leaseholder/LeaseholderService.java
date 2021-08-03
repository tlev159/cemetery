package com.tl.cemetery.leaseholder;

import com.tl.cemetery.grave.Grave;
import com.tl.cemetery.grave.GraveRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class LeaseholderService {

    private ModelMapper modelMapper;

    private LeaseholderRepository repository;
    private GraveRepository graveRepository;

    @Transactional
    public LeaseholderDTO createLeaseholder(CreateLeaseholderCommand command) {
        Leaseholder leaseholderTemplate = new Leaseholder(command);
        Grave graveTemplate = graveRepository.findById(command.getGraveId()).orElseThrow(() -> new IllegalArgumentException("Grave with id(" + command.getGraveId() + ") not found!"));
        if (repository.findAll().stream().noneMatch(l -> l.getName().equalsIgnoreCase(leaseholderTemplate.getName())
                && l.getAddress().equalsIgnoreCase(leaseholderTemplate.getAddress()))) {
            repository.save(leaseholderTemplate);
        }
        leaseholderTemplate.addGrave(graveTemplate);
        return modelMapper.map(leaseholderTemplate, LeaseholderDTO.class);
    }

    public List<LeaseholderDTO> listAllLeaseholder(Optional<String> name) {
        return repository.findAll().stream()
                .filter(l -> name.isEmpty() || l.getName().equalsIgnoreCase(name.get()))
                .map(l -> modelMapper.map(l, LeaseholderDTO.class)).collect(Collectors.toList());
    }


}
