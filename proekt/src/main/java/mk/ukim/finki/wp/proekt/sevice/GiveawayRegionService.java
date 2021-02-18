package mk.ukim.finki.wp.proekt.sevice;

import mk.ukim.finki.wp.proekt.model.Giveaway;
import mk.ukim.finki.wp.proekt.model.GiveawayRegion;

import java.util.List;

public interface GiveawayRegionService {

    GiveawayRegion save(List<Integer> country_ids);

    GiveawayRegion updateCountries(Integer giveawayRegion_id, List<Integer> country_ids);

    GiveawayRegion findById(Integer region_Id);

}
