package com.tl.cemetery.grave;

import com.tl.cemetery.leaseholder.Leaseholder;
import com.tl.cemetery.onituary.Obituary;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "graves")
public class Grave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 3, nullable = false)
    @OrderBy
    private String name;

    @Column(name = "grave_row", length = 3)
    @Min(1)
    @Max(999)
    private int row;

    @Column(name = "grave_column", length = 3)
    @Min(1)
    @Max(999)
    private int column;

    @OneToOne(mappedBy = "grave")
    private Leaseholder leaseholder;

    @OneToMany(mappedBy = "grave")
    private List<Obituary> obituaries;

    public Grave(String name, int row, int column) {
        this.name = name;
        this.row = row;
        this.column = column;
    }

    public void addLeaseholder(Leaseholder lh) {
        if (leaseholder == null) {
            leaseholder = lh;
        }
        leaseholder.setGrave(this);
    }

    public void addObituaries(Obituary obituary) {
        if (obituaries == null) {
            obituaries = new ArrayList<>();
        }
        obituaries.add(obituary);
    }
}

