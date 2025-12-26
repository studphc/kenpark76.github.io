package androidx.leanback.media;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import androidx.leanback.media.PlayerAdapter;
import androidx.leanback.widget.AbstractDetailsDescriptionPresenter;
import androidx.leanback.widget.Action;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.PlaybackControlsRow;
import androidx.leanback.widget.PlaybackControlsRowPresenter;
import androidx.leanback.widget.PlaybackRowPresenter;
import androidx.leanback.widget.RowPresenter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/* loaded from: classes.dex */
public class PlaybackBannerControlGlue<T extends PlayerAdapter> extends PlaybackBaseControlGlue<T> {
    public static final int ACTION_CUSTOM_LEFT_FIRST = 1;
    public static final int ACTION_CUSTOM_RIGHT_FIRST = 4096;
    public static final int ACTION_FAST_FORWARD = 128;
    public static final int ACTION_PLAY_PAUSE = 64;
    public static final int ACTION_REWIND = 32;
    public static final int ACTION_SKIP_TO_NEXT = 256;
    public static final int ACTION_SKIP_TO_PREVIOUS = 16;
    private static final int NUMBER_OF_SEEK_SPEEDS = 5;
    public static final int PLAYBACK_SPEED_FAST_L0 = 10;
    public static final int PLAYBACK_SPEED_FAST_L1 = 11;
    public static final int PLAYBACK_SPEED_FAST_L2 = 12;
    public static final int PLAYBACK_SPEED_FAST_L3 = 13;
    public static final int PLAYBACK_SPEED_FAST_L4 = 14;
    public static final int PLAYBACK_SPEED_INVALID = -1;
    public static final int PLAYBACK_SPEED_NORMAL = 1;
    public static final int PLAYBACK_SPEED_PAUSED = 0;
    private static final String TAG = "PlaybackBannerControlGlue";
    private PlaybackControlsRow.FastForwardAction mFastForwardAction;
    private final int[] mFastForwardSpeeds;
    private boolean mIsCustomizedFastForwardSupported;
    private boolean mIsCustomizedRewindSupported;
    private int mPlaybackSpeed;
    private PlaybackControlsRow.RewindAction mRewindAction;
    private final int[] mRewindSpeeds;
    private PlaybackControlsRow.SkipNextAction mSkipNextAction;
    private PlaybackControlsRow.SkipPreviousAction mSkipPreviousAction;
    private long mStartPosition;
    private long mStartTime;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface ACTION_ {
    }

    public PlaybackBannerControlGlue(Context context, int[] iArr, T t) {
        this(context, iArr, iArr, t);
    }

    public PlaybackBannerControlGlue(Context context, int[] iArr, int[] iArr2, T t) {
        super(context, t);
        this.mPlaybackSpeed = 0;
        this.mStartPosition = 0L;
        if (iArr.length == 0 || iArr.length > 5) {
            throw new IllegalArgumentException("invalid fastForwardSpeeds array size");
        }
        this.mFastForwardSpeeds = iArr;
        if (iArr2.length == 0 || iArr2.length > 5) {
            throw new IllegalArgumentException("invalid rewindSpeeds array size");
        }
        this.mRewindSpeeds = iArr2;
        if ((this.mPlayerAdapter.getSupportedActions() & 128) != 0) {
            this.mIsCustomizedFastForwardSupported = true;
        }
        if ((this.mPlayerAdapter.getSupportedActions() & 32) != 0) {
            this.mIsCustomizedRewindSupported = true;
        }
    }

    @Override // androidx.leanback.media.PlaybackBaseControlGlue
    public void setControlsRow(PlaybackControlsRow playbackControlsRow) {
        super.setControlsRow(playbackControlsRow);
        onUpdatePlaybackState();
    }

