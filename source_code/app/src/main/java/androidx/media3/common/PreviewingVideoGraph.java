package androidx.media3.common;

import android.content.Context;
import androidx.media3.common.VideoGraph;
import java.util.List;
import java.util.concurrent.Executor;
/* loaded from: classes.dex */
public interface PreviewingVideoGraph extends VideoGraph {

    /* loaded from: classes.dex */
    public interface Factory {
        PreviewingVideoGraph create(Context context, ColorInfo colorInfo, ColorInfo colorInfo2, DebugViewProvider debugViewProvider, VideoGraph.Listener listener, Executor executor, List<Effect> list, long j) throws VideoFrameProcessingException;
    }

    void renderOutputFrame(long j);
}
