package ocpGuideBook.cha11;

import java.text.NumberFormat;
import java.text.NumberFormat.Style;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Locale.Category;
import java.util.stream.Stream;

public class Ch11Localization {
    
    public static void main(String[] args) {
        
        // Locale: smallCaseLanguage or smallCaseLanguage_upperCaseCounty
        // Cannot have just country.
        Locale locale = Locale.getDefault();
        System.out.println(locale);  // en_US
        
        // Get locale
        // Using built-in constants
        System.out.println(Locale.FRENCH);  // fr
        System.out.println(Locale.FRANCE);  // fr_FR
        
        // Using constructor. You can throw in any character but it will not work as intended.
        System.out.println(new Locale("fr"));  // fr
        System.out.println(new Locale("fr", "FR"));  // fr_FR
        
        // Using builder. Can set properties in any order, then build().
        Locale l1 = new Locale.Builder().setLanguage("en").setRegion("US").build();
        System.out.println(l1);  // en_US
        
        // Set default locale. In practice, you rarely write code to change default locale. But this way, you can see how it behaves in user's locale.
        Locale.setDefault(Locale.FRANCE);
        
        
        // Localizing numbers: NumberFormat
        // Below methods get NumberFormat with default locale. Each method has overloaded version with locale parameter.
        // NumberFormat.getInstance(), getNumberInstance (same as getInstance), getCurrencyInstance, getPercentInstance, getIntegerInstance, getCompactNumberInstance.
        // Note DateTimeFormatter is with -ter and NumberFormat(ter) is just NumberFormat.
        
        int visitors = 3_200_000;
        int perMonth = visitors/12;
        
        NumberFormat numFormat = NumberFormat.getInstance();  // Default locale is French right now.
        System.out.println(numFormat.format(perMonth));  // 266 666  It's how French writes number.
        
        Locale.setDefault(Locale.US);
        numFormat = NumberFormat.getInstance();  // Default locale is US now.
        System.out.println(numFormat.format(perMonth));  // 266,666
        
        numFormat = NumberFormat.getInstance(Locale.GERMANY);
        System.out.println(numFormat.format(perMonth));  // 266.666  It's how German writes number.
        
        // In practice, use int, long, or BigDecimal instead of double for money. Doing math with double may loose small fractions because double is floating point number.
        // In practice, you write getInstance() and rely on user's default locale, to format the output.
        
        numFormat = NumberFormat.getCurrencyInstance();
        System.out.println(numFormat.format(2100.209));  // $2,100.21
        
        numFormat = NumberFormat.getCurrencyInstance(Locale.UK);
        System.out.println(numFormat.format(2100.209));  // £2,100.21
        
        numFormat = NumberFormat.getCurrencyInstance(Locale.FRANCE);
        System.out.println(numFormat.format(2100.209));  // 2 100,21 €  (French uses comma as decimal separator.)
        
        numFormat = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        System.out.println(numFormat.format(2100.209));  // 2.100,21 €  (German uses comma as decimal separator, and dot as number separator.)
        
        numFormat = NumberFormat.getPercentInstance();
        System.out.println(numFormat.format(0.95211));  // 95%
        
        numFormat = NumberFormat.getPercentInstance(Locale.FRANCE);
        System.out.println(numFormat.format(0.95211));  // 95 %
        
        numFormat = NumberFormat.getPercentInstance();
        try {  // parse takes String and parse to Number. If format of string is invalid for the specific instance, throws ParseException.
            System.out.println(numFormat.parse("21.216%"));  // 0.21216000000000002
            double doubleNum = (Double) numFormat.parse("21.216%");
            System.out.println(doubleNum);  // 0.21216000000000002
        }
        catch (ParseException e) {
            System.out.println("Exception happened");
        }
        
        numFormat = NumberFormat.getCurrencyInstance();
        try {
            System.out.println(numFormat.parse("$2,100.216"));  // 2100.216
        }
        catch (ParseException e) {
            System.out.println("Exception happened");
        }
        
        // CompactNumberFormat is another concrete class that implements NumberFormat. (The other one was DecimalFormat.)
        // It's designed to be used in limited spaces, and truncated output can be different by locale.
        Stream<NumberFormat> formatters =
            Stream.of(
                NumberFormat.getCompactNumberInstance(),  // 8M  Short is default.
                NumberFormat.getCompactNumberInstance(Locale.getDefault(), Style.SHORT),  // 8M
                NumberFormat.getCompactNumberInstance(Locale.getDefault(), Style.LONG),  // 8 million
                NumberFormat.getCompactNumberInstance(Locale.GERMAN, Style.SHORT),  // 8 Mio.
                NumberFormat.getCompactNumberInstance(Locale.GERMAN, Style.LONG),  // 8 Million
                NumberFormat.getNumberInstance()  // 7,890,123
            );
        
        formatters.map(s -> s.format(7_890_123)).forEach(System.out::println);
        
        
        // Localizing dates: DateTimeFormatter
        // Below methods are used to get an instance of DateTimeFormatter with default locale.
        // ofLocalizedDate(FormatStyle dateStyle), ofLocalizedTime(FormatStyle timeStyle)
        // ofLocalizedDateTime(FormatStyle dateStyle, FormatStyle timeStyle)
        // ofLocalizedDateTime(FormatStyle dateTimeStyle)
        // FormatStyle is enum with SHORT, MEDIUM, LONG, FULL. No need to know exact format of these for exam.
        
        LocalDate date = LocalDate.of(1999, Month.JANUARY, 2);
        LocalTime time = LocalTime.of(1, 2, 3);
        LocalDateTime dt = LocalDateTime.of(date, time);
        
        // See printDT method below.
        // DateTimeFormatter can call format(dt), or call withLocale(locale) then format(dt).
        printDT(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT), dt, Locale.FRANCE);  // 1/2/99 --- 02/01/1999
        printDT(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT), dt, Locale.FRANCE);  // 1:02 AM --- 01:02
        printDT(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT), dt, Locale.FRANCE);  // 1/2/99, 1:02 AM --- 02/01/1999 01:02
        
        
        // When doing Locale.setDefault(), several display and formatting options are internally selected.
        // For finer-grained control of setting default locale, Java subdivides these options to distinct categories.
        // Locale.Category is nested enum in Locale. For exam, know two: DISPLAY, FORMAT, and they can be set independently.
        // DISPLAY: for displaying data about locale. FORMAT: for formatting dates, numbers, currencies.
        
        double money = 2_100.21;  // default is US right now.
        printCurrency(money, Locale.FRANCE);  // $2,100.21, French
        
        Locale.setDefault(Locale.FRANCE);  // default as France.
        printCurrency(money, Locale.FRANCE);  // 2 100,21 €, français
        
        Locale.setDefault(Locale.US);  // set default back to US.
        
        // setDefault(Category, newLocaleForCategory);
        Locale.setDefault(Category.DISPLAY, Locale.FRANCE);  // finer-grained control, just change display.
        printCurrency(money, Locale.FRANCE);  // $2,100.21, français
        
        Locale.setDefault(Locale.US);  // set default back to US.
        
        Locale.setDefault(Category.FORMAT, Locale.FRANCE);  // finer-grained control, just change date/number/currency format.
        printCurrency(money, Locale.FRANCE);  // 2 100,21 €, French
        
    }
    
    // Method for dateTimeFormatter.format(dateTime).
    // DateTimeFormatter can call format(), or call withLocale() then format().
    public static void printDT(DateTimeFormatter dtf, LocalDateTime dateTime, Locale locale) {
        System.out.println(dtf.format(dateTime) + " --- " + dtf.withLocale(locale).format(dateTime));
    }
    
    // Method showing FORMAT & DISPLAY can be set independently.
    public static void printCurrency(double money, Locale locale) {
       System.out.println(NumberFormat.getCurrencyInstance().format(money) + ", " + locale.getDisplayLanguage());
    }
    
}
