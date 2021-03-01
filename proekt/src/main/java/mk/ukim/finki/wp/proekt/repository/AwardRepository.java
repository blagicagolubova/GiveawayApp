package mk.ukim.finki.wp.proekt.repository;

import mk.ukim.finki.wp.proekt.model.Award;
import mk.ukim.finki.wp.proekt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AwardRepository extends JpaRepository<Award,Integer> {

    List<Award> findAllByCreator(User user);
}
