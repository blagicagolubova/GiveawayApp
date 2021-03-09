package mk.ukim.finki.wp.proekt.model;

import lombok.Data;
import mk.ukim.finki.wp.proekt.model.enumerations.AwardStatus;

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

    @Enumerated(EnumType.STRING)
    private AwardStatus status;

    @ManyToOne
    private Manufacturer manufacturer;

    @ManyToOne
    private User creator;

    public Award() {
    }


    public Award(String name, Float weight, String url, Manufacturer manufacturer, User creator, AwardStatus status) {
        this.name = name;
        this.weight = weight;
        this.url = url;
        this.manufacturer = manufacturer;
        this.creator = creator;
        this.status=status;
    }
}
