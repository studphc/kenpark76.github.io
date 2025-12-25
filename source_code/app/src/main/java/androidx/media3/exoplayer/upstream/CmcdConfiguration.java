package androidx.media3.exoplayer.upstream;

import androidx.media3.common.C;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.Assertions;
import androidx.media3.exoplayer.upstream.CmcdConfiguration;
import com.google.common.collect.ImmutableListMultimap;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.UUID;
/* loaded from: classes.dex */
public final class CmcdConfiguration {
    public static final String CMCD_QUERY_PARAMETER_KEY = "CMCD";
    public static final String KEY_BITRATE = "br";
    public static final String KEY_BUFFER_LENGTH = "bl";
    public static final String KEY_BUFFER_STARVATION = "bs";
    public static final String KEY_CMCD_OBJECT = "CMCD-Object";
    public static final String KEY_CMCD_REQUEST = "CMCD-Request";
    public static final String KEY_CMCD_SESSION = "CMCD-Session";
    public static final String KEY_CMCD_STATUS = "CMCD-Status";
    public static final String KEY_CONTENT_ID = "cid";
    public static final String KEY_DEADLINE = "dl";
    public static final String KEY_MAXIMUM_REQUESTED_BITRATE = "rtp";
    public static final String KEY_MEASURED_THROUGHPUT = "mtp";
    public static final String KEY_NEXT_OBJECT_REQUEST = "nor";
    public static final String KEY_NEXT_RANGE_REQUEST = "nrr";
    public static final String KEY_OBJECT_DURATION = "d";
    public static final String KEY_OBJECT_TYPE = "ot";
    public static final String KEY_PLAYBACK_RATE = "pr";
    public static final String KEY_SESSION_ID = "sid";
    public static final String KEY_STARTUP = "su";
    public static final String KEY_STREAMING_FORMAT = "sf";
    public static final String KEY_STREAM_TYPE = "st";
    public static final String KEY_TOP_BITRATE = "tb";
    public static final String KEY_VERSION = "v";
    public static final int MAX_ID_LENGTH = 64;
    public static final int MODE_QUERY_PARAMETER = 1;
    public static final int MODE_REQUEST_HEADER = 0;
    public final String contentId;
    public final int dataTransmissionMode;
    public final RequestConfig requestConfig;
    public final String sessionId;

