package mk.ukim.finki.wp.proekt.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Award {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    private Manufacturer manufacturer;

    public Award() {
    }

    public Award(String name, Manufacturer manufacturer) {
        this.name = name;
        this.manufacturer = manufacturer;
    }
}
