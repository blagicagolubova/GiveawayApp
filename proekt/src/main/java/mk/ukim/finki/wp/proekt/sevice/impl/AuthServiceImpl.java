package mk.ukim.finki.wp.proekt.sevice.impl;

import mk.ukim.finki.wp.proekt.model.User;
import mk.ukim.finki.wp.proekt.model.exceptions.InvalidArgumentException;
import mk.ukim.finki.wp.proekt.model.exceptions.InvalidUserCredentialsException;
import mk.ukim.finki.wp.proekt.repository.UserRepository;
import mk.ukim.finki.wp.proekt.sevice.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User login(String username, String password) {
        if( username==null || username.isEmpty() || password==null || password.isEmpty()){
            throw new InvalidArgumentException();
        }
        return userRepository.findByUsernameAndPassword(username, password).orElseThrow(()->new InvalidUserCredentialsException());
    }
}
