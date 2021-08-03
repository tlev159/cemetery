package com.tl.cemetery;

import com.tl.cemetery.grave.CreateGraveCommand;
import com.tl.cemetery.grave.GraveDTO;
import com.tl.cemetery.grave.GraveType;
import com.tl.cemetery.leaseholder.CreateLeaseholderCommand;
import com.tl.cemetery.leaseholder.LeaseholderDTO;
import com.tl.cemetery.obituary.CreateObituaryCommand;
import com.tl.cemetery.obituary.ObituaryDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ObituaryRestTemplateIT {

    private final String URL_FOR_GRAVES = "/api/graves";
    private final String URL_FOR_OBITUARIES = "/api/obituaries";

    @Autowired
    TestRestTemplate template;

    @BeforeEach
    void init() {

        template.delete(URL_FOR_GRAVES);
        template.delete(URL_FOR_OBITUARIES);
    }

    @Test
    void createGraveObituarySaveThenFindById() {

        GraveDTO graveDTO =
                template.postForObject(URL_FOR_GRAVES, new CreateGraveCommand("B", 2, 4), GraveDTO.class);

        Long graveForId = graveDTO.getId();

        ObituaryDTO obituaryDTO =
                template.postForObject(URL_FOR_OBITUARIES,
                        new CreateObituaryCommand("Minta Aladár",
                                "Csendes Ilonka", LocalDate.of(1945, 3, 8),
                                LocalDate.of(2020, 3, 8), graveForId),
                        ObituaryDTO.class);

        Long id = obituaryDTO.getId();

        ObituaryDTO loadedObituary =
                template.getForObject(URL_FOR_OBITUARIES + "/" + id, ObituaryDTO.class);

        assertThat(loadedObituary)
                .extracting(ObituaryDTO::getName)
                .isEqualTo("Minta Aladár");

    }
}
