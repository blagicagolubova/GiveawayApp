package mk.ukim.finki.wp.proekt.sevice.impl;

import mk.ukim.finki.wp.proekt.model.User;
import mk.ukim.finki.wp.proekt.repository.UserRepository;
import mk.ukim.finki.wp.proekt.sevice.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(String username, String name, String surname, String address, String phone, String email, String password) {
        if(!username.isEmpty() && !email.isEmpty() && !name.isEmpty() && !surname.isEmpty() && !password.isEmpty()){
            User user=new User(username, name, surname, address, phone, email, password);
            return this.userRepository.save(user);
        }
        else {
            //TODO: exception
            return null;
        }
    }

    @Override
    public User findByUsername(String username) {
        if (username!=null && !username.isEmpty() && this.userRepository.findById(username).isPresent()){
            return this.userRepository.findById(username).get();
        }
        else {
            //TODO: exception
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }
}
