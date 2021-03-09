package mk.ukim.finki.wp.proekt.sevice.impl;

import mk.ukim.finki.wp.proekt.model.*;
import mk.ukim.finki.wp.proekt.model.enumerations.UserType;
import mk.ukim.finki.wp.proekt.repository.CategoryRepository;
import mk.ukim.finki.wp.proekt.repository.CompanyRepository;
import mk.ukim.finki.wp.proekt.repository.GiveawayRepository;
import mk.ukim.finki.wp.proekt.sevice.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GiveawayServiceImpl implements GiveawayService {
    private final GiveawayRepository giveawayRepository;
    private  final UserService userService;
    private final CategoryRepository categoryRepository;
    private final AwardService awardService;
    private final GiveawayRegionService giveawayRegionService;
    private final CompanyRepository companyRepository;
    private final CompanyService companyService;

    public GiveawayServiceImpl(GiveawayRepository giveawayRepository, UserService userService, CategoryRepository categoryRepository, AwardService awardService, GiveawayRegionService giveawayRegionService, CompanyRepository companyRepository, CompanyService companyService) {
        this.giveawayRepository = giveawayRepository;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
        this.awardService = awardService;
        this.giveawayRegionService = giveawayRegionService;
        this.companyRepository = companyRepository;
        this.companyService = companyService;
    }

    @Override
    public Giveaway save(String name, Date startDate, Date endDate, Integer category_Id, Integer award_Id, UserType userType, String username, Integer giveawayRegion_Id,Integer company_id) {
        if(!name.isEmpty() && category_Id!=null && award_Id!=null && giveawayRegion_Id!=null  && !username.isEmpty()){
            Category category=this.categoryRepository.findById(category_Id).orElseThrow();
            Award award=this.awardService.findById(award_Id);
            User user=this.userService.findByUsername(username);
            GiveawayRegion region=this.giveawayRegionService.findById(giveawayRegion_Id);
            if(company_id==null){
                Giveaway giveaway= new Giveaway(name,startDate,endDate,category,award, userType,user,region);
                return this.giveawayRepository.save(giveaway);
            }
           else {
               Company company= this.companyService.findById(company_id);
               Giveaway giveaway= new Giveaway(name,startDate,endDate,category,award, userType,user,company,region);
               return this.giveawayRepository.save(giveaway);
           }
        }
        else{
            //TODO: exception
            return null;
        }
    }

    @Override
    public List<Giveaway> findAll() {
        return this.giveawayRepository.findAll();
    }

    @Override
    public Giveaway addParticipant(Integer giveaway_id, String username) {
        return null;
    }

    @Override
    public Giveaway winner(Integer giveaway_id) {
        return null;
    }
}
