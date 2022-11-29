package kg.kundoluk.school.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Locale;

public class TimeHelper  {

    public static final String DATE_TIME_PIN_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSSSS";

    public static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm";
    public static final String TIME_FORMAT = "HH:mm";
    public static final String EXAM_TIME_FORMAT = "HH:mm:ss";
    public static final String DATE_TIME_SS_FORMAT = "dd.MM.yyyy HH:mm:ss";
    public static final String DATE_FORMAT = "dd.MM.yyyy";
    public static final String DATE_REVERSE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_REVERSE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_SCHEDULE_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String DATE_EXCEL_FORMAT = "dd/MM/yyyy";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);
    public static final DateTimeFormatter EXAM_TIME_FORMATTER = DateTimeFormatter.ofPattern(EXAM_TIME_FORMAT);
    public static final DateTimeFormatter DATE_TIME_SS_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_SS_FORMAT);
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    public static final DateTimeFormatter DATE_REVERSE_FORMATTER = DateTimeFormatter.ofPattern(DATE_REVERSE_FORMAT);
    public static final DateTimeFormatter DATE_TIME_REVERSE_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_REVERSE_FORMAT);
    public static final DateTimeFormatter DATE_TIME_SCHEDULE_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_SCHEDULE_FORMAT);
    public static final DateTimeFormatter DATE_TIME_PIN_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PIN_FORMAT);
    public static final DateTimeFormatter DATE_EXCEL_FORMATTER = DateTimeFormatter.ofPattern(DATE_EXCEL_FORMAT);

    public static LocalDate getDateOfBirth(String dateOfBirth){
        LocalDate dob = LocalDate.of(1980,1,1);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US).withResolverStyle(ResolverStyle.STRICT);
        DateValidator validator = new DateTimeValidator(dateFormatter);
        if (!StringUtils.isEmpty(dateOfBirth) && validator.isValid(dateOfBirth))
            dob = LocalDate.parse(dateOfBirth, TimeHelper.DATE_REVERSE_FORMATTER);

        return dob;
    }
}
