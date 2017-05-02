package cinema.view;

import cinema.dao.OnlineBookingDao;
import cinema.dto.Seance;

import javax.faces.bean.SessionScoped;
import javax.faces.event.NamedEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by vazhinskaya on 4/28/17.
 */
@Named
@SessionScoped
public class ScheduleViewBean {

    @Inject
    private OnlineBookingDao onlineBookingDao;

    private List<Seance> seances;

    public String getSchedule(){
        seances = onlineBookingDao.getSchedule();
        return "schedule";
    }

    public List<Seance> getSeances() {
        return seances;
    }


}
