package mk.ukim.finki.wp.proekt.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class GiveawayRegion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(fetch=FetchType.EAGER)
    List<Country> countries;
}
