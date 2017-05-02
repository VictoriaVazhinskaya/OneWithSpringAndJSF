package cinema.dto;

import javax.faces.bean.SessionScoped;
import javax.inject.Named;

/**
 * Created by vazhinskaya on 4/29/17.
 */
@Named
@SessionScoped
public class AfterBookingResponse {

    private String bookingCode;
    private String errorMessage;

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
