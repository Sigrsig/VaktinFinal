package is.hi.hbv601.vaktin.Utilities;

import androidx.room.TypeConverter;

import java.time.LocalDate;

/***
 * Parses LocalDate to String and String to LocalDate
 * Needed for Room database
 */
public class LocalDateConverter {

    @TypeConverter
    public static LocalDate toDate(String dateString) {
        LocalDate d;
        if (dateString == null) {
            return null;
        }
        else {
            d = LocalDate.parse(dateString);
        }

        return d;
    }

    @TypeConverter
    public static String toDateString(LocalDate date) {
        if (date == null) {
            return null;
        }
        else {
            return date.toString();
        }
    }
}
