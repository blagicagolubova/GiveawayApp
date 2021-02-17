package mk.ukim.finki.wp.proekt.repository;

import mk.ukim.finki.wp.proekt.model.GiveawayRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiveawayRegionRepository extends JpaRepository<GiveawayRegion,Integer> {
}
