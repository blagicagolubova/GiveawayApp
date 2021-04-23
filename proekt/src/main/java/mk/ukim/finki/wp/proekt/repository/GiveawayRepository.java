package mk.ukim.finki.wp.proekt.repository;

import mk.ukim.finki.wp.proekt.model.Giveaway;
import mk.ukim.finki.wp.proekt.model.enumerations.GiveawayStatus;
import mk.ukim.finki.wp.proekt.views.Top3;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiveawayRepository extends JpaRepository<Giveaway,Integer> {

    List<Giveaway> findAllByCategoryLike(String text);

    List<Giveaway> findAllByStatus(GiveawayStatus status);

//      @Query("SELECT count(g.participants) FROM Giveaway g where g.participants= :username")
//      List<Giveaway> fetchOne(@Param("username") String username);

    @Query(nativeQuery = true, value = "select * from most_participants")
    List<Top3> getTOP3();
}
