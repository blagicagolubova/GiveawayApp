package mk.ukim.finki.wp.proekt.sevice.impl;

import mk.ukim.finki.wp.proekt.model.Country;
import mk.ukim.finki.wp.proekt.model.GiveawayRegion;
import mk.ukim.finki.wp.proekt.model.exceptions.CanNotMakeGiveawayRegionWithoutCountriesException;
import mk.ukim.finki.wp.proekt.model.exceptions.InvalidGiveawayRegionIdException;
import mk.ukim.finki.wp.proekt.repository.GiveawayRegionRepository;
import mk.ukim.finki.wp.proekt.sevice.CountryService;
import mk.ukim.finki.wp.proekt.sevice.GiveawayRegionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiveawayRegionServiceImpl implements GiveawayRegionService {

    private final GiveawayRegionRepository giveawayRegionRepository;

    private final CountryService countryService;

    public GiveawayRegionServiceImpl(GiveawayRegionRepository giveawayRegionRepository, CountryService countryService) {
        this.giveawayRegionRepository = giveawayRegionRepository;
        this.countryService = countryService;
    }

    @Override
    public GiveawayRegion save(List<Integer> country_ids) {
        List<Country> countries = this.countryService.findAllById(country_ids);
        GiveawayRegion giveawayRegion= new GiveawayRegion(countries);
        return this.giveawayRegionRepository.save(giveawayRegion);
    }

    @Override
    public GiveawayRegion updateCountries(Integer giveawayRegion_id, List<Integer> country_ids) {
        if (giveawayRegion_id!=null && this.giveawayRegionRepository.findById(giveawayRegion_id).isPresent()){
            GiveawayRegion giveawayRegion=this.giveawayRegionRepository.findById(giveawayRegion_id).get();
            if (country_ids.size()>0){
                List<Country> new_countries= this.countryService.findAllById(country_ids);
                giveawayRegion.setCountries(new_countries);
                return this.giveawayRegionRepository.save(giveawayRegion);
            }
            else{
                throw new CanNotMakeGiveawayRegionWithoutCountriesException();
            }
        }
        else{
            throw new InvalidGiveawayRegionIdException();
        }

    }

    @Override
    public GiveawayRegion findById(Integer region_Id) {
        return this.giveawayRegionRepository.findById(region_Id).get();
    }


}
