package mk.ukim.finki.wp.proekt.sevice.impl;

import mk.ukim.finki.wp.proekt.model.Country;
import mk.ukim.finki.wp.proekt.model.enumerations.Continent;
import mk.ukim.finki.wp.proekt.model.exceptions.CountryNameAndCodeCanNotBeEmptyException;
import mk.ukim.finki.wp.proekt.model.exceptions.InvalidCountryIdException;
import mk.ukim.finki.wp.proekt.repository.CountryRepository;
import mk.ukim.finki.wp.proekt.sevice.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public Country save(String name, String code, Continent continent) {
        if (!name.isEmpty() && !code.isEmpty()){
            Country country=new Country(name, code, continent);
            return this.countryRepository.save(country);
        }
        else{
            throw new CountryNameAndCodeCanNotBeEmptyException();
        }
    }

    @Override
    public Country findById(Integer id) {
        if(this.countryRepository.findById(id).isPresent()){
            return this.countryRepository.findById(id).get();
        }
        else{
            throw new InvalidCountryIdException();
        }
    }

    @Override
    public List<Country> findAll() {
        return  this.countryRepository.findAll();
    }

    @Override
    public List<Country> findAllById(List<Integer> country_ids) {
        return this.countryRepository.findAllById(country_ids);
    }



}
