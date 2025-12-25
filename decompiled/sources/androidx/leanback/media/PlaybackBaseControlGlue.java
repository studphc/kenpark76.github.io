package androidx.leanback.media;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import androidx.leanback.media.PlaybackGlue;
import androidx.leanback.media.PlaybackGlueHost;
import androidx.leanback.media.PlayerAdapter;
import androidx.leanback.widget.Action;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ControlButtonPresenterSelector;
import androidx.leanback.widget.OnActionClickedListener;
import androidx.leanback.widget.PlaybackControlsRow;
import androidx.leanback.widget.PlaybackRowPresenter;
import java.util.List;
/* loaded from: classes.dex */
public abstract class PlaybackBaseControlGlue<T extends PlayerAdapter> extends PlaybackGlue implements OnActionClickedListener, View.OnKeyListener {
    public static final int ACTION_CUSTOM_LEFT_FIRST = 1;
    public static final int ACTION_CUSTOM_RIGHT_FIRST = 4096;
    public static final int ACTION_FAST_FORWARD = 128;
    public static final int ACTION_PLAY_PAUSE = 64;
    public static final int ACTION_REPEAT = 512;
    public static final int ACTION_REWIND = 32;
    public static final int ACTION_SHUFFLE = 1024;
    public static final int ACTION_SKIP_TO_NEXT = 256;
    public static final int ACTION_SKIP_TO_PREVIOUS = 16;
    static final boolean DEBUG = false;
    static final String TAG = "PlaybackTransportGlue";
    final PlayerAdapter.Callback mAdapterCallback;
    boolean mBuffering;
    PlaybackControlsRow mControlsRow;
    PlaybackRowPresenter mControlsRowPresenter;
    Drawable mCover;
    int mErrorCode;
    String mErrorMessage;
    boolean mErrorSet;
    boolean mFadeWhenPlaying;
    boolean mIsPlaying;
    PlaybackControlsRow.PlayPauseAction mPlayPauseAction;
    final T mPlayerAdapter;
    PlaybackGlueHost.PlayerCallback mPlayerCallback;
    CharSequence mSubtitle;
    CharSequence mTitle;
    int mVideoHeight;
    int mVideoWidth;

    public abstract void onActionClicked(Action action);

    protected void onCreatePrimaryActions(ArrayObjectAdapter arrayObjectAdapter) {
    }

    protected abstract PlaybackRowPresenter onCreateRowPresenter();

    protected void onCreateSecondaryActions(ArrayObjectAdapter arrayObjectAdapter) {
    }

    public abstract boolean onKey(View view, int i, KeyEvent keyEvent);

