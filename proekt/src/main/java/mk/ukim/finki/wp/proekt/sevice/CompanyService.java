package mk.ukim.finki.wp.proekt.sevice;

import mk.ukim.finki.wp.proekt.model.Company;

public interface CompanyService {

    Company save(String name, String address, String description);

    Company findById(Integer id);
}
