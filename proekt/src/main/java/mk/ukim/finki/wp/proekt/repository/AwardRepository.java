package mk.ukim.finki.wp.proekt.repository;

import mk.ukim.finki.wp.proekt.model.Award;
import mk.ukim.finki.wp.proekt.model.User;
import mk.ukim.finki.wp.proekt.model.enumerations.AwardStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AwardRepository extends JpaRepository<Award,Integer> {

    List<Award> findAllByCreator(User user);

    List<Award> findAllByStatusAndCreator(AwardStatus awardStatus, User creator);
}
