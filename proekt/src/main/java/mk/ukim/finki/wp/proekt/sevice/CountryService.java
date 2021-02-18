package mk.ukim.finki.wp.proekt.sevice;

import jdk.dynalink.linker.LinkerServices;
import mk.ukim.finki.wp.proekt.model.Country;
import mk.ukim.finki.wp.proekt.model.enumerations.Continent;

import java.util.List;

public interface CountryService {

    Country save(String name, String code, Continent continent);

    Country findById(Integer id);

    List<Country> findAll();

    List<Country> findAllById(List<Integer> country_ids);
}
