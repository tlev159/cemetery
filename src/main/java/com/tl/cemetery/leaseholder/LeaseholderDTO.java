package com.tl.cemetery.leaseholder;

import com.tl.cemetery.grave.GraveType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaseholderDTO {

    private Long id;

    private String name;

    private String address;

    private String telephone;

    private LocalDate leasedAt;

    private GraveType type;

    private Long graveId;
}
