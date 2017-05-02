package cinema.service;

import cinema.dao.OnlineBookingDao;
import cinema.dto.*;
import cinema.manager.MessageManager;
import cinema.util.BookingCodeValidator;
import cinema.util.SeatNumberValidator;
import cinema.util.SeatsSequenceInputValidator;
import cinema.util.StringFromNumbersMaker;
import org.springframework.web.bind.annotation.RequestBody;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by vazhinskaya on 4/29/17.
 */
@Named
public class OnlineBookingService {

    @Inject
    private OnlineBookingDao onlineBookingDao;

    @Inject
    private MessageManager manager;

    public List<Short> getReservedSeats(int seanceId){
          return onlineBookingDao.getReservedSeats(seanceId);
    }

    public AfterBookingResponse reserve(final String seatsSequence, int seanceId){
        AfterBookingResponse response = new AfterBookingResponse();
        String errorMessage = null;
        String reservationCode = null;
        if(SeatsSequenceInputValidator.isValid(seatsSequence)) {
            String[] numbersAsString = seatsSequence.split("\\s+");
            List<Short> seats = Arrays.stream(numbersAsString).map(Short::valueOf).collect(Collectors.toList());
            if (SeatNumberValidator.isValidNumbers(seats) == SeatNumberValidator.SUCCESSFUL_VALIDATION) {
                System.out.println("Seat numbers are valid!!!");
                Set<Short> alreadyReserved = new HashSet<Short>(onlineBookingDao.getReservedSeats(seanceId));
                List<Short> unavailableSeats = seats.stream().filter(seat -> alreadyReserved.contains(seat))
                            .collect(Collectors.toList());
                    if (unavailableSeats.isEmpty()) {
                        SeanceShortInfo info = onlineBookingDao.getSeanceInfo(seanceId);
                        reservationCode = generateBookingCode(info, seats.get(0));
                        onlineBookingDao.addReservation(reservationCode, seats, seanceId);
                    } else {
                        errorMessage = String.format(manager.getProperty(MessageManager
                                        .TRYING_RESERVE_ALREADY_RESERVED_SEATS_ERR_MSG),
                                StringFromNumbersMaker.getStringRepresentation(unavailableSeats));
                    }
            }else{
                errorMessage = manager.getProperty(MessageManager.NONEXISTENT_SEAT_ERR_MSG);
            }
        }else{
            errorMessage = manager.getProperty(MessageManager.INVALID_SEATS_NUMBERS_SEQUENCE_ERR_MSG);
        }
        response.setBookingCode(reservationCode);
        response.setErrorMessage(errorMessage);
        return response;
    }

    public BookingInfoResponse getBookingInfo(final String input){
        BookingInfoResponse response = new BookingInfoResponse();
        boolean isBookingReal = false;
        if(BookingCodeValidator.isValid(input)) {
            Booking booking = onlineBookingDao.getBookingInfo(input);
            if(booking != null) {
                response.setBooking(booking);
                response.setErrorMessage(null);
                isBookingReal = true;
            }
        }
        if(!isBookingReal){
            response.setBooking(null);
            String errorMessage = String.format(manager.getProperty(MessageManager
                    .NONEXISTENT_RESERVATION_CODE_ERR_MSG), input);
            response.setErrorMessage(errorMessage);
        }
        return response;
    }

    public CancellationResponse cancelReservation(final String input){
        String inputString = input;
        CancellationResponse response = new CancellationResponse();
        String errorMessage = null;
        boolean isReservationCanceled = false;
        if(BookingCodeValidator.isValid(inputString)) {
            isReservationCanceled = onlineBookingDao.deleteBooking(inputString);
        }
        if(!isReservationCanceled) {
            errorMessage = String.format(manager.getProperty(MessageManager
                    .RESERVATION_CANCELING_FAILURE_ERR_MSG), String.format(manager.getProperty(MessageManager
                    .NONEXISTENT_RESERVATION_CODE_ERR_MSG), inputString));
        }
        response.setErrorMessage(errorMessage);
        return response;
    }

    public CancellationResponse cancelReservationWithoutValidation(final String bookingCode){
        CancellationResponse response = new CancellationResponse();
        onlineBookingDao.deleteBooking(bookingCode);
        response.setErrorMessage(null);
        return response;
    }

    private String generateBookingCode(SeanceShortInfo info, short seatNumber) {
        int filmId = info.getFilm_id();
        String date = info.getDate().toString().replaceAll("-", "");
        String time = info.getTime().toString().substring(0, 6).replaceAll(":", "");
        return String.format("%1$d%2$s%3$s%4$d", filmId, date, time, seatNumber);
    }
}
