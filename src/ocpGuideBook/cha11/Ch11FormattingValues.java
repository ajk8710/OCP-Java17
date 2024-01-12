package ocpGuideBook.cha11;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class Ch11FormattingValues {
    
    public static void main(String[] args) {
        
        // Formatting numbers:
        
        // NumberFormat interface includes these methods.
        // public final String format(double number)
        // public final String format(long number)
        
        // DecimalFormat is concrete class that implements NumberFormat.
        // DecimalFormat includes this constructor.
        // public DecimalFormat(String pattern)
        
        // #: Omit position if no digit exists for it.
        // 0: Put 0 in position if no digit exists for it.
        // Last digit is rounded when cut off.
        
        double d = 1234.567;
        // can only have one dot . (decimal separator)
        NumberFormat f1 = new DecimalFormat("###,###,###.0");
        System.out.println(f1.format(d));  // 1,234.6
        
        NumberFormat f2 = new DecimalFormat("000,000,000.00000");
        System.out.println(f2.format(d));  // 000,001,234.56700
        
        NumberFormat f3 = new DecimalFormat("Your Balance $#,###,###.##");
        System.out.println(f3.format(d));  // Your Balance $1,234.57
        
        // Another way to separate with comma is %,
        String string = "123456789.101";
        Double number = Double.parseDouble(string);
        System.out.println(String.format("%,.2f", number));  // 123,456,789.10
        
        
        // Formatting Dates and Times:
        
        LocalDate date = LocalDate.of(2022, Month.OCTOBER, 20);
        LocalTime time = LocalTime.of(11, 12, 34);
        LocalDateTime dt = LocalDateTime.of(date, time);
        
        // format method of Date, Time, DateTime: format(DateTimeFormatter)
        System.out.println(date.format(DateTimeFormatter.ISO_LOCAL_DATE));  // 2022-10-20  Runtime error if ISO_LOCAL_TIME
        System.out.println(time.format(DateTimeFormatter.ISO_LOCAL_TIME));  // 11:12:34  Runtime error if ISO_LOCAL_DATE
        System.out.println(dt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));  // 2022-10-20T11:12:34
        
        System.out.println(dt);  // 2022-10-20T11:12:34  It looks default format is ISO.
        System.out.println(dt.format(DateTimeFormatter.ISO_LOCAL_DATE));  // 2022-10-20  Let formatter to print date only.
        
        date = LocalDate.of(1999, Month.JANUARY, 2);
        time = LocalTime.of(1, 2, 3);
        dt = LocalDateTime.of(date, time);
        
        // Custom date/time pattern formatter
        // M for month, d for day, (M to differentiate with m) etc... a for AM/PM, z for time zone for only ZonedDateTime & Z for offset.
        // M: 1 or 2 digit, MM: 2 digit, MMM: 3 chars, MMMM: full
        // yy: last 2 digit, y/yyy/yyyy: full
        // Single quotes to escape. Add another ' to write char '. Colon : does not need single quotes.
        // Use compatible symbols: trying to format a month for a LocalTime or an hour for a LocalDate results runtime exception.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' hh:mm:ss");
        System.out.println(dt.format(formatter));  // January 02, 1999 at 01:02:03
        
        formatter = DateTimeFormatter.ofPattern("M d, yyy 'aT!!!' h-m-s"); 
        System.out.println(dt.format(formatter));  // 1 2, 1999 aT!!! 1-2-3
        
        formatter = DateTimeFormatter.ofPattern("MM dd, yy    h:m");
        System.out.println(dt.format(formatter));  // 01 02, 99    1:2
        
        formatter = DateTimeFormatter.ofPattern("MMM dd, y hh:mm a");
        System.out.println(dt.format(formatter));  // Jan 02, 1999 01:02 AM
        
        // DateTime.format(dateTimeFormatter) does same thing as DateTimeFormatter.format(dateTime).
        System.out.println(formatter.format(dt));  // Jan 02, 1999 01:02 AM
        
    }
    
}
