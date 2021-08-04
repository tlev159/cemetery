package com.tl.cemetery;

import com.tl.cemetery.grave.CreateGraveCommand;
import com.tl.cemetery.grave.GraveDTO;
import com.tl.cemetery.grave.UpdateGraveCommand;
import com.tl.cemetery.obituary.CreateObituaryCommand;
import com.tl.cemetery.obituary.ObituaryDTO;
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
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GraveRestTemplateIT {

    private final String URL_FOR_QUERY = "/api/graves";
    private final String URL_FOR_OBITUARIES = "/api/obituaries";

    @Autowired
    TestRestTemplate template;

    @BeforeEach
    void init() {

        template.delete(URL_FOR_OBITUARIES);
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
    void createGraveAndMoreObituariesThenListByGrave() {

        GraveDTO graveDTO1 =
        template.postForObject(URL_FOR_QUERY, new CreateGraveCommand("B", 2, 4), GraveDTO.class);

        GraveDTO graveDTO2 =
                template.postForObject(URL_FOR_QUERY, new CreateGraveCommand("A", 3, 3), GraveDTO.class);

        Long id1 = graveDTO1.getId();
        Long id2 = graveDTO2.getId();

        template.postForObject(URL_FOR_OBITUARIES,
                new CreateObituaryCommand("Minta Aladár",
                        "Csendes Ilonka", LocalDate.of(1945, 3, 8),
                        LocalDate.of(2020, 3, 8), id1),
                ObituaryDTO.class);
        template.postForObject(URL_FOR_OBITUARIES,
                new CreateObituaryCommand("Minta Béla",
                        "Jane Doe", LocalDate.of(1945, 3, 8),
                        LocalDate.of(2020, 3, 8), id2),
                ObituaryDTO.class);
        template.postForObject(URL_FOR_OBITUARIES,
                new CreateObituaryCommand("Minta Cecília",
                        "Hangos Ilonka", LocalDate.of(1945, 3, 8),
                        LocalDate.of(2020, 3, 8), id1),
                ObituaryDTO.class);
        template.postForObject(URL_FOR_OBITUARIES,
                new CreateObituaryCommand("Minta Dezső",
                        "Rendes Ilonka", LocalDate.of(1945, 3, 8),
                        LocalDate.of(2020, 3, 8), id2),
                ObituaryDTO.class);

        List<ObituaryDTO> loadedObituaryDTOs =
                template.exchange(URL_FOR_QUERY + "/obituaries?name=A&row=3&column=3",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<ObituaryDTO>>() {
                        }).getBody();

        assertThat(loadedObituaryDTOs)
                .hasSize(2)
                .extracting(ObituaryDTO::getName)
                .containsExactly("Minta Béla", "Minta Dezső");
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

        Problem result = template.getForObject(URL_FOR_QUERY + "/999", Problem.class);

        assertEquals(Status.NOT_FOUND, result.getStatus());
        assertEquals(URI.create("grave/grave-not-found"), result.getType());

    }
}
