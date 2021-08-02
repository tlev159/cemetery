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
        Grave grave = new Grave(command.getName(), command.getRow(), command.getColumn());
        repository.save(grave);
        return modelMapper.map(grave, GraveDTO.class);
    }

    public List<GraveDTO> listAllGraves() {
        return repository.findAll().stream()
                .map(g -> modelMapper.map(g, GraveDTO.class))
                .collect(Collectors.toList());
    }
}
