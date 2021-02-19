package mk.ukim.finki.wp.proekt.sevice;

import mk.ukim.finki.wp.proekt.model.User;

public interface AuthService {

    User login(String username, String password);
}
