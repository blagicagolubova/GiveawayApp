package mk.ukim.finki.wp.proekt.sevice.impl;

import mk.ukim.finki.wp.proekt.model.Manufacturer;
import mk.ukim.finki.wp.proekt.model.exceptions.InvalidManufacturerIdException;
import mk.ukim.finki.wp.proekt.repository.ManufacturerRepository;
import mk.ukim.finki.wp.proekt.sevice.ManufacturerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public Manufacturer save(String name, String description) {
        Manufacturer manufacturer= new Manufacturer(name, description);
        return this.manufacturerRepository.save(manufacturer);
    }

    @Override
    public Manufacturer findById(Integer id) {
        if(this.manufacturerRepository.findById(id).isPresent()){
            return this.manufacturerRepository.findById(id).get();
        }
        else{
            throw new InvalidManufacturerIdException();
        }
    }

    @Override
    public List<Manufacturer> findAll() {
        return this.manufacturerRepository.findAll();
    }
}
