package com.tl.cemetery.obituary;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
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

    @GetMapping("/search")
    @Operation(summary = "Find obituaries by name, name of mother or date of birth")
    public List<ObituaryDTO> findByNameNameOfMotherAndDataOfBirth(@RequestParam Optional<String> name, @RequestParam Optional<String> nameOfMother, @RequestParam Optional<LocalDate> dateOfBirth) {
        return service.findByNameNameOfMotherAndDataOfBirth(name, nameOfMother, dateOfBirth);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update obituary data by id")
    public ObituaryDTO updateObituary(@PathVariable("id") Long id, @Valid @RequestBody UpdateObituaryCommand command) {
        return service.updateObituary(id, command);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete obituary by id", description = "Delete obituary by id")
    public void deleteObituaryById(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    @DeleteMapping
    @Operation(summary = "Delete all obituaries", description = "Delete all obituaries")
    public void deleteAllObituaries() {
        service.deleteAllObituaries();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Problem> handleNotFound(IllegalArgumentException iae) {
        Problem problem = Problem.builder()
                .withType(URI.create("obituary/obituary-not-found"))
                .withTitle("Obituary not found")
                .withStatus(Status.NOT_FOUND)
                .withDetail(iae.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }
}
