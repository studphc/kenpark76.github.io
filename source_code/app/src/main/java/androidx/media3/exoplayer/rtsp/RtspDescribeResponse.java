package androidx.media3.exoplayer.rtsp;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class RtspDescribeResponse {
    public final RtspHeaders headers;
    public final SessionDescription sessionDescription;
    public final int status;

    public RtspDescribeResponse(RtspHeaders rtspHeaders, int i, SessionDescription sessionDescription) {
        this.headers = rtspHeaders;
        this.status = i;
        this.sessionDescription = sessionDescription;
    }
}
