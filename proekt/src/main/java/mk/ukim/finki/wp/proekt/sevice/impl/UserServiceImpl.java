package mk.ukim.finki.wp.proekt.sevice.impl;

import mk.ukim.finki.wp.proekt.model.User;
import mk.ukim.finki.wp.proekt.model.enumerations.Role;
import mk.ukim.finki.wp.proekt.model.exceptions.*;
import mk.ukim.finki.wp.proekt.repository.UserRepository;
import mk.ukim.finki.wp.proekt.sevice.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User save(String username, String password, String repeatedPassword, String name, String surname, String address, String phone, String email) {
        if( username==null || username.isEmpty() || password==null || password.isEmpty()){
            throw new InvalidArgumentException();
        }
        if(!password.equals(repeatedPassword)){
            throw new PasswordDoNotMatchException();
        }
        if(this.userRepository.findByUsername(username).isPresent()){
            throw new UserNameAlreadyExistsException(username);
        }
        User user=new User(username, name, surname, address, phone, email, passwordEncoder.encode(password), Role.ROLE_USER);
        userRepository.save(user);
        return user;
    }

    @Override
    public User update(String username, String name, String surname, String address, String phone, String email) {
        if( username==null || username.isEmpty()){
            throw new InvalidArgumentException();
        }
        User user=this.userRepository.findById(username).get();
        user.setName(name);
        user.setSurname(surname);
        user.setAddress(address);
        user.setPhone(phone);
        user.setEmail(email);
        return this.userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        if (username!=null && !username.isEmpty() && this.userRepository.findById(username).isPresent()){
            return this.userRepository.findById(username).get();
        }
        else {
            throw new InvalidUsernameException();
        }
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s).orElseThrow(()->new UsernameNotFoundException(s));
    }

}
