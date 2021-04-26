package mk.ukim.finki.wp.proekt.sevice;

import mk.ukim.finki.wp.proekt.model.Award;
import mk.ukim.finki.wp.proekt.model.enumerations.AwardStatus;

import java.util.List;

public interface AwardService {

    Award save(String name, Float weight, String url, Integer manufacturer_id, String username);

    Award update(Integer id, String name, Float weight, String url, Integer manufacturer_id, String username);

    Award findById(Integer id);

    List<Award> findAllByCreator(String username);

    List<Award> findAllByStatusAndUser(AwardStatus status, String username);

    Award updateStatus(Integer awardId,AwardStatus awardStatus);

    List<Award> findAll();

    void deleteById(Integer id);
}
