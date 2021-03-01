package mk.ukim.finki.wp.proekt.model;

import lombok.Data;
import mk.ukim.finki.wp.proekt.model.enumerations.Role;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Data
@Entity
@Table(name = "Users")
public class User implements UserDetails {

    @Id
    private String username;

    private String name;

    private String surname;

    private String address;

    private String phone;

    private String email;

    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;


    public User() {
    }

    private boolean isAccountNonExpired=true;

    private boolean isAccountNonLocked=true;

    private boolean isCredentialsNonExpired=true;

    private boolean isEnabled=true;

    public User(String username, String name, String surname, String address, String phone, String email, String password, Role role) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.role=role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(role);
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
