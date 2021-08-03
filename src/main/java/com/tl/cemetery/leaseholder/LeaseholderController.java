package com.tl.cemetery.leaseholder;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/leaseholder")
@Tag(name = "Operations with leaseholders", description = "Operations with leaseholders")
public class LeaseholderController {

    private LeaseholderService service;

    @PostMapping
    @Schema(name = "create new leaseholder", description = "create new leaseholder")
    private LeaseholderDTO createLeaseholder(@Valid @RequestBody CreateLeaseholderCommand command) {
        return service.createLeaseholder(command);
    }

    @GetMapping
    @Schema(name = "list all leaseholder or optional only they with a given name", description = "list all leaseholder or optional only they with a given name")
    public List<LeaseholderDTO> listAllLeaseholder(@RequestParam Optional<String> name) {
        return service.listAllLeaseholder(name);
    }

    @PutMapping("/{id}")
    @Schema(name = "update leaseholder data", description = "update leaseholders data")
    public LeaseholderDTO updateLeaseholderById(@PathVariable("id") Long id, @Valid @RequestBody UpdateLeaseholderCommand command) {
        return service.updateLeaseholderById(id, command);
    }

    @DeleteMapping("/{id}")
    @Schema(name = "delete leaseholder by id", description = "delete leaseholder by id")
    public void deleteLeaseholderById(@PathVariable("id") Long id) {
        service.deleteLeaseholderById(id);
    }

    @DeleteMapping
    @Schema(name = "delete all leaseholder", description = "delete all leaseholder")
    public void deleteAllLeaseholder() {
        service.deleteAllLeaseholder();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Problem> handleNotFound(IllegalArgumentException iae) {
        Problem problem = Problem.builder()
                .withType(URI.create("grave_or_leaseholder/grave-or-leaseholder-not-found"))
                .withTitle("Grave/leaseholder not found")
                .withStatus(Status.NOT_FOUND)
                .withDetail(iae.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }
}
