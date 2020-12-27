package in.b2k.audit;

import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class ConstantDateTimeService implements DateTimeService {

    public static final String CURRENT_DATE_AND_TIME = getConstantDateAndTime();

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_ZONED_DATE_TIME;


    private static String getConstantDateAndTime() {
        return "2015-07-19T12:52:28" +
                getSystemZoneOffset() +
                getSystemZoneId();
    }

    private static String getSystemZoneOffset() {
        return ZonedDateTime.now().getOffset().toString();
    }

    private static String getSystemZoneId() {
        return "[" + ZoneId.systemDefault().toString() + "]";
    }

    @Override
    public ZonedDateTime getCurrentDateAndTime() {
        ZonedDateTime constantDateAndTime = ZonedDateTime.from(FORMATTER.parse(CURRENT_DATE_AND_TIME));

        log.info("Returning constant date and time: {}", constantDateAndTime);

        return constantDateAndTime;
    }
}
