package mk.ukim.finki.wp.proekt.repository;

import mk.ukim.finki.wp.proekt.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country,Integer> {
}
