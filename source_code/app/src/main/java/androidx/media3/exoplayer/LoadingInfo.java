package androidx.media3.exoplayer;

import androidx.media3.common.util.Assertions;
import com.google.common.base.Objects;
/* loaded from: classes.dex */
public final class LoadingInfo {
    public final long lastRebufferRealtimeMs;
    public final long playbackPositionUs;
    public final float playbackSpeed;

    /* loaded from: classes.dex */
    public static final class Builder {
        private long lastRebufferRealtimeMs;
        private long playbackPositionUs;
        private float playbackSpeed;

        public Builder() {
            this.playbackPositionUs = -9223372036854775807L;
            this.playbackSpeed = -3.4028235E38f;
            this.lastRebufferRealtimeMs = -9223372036854775807L;
        }

        private Builder(LoadingInfo loadingInfo) {
            this.playbackPositionUs = loadingInfo.playbackPositionUs;
            this.playbackSpeed = loadingInfo.playbackSpeed;
            this.lastRebufferRealtimeMs = loadingInfo.lastRebufferRealtimeMs;
        }

        public Builder setPlaybackPositionUs(long j) {
            this.playbackPositionUs = j;
            return this;
        }

        public Builder setPlaybackSpeed(float f) {
            Assertions.checkArgument(f > 0.0f || f == -3.4028235E38f);
            this.playbackSpeed = f;
            return this;
        }

        public Builder setLastRebufferRealtimeMs(long j) {
            Assertions.checkArgument(j >= 0 || j == -9223372036854775807L);
            this.lastRebufferRealtimeMs = j;
            return this;
        }

        public LoadingInfo build() {
            return new LoadingInfo(this);
        }
    }

    private LoadingInfo(Builder builder) {
        this.playbackPositionUs = builder.playbackPositionUs;
        this.playbackSpeed = builder.playbackSpeed;
        this.lastRebufferRealtimeMs = builder.lastRebufferRealtimeMs;
    }

    public Builder buildUpon() {
        return new Builder();
    }

    public boolean rebufferedSince(long j) {
        long j2 = this.lastRebufferRealtimeMs;
        return (j2 == -9223372036854775807L || j == -9223372036854775807L || j2 < j) ? false : true;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof LoadingInfo) {
            LoadingInfo loadingInfo = (LoadingInfo) obj;
            return this.playbackPositionUs == loadingInfo.playbackPositionUs && this.playbackSpeed == loadingInfo.playbackSpeed && this.lastRebufferRealtimeMs == loadingInfo.lastRebufferRealtimeMs;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hashCode(Long.valueOf(this.playbackPositionUs), Float.valueOf(this.playbackSpeed), Long.valueOf(this.lastRebufferRealtimeMs));
    }
}
