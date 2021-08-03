package com.tl.cemetery.obituary;

import com.tl.cemetery.grave.Grave;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "Create obituary", description = "Create obituary")
public class CreateObituaryCommand {

    @Length(min = 5, max = 200)
    @Schema(name = "add obituaries name", example = "Minta Jenő")
    private String name;

    @Length(min = 5, max = 200)
    @Schema(name = "add obituaries name of mother", example = "Minta Ilona")
    private String nameOfMother;

    @NotNull
    @Schema(name = "add date of birth from obituary", example = "1940-07-03")
    private LocalDate dateOfBirth;

    @NotNull
    @Schema(name = "add date of funeral", example = "2020-05-03")
    private LocalDate dateOfRIP;

    @NotNull
    @NotEmpty
    @Schema(name = "add grave id", example = "3")
    private Long graveId;
}
