package mk.ukim.finki.wp.proekt.sevice;

import mk.ukim.finki.wp.proekt.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService  extends UserDetailsService {

    User save(String username, String password, String repeatedPassword, String name, String surname, String address, String phone, String email);

    User update(String username,String name, String surname, String address, String phone, String email);

    User findByUsername(String username);

    List<User> findAll();

    UserDetails loadUserByUsername(String s);
}
