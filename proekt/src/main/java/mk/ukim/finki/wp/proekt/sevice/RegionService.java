package mk.ukim.finki.wp.proekt.sevice;

import mk.ukim.finki.wp.proekt.model.Country;
import mk.ukim.finki.wp.proekt.model.Region;

import java.util.List;

public interface RegionService {

    Region save(String name, List<Integer> country_ids);

    Region findByName(String name);

    Region findById(Integer id);

    List<Region> findAll();
}
