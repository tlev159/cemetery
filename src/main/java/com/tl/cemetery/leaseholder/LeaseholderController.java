package com.tl.cemetery.leaseholder;

import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/leaseholders")
@Tag(name = "Operations with leaseholders")
public class LeaseholderController {

    private LeaseholderService service;

    @PostMapping
    @Operation(summary = "Create new leaseholder", description = "Create new leaseholder")
    public LeaseholderDTO createLeaseholder(@Valid @RequestBody CreateLeaseholderCommand command) {
        return service.createLeaseholder(command);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get leaseholder by id", description = "Get leaseholder by id")
    public LeaseholderDTO findLeaseholderById(@PathVariable("id") Long id) {
        return service.findLeaseholderById(id);
    }

    @GetMapping
    @Operation(summary = "List all leaseholder or optional only they with a given name")
    public List<LeaseholderDTO> listAllLeaseholder(@RequestParam Optional<String> name) {
        return service.listAllLeaseholder(name);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update leaseholder data")
    public LeaseholderDTO updateLeaseholderById(@PathVariable("id") Long id, @Valid @RequestBody UpdateLeaseholderCommand command) {
        return service.updateLeaseholderById(id, command);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete leaseholder by id")
    public void deleteLeaseholderById(@PathVariable("id") Long id) {
        service.deleteLeaseholderById(id);
    }

    @DeleteMapping
    @Operation(summary= "Delete all leaseholder")
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
