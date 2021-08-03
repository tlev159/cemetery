package com.tl.cemetery.onituary;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/obituaries")
public class ObituaryController {

    private ObituaryService service;

    @PostMapping
    public ObituaryDTO createObituary(@RequestBody CreateObituaryCommand command) {
        return service.createObituary(command);
    }

    @GetMapping("/{id}")
    @Operation(summary = "List obituary by id", description = "List obituary by id")
    public ObituaryDTO findObituaryById(@PathVariable("id") Long id) {
        return service.findObituaryById(id);
    }


}
