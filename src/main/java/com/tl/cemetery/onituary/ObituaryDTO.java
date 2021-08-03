package com.tl.cemetery.onituary;

import com.tl.cemetery.grave.Grave;
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

    private Grave grave;
}
