package mk.ukim.finki.wp.proekt.sevice;

import mk.ukim.finki.wp.proekt.model.*;

import java.util.Date;
import java.util.List;

public interface GiveawayService {

    Giveaway save(String name, Date startDate, Date endDate, Integer category_Id, Integer award_Id, String username, Integer giveawayRegion_Id);

    List<Giveaway> findAll();

    Giveaway addParticipant(Integer giveaway_id,  String username);

    Giveaway winner(Integer giveaway_id);
}
