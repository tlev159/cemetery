package com.tl.cemetery.obituary;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/obituaries")
public class ObituaryController {

    private ObituaryService service;

    @PostMapping
    @Operation(summary = "Create obituary", description = "Create grave")
    @ResponseStatus(HttpStatus.CREATED)
    public ObituaryDTO createObituary(@RequestBody CreateObituaryCommand command) {
        return service.createObituary(command);
    }

    @GetMapping("/{id}")
    @Operation(summary = "List obituary by id", description = "List obituary by id")
    public ObituaryDTO findObituaryById(@PathVariable("id") Long id) {
        return service.findObituaryById(id);
    }

    @GetMapping
    @Operation(summary = "List all obituaries with given name")
    public List<ObituaryDTO> listAllObituariesOptionalByName(@RequestParam Optional<String> name) {
        return service.findAllByName(name);
    }

    @DeleteMapping
    @Operation(summary = "Delete all obituaries", description = "Delete all obituaries")
    public void deleteAllObituaries() {
        service.deleteAllObituaries();
    }

}
