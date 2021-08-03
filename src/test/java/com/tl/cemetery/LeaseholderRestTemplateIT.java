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

    @Test
    void createThenUpdate() {

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

                template.put(URL_FOR_LEASEHOLDERS + "/" + id,
                        new CreateLeaseholderCommand("Minta2 Elemér",
                                "345 Pusztakalász, Sutha u. 4.", "+36-2/234-5678",
                                LocalDate.of(2020, 3, 9),
                                GraveType.STONE, graveForId), LeaseholderDTO.class);


        LeaseholderDTO loadedLeaseholder =
                template.getForObject(URL_FOR_LEASEHOLDERS + "/" + id, LeaseholderDTO.class);

        assertThat(loadedLeaseholder)
                .extracting(LeaseholderDTO::getName)
                .isEqualTo("Minta2 Elemér");
    }

    @Test
    void createMoreLeaseholderAndListAll() {

        GraveDTO graveDTO1 =
                template.postForObject(URL_FOR_GRAVES, new CreateGraveCommand("B", 1, 4), GraveDTO.class);
        GraveDTO graveDTO2 =
                template.postForObject(URL_FOR_GRAVES, new CreateGraveCommand("A", 1, 4), GraveDTO.class);
        GraveDTO graveDTO3 =
                template.postForObject(URL_FOR_GRAVES, new CreateGraveCommand("A", 3, 4), GraveDTO.class);
        GraveDTO graveDTO4 =
                template.postForObject(URL_FOR_GRAVES, new CreateGraveCommand("B", 2, 4), GraveDTO.class);

        Long id1 = graveDTO1.getId();
        Long id2 = graveDTO2.getId();
        Long id3 = graveDTO3.getId();
        Long id4 = graveDTO4.getId();

        template.postForObject(URL_FOR_LEASEHOLDERS,
                new CreateLeaseholderCommand("Minta Aladár",
                        "6543 Kalászpuszta, Kossuth u. 4.", "+36-1/234-5678",
                        LocalDate.of(2020, 3, 8),
                        GraveType.STONE, id1), LeaseholderDTO.class);
        template.postForObject(URL_FOR_LEASEHOLDERS,
                new CreateLeaseholderCommand("Minta Balázs",
                        "1357 Pusztaháza, Petőfi u. 4.", "+36-1/111-5678",
                        LocalDate.of(2020, 3, 8),
                        GraveType.STONE, id2), LeaseholderDTO.class);
        template.postForObject(URL_FOR_LEASEHOLDERS,
                new CreateLeaseholderCommand("Minta Cecília",
                        "2345 Bélaháza, Molnár u. 34.", "+36-1/222-5678",
                        LocalDate.of(2020, 3, 8),
                        GraveType.STONE, id3), LeaseholderDTO.class);
        template.postForObject(URL_FOR_LEASEHOLDERS,
                new CreateLeaseholderCommand("Minta Dezső",
                        "6727 Szeged, Tisza körút 4.", "+36-1/333-5678",
                        LocalDate.of(2020, 3, 8),
                        GraveType.STONE, id4), LeaseholderDTO.class);

        List<LeaseholderDTO> loadedGraveDTOs =
                template.exchange(URL_FOR_LEASEHOLDERS,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<LeaseholderDTO>>() {
                        }).getBody();

        assertThat(loadedGraveDTOs)
                .hasSize(4)
                .extracting(LeaseholderDTO::getName)
                .containsExactly("Minta Aladár", "Minta Balázs", "Minta Cecília", "Minta Dezső");
    }

    @Test
    void createTwoDeleteOneAndListAll() {

        GraveDTO graveDTO1 =
                template.postForObject(URL_FOR_GRAVES, new CreateGraveCommand("B", 1, 4), GraveDTO.class);
        GraveDTO graveDTO2 =
                template.postForObject(URL_FOR_GRAVES, new CreateGraveCommand("A", 1, 4), GraveDTO.class);

        Long id1 = graveDTO1.getId();
        Long id2 = graveDTO2.getId();

        template.postForObject(URL_FOR_LEASEHOLDERS,
                new CreateLeaseholderCommand("Minta Aladár",
                        "6543 Kalászpuszta, Kossuth u. 4.", "+36-1/234-5678",
                        LocalDate.of(2020, 3, 8),
                        GraveType.STONE, id1), LeaseholderDTO.class);

        LeaseholderDTO loadedLeaseholderDTO =
                template.postForObject(URL_FOR_LEASEHOLDERS,
                        new CreateLeaseholderCommand("Minta Balázs",
                                "1357 Pusztaháza, Petőfi u. 4.", "+36-1/111-5678",
                                LocalDate.of(2020, 3, 8),
                                GraveType.STONE, id2), LeaseholderDTO.class);

        Long id = loadedLeaseholderDTO.getId();

        template.delete(URL_FOR_LEASEHOLDERS + "/" + id);

        List<LeaseholderDTO> loadedGraveDTOs =
                template.exchange(URL_FOR_LEASEHOLDERS,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<LeaseholderDTO>>() {
                        }).getBody();

        assertThat(loadedGraveDTOs)
                .hasSize(1)
                .extracting(LeaseholderDTO::getName)
                .containsExactly("Minta Aladár");
    }

    @Test
    void createWithEmptyName() {

        GraveDTO graveDTO1 =
                template.postForObject(URL_FOR_GRAVES, new CreateGraveCommand("B", 1, 4), GraveDTO.class);

        Long id1 = graveDTO1.getId();

        Problem result = template.postForObject(URL_FOR_LEASEHOLDERS,
                new CreateLeaseholderCommand("",
                        "6543 Kalászpuszta, Kossuth u. 4.", "+36-1/234-5678",
                        LocalDate.of(2020, 3, 8),
                        GraveType.STONE, id1), Problem.class);

        assertEquals(Status.BAD_REQUEST, result.getStatus());
    }

}
