package com.tl.cemetery.leaseholder;

import com.tl.cemetery.grave.Grave;
import com.tl.cemetery.grave.GraveType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "leaseholders")
public class Leaseholder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "leaseholder_name", nullable = false, length = 200)
    @OrderBy
    private String name;

    @Column(nullable = true)
    private String address;

    @Column(name = "phone", nullable = false, length = 100)
    private String telephone;

    @Column(name = "leased_at", nullable = false)
    private LocalDate leasedAt;

    @Column(name = "type_of_grave", nullable = false)
    @Enumerated(EnumType.STRING)
    private GraveType type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "grave_id", referencedColumnName = "id")
    private Grave grave;

    public Leaseholder(String name, String address, String telephone, LocalDate leasedAt, GraveType type) {
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.leasedAt = leasedAt;
        this.type = type;
    }

    public Leaseholder(CreateLeaseholderCommand command) {
        this.name = command.getName();
        this.address = command.getAddress();
        this.telephone = command.getTelephone();
        this.leasedAt = command.getLeasedAt();
        this.type = command.getType();
    }

    public void addGrave(Grave graveTemplate) {
        if (grave == null) {
            grave = graveTemplate;
        }
        grave.setLeaseholder(this);
    }
}
