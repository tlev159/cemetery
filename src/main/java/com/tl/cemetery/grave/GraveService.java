package com.tl.cemetery.grave;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class GraveService {

    private ModelMapper modelMapper;

    private GraveRepository repository;

    public GraveDTO createGrave(CreateGraveCommand command) {
        Grave graveTemplate = new Grave(command.getName(), command.getRow(), command.getColumn());
        if (!repository.findAll().stream().filter(g -> g.getName().equalsIgnoreCase(graveTemplate.getName()) && g.getRow() == graveTemplate.getRow() && g.getColumn() == graveTemplate.getColumn()).findFirst().isPresent()) {
            repository.save(graveTemplate);
        }
        return modelMapper.map(graveTemplate, GraveDTO.class);
    }

    public List<GraveDTO> listAllGraves() {
        return repository.findAll().stream()
                .map(g -> modelMapper.map(g, GraveDTO.class))
                .collect(Collectors.toList());
    }

    public GraveDTO findGraveById(Long id) {
        Grave grave = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Grave not found!"));
        return modelMapper.map(grave, GraveDTO.class);
    }

    public void deleteAllFromGraves() {
        repository.deleteAll();
    }
}
