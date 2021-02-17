package mk.ukim.finki.wp.proekt.repository;

import mk.ukim.finki.wp.proekt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
}
