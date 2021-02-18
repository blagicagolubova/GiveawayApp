package mk.ukim.finki.wp.proekt.sevice;

import mk.ukim.finki.wp.proekt.model.Award;
import org.springframework.web.method.annotation.AbstractWebArgumentResolverAdapter;

public interface AwardService {

    Award save(String name, Integer manufacturer_id);

    Award findById(Integer id);
}
