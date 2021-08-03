package com.tl.cemetery.leaseholder;

import com.tl.cemetery.grave.GraveType;
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
public class CreateLeaseholderCommand {

    @NotBlank
    @Length(min = 5, max = 200)
    private String name;

    @NotBlank
    @Length(min = 5, max = 255)
    private String address;

    @NotBlank
    @Length(min = 5, max = 100)
    private String telephone;

    @NotNull
    private LocalDate leasedAt;

    @NotNull
    private GraveType type;

    @NotNull
    private Long graveId;
}
