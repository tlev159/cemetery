package com.tl.cemetery.grave;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "Find obituaries in a grave with given parameters")
public class FindObituariesInGraveCommand {

    @NotEmpty
    @NotNull
    @Schema(description = "add name to grave", example = "D")
    @Column(length = 3)
    private String name;

    @NotNull
    @Schema(description = "add row number to grave", example = "3")
    @Min(1)
    @Max(999)
    private int row;

    @NotNull
    @Schema(description = "add column number to grave", example = "2")
    @Min(1)
    @Max(999)
    private int column;

}
