package androidx.media3.exoplayer.mediacodec;

import android.media.MediaCodecInfo;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.drm.FrameworkMediaDrm$$ExternalSyntheticApiModelOutline0;
import java.util.List;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class MediaCodecPerformancePointCoverageProvider {
    static final int COVERAGE_RESULT_NO = 1;
    static final int COVERAGE_RESULT_NO_PERFORMANCE_POINTS_UNSUPPORTED = 0;
    static final int COVERAGE_RESULT_YES = 2;
    private static Boolean shouldIgnorePerformancePoints;

    private MediaCodecPerformancePointCoverageProvider() {
    }

    public static int areResolutionAndFrameRateCovered(MediaCodecInfo.VideoCapabilities videoCapabilities, int i, int i2, double d) {
        if (Util.SDK_INT >= 29) {
            Boolean bool = shouldIgnorePerformancePoints;
            if (bool == null || !bool.booleanValue()) {
                return Api29.areResolutionAndFrameRateCovered(videoCapabilities, i, i2, d);
            }
            return 0;
        }
        return 0;
    }

    /* loaded from: classes.dex */
    private static final class Api29 {
        private Api29() {
        }

        public static int areResolutionAndFrameRateCovered(MediaCodecInfo.VideoCapabilities videoCapabilities, int i, int i2, double d) {
            List supportedPerformancePoints;
            supportedPerformancePoints = videoCapabilities.getSupportedPerformancePoints();
            if (supportedPerformancePoints == null || supportedPerformancePoints.isEmpty()) {
                return 0;
            }
            FrameworkMediaDrm$$ExternalSyntheticApiModelOutline0.m$1();
            int evaluatePerformancePointCoverage = evaluatePerformancePointCoverage(supportedPerformancePoints, FrameworkMediaDrm$$ExternalSyntheticApiModelOutline0.m(i, i2, (int) d));
            if (evaluatePerformancePointCoverage == 1 && MediaCodecPerformancePointCoverageProvider.shouldIgnorePerformancePoints == null) {
                Boolean unused = MediaCodecPerformancePointCoverageProvider.shouldIgnorePerformancePoints = Boolean.valueOf(shouldIgnorePerformancePoints());
                if (MediaCodecPerformancePointCoverageProvider.shouldIgnorePerformancePoints.booleanValue()) {
                    return 0;
                }
            }
            return evaluatePerformancePointCoverage;
        }

        /* JADX WARN: Code restructure failed: missing block: B:15:0x0041, code lost:
            r4 = r1.get(r3).capabilities.getVideoCapabilities().getSupportedPerformancePoints();
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        private static boolean shouldIgnorePerformancePoints() {
            /*
                int r0 = androidx.media3.common.util.Util.SDK_INT
                r1 = 35
                r2 = 0
                if (r0 < r1) goto L8
                return r2
            L8:
                r0 = 1
                androidx.media3.common.Format$Builder r1 = new androidx.media3.common.Format$Builder     // Catch: androidx.media3.exoplayer.mediacodec.MediaCodecUtil.DecoderQueryException -> L71
                r1.<init>()     // Catch: androidx.media3.exoplayer.mediacodec.MediaCodecUtil.DecoderQueryException -> L71
                java.lang.String r3 = "video/avc"
                androidx.media3.common.Format$Builder r1 = r1.setSampleMimeType(r3)     // Catch: androidx.media3.exoplayer.mediacodec.MediaCodecUtil.DecoderQueryException -> L71
                androidx.media3.common.Format r1 = r1.build()     // Catch: androidx.media3.exoplayer.mediacodec.MediaCodecUtil.DecoderQueryException -> L71
                java.lang.String r3 = r1.sampleMimeType     // Catch: androidx.media3.exoplayer.mediacodec.MediaCodecUtil.DecoderQueryException -> L71
                if (r3 == 0) goto L71
                androidx.media3.exoplayer.mediacodec.MediaCodecSelector r3 = androidx.media3.exoplayer.mediacodec.MediaCodecSelector.DEFAULT     // Catch: androidx.media3.exoplayer.mediacodec.MediaCodecUtil.DecoderQueryException -> L71
                java.util.List r1 = androidx.media3.exoplayer.mediacodec.MediaCodecUtil.getDecoderInfosSoftMatch(r3, r1, r2, r2)     // Catch: androidx.media3.exoplayer.mediacodec.MediaCodecUtil.DecoderQueryException -> L71
                r3 = 0
            L23:
                int r4 = r1.size()     // Catch: androidx.media3.exoplayer.mediacodec.MediaCodecUtil.DecoderQueryException -> L71
                if (r3 >= r4) goto L71
                java.lang.Object r4 = r1.get(r3)     // Catch: androidx.media3.exoplayer.mediacodec.MediaCodecUtil.DecoderQueryException -> L71
                androidx.media3.exoplayer.mediacodec.MediaCodecInfo r4 = (androidx.media3.exoplayer.mediacodec.MediaCodecInfo) r4     // Catch: androidx.media3.exoplayer.mediacodec.MediaCodecUtil.DecoderQueryException -> L71
                android.media.MediaCodecInfo$CodecCapabilities r4 = r4.capabilities     // Catch: androidx.media3.exoplayer.mediacodec.MediaCodecUtil.DecoderQueryException -> L71
                if (r4 == 0) goto L6e
                java.lang.Object r4 = r1.get(r3)     // Catch: androidx.media3.exoplayer.mediacodec.MediaCodecUtil.DecoderQueryException -> L71
                androidx.media3.exoplayer.mediacodec.MediaCodecInfo r4 = (androidx.media3.exoplayer.mediacodec.MediaCodecInfo) r4     // Catch: androidx.media3.exoplayer.mediacodec.MediaCodecUtil.DecoderQueryException -> L71
                android.media.MediaCodecInfo$CodecCapabilities r4 = r4.capabilities     // Catch: androidx.media3.exoplayer.mediacodec.MediaCodecUtil.DecoderQueryException -> L71
                android.media.MediaCodecInfo$VideoCapabilities r4 = r4.getVideoCapabilities()     // Catch: androidx.media3.exoplayer.mediacodec.MediaCodecUtil.DecoderQueryException -> L71
                if (r4 == 0) goto L6e
                java.lang.Object r4 = r1.get(r3)     // Catch: androidx.media3.exoplayer.mediacodec.MediaCodecUtil.DecoderQueryException -> L71
                androidx.media3.exoplayer.mediacodec.MediaCodecInfo r4 = (androidx.media3.exoplayer.mediacodec.MediaCodecInfo) r4     // Catch: androidx.media3.exoplayer.mediacodec.MediaCodecUtil.DecoderQueryException -> L71
                android.media.MediaCodecInfo$CodecCapabilities r4 = r4.capabilities     // Catch: androidx.media3.exoplayer.mediacodec.MediaCodecUtil.DecoderQueryException -> L71
                android.media.MediaCodecInfo$VideoCapabilities r4 = r4.getVideoCapabilities()     // Catch: androidx.media3.exoplayer.mediacodec.MediaCodecUtil.DecoderQueryException -> L71
                java.util.List r4 = androidx.media3.exoplayer.drm.FrameworkMediaDrm$$ExternalSyntheticApiModelOutline0.m(r4)     // Catch: androidx.media3.exoplayer.mediacodec.MediaCodecUtil.DecoderQueryException -> L71
                if (r4 == 0) goto L6e
                boolean r5 = r4.isEmpty()     // Catch: androidx.media3.exoplayer.mediacodec.MediaCodecUtil.DecoderQueryException -> L71
                if (r5 != 0) goto L6e
                androidx.media3.exoplayer.drm.FrameworkMediaDrm$$ExternalSyntheticApiModelOutline0.m$1()     // Catch: androidx.media3.exoplayer.mediacodec.MediaCodecUtil.DecoderQueryException -> L71
                r1 = 720(0x2d0, float:1.009E-42)
                r3 = 60
                r5 = 1280(0x500, float:1.794E-42)
                android.media.MediaCodecInfo$VideoCapabilities$PerformancePoint r1 = androidx.media3.exoplayer.drm.FrameworkMediaDrm$$ExternalSyntheticApiModelOutline0.m(r5, r1, r3)     // Catch: androidx.media3.exoplayer.mediacodec.MediaCodecUtil.DecoderQueryException -> L71
                int r1 = evaluatePerformancePointCoverage(r4, r1)     // Catch: androidx.media3.exoplayer.mediacodec.MediaCodecUtil.DecoderQueryException -> L71
                if (r1 != r0) goto L6d
                r2 = 1
            L6d:
                return r2
            L6e:
                int r3 = r3 + 1
                goto L23
            L71:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.media3.exoplayer.mediacodec.MediaCodecPerformancePointCoverageProvider.Api29.shouldIgnorePerformancePoints():boolean");
        }

        private static int evaluatePerformancePointCoverage(List<MediaCodecInfo.VideoCapabilities.PerformancePoint> list, MediaCodecInfo.VideoCapabilities.PerformancePoint performancePoint) {
            boolean covers;
            for (int i = 0; i < list.size(); i++) {
                covers = FrameworkMediaDrm$$ExternalSyntheticApiModelOutline0.m227m((Object) list.get(i)).covers(performancePoint);
                if (covers) {
                    return 2;
                }
            }
            return 1;
        }
    }
}
