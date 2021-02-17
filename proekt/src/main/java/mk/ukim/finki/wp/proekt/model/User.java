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
}
