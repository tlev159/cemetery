package com.tl.cemetery.grave;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GraveDTO {

    private Long id;

    private String name;

    private int row;

    private int column;

}
