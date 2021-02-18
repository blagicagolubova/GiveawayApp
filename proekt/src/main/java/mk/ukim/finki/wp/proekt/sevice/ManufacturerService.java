package mk.ukim.finki.wp.proekt.sevice;

import mk.ukim.finki.wp.proekt.model.Manufacturer;

import java.util.Optional;

public interface ManufacturerService {

    Manufacturer save(String name, String description);

    Manufacturer findById(Integer id);

}
