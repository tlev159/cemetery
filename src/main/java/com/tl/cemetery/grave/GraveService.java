package com.tl.cemetery.grave;

import com.tl.cemetery.obituary.ObituaryDTO;
import com.tl.cemetery.obituary.ObituaryRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class GraveService {

    private ModelMapper modelMapper;

    private GraveRepository repository;
    private ObituaryRepository obituaryRepository;

    public GraveDTO createGrave(CreateGraveCommand command) {
        Grave graveTemplate = new Grave(command.getName(), command.getRow(), command.getColumn());
        if (repository.findAll().stream().noneMatch(g -> g.getName().equalsIgnoreCase(graveTemplate.getName())
                && g.getRow() == graveTemplate.getRow() && g.getColumn() == graveTemplate.getColumn())) {
            repository.save(graveTemplate);
        }
        return modelMapper.map(graveTemplate, GraveDTO.class);
    }

    public List<GraveDTO> listAllGraves() {
        return repository.findAll().stream()
                .map(g -> modelMapper.map(g, GraveDTO.class))
                .collect(Collectors.toList());
    }

    public List<GraveDTO> listAllGravesInAParcel(String name, Optional<Integer> row) {
        return repository.findAllGravesInParcel(name).stream()
                .filter(g -> row.isEmpty() || g.getRow() == row.get())
                .map(g -> modelMapper.map(g, GraveDTO.class))
                .collect(Collectors.toList());
    }

    public GraveDTO findGraveById(Long id) {
        Grave grave = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Grave not found!"));
        return modelMapper.map(grave, GraveDTO.class);
    }

    @Transactional
    public GraveDTO updateGrave(Long id, UpdateGraveCommand command) {
        Grave loadedGrave = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cannot found grave!"));
        loadedGrave.setName(command.getName());
        loadedGrave.setRow(command.getRow());
        loadedGrave.setColumn(command.getColumn());
        repository.save(loadedGrave);
        return modelMapper.map(loadedGrave, GraveDTO.class);
    }

    @Transactional
    public List<ObituaryDTO> listAllObituariesInGrave(FindObituariesInGraveCommand command) {
        String name = command.getName();
        int row = command.getRow();
        int column = command.getColumn();
        Grave grave = repository.findGraveByCommand(name, row, column);
        Long graveId = grave.getId();
        return obituaryRepository.findAllInGraveWithGraveId(graveId).stream().map(o -> modelMapper.map(o, ObituaryDTO.class)).collect(Collectors.toList());
    }
    public void deleteAllFromGraves() {
        repository.deleteAll();
    }

    public void deleteGraveById(Long id) {
        repository.deleteById(id);
    }

}
