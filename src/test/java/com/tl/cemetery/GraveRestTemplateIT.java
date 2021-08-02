package com.tl.cemetery;

import com.tl.cemetery.grave.CreateGraveCommand;
import com.tl.cemetery.grave.GraveDTO;
import com.tl.cemetery.grave.UpdateGraveCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void createThenUpdateAndFindGraveById() {

        GraveDTO graveDTO =
                template.postForObject(URL_FOR_QUERY, new CreateGraveCommand("B", 2, 4), GraveDTO.class);

        Long id = graveDTO.getId();

        template.put(URL_FOR_QUERY + "/" + id, new UpdateGraveCommand("C", 3, 5));

        GraveDTO loadedGrave =
                template.getForObject(URL_FOR_QUERY + "/" + id, GraveDTO.class);

        assertThat(loadedGrave)
                .isEqualTo(new GraveDTO(id, "C", 3, 5));
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

    @Test
    void createMoreGraveAndListAllInAGivenParcel() {

        template.postForObject(URL_FOR_QUERY, new CreateGraveCommand("B", 13, 15), GraveDTO.class);
        template.postForObject(URL_FOR_QUERY, new CreateGraveCommand("A", 5, 4), GraveDTO.class);
        template.postForObject(URL_FOR_QUERY, new CreateGraveCommand("B", 7, 4), GraveDTO.class);
        template.postForObject(URL_FOR_QUERY, new CreateGraveCommand("C", 7, 4), GraveDTO.class);
        template.postForObject(URL_FOR_QUERY, new CreateGraveCommand("B", 1, 4), GraveDTO.class);
        template.postForObject(URL_FOR_QUERY, new CreateGraveCommand("D", 11, 4), GraveDTO.class);
        template.postForObject(URL_FOR_QUERY, new CreateGraveCommand("B", 13, 2), GraveDTO.class);

        List<GraveDTO> loadedGraveDTOs =
                template.exchange(URL_FOR_QUERY + "/parcel?name=B",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<GraveDTO>>() {
                        }).getBody();

        assertThat(loadedGraveDTOs)
                .hasSize(4)
                .extracting(GraveDTO::getRow)
                .containsExactly(1, 7, 13, 13);
    }

    @Test
    void createTwoGraveThenDeleteById() {

        GraveDTO graveDTO =
        template.postForObject(URL_FOR_QUERY, new CreateGraveCommand("B", 13, 15), GraveDTO.class);

        template.postForObject(URL_FOR_QUERY, new CreateGraveCommand("A", 5, 4), GraveDTO.class);

        Long id = graveDTO.getId();

        template.delete(URL_FOR_QUERY + "/" + id);

        List<GraveDTO> loadedGraveDTOs =
                template.exchange(URL_FOR_QUERY,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<GraveDTO>>() {
                        }).getBody();

        assertThat(loadedGraveDTOs)
                .hasSize(1)
                .extracting(GraveDTO::getName)
                .containsExactly("A");
    }

    @Test
    void createWithEmptyName() {
        Problem result = template.postForObject(URL_FOR_QUERY,
                new CreateGraveCommand("", 2, 4), Problem.class);

        assertEquals(Status.BAD_REQUEST, result.getStatus());
    }

    @Test
    void createWithMoreAsThreeDigits() {
        Problem result = template.postForObject(URL_FOR_QUERY,
                new CreateGraveCommand("F", 2321, 4), Problem.class);

        assertEquals(Status.BAD_REQUEST, result.getStatus());
    }

    @Test
    void NotFoundGraveException4xx() {

        Problem result = template.getForObject(URL_FOR_QUERY +"/999", Problem.class);

        assertEquals(Status.NOT_FOUND, result.getStatus());
        assertEquals(URI.create("grave/grave-not-found"), result.getType());

    }
}
