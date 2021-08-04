package com.tl.cemetery.obituary;

import com.tl.cemetery.grave.GraveDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObituaryDTO {

    private Long id;

    private String name;

    private String nameOfMother;

    private LocalDate dateOfBirth;

    private LocalDate dateOfRIP;

    private GraveDTO grave;
}
