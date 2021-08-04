package com.tl.cemetery;

import com.tl.cemetery.grave.CreateGraveCommand;
import com.tl.cemetery.grave.GraveDTO;
import com.tl.cemetery.grave.GraveType;
import com.tl.cemetery.leaseholder.CreateLeaseholderCommand;
import com.tl.cemetery.leaseholder.LeaseholderDTO;
import com.tl.cemetery.obituary.CreateObituaryCommand;
import com.tl.cemetery.obituary.ObituaryDTO;
import com.tl.cemetery.obituary.UpdateObituaryCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void createThenUpdateById() {

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

        template.put(URL_FOR_OBITUARIES + "/" + id,
                new UpdateObituaryCommand("Minta Dezső",
                        "Hangos Ilonka", LocalDate.of(1944, 3, 8),
                        LocalDate.of(2020, 4, 8), graveForId),
                ObituaryDTO.class);

        ObituaryDTO loadedObituary =
                template.getForObject(URL_FOR_OBITUARIES + "/" + id, ObituaryDTO.class);

        assertThat(loadedObituary)
                .extracting(ObituaryDTO::getName)
                .isEqualTo("Minta Dezső");

    }

    @Test
    void createMoreThenListAll() {

        GraveDTO graveDTO1 =
                template.postForObject(URL_FOR_GRAVES, new CreateGraveCommand("B", 1, 4), GraveDTO.class);
        GraveDTO graveDTO2 =
                template.postForObject(URL_FOR_GRAVES, new CreateGraveCommand("A", 1, 4), GraveDTO.class);

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
                template.exchange(URL_FOR_OBITUARIES,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<ObituaryDTO>>() {
                        }).getBody();

        assertThat(loadedObituaryDTOs)
                .hasSize(4)
                .extracting(ObituaryDTO::getName)
                .containsExactly("Minta Aladár","Minta Béla", "Minta Cecília", "Minta Dezső");

    }

    @Test
    void createTwoThenDeleteOneThenListAll() {

        GraveDTO graveDTO1 =
                template.postForObject(URL_FOR_GRAVES, new CreateGraveCommand("B", 1, 4), GraveDTO.class);
        GraveDTO graveDTO2 =
                template.postForObject(URL_FOR_GRAVES, new CreateGraveCommand("A", 1, 4), GraveDTO.class);

        Long id1 = graveDTO1.getId();
        Long id2 = graveDTO2.getId();

        template.postForObject(URL_FOR_OBITUARIES,
                new CreateObituaryCommand("Minta Aladár",
                        "Csendes Ilonka", LocalDate.of(1945, 3, 8),
                        LocalDate.of(2020, 3, 8), id1),
                ObituaryDTO.class);

        ObituaryDTO obituaryDTO =
        template.postForObject(URL_FOR_OBITUARIES,
                new CreateObituaryCommand("Minta Béla",
                        "Jane Doe", LocalDate.of(1945, 3, 8),
                        LocalDate.of(2020, 3, 8), id2),
                ObituaryDTO.class);

        Long obituaryId = obituaryDTO.getId();

        template.delete(URL_FOR_OBITUARIES + "/" + obituaryId);

        List<ObituaryDTO> loadedObituaryDTOs =
                template.exchange(URL_FOR_OBITUARIES,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<ObituaryDTO>>() {
                        }).getBody();

        assertThat(loadedObituaryDTOs)
                .hasSize(1)
                .extracting(ObituaryDTO::getName)
                .containsExactly("Minta Aladár");
    }

    @Test
    void createWithEmptyNameOfMother() {

        GraveDTO graveDTO =
                template.postForObject(URL_FOR_GRAVES, new CreateGraveCommand("B", 2, 4), GraveDTO.class);

        Long graveForId = graveDTO.getId();

        Problem result =
                template.postForObject(URL_FOR_OBITUARIES,
                        new CreateObituaryCommand("Minta Aladár",
                                "", LocalDate.of(1945, 3, 8),
                                LocalDate.of(2020, 3, 8), graveForId),
                        Problem.class);

        assertEquals(Status.BAD_REQUEST, result.getStatus());
    }


    @Test
    void createWithEmptyDateOfBirth() {

        GraveDTO graveDTO =
                template.postForObject(URL_FOR_GRAVES, new CreateGraveCommand("B", 2, 4), GraveDTO.class);

        Long graveForId = graveDTO.getId();

        Problem result =
                template.postForObject(URL_FOR_OBITUARIES,
                        new CreateObituaryCommand("Minta Aladár",
                                "Csendes Ilonka", null,
                                LocalDate.of(2020, 3, 8), graveForId),
                        Problem.class);

        assertEquals(Status.BAD_REQUEST, result.getStatus());

    }
}
