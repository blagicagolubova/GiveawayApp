package mk.ukim.finki.wp.proekt.model;

import lombok.Data;
import mk.ukim.finki.wp.proekt.model.enumerations.UserType;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Giveaway {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Date startDate;

    private Date endDate;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Award award;

    @ManyToMany
    private List<User> participants;

    @ManyToOne
    private User winner;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @ManyToOne
    private Company company;

    @ManyToOne
    private User creator;

    @OneToOne(fetch = FetchType.EAGER)
    private GiveawayRegion giveawayRegion;

    public Giveaway() {
    }


}
