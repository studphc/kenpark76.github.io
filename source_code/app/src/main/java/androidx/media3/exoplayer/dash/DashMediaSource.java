package androidx.media3.exoplayer.dash;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.SparseArray;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MediaLibraryInfo;
import androidx.media3.common.ParserException;
import androidx.media3.common.StreamKey;
import androidx.media3.common.Timeline;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.Util;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.TransferListener;
import androidx.media3.exoplayer.audio.SilenceSkippingAudioProcessor;
import androidx.media3.exoplayer.dash.DashChunkSource;
import androidx.media3.exoplayer.dash.DefaultDashChunkSource;
import androidx.media3.exoplayer.dash.PlayerEmsgHandler;
import androidx.media3.exoplayer.dash.manifest.AdaptationSet;
import androidx.media3.exoplayer.dash.manifest.DashManifest;
import androidx.media3.exoplayer.dash.manifest.DashManifestParser;
import androidx.media3.exoplayer.dash.manifest.Period;
import androidx.media3.exoplayer.dash.manifest.Representation;
import androidx.media3.exoplayer.dash.manifest.UtcTimingElement;
import androidx.media3.exoplayer.drm.DefaultDrmSessionManagerProvider;
import androidx.media3.exoplayer.drm.DrmSessionManager;
import androidx.media3.exoplayer.drm.DrmSessionManagerProvider;
import androidx.media3.exoplayer.offline.FilteringManifestParser;
import androidx.media3.exoplayer.source.BaseMediaSource;
import androidx.media3.exoplayer.source.CompositeSequenceableLoaderFactory;
import androidx.media3.exoplayer.source.DefaultCompositeSequenceableLoaderFactory;
import androidx.media3.exoplayer.source.LoadEventInfo;
import androidx.media3.exoplayer.source.MediaLoadData;
import androidx.media3.exoplayer.source.MediaPeriod;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.MediaSourceEventListener;
import androidx.media3.exoplayer.source.MediaSourceFactory;
import androidx.media3.exoplayer.upstream.Allocator;
import androidx.media3.exoplayer.upstream.CmcdConfiguration;
import androidx.media3.exoplayer.upstream.DefaultLoadErrorHandlingPolicy;
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy;
import androidx.media3.exoplayer.upstream.Loader;
import androidx.media3.exoplayer.upstream.LoaderErrorThrower;
import androidx.media3.exoplayer.upstream.ParsingLoadable;
import androidx.media3.exoplayer.util.SntpClient;
import androidx.media3.extractor.text.SubtitleParser;
import com.google.common.base.Charsets;
import com.google.common.math.LongMath;
import j$.util.DesugarTimeZone;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/* loaded from: classes.dex */
public final class DashMediaSource extends BaseMediaSource {
    public static final long DEFAULT_FALLBACK_TARGET_LIVE_OFFSET_MS = 30000;
    @Deprecated
    public static final long DEFAULT_LIVE_PRESENTATION_DELAY_MS = 30000;
    public static final String DEFAULT_MEDIA_ID = "DashMediaSource";
    private static final long DEFAULT_NOTIFY_MANIFEST_INTERVAL_MS = 5000;
    public static final long MIN_LIVE_DEFAULT_START_POSITION_US = 5000000;
    private static final String TAG = "DashMediaSource";
    private final BaseUrlExclusionList baseUrlExclusionList;
    private final DashChunkSource.Factory chunkSourceFactory;
    private final CmcdConfiguration cmcdConfiguration;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private DataSource dataSource;
    private final DrmSessionManager drmSessionManager;
    private long elapsedRealtimeOffsetMs;
    private long expiredManifestPublishTimeUs;
    private final long fallbackTargetLiveOffsetMs;
    private int firstPeriodId;
    private Handler handler;
    private Uri initialManifestUri;
    private MediaItem.LiveConfiguration liveConfiguration;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private Loader loader;
    private DashManifest manifest;
    private final ManifestCallback manifestCallback;
    private final DataSource.Factory manifestDataSourceFactory;
    private final MediaSourceEventListener.EventDispatcher manifestEventDispatcher;
    private IOException manifestFatalError;
    private long manifestLoadEndTimestampMs;
    private final LoaderErrorThrower manifestLoadErrorThrower;
    private boolean manifestLoadPending;
    private long manifestLoadStartTimestampMs;
    private final ParsingLoadable.Parser<? extends DashManifest> manifestParser;
    private Uri manifestUri;
    private final Object manifestUriLock;
    private MediaItem mediaItem;
    private TransferListener mediaTransferListener;
    private final long minLiveStartPositionUs;
    private final SparseArray<DashMediaPeriod> periodsById;
    private final PlayerEmsgHandler.PlayerEmsgCallback playerEmsgCallback;
    private final Runnable refreshManifestRunnable;
    private final boolean sideloadedManifest;
    private final Runnable simulateManifestRefreshRunnable;
    private int staleManifestReloadAttempt;

    static {
        MediaLibraryInfo.registerModule("media3.exoplayer.dash");
    }

    /* loaded from: classes.dex */
    public static final class Factory implements MediaSourceFactory {
        private final DashChunkSource.Factory chunkSourceFactory;
        private CmcdConfiguration.Factory cmcdConfigurationFactory;
        private CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
        private DrmSessionManagerProvider drmSessionManagerProvider;
        private long fallbackTargetLiveOffsetMs;
        private LoadErrorHandlingPolicy loadErrorHandlingPolicy;
        private final DataSource.Factory manifestDataSourceFactory;
        private ParsingLoadable.Parser<? extends DashManifest> manifestParser;
        private long minLiveStartPositionUs;

