package com.tl.cemetery.leaseholder;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/leaseholder")
@Tag(name = "Operations with leaseholders", description = "Operations with leaseholders")
public class LeaseholderController {

    private LeaseholderService service;

    @PostMapping
    private LeaseholderDTO createLeaseholder(@Valid @RequestBody CreateLeaseholderCommand command) {
        return service.createLeaseholder(command);
    }

    @GetMapping
    public List<LeaseholderDTO> listAllLeaseholder(@RequestParam Optional<String> name) {
        return service.listAllLeaseholder(name);
    }

}
