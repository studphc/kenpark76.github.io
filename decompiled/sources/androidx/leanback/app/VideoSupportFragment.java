package androidx.leanback.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import androidx.leanback.R;
/* loaded from: classes.dex */
public class VideoSupportFragment extends PlaybackSupportFragment {
    static final int SURFACE_CREATED = 1;
    static final int SURFACE_NOT_CREATED = 0;
    SurfaceHolder.Callback mMediaPlaybackCallback;
    int mState = 0;
    SurfaceView mVideoSurface;

    @Override // androidx.leanback.app.PlaybackSupportFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ViewGroup viewGroup2 = (ViewGroup) super.onCreateView(layoutInflater, viewGroup, bundle);
        SurfaceView surfaceView = (SurfaceView) LayoutInflater.from(getContext()).inflate(R.layout.lb_video_surface, viewGroup2, false);
        this.mVideoSurface = surfaceView;
        viewGroup2.addView(surfaceView, 0);
        this.mVideoSurface.getHolder().addCallback(new SurfaceHolder.Callback() { // from class: androidx.leanback.app.VideoSupportFragment.1
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (VideoSupportFragment.this.mMediaPlaybackCallback != null) {
                    VideoSupportFragment.this.mMediaPlaybackCallback.surfaceCreated(surfaceHolder);
                }
                VideoSupportFragment.this.mState = 1;
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
                if (VideoSupportFragment.this.mMediaPlaybackCallback != null) {
                    VideoSupportFragment.this.mMediaPlaybackCallback.surfaceChanged(surfaceHolder, i, i2, i3);
                }
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                if (VideoSupportFragment.this.mMediaPlaybackCallback != null) {
                    VideoSupportFragment.this.mMediaPlaybackCallback.surfaceDestroyed(surfaceHolder);
                }
                VideoSupportFragment.this.mState = 0;
            }
        });
        setBackgroundType(2);
        return viewGroup2;
    }

    public void setSurfaceHolderCallback(SurfaceHolder.Callback callback) {
        this.mMediaPlaybackCallback = callback;
        if (callback == null || this.mState != 1) {
            return;
        }
        callback.surfaceCreated(this.mVideoSurface.getHolder());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.leanback.app.PlaybackSupportFragment
    public void onVideoSizeChanged(int i, int i2) {
        int width = getView().getWidth();
        int height = getView().getHeight();
        ViewGroup.LayoutParams layoutParams = this.mVideoSurface.getLayoutParams();
        int i3 = width * i2;
        int i4 = i * height;
        if (i3 > i4) {
            layoutParams.height = height;
            layoutParams.width = i4 / i2;
        } else {
            layoutParams.width = width;
            layoutParams.height = i3 / i;
        }
        this.mVideoSurface.setLayoutParams(layoutParams);
    }

    public SurfaceView getSurfaceView() {
        return this.mVideoSurface;
    }

    @Override // androidx.leanback.app.PlaybackSupportFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        this.mVideoSurface = null;
        this.mState = 0;
        super.onDestroyView();
    }
}
