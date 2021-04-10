package mk.ukim.finki.wp.proekt.jobs;

import mk.ukim.finki.wp.proekt.sevice.GiveawayService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private final GiveawayService giveawayService;

    public ScheduledTasks(GiveawayService giveawayService) {
        this.giveawayService = giveawayService;
    }

    //@Scheduled(cron = "")
    @Scheduled(cron = "0 13 1 ? * *")
    public void refreshGiveawayStatus(){
        giveawayService.refreshGiveawayStatus();
    }
}
