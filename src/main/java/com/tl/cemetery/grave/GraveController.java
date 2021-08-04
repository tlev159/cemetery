package com.tl.cemetery.grave;

import com.tl.cemetery.obituary.ObituaryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Create new grave", description = "Create new grave")
    public GraveDTO createGrave(@Valid @RequestBody CreateGraveCommand command) {
        return service.createGrave(command);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Update grave", description = "Update grave")
    public GraveDTO updateGrave(@PathVariable("id") Long id, @Valid @RequestBody UpdateGraveCommand command) {
        return service.updateGrave(id, command);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @Operation(summary = "List all graves", description = "List all graves")
    @ApiResponse(responseCode = "404", description = "No one graves found")
    public List<GraveDTO> listAllGraves() {

        return service.listAllGraves();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @Operation(summary = "Find grave by id", description = "Find grave by id")
    @ApiResponse(responseCode = "404", description = "Grave with id not found")
    public GraveDTO findGraveById(@PathVariable("id") Long id) {

        return service.findGraveById(id);
    }

    @GetMapping("/parcel")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @Operation(summary = "List all graves in parcel - optional for the row", description = "List all graves in parcel - optional for the row")
    @ApiResponse(responseCode = "404", description = "No one grave was found")
    public List<GraveDTO> listAllGravesInAParcel(@RequestParam String name, Optional<Integer> row) {
        return service.listAllGravesInAParcel(name, row);
    }

    @GetMapping("/obituaries")
    @Operation(summary = "List all obituaries in a grave by name, row, column", description = "List all obituaries in a grave by name, row, column")
    public List<ObituaryDTO> listAllObituariesInGrave(@RequestParam String name, @RequestParam int row, @RequestParam int column) {
        return service.listAllObituariesInGrave(name, row, column);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete grave by id", description = "Delete grave by id")
    public void deleteGraveById(@PathVariable("id") Long id) {

        service.deleteGraveById(id);
    }

    @DeleteMapping
    @Operation(summary = "Delete all records from the table", description = "Delete all records from the table")
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
