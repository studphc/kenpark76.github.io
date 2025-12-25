package androidx.media3.exoplayer.text;

import androidx.media3.common.text.Cue;
import androidx.media3.common.util.Assertions;
import androidx.media3.extractor.text.CuesWithTiming;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
final class MergingCuesResolver implements CuesResolver {
    private static final Ordering<CuesWithTiming> CUES_DISPLAY_PRIORITY_COMPARATOR = Ordering.natural().onResultOf(new Function() { // from class: androidx.media3.exoplayer.text.MergingCuesResolver$$ExternalSyntheticLambda0
        @Override // com.google.common.base.Function
        public final Object apply(Object obj) {
            Long valueOf;
            valueOf = Long.valueOf(((CuesWithTiming) obj).startTimeUs);
            return valueOf;
        }
    }).compound(Ordering.natural().reverse().onResultOf(new Function() { // from class: androidx.media3.exoplayer.text.MergingCuesResolver$$ExternalSyntheticLambda1
        @Override // com.google.common.base.Function
        public final Object apply(Object obj) {
            Long valueOf;
            valueOf = Long.valueOf(((CuesWithTiming) obj).durationUs);
            return valueOf;
        }
    }));
    private final List<CuesWithTiming> cuesWithTimingList = new ArrayList();

    @Override // androidx.media3.exoplayer.text.CuesResolver
    public boolean addCues(CuesWithTiming cuesWithTiming, long j) {
        Assertions.checkArgument(cuesWithTiming.startTimeUs != -9223372036854775807L);
        Assertions.checkArgument(cuesWithTiming.durationUs != -9223372036854775807L);
        boolean z = cuesWithTiming.startTimeUs <= j && j < cuesWithTiming.endTimeUs;
        for (int size = this.cuesWithTimingList.size() - 1; size >= 0; size--) {
            if (cuesWithTiming.startTimeUs >= this.cuesWithTimingList.get(size).startTimeUs) {
                this.cuesWithTimingList.add(size + 1, cuesWithTiming);
                return z;
            }
        }
        this.cuesWithTimingList.add(0, cuesWithTiming);
        return z;
    }

    @Override // androidx.media3.exoplayer.text.CuesResolver
    public ImmutableList<Cue> getCuesAtTimeUs(long j) {
        if (!this.cuesWithTimingList.isEmpty()) {
            if (j >= this.cuesWithTimingList.get(0).startTimeUs) {
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < this.cuesWithTimingList.size(); i++) {
                    CuesWithTiming cuesWithTiming = this.cuesWithTimingList.get(i);
                    if (j >= cuesWithTiming.startTimeUs && j < cuesWithTiming.endTimeUs) {
                        arrayList.add(cuesWithTiming);
                    }
                    if (j < cuesWithTiming.startTimeUs) {
                        break;
                    }
                }
                ImmutableList sortedCopyOf = ImmutableList.sortedCopyOf(CUES_DISPLAY_PRIORITY_COMPARATOR, arrayList);
                ImmutableList.Builder builder = ImmutableList.builder();
                for (int i2 = 0; i2 < sortedCopyOf.size(); i2++) {
                    builder.addAll((Iterable) ((CuesWithTiming) sortedCopyOf.get(i2)).cues);
                }
                return builder.build();
            }
        }
        return ImmutableList.of();
    }

    @Override // androidx.media3.exoplayer.text.CuesResolver
    public void discardCuesBeforeTimeUs(long j) {
        int i = 0;
        while (i < this.cuesWithTimingList.size()) {
            int i2 = (j > this.cuesWithTimingList.get(i).startTimeUs ? 1 : (j == this.cuesWithTimingList.get(i).startTimeUs ? 0 : -1));
            if (i2 > 0 && j > this.cuesWithTimingList.get(i).endTimeUs) {
                this.cuesWithTimingList.remove(i);
                i--;
            } else if (i2 < 0) {
                return;
            }
            i++;
        }
    }

    @Override // androidx.media3.exoplayer.text.CuesResolver
    public long getPreviousCueChangeTimeUs(long j) {
        if (this.cuesWithTimingList.isEmpty()) {
            return -9223372036854775807L;
        }
        if (j < this.cuesWithTimingList.get(0).startTimeUs) {
            return -9223372036854775807L;
        }
        long j2 = this.cuesWithTimingList.get(0).startTimeUs;
        for (int i = 0; i < this.cuesWithTimingList.size(); i++) {
            long j3 = this.cuesWithTimingList.get(i).startTimeUs;
            long j4 = this.cuesWithTimingList.get(i).endTimeUs;
            if (j4 <= j) {
                j2 = Math.max(j2, j4);
            } else {
                if (j3 > j) {
                    break;
                }
                j2 = Math.max(j2, j3);
            }
        }
        return j2;
    }

    @Override // androidx.media3.exoplayer.text.CuesResolver
    public long getNextCueChangeTimeUs(long j) {
        int i = 0;
        long j2 = -9223372036854775807L;
        while (true) {
            if (i >= this.cuesWithTimingList.size()) {
                break;
            }
            long j3 = this.cuesWithTimingList.get(i).startTimeUs;
            long j4 = this.cuesWithTimingList.get(i).endTimeUs;
            if (j < j3) {
                j2 = j2 == -9223372036854775807L ? j3 : Math.min(j2, j3);
            } else {
                if (j < j4) {
                    j2 = j2 == -9223372036854775807L ? j4 : Math.min(j2, j4);
                }
                i++;
            }
        }
        if (j2 != -9223372036854775807L) {
            return j2;
        }
        return Long.MIN_VALUE;
    }

    @Override // androidx.media3.exoplayer.text.CuesResolver
    public void clear() {
        this.cuesWithTimingList.clear();
    }
}
