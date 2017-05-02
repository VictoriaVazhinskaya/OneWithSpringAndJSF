package cinema.view;

import cinema.dao.OnlineBookingDao;
import cinema.dto.ReservedSeatsResponse;
import cinema.dto.Seance;
import cinema.service.OnlineBookingService;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by vazhinskaya on 4/29/17.
 */
@Named
@SessionScope
public class SeanceInfoViewBean {

    @Inject
    private OnlineBookingService onlineBookingService;

    private Seance seance;

    private List<Short> alreadyReserved;


    public String makeReservation(Seance seance){
        this.seance = seance;
        alreadyReserved = onlineBookingService.getReservedSeats(seance.getId());
        return "booking";
    }

    public Seance getSeance() {
        return seance;
    }

    public void setSeance(Seance seance) {
        this.seance = seance;
    }

    public List<Short> getAlreadyReserved() {
        return alreadyReserved;
    }

    public void setAlreadyReserved(List<Short> alreadyReserved) {
        this.alreadyReserved = alreadyReserved;
    }
}
