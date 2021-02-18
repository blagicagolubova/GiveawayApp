package mk.ukim.finki.wp.proekt.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String address;

    private String description;

    public Company() {
    }

    public Company(String name, String address, String description) {
        this.name = name;
        this.address = address;
        this.description = description;
    }

}
