package mk.ukim.finki.wp.proekt.model;

import lombok.Data;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "Users")
public class User {

    @Id
    private String username;

    private String name;

    private String surname;

    private String address;

    private String phone;

    private String email;

    private String password;

    public User() {
    }

    public User(String username, String name, String surname, String address, String phone, String email, String password) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }
}
