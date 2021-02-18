package mk.ukim.finki.wp.proekt.sevice.impl;

import mk.ukim.finki.wp.proekt.model.Award;
import mk.ukim.finki.wp.proekt.model.Manufacturer;
import mk.ukim.finki.wp.proekt.repository.AwardRepository;
import mk.ukim.finki.wp.proekt.sevice.AwardService;
import mk.ukim.finki.wp.proekt.sevice.ManufacturerService;
import org.springframework.stereotype.Service;

@Service
public class AwardServiceImpl implements AwardService {

    private final AwardRepository awardRepository;

    private final ManufacturerService manufacturerService;

    public AwardServiceImpl(AwardRepository awardRepository, ManufacturerService manufacturerService) {
        this.awardRepository = awardRepository;
        this.manufacturerService = manufacturerService;
    }

    @Override
    public Award save(String name, Integer manufacturer_id) {
        if (this.manufacturerService.findById(manufacturer_id)!= null && !name.isEmpty()) {
            Manufacturer manufacturer=this.manufacturerService.findById(manufacturer_id);
            Award award= new Award(name, manufacturer);
            return this.awardRepository.save(award);
        }
        else {
            //TODO:exception
            return null;
        }
    }

    @Override
    public Award findById(Integer id) {
        if (this.awardRepository.findById(id).isPresent()){
            return this.awardRepository.findById(id).get();
        }
        else {
            //TODO:exception
            return null;
        }
    }
}
