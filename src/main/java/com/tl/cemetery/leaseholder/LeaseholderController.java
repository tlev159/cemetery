package com.tl.cemetery.leaseholder;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/leaseholder")
public class LeaseholderController {

    private LeaserholderService service;


}