    @Target({ElementType.TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface CmcdKey {
    }

    @Target({ElementType.TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface DataTransmissionMode {
    }

    @Target({ElementType.TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface HeaderKey {
    }

    /* loaded from: classes.dex */
    public interface Factory {
        public static final Factory DEFAULT = new Factory() { // from class: androidx.media3.exoplayer.upstream.CmcdConfiguration$Factory$$ExternalSyntheticLambda0
            @Override // androidx.media3.exoplayer.upstream.CmcdConfiguration.Factory
            public final CmcdConfiguration createCmcdConfiguration(MediaItem mediaItem) {
                return CmcdConfiguration.Factory.CC.lambda$static$0(mediaItem);
            }
        };

        CmcdConfiguration createCmcdConfiguration(MediaItem mediaItem);

        /* renamed from: androidx.media3.exoplayer.upstream.CmcdConfiguration$Factory$-CC  reason: invalid class name */
        /* loaded from: classes.dex */
        public final /* synthetic */ class CC {
            static {
                Factory factory = Factory.DEFAULT;
            }

            public static /* synthetic */ CmcdConfiguration lambda$static$0(MediaItem mediaItem) {
                return new CmcdConfiguration(UUID.randomUUID().toString(), mediaItem.mediaId != null ? mediaItem.mediaId : "", new RequestConfig() { // from class: androidx.media3.exoplayer.upstream.CmcdConfiguration.Factory.1
                    /*  JADX ERROR: JadxRuntimeException in pass: InlineMethods
                        jadx.core.utils.exceptions.JadxRuntimeException: Failed to process method for inline: androidx.media3.exoplayer.upstream.CmcdConfiguration.RequestConfig.-CC.$default$getCustomData(androidx.media3.exoplayer.upstream.CmcdConfiguration$RequestConfig):com.google.common.collect.ImmutableListMultimap
                        	at jadx.core.dex.visitors.InlineMethods.processInvokeInsn(InlineMethods.java:76)
                        	at jadx.core.dex.visitors.InlineMethods.visit(InlineMethods.java:51)
                        Caused by: java.lang.IndexOutOfBoundsException: Index: 0
                        	at java.base/java.util.Collections$EmptyList.get(Collections.java:4483)
                        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:104)
                        	at jadx.core.dex.visitors.MarkMethodsForInline.isSyntheticAccessPattern(MarkMethodsForInline.java:117)
                        	at jadx.core.dex.visitors.MarkMethodsForInline.inlineMth(MarkMethodsForInline.java:86)
                        	at jadx.core.dex.visitors.MarkMethodsForInline.process(MarkMethodsForInline.java:53)
                        	at jadx.core.dex.visitors.InlineMethods.processInvokeInsn(InlineMethods.java:65)
                        	... 1 more
                        */
                    @Override // androidx.media3.exoplayer.upstream.CmcdConfiguration.RequestConfig
                    public /* synthetic */ com.google.common.collect.ImmutableListMultimap getCustomData() {
                        /*
                            r1 = this;
                            com.google.common.collect.ImmutableListMultimap r0 = androidx.media3.exoplayer.upstream.CmcdConfiguration.RequestConfig.CC.$default$getCustomData(r1)
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.exoplayer.upstream.CmcdConfiguration.Factory.AnonymousClass1.getCustomData():com.google.common.collect.ImmutableListMultimap");
                    }

                    @Override // androidx.media3.exoplayer.upstream.CmcdConfiguration.RequestConfig
                    public /* synthetic */ int getRequestedMaximumThroughputKbps(int i) {
                        int i2;
                        i2 = C.RATE_UNSET_INT;
                        return i2;
                    }

                    @Override // androidx.media3.exoplayer.upstream.CmcdConfiguration.RequestConfig
                    public /* synthetic */ boolean isKeyAllowed(String str) {
                        return RequestConfig.CC.$default$isKeyAllowed(this, str);
                    }
                });
            }
        }
    }

    /* loaded from: classes.dex */
    public interface RequestConfig {
        ImmutableListMultimap<String, String> getCustomData();

        int getRequestedMaximumThroughputKbps(int i);

        boolean isKeyAllowed(String str);

        /* renamed from: androidx.media3.exoplayer.upstream.CmcdConfiguration$RequestConfig$-CC  reason: invalid class name */
        /* loaded from: classes.dex */
        public final /* synthetic */ class CC {
            public static boolean $default$isKeyAllowed(RequestConfig _this, String str) {
                return true;
            }

            public static ImmutableListMultimap $default$getCustomData(RequestConfig _this) {
                return ImmutableListMultimap.of();
            }
        }
    }

    public CmcdConfiguration(String str, String str2, RequestConfig requestConfig) {
        this(str, str2, requestConfig, 0);
    }

    public CmcdConfiguration(String str, String str2, RequestConfig requestConfig, int i) {
        boolean z = false;
        Assertions.checkArgument(str == null || str.length() <= 64);
        Assertions.checkArgument((str2 == null || str2.length() <= 64) ? true : true);
        Assertions.checkNotNull(requestConfig);
        this.sessionId = str;
        this.contentId = str2;
        this.requestConfig = requestConfig;
        this.dataTransmissionMode = i;
    }

    public boolean isBitrateLoggingAllowed() {
        return this.requestConfig.isKeyAllowed("br");
    }

    public boolean isBufferLengthLoggingAllowed() {
        return this.requestConfig.isKeyAllowed(KEY_BUFFER_LENGTH);
    }

    public boolean isContentIdLoggingAllowed() {
        return this.requestConfig.isKeyAllowed(KEY_CONTENT_ID);
    }

    public boolean isSessionIdLoggingAllowed() {
        return this.requestConfig.isKeyAllowed(KEY_SESSION_ID);
    }

    public boolean isMaximumRequestThroughputLoggingAllowed() {
        return this.requestConfig.isKeyAllowed(KEY_MAXIMUM_REQUESTED_BITRATE);
    }

    public boolean isStreamingFormatLoggingAllowed() {
        return this.requestConfig.isKeyAllowed(KEY_STREAMING_FORMAT);
    }

    public boolean isStreamTypeLoggingAllowed() {
        return this.requestConfig.isKeyAllowed(KEY_STREAM_TYPE);
    }

    public boolean isTopBitrateLoggingAllowed() {
        return this.requestConfig.isKeyAllowed("tb");
    }

    public boolean isObjectDurationLoggingAllowed() {
        return this.requestConfig.isKeyAllowed("d");
    }

    public boolean isMeasuredThroughputLoggingAllowed() {
        return this.requestConfig.isKeyAllowed(KEY_MEASURED_THROUGHPUT);
    }

    public boolean isObjectTypeLoggingAllowed() {
        return this.requestConfig.isKeyAllowed(KEY_OBJECT_TYPE);
    }

    public boolean isBufferStarvationLoggingAllowed() {
        return this.requestConfig.isKeyAllowed(KEY_BUFFER_STARVATION);
    }

    public boolean isDeadlineLoggingAllowed() {
        return this.requestConfig.isKeyAllowed(KEY_DEADLINE);
    }

    public boolean isPlaybackRateLoggingAllowed() {
        return this.requestConfig.isKeyAllowed(KEY_PLAYBACK_RATE);
    }

    public boolean isStartupLoggingAllowed() {
        return this.requestConfig.isKeyAllowed(KEY_STARTUP);
    }

    public boolean isNextObjectRequestLoggingAllowed() {
        return this.requestConfig.isKeyAllowed(KEY_NEXT_OBJECT_REQUEST);
    }

    public boolean isNextRangeRequestLoggingAllowed() {
        return this.requestConfig.isKeyAllowed(KEY_NEXT_RANGE_REQUEST);
    }
}
