package mk.ukim.finki.wp.proekt.model;

import lombok.Data;
import mk.ukim.finki.wp.proekt.model.enumerations.GiveawayStatus;
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

    @Enumerated(EnumType.STRING)
    private GiveawayStatus status;

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

    public Giveaway(Integer id, String name, Date startDate, Date endDate, GiveawayStatus status, Category category, Award award, List<User> participants, User winner, UserType userType, Company company, User creator, GiveawayRegion giveawayRegion) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.category = category;
        this.award = award;
        this.participants = participants;
        this.winner = winner;
        this.userType = userType;
        this.company = company;
        this.creator = creator;
        this.giveawayRegion = giveawayRegion;
    }

    public Giveaway(String name, Date startDate, Date endDate, Category category, Award award, List<User> participants, User winner, UserType userType, Company company, User creator, GiveawayRegion giveawayRegion) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.award = award;
        this.participants = participants;
        this.winner = winner;
        this.userType = userType;
        this.company = company;
        this.creator = creator;
        this.giveawayRegion = giveawayRegion;
    }

    public Giveaway(String name, Date startDate, Date endDate, Category category, Award award, UserType userType, User creator, GiveawayRegion giveawayRegion, GiveawayStatus status) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.award = award;
        this.creator = creator;
        this.giveawayRegion = giveawayRegion;
        this.userType= userType;
        this.status = status;
    }

    public Giveaway(String name, Date startDate, Date endDate, Category category, Award award, UserType userType, User creator,Company company, GiveawayRegion giveawayRegion, GiveawayStatus status) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.award = award;
        this.creator = creator;
        this.giveawayRegion = giveawayRegion;
        this.userType=userType;
        this.company=company;
        this.status = status;
    }


}