        public Factory(DataSource.Factory factory) {
            this(new DefaultDashChunkSource.Factory(factory), factory);
        }

        public Factory(DashChunkSource.Factory factory, DataSource.Factory factory2) {
            this.chunkSourceFactory = (DashChunkSource.Factory) Assertions.checkNotNull(factory);
            this.manifestDataSourceFactory = factory2;
            this.drmSessionManagerProvider = new DefaultDrmSessionManagerProvider();
            this.loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
            this.fallbackTargetLiveOffsetMs = 30000L;
            this.minLiveStartPositionUs = DashMediaSource.MIN_LIVE_DEFAULT_START_POSITION_US;
            this.compositeSequenceableLoaderFactory = new DefaultCompositeSequenceableLoaderFactory();
        }

        @Override // androidx.media3.exoplayer.source.MediaSource.Factory
        public Factory setCmcdConfigurationFactory(CmcdConfiguration.Factory factory) {
            this.cmcdConfigurationFactory = (CmcdConfiguration.Factory) Assertions.checkNotNull(factory);
            return this;
        }

        @Override // androidx.media3.exoplayer.source.MediaSource.Factory
        public Factory setDrmSessionManagerProvider(DrmSessionManagerProvider drmSessionManagerProvider) {
            this.drmSessionManagerProvider = (DrmSessionManagerProvider) Assertions.checkNotNull(drmSessionManagerProvider, "MediaSource.Factory#setDrmSessionManagerProvider no longer handles null by instantiating a new DefaultDrmSessionManagerProvider. Explicitly construct and pass an instance in order to retain the old behavior.");
            return this;
        }

        @Override // androidx.media3.exoplayer.source.MediaSource.Factory
        public Factory setLoadErrorHandlingPolicy(LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
            this.loadErrorHandlingPolicy = (LoadErrorHandlingPolicy) Assertions.checkNotNull(loadErrorHandlingPolicy, "MediaSource.Factory#setLoadErrorHandlingPolicy no longer handles null by instantiating a new DefaultLoadErrorHandlingPolicy. Explicitly construct and pass an instance in order to retain the old behavior.");
            return this;
        }

        @Override // androidx.media3.exoplayer.source.MediaSource.Factory
        public Factory setSubtitleParserFactory(SubtitleParser.Factory factory) {
            this.chunkSourceFactory.setSubtitleParserFactory((SubtitleParser.Factory) Assertions.checkNotNull(factory));
            return this;
        }

        @Override // androidx.media3.exoplayer.source.MediaSource.Factory
        public Factory experimentalParseSubtitlesDuringExtraction(boolean z) {
            this.chunkSourceFactory.experimentalParseSubtitlesDuringExtraction(z);
            return this;
        }

        public Factory setFallbackTargetLiveOffsetMs(long j) {
            this.fallbackTargetLiveOffsetMs = j;
            return this;
        }

        public Factory setMinLiveStartPositionUs(long j) {
            this.minLiveStartPositionUs = j;
            return this;
        }

        public Factory setManifestParser(ParsingLoadable.Parser<? extends DashManifest> parser) {
            this.manifestParser = parser;
            return this;
        }

        public Factory setCompositeSequenceableLoaderFactory(CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory) {
            this.compositeSequenceableLoaderFactory = (CompositeSequenceableLoaderFactory) Assertions.checkNotNull(compositeSequenceableLoaderFactory, "DashMediaSource.Factory#setCompositeSequenceableLoaderFactory no longer handles null by instantiating a new DefaultCompositeSequenceableLoaderFactory. Explicitly construct and pass an instance in order to retain the old behavior.");
            return this;
        }

        public DashMediaSource createMediaSource(DashManifest dashManifest) {
            return createMediaSource(dashManifest, new MediaItem.Builder().setUri(Uri.EMPTY).setMediaId("DashMediaSource").setMimeType("application/dash+xml").build());
        }

        public DashMediaSource createMediaSource(DashManifest dashManifest, MediaItem mediaItem) {
            Assertions.checkArgument(!dashManifest.dynamic);
            MediaItem.Builder mimeType = mediaItem.buildUpon().setMimeType("application/dash+xml");
            if (mediaItem.localConfiguration == null) {
                mimeType.setUri(Uri.EMPTY);
            }
            MediaItem build = mimeType.build();
            CmcdConfiguration.Factory factory = this.cmcdConfigurationFactory;
            return new DashMediaSource(build, dashManifest, null, null, this.chunkSourceFactory, this.compositeSequenceableLoaderFactory, factory == null ? null : factory.createCmcdConfiguration(build), this.drmSessionManagerProvider.get(build), this.loadErrorHandlingPolicy, this.fallbackTargetLiveOffsetMs, this.minLiveStartPositionUs);
        }

        @Override // androidx.media3.exoplayer.source.MediaSource.Factory
        public DashMediaSource createMediaSource(MediaItem mediaItem) {
            Assertions.checkNotNull(mediaItem.localConfiguration);
            ParsingLoadable.Parser parser = this.manifestParser;
            if (parser == null) {
                parser = new DashManifestParser();
            }
            List<StreamKey> list = mediaItem.localConfiguration.streamKeys;
            FilteringManifestParser filteringManifestParser = !list.isEmpty() ? new FilteringManifestParser(parser, list) : parser;
            CmcdConfiguration.Factory factory = this.cmcdConfigurationFactory;
            return new DashMediaSource(mediaItem, null, this.manifestDataSourceFactory, filteringManifestParser, this.chunkSourceFactory, this.compositeSequenceableLoaderFactory, factory == null ? null : factory.createCmcdConfiguration(mediaItem), this.drmSessionManagerProvider.get(mediaItem), this.loadErrorHandlingPolicy, this.fallbackTargetLiveOffsetMs, this.minLiveStartPositionUs);
        }

