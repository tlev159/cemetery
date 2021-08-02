package com.tl.cemetery.grave;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateGraveCommand {

    @NotEmpty
    @NotNull
    @Schema(description = "update name to grave", example = "D")
    private String name;

    @NotNull
    @Schema(description = "update row number to grave", example = "3")
    private int row;

    @NotNull
    @Schema(description = "update column number to grave", example = "2")
    private int column;

}
