package com.tl.cemetery.leaseholder;

import com.tl.cemetery.grave.GraveType;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "create leaseholder", description = "create leaseholder")
public class CreateLeaseholderCommand {

    @NotBlank
    @Length(min = 5, max = 200)
    @Schema(description = "add leaseholders name", example = "John Sample")
    private String name;

    @NotBlank
    @Length(min = 5, max = 255)
    @Schema(description = "add address to leaseholder", example = "1212 OverTheRainbow, Backer street 13.")
    private String address;

    @NotBlank
    @Length(min = 5, max = 100)
    @Schema(description = "add phone number to leaseholder", example = "+36-1/123-4567")
    private String telephone;

    @NotNull
    @Schema(description = "add date for lease", example = "2020-08-20")
    private LocalDate leasedAt;

    @NotNull
    @Schema(description = "add type of the grave", example = "STONE")
    private GraveType type;

    @NotNull
    @Schema(description = "add id of grave", example = "3")
    private Long graveId;
}
