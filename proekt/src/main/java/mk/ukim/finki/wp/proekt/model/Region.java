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

    @OneToMany(fetch = FetchType.EAGER)
    private List<Country> countries;
}
