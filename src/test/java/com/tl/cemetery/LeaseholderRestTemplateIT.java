package com.tl.cemetery;

import com.tl.cemetery.grave.CreateGraveCommand;
import com.tl.cemetery.grave.GraveDTO;
import com.tl.cemetery.grave.GraveType;
import com.tl.cemetery.leaseholder.CreateLeaseholderCommand;
import com.tl.cemetery.leaseholder.LeaseholderDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LeaseholderRestTemplateIT {

    private final String URL_FOR_GRAVES = "/api/graves";
    private final String URL_FOR_LEASEHOLDERS = "/api/leaseholders";

    @Autowired
    TestRestTemplate template;

    @BeforeEach
    void init() {

        template.delete(URL_FOR_GRAVES);
        template.delete(URL_FOR_LEASEHOLDERS);
    }

    @Test
    void createLeaseholderThenFindById() {

        GraveDTO graveDTO =
                template.postForObject(URL_FOR_GRAVES, new CreateGraveCommand("B", 2, 4), GraveDTO.class);

        Long graveForId = graveDTO.getId();

        LeaseholderDTO leaseholderDTO =
                template.postForObject(URL_FOR_LEASEHOLDERS,
                        new CreateLeaseholderCommand("Minta Aladár",
                                "6543 Kalászpuszta, Kossuth u. 4.", "+36-1/234-5678",
                                LocalDate.of(2020, 3, 8),
                                GraveType.STONE, graveForId), LeaseholderDTO.class);

        Long id = leaseholderDTO.getId();

        LeaseholderDTO loadedLeaseholder =
                template.getForObject(URL_FOR_LEASEHOLDERS + "/" + id, LeaseholderDTO.class);

        assertThat(loadedLeaseholder)
                .extracting(LeaseholderDTO::getName)
                .isEqualTo("Minta Aladár");
    }


}