    public PlaybackBaseControlGlue(Context context, T t) {
        super(context);
        this.mIsPlaying = false;
        this.mFadeWhenPlaying = true;
        this.mBuffering = false;
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
        this.mErrorSet = false;
        PlayerAdapter.Callback callback = new PlayerAdapter.Callback() { // from class: androidx.leanback.media.PlaybackBaseControlGlue.1
            @Override // androidx.leanback.media.PlayerAdapter.Callback
            public void onPlayStateChanged(PlayerAdapter playerAdapter) {
                PlaybackBaseControlGlue.this.onPlayStateChanged();
            }

            @Override // androidx.leanback.media.PlayerAdapter.Callback
            public void onCurrentPositionChanged(PlayerAdapter playerAdapter) {
                PlaybackBaseControlGlue.this.onUpdateProgress();
            }

            @Override // androidx.leanback.media.PlayerAdapter.Callback
            public void onBufferedPositionChanged(PlayerAdapter playerAdapter) {
                PlaybackBaseControlGlue.this.onUpdateBufferedProgress();
            }

            @Override // androidx.leanback.media.PlayerAdapter.Callback
            public void onDurationChanged(PlayerAdapter playerAdapter) {
                PlaybackBaseControlGlue.this.onUpdateDuration();
            }

            @Override // androidx.leanback.media.PlayerAdapter.Callback
            public void onPlayCompleted(PlayerAdapter playerAdapter) {
                PlaybackBaseControlGlue.this.onPlayCompleted();
            }

            @Override // androidx.leanback.media.PlayerAdapter.Callback
            public void onPreparedStateChanged(PlayerAdapter playerAdapter) {
                PlaybackBaseControlGlue.this.onPreparedStateChanged();
            }

            @Override // androidx.leanback.media.PlayerAdapter.Callback
            public void onVideoSizeChanged(PlayerAdapter playerAdapter, int i, int i2) {
                PlaybackBaseControlGlue.this.mVideoWidth = i;
                PlaybackBaseControlGlue.this.mVideoHeight = i2;
                if (PlaybackBaseControlGlue.this.mPlayerCallback != null) {
                    PlaybackBaseControlGlue.this.mPlayerCallback.onVideoSizeChanged(i, i2);
                }
            }

            @Override // androidx.leanback.media.PlayerAdapter.Callback
            public void onError(PlayerAdapter playerAdapter, int i, String str) {
                PlaybackBaseControlGlue.this.mErrorSet = true;
                PlaybackBaseControlGlue.this.mErrorCode = i;
                PlaybackBaseControlGlue.this.mErrorMessage = str;
                if (PlaybackBaseControlGlue.this.mPlayerCallback != null) {
                    PlaybackBaseControlGlue.this.mPlayerCallback.onError(i, str);
                }
            }

            @Override // androidx.leanback.media.PlayerAdapter.Callback
            public void onBufferingStateChanged(PlayerAdapter playerAdapter, boolean z) {
                PlaybackBaseControlGlue.this.mBuffering = z;
                if (PlaybackBaseControlGlue.this.mPlayerCallback != null) {
                    PlaybackBaseControlGlue.this.mPlayerCallback.onBufferingStateChanged(z);
                }
            }

            @Override // androidx.leanback.media.PlayerAdapter.Callback
            public void onMetadataChanged(PlayerAdapter playerAdapter) {
                PlaybackBaseControlGlue.this.onMetadataChanged();
            }
        };
        this.mAdapterCallback = callback;
        this.mPlayerAdapter = t;
        t.setCallback(callback);
    }

