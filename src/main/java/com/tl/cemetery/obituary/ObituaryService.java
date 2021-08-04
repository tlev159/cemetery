package com.tl.cemetery.obituary;

import com.tl.cemetery.grave.Grave;
import com.tl.cemetery.grave.GraveRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ObituaryService {

    private ModelMapper modelMapper;

    private ObituaryRepository repository;
    private GraveRepository graveRepository;

    @Transactional
    public ObituaryDTO createObituary(CreateObituaryCommand command) {
        Obituary obituaryTemplate = new Obituary(command);
        Grave graveTemplate = graveRepository.findById(command.getGraveId()).orElseThrow(() -> new IllegalArgumentException("Grave not found"));
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

    public List<ObituaryDTO> findAllByName(Optional<String> name) {
        return repository.findAll().stream()
                .filter(o -> name.isEmpty() || o.getName().equalsIgnoreCase(name.get()))
                .map(o -> modelMapper.map(o, ObituaryDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public ObituaryDTO updateObituary(Long id, UpdateObituaryCommand command) {
        Obituary obituaryTemplate = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Obituary not found!"));
        Grave graveTemplate = graveRepository.findById(command.getGraveId()).orElseThrow(() -> new IllegalArgumentException("Grave not found"));
        setArgumentsForObituary(command, obituaryTemplate, graveTemplate);
        graveTemplate.addObituary(obituaryTemplate);
        repository.save(obituaryTemplate);
        return modelMapper.map(obituaryTemplate, ObituaryDTO.class);
    }

    private void setArgumentsForObituary(UpdateObituaryCommand command, Obituary obituary, Grave grave) {
        obituary.setName(command.getName());
        obituary.setNameOfMother(command.getNameOfMother());
        obituary.setDateOfBirth(command.getDateOfBirth());
        obituary.setDateOfRIP(command.getDateOfRIP());
        obituary.setGrave(grave);
    }

    public List<ObituaryDTO> findByNameNameOfMotherAndDataOfBirth(Optional<String> name, Optional<String> nameOfMother, Optional<LocalDate> dateOfBirth) {
        return repository.findAll().stream()
                .filter(o -> name.isEmpty() || o.getName().equalsIgnoreCase(name.get()))
                .filter(o -> nameOfMother.isEmpty() || o.getNameOfMother().equalsIgnoreCase(nameOfMother.get()))
                .filter(o -> dateOfBirth.isEmpty() || o.getDateOfBirth().isEqual(dateOfBirth.get()))
                .map(o -> modelMapper.map(o, ObituaryDTO.class)).collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public void deleteAllObituaries() {
        repository.deleteAll();
    }
}
