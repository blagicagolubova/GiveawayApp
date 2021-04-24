package mk.ukim.finki.wp.proekt.sevice.impl;

import mk.ukim.finki.wp.proekt.model.Country;
import mk.ukim.finki.wp.proekt.model.Region;
import mk.ukim.finki.wp.proekt.model.exceptions.EmptyRegionNameException;
import mk.ukim.finki.wp.proekt.model.exceptions.InvalidRegionArgumentsException;
import mk.ukim.finki.wp.proekt.model.exceptions.InvalidRegionIdException;
import mk.ukim.finki.wp.proekt.repository.RegionRepository;
import mk.ukim.finki.wp.proekt.sevice.CountryService;
import mk.ukim.finki.wp.proekt.sevice.RegionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    private final CountryService countryService;

    public RegionServiceImpl(RegionRepository regionRepository, CountryService countryService) {
        this.regionRepository = regionRepository;
        this.countryService = countryService;
    }

    @Override
    public Region save(String name, List<Integer> country_ids) {
        if (!name.isEmpty() && country_ids.size()>=1 && this.regionRepository.findByName(name)==null){
            List<Country> countries = this.countryService.findAllById(country_ids);
            Region region= new  Region(name, countries);
            return this.regionRepository.save(region);
        }
        else if(!name.isEmpty() && country_ids.size()>=1 && this.regionRepository.findByName(name)!=null){
            Region region = this.regionRepository.findByName(name);
            List<Country> countries = this.countryService.findAllById(country_ids);
            region.setCountries(countries);
            region.setName(name);
            return this.regionRepository.save(region);
        }
        else{
            throw new InvalidRegionArgumentsException();
        }
    }

    @Override
    public Region findByName(String name) {
        if(!name.isEmpty()){
            return this.regionRepository.findByName(name);
        }
        else{
            throw new EmptyRegionNameException();
        }
    }

    @Override
    public Region findById(Integer id) {
        if(id!=null && this.regionRepository.findById(id).isPresent()){
            return this.regionRepository.findById(id).get();
        }
        else{
            throw new InvalidRegionIdException();
        }
    }

    @Override
    public List<Region> findAll() {
        return this.regionRepository.findAll();
    }
}
