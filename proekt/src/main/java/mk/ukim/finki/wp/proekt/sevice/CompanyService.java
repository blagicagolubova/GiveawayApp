package mk.ukim.finki.wp.proekt.sevice;

import mk.ukim.finki.wp.proekt.model.Company;

import java.util.Optional;

public interface CompanyService {

    Company save(String name, String address, String description);

    Optional<Company> findBYName(String name);

    Company findById(Integer id);
}
