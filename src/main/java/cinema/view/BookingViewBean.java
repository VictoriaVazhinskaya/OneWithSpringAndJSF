package cinema.view;

import cinema.dto.AfterBookingResponse;
import cinema.service.OnlineBookingService;
import org.springframework.web.context.annotation.RequestScope;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by vazhinskaya on 4/29/17.
 */
@Named
@RequestScope
public class BookingViewBean {

    @Inject
    private OnlineBookingService onlineBookingService;

    private AfterBookingResponse response;

    public AfterBookingResponse getResponse() {
        return response;
    }

    public void setResponse(AfterBookingResponse response) {
        this.response = response;
    }

    public String reserve(int seanceId, String seatsSequence){
        //System.out.println("seats: " + seatsSequence + ", id: " + seanceId);
        System.out.println(seanceId + "<- seanceId");
        response = onlineBookingService.reserve(seatsSequence.trim(), seanceId);
        //System.out.println(response.);
        return "booking_code";
    }
}