    @Override // androidx.leanback.media.PlaybackBaseControlGlue
    protected void onCreatePrimaryActions(ArrayObjectAdapter arrayObjectAdapter) {
        Object obj;
        Object obj2;
        Object obj3;
        Object obj4;
        long supportedActions = getSupportedActions();
        int i = ((16 & supportedActions) > 0L ? 1 : ((16 & supportedActions) == 0L ? 0 : -1));
        if (i != 0 && this.mSkipPreviousAction == null) {
            PlaybackControlsRow.SkipPreviousAction skipPreviousAction = new PlaybackControlsRow.SkipPreviousAction(getContext());
            this.mSkipPreviousAction = skipPreviousAction;
            arrayObjectAdapter.add(skipPreviousAction);
        } else if (i == 0 && (obj = this.mSkipPreviousAction) != null) {
            arrayObjectAdapter.remove(obj);
            this.mSkipPreviousAction = null;
        }
        int i2 = ((32 & supportedActions) > 0L ? 1 : ((32 & supportedActions) == 0L ? 0 : -1));
        if (i2 != 0 && this.mRewindAction == null) {
            PlaybackControlsRow.RewindAction rewindAction = new PlaybackControlsRow.RewindAction(getContext(), this.mRewindSpeeds.length);
            this.mRewindAction = rewindAction;
            arrayObjectAdapter.add(rewindAction);
        } else if (i2 == 0 && (obj2 = this.mRewindAction) != null) {
            arrayObjectAdapter.remove(obj2);
            this.mRewindAction = null;
        }
        int i3 = ((64 & supportedActions) > 0L ? 1 : ((64 & supportedActions) == 0L ? 0 : -1));
        if (i3 != 0 && this.mPlayPauseAction == null) {
            this.mPlayPauseAction = new PlaybackControlsRow.PlayPauseAction(getContext());
            PlaybackControlsRow.PlayPauseAction playPauseAction = new PlaybackControlsRow.PlayPauseAction(getContext());
            this.mPlayPauseAction = playPauseAction;
            arrayObjectAdapter.add(playPauseAction);
        } else if (i3 == 0 && this.mPlayPauseAction != null) {
            arrayObjectAdapter.remove(this.mPlayPauseAction);
            this.mPlayPauseAction = null;
        }
        int i4 = ((128 & supportedActions) > 0L ? 1 : ((128 & supportedActions) == 0L ? 0 : -1));
        if (i4 != 0 && this.mFastForwardAction == null) {
            this.mFastForwardAction = new PlaybackControlsRow.FastForwardAction(getContext(), this.mFastForwardSpeeds.length);
            PlaybackControlsRow.FastForwardAction fastForwardAction = new PlaybackControlsRow.FastForwardAction(getContext(), this.mFastForwardSpeeds.length);
            this.mFastForwardAction = fastForwardAction;
            arrayObjectAdapter.add(fastForwardAction);
        } else if (i4 == 0 && (obj3 = this.mFastForwardAction) != null) {
            arrayObjectAdapter.remove(obj3);
            this.mFastForwardAction = null;
        }
        int i5 = ((supportedActions & 256) > 0L ? 1 : ((supportedActions & 256) == 0L ? 0 : -1));
        if (i5 != 0 && this.mSkipNextAction == null) {
            PlaybackControlsRow.SkipNextAction skipNextAction = new PlaybackControlsRow.SkipNextAction(getContext());
            this.mSkipNextAction = skipNextAction;
            arrayObjectAdapter.add(skipNextAction);
        } else if (i5 != 0 || (obj4 = this.mSkipNextAction) == null) {
        } else {
            arrayObjectAdapter.remove(obj4);
            this.mSkipNextAction = null;
        }
    }

