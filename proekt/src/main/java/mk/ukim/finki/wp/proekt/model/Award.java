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
}
