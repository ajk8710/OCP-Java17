package ocpGuideBook.ch4;

import java.time.*;

public class Ch4CoreAPIs4DateTime {

    public static void main(String[] args) {
        
        // import java.time.* to use modern date & time related classes
        // (java.util.Date is an old class)
        LocalDate localDate;  // only local date
        LocalTime localTime;  // only local time
        LocalDateTime localDateTime;  // local date & time
        ZonedDateTime zonedDateTime;  // zoned date & time
        
        // These 4 classes have static method now() that obtains and returns system date/time now.
        System.out.println(LocalDate.now());  // 2023-12-20
        System.out.println(LocalTime.now());  // 20:22:07.973342500
        System.out.println(LocalDateTime.now());  // 2023-12-20T20:22:07.974353500
        System.out.println(ZonedDateTime.now());  // 2023-12-20T20:22:07.974353500-05:00[America/New_York]
        // EST time zone is -05:00 from GMT (or UTC)
        // To get GMT, subtract time zone -05:00 from local time. (EST 20:22 - -05:00 = GMT 25:22 = GMT 01:22 Next Day)
        
        
        // There is no public constructors, instead private constructors and static methods to create instances (factory pattern).
        // LocalDate.of(year, month, dayOfMonth)
        localDate = LocalDate.of(2023, 12, 20);  // input valid date, or runtime exception is thrown.
        localDate = LocalDate.of(2023, Month.DECEMBER, 20);  // Month is enum in java.time.
        
        // LocalTime.of(hour, min, sec, nano-sec)
        localTime = LocalTime.of(20, 22);  // At least hour and min are required.
        // localTime = LocalTime.of(24, 60);  // runtime exception invalid value (valid from 0 to 23 hour, 59 minute)
        
        localDateTime = LocalDateTime.of(localDate, localTime);
        
        // System.out.println(ZoneId.getAvailableZoneIds());  // lists all available zone IDs.
        ZoneId zoneId = ZoneId.of("US/Eastern");
        zonedDateTime = ZonedDateTime.of(localDate, localTime, zoneId);
        
        
        // Date & Time are immutable, but can use plus & minus methods that return new instance.
        localDate = localDate.plusYears(1);  // does not change original localDate, unless re-assigning.
        localDate = localDate.plusMonths(-1);  // parameter may be negative, but result must be valid date time.
        localDate = localDate.minusWeeks(1);
        localDate = localDate.minusDays(-1);
        
        localTime = localTime.plusHours(1);
        localTime = localTime.plusMinutes(-1);
        localTime = localTime.minusSeconds(1);
        localTime = localTime.minusNanos(-1);
        
    }

}
