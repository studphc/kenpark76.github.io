package com.tvhome.app;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.FrameLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.media3.common.AudioAttributes;
import androidx.media3.common.DeviceInfo;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MediaMetadata;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.PlaybackParameters;
import androidx.media3.common.Player;
import androidx.media3.common.Timeline;
import androidx.media3.common.TrackSelectionParameters;
import androidx.media3.common.Tracks;
import androidx.media3.common.VideoSize;
import androidx.media3.common.text.CueGroup;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DataSpec;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.datasource.TransferListener;
import androidx.media3.exoplayer.DefaultRenderersFactory;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.mediacodec.MediaCodecInfo;
import androidx.media3.exoplayer.mediacodec.MediaCodecSelector;
import androidx.media3.exoplayer.mediacodec.MediaCodecUtil;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.ui.PlayerView;
import com.tvhome.app.PlayerFragment;
import com.tvhome.app.databinding.PlayerBinding;
import com.tvhome.app.models.TVModel;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: PlayerFragment.kt */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 #2\u00020\u0001:\u0002#$B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J$\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016J\b\u0010\u001d\u001a\u00020\u0011H\u0016J\b\u0010\u001e\u001a\u00020\u0011H\u0016J\b\u0010\u001f\u001a\u00020\u0011H\u0016J\b\u0010 \u001a\u00020\u0011H\u0016J\b\u0010!\u001a\u00020\u0011H\u0016J\u0010\u0010\"\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000fH\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lcom/pjy/koreatv/PlayerFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/pjy/koreatv/databinding/PlayerBinding;", "aspectRatio", "", "binding", "getBinding", "()Lcom/pjy/koreatv/databinding/PlayerBinding;", "mainActivity", "Lcom/pjy/koreatv/MainActivity;", "player", "Landroidx/media3/exoplayer/ExoPlayer;", "tvModel", "Lcom/pjy/koreatv/models/TVModel;", "onActivityCreated", "", "savedInstanceState", "Landroid/os/Bundle;", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onDestroy", "onDestroyView", "onPause", "onResume", "onStart", "play", "Companion", "PlayerMediaCodecSelector", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class PlayerFragment extends Fragment {
    public static final Companion Companion = new Companion(null);
    private static final String TAG = "PlayerFragment";
    private PlayerBinding _binding;
    private final float aspectRatio = 1.7777778f;
    private MainActivity mainActivity;
    private ExoPlayer player;
    private TVModel tvModel;

    private final PlayerBinding getBinding() {
        PlayerBinding playerBinding = this._binding;
        Intrinsics.checkNotNull(playerBinding);
        return playerBinding;
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        FragmentActivity activity = getActivity();
        Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type com.pjy.koreatv.MainActivity");
        this.mainActivity = (MainActivity) activity;
        super.onActivityCreated(bundle);
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        int width;
        int height;
        WindowMetrics windowMetrics;
        Rect bounds;
        Rect bounds2;
        Object systemService;
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        if (Build.VERSION.SDK_INT >= 30) {
            Context context = getContext();
            Integer num = null;
            if (context != null) {
                systemService = context.getSystemService(WindowManager.class);
                WindowManager windowManager = (WindowManager) systemService;
                if (windowManager != null) {
                    windowMetrics = windowManager.getCurrentWindowMetrics();
                    if (windowMetrics != null && bounds2 != null) {
                        num = Integer.valueOf(bounds2.width());
                    }
                    Intrinsics.checkNotNull(num);
                    width = num.intValue();
                    bounds = windowMetrics.getBounds();
                    height = bounds.height();
                }
            }
            windowMetrics = null;
            if (windowMetrics != null) {
                bounds2 = windowMetrics.getBounds();
                num = Integer.valueOf(bounds2.width());
            }
            Intrinsics.checkNotNull(num);
            width = num.intValue();
            bounds = windowMetrics.getBounds();
            height = bounds.height();
        } else {
            Rect rect = new Rect();
            Context context2 = getContext();
            Intrinsics.checkNotNull(context2, "null cannot be cast to non-null type android.app.Activity");
            ((Activity) context2).getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            width = rect.width();
            height = rect.height();
        }
        float f = this.aspectRatio;
        if (width / height > f) {
            width = (int) (height * f);
        } else {
            height = (int) (width / f);
        }
        PlayerBinding playerBinding = this._binding;
        Intrinsics.checkNotNull(playerBinding);
        PlayerView playerView = playerBinding.playerView;
        Intrinsics.checkNotNullExpressionValue(playerView, "playerView");
        ViewGroup.LayoutParams layoutParams = playerView.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.height = height;
        }
        if (layoutParams != null) {
            layoutParams.width = width;
        }
        playerView.setLayoutParams(layoutParams);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        PlayerBinding inflate = PlayerBinding.inflate(inflater, viewGroup, false);
        this._binding = inflate;
        Intrinsics.checkNotNull(inflate);
        final PlayerView playerView = inflate.playerView;
        Intrinsics.checkNotNullExpressionValue(playerView, "playerView");
        ViewTreeObserver viewTreeObserver = playerView.getViewTreeObserver();
        if (viewTreeObserver != null) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.pjy.koreatv.PlayerFragment$onCreateView$1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    ExoPlayer exoPlayer;
                    ExoPlayer exoPlayer2;
                    ExoPlayer exoPlayer3;
                    ExoPlayer exoPlayer4;
                    PlayerView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    Context context = this.getContext();
                    ExoPlayer exoPlayer5 = null;
                    DefaultRenderersFactory defaultRenderersFactory = context != null ? new DefaultRenderersFactory(context) : null;
                    PlayerFragment.PlayerMediaCodecSelector playerMediaCodecSelector = new PlayerFragment.PlayerMediaCodecSelector();
                    if (defaultRenderersFactory != null) {
                        defaultRenderersFactory.setMediaCodecSelector(playerMediaCodecSelector);
                    }
                    if (defaultRenderersFactory != null) {
                        defaultRenderersFactory.setExtensionRendererMode(1);
                    }
                    PlayerFragment playerFragment = this;
                    Context context2 = playerFragment.getContext();
                    if (context2 != null) {
                        ExoPlayer.Builder builder = new ExoPlayer.Builder(context2);
                        Intrinsics.checkNotNull(defaultRenderersFactory);
                        exoPlayer5 = builder.setRenderersFactory(defaultRenderersFactory).build();
                    }
                    playerFragment.player = exoPlayer5;
                    PlayerView playerView2 = PlayerView.this;
                    exoPlayer = this.player;
                    playerView2.setPlayer(exoPlayer);
                    exoPlayer2 = this.player;
                    if (exoPlayer2 != null) {
                        exoPlayer2.setRepeatMode(2);
                    }
                    exoPlayer3 = this.player;
                    if (exoPlayer3 != null) {
                        exoPlayer3.setPlayWhenReady(true);
                    }
                    exoPlayer4 = this.player;
                    if (exoPlayer4 != null) {
                        final PlayerView playerView3 = PlayerView.this;
                        final PlayerFragment playerFragment2 = this;
                        exoPlayer4.addListener(new Player.Listener() { // from class: com.pjy.koreatv.PlayerFragment$onCreateView$1$onGlobalLayout$2
                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onAudioAttributesChanged(AudioAttributes audioAttributes) {
                                Player.Listener.CC.$default$onAudioAttributesChanged(this, audioAttributes);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onAudioSessionIdChanged(int i) {
                                Player.Listener.CC.$default$onAudioSessionIdChanged(this, i);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onAvailableCommandsChanged(Player.Commands commands) {
                                Player.Listener.CC.$default$onAvailableCommandsChanged(this, commands);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onCues(CueGroup cueGroup) {
                                Player.Listener.CC.$default$onCues(this, cueGroup);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onCues(List list) {
                                Player.Listener.CC.$default$onCues(this, list);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onDeviceInfoChanged(DeviceInfo deviceInfo) {
                                Player.Listener.CC.$default$onDeviceInfoChanged(this, deviceInfo);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onDeviceVolumeChanged(int i, boolean z) {
                                Player.Listener.CC.$default$onDeviceVolumeChanged(this, i, z);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onEvents(Player player, Player.Events events) {
                                Player.Listener.CC.$default$onEvents(this, player, events);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onIsLoadingChanged(boolean z) {
                                Player.Listener.CC.$default$onIsLoadingChanged(this, z);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onLoadingChanged(boolean z) {
                                Player.Listener.CC.$default$onLoadingChanged(this, z);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onMaxSeekToPreviousPositionChanged(long j) {
                                Player.Listener.CC.$default$onMaxSeekToPreviousPositionChanged(this, j);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onMediaItemTransition(MediaItem mediaItem, int i) {
                                Player.Listener.CC.$default$onMediaItemTransition(this, mediaItem, i);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onMediaMetadataChanged(MediaMetadata mediaMetadata) {
                                Player.Listener.CC.$default$onMediaMetadataChanged(this, mediaMetadata);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onMetadata(androidx.media3.common.Metadata metadata) {
                                Player.Listener.CC.$default$onMetadata(this, metadata);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onPlayWhenReadyChanged(boolean z, int i) {
                                Player.Listener.CC.$default$onPlayWhenReadyChanged(this, z, i);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
                                Player.Listener.CC.$default$onPlaybackParametersChanged(this, playbackParameters);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onPlaybackSuppressionReasonChanged(int i) {
                                Player.Listener.CC.$default$onPlaybackSuppressionReasonChanged(this, i);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onPlayerErrorChanged(PlaybackException playbackException) {
                                Player.Listener.CC.$default$onPlayerErrorChanged(this, playbackException);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onPlayerStateChanged(boolean z, int i) {
                                Player.Listener.CC.$default$onPlayerStateChanged(this, z, i);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onPlaylistMetadataChanged(MediaMetadata mediaMetadata) {
                                Player.Listener.CC.$default$onPlaylistMetadataChanged(this, mediaMetadata);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onPositionDiscontinuity(int i) {
                                Player.Listener.CC.$default$onPositionDiscontinuity(this, i);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onRenderedFirstFrame() {
                                Player.Listener.CC.$default$onRenderedFirstFrame(this);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onRepeatModeChanged(int i) {
                                Player.Listener.CC.$default$onRepeatModeChanged(this, i);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onSeekBackIncrementChanged(long j) {
                                Player.Listener.CC.$default$onSeekBackIncrementChanged(this, j);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onSeekForwardIncrementChanged(long j) {
                                Player.Listener.CC.$default$onSeekForwardIncrementChanged(this, j);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onShuffleModeEnabledChanged(boolean z) {
                                Player.Listener.CC.$default$onShuffleModeEnabledChanged(this, z);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onSkipSilenceEnabledChanged(boolean z) {
                                Player.Listener.CC.$default$onSkipSilenceEnabledChanged(this, z);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onSurfaceSizeChanged(int i, int i2) {
                                Player.Listener.CC.$default$onSurfaceSizeChanged(this, i, i2);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onTimelineChanged(Timeline timeline, int i) {
                                Player.Listener.CC.$default$onTimelineChanged(this, timeline, i);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onTrackSelectionParametersChanged(TrackSelectionParameters trackSelectionParameters) {
                                Player.Listener.CC.$default$onTrackSelectionParametersChanged(this, trackSelectionParameters);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onTracksChanged(Tracks tracks) {
                                Player.Listener.CC.$default$onTracksChanged(this, tracks);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public /* synthetic */ void onVolumeChanged(float f) {
                                Player.Listener.CC.$default$onVolumeChanged(this, f);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public void onVideoSizeChanged(VideoSize videoSize) {
                                float f;
                                float f2;
                                float f3;
                                float f4;
                                Intrinsics.checkNotNullParameter(videoSize, "videoSize");
                                int measuredWidth = PlayerView.this.getMeasuredWidth() / PlayerView.this.getMeasuredHeight();
                                ViewGroup.LayoutParams layoutParams = PlayerView.this.getLayoutParams();
                                float f5 = measuredWidth;
                                f = playerFragment2.aspectRatio;
                                if (f5 >= f) {
                                    f2 = playerFragment2.aspectRatio;
                                    if (f5 > f2) {
                                        if (layoutParams != null) {
                                            f3 = playerFragment2.aspectRatio;
                                            layoutParams.width = (int) (PlayerView.this.getMeasuredHeight() * f3);
                                        }
                                        PlayerView.this.setLayoutParams(layoutParams);
                                        return;
                                    }
                                    return;
                                }
                                if (layoutParams != null) {
                                    f4 = playerFragment2.aspectRatio;
                                    layoutParams.height = (int) (PlayerView.this.getMeasuredWidth() / f4);
                                }
                                PlayerView.this.setLayoutParams(layoutParams);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public void onIsPlayingChanged(boolean z) {
                                TVModel tVModel;
                                TVModel tVModel2;
                                TVModel tVModel3;
                                Player.Listener.CC.$default$onIsPlayingChanged(this, z);
                                if (z) {
                                    tVModel = playerFragment2.tvModel;
                                    if (tVModel != null) {
                                        tVModel.confirmSourceType();
                                    }
                                    tVModel2 = playerFragment2.tvModel;
                                    if (tVModel2 != null) {
                                        tVModel2.setErrInfo("");
                                    }
                                    tVModel3 = playerFragment2.tvModel;
                                    Intrinsics.checkNotNull(tVModel3);
                                    tVModel3.setRetryTimes(0);
                                }
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public void onPlaybackStateChanged(int i) {
                                Player.Listener.CC.$default$onPlaybackStateChanged(this, i);
                            }

                            @Override // androidx.media3.common.Player.Listener
                            public void onPositionDiscontinuity(Player.PositionInfo oldPosition, Player.PositionInfo newPosition, int i) {
                                MainActivity mainActivity;
                                Intrinsics.checkNotNullParameter(oldPosition, "oldPosition");
                                Intrinsics.checkNotNullParameter(newPosition, "newPosition");
                                if (i == 0) {
                                    mainActivity = playerFragment2.mainActivity;
                                    if (mainActivity == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("mainActivity");
                                        mainActivity = null;
                                    }
                                    mainActivity.onPlayEnd();
                                }
                                Player.Listener.CC.$default$onPositionDiscontinuity(this, oldPosition, newPosition, i);
                            }

                            /* JADX WARN: Code restructure failed: missing block: B:11:0x0076, code lost:
                                r5 = r2.tvModel;
                             */
                            @Override // androidx.media3.common.Player.Listener
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct add '--show-bad-code' argument
                            */
                            public void onPlayerError(androidx.media3.common.PlaybackException r5) {
                                /*
                                    r4 = this;
                                    java.lang.String r0 = "error"
                                    kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r0)
                                    androidx.media3.common.Player.Listener.CC.$default$onPlayerError(r4, r5)
                                    java.lang.StringBuilder r0 = new java.lang.StringBuilder
                                    java.lang.String r1 = "Play Error "
                                    r0.<init>(r1)
                                    int r1 = r5.errorCode
                                    r0.append(r1)
                                    java.lang.String r1 = "||| "
                                    r0.append(r1)
                                    java.lang.String r2 = r5.getErrorCodeName()
                                    r0.append(r2)
                                    r0.append(r1)
                                    java.lang.String r2 = r5.getMessage()
                                    r0.append(r2)
                                    r0.append(r1)
                                    r0.append(r5)
                                    java.lang.String r0 = r0.toString()
                                    java.lang.String r1 = "PlayerFragment"
                                    android.util.Log.i(r1, r0)
                                    com.pjy.koreatv.PlayerFragment r0 = r2
                                    com.pjy.koreatv.models.TVModel r0 = com.pjy.koreatv.PlayerFragment.access$getTvModel$p(r0)
                                    if (r0 == 0) goto L64
                                    java.lang.StringBuilder r1 = new java.lang.StringBuilder
                                    r1.<init>()
                                    com.pjy.koreatv.PlayerFragment r2 = r2
                                    int r3 = com.pjy.koreatv.R.string.play_error
                                    java.lang.String r2 = r2.getString(r3)
                                    r1.append(r2)
                                    r2 = 58
                                    r1.append(r2)
                                    java.lang.String r5 = r5.getErrorCodeName()
                                    r1.append(r5)
                                    java.lang.String r5 = r1.toString()
                                    r0.setErrInfo(r5)
                                L64:
                                    com.pjy.koreatv.PlayerFragment r5 = r2
                                    com.pjy.koreatv.models.TVModel r5 = com.pjy.koreatv.PlayerFragment.access$getTvModel$p(r5)
                                    if (r5 == 0) goto L71
                                    com.pjy.koreatv.models.SourceType r5 = r5.getSourceType()
                                    goto L72
                                L71:
                                    r5 = 0
                                L72:
                                    com.pjy.koreatv.models.SourceType r0 = com.pjy.koreatv.models.SourceType.UNKNOWN
                                    if (r5 != r0) goto L81
                                    com.pjy.koreatv.PlayerFragment r5 = r2
                                    com.pjy.koreatv.models.TVModel r5 = com.pjy.koreatv.PlayerFragment.access$getTvModel$p(r5)
                                    if (r5 == 0) goto L81
                                    r5.nextSource()
                                L81:
                                    com.pjy.koreatv.PlayerFragment r5 = r2
                                    com.pjy.koreatv.models.TVModel r5 = com.pjy.koreatv.PlayerFragment.access$getTvModel$p(r5)
                                    kotlin.jvm.internal.Intrinsics.checkNotNull(r5)
                                    int r5 = r5.getRetryTimes()
                                    com.pjy.koreatv.PlayerFragment r0 = r2
                                    com.pjy.koreatv.models.TVModel r0 = com.pjy.koreatv.PlayerFragment.access$getTvModel$p(r0)
                                    kotlin.jvm.internal.Intrinsics.checkNotNull(r0)
                                    int r0 = r0.getRetryMaxTimes()
                                    if (r5 >= r0) goto Lba
                                    com.pjy.koreatv.PlayerFragment r5 = r2
                                    com.pjy.koreatv.models.TVModel r5 = com.pjy.koreatv.PlayerFragment.access$getTvModel$p(r5)
                                    if (r5 == 0) goto La8
                                    r5.setReady()
                                La8:
                                    com.pjy.koreatv.PlayerFragment r5 = r2
                                    com.pjy.koreatv.models.TVModel r5 = com.pjy.koreatv.PlayerFragment.access$getTvModel$p(r5)
                                    kotlin.jvm.internal.Intrinsics.checkNotNull(r5)
                                    int r0 = r5.getRetryTimes()
                                    int r0 = r0 + 1
                                    r5.setRetryTimes(r0)
                                Lba:
                                    return
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.pjy.koreatv.PlayerFragment$onCreateView$1$onGlobalLayout$2.onPlayerError(androidx.media3.common.PlaybackException):void");
                            }
                        });
                    }
                    FragmentActivity activity = this.getActivity();
                    Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type com.pjy.koreatv.MainActivity");
                    ((MainActivity) activity).ready("PlayerFragment");
                    Log.i("PlayerFragment", "player ready");
                }
            });
        }
        PlayerBinding playerBinding = this._binding;
        Intrinsics.checkNotNull(playerBinding);
        FrameLayout root = playerBinding.getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "getRoot(...)");
        return root;
    }

    public final void play(TVModel tvModel) {
        Intrinsics.checkNotNullParameter(tvModel, "tvModel");
        this.tvModel = tvModel;
        ExoPlayer exoPlayer = this.player;
        if (exoPlayer != null) {
            IgnoreSSLCertificate.INSTANCE.ignore();
            DefaultHttpDataSource.Factory factory = new DefaultHttpDataSource.Factory();
            factory.setKeepPostFor302Redirects(true);
            factory.setAllowCrossProtocolRedirects(true);
            factory.setTransferListener(new TransferListener() { // from class: com.pjy.koreatv.PlayerFragment$play$1$1
                @Override // androidx.media3.datasource.TransferListener
                public void onBytesTransferred(DataSource source, DataSpec dataSpec, boolean z, int i) {
                    Intrinsics.checkNotNullParameter(source, "source");
                    Intrinsics.checkNotNullParameter(dataSpec, "dataSpec");
                }

                @Override // androidx.media3.datasource.TransferListener
                public void onTransferEnd(DataSource source, DataSpec dataSpec, boolean z) {
                    Intrinsics.checkNotNullParameter(source, "source");
                    Intrinsics.checkNotNullParameter(dataSpec, "dataSpec");
                }

                @Override // androidx.media3.datasource.TransferListener
                public void onTransferInitializing(DataSource source, DataSpec dataSpec, boolean z) {
                    Intrinsics.checkNotNullParameter(source, "source");
                    Intrinsics.checkNotNullParameter(dataSpec, "dataSpec");
                }

                @Override // androidx.media3.datasource.TransferListener
                public void onTransferStart(DataSource source, DataSpec dataSpec, boolean z) {
                    Intrinsics.checkNotNullParameter(source, "source");
                    Intrinsics.checkNotNullParameter(dataSpec, "dataSpec");
                    Log.d("PlayerFragment", "onTransferStart uri " + source.getUri());
                }
            });
            MediaSource source = tvModel.getSource();
            if (source != null) {
                exoPlayer.setMediaSource(source);
            } else {
                exoPlayer.setMediaItem(tvModel.getMediaItem());
            }
            exoPlayer.prepare();
        }
    }

    /* compiled from: PlayerFragment.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0016¨\u0006\u000b"}, d2 = {"Lcom/pjy/koreatv/PlayerFragment$PlayerMediaCodecSelector;", "Landroidx/media3/exoplayer/mediacodec/MediaCodecSelector;", "()V", "getDecoderInfos", "", "Landroidx/media3/exoplayer/mediacodec/MediaCodecInfo;", "mimeType", "", "requiresSecureDecoder", "", "requiresTunnelingDecoder", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    public static final class PlayerMediaCodecSelector implements MediaCodecSelector {
        @Override // androidx.media3.exoplayer.mediacodec.MediaCodecSelector
        public List<MediaCodecInfo> getDecoderInfos(String mimeType, boolean z, boolean z2) {
            Object obj;
            Intrinsics.checkNotNullParameter(mimeType, "mimeType");
            List<MediaCodecInfo> decoderInfos = MediaCodecUtil.getDecoderInfos(mimeType, z, z2);
            Intrinsics.checkNotNullExpressionValue(decoderInfos, "getDecoderInfos(...)");
            if (Intrinsics.areEqual(mimeType, "video/hevc") && !z && !z2 && decoderInfos.size() > 0) {
                Iterator<T> it = decoderInfos.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        obj = null;
                        break;
                    }
                    obj = it.next();
                    if (Intrinsics.areEqual(((MediaCodecInfo) obj).name, "c2.android.hevc.decoder")) {
                        break;
                    }
                }
                MediaCodecInfo mediaCodecInfo = (MediaCodecInfo) obj;
                List<MediaCodecInfo> mutableListOf = mediaCodecInfo != null ? CollectionsKt.mutableListOf(mediaCodecInfo) : null;
                if (mutableListOf != null) {
                    return mutableListOf;
                }
            }
            return decoderInfos;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        Log.i(TAG, "onStart");
        super.onStart();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        Log.i(TAG, "play onResume");
        super.onResume();
        ExoPlayer exoPlayer = this.player;
        boolean z = false;
        if (exoPlayer != null && !exoPlayer.isPlaying()) {
            z = true;
        }
        if (z) {
            Log.i(TAG, "replay");
            ExoPlayer exoPlayer2 = this.player;
            if (exoPlayer2 != null) {
                exoPlayer2.prepare();
            }
            ExoPlayer exoPlayer3 = this.player;
            if (exoPlayer3 != null) {
                exoPlayer3.play();
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        ExoPlayer exoPlayer;
        super.onPause();
        ExoPlayer exoPlayer2 = this.player;
        boolean z = false;
        if (exoPlayer2 != null && exoPlayer2.isPlaying()) {
            z = true;
        }
        if (!z || (exoPlayer = this.player) == null) {
            return;
        }
        exoPlayer.stop();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        ExoPlayer exoPlayer = this.player;
        if (exoPlayer != null) {
            exoPlayer.release();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this._binding = null;
    }

    /* compiled from: PlayerFragment.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/pjy/koreatv/PlayerFragment$Companion;", "", "()V", "TAG", "", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
