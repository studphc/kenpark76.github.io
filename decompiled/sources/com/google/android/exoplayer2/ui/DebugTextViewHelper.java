package com.google.android.exoplayer2.ui;

import android.os.Looper;
import android.widget.TextView;
import androidx.core.os.EnvironmentCompat;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.util.Assertions;
import java.util.List;
import java.util.Locale;
/* loaded from: classes3.dex */
public class DebugTextViewHelper implements Player.EventListener, Runnable {
    private static final int REFRESH_INTERVAL_MS = 1000;
    private final SimpleExoPlayer player;
    private boolean started;
    private final TextView textView;

    @Override // com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onEvents(Player player, Player.Events events) {
        Player.EventListener.CC.$default$onEvents(this, player, events);
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onExperimentalOffloadSchedulingEnabledChanged(boolean z) {
        Player.EventListener.CC.$default$onExperimentalOffloadSchedulingEnabledChanged(this, z);
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onExperimentalSleepingForOffloadChanged(boolean z) {
        Player.EventListener.CC.$default$onExperimentalSleepingForOffloadChanged(this, z);
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onIsLoadingChanged(boolean z) {
        onLoadingChanged(z);
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onIsPlayingChanged(boolean z) {
        Player.EventListener.CC.$default$onIsPlayingChanged(this, z);
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onLoadingChanged(boolean z) {
        Player.EventListener.CC.$default$onLoadingChanged(this, z);
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onMediaItemTransition(MediaItem mediaItem, int i) {
        Player.EventListener.CC.$default$onMediaItemTransition(this, mediaItem, i);
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        Player.EventListener.CC.$default$onPlaybackParametersChanged(this, playbackParameters);
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onPlaybackSuppressionReasonChanged(int i) {
        Player.EventListener.CC.$default$onPlaybackSuppressionReasonChanged(this, i);
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onPlayerError(ExoPlaybackException exoPlaybackException) {
        Player.EventListener.CC.$default$onPlayerError(this, exoPlaybackException);
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onPlayerStateChanged(boolean z, int i) {
        Player.EventListener.CC.$default$onPlayerStateChanged(this, z, i);
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onRepeatModeChanged(int i) {
        Player.EventListener.CC.$default$onRepeatModeChanged(this, i);
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onSeekProcessed() {
        Player.EventListener.CC.$default$onSeekProcessed(this);
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onShuffleModeEnabledChanged(boolean z) {
        Player.EventListener.CC.$default$onShuffleModeEnabledChanged(this, z);
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onStaticMetadataChanged(List list) {
        Player.EventListener.CC.$default$onStaticMetadataChanged(this, list);
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onTimelineChanged(Timeline timeline, int i) {
        onTimelineChanged(timeline, r3.getWindowCount() == 1 ? timeline.getWindow(0, new Timeline.Window()).manifest : null, i);
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onTimelineChanged(Timeline timeline, Object obj, int i) {
        Player.EventListener.CC.$default$onTimelineChanged(this, timeline, obj, i);
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public /* synthetic */ void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
        Player.EventListener.CC.$default$onTracksChanged(this, trackGroupArray, trackSelectionArray);
    }

    public DebugTextViewHelper(SimpleExoPlayer simpleExoPlayer, TextView textView) {
        Assertions.checkArgument(simpleExoPlayer.getApplicationLooper() == Looper.getMainLooper());
        this.player = simpleExoPlayer;
        this.textView = textView;
    }

    public final void start() {
        if (this.started) {
            return;
        }
        this.started = true;
        this.player.addListener(this);
        updateAndPost();
    }

    public final void stop() {
        if (this.started) {
            this.started = false;
            this.player.removeListener(this);
            this.textView.removeCallbacks(this);
        }
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public final void onPlaybackStateChanged(int i) {
        updateAndPost();
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public final void onPlayWhenReadyChanged(boolean z, int i) {
        updateAndPost();
    }

    @Override // com.google.android.exoplayer2.Player.EventListener
    public final void onPositionDiscontinuity(int i) {
        updateAndPost();
    }

    @Override // java.lang.Runnable
    public final void run() {
        updateAndPost();
    }

    protected final void updateAndPost() {
        this.textView.setText(getDebugString());
        this.textView.removeCallbacks(this);
        this.textView.postDelayed(this, 1000L);
    }

    protected String getDebugString() {
        return getPlayerStateString() + getVideoString() + getAudioString();
    }

    protected String getPlayerStateString() {
        int playbackState = this.player.getPlaybackState();
        return String.format("playWhenReady:%s playbackState:%s window:%s", Boolean.valueOf(this.player.getPlayWhenReady()), playbackState != 1 ? playbackState != 2 ? playbackState != 3 ? playbackState != 4 ? EnvironmentCompat.MEDIA_UNKNOWN : "ended" : "ready" : "buffering" : "idle", Integer.valueOf(this.player.getCurrentWindowIndex()));
    }

    protected String getVideoString() {
        Format videoFormat = this.player.getVideoFormat();
        DecoderCounters videoDecoderCounters = this.player.getVideoDecoderCounters();
        if (videoFormat == null || videoDecoderCounters == null) {
            return "";
        }
        return "\n" + videoFormat.sampleMimeType + "(id:" + videoFormat.id + " r:" + videoFormat.width + "x" + videoFormat.height + getPixelAspectRatioString(videoFormat.pixelWidthHeightRatio) + getDecoderCountersBufferCountString(videoDecoderCounters) + " vfpo: " + getVideoFrameProcessingOffsetAverageString(videoDecoderCounters.totalVideoFrameProcessingOffsetUs, videoDecoderCounters.videoFrameProcessingOffsetCount) + ")";
    }

    protected String getAudioString() {
        Format audioFormat = this.player.getAudioFormat();
        DecoderCounters audioDecoderCounters = this.player.getAudioDecoderCounters();
        if (audioFormat == null || audioDecoderCounters == null) {
            return "";
        }
        return "\n" + audioFormat.sampleMimeType + "(id:" + audioFormat.id + " hz:" + audioFormat.sampleRate + " ch:" + audioFormat.channelCount + getDecoderCountersBufferCountString(audioDecoderCounters) + ")";
    }

    private static String getDecoderCountersBufferCountString(DecoderCounters decoderCounters) {
        if (decoderCounters == null) {
            return "";
        }
        decoderCounters.ensureUpdated();
        return " sib:" + decoderCounters.skippedInputBufferCount + " sb:" + decoderCounters.skippedOutputBufferCount + " rb:" + decoderCounters.renderedOutputBufferCount + " db:" + decoderCounters.droppedBufferCount + " mcdb:" + decoderCounters.maxConsecutiveDroppedBufferCount + " dk:" + decoderCounters.droppedToKeyframeCount;
    }

    private static String getPixelAspectRatioString(float f) {
        if (f == -1.0f || f == 1.0f) {
            return "";
        }
        return " par:" + String.format(Locale.US, "%.02f", Float.valueOf(f));
    }

    private static String getVideoFrameProcessingOffsetAverageString(long j, int i) {
        return i == 0 ? "N/A" : String.valueOf((long) (j / i));
    }
}
