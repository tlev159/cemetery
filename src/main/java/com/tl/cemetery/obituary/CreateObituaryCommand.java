package com.tl.cemetery.obituary;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "Create obituary", description = "Create obituary")
public class CreateObituaryCommand {

    @NotBlank
    @NotEmpty
    @Length(min = 5, max = 200)
    @Schema(name = "add obituaries name", example = "Minta Jenő")
    private String name;

    @NotBlank
    @NotEmpty
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
    @Schema(name = "add grave id", example = "3")
    private Long graveId;
}
