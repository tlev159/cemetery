package com.tl.cemetery.leaseholder;

import com.tl.cemetery.grave.Grave;
import com.tl.cemetery.grave.GraveRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.net.URI;
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

    @Transactional
    public LeaseholderDTO updateLeaseholderById(Long id, UpdateLeaseholderCommand command) {
        Leaseholder leaseholderTemplate = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Leaseholder not found!"));
        Grave graveTemplate = graveRepository.findById(command.getGraveId()).orElseThrow(() -> new IllegalArgumentException("Grave not found!"));
        setArgumentsForLeaseholder(command, leaseholderTemplate, graveTemplate);
        graveTemplate.setLeaseholder(leaseholderTemplate);
        repository.save(leaseholderTemplate);
        return modelMapper.map(leaseholderTemplate, LeaseholderDTO.class);
    }

    private void setArgumentsForLeaseholder(UpdateLeaseholderCommand command, Leaseholder leaseholderTemplate, Grave graveTemplate) {
        leaseholderTemplate.setName(command.getName());
        leaseholderTemplate.setAddress(command.getAddress());
        leaseholderTemplate.setTelephone(command.getTelephone());
        leaseholderTemplate.setLeasedAt(command.getLeasedAt());
        leaseholderTemplate.setType(command.getType());
        leaseholderTemplate.setGrave(graveTemplate);
    }

    public void deleteLeaseholderById(Long id) {
        repository.deleteById(id);
    }

    public void deleteAllLeaseholder() {
        repository.deleteAll();
    }
}
