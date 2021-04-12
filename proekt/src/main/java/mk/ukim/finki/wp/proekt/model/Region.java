package mk.ukim.finki.wp.proekt.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany(fetch=FetchType.EAGER)
    private List<Country> countries;

    public Region() {
    }

    public Region(String name, List<Country> countries) {
        this.name = name;
        this.countries = countries;
    }
}