        @Override // androidx.media3.exoplayer.source.MediaSource.Factory
        public int[] getSupportedTypes() {
            return new int[]{0};
        }
    }

    private DashMediaSource(MediaItem mediaItem, DashManifest dashManifest, DataSource.Factory factory, ParsingLoadable.Parser<? extends DashManifest> parser, DashChunkSource.Factory factory2, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory, CmcdConfiguration cmcdConfiguration, DrmSessionManager drmSessionManager, LoadErrorHandlingPolicy loadErrorHandlingPolicy, long j, long j2) {
        this.mediaItem = mediaItem;
        this.liveConfiguration = mediaItem.liveConfiguration;
        this.manifestUri = ((MediaItem.LocalConfiguration) Assertions.checkNotNull(mediaItem.localConfiguration)).uri;
        this.initialManifestUri = mediaItem.localConfiguration.uri;
        this.manifest = dashManifest;
        this.manifestDataSourceFactory = factory;
        this.manifestParser = parser;
        this.chunkSourceFactory = factory2;
        this.cmcdConfiguration = cmcdConfiguration;
        this.drmSessionManager = drmSessionManager;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
        this.fallbackTargetLiveOffsetMs = j;
        this.minLiveStartPositionUs = j2;
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory;
        this.baseUrlExclusionList = new BaseUrlExclusionList();
        boolean z = dashManifest != null;
        this.sideloadedManifest = z;
        this.manifestEventDispatcher = createEventDispatcher(null);
        this.manifestUriLock = new Object();
        this.periodsById = new SparseArray<>();
        this.playerEmsgCallback = new DefaultPlayerEmsgCallback();
        this.expiredManifestPublishTimeUs = -9223372036854775807L;
        this.elapsedRealtimeOffsetMs = -9223372036854775807L;
        if (z) {
            Assertions.checkState(true ^ dashManifest.dynamic);
            this.manifestCallback = null;
            this.refreshManifestRunnable = null;
            this.simulateManifestRefreshRunnable = null;
            this.manifestLoadErrorThrower = new LoaderErrorThrower.Placeholder();
            return;
        }
        this.manifestCallback = new ManifestCallback();
        this.manifestLoadErrorThrower = new ManifestLoadErrorThrower();
        this.refreshManifestRunnable = new Runnable() { // from class: androidx.media3.exoplayer.dash.DashMediaSource$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DashMediaSource.this.startLoadingManifest();
            }
        };
        this.simulateManifestRefreshRunnable = new Runnable() { // from class: androidx.media3.exoplayer.dash.DashMediaSource$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                DashMediaSource.this.m214lambda$new$0$androidxmedia3exoplayerdashDashMediaSource();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$new$0$androidx-media3-exoplayer-dash-DashMediaSource  reason: not valid java name */
    public /* synthetic */ void m214lambda$new$0$androidxmedia3exoplayerdashDashMediaSource() {
        processManifest(false);
    }

    public void replaceManifestUri(Uri uri) {
        synchronized (this.manifestUriLock) {
            this.manifestUri = uri;
            this.initialManifestUri = uri;
        }
    }

    @Override // androidx.media3.exoplayer.source.MediaSource
    public synchronized MediaItem getMediaItem() {
        return this.mediaItem;
    }

    @Override // androidx.media3.exoplayer.source.BaseMediaSource, androidx.media3.exoplayer.source.MediaSource
    public boolean canUpdateMediaItem(MediaItem mediaItem) {
        MediaItem mediaItem2 = getMediaItem();
        MediaItem.LocalConfiguration localConfiguration = (MediaItem.LocalConfiguration) Assertions.checkNotNull(mediaItem2.localConfiguration);
        MediaItem.LocalConfiguration localConfiguration2 = mediaItem.localConfiguration;
        return localConfiguration2 != null && localConfiguration2.uri.equals(localConfiguration.uri) && localConfiguration2.streamKeys.equals(localConfiguration.streamKeys) && Util.areEqual(localConfiguration2.drmConfiguration, localConfiguration.drmConfiguration) && mediaItem2.liveConfiguration.equals(mediaItem.liveConfiguration);
    }

    @Override // androidx.media3.exoplayer.source.BaseMediaSource, androidx.media3.exoplayer.source.MediaSource
    public synchronized void updateMediaItem(MediaItem mediaItem) {
        this.mediaItem = mediaItem;
    }

    @Override // androidx.media3.exoplayer.source.BaseMediaSource
    protected void prepareSourceInternal(TransferListener transferListener) {
        this.mediaTransferListener = transferListener;
        this.drmSessionManager.setPlayer(Looper.myLooper(), getPlayerId());
        this.drmSessionManager.prepare();
        if (this.sideloadedManifest) {
            processManifest(false);
            return;
        }
        this.dataSource = this.manifestDataSourceFactory.createDataSource();
        this.loader = new Loader("DashMediaSource");
        this.handler = Util.createHandlerForCurrentLooper();
        startLoadingManifest();
    }

    @Override // androidx.media3.exoplayer.source.MediaSource
    public void maybeThrowSourceInfoRefreshError() throws IOException {
        this.manifestLoadErrorThrower.maybeThrowError();
    }

    @Override // androidx.media3.exoplayer.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j) {
        int intValue = ((Integer) mediaPeriodId.periodUid).intValue() - this.firstPeriodId;
        MediaSourceEventListener.EventDispatcher createEventDispatcher = createEventDispatcher(mediaPeriodId);
        DashMediaPeriod dashMediaPeriod = new DashMediaPeriod(intValue + this.firstPeriodId, this.manifest, this.baseUrlExclusionList, intValue, this.chunkSourceFactory, this.mediaTransferListener, this.cmcdConfiguration, this.drmSessionManager, createDrmEventDispatcher(mediaPeriodId), this.loadErrorHandlingPolicy, createEventDispatcher, this.elapsedRealtimeOffsetMs, this.manifestLoadErrorThrower, allocator, this.compositeSequenceableLoaderFactory, this.playerEmsgCallback, getPlayerId());
        this.periodsById.put(dashMediaPeriod.id, dashMediaPeriod);
        return dashMediaPeriod;
    }

