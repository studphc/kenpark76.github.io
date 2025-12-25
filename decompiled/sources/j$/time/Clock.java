package j$.time;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.ObjectInputStream;
import java.io.Serializable;
/* loaded from: classes2.dex */
public abstract class Clock {

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static final class SystemClock extends Clock implements Serializable {
        private static final long OFFSET_SEED = (System.currentTimeMillis() / 1000) - PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
        static final SystemClock UTC = new SystemClock(ZoneOffset.UTC);
        private static final long serialVersionUID = 6740630888130243051L;
        private final ZoneId zone;

        SystemClock(ZoneId zoneId) {
            this.zone = zoneId;
        }

        private void readObject(ObjectInputStream objectInputStream) {
            objectInputStream.defaultReadObject();
        }

        public boolean equals(Object obj) {
            if (obj instanceof SystemClock) {
                return this.zone.equals(((SystemClock) obj).zone);
            }
            return false;
        }

        public int hashCode() {
            return this.zone.hashCode() + 1;
        }

        @Override // j$.time.Clock
        public Instant instant() {
            return Instant.ofEpochMilli(millis());
        }

        @Override // j$.time.Clock
        public long millis() {
            return System.currentTimeMillis();
        }

        public String toString() {
            ZoneId zoneId = this.zone;
            return "SystemClock[" + zoneId + "]";
        }
    }

    protected Clock() {
    }

    public static Clock systemUTC() {
        return SystemClock.UTC;
    }

    public abstract Instant instant();

    public abstract long millis();
}
