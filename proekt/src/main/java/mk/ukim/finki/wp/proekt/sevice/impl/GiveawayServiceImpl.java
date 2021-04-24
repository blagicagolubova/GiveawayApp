package mk.ukim.finki.wp.proekt.sevice.impl;

import mk.ukim.finki.wp.proekt.model.*;
import mk.ukim.finki.wp.proekt.model.enumerations.GiveawayStatus;
import mk.ukim.finki.wp.proekt.model.enumerations.UserType;
import mk.ukim.finki.wp.proekt.model.exceptions.CanNotChooseWinnerIfThereAreNotParticipantsException;
import mk.ukim.finki.wp.proekt.model.exceptions.InvalidGiveawayArgumentsException;
import mk.ukim.finki.wp.proekt.model.exceptions.InvalidGiveawayIdException;
import mk.ukim.finki.wp.proekt.model.exceptions.InvalidGiveawayRegionIdException;
import mk.ukim.finki.wp.proekt.repository.CategoryRepository;
import mk.ukim.finki.wp.proekt.repository.GiveawayRepository;
import mk.ukim.finki.wp.proekt.sevice.*;
import mk.ukim.finki.wp.proekt.views.Top3;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class GiveawayServiceImpl implements GiveawayService {

    private final GiveawayRepository giveawayRepository;
    private final UserService userService;
    private final CategoryRepository categoryRepository;
    private final AwardService awardService;
    private final GiveawayRegionService giveawayRegionService;
    private final CompanyService companyService;
    private final EmailService emailService;
    private final CategoryService categoryService;

    public GiveawayServiceImpl(GiveawayRepository giveawayRepository, UserService userService, CategoryRepository categoryRepository, AwardService awardService, GiveawayRegionService giveawayRegionService, CompanyService companyService, EmailService emailService, CategoryService categoryService) {
        this.giveawayRepository = giveawayRepository;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
        this.awardService = awardService;
        this.giveawayRegionService = giveawayRegionService;
        this.companyService = companyService;
        this.emailService = emailService;
        this.categoryService = categoryService;
    }

    @Override
    public Giveaway save(String name, Date startDate, Date endDate, Integer category_Id, Integer award_Id, UserType userType, String username, Integer giveawayRegion_Id,Integer company_id) {
        if(!name.isEmpty() && category_Id!=null && award_Id!=null && giveawayRegion_Id!=null  && !username.isEmpty()){
            Category category=this.categoryRepository.findById(category_Id).orElseThrow();
            Award award=this.awardService.findById(award_Id);
            User user=this.userService.findByUsername(username);
            GiveawayRegion region=this.giveawayRegionService.findById(giveawayRegion_Id);
            if(company_id==null){
                if (startDate.before(Date.from(java.time.ZonedDateTime.now().toInstant()))){
                    Giveaway giveaway= new Giveaway(name,startDate,endDate,category,award, userType,user,region,GiveawayStatus.ACTIVE);
                    return this.giveawayRepository.save(giveaway);
                }
                else {
                    Giveaway giveaway= new Giveaway(name,startDate,endDate,category,award, userType,user,region,GiveawayStatus.PENDING);
                    return this.giveawayRepository.save(giveaway);
                }
            }
           else {
               Company company= this.companyService.findById(company_id);
                if (startDate.before(Date.from(java.time.ZonedDateTime.now().toInstant()))){
                    Giveaway giveaway= new Giveaway(name,startDate,endDate,category,award, userType,user,company,region, GiveawayStatus.ACTIVE);
                    return this.giveawayRepository.save(giveaway);
                }
                else {
                    Giveaway giveaway= new Giveaway(name,startDate,endDate,category,award, userType,user,company,region, GiveawayStatus.PENDING);
                    return this.giveawayRepository.save(giveaway);
                }
           }
        }
        else{
            throw new InvalidGiveawayArgumentsException();
        }
    }

    @Override
    public List<Giveaway> findAll() {
        return this.giveawayRepository.findAll();
    }

    @Override
    public List<Giveaway> findAvailableForParticipation(String username) {
        List<Giveaway> giveawayList = this.giveawayRepository.findAllByStatus(GiveawayStatus.ACTIVE);
        List<Giveaway> list = new ArrayList<Giveaway>();
        for (Giveaway giveaway : giveawayList) {
            if (!giveaway.getCreator().getUsername().equals(username)) {
                list.add(giveaway);
            }
        }
        return list;
    }

    @Override
    public List<Giveaway> top3AvailableForParticipation(String username) {
        List<Top3> top3s = giveawayRepository.getTOP3();
        List<Giveaway> listtop3s = new ArrayList<Giveaway>();
        for(Top3 top3 : top3s)
        {
            listtop3s.add(giveawayRepository.findById(top3.getID()).get());
        }
        int i=0;
        List<Giveaway> list = new ArrayList<Giveaway>();
        for (Giveaway giveaway : listtop3s) {
            if (username!=null) {
                if (!giveaway.getCreator().getUsername().equals(username)) {//&&givaways.status==active
                    list.add(giveaway);
                    i++;
                }
                if(i==3){
                    break;
                }

            }
            else
            {
                list.add(giveaway);
                i++;
                if(i==3){
                    break;
                }
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
           this.emailService.sendSimpleMessage(winner.getEmail(), "Giveaway winner", "You are winner for giveaway with name "+ giveaway.getName());
           this.giveawayRepository.save(giveaway);
           return winner;
        }
       else {
           throw new CanNotChooseWinnerIfThereAreNotParticipantsException();
        }
    }

    @Override
    public List<Giveaway> myActiveGiveaways(String username) {
        List<Giveaway> giveawayList = this.giveawayRepository.findAllByStatus(GiveawayStatus.ACTIVE);
        List<Giveaway> list = new ArrayList<Giveaway>();
        for (Giveaway giveaway : giveawayList) {
            if (giveaway.getCreator().getUsername().equals(username)) {
                list.add(giveaway);
            }
        }
        return list;
    }

    @Override
    public List<Giveaway> myFinishedGiveaways(String username) {
        List<Giveaway> giveawayList = this.giveawayRepository.findAllByStatus(GiveawayStatus.FINISHED);
        List<Giveaway> list = new ArrayList<Giveaway>();
        for (Giveaway giveaway : giveawayList) {
            if (giveaway.getCreator().getUsername().equals(username)) {
                if(giveaway.getWinner()!=null)
                {
                    list.add(giveaway);
                }
                else if(giveaway.getParticipants().size()==0){
                    list.add(giveaway);
                }
            }
       }
        return list;
    }

    @Override
    public List<Giveaway> myGiveawaysWaitingForWinner(String username) {
        List<Giveaway> giveawayList = this.giveawayRepository.findAllByStatus(GiveawayStatus.FINISHED);
        List<Giveaway> list = new ArrayList<Giveaway>();
        for (Giveaway giveaway : giveawayList) {
            if (giveaway.getCreator().getUsername().equals(username) && giveaway.getWinner()==null) {
                if(giveaway.getParticipants().size()!=0) {
                    list.add(giveaway);
                }
            }
        }
        return list;
    }

    @Override
    public Giveaway findById(Integer id) {
        if(this.giveawayRepository.findById(id).isPresent())
        {return this.giveawayRepository.findById(id).get();}
        else {
            throw new InvalidGiveawayIdException();
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
    public Boolean checkIfIsFinished(Integer giveaway_id) {
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
        if(search.equals("all")){
            return available;
        }
        else {
            for (Giveaway giveaway : available) {
                if (giveaway.getCategory().getName().equals(search)) {
                    availableByCategory.add(giveaway);
                }
            }
            return availableByCategory;
        }
    }

    @Override
    public List<Giveaway> findAllByStatus(GiveawayStatus status) {
        return this.giveawayRepository.findAllByStatus(status);
    }

    @Override
    public List<Giveaway> findAllByStatusAndCategory(String status, String search) {
        if (status==null && search==null){
            return this.findAll();
        }
        else if(status.equals("all") && search.equals("all")){
            return this.findAll();
        }
        else if(search.equals("all")) {
            GiveawayStatus status1= GiveawayStatus.valueOf(status);
            return this.findAllByStatus(status1);
        }
        else if(status.equals("all")) {
            Category category=this.categoryService.findByName(search);
            return this.giveawayRepository.findAllByCategory(category);
        }
        else {
            GiveawayStatus status1= GiveawayStatus.valueOf(status);
            Category category=this.categoryService.findByName(search);
            return this.giveawayRepository.findAllByStatusAndCategory(status1, category);
        }
    }

    @Override
    public void refreshGiveawayStatus() {
        List<Giveaway> giveawayList = this.findAll();
        for (Giveaway giveaway : giveawayList) {
            if (giveaway.getEndDate().before(Date.from(java.time.ZonedDateTime.now().toInstant()))){
                giveaway.setStatus(GiveawayStatus.FINISHED);
                this.giveawayRepository.save(giveaway);
            }
            else if (giveaway.getStartDate().before(Date.from(java.time.ZonedDateTime.now().toInstant()))){
                giveaway.setStatus(GiveawayStatus.ACTIVE);
                this.giveawayRepository.save(giveaway);
            }
        }
    }


}


