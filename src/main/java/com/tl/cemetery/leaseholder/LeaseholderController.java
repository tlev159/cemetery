package com.tl.cemetery.leaseholder;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/leaseholder")
@Tag(name = "Operations with leaseholders", description = "Operations with leaseholders")
public class LeaseholderController {

    private LeaseholderService service;



}
