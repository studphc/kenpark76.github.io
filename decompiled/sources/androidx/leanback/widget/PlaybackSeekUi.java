package androidx.leanback.widget;
/* loaded from: classes.dex */
public interface PlaybackSeekUi {

    /* loaded from: classes.dex */
    public static class Client {
        public PlaybackSeekDataProvider getPlaybackSeekDataProvider() {
            return null;
        }

        public boolean isSeekEnabled() {
            return false;
        }

        public void onSeekFinished(boolean z) {
        }

        public void onSeekPositionChanged(long j) {
        }

        public void onSeekStarted() {
        }
    }

    void setPlaybackSeekUiClient(Client client);
}
