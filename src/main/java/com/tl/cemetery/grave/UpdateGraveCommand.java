package com.tl.cemetery.grave;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateGraveCommand {

    @NotEmpty
    @NotNull
    @Column(length = 3)
    @Schema(description = "update name to grave", example = "D")
    private String name;

    @NotNull
    @Min(1)
    @Max(999)
    @Schema(description = "update row number to grave", example = "3")
    private int row;

    @NotNull
    @Min(1)
    @Max(999)
    @Schema(description = "update column number to grave", example = "2")
    private int column;

}
