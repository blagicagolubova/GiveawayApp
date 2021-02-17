package mk.ukim.finki.wp.proekt.repository;

import mk.ukim.finki.wp.proekt.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Integer> {
}
