package mk.ukim.finki.wp.proekt.repository;

import mk.ukim.finki.wp.proekt.model.Giveaway;
import mk.ukim.finki.wp.proekt.model.enumerations.GiveawayStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiveawayRepository extends JpaRepository<Giveaway,Integer> {

    List<Giveaway> findAllByCategoryLike(String text);

    List<Giveaway> findAllByStatus(GiveawayStatus status);
}
