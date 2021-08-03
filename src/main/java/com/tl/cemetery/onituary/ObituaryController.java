package com.tl.cemetery.onituary;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/obituaries")
public class ObituaryController {

    private ObituaryService service;

    @PostMapping
    public ObituaryDTO createObituary(@RequestBody CreateObituaryCommand command) {
        return service.createObituary(command);
    }

}
