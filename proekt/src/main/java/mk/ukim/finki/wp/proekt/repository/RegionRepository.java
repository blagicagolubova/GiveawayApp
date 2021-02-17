package mk.ukim.finki.wp.proekt.repository;

import mk.ukim.finki.wp.proekt.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region,Integer> {
}
