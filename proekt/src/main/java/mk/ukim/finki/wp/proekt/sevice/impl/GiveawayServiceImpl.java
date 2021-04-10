package mk.ukim.finki.wp.proekt.sevice.impl;

import mk.ukim.finki.wp.proekt.model.*;
import mk.ukim.finki.wp.proekt.model.enumerations.GiveawayStatus;
import mk.ukim.finki.wp.proekt.model.enumerations.UserType;
import mk.ukim.finki.wp.proekt.repository.CategoryRepository;
import mk.ukim.finki.wp.proekt.repository.CompanyRepository;
import mk.ukim.finki.wp.proekt.repository.GiveawayRepository;
import mk.ukim.finki.wp.proekt.sevice.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
    public List<Giveaway> findAvailableForParticipation(String username) {
        List<Giveaway> giveawayList = this.findAll();
        List<Giveaway> list = new ArrayList<Giveaway>();
        for (Giveaway giveaway : giveawayList) {
            if (!giveaway.getCreator().getUsername().equals(username)
                    && giveaway.getStartDate().before(Date.from(java.time.ZonedDateTime.now().toInstant()))
                    && giveaway.getEndDate().after(Date.from(java.time.ZonedDateTime.now().toInstant()))) {
                list.add(giveaway);
            }
        }
        return list;
    }

    @Override
    public Giveaway addParticipant(Integer giveaway_id, String username) {
        Giveaway giveaway = this.findById(giveaway_id);
        User user = this.userService.findByUsername(username);
        List<User> participantList = giveaway.getParticipants();
        if (!participantList.contains(user)) {
            participantList.add(user);
            giveaway.setParticipants(participantList);
        }
        return this.giveawayRepository.save(giveaway);
    }

    @Override
    public User winner(Integer giveaway_id) {
        Giveaway giveaway = this.findById(giveaway_id);
        List<User> participantList = giveaway.getParticipants();
        Random randomizer = new Random();
        if(participantList.size()>0){
           User winner =  participantList.get(randomizer.nextInt(participantList.size()));
           giveaway.setWinner(winner);
           this.giveawayRepository.save(giveaway);
           return winner;
        }
       else {
           //ToDo:Exception
            return null;
        }
    }

    @Override
    public List<Giveaway> myActiveGiveaways(String username) {
        List<Giveaway> giveawayList = this.findAll();
        List<Giveaway> list = new ArrayList<Giveaway>();
        for (Giveaway giveaway : giveawayList) {
            if (giveaway.getCreator().getUsername().equals(username)
                    && giveaway.getStartDate().before(Date.from(java.time.ZonedDateTime.now().toInstant()))
                    && giveaway.getEndDate().after(Date.from(java.time.ZonedDateTime.now().toInstant()))) {
                list.add(giveaway);
            }
        }
        return list;
    }

    @Override
    public List<Giveaway> myFinishedGiveaways(String username) {
        List<Giveaway> giveawayList = this.findAll();
        List<Giveaway> list = new ArrayList<Giveaway>();
        for (Giveaway giveaway : giveawayList) {
            if (giveaway.getCreator().getUsername().equals(username)&&giveaway.getWinner()!=null) {
                list.add(giveaway);
            }
        }
        return list;
    }

    @Override
    public List<Giveaway> myGiveawaysWaitingForWinner(String username) {
        List<Giveaway> giveawayList = this.findAll();
        List<Giveaway> list = new ArrayList<Giveaway>();
        for (Giveaway giveaway : giveawayList) {
            if (giveaway.getCreator().getUsername().equals(username)
                    &&giveaway.getWinner()==null
                    && giveaway.getEndDate().before(Date.from(java.time.ZonedDateTime.now().toInstant()))) {
                list.add(giveaway);
            }
        }
        return list;
    }

    @Override
    public Giveaway findById(Integer id) {
        if(this.giveawayRepository.findById(id).isPresent())
        {return this.giveawayRepository.findById(id).get();}
        else {
        //ToDo:Exception
            return null;

        }
    }

    @Override
    public Boolean checkForParticipationInAGiveaway(Integer giveaway_id, String username) {
        Giveaway giveaway = this.findById(giveaway_id);
        User user = this.userService.findByUsername(username);
        List<User> participantList = giveaway.getParticipants();
        return participantList.contains(user);
    }

    @Override
    public Boolean checkIfGiveawayHasWinner(Integer giveaway_id) {
        Giveaway giveaway = this.findById(giveaway_id);
        return giveaway.getWinner() != null;
    }

    @Override
    public Boolean checkIfThereAreParticipantsInAGiveaway(Integer giveaway_id) {
        Giveaway giveaway = this.findById(giveaway_id);
        return giveaway.getParticipants().size()!=0;
    }

    @Override
    public Boolean checkIfUserIsCreator(Integer giveaway_id,String username) {
        Giveaway giveaway = this.findById(giveaway_id);
        return giveaway.getCreator().getUsername().equals(username);
    }

    @Override
    public Boolean checkIfIsFinshed(Integer giveaway_id) {
        Giveaway giveaway=this.findById(giveaway_id);
        if(giveaway.getWinner()==null
                && giveaway.getEndDate().before(Date.from(java.time.ZonedDateTime.now().toInstant()))) {
            return true;
        }
        else{
            return false;
        }

    }

    @Override
    public List<Giveaway> listByCategory(String search, String username) {
        List<Giveaway> available=this.findAvailableForParticipation(username);
        List<Giveaway> availableByCategory=new ArrayList<Giveaway>();
        for (Giveaway giveaway : available) {
            if (giveaway.getCategory().getName().equals(search)) {
                availableByCategory.add(giveaway);
            }
        }
        return availableByCategory;
    }

    @Override
    public void refreshGiveawayStatus() {
        List<Giveaway> giveawayList = this.findAll();
        for (Giveaway giveaway : giveawayList) {
            if (giveaway.getEndDate().before(Date.from(java.time.ZonedDateTime.now().toInstant()))){
                giveaway.setStatus(GiveawayStatus.FINISHED);
                this.giveawayRepository.save(giveaway);
            }
        }
    }


}