    @Override // androidx.leanback.media.PlaybackBaseControlGlue
    protected PlaybackRowPresenter onCreateRowPresenter() {
        return new PlaybackControlsRowPresenter(new AbstractDetailsDescriptionPresenter() { // from class: androidx.leanback.media.PlaybackBannerControlGlue.1
            @Override // androidx.leanback.widget.AbstractDetailsDescriptionPresenter
            protected void onBindDescription(AbstractDetailsDescriptionPresenter.ViewHolder viewHolder, Object obj) {
                PlaybackBannerControlGlue playbackBannerControlGlue = (PlaybackBannerControlGlue) obj;
                viewHolder.getTitle().setText(playbackBannerControlGlue.getTitle());
                viewHolder.getSubtitle().setText(playbackBannerControlGlue.getSubtitle());
            }
        }) { // from class: androidx.leanback.media.PlaybackBannerControlGlue.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // androidx.leanback.widget.PlaybackControlsRowPresenter, androidx.leanback.widget.RowPresenter
            public void onBindRowViewHolder(RowPresenter.ViewHolder viewHolder, Object obj) {
                super.onBindRowViewHolder(viewHolder, obj);
                viewHolder.setOnKeyListener(PlaybackBannerControlGlue.this);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // androidx.leanback.widget.PlaybackControlsRowPresenter, androidx.leanback.widget.RowPresenter
            public void onUnbindRowViewHolder(RowPresenter.ViewHolder viewHolder) {
                super.onUnbindRowViewHolder(viewHolder);
                viewHolder.setOnKeyListener(null);
            }
        };
    }

    @Override // androidx.leanback.media.PlaybackBaseControlGlue, androidx.leanback.widget.OnActionClickedListener
    public void onActionClicked(Action action) {
        dispatchAction(action, null);
    }

    @Override // androidx.leanback.media.PlaybackBaseControlGlue, android.view.View.OnKeyListener
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i != 4 && i != 111) {
            switch (i) {
                case 19:
                case 20:
                case 21:
                case 22:
                    break;
                default:
                    Action actionForKeyCode = this.mControlsRow.getActionForKeyCode(this.mControlsRow.getPrimaryActionsAdapter(), i);
                    if (actionForKeyCode == null) {
                        actionForKeyCode = this.mControlsRow.getActionForKeyCode(this.mControlsRow.getSecondaryActionsAdapter(), i);
                    }
                    if (actionForKeyCode != null) {
                        if (keyEvent.getAction() == 0) {
                            dispatchAction(actionForKeyCode, keyEvent);
                        }
                        return true;
                    }
                    return false;
            }
        }
        int i2 = this.mPlaybackSpeed;
        if (i2 >= 10 || i2 <= -10) {
            play();
            onUpdatePlaybackStatusAfterUserAction();
            return i == 4 || i == 111;
        }
        return false;
    }

    void onUpdatePlaybackStatusAfterUserAction() {
        updatePlaybackState(this.mIsPlaying);
    }

    private void incrementFastForwardPlaybackSpeed() {
        int i = this.mPlaybackSpeed;
        switch (i) {
            case 10:
            case 11:
            case 12:
            case 13:
                this.mPlaybackSpeed = i + 1;
                return;
            default:
                this.mPlaybackSpeed = 10;
                return;
        }
    }

    private void decrementRewindPlaybackSpeed() {
        int i = this.mPlaybackSpeed;
        switch (i) {
            case -13:
            case -12:
            case -11:
            case -10:
                this.mPlaybackSpeed = i - 1;
                return;
            default:
                this.mPlaybackSpeed = -10;
                return;
        }
    }

    boolean dispatchAction(Action action, KeyEvent keyEvent) {
        boolean z = false;
        if (action == this.mPlayPauseAction) {
            boolean z2 = keyEvent == null || keyEvent.getKeyCode() == 85 || keyEvent.getKeyCode() == 126;
            if ((keyEvent == null || keyEvent.getKeyCode() == 85 || keyEvent.getKeyCode() == 127) ? true : true) {
                int i = this.mPlaybackSpeed;
                if (!z2 ? i != 0 : i == 1) {
                    pause();
                    onUpdatePlaybackStatusAfterUserAction();
                    return true;
                }
            }
            if (z2 && this.mPlaybackSpeed != 1) {
                play();
            }
            onUpdatePlaybackStatusAfterUserAction();
            return true;
        } else if (action == this.mSkipNextAction) {
            next();
            return true;
        } else if (action == this.mSkipPreviousAction) {
            previous();
            return true;
        } else if (action == this.mFastForwardAction) {
            if (!this.mPlayerAdapter.isPrepared() || this.mPlaybackSpeed >= getMaxForwardSpeedId()) {
                return true;
            }
            if (this.mIsCustomizedFastForwardSupported) {
                this.mIsPlaying = true;
                this.mPlayerAdapter.fastForward();
            } else {
                fakePause();
            }
            incrementFastForwardPlaybackSpeed();
            onUpdatePlaybackStatusAfterUserAction();
            return true;
        } else if (action == this.mRewindAction) {
            if (!this.mPlayerAdapter.isPrepared() || this.mPlaybackSpeed <= (-getMaxRewindSpeedId())) {
                return true;
            }
            if (this.mIsCustomizedFastForwardSupported) {
                this.mIsPlaying = true;
                this.mPlayerAdapter.rewind();
            } else {
                fakePause();
            }
            decrementRewindPlaybackSpeed();
            onUpdatePlaybackStatusAfterUserAction();
            return true;
        } else {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.leanback.media.PlaybackBaseControlGlue
    public void onPlayStateChanged() {
        onUpdatePlaybackState();
        super.onPlayStateChanged();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.leanback.media.PlaybackBaseControlGlue
    public void onPlayCompleted() {
        super.onPlayCompleted();
        this.mIsPlaying = false;
        this.mPlaybackSpeed = 0;
        this.mStartPosition = getCurrentPosition();
        this.mStartTime = System.currentTimeMillis();
        onUpdatePlaybackState();
    }

    void onUpdatePlaybackState() {
        updatePlaybackState(this.mIsPlaying);
    }

    private void updatePlaybackState(boolean z) {
        if (this.mControlsRow == null) {
            return;
        }
        if (!z) {
            onUpdateProgress();
            this.mPlayerAdapter.setProgressUpdatingEnabled(false);
        } else {
            this.mPlayerAdapter.setProgressUpdatingEnabled(true);
        }
        if (this.mFadeWhenPlaying && getHost() != null) {
            getHost().setControlsOverlayAutoHideEnabled(z);
        }
        ArrayObjectAdapter arrayObjectAdapter = (ArrayObjectAdapter) getControlsRow().getPrimaryActionsAdapter();
        if (this.mPlayPauseAction != null && this.mPlayPauseAction.getIndex() != z) {
            this.mPlayPauseAction.setIndex(z ? 1 : 0);
            notifyItemChanged(arrayObjectAdapter, this.mPlayPauseAction);
        }
        PlaybackControlsRow.FastForwardAction fastForwardAction = this.mFastForwardAction;
        if (fastForwardAction != null) {
            int i = this.mPlaybackSpeed;
            int i2 = i >= 10 ? (i - 10) + 1 : 0;
            if (fastForwardAction.getIndex() != i2) {
                this.mFastForwardAction.setIndex(i2);
                notifyItemChanged(arrayObjectAdapter, this.mFastForwardAction);
            }
        }
        PlaybackControlsRow.RewindAction rewindAction = this.mRewindAction;
        if (rewindAction != null) {
            int i3 = this.mPlaybackSpeed;
            int i4 = i3 <= -10 ? ((-i3) - 10) + 1 : 0;
            if (rewindAction.getIndex() != i4) {
                this.mRewindAction.setIndex(i4);
                notifyItemChanged(arrayObjectAdapter, this.mRewindAction);
            }
        }
    }

    public int[] getFastForwardSpeeds() {
        return this.mFastForwardSpeeds;
    }

    public int[] getRewindSpeeds() {
        return this.mRewindSpeeds;
    }

    private int getMaxForwardSpeedId() {
        return (this.mFastForwardSpeeds.length - 1) + 10;
    }

    private int getMaxRewindSpeedId() {
        return (this.mRewindSpeeds.length - 1) + 10;
    }

    @Override // androidx.leanback.media.PlaybackBaseControlGlue
    public long getCurrentPosition() {
        int i;
        int i2 = this.mPlaybackSpeed;
        if (i2 == 0 || i2 == 1) {
            return this.mPlayerAdapter.getCurrentPosition();
        }
        if (i2 >= 10) {
            if (this.mIsCustomizedFastForwardSupported) {
                return this.mPlayerAdapter.getCurrentPosition();
            }
            i = getFastForwardSpeeds()[i2 - 10];
        } else if (i2 > -10) {
            return -1L;
        } else {
            if (this.mIsCustomizedRewindSupported) {
                return this.mPlayerAdapter.getCurrentPosition();
            }
            i = -getRewindSpeeds()[(-i2) - 10];
        }
        long currentTimeMillis = this.mStartPosition + ((System.currentTimeMillis() - this.mStartTime) * i);
        if (currentTimeMillis > getDuration()) {
            this.mPlaybackSpeed = 0;
            long duration = getDuration();
            this.mPlayerAdapter.seekTo(duration);
            this.mStartPosition = 0L;
            pause();
            return duration;
        } else if (currentTimeMillis < 0) {
            this.mPlaybackSpeed = 0;
            this.mPlayerAdapter.seekTo(0L);
            this.mStartPosition = 0L;
            pause();
            return 0L;
        } else {
            return currentTimeMillis;
        }
    }

    @Override // androidx.leanback.media.PlaybackBaseControlGlue, androidx.leanback.media.PlaybackGlue
    public void play() {
        if (this.mPlayerAdapter.isPrepared()) {
            if (this.mPlaybackSpeed == 0 && this.mPlayerAdapter.getCurrentPosition() >= this.mPlayerAdapter.getDuration()) {
                this.mStartPosition = 0L;
            } else {
                this.mStartPosition = getCurrentPosition();
            }
            this.mStartTime = System.currentTimeMillis();
            this.mIsPlaying = true;
            this.mPlaybackSpeed = 1;
            this.mPlayerAdapter.seekTo(this.mStartPosition);
            super.play();
            onUpdatePlaybackState();
        }
    }

    @Override // androidx.leanback.media.PlaybackBaseControlGlue, androidx.leanback.media.PlaybackGlue
    public void pause() {
        this.mIsPlaying = false;
        this.mPlaybackSpeed = 0;
        this.mStartPosition = getCurrentPosition();
        this.mStartTime = System.currentTimeMillis();
        super.pause();
        onUpdatePlaybackState();
    }

    private void fakePause() {
        this.mIsPlaying = true;
        this.mStartPosition = getCurrentPosition();
        this.mStartTime = System.currentTimeMillis();
        super.pause();
        onUpdatePlaybackState();
    }
}
