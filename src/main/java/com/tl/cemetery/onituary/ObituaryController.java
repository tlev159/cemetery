package com.tl.cemetery.onituary;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/obituaries")
public class ObituaryController {

    private ObituaryService service;


}
