package androidx.media3.extractor.text;

import androidx.media3.common.text.Cue;
import androidx.media3.common.util.Consumer;
import androidx.media3.extractor.text.SubtitleParser;
import java.util.List;
/* loaded from: classes.dex */
public class LegacySubtitleUtil {
    private LegacySubtitleUtil() {
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0045 A[LOOP:0: B:13:0x003f->B:15:0x0045, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void toCuesWithTiming(androidx.media3.extractor.text.Subtitle r12, androidx.media3.extractor.text.SubtitleParser.OutputOptions r13, androidx.media3.common.util.Consumer<androidx.media3.extractor.text.CuesWithTiming> r14) {
        /*
            int r0 = getStartIndex(r12, r13)
            long r1 = r13.startTimeUs
            r3 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r5 = 0
            int r6 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r6 == 0) goto L3d
            long r1 = r13.startTimeUs
            java.util.List r7 = r12.getCues(r1)
            long r1 = r12.getEventTime(r0)
            boolean r3 = r7.isEmpty()
            if (r3 != 0) goto L3d
            int r3 = r12.getEventTimeCount()
            if (r0 >= r3) goto L3d
            long r3 = r13.startTimeUs
            int r6 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r6 >= 0) goto L3d
            androidx.media3.extractor.text.CuesWithTiming r3 = new androidx.media3.extractor.text.CuesWithTiming
            long r8 = r13.startTimeUs
            long r10 = r13.startTimeUs
            long r10 = r1 - r10
            r6 = r3
            r6.<init>(r7, r8, r10)
            r14.accept(r3)
            r1 = 1
            goto L3e
        L3d:
            r1 = 0
        L3e:
            r2 = r0
        L3f:
            int r3 = r12.getEventTimeCount()
            if (r2 >= r3) goto L4b
            outputSubtitleEvent(r12, r2, r14)
            int r2 = r2 + 1
            goto L3f
        L4b:
            boolean r2 = r13.outputAllCues
            if (r2 == 0) goto L78
            if (r1 == 0) goto L53
            int r0 = r0 + (-1)
        L53:
            if (r5 >= r0) goto L5b
            outputSubtitleEvent(r12, r5, r14)
            int r5 = r5 + 1
            goto L53
        L5b:
            if (r1 == 0) goto L78
            androidx.media3.extractor.text.CuesWithTiming r1 = new androidx.media3.extractor.text.CuesWithTiming
            long r2 = r13.startTimeUs
            java.util.List r7 = r12.getCues(r2)
            long r8 = r12.getEventTime(r0)
            long r2 = r13.startTimeUs
            long r12 = r12.getEventTime(r0)
            long r10 = r2 - r12
            r6 = r1
            r6.<init>(r7, r8, r10)
            r14.accept(r1)
        L78:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.extractor.text.LegacySubtitleUtil.toCuesWithTiming(androidx.media3.extractor.text.Subtitle, androidx.media3.extractor.text.SubtitleParser$OutputOptions, androidx.media3.common.util.Consumer):void");
    }

    private static int getStartIndex(Subtitle subtitle, SubtitleParser.OutputOptions outputOptions) {
        if (outputOptions.startTimeUs == -9223372036854775807L) {
            return 0;
        }
        int nextEventTimeIndex = subtitle.getNextEventTimeIndex(outputOptions.startTimeUs);
        if (nextEventTimeIndex == -1) {
            return subtitle.getEventTimeCount();
        }
        return (nextEventTimeIndex <= 0 || subtitle.getEventTime(nextEventTimeIndex + (-1)) != outputOptions.startTimeUs) ? nextEventTimeIndex : nextEventTimeIndex - 1;
    }

    private static void outputSubtitleEvent(Subtitle subtitle, int i, Consumer<CuesWithTiming> consumer) {
        long eventTime = subtitle.getEventTime(i);
        List<Cue> cues = subtitle.getCues(eventTime);
        if (cues.isEmpty()) {
            return;
        }
        if (i == subtitle.getEventTimeCount() - 1) {
            throw new IllegalStateException();
        }
        long eventTime2 = subtitle.getEventTime(i + 1) - subtitle.getEventTime(i);
        if (eventTime2 > 0) {
            consumer.accept(new CuesWithTiming(cues, eventTime, eventTime2));
        }
    }
}
