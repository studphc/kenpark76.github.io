package androidx.leanback.media;

import android.view.View;
import androidx.leanback.widget.OnActionClickedListener;
import androidx.leanback.widget.PlaybackRowPresenter;
import androidx.leanback.widget.Row;
/* loaded from: classes.dex */
public abstract class PlaybackGlueHost {
    PlaybackGlue mGlue;

    /* loaded from: classes.dex */
    public static abstract class HostCallback {
        public void onHostDestroy() {
        }

        public void onHostPause() {
        }

        public void onHostResume() {
        }

        public void onHostStart() {
        }

        public void onHostStop() {
        }
    }

    /* loaded from: classes.dex */
    public static class PlayerCallback {
        public void onBufferingStateChanged(boolean z) {
        }

        public void onError(int i, CharSequence charSequence) {
        }

        public void onVideoSizeChanged(int i, int i2) {
        }
    }

    @Deprecated
    public void fadeOut() {
    }

    public PlayerCallback getPlayerCallback() {
        return null;
    }

    public void hideControlsOverlay(boolean z) {
    }

    public boolean isControlsOverlayAutoHideEnabled() {
        return false;
    }

    public boolean isControlsOverlayVisible() {
        return true;
    }

    public void notifyPlaybackRowChanged() {
    }

    @Deprecated
    public void setFadingEnabled(boolean z) {
    }

    public void setHostCallback(HostCallback hostCallback) {
    }

    public void setOnActionClickedListener(OnActionClickedListener onActionClickedListener) {
    }

    public void setOnKeyInterceptListener(View.OnKeyListener onKeyListener) {
    }

    public void setPlaybackRow(Row row) {
    }

    public void setPlaybackRowPresenter(PlaybackRowPresenter playbackRowPresenter) {
    }

    public void showControlsOverlay(boolean z) {
    }

    public void setControlsOverlayAutoHideEnabled(boolean z) {
        setFadingEnabled(z);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void attachToGlue(PlaybackGlue playbackGlue) {
        PlaybackGlue playbackGlue2 = this.mGlue;
        if (playbackGlue2 != null) {
            playbackGlue2.onDetachedFromHost();
        }
        this.mGlue = playbackGlue;
        if (playbackGlue != null) {
            playbackGlue.onAttachedToHost(this);
        }
    }
}
