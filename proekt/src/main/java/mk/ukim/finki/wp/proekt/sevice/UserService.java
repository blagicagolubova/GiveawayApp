package mk.ukim.finki.wp.proekt.sevice;

import mk.ukim.finki.wp.proekt.model.User;

import java.util.List;

public interface UserService {

    User save(String username, String name, String surname, String address, String phone, String email, String password);

    User findByUsername(String username);

    List<User> findAll();
}
