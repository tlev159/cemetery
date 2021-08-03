package com.tl.cemetery.leaseholder;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

}