    public final T getPlayerAdapter() {
        return this.mPlayerAdapter;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.leanback.media.PlaybackGlue
    public void onAttachedToHost(PlaybackGlueHost playbackGlueHost) {
        super.onAttachedToHost(playbackGlueHost);
        playbackGlueHost.setOnKeyInterceptListener(this);
        playbackGlueHost.setOnActionClickedListener(this);
        onCreateDefaultControlsRow();
        onCreateDefaultRowPresenter();
        playbackGlueHost.setPlaybackRowPresenter(getPlaybackRowPresenter());
        playbackGlueHost.setPlaybackRow(getControlsRow());
        this.mPlayerCallback = playbackGlueHost.getPlayerCallback();
        onAttachHostCallback();
        this.mPlayerAdapter.onAttachedToHost(playbackGlueHost);
    }

    void onAttachHostCallback() {
        int i;
        PlaybackGlueHost.PlayerCallback playerCallback = this.mPlayerCallback;
        if (playerCallback != null) {
            int i2 = this.mVideoWidth;
            if (i2 != 0 && (i = this.mVideoHeight) != 0) {
                playerCallback.onVideoSizeChanged(i2, i);
            }
            if (this.mErrorSet) {
                this.mPlayerCallback.onError(this.mErrorCode, this.mErrorMessage);
            }
            this.mPlayerCallback.onBufferingStateChanged(this.mBuffering);
        }
    }

    void onDetachHostCallback() {
        this.mErrorSet = false;
        this.mErrorCode = 0;
        this.mErrorMessage = null;
        PlaybackGlueHost.PlayerCallback playerCallback = this.mPlayerCallback;
        if (playerCallback != null) {
            playerCallback.onBufferingStateChanged(false);
        }
    }

    @Override // androidx.leanback.media.PlaybackGlue
    protected void onHostStart() {
        this.mPlayerAdapter.setProgressUpdatingEnabled(true);
    }

    @Override // androidx.leanback.media.PlaybackGlue
    protected void onHostStop() {
        this.mPlayerAdapter.setProgressUpdatingEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.leanback.media.PlaybackGlue
    public void onDetachedFromHost() {
        onDetachHostCallback();
        this.mPlayerCallback = null;
        this.mPlayerAdapter.onDetachedFromHost();
        this.mPlayerAdapter.setProgressUpdatingEnabled(false);
        super.onDetachedFromHost();
    }

    void onCreateDefaultControlsRow() {
        if (this.mControlsRow == null) {
            setControlsRow(new PlaybackControlsRow(this));
        }
    }

    void onCreateDefaultRowPresenter() {
        if (this.mControlsRowPresenter == null) {
            setPlaybackRowPresenter(onCreateRowPresenter());
        }
    }

    public void setControlsOverlayAutoHideEnabled(boolean z) {
        this.mFadeWhenPlaying = z;
        if (z || getHost() == null) {
            return;
        }
        getHost().setControlsOverlayAutoHideEnabled(false);
    }

    public boolean isControlsOverlayAutoHideEnabled() {
        return this.mFadeWhenPlaying;
    }

    public void setControlsRow(PlaybackControlsRow playbackControlsRow) {
        this.mControlsRow = playbackControlsRow;
        playbackControlsRow.setCurrentPosition(-1L);
        this.mControlsRow.setDuration(-1L);
        this.mControlsRow.setBufferedPosition(-1L);
        if (this.mControlsRow.getPrimaryActionsAdapter() == null) {
            ArrayObjectAdapter arrayObjectAdapter = new ArrayObjectAdapter(new ControlButtonPresenterSelector());
            onCreatePrimaryActions(arrayObjectAdapter);
            this.mControlsRow.setPrimaryActionsAdapter(arrayObjectAdapter);
        }
        if (this.mControlsRow.getSecondaryActionsAdapter() == null) {
            ArrayObjectAdapter arrayObjectAdapter2 = new ArrayObjectAdapter(new ControlButtonPresenterSelector());
            onCreateSecondaryActions(arrayObjectAdapter2);
            getControlsRow().setSecondaryActionsAdapter(arrayObjectAdapter2);
        }
        updateControlsRow();
    }

    public void setPlaybackRowPresenter(PlaybackRowPresenter playbackRowPresenter) {
        this.mControlsRowPresenter = playbackRowPresenter;
    }

    public PlaybackControlsRow getControlsRow() {
        return this.mControlsRow;
    }

    public PlaybackRowPresenter getPlaybackRowPresenter() {
        return this.mControlsRowPresenter;
    }

    private void updateControlsRow() {
        onMetadataChanged();
    }

    @Override // androidx.leanback.media.PlaybackGlue
    public final boolean isPlaying() {
        return this.mPlayerAdapter.isPlaying();
    }

    @Override // androidx.leanback.media.PlaybackGlue
    public void play() {
        this.mPlayerAdapter.play();
    }

    @Override // androidx.leanback.media.PlaybackGlue
    public void pause() {
        this.mPlayerAdapter.pause();
    }

    @Override // androidx.leanback.media.PlaybackGlue
    public void next() {
        this.mPlayerAdapter.next();
    }

    @Override // androidx.leanback.media.PlaybackGlue
    public void previous() {
        this.mPlayerAdapter.previous();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static void notifyItemChanged(ArrayObjectAdapter arrayObjectAdapter, Object obj) {
        int indexOf = arrayObjectAdapter.indexOf(obj);
        if (indexOf >= 0) {
            arrayObjectAdapter.notifyArrayItemRangeChanged(indexOf, 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onUpdateProgress() {
        PlaybackControlsRow playbackControlsRow = this.mControlsRow;
        if (playbackControlsRow != null) {
            playbackControlsRow.setCurrentPosition(this.mPlayerAdapter.isPrepared() ? getCurrentPosition() : -1L);
        }
    }

    protected void onUpdateBufferedProgress() {
        PlaybackControlsRow playbackControlsRow = this.mControlsRow;
        if (playbackControlsRow != null) {
            playbackControlsRow.setBufferedPosition(this.mPlayerAdapter.getBufferedPosition());
        }
    }

    protected void onUpdateDuration() {
        PlaybackControlsRow playbackControlsRow = this.mControlsRow;
        if (playbackControlsRow != null) {
            playbackControlsRow.setDuration(this.mPlayerAdapter.isPrepared() ? this.mPlayerAdapter.getDuration() : -1L);
        }
    }

    public final long getDuration() {
        return this.mPlayerAdapter.getDuration();
    }

    public long getCurrentPosition() {
        return this.mPlayerAdapter.getCurrentPosition();
    }

    public final long getBufferedPosition() {
        return this.mPlayerAdapter.getBufferedPosition();
    }

    @Override // androidx.leanback.media.PlaybackGlue
    public final boolean isPrepared() {
        return this.mPlayerAdapter.isPrepared();
    }

    protected void onPreparedStateChanged() {
        onUpdateDuration();
        List<PlaybackGlue.PlayerCallback> playerCallbacks = getPlayerCallbacks();
        if (playerCallbacks != null) {
            int size = playerCallbacks.size();
            for (int i = 0; i < size; i++) {
                playerCallbacks.get(i).onPreparedStateChanged(this);
            }
        }
    }

    public void setArt(Drawable drawable) {
        if (this.mCover == drawable) {
            return;
        }
        this.mCover = drawable;
        this.mControlsRow.setImageDrawable(drawable);
        if (getHost() != null) {
            getHost().notifyPlaybackRowChanged();
        }
    }

    public Drawable getArt() {
        return this.mCover;
    }

    public void setSubtitle(CharSequence charSequence) {
        if (TextUtils.equals(charSequence, this.mSubtitle)) {
            return;
        }
        this.mSubtitle = charSequence;
        if (getHost() != null) {
            getHost().notifyPlaybackRowChanged();
        }
    }

    public CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    public void setTitle(CharSequence charSequence) {
        if (TextUtils.equals(charSequence, this.mTitle)) {
            return;
        }
        this.mTitle = charSequence;
        if (getHost() != null) {
            getHost().notifyPlaybackRowChanged();
        }
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    protected void onMetadataChanged() {
        PlaybackControlsRow playbackControlsRow = this.mControlsRow;
        if (playbackControlsRow == null) {
            return;
        }
        playbackControlsRow.setImageDrawable(getArt());
        this.mControlsRow.setDuration(getDuration());
        this.mControlsRow.setCurrentPosition(getCurrentPosition());
        if (getHost() != null) {
            getHost().notifyPlaybackRowChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPlayStateChanged() {
        List<PlaybackGlue.PlayerCallback> playerCallbacks = getPlayerCallbacks();
        if (playerCallbacks != null) {
            int size = playerCallbacks.size();
            for (int i = 0; i < size; i++) {
                playerCallbacks.get(i).onPlayStateChanged(this);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPlayCompleted() {
        List<PlaybackGlue.PlayerCallback> playerCallbacks = getPlayerCallbacks();
        if (playerCallbacks != null) {
            int size = playerCallbacks.size();
            for (int i = 0; i < size; i++) {
                playerCallbacks.get(i).onPlayCompleted(this);
            }
        }
    }

    public final void seekTo(long j) {
        this.mPlayerAdapter.seekTo(j);
    }

    public long getSupportedActions() {
        return this.mPlayerAdapter.getSupportedActions();
    }
}
