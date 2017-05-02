package cinema.view;

import cinema.dto.BookingInfoResponse;
import cinema.dto.CancellationResponse;
import cinema.service.OnlineBookingService;
import org.springframework.web.context.annotation.SessionScope;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Pattern;
import java.io.IOException;

/**
 * Created by vazhinskaya on 5/2/17.
 */
@Named
@SessionScope
public class BookingCodeViewBean {

    @Inject
    private OnlineBookingService onlineBookingService;

    @Pattern(regexp="[0-9]{13,20}")
    private String code;

    private BookingInfoResponse response;


    public void getBookingInfo(){
        response = onlineBookingService.getBookingInfo(code.trim());
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        try {
            if(response.getErrorMessage() == null) {
                context.redirect("mybooking.xhtml");
            }else{
                code = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", response.getErrorMessage()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancelReservation(){
        CancellationResponse cancellationResponse = null;
        if(response != null && response.getErrorMessage() == null){
            cancellationResponse = onlineBookingService.cancelReservationWithoutValidation(code.trim());
        }else{
            cancellationResponse = onlineBookingService.cancelReservation(code.trim());
        }
        FacesContext context = FacesContext.getCurrentInstance();
        if(cancellationResponse.getErrorMessage() == null){
            context.addMessage(null, new FacesMessage("Successful",  "Your reservation is canceled.") );
            ExternalContext excontext = context.getExternalContext();
            excontext.getFlash().setKeepMessages(true);
            try {
                excontext.redirect(clear() + ".xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", cancellationResponse.getErrorMessage()));
        }
    }

    public BookingInfoResponse getResponse() {
        return response;
    }

    public void setResponse(BookingInfoResponse response) {
        this.response = response;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String clear(){
        code  = null;
        response = null;
        return "main";
    }

    public String goToMainPage(){
        return clear() + "?faces-redirect=true";
    }
}
