package com.tl.cemetery.grave;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GraveDTO {

    @NotEmpty
    @NotNull
    @Column(nullable = false, length = 3)
    private String name;

    @Column(nullable = false, length = 3)
    private int row;

    @Column(nullable = false, length = 3)
    private int column;

}
