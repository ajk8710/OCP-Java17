package ocpGuideBook.ch4;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class Ch4CoreAPIs4DateTimePeriodDuration {

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
        // EST time zone is -05:00 from GMT (or UTC). (During DST, it is -04:00)
        // To get GMT, subtract time zone -05:00 from local time. (EST 20:22 - -05:00 = GMT 25:22 = GMT 01:22 Next Day)
        
        
        // There is no public constructors, instead private constructors and static methods to create instances (factory pattern).
        // LocalDate.of(year, month, dayOfMonth)
        localDate = LocalDate.of(2023, 12, 20);  // input valid date, or runtime exception is thrown.
        localDate = LocalDate.of(2023, Month.DECEMBER, 20);  // Month is enum in java.time.
        
        // LocalTime.of(hour, min, sec, nano-sec)
        localTime = LocalTime.of(20, 22);  // At least hour and min are required.
        localTime = LocalTime.of(0, 59, 59);
        // localTime = LocalTime.of(24, 60);  // runtime exception invalid value (valid from 0 to 23 hour, 59 minute)
        
        localDateTime = LocalDateTime.of(localDate, localTime);  // At least year, month, day, hour, min required.
        
        // System.out.println(ZoneId.getAvailableZoneIds());  // lists all available zone IDs.
        ZoneId zoneId = ZoneId.of("US/Eastern");
        zonedDateTime = ZonedDateTime.of(localDate, localTime, zoneId);
        System.out.println(zonedDateTime.getOffset());  // -05:00. getOffset returns offset from this time zone to GMT.
        
        
        // Date & Time are immutable, but can use plus & minus methods that return new instance.
        localDate = localDate.plusYears(1);  // does not change original localDate, unless re-assigning.
        localDate = localDate.plusMonths(-1);  // parameter may be negative, but result must be valid date time.
        localDate = localDate.minusWeeks(1);  // weeks method converts weeks to days
        localDate = localDate.minusDays(-1);
        
        localTime = localTime.plusHours(1);
        localTime = localTime.plusMinutes(-1);
        localTime = localTime.minusSeconds(1);
        localTime = localTime.minusNanos(-1);
        
        var ddd = LocalDate.of(2022, Month.APRIL, 30);  // Java can do all normal operations. (knows after April is May, not April 32)
        System.out.println(ddd.plusDays(2));  // 2022-05-02
        
        
        // Use Period to work with Date & DateTime. Use Duration to work with Time & DateTime.
        
        // Period represents a specific time period >= a day. Number of years, months, weeks, days.
        // Static method Period.of(years, months, days) creates new instance.
        Period period = Period.of(1, 1, 1);  // 1 year, 1 month and 1 day (All three parameters are required)
        Period annual = Period.ofYears(1);
        Period quarterly = Period.ofMonths(3);
        Period everyThreeWeeks = Period.ofWeeks(3);
        Period everyOtherDay = Period.ofDays(2);
        
        // Method chaining do not apply here because it's static method. Following only does ofMonth.
        period = Period.ofYears(1).ofMonths(1);  // Static method must be accessed static way. It's accessing on returned instance instead of class.
        System.out.println(period);  // P1M. Period prints non-zero value in form of P1Y1M1D.
        
        // Period can be used to update Date & DateTime using plus(period or duration) and minus(period or duration) methods.
        LocalDate date1 = LocalDate.of(2023, 11, 20);
        LocalDate date2 = LocalDate.of(2023, 12, 20);
        LocalDate temp = date1;
        while (temp.isBefore(date2)) {
            System.out.println("Run");
            temp = temp.plus(period);  // make sure to assign return value
        }
        
        
        // Duration represents a specific time duration =< days. Number of days, hours, minutes, seconds, milli-sec, nano-sec.
        // Duration does not have a composite .of method like Period.of(years, months, days). To represent 1 hour and 30 min, use 90 min.
        Duration manyDays = Duration.ofDays(1000);  // Compiles, but bad practice. Use period instead.
        Duration daily = Duration.ofDays(1);
        Duration hourly = Duration.ofHours(1);
        Duration everyMinute = Duration.ofMinutes(1);
        Duration everySec = Duration.ofSeconds(1);
        Duration everyMilliSec = Duration.ofMillis(1);
        Duration everyNanoSec = Duration.ofNanos(1);
        System.out.println(hourly);  // PT1H. Duration prints with PT (period time).
        
        // Another .of method taking TemporalUnit interface. ChronoUnit implements TemporalUnit.
        Duration duration1 = Duration.of(1, ChronoUnit.HOURS);
        Duration duration2 = Duration.of(1, ChronoUnit.HALF_DAYS);  // duration of 1 half day = 12 hrs
        System.out.println(duration2);  // PT12H
        
        // ChronoUnit has static methods to calculate how far two date objects or two time objects are apart.
        LocalDate d1 = LocalDate.of(2023, 1, 1);
        LocalDate d2 = LocalDate.of(2023, 2, 1);
        System.out.println(ChronoUnit.DAYS.between(d1, d2));  // 31. Can't be HOURS or MINUTES because LocalDate does not have time related values.
        
        LocalDateTime dt1 = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime dt2 = LocalDateTime.of(2023, 2, 1, 0, 0, 1);
        System.out.println(ChronoUnit.SECONDS.between(dt1, dt2));  // 2678401
        
        // localTime & localDateTime has instance method truncatedTo().
        System.out.println(dt2);  // 2023-02-01T00:00:01
        dt2 = dt2.truncatedTo(ChronoUnit.MINUTES);  // truncatedTo zeroes out any fields smaller. (can pass a parameter up to DAYS)
        System.out.println(dt2);  // 2023-02-01T00:00
        
        // Duration can be used to update Time & DateTime using plus(period or duration) and minus(period or duration) methods.
        localTime = localTime.plus(daily);  // daily from daily = Duration.ofDays(1);
        // d1.plus(daily);  // runtime error. Duration should only be applied to Time representing objects (Time & DateTime).
        // localTime.plus(everyOtherDay);  // runtime error. Period should only be applied to Date representing objects (Date & DateTime)
        dt1 = dt1.plus(daily);
        dt1 = dt1.plus(everyOtherDay);
        
        
        // Java's ZonedDateTime is aware of DST and plus & minus functions works in consideration of DST.
        // For example, adding 2 hours to zonedDateTime and causing DST to change, will add 1 hour or 3 hours.
        
        // Java automatically handles DST.
        // There is DST change on 2022/03/13. After US time 1:59AM is 3:00AM. There is no 2AM ~ 2:59AM on this day.
        LocalDate ld = LocalDate.of(2022, 3, 13);
        LocalTime lt = LocalTime.of(2, 15);
        ZonedDateTime zdt = ZonedDateTime.of(ld, lt, ZoneId.of("US/Eastern"));  // Java auto converts 2:15AM to 3:15AM.
        System.out.println(zdt);  // 2022-03-13T03:15-04:00[US/Eastern]
        
        
        // Instant represents a specific moment in time in GMT. Static now() method returns current time in GMT.
        Instant now = Instant.now();
        Instant later = Instant.now();
        System.out.println(Duration.between(now, later));  // PT0S
        
        // zonedDateTime has instant method toInstant() returns GMT time of itself.
        System.out.println(zonedDateTime);  // 2023-12-20T00:59:59-05:00[US/Eastern]
        System.out.println(zonedDateTime.toInstant());  // 2023-12-20T05:59:59Z
        
    }

}
