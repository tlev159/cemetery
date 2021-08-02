package com.tl.cemetery.grave;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "grave")
@Table(name = "graves")
public class Grave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "grave_row", length = 3)
    private int row;

    @Column(name = "grave_column", length = 3)
    private int column;

    public Grave(String name, int row, int column) {
        this.name = name;
        this.row = row;
        this.column = column;
    }
}

