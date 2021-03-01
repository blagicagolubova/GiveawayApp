package mk.ukim.finki.wp.proekt.sevice.impl;

import mk.ukim.finki.wp.proekt.model.Award;
import mk.ukim.finki.wp.proekt.model.Manufacturer;
import mk.ukim.finki.wp.proekt.model.User;
import mk.ukim.finki.wp.proekt.repository.AwardRepository;
import mk.ukim.finki.wp.proekt.repository.UserRepository;
import mk.ukim.finki.wp.proekt.sevice.AwardService;
import mk.ukim.finki.wp.proekt.sevice.ManufacturerService;
import mk.ukim.finki.wp.proekt.sevice.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AwardServiceImpl implements AwardService {

    private final AwardRepository awardRepository;

    private final ManufacturerService manufacturerService;

    private final UserService userService;

    public AwardServiceImpl(AwardRepository awardRepository, ManufacturerService manufacturerService, UserService userService) {
        this.awardRepository = awardRepository;
        this.manufacturerService = manufacturerService;
        this.userService = userService;
    }

    @Override
    public Award save(String name, Float weight, String url, Integer manufacturer_id, String username) {
        if (this.manufacturerService.findById(manufacturer_id)!= null && !name.isEmpty()) {
            Manufacturer manufacturer=this.manufacturerService.findById(manufacturer_id);
            User user= this.userService.findByUsername(username);
            Award award= new Award(name, weight, url, manufacturer, user);
            return this.awardRepository.save(award);
        }
        else {
            //TODO:exception
            return null;
        }
    }

    @Override
    public Award update(Integer id, String name, Float weight, String url, Integer manufacturer_id, String username) {
       if(this.awardRepository.findById(id).isPresent()){
           Award a=this.awardRepository.findById(id).get();
           a.setName(name);
           a.setWeight(weight);
           a.setUrl(url);
           a.setManufacturer(this.manufacturerService.findById(manufacturer_id));
           return this.awardRepository.save(a);
       }
       else{
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

    @Override
    public List<Award> findAllByCreator(String username) {
        User user= this.userService.findByUsername(username);
        List<Award> awards= this.awardRepository.findAllByCreator(user);
        return awards;
    }

    @Override
    public void deleteById(Integer id) {
        this.awardRepository.deleteById(id);
    }
}
