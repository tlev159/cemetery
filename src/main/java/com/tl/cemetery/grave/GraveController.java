package com.tl.cemetery.grave;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "Operations on Graves")
public class GraveController {

    private GraveService service;

    @PostMapping("/graves")
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
}
