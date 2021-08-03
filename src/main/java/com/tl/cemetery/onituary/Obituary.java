package com.tl.cemetery.onituary;

import com.tl.cemetery.grave.Grave;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "obituaries")
public class Obituary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String nameOfMother;

    private LocalDate dateOfBirth;

    private LocalDate dateOfRIP;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "grave_id", nullable = false)
    private Grave grave;

    public Obituary(String name, String nameOfMother, LocalDate dateOfBirth, LocalDate dateOfRIP) {
        this.name = name;
        this.nameOfMother = nameOfMother;
        this.dateOfBirth = dateOfBirth;
        this.dateOfRIP = dateOfRIP;
    }

}
