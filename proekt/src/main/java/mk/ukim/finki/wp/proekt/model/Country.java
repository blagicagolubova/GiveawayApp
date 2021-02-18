package mk.ukim.finki.wp.proekt.model;

import lombok.Data;
import mk.ukim.finki.wp.proekt.model.enumerations.Continent;

import javax.persistence.*;

@Data
@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String code;

    @Enumerated(EnumType.STRING)
    private Continent continent;

    public Country() {
    }

    public Country(String name, String code, Continent continent) {
        this.name = name;
        this.code = code;
        this.continent = continent;
    }
}