    @Override // androidx.media3.exoplayer.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        DashMediaPeriod dashMediaPeriod = (DashMediaPeriod) mediaPeriod;
        dashMediaPeriod.release();
        this.periodsById.remove(dashMediaPeriod.id);
    }

    @Override // androidx.media3.exoplayer.source.BaseMediaSource
    protected void releaseSourceInternal() {
        this.manifestLoadPending = false;
        this.dataSource = null;
        Loader loader = this.loader;
        if (loader != null) {
            loader.release();
            this.loader = null;
        }
        this.manifestLoadStartTimestampMs = 0L;
        this.manifestLoadEndTimestampMs = 0L;
        this.manifest = this.sideloadedManifest ? this.manifest : null;
        this.manifestUri = this.initialManifestUri;
        this.manifestFatalError = null;
        Handler handler = this.handler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            this.handler = null;
        }
        this.elapsedRealtimeOffsetMs = -9223372036854775807L;
        this.staleManifestReloadAttempt = 0;
        this.expiredManifestPublishTimeUs = -9223372036854775807L;
        this.periodsById.clear();
        this.baseUrlExclusionList.reset();
        this.drmSessionManager.release();
    }

    void onDashManifestRefreshRequested() {
        this.handler.removeCallbacks(this.simulateManifestRefreshRunnable);
        startLoadingManifest();
    }

    void onDashManifestPublishTimeExpired(long j) {
        long j2 = this.expiredManifestPublishTimeUs;
        if (j2 == -9223372036854775807L || j2 < j) {
            this.expiredManifestPublishTimeUs = j;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00ca  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void onManifestLoadCompleted(androidx.media3.exoplayer.upstream.ParsingLoadable<androidx.media3.exoplayer.dash.manifest.DashManifest> r19, long r20, long r22) {
        /*
            Method dump skipped, instructions count: 295
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.exoplayer.dash.DashMediaSource.onManifestLoadCompleted(androidx.media3.exoplayer.upstream.ParsingLoadable, long, long):void");
    }

    Loader.LoadErrorAction onManifestLoadError(ParsingLoadable<DashManifest> parsingLoadable, long j, long j2, IOException iOException, int i) {
        Loader.LoadErrorAction createRetryAction;
        LoadEventInfo loadEventInfo = new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), j, j2, parsingLoadable.bytesLoaded());
        long retryDelayMsFor = this.loadErrorHandlingPolicy.getRetryDelayMsFor(new LoadErrorHandlingPolicy.LoadErrorInfo(loadEventInfo, new MediaLoadData(parsingLoadable.type), iOException, i));
        if (retryDelayMsFor == -9223372036854775807L) {
            createRetryAction = Loader.DONT_RETRY_FATAL;
        } else {
            createRetryAction = Loader.createRetryAction(false, retryDelayMsFor);
        }
        boolean z = !createRetryAction.isRetry();
        this.manifestEventDispatcher.loadError(loadEventInfo, parsingLoadable.type, iOException, z);
        if (z) {
            this.loadErrorHandlingPolicy.onLoadTaskConcluded(parsingLoadable.loadTaskId);
        }
        return createRetryAction;
    }

    void onUtcTimestampLoadCompleted(ParsingLoadable<Long> parsingLoadable, long j, long j2) {
        LoadEventInfo loadEventInfo = new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), j, j2, parsingLoadable.bytesLoaded());
        this.loadErrorHandlingPolicy.onLoadTaskConcluded(parsingLoadable.loadTaskId);
        this.manifestEventDispatcher.loadCompleted(loadEventInfo, parsingLoadable.type);
        onUtcTimestampResolved(parsingLoadable.getResult().longValue() - j);
    }

    Loader.LoadErrorAction onUtcTimestampLoadError(ParsingLoadable<Long> parsingLoadable, long j, long j2, IOException iOException) {
        this.manifestEventDispatcher.loadError(new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), j, j2, parsingLoadable.bytesLoaded()), parsingLoadable.type, iOException, true);
        this.loadErrorHandlingPolicy.onLoadTaskConcluded(parsingLoadable.loadTaskId);
        onUtcTimestampResolutionError(iOException);
        return Loader.DONT_RETRY;
    }

    void onLoadCanceled(ParsingLoadable<?> parsingLoadable, long j, long j2) {
        LoadEventInfo loadEventInfo = new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), j, j2, parsingLoadable.bytesLoaded());
        this.loadErrorHandlingPolicy.onLoadTaskConcluded(parsingLoadable.loadTaskId);
        this.manifestEventDispatcher.loadCanceled(loadEventInfo, parsingLoadable.type);
    }

    private void resolveUtcTimingElement(UtcTimingElement utcTimingElement) {
        String str = utcTimingElement.schemeIdUri;
        if (Util.areEqual(str, "urn:mpeg:dash:utc:direct:2014") || Util.areEqual(str, "urn:mpeg:dash:utc:direct:2012")) {
            resolveUtcTimingElementDirect(utcTimingElement);
        } else if (Util.areEqual(str, "urn:mpeg:dash:utc:http-iso:2014") || Util.areEqual(str, "urn:mpeg:dash:utc:http-iso:2012")) {
            resolveUtcTimingElementHttp(utcTimingElement, new Iso8601Parser());
        } else if (Util.areEqual(str, "urn:mpeg:dash:utc:http-xsdate:2014") || Util.areEqual(str, "urn:mpeg:dash:utc:http-xsdate:2012")) {
            resolveUtcTimingElementHttp(utcTimingElement, new XsDateTimeParser());
        } else if (Util.areEqual(str, "urn:mpeg:dash:utc:ntp:2014") || Util.areEqual(str, "urn:mpeg:dash:utc:ntp:2012")) {
            loadNtpTimeOffset();
        } else {
            onUtcTimestampResolutionError(new IOException("Unsupported UTC timing scheme"));
        }
    }

    private void resolveUtcTimingElementDirect(UtcTimingElement utcTimingElement) {
        try {
            onUtcTimestampResolved(Util.parseXsDateTime(utcTimingElement.value) - this.manifestLoadEndTimestampMs);
        } catch (ParserException e) {
            onUtcTimestampResolutionError(e);
        }
    }

    private void resolveUtcTimingElementHttp(UtcTimingElement utcTimingElement, ParsingLoadable.Parser<Long> parser) {
        startLoading(new ParsingLoadable(this.dataSource, Uri.parse(utcTimingElement.value), 5, parser), new UtcTimestampCallback(), 1);
    }

    private void loadNtpTimeOffset() {
        SntpClient.initialize(this.loader, new SntpClient.InitializationCallback() { // from class: androidx.media3.exoplayer.dash.DashMediaSource.1
            @Override // androidx.media3.exoplayer.util.SntpClient.InitializationCallback
            public void onInitialized() {
                DashMediaSource.this.onUtcTimestampResolved(SntpClient.getElapsedRealtimeOffsetMs());
            }

            @Override // androidx.media3.exoplayer.util.SntpClient.InitializationCallback
            public void onInitializationFailed(IOException iOException) {
                DashMediaSource.this.onUtcTimestampResolutionError(iOException);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUtcTimestampResolved(long j) {
        this.elapsedRealtimeOffsetMs = j;
        processManifest(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUtcTimestampResolutionError(IOException iOException) {
        Log.e("DashMediaSource", "Failed to resolve time offset.", iOException);
        processManifest(true);
    }

    private void processManifest(boolean z) {
        long j;
        long j2;
        for (int i = 0; i < this.periodsById.size(); i++) {
            int keyAt = this.periodsById.keyAt(i);
            if (keyAt >= this.firstPeriodId) {
                this.periodsById.valueAt(i).updateManifest(this.manifest, keyAt - this.firstPeriodId);
            }
        }
        Period period = this.manifest.getPeriod(0);
        int periodCount = this.manifest.getPeriodCount() - 1;
        Period period2 = this.manifest.getPeriod(periodCount);
        long periodDurationUs = this.manifest.getPeriodDurationUs(periodCount);
        long msToUs = Util.msToUs(Util.getNowUnixTimeMs(this.elapsedRealtimeOffsetMs));
        long availableStartTimeInManifestUs = getAvailableStartTimeInManifestUs(period, this.manifest.getPeriodDurationUs(0), msToUs);
        long availableEndTimeInManifestUs = getAvailableEndTimeInManifestUs(period2, periodDurationUs, msToUs);
        boolean z2 = this.manifest.dynamic && !isIndexExplicit(period2);
        if (z2 && this.manifest.timeShiftBufferDepthMs != -9223372036854775807L) {
            availableStartTimeInManifestUs = Math.max(availableStartTimeInManifestUs, availableEndTimeInManifestUs - Util.msToUs(this.manifest.timeShiftBufferDepthMs));
        }
        long j3 = availableEndTimeInManifestUs - availableStartTimeInManifestUs;
        if (this.manifest.dynamic) {
            Assertions.checkState(this.manifest.availabilityStartTimeMs != -9223372036854775807L);
            long msToUs2 = (msToUs - Util.msToUs(this.manifest.availabilityStartTimeMs)) - availableStartTimeInManifestUs;
            updateLiveConfiguration(msToUs2, j3);
            long msToUs3 = msToUs2 - Util.msToUs(this.liveConfiguration.targetOffsetMs);
            j = this.manifest.availabilityStartTimeMs + Util.usToMs(availableStartTimeInManifestUs);
            long min = Math.min(this.minLiveStartPositionUs, j3 / 2);
            j2 = msToUs3 < min ? min : msToUs3;
        } else {
            j = -9223372036854775807L;
            j2 = 0;
        }
        refreshSourceInfo(new DashTimeline(this.manifest.availabilityStartTimeMs, j, this.elapsedRealtimeOffsetMs, this.firstPeriodId, availableStartTimeInManifestUs - Util.msToUs(period.startMs), j3, j2, this.manifest, getMediaItem(), this.manifest.dynamic ? this.liveConfiguration : null));
        if (this.sideloadedManifest) {
            return;
        }
        this.handler.removeCallbacks(this.simulateManifestRefreshRunnable);
        if (z2) {
            this.handler.postDelayed(this.simulateManifestRefreshRunnable, getIntervalUntilNextManifestRefreshMs(this.manifest, Util.getNowUnixTimeMs(this.elapsedRealtimeOffsetMs)));
        }
        if (this.manifestLoadPending) {
            startLoadingManifest();
        } else if (z && this.manifest.dynamic && this.manifest.minUpdatePeriodMs != -9223372036854775807L) {
            long j4 = this.manifest.minUpdatePeriodMs;
            if (j4 == 0) {
                j4 = 5000;
            }
            scheduleManifestRefresh(Math.max(0L, (this.manifestLoadStartTimestampMs + j4) - SystemClock.elapsedRealtime()));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0102  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void updateLiveConfiguration(long r19, long r21) {
        /*
            Method dump skipped, instructions count: 334
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.exoplayer.dash.DashMediaSource.updateLiveConfiguration(long, long):void");
    }

    private void scheduleManifestRefresh(long j) {
        this.handler.postDelayed(this.refreshManifestRunnable, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startLoadingManifest() {
        Uri uri;
        this.handler.removeCallbacks(this.refreshManifestRunnable);
        if (this.loader.hasFatalError()) {
            return;
        }
        if (this.loader.isLoading()) {
            this.manifestLoadPending = true;
            return;
        }
        synchronized (this.manifestUriLock) {
            uri = this.manifestUri;
        }
        this.manifestLoadPending = false;
        startLoading(new ParsingLoadable(this.dataSource, uri, 4, this.manifestParser), this.manifestCallback, this.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(4));
    }

    private long getManifestLoadRetryDelayMillis() {
        return Math.min((this.staleManifestReloadAttempt - 1) * 1000, 5000);
    }

    private <T> void startLoading(ParsingLoadable<T> parsingLoadable, Loader.Callback<ParsingLoadable<T>> callback, int i) {
        this.manifestEventDispatcher.loadStarted(new LoadEventInfo(parsingLoadable.loadTaskId, parsingLoadable.dataSpec, this.loader.startLoading(parsingLoadable, callback, i)), parsingLoadable.type);
    }

    private static long getIntervalUntilNextManifestRefreshMs(DashManifest dashManifest, long j) {
        DashSegmentIndex index;
        int periodCount = dashManifest.getPeriodCount() - 1;
        Period period = dashManifest.getPeriod(periodCount);
        long msToUs = Util.msToUs(period.startMs);
        long periodDurationUs = dashManifest.getPeriodDurationUs(periodCount);
        long msToUs2 = Util.msToUs(j);
        long msToUs3 = Util.msToUs(dashManifest.availabilityStartTimeMs);
        long msToUs4 = Util.msToUs(5000L);
        for (int i = 0; i < period.adaptationSets.size(); i++) {
            List<Representation> list = period.adaptationSets.get(i).representations;
            if (!list.isEmpty() && (index = list.get(0).getIndex()) != null) {
                long nextSegmentAvailableTimeUs = ((msToUs3 + msToUs) + index.getNextSegmentAvailableTimeUs(periodDurationUs, msToUs2)) - msToUs2;
                if (nextSegmentAvailableTimeUs < msToUs4 - SilenceSkippingAudioProcessor.DEFAULT_MINIMUM_SILENCE_DURATION_US || (nextSegmentAvailableTimeUs > msToUs4 && nextSegmentAvailableTimeUs < msToUs4 + SilenceSkippingAudioProcessor.DEFAULT_MINIMUM_SILENCE_DURATION_US)) {
                    msToUs4 = nextSegmentAvailableTimeUs;
                }
            }
        }
        return LongMath.divide(msToUs4, 1000L, RoundingMode.CEILING);
    }

    private static long getAvailableStartTimeInManifestUs(Period period, long j, long j2) {
        long msToUs = Util.msToUs(period.startMs);
        boolean hasVideoOrAudioAdaptationSets = hasVideoOrAudioAdaptationSets(period);
        long j3 = msToUs;
        for (int i = 0; i < period.adaptationSets.size(); i++) {
            AdaptationSet adaptationSet = period.adaptationSets.get(i);
            List<Representation> list = adaptationSet.representations;
            boolean z = true;
            z = (adaptationSet.type == 1 || adaptationSet.type == 2) ? false : false;
            if ((!hasVideoOrAudioAdaptationSets || !z) && !list.isEmpty()) {
                DashSegmentIndex index = list.get(0).getIndex();
                if (index == null || index.getAvailableSegmentCount(j, j2) == 0) {
                    return msToUs;
                }
                j3 = Math.max(j3, index.getTimeUs(index.getFirstAvailableSegmentNum(j, j2)) + msToUs);
            }
        }
        return j3;
    }

    private static long getAvailableEndTimeInManifestUs(Period period, long j, long j2) {
        long msToUs = Util.msToUs(period.startMs);
        boolean hasVideoOrAudioAdaptationSets = hasVideoOrAudioAdaptationSets(period);
        long j3 = Long.MAX_VALUE;
        for (int i = 0; i < period.adaptationSets.size(); i++) {
            AdaptationSet adaptationSet = period.adaptationSets.get(i);
            List<Representation> list = adaptationSet.representations;
            boolean z = true;
            z = (adaptationSet.type == 1 || adaptationSet.type == 2) ? false : false;
            if ((!hasVideoOrAudioAdaptationSets || !z) && !list.isEmpty()) {
                DashSegmentIndex index = list.get(0).getIndex();
                if (index == null) {
                    return msToUs + j;
                }
                long availableSegmentCount = index.getAvailableSegmentCount(j, j2);
                if (availableSegmentCount == 0) {
                    return msToUs;
                }
                long firstAvailableSegmentNum = (index.getFirstAvailableSegmentNum(j, j2) + availableSegmentCount) - 1;
                j3 = Math.min(j3, index.getDurationUs(firstAvailableSegmentNum, j) + index.getTimeUs(firstAvailableSegmentNum) + msToUs);
            }
        }
        return j3;
    }

    private static boolean isIndexExplicit(Period period) {
        for (int i = 0; i < period.adaptationSets.size(); i++) {
            DashSegmentIndex index = period.adaptationSets.get(i).representations.get(0).getIndex();
            if (index == null || index.isExplicit()) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasVideoOrAudioAdaptationSets(Period period) {
        for (int i = 0; i < period.adaptationSets.size(); i++) {
            int i2 = period.adaptationSets.get(i).type;
            if (i2 == 1 || i2 == 2) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class DashTimeline extends Timeline {
        private final long elapsedRealtimeEpochOffsetMs;
        private final int firstPeriodId;
        private final MediaItem.LiveConfiguration liveConfiguration;
        private final DashManifest manifest;
        private final MediaItem mediaItem;
        private final long offsetInFirstPeriodUs;
        private final long presentationStartTimeMs;
        private final long windowDefaultStartPositionUs;
        private final long windowDurationUs;
        private final long windowStartTimeMs;

        @Override // androidx.media3.common.Timeline
        public int getWindowCount() {
            return 1;
        }

        public DashTimeline(long j, long j2, long j3, int i, long j4, long j5, long j6, DashManifest dashManifest, MediaItem mediaItem, MediaItem.LiveConfiguration liveConfiguration) {
            Assertions.checkState(dashManifest.dynamic == (liveConfiguration != null));
            this.presentationStartTimeMs = j;
            this.windowStartTimeMs = j2;
            this.elapsedRealtimeEpochOffsetMs = j3;
            this.firstPeriodId = i;
            this.offsetInFirstPeriodUs = j4;
            this.windowDurationUs = j5;
            this.windowDefaultStartPositionUs = j6;
            this.manifest = dashManifest;
            this.mediaItem = mediaItem;
            this.liveConfiguration = liveConfiguration;
        }

        @Override // androidx.media3.common.Timeline
        public int getPeriodCount() {
            return this.manifest.getPeriodCount();
        }

        @Override // androidx.media3.common.Timeline
        public Timeline.Period getPeriod(int i, Timeline.Period period, boolean z) {
            Assertions.checkIndex(i, 0, getPeriodCount());
            return period.set(z ? this.manifest.getPeriod(i).id : null, z ? Integer.valueOf(this.firstPeriodId + i) : null, 0, this.manifest.getPeriodDurationUs(i), Util.msToUs(this.manifest.getPeriod(i).startMs - this.manifest.getPeriod(0).startMs) - this.offsetInFirstPeriodUs);
        }

        @Override // androidx.media3.common.Timeline
        public Timeline.Window getWindow(int i, Timeline.Window window, long j) {
            Assertions.checkIndex(i, 0, 1);
            long adjustedWindowDefaultStartPositionUs = getAdjustedWindowDefaultStartPositionUs(j);
            Object obj = Timeline.Window.SINGLE_WINDOW_UID;
            MediaItem mediaItem = this.mediaItem;
            DashManifest dashManifest = this.manifest;
            return window.set(obj, mediaItem, dashManifest, this.presentationStartTimeMs, this.windowStartTimeMs, this.elapsedRealtimeEpochOffsetMs, true, isMovingLiveWindow(dashManifest), this.liveConfiguration, adjustedWindowDefaultStartPositionUs, this.windowDurationUs, 0, getPeriodCount() - 1, this.offsetInFirstPeriodUs);
        }

        @Override // androidx.media3.common.Timeline
        public int getIndexOfPeriod(Object obj) {
            int intValue;
            if ((obj instanceof Integer) && (intValue = ((Integer) obj).intValue() - this.firstPeriodId) >= 0 && intValue < getPeriodCount()) {
                return intValue;
            }
            return -1;
        }

        private long getAdjustedWindowDefaultStartPositionUs(long j) {
            DashSegmentIndex index;
            long j2 = this.windowDefaultStartPositionUs;
            if (isMovingLiveWindow(this.manifest)) {
                if (j > 0) {
                    j2 += j;
                    if (j2 > this.windowDurationUs) {
                        return -9223372036854775807L;
                    }
                }
                long j3 = this.offsetInFirstPeriodUs + j2;
                long periodDurationUs = this.manifest.getPeriodDurationUs(0);
                int i = 0;
                while (i < this.manifest.getPeriodCount() - 1 && j3 >= periodDurationUs) {
                    j3 -= periodDurationUs;
                    i++;
                    periodDurationUs = this.manifest.getPeriodDurationUs(i);
                }
                Period period = this.manifest.getPeriod(i);
                int adaptationSetIndex = period.getAdaptationSetIndex(2);
                return (adaptationSetIndex == -1 || (index = period.adaptationSets.get(adaptationSetIndex).representations.get(0).getIndex()) == null || index.getSegmentCount(periodDurationUs) == 0) ? j2 : (j2 + index.getTimeUs(index.getSegmentNum(j3, periodDurationUs))) - j3;
            }
            return j2;
        }

        @Override // androidx.media3.common.Timeline
        public Object getUidOfPeriod(int i) {
            Assertions.checkIndex(i, 0, getPeriodCount());
            return Integer.valueOf(this.firstPeriodId + i);
        }

        private static boolean isMovingLiveWindow(DashManifest dashManifest) {
            return dashManifest.dynamic && dashManifest.minUpdatePeriodMs != -9223372036854775807L && dashManifest.durationMs == -9223372036854775807L;
        }
    }

    /* loaded from: classes.dex */
    private final class DefaultPlayerEmsgCallback implements PlayerEmsgHandler.PlayerEmsgCallback {
        private DefaultPlayerEmsgCallback() {
        }

        @Override // androidx.media3.exoplayer.dash.PlayerEmsgHandler.PlayerEmsgCallback
        public void onDashManifestRefreshRequested() {
            DashMediaSource.this.onDashManifestRefreshRequested();
        }

        @Override // androidx.media3.exoplayer.dash.PlayerEmsgHandler.PlayerEmsgCallback
        public void onDashManifestPublishTimeExpired(long j) {
            DashMediaSource.this.onDashManifestPublishTimeExpired(j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public final class ManifestCallback implements Loader.Callback<ParsingLoadable<DashManifest>> {
        private ManifestCallback() {
        }

        @Override // androidx.media3.exoplayer.upstream.Loader.Callback
        public void onLoadCompleted(ParsingLoadable<DashManifest> parsingLoadable, long j, long j2) {
            DashMediaSource.this.onManifestLoadCompleted(parsingLoadable, j, j2);
        }

        @Override // androidx.media3.exoplayer.upstream.Loader.Callback
        public void onLoadCanceled(ParsingLoadable<DashManifest> parsingLoadable, long j, long j2, boolean z) {
            DashMediaSource.this.onLoadCanceled(parsingLoadable, j, j2);
        }

        @Override // androidx.media3.exoplayer.upstream.Loader.Callback
        public Loader.LoadErrorAction onLoadError(ParsingLoadable<DashManifest> parsingLoadable, long j, long j2, IOException iOException, int i) {
            return DashMediaSource.this.onManifestLoadError(parsingLoadable, j, j2, iOException, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public final class UtcTimestampCallback implements Loader.Callback<ParsingLoadable<Long>> {
        private UtcTimestampCallback() {
        }

        @Override // androidx.media3.exoplayer.upstream.Loader.Callback
        public void onLoadCompleted(ParsingLoadable<Long> parsingLoadable, long j, long j2) {
            DashMediaSource.this.onUtcTimestampLoadCompleted(parsingLoadable, j, j2);
        }

        @Override // androidx.media3.exoplayer.upstream.Loader.Callback
        public void onLoadCanceled(ParsingLoadable<Long> parsingLoadable, long j, long j2, boolean z) {
            DashMediaSource.this.onLoadCanceled(parsingLoadable, j, j2);
        }

        @Override // androidx.media3.exoplayer.upstream.Loader.Callback
        public Loader.LoadErrorAction onLoadError(ParsingLoadable<Long> parsingLoadable, long j, long j2, IOException iOException, int i) {
            return DashMediaSource.this.onUtcTimestampLoadError(parsingLoadable, j, j2, iOException);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class XsDateTimeParser implements ParsingLoadable.Parser<Long> {
        private XsDateTimeParser() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.media3.exoplayer.upstream.ParsingLoadable.Parser
        public Long parse(Uri uri, InputStream inputStream) throws IOException {
            return Long.valueOf(Util.parseXsDateTime(new BufferedReader(new InputStreamReader(inputStream)).readLine()));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class Iso8601Parser implements ParsingLoadable.Parser<Long> {
        private static final Pattern TIMESTAMP_WITH_TIMEZONE_PATTERN = Pattern.compile("(.+?)(Z|((\\+|-|−)(\\d\\d)(:?(\\d\\d))?))");

        Iso8601Parser() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.media3.exoplayer.upstream.ParsingLoadable.Parser
        public Long parse(Uri uri, InputStream inputStream) throws IOException {
            String readLine = new BufferedReader(new InputStreamReader(inputStream, Charsets.UTF_8)).readLine();
            try {
                Matcher matcher = TIMESTAMP_WITH_TIMEZONE_PATTERN.matcher(readLine);
                if (!matcher.matches()) {
                    throw ParserException.createForMalformedManifest("Couldn't parse timestamp: " + readLine, null);
                }
                String group = matcher.group(1);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                simpleDateFormat.setTimeZone(DesugarTimeZone.getTimeZone("UTC"));
                long time = simpleDateFormat.parse(group).getTime();
                if (!"Z".equals(matcher.group(2))) {
                    long j = "+".equals(matcher.group(4)) ? 1L : -1L;
                    long parseLong = Long.parseLong(matcher.group(5));
                    String group2 = matcher.group(7);
                    time -= j * ((((parseLong * 60) + (TextUtils.isEmpty(group2) ? 0L : Long.parseLong(group2))) * 60) * 1000);
                }
                return Long.valueOf(time);
            } catch (ParseException e) {
                throw ParserException.createForMalformedManifest(null, e);
            }
        }
    }

    /* loaded from: classes.dex */
    final class ManifestLoadErrorThrower implements LoaderErrorThrower {
        ManifestLoadErrorThrower() {
        }

        @Override // androidx.media3.exoplayer.upstream.LoaderErrorThrower
        public void maybeThrowError() throws IOException {
            DashMediaSource.this.loader.maybeThrowError();
            maybeThrowManifestError();
        }

        @Override // androidx.media3.exoplayer.upstream.LoaderErrorThrower
        public void maybeThrowError(int i) throws IOException {
            DashMediaSource.this.loader.maybeThrowError(i);
            maybeThrowManifestError();
        }

        private void maybeThrowManifestError() throws IOException {
            if (DashMediaSource.this.manifestFatalError != null) {
                throw DashMediaSource.this.manifestFatalError;
            }
        }
    }
}
