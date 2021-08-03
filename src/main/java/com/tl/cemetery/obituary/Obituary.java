package com.tl.cemetery.obituary;

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

    @Column(nullable = false)
    private String nameOfMother;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "date_of_rip", nullable = false)
    private LocalDate dateOfRIP;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "grave_id", nullable = false)
    private Grave grave;

    public Obituary(CreateObituaryCommand command) {
        this.name = command.getName();
        this.nameOfMother = command.getNameOfMother();
        this.dateOfBirth = command.getDateOfBirth();
        this.dateOfRIP = command.getDateOfRIP();
    }

}
