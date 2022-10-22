package playduin.newsfeed.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UserDateTime {
    public static String getUserDateTime(long time) {
        final DateFormat formatter = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return formatter.format(calendar.getTime());
    }
}
