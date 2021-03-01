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

    private Float weight;

    private String url;

    @ManyToOne
    private Manufacturer manufacturer;

    @ManyToOne
    private User creator;

    public Award() {
    }

    public Award(String name, Manufacturer manufacturer, User user) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.creator=user;
    }

    public Award(String name, Float weight, String url, Manufacturer manufacturer, User creator) {
        this.name = name;
        this.weight = weight;
        this.url = url;
        this.manufacturer = manufacturer;
        this.creator = creator;
    }
}
