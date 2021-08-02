package com.tl.cemetery;

import com.tl.cemetery.grave.CreateGraveCommand;
import com.tl.cemetery.grave.GraveDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Sql
public class GraveRestTemplateIT {

    private final String URL_FOR_QUERY = "/api/graves";

    @Autowired
    TestRestTemplate template;

    @BeforeEach
    void init() {
        template.delete(URL_FOR_QUERY);
    }

    @Test
    void createGraveThenFindById() {

        GraveDTO graveDTO =
                template.postForObject(URL_FOR_QUERY, new CreateGraveCommand("B", 2,4), GraveDTO.class);

        Long id = graveDTO.getId();

        GraveDTO loadedGrave =
                template.getForObject(URL_FOR_QUERY  + "/" + id, GraveDTO.class);

        assertThat(loadedGrave)
                .extracting(GraveDTO::getName)
                .isEqualTo("B");
    }

}
