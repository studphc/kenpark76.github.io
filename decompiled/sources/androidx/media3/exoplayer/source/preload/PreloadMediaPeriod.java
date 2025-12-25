package androidx.media3.exoplayer.source.preload;

import androidx.media3.common.util.Assertions;
import androidx.media3.exoplayer.LoadingInfo;
import androidx.media3.exoplayer.SeekParameters;
import androidx.media3.exoplayer.source.MediaPeriod;
import androidx.media3.exoplayer.source.SampleStream;
import androidx.media3.exoplayer.source.TrackGroupArray;
import androidx.media3.exoplayer.trackselection.ExoTrackSelection;
import j$.util.Objects;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class PreloadMediaPeriod implements MediaPeriod {
    private MediaPeriod.Callback callback;
    public final MediaPeriod mediaPeriod;
    private PreloadTrackSelectionHolder preloadTrackSelectionHolder;
    private boolean prepareInternalCalled;
    private boolean prepared;

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public /* synthetic */ List getStreamKeys(List list) {
        List emptyList;
        emptyList = Collections.emptyList();
        return emptyList;
    }

    public PreloadMediaPeriod(MediaPeriod mediaPeriod) {
        this.mediaPeriod = mediaPeriod;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void preload(MediaPeriod.Callback callback, long j) {
        this.callback = callback;
        if (this.prepared) {
            callback.onPrepared(this);
        }
        if (this.prepareInternalCalled) {
            return;
        }
        prepareInternal(j);
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public void prepare(MediaPeriod.Callback callback, long j) {
        this.callback = callback;
        if (this.prepared) {
            callback.onPrepared(this);
        } else if (this.prepareInternalCalled) {
        } else {
            prepareInternal(j);
        }
    }

    private void prepareInternal(long j) {
        this.prepareInternalCalled = true;
        this.mediaPeriod.prepare(new MediaPeriod.Callback() { // from class: androidx.media3.exoplayer.source.preload.PreloadMediaPeriod.1
            @Override // androidx.media3.exoplayer.source.SequenceableLoader.Callback
            public void onContinueLoadingRequested(MediaPeriod mediaPeriod) {
                ((MediaPeriod.Callback) Assertions.checkNotNull(PreloadMediaPeriod.this.callback)).onContinueLoadingRequested(PreloadMediaPeriod.this);
            }

            @Override // androidx.media3.exoplayer.source.MediaPeriod.Callback
            public void onPrepared(MediaPeriod mediaPeriod) {
                PreloadMediaPeriod.this.prepared = true;
                ((MediaPeriod.Callback) Assertions.checkNotNull(PreloadMediaPeriod.this.callback)).onPrepared(PreloadMediaPeriod.this);
            }
        }, j);
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public void maybeThrowPrepareError() throws IOException {
        this.mediaPeriod.maybeThrowPrepareError();
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public TrackGroupArray getTrackGroups() {
        return this.mediaPeriod.getTrackGroups();
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public long selectTracks(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j) {
        return selectTracksInternal(exoTrackSelectionArr, zArr, sampleStreamArr, zArr2, j);
    }

    private long selectTracksInternal(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j) {
        PreloadTrackSelectionHolder preloadTrackSelectionHolder = this.preloadTrackSelectionHolder;
        if (preloadTrackSelectionHolder == null) {
            return this.mediaPeriod.selectTracks(exoTrackSelectionArr, zArr, sampleStreamArr, zArr2, j);
        }
        Assertions.checkState(sampleStreamArr.length == preloadTrackSelectionHolder.streams.length);
        if (j != this.preloadTrackSelectionHolder.trackSelectionPositionUs) {
            for (int i = 0; i < this.preloadTrackSelectionHolder.streams.length; i++) {
                if (this.preloadTrackSelectionHolder.streams[i] != null) {
                    sampleStreamArr[i] = this.preloadTrackSelectionHolder.streams[i];
                    zArr[i] = false;
                }
            }
            this.preloadTrackSelectionHolder = null;
            return this.mediaPeriod.selectTracks(exoTrackSelectionArr, zArr, sampleStreamArr, zArr2, j);
        }
        PreloadTrackSelectionHolder preloadTrackSelectionHolder2 = (PreloadTrackSelectionHolder) Assertions.checkNotNull(this.preloadTrackSelectionHolder);
        long j2 = preloadTrackSelectionHolder2.trackSelectionPositionUs;
        boolean[] zArr3 = preloadTrackSelectionHolder2.streamResetFlags;
        if (maybeUpdatePreloadTrackSelectionHolderForReselection(exoTrackSelectionArr, preloadTrackSelectionHolder2)) {
            zArr3 = new boolean[zArr3.length];
            j2 = this.mediaPeriod.selectTracks(preloadTrackSelectionHolder2.selections, preloadTrackSelectionHolder2.mayRetainStreamFlags, preloadTrackSelectionHolder2.streams, zArr3, preloadTrackSelectionHolder2.trackSelectionPositionUs);
            for (int i2 = 0; i2 < preloadTrackSelectionHolder2.mayRetainStreamFlags.length; i2++) {
                if (preloadTrackSelectionHolder2.mayRetainStreamFlags[i2]) {
                    zArr3[i2] = true;
                }
            }
        }
        System.arraycopy(preloadTrackSelectionHolder2.streams, 0, sampleStreamArr, 0, preloadTrackSelectionHolder2.streams.length);
        System.arraycopy(zArr3, 0, zArr2, 0, zArr3.length);
        this.preloadTrackSelectionHolder = null;
        return j2;
    }

    private static boolean maybeUpdatePreloadTrackSelectionHolderForReselection(ExoTrackSelection[] exoTrackSelectionArr, PreloadTrackSelectionHolder preloadTrackSelectionHolder) {
        ExoTrackSelection[] exoTrackSelectionArr2 = ((PreloadTrackSelectionHolder) Assertions.checkNotNull(preloadTrackSelectionHolder)).selections;
        boolean z = false;
        for (int i = 0; i < exoTrackSelectionArr.length; i++) {
            ExoTrackSelection exoTrackSelection = exoTrackSelectionArr[i];
            ExoTrackSelection exoTrackSelection2 = exoTrackSelectionArr2[i];
            if (exoTrackSelection != null || exoTrackSelection2 != null) {
                preloadTrackSelectionHolder.mayRetainStreamFlags[i] = false;
                if (exoTrackSelection == null) {
                    preloadTrackSelectionHolder.selections[i] = null;
                } else if (exoTrackSelection2 == null) {
                    preloadTrackSelectionHolder.selections[i] = exoTrackSelection;
                } else if (!isSameAdaptionSet(exoTrackSelection, exoTrackSelection2)) {
                    preloadTrackSelectionHolder.selections[i] = exoTrackSelection;
                } else if (exoTrackSelection.getTrackGroup().type == 2 || exoTrackSelection.getTrackGroup().type == 1 || exoTrackSelection.getSelectedIndexInTrackGroup() == exoTrackSelection2.getSelectedIndexInTrackGroup()) {
                    preloadTrackSelectionHolder.mayRetainStreamFlags[i] = true;
                } else {
                    preloadTrackSelectionHolder.selections[i] = exoTrackSelection;
                }
                z = true;
            }
        }
        return z;
    }

    private static boolean isSameAdaptionSet(ExoTrackSelection exoTrackSelection, ExoTrackSelection exoTrackSelection2) {
        if (Objects.equals(exoTrackSelection.getTrackGroup(), exoTrackSelection2.getTrackGroup()) && exoTrackSelection.length() == exoTrackSelection2.length()) {
            for (int i = 0; i < exoTrackSelection.length(); i++) {
                if (exoTrackSelection.getIndexInTrackGroup(i) != exoTrackSelection2.getIndexInTrackGroup(i)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public long selectTracksForPreloading(ExoTrackSelection[] exoTrackSelectionArr, long j) {
        SampleStream[] sampleStreamArr = new SampleStream[exoTrackSelectionArr.length];
        boolean[] zArr = new boolean[exoTrackSelectionArr.length];
        boolean[] zArr2 = new boolean[exoTrackSelectionArr.length];
        long selectTracksInternal = selectTracksInternal(exoTrackSelectionArr, zArr2, sampleStreamArr, zArr, j);
        this.preloadTrackSelectionHolder = new PreloadTrackSelectionHolder(exoTrackSelectionArr, zArr2, sampleStreamArr, zArr, selectTracksInternal);
        return selectTracksInternal;
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public void discardBuffer(long j, boolean z) {
        this.mediaPeriod.discardBuffer(j, z);
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public long readDiscontinuity() {
        return this.mediaPeriod.readDiscontinuity();
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public long seekToUs(long j) {
        return this.mediaPeriod.seekToUs(j);
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod
    public long getAdjustedSeekPositionUs(long j, SeekParameters seekParameters) {
        return this.mediaPeriod.getAdjustedSeekPositionUs(j, seekParameters);
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod, androidx.media3.exoplayer.source.SequenceableLoader
    public long getBufferedPositionUs() {
        return this.mediaPeriod.getBufferedPositionUs();
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod, androidx.media3.exoplayer.source.SequenceableLoader
    public long getNextLoadPositionUs() {
        return this.mediaPeriod.getNextLoadPositionUs();
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod, androidx.media3.exoplayer.source.SequenceableLoader
    public boolean continueLoading(LoadingInfo loadingInfo) {
        return this.mediaPeriod.continueLoading(loadingInfo);
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod, androidx.media3.exoplayer.source.SequenceableLoader
    public boolean isLoading() {
        return this.mediaPeriod.isLoading();
    }

    @Override // androidx.media3.exoplayer.source.MediaPeriod, androidx.media3.exoplayer.source.SequenceableLoader
    public void reevaluateBuffer(long j) {
        this.mediaPeriod.reevaluateBuffer(j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class PreloadTrackSelectionHolder {
        public final boolean[] mayRetainStreamFlags;
        public final ExoTrackSelection[] selections;
        public final boolean[] streamResetFlags;
        public final SampleStream[] streams;
        public final long trackSelectionPositionUs;

        public PreloadTrackSelectionHolder(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j) {
            this.selections = exoTrackSelectionArr;
            this.mayRetainStreamFlags = zArr;
            this.streams = sampleStreamArr;
            this.streamResetFlags = zArr2;
            this.trackSelectionPositionUs = j;
        }
    }
}
