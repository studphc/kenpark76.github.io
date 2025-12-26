package j$.time.format;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
/* loaded from: classes2.dex */
public abstract class DesugarDateTimeTextProviderHelper {
    private static String computeStandaloneMonthName(int i, String str, Locale locale) {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str, locale);
        simpleDateFormat.setTimeZone(timeZone);
        Calendar calendar = Calendar.getInstance();
        calendar.set(0, i, 0);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static void fillWithStandaloneStyleMap(Map map, DateFormatSymbols dateFormatSymbols, Locale locale) {
        int length = dateFormatSymbols.getMonths().length;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        LinkedHashMap linkedHashMap3 = new LinkedHashMap();
        for (int i = 1; i < length; i++) {
            String computeStandaloneMonthName = computeStandaloneMonthName(i, "LLLL", locale);
            long j = i;
            linkedHashMap.put(Long.valueOf(j), computeStandaloneMonthName);
            linkedHashMap2.put(Long.valueOf(j), firstCodePoint(computeStandaloneMonthName));
            linkedHashMap3.put(Long.valueOf(j), computeStandaloneMonthName(i, "LLL", locale));
        }
        if (length > 0) {
            long j2 = length;
            linkedHashMap.put(Long.valueOf(j2), "");
            linkedHashMap2.put(Long.valueOf(j2), "");
            linkedHashMap3.put(Long.valueOf(j2), "");
            map.put(TextStyle.FULL_STANDALONE, linkedHashMap);
            map.put(TextStyle.NARROW_STANDALONE, linkedHashMap2);
            map.put(TextStyle.SHORT_STANDALONE, linkedHashMap3);
        }
    }

    private static String firstCodePoint(String str) {
        return str.substring(0, Character.charCount(str.codePointAt(0)));
    }
}
