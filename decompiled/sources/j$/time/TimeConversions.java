package j$.time;
/* loaded from: classes2.dex */
public class TimeConversions {
    public static Duration convert(java.time.Duration duration) {
        if (duration == null) {
            return null;
        }
        return Duration.ofSeconds(duration.getSeconds(), duration.getNano());
    }

    public static java.time.Duration convert(Duration duration) {
        if (duration == null) {
            return null;
        }
        return java.time.Duration.ofSeconds(duration.getSeconds(), duration.getNano());
    }
}
