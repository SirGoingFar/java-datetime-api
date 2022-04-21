import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeOps {

    public static void main(String[] args) {
        localDate();
        localTime();
        localDateTime();
        zonedDateTime();
        period();
        duration();
        conversions();
        dateTimeFormatting();
    }

    private static void localDate() {

        System.out.println("LocalDate:");

        //Instantiation
        final LocalDate localDate = LocalDate.now(); //Derived from system clock with format ISO format (yyyy-MM-dd)
        final LocalDate specificDate = LocalDate.of(2022, 2, 1); //2022-Feb-1
        final LocalDate anotherSpecificDate = LocalDate.parse("2022-02-01"); //2022-Feb-1

        System.out.println("\n> Instantiation:");
        System.out.printf("Now: %s\nLocalDate.of(): %s\nLocalDate.parse(): %s", localDate, specificDate, anotherSpecificDate);
        System.out.println();

        //Common operations - for every "plus", there is a "minus"
        final LocalDate yesterday = LocalDate.of(2022, 1, 1).minusDays(1); //subtract 1 day and resolve to 2021-12-31
        LocalDate tomorrow = LocalDate.now().plusDays(1); //adds 1 day
        final LocalDate nextWeek = LocalDate.now().plusWeeks(1);
        final LocalDate thisDayNextMonth = LocalDate.now().plusMonths(1);
        final LocalDate thisDayNextYear = LocalDate.now().plusYears(1);

        System.out.println("\n> Common operations");
        System.out.printf("Yesterday: %s\nTomorrow: %s\nNext week: %s\nThis day next month: %s\nThis day next year: %s\n",
                yesterday, tomorrow, nextWeek, thisDayNextMonth, thisDayNextYear);

        //Other operations
        tomorrow = tomorrow.plus(1, ChronoUnit.DAYS);
        final DayOfWeek dow = tomorrow.getDayOfWeek(); //Gets the day of the week
        final int dom = tomorrow.getDayOfMonth(); //Gets the day of the month
        final Month month = tomorrow.getMonth(); //Gets the month
        final boolean isLeapYear = tomorrow.isLeapYear();
        final boolean isBefore = nextWeek.isBefore(tomorrow); //Check if "nextWeek" is before "tomorrow"
        final boolean isAfter = nextWeek.isAfter(tomorrow); //Check if "nextWeek" is after "tomorrow"

        System.out.println("\n> Other operations:");
        System.out.printf("LocalDate: %s\nDay of Week: %s\nDay of Month: %s\nMonth: %s\nIs Leap Year: %s\nisBefore tomorrow: %s\nisAfter tomorrow: %s", tomorrow,
                dow, dom, month, isLeapYear, isBefore, isAfter);

        //Conversions
        System.out.println("\n\n> Conversions:");
        final LocalDateTime beginningOfToday = LocalDate.now().atStartOfDay();
        System.out.printf("Beginning of Day: %s\n", beginningOfToday);
        LocalDate firstDayOfThisMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        System.out.printf("First Day of this Month: %s\n", firstDayOfThisMonth);
        firstDayOfThisMonth = LocalDate.now().withDayOfMonth(1);
        System.out.printf("First Day of this Month: %s\n", firstDayOfThisMonth);
        firstDayOfThisMonth = LocalDate.now().with(ChronoField.DAY_OF_MONTH, 1);
        System.out.printf("First Day of this Month: %s\n", firstDayOfThisMonth);
        System.out.println();
        System.out.println();
    }

    private static void localTime() {

        System.out.println("LocalTime:");

        //Instantiation
        final LocalTime now = LocalTime.now();
        final LocalTime of = LocalTime.of(6, 30, 0);
        final LocalTime parse = LocalTime.parse("06:30");

        System.out.println("\n> Instantiation:");
        System.out.printf("Now: %s\nOf: %s\nParse: %s\n", now, of, parse);

        //Common operations - for every "plus", there is a "minus"
        System.out.println("\n> Common operations");
        LocalTime plus1Hr = now.plus(Duration.of(1, ChronoUnit.HOURS));
        System.out.printf("Add 1 hour to now(): %s", plus1Hr);
        System.out.println();

        //Other operations
        final int hour = plus1Hr.getHour();
        final int mins = plus1Hr.getMinute();
        final int sec = plus1Hr.getSecond();
        final int nano = plus1Hr.getNano();
        final int secondOfDay = plus1Hr.toSecondOfDay();
        final boolean isBefore = parse.isBefore(of);
        final boolean isAfter = parse.isAfter(of);
        final LocalTime max = LocalTime.MAX;
        final LocalTime min = LocalTime.MIN;

        System.out.println("\n> Other operations:");
        System.out.printf("Hour: %s\nMinute: %s\nSecond: %s\nNanosecond: %s\nSecond of Day: %s\nisBefore: %s\nisAfter: %s\nMax: %s\nMin: %s\n\n",
                hour, mins, sec, nano, secondOfDay, isBefore, isAfter, max, min);
    }

    private static void localDateTime() {
        System.out.println("LocalDateTime:");

        //Instantiation
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime of1 = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        final LocalDateTime of2 = LocalDateTime.of(2022, 2, 2, 6, 30);
        final LocalDateTime of3 = LocalDateTime.of(2022, Month.FEBRUARY, 2, 6, 30, 58, 12345);
        final LocalDateTime of4 = LocalDateTime.of(2022, Month.FEBRUARY, 2, 6, 30);
        final LocalDateTime parse = LocalDateTime.parse("2022-02-02T06:30:00");

        System.out.println("\n> Instantiation:");
        System.out.printf("Now: %s\nof_1: %s\nof_2: %s\nof_3: %s\nof_4: %s\nparse: %s\n", now, of1, of2, of3, of4, parse);

        //Common operations - for every "plus", there is a "minus"
        final LocalDateTime sameTimeTomorrow = now.plus(Period.ofDays(1));
        final LocalDateTime anHrAgo = now.plus(Duration.ofHours(-1));

        System.out.println("\n> Common operations");
        System.out.printf("Same time tomorrow: %s\nAn Hour Ago: %s\n", sameTimeTomorrow, anHrAgo);
    }

    private static void zonedDateTime() {
        System.out.println("\n\nZonedDateTime:");

        System.out.printf("\nAvailable zones: [Size = %s],%s\nDefault/System zone: %s\n",
                ZoneId.getAvailableZoneIds().size(), ZoneId.getAvailableZoneIds(), ZoneId.systemDefault());

        //Instantiation
        final LocalDateTime nowInSystemTimeZone = LocalDateTime.now();
        final ZonedDateTime nowInParisTimeZone = ZonedDateTime.of(nowInSystemTimeZone, ZoneId.of("Europe/Paris")); //Add a time zone component to a date-time
        final ZonedDateTime nowInLagosTimeZone = ZonedDateTime.now(ZoneId.of("Africa/Lagos")); //Hour returned tallies with the hour of the local time in the zone
        final ZonedDateTime parse = ZonedDateTime.parse("2015-05-03T10:15:30+01:00[Europe/Paris]"); //Adjusts the time parsed to the time in the zone's local time
        System.out.printf("\nLocal Time: %s\nLocal Time in 'Paris' Time Zone: %s\nLagos Time Zone: %s\nParse: %s", nowInSystemTimeZone, nowInParisTimeZone, nowInLagosTimeZone, parse);

        System.out.println("\n\nOffsetDateTime:");
        final LocalDateTime localDateTime = LocalDateTime.of(2015, Month.FEBRUARY, 20, 6, 30);
        ZoneOffset offset = ZoneOffset.of("+02:00");
        OffsetDateTime offSetByTwo = OffsetDateTime.of(localDateTime, offset);
        System.out.printf("\nLocalDateTime: %s\nZone Offset: %s\nOffsetDateTime: %s\n", localDateTime, offset, offSetByTwo);
    }

    private static void period() {
        //For date management
        System.out.println("\n\nPeriod:");
        final LocalDate initialDate = LocalDate.now();
        final LocalDate finalDate = initialDate.plus(Period.ofDays(5));
        final Period periodDiff = Period.between(initialDate, finalDate);
        final long dayDiff = ChronoUnit.DAYS.between(initialDate, finalDate);
        System.out.printf("Same? %s\n", periodDiff.getDays() == 5);
        System.out.printf("Same? %s\n", dayDiff == 5);
    }

    private static void duration() {
        //For time management
        System.out.println("\n\nDuration:");
        final LocalTime initialTime = LocalTime.now();
        final LocalTime finalTime = initialTime.plus(Duration.of(5, ChronoUnit.SECONDS));
        final Duration diff = Duration.between(initialTime, finalTime);
        final long secDiff = ChronoUnit.SECONDS.between(initialTime, finalTime);
        System.out.printf("Same? %s\n", diff.getSeconds() == 5);
        System.out.printf("Same? %s\n", secDiff == 5);
    }

    private static void conversions() {
        final Date date = new Date();
        final Calendar cal = Calendar.getInstance();

        final LocalDateTime dateLdt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        final LocalDateTime calLdt = LocalDateTime.ofInstant(cal.toInstant(), ZoneId.systemDefault());
        final LocalDateTime epochLdt = LocalDateTime.ofEpochSecond(System.currentTimeMillis(), 0, ZoneOffset.UTC);

        System.out.println("\n\nConversions:");
        System.out.printf("From Date: %s\n", dateLdt);
        System.out.printf("From Calendar: %s\n", calLdt);
        System.out.printf("From Epoch: %s\n", epochLdt);
    }

    private static void dateTimeFormatting() {
        System.out.println("\n\nDateTime Formatting:");
        final LocalDateTime ldt = LocalDateTime.now();
        final String styleOne = ldt.format(DateTimeFormatter.ISO_DATE);
        final String styleTwo = ldt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        final String styleThree = ldt.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        final String styleFour = ldt.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
        final String styleFive = ldt.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
        final String styleSix = ldt.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.CANADA));

        System.out.println(styleOne);
        System.out.println(styleTwo);
        System.out.println(styleThree);
        System.out.println(styleFour);
        System.out.println(styleFive);
        System.out.println(styleSix);
    }

}
