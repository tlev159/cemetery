package com.tl.cemetery.grave;

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
@RequestMapping("/api/graves")
@Tag(name = "Operations on Graves")
public class GraveController {

    private GraveService service;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Tag(name = "create new grave")
    public GraveDTO createGrave(@Valid @RequestBody CreateGraveCommand command) {
        return service.createGrave(command);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @Tag(name = "list all graves")
    public List<GraveDTO> listAllGraves() {
        return service.listAllGraves();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @Tag(name = "find grave by id")
    public GraveDTO findGraveById(@PathVariable("id") Long id) {
        return service.findGraveById(id);
    }

    @GetMapping("/parcel")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @Tag(name = "list all graves in parcel - optional for the row")
    public List<GraveDTO> listAllGravesInAParcel(@RequestParam String name, Optional<Integer> row) {
        return service.listAllGravesInAParcel(name, row);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGraveById(@PathVariable("id") Long id) {
        service.deleteGraveById(id);
    }

    @DeleteMapping
    @Tag(name = "delete all records from the table")
    public void deleteAllFromGraves() {
        service.deleteAllFromGraves();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Problem> handleNotFound(IllegalArgumentException iae) {
        Problem problem = Problem.builder()
                .withType(URI.create("grave/grave-not-found"))
                .withTitle("Grave not found")
                .withStatus(Status.NOT_FOUND)
                .withDetail(iae.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }

}
