package cinema.util;

import cinema.dao.OnlineBookingDao;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vazhinskaya on 4/29/17.
 */

public class SeatsSequenceInputValidator {

    private static final String SEATS_NUMBER_SEQUENCE_REGEX = "(\\d{1,3}\\s*){1,30}";


    public static boolean isValid(String seatsSequence){
        Pattern pattern = Pattern.compile(SEATS_NUMBER_SEQUENCE_REGEX);
        Matcher matcher = pattern.matcher(seatsSequence);
        return matcher.matches();
    }
}
