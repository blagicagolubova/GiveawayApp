package mk.ukim.finki.wp.proekt.sevice;

import mk.ukim.finki.wp.proekt.model.*;
import mk.ukim.finki.wp.proekt.model.enumerations.GiveawayStatus;
import mk.ukim.finki.wp.proekt.model.enumerations.UserType;

import java.util.Date;
import java.util.List;

public interface GiveawayService {

    Giveaway save(String name, Date startDate, Date endDate, Integer category_Id, Integer award_Id, UserType userType, String username, Integer giveawayRegion_Id, Integer company_id);

    List<Giveaway> findAll();

    List<Giveaway> findAvailableForParticipation(String username);

    List<Giveaway> top3AvailableForParticipation(String username);

    Giveaway addParticipant(Integer giveaway_id,  String username);

    User winner(Integer giveaway_id);

    List<Giveaway> myActiveGiveaways(String username);

    List<Giveaway> myFinishedGiveaways(String username);

    List<Giveaway> myGiveawaysWaitingForWinner(String username);

    Giveaway findById(Integer id);

    Boolean checkForParticipationInAGiveaway(Integer giveaway_id,  String username);

    Boolean checkIfGiveawayHasWinner(Integer giveaway_id);

    Boolean checkIfThereAreParticipantsInAGiveaway(Integer giveaway_id);

    Boolean checkIfUserIsCreator(Integer giveaway_id,String username);

    Boolean checkIfIsFinished(Integer giveaway_id);

    List<Giveaway> listByCategory(String search, String username);

    List<Giveaway> findAllByStatus(GiveawayStatus status);

    List<Giveaway> findAllByStatusAndCategory(String status, String search);

    void refreshGiveawayStatus();


}
