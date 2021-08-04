package com.tl.cemetery.obituary;

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
@Tag(name = "Update obituary", description = "Update obituary")
public class UpdateObituaryCommand {

    @Length(min = 5, max = 200)
    @Schema(name = "update obituaries name", example = "Minta Jenő")
    @NotNull
    private String name;

    @Length(min = 5, max = 200)
    @Schema(name = "update obituaries name of mother", example = "Minta Ilona")
    @NotNull
    private String nameOfMother;

    @NotNull
    @Schema(name = "update date of birth from obituary", example = "1940-07-03")
    private LocalDate dateOfBirth;

    @NotNull
    @Schema(name = "update date of funeral", example = "2020-05-03")
    private LocalDate dateOfRIP;

    @NotNull
    @NotEmpty
    @Schema(name = "add grave id", example = "3")
    private Long graveId;
}