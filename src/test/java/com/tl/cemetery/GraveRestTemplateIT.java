package com.tl.cemetery;

import com.tl.cemetery.grave.CreateGraveCommand;
import com.tl.cemetery.grave.GraveDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
                template.postForObject(URL_FOR_QUERY, new CreateGraveCommand("B", 2, 4), GraveDTO.class);

        Long id = graveDTO.getId();

        GraveDTO loadedGrave =
                template.getForObject(URL_FOR_QUERY + "/" + id, GraveDTO.class);

        assertThat(loadedGrave)
                .extracting(GraveDTO::getName)
                .isEqualTo("B");
    }

    @Test
    void createTwiceTheSameGraveAndThenThatOnlyOnceInTheDatabase() {

        template.postForObject(URL_FOR_QUERY, new CreateGraveCommand("B", 2, 4), GraveDTO.class);
        template.postForObject(URL_FOR_QUERY, new CreateGraveCommand("B", 2, 4), GraveDTO.class);

        List<GraveDTO> loadedGraveDTOs =
                template.exchange(URL_FOR_QUERY,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<GraveDTO>>() {
                        }).getBody();

        assertThat(loadedGraveDTOs)
                .hasSize(1)
                .extracting(GraveDTO::getName)
                .containsExactly("B");
    }

}
