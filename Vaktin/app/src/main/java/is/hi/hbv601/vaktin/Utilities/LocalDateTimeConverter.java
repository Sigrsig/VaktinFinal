package is.hi.hbv601.vaktin.Utilities;

import androidx.room.TypeConverter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;


public class LocalDateTimeConverter {

    @TypeConverter
    public static LocalDateTime toDate(String dateString) {
        LocalDateTime d;
        if (dateString == null) {
            return null;
        }
        else {
            d = LocalDateTime.parse(dateString);
        }

        return d;
    }

    @TypeConverter
    public static String toDateString(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        else {
            return date.toString();
        }
    }


}
