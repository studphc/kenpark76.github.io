package com.google.android.exoplayer2.upstream;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.media.session.PlaybackStateCompat;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.SlidingPercentile;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.zxing.client.result.ExpandedProductParsedResult;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/* loaded from: classes3.dex */
public final class DefaultBandwidthMeter implements BandwidthMeter, TransferListener {
    private static final int BYTES_TRANSFERRED_FOR_ESTIMATE = 524288;
    private static final int COUNTRY_GROUP_INDEX_2G = 1;
    private static final int COUNTRY_GROUP_INDEX_3G = 2;
    private static final int COUNTRY_GROUP_INDEX_4G = 3;
    private static final int COUNTRY_GROUP_INDEX_5G_NSA = 4;
    private static final int COUNTRY_GROUP_INDEX_WIFI = 0;
    public static final long DEFAULT_INITIAL_BITRATE_ESTIMATE = 1000000;
    public static final int DEFAULT_SLIDING_WINDOW_MAX_WEIGHT = 2000;
    private static final int ELAPSED_MILLIS_FOR_ESTIMATE = 2000;
    private static DefaultBandwidthMeter singletonInstance;
    private long bitrateEstimate;
    private final Clock clock;
    private final Context context;
    private final BandwidthMeter.EventListener.EventDispatcher eventDispatcher;
    private final ImmutableMap<Integer, Long> initialBitrateEstimates;
    private long lastReportedBitrateEstimate;
    private int networkType;
    private int networkTypeOverride;
    private boolean networkTypeOverrideSet;
    private long sampleBytesTransferred;
    private long sampleStartTimeMs;
    private final SlidingPercentile slidingPercentile;
    private int streamCount;
    private long totalBytesTransferred;
    private long totalElapsedTimeMs;
    public static final ImmutableListMultimap<String, Integer> DEFAULT_INITIAL_BITRATE_COUNTRY_GROUPS = createInitialBitrateCountryGroupAssignment();
    public static final ImmutableList<Long> DEFAULT_INITIAL_BITRATE_ESTIMATES_WIFI = ImmutableList.of(6100000L, 3800000L, 2100000L, 1300000L, 590000L);
    public static final ImmutableList<Long> DEFAULT_INITIAL_BITRATE_ESTIMATES_2G = ImmutableList.of(218000L, 159000L, 145000L, 130000L, 112000L);
    public static final ImmutableList<Long> DEFAULT_INITIAL_BITRATE_ESTIMATES_3G = ImmutableList.of(2200000L, 1300000L, 930000L, 730000L, 530000L);
    public static final ImmutableList<Long> DEFAULT_INITIAL_BITRATE_ESTIMATES_4G = ImmutableList.of(4800000L, 2700000L, 1800000L, 1200000L, 630000L);
    public static final ImmutableList<Long> DEFAULT_INITIAL_BITRATE_ESTIMATES_5G_NSA = ImmutableList.of(12000000L, 8800000L, 5900000L, 3500000L, 1800000L);

    @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
    public TransferListener getTransferListener() {
        return this;
    }

    @Override // com.google.android.exoplayer2.upstream.TransferListener
    public void onTransferInitializing(DataSource dataSource, DataSpec dataSpec, boolean z) {
    }

    /* loaded from: classes3.dex */
    public static final class Builder {
        private Clock clock;
        private final Context context;
        private Map<Integer, Long> initialBitrateEstimates;
        private boolean resetOnNetworkTypeChange;
        private int slidingWindowMaxWeight;

        public Builder(Context context) {
            this.context = context == null ? null : context.getApplicationContext();
            this.initialBitrateEstimates = getInitialBitrateEstimatesForCountry(Util.getCountryCode(context));
            this.slidingWindowMaxWeight = 2000;
            this.clock = Clock.DEFAULT;
            this.resetOnNetworkTypeChange = true;
        }

        public Builder setSlidingWindowMaxWeight(int i) {
            this.slidingWindowMaxWeight = i;
            return this;
        }

        public Builder setInitialBitrateEstimate(long j) {
            for (Integer num : this.initialBitrateEstimates.keySet()) {
                setInitialBitrateEstimate(num.intValue(), j);
            }
            return this;
        }

        public Builder setInitialBitrateEstimate(int i, long j) {
            this.initialBitrateEstimates.put(Integer.valueOf(i), Long.valueOf(j));
            return this;
        }

        public Builder setInitialBitrateEstimate(String str) {
            this.initialBitrateEstimates = getInitialBitrateEstimatesForCountry(Util.toUpperInvariant(str));
            return this;
        }

        public Builder setClock(Clock clock) {
            this.clock = clock;
            return this;
        }

        public Builder setResetOnNetworkTypeChange(boolean z) {
            this.resetOnNetworkTypeChange = z;
            return this;
        }

        public DefaultBandwidthMeter build() {
            return new DefaultBandwidthMeter(this.context, this.initialBitrateEstimates, this.slidingWindowMaxWeight, this.clock, this.resetOnNetworkTypeChange);
        }

        private static Map<Integer, Long> getInitialBitrateEstimatesForCountry(String str) {
            ImmutableList<Integer> countryGroupIndices = getCountryGroupIndices(str);
            HashMap hashMap = new HashMap(6);
            hashMap.put(0, 1000000L);
            hashMap.put(2, DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_ESTIMATES_WIFI.get(countryGroupIndices.get(0).intValue()));
            hashMap.put(3, DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_ESTIMATES_2G.get(countryGroupIndices.get(1).intValue()));
            hashMap.put(4, DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_ESTIMATES_3G.get(countryGroupIndices.get(2).intValue()));
            hashMap.put(5, DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_ESTIMATES_4G.get(countryGroupIndices.get(3).intValue()));
            hashMap.put(9, DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_ESTIMATES_5G_NSA.get(countryGroupIndices.get(4).intValue()));
            hashMap.put(7, DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_ESTIMATES_WIFI.get(countryGroupIndices.get(0).intValue()));
            return hashMap;
        }

        private static ImmutableList<Integer> getCountryGroupIndices(String str) {
            ImmutableList<Integer> immutableList = DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_COUNTRY_GROUPS.get((ImmutableListMultimap<String, Integer>) str);
            return immutableList.isEmpty() ? ImmutableList.of(2, 2, 2, 2, 2) : immutableList;
        }
    }

    public static synchronized DefaultBandwidthMeter getSingletonInstance(Context context) {
        DefaultBandwidthMeter defaultBandwidthMeter;
        synchronized (DefaultBandwidthMeter.class) {
            if (singletonInstance == null) {
                singletonInstance = new Builder(context).build();
            }
            defaultBandwidthMeter = singletonInstance;
        }
        return defaultBandwidthMeter;
    }

    @Deprecated
    public DefaultBandwidthMeter() {
        this(null, ImmutableMap.of(), 2000, Clock.DEFAULT, false);
    }

    private DefaultBandwidthMeter(Context context, Map<Integer, Long> map, int i, Clock clock, boolean z) {
        this.context = context == null ? null : context.getApplicationContext();
        this.initialBitrateEstimates = ImmutableMap.copyOf((Map) map);
        this.eventDispatcher = new BandwidthMeter.EventListener.EventDispatcher();
        this.slidingPercentile = new SlidingPercentile(i);
        this.clock = clock;
        int networkType = context == null ? 0 : Util.getNetworkType(context);
        this.networkType = networkType;
        this.bitrateEstimate = getInitialBitrateEstimateForNetworkType(networkType);
        if (context == null || !z) {
            return;
        }
        ConnectivityActionReceiver.getInstance(context).register(this);
    }

    public synchronized void setNetworkTypeOverride(int i) {
        this.networkTypeOverride = i;
        this.networkTypeOverrideSet = true;
        onConnectivityAction();
    }

    @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
    public synchronized long getBitrateEstimate() {
        return this.bitrateEstimate;
    }

    @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
    public void addEventListener(Handler handler, BandwidthMeter.EventListener eventListener) {
        Assertions.checkNotNull(handler);
        Assertions.checkNotNull(eventListener);
        this.eventDispatcher.addListener(handler, eventListener);
    }

    @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
    public void removeEventListener(BandwidthMeter.EventListener eventListener) {
        this.eventDispatcher.removeListener(eventListener);
    }

    @Override // com.google.android.exoplayer2.upstream.TransferListener
    public synchronized void onTransferStart(DataSource dataSource, DataSpec dataSpec, boolean z) {
        if (isTransferAtFullNetworkSpeed(dataSpec, z)) {
            if (this.streamCount == 0) {
                this.sampleStartTimeMs = this.clock.elapsedRealtime();
            }
            this.streamCount++;
        }
    }

    @Override // com.google.android.exoplayer2.upstream.TransferListener
    public synchronized void onBytesTransferred(DataSource dataSource, DataSpec dataSpec, boolean z, int i) {
        if (isTransferAtFullNetworkSpeed(dataSpec, z)) {
            this.sampleBytesTransferred += i;
        }
    }

    @Override // com.google.android.exoplayer2.upstream.TransferListener
    public synchronized void onTransferEnd(DataSource dataSource, DataSpec dataSpec, boolean z) {
        if (isTransferAtFullNetworkSpeed(dataSpec, z)) {
            Assertions.checkState(this.streamCount > 0);
            long elapsedRealtime = this.clock.elapsedRealtime();
            int i = (int) (elapsedRealtime - this.sampleStartTimeMs);
            this.totalElapsedTimeMs += i;
            long j = this.totalBytesTransferred;
            long j2 = this.sampleBytesTransferred;
            this.totalBytesTransferred = j + j2;
            if (i > 0) {
                this.slidingPercentile.addSample((int) Math.sqrt(j2), (((float) j2) * 8000.0f) / i);
                if (this.totalElapsedTimeMs >= 2000 || this.totalBytesTransferred >= PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED) {
                    this.bitrateEstimate = this.slidingPercentile.getPercentile(0.5f);
                }
                maybeNotifyBandwidthSample(i, this.sampleBytesTransferred, this.bitrateEstimate);
                this.sampleStartTimeMs = elapsedRealtime;
                this.sampleBytesTransferred = 0L;
            }
            this.streamCount--;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void onConnectivityAction() {
        int networkType;
        if (this.networkTypeOverrideSet) {
            networkType = this.networkTypeOverride;
        } else {
            Context context = this.context;
            networkType = context == null ? 0 : Util.getNetworkType(context);
        }
        if (this.networkType == networkType) {
            return;
        }
        this.networkType = networkType;
        if (networkType != 1 && networkType != 0 && networkType != 8) {
            this.bitrateEstimate = getInitialBitrateEstimateForNetworkType(networkType);
            long elapsedRealtime = this.clock.elapsedRealtime();
            maybeNotifyBandwidthSample(this.streamCount > 0 ? (int) (elapsedRealtime - this.sampleStartTimeMs) : 0, this.sampleBytesTransferred, this.bitrateEstimate);
            this.sampleStartTimeMs = elapsedRealtime;
            this.sampleBytesTransferred = 0L;
            this.totalBytesTransferred = 0L;
            this.totalElapsedTimeMs = 0L;
            this.slidingPercentile.reset();
        }
    }

    private void maybeNotifyBandwidthSample(int i, long j, long j2) {
        if (i == 0 && j == 0 && j2 == this.lastReportedBitrateEstimate) {
            return;
        }
        this.lastReportedBitrateEstimate = j2;
        this.eventDispatcher.bandwidthSample(i, j, j2);
    }

    private long getInitialBitrateEstimateForNetworkType(int i) {
        Long l = this.initialBitrateEstimates.get(Integer.valueOf(i));
        if (l == null) {
            l = this.initialBitrateEstimates.get(0);
        }
        if (l == null) {
            l = 1000000L;
        }
        return l.longValue();
    }

    private static boolean isTransferAtFullNetworkSpeed(DataSpec dataSpec, boolean z) {
        return z && !dataSpec.isFlagSet(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class ConnectivityActionReceiver extends BroadcastReceiver {
        private static ConnectivityActionReceiver staticInstance;
        private final Handler mainHandler = new Handler(Looper.getMainLooper());
        private final ArrayList<WeakReference<DefaultBandwidthMeter>> bandwidthMeters = new ArrayList<>();

        public static synchronized ConnectivityActionReceiver getInstance(Context context) {
            ConnectivityActionReceiver connectivityActionReceiver;
            synchronized (ConnectivityActionReceiver.class) {
                if (staticInstance == null) {
                    staticInstance = new ConnectivityActionReceiver();
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                    context.registerReceiver(staticInstance, intentFilter);
                }
                connectivityActionReceiver = staticInstance;
            }
            return connectivityActionReceiver;
        }

        private ConnectivityActionReceiver() {
        }

        public synchronized void register(final DefaultBandwidthMeter defaultBandwidthMeter) {
            removeClearedReferences();
            this.bandwidthMeters.add(new WeakReference<>(defaultBandwidthMeter));
            this.mainHandler.post(new Runnable() { // from class: com.google.android.exoplayer2.upstream.DefaultBandwidthMeter$ConnectivityActionReceiver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DefaultBandwidthMeter.ConnectivityActionReceiver.this.m435xb89d478f(defaultBandwidthMeter);
                }
            });
        }

        @Override // android.content.BroadcastReceiver
        public synchronized void onReceive(Context context, Intent intent) {
            if (isInitialStickyBroadcast()) {
                return;
            }
            removeClearedReferences();
            for (int i = 0; i < this.bandwidthMeters.size(); i++) {
                DefaultBandwidthMeter defaultBandwidthMeter = this.bandwidthMeters.get(i).get();
                if (defaultBandwidthMeter != null) {
                    m435xb89d478f(defaultBandwidthMeter);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: updateBandwidthMeter */
        public void m435xb89d478f(DefaultBandwidthMeter defaultBandwidthMeter) {
            defaultBandwidthMeter.onConnectivityAction();
        }

        private void removeClearedReferences() {
            for (int size = this.bandwidthMeters.size() - 1; size >= 0; size--) {
                if (this.bandwidthMeters.get(size).get() == null) {
                    this.bandwidthMeters.remove(size);
                }
            }
        }
    }

    private static ImmutableListMultimap<String, Integer> createInitialBitrateCountryGroupAssignment() {
        ImmutableListMultimap.Builder builder = ImmutableListMultimap.builder();
        builder.putAll((ImmutableListMultimap.Builder) "AD", (Object[]) new Integer[]{1, 2, 0, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "AE", (Object[]) new Integer[]{1, 4, 4, 4, 1});
        builder.putAll((ImmutableListMultimap.Builder) "AF", (Object[]) new Integer[]{4, 4, 3, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "AG", (Object[]) new Integer[]{2, 2, 1, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "AI", (Object[]) new Integer[]{1, 2, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "AL", (Object[]) new Integer[]{1, 1, 0, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "AM", (Object[]) new Integer[]{2, 2, 1, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "AO", (Object[]) new Integer[]{3, 4, 4, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "AR", (Object[]) new Integer[]{2, 4, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "AS", (Object[]) new Integer[]{2, 2, 4, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "AT", (Object[]) new Integer[]{0, 3, 0, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "AU", (Object[]) new Integer[]{0, 2, 0, 1, 1});
        builder.putAll((ImmutableListMultimap.Builder) "AW", (Object[]) new Integer[]{1, 2, 0, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "AX", (Object[]) new Integer[]{0, 2, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "AZ", (Object[]) new Integer[]{3, 3, 3, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "BA", (Object[]) new Integer[]{1, 1, 0, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "BB", (Object[]) new Integer[]{0, 2, 0, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "BD", (Object[]) new Integer[]{2, 0, 3, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "BE", (Object[]) new Integer[]{0, 1, 2, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "BF", (Object[]) new Integer[]{4, 4, 4, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "BG", (Object[]) new Integer[]{0, 1, 0, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "BH", (Object[]) new Integer[]{1, 0, 2, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "BI", (Object[]) new Integer[]{4, 4, 4, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "BJ", (Object[]) new Integer[]{4, 4, 3, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "BL", (Object[]) new Integer[]{1, 2, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "BM", (Object[]) new Integer[]{1, 2, 0, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "BN", (Object[]) new Integer[]{4, 0, 1, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "BO", (Object[]) new Integer[]{2, 3, 3, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "BQ", (Object[]) new Integer[]{1, 2, 1, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "BR", (Object[]) new Integer[]{2, 4, 2, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "BS", (Object[]) new Integer[]{3, 2, 2, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "BT", (Object[]) new Integer[]{3, 0, 3, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "BW", (Object[]) new Integer[]{3, 4, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "BY", (Object[]) new Integer[]{1, 0, 2, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "BZ", (Object[]) new Integer[]{2, 2, 2, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "CA", (Object[]) new Integer[]{0, 3, 1, 2, 3});
        builder.putAll((ImmutableListMultimap.Builder) "CD", (Object[]) new Integer[]{4, 3, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "CF", (Object[]) new Integer[]{4, 2, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "CG", (Object[]) new Integer[]{3, 4, 1, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "CH", (Object[]) new Integer[]{0, 1, 0, 0, 0});
        builder.putAll((ImmutableListMultimap.Builder) "CI", (Object[]) new Integer[]{3, 3, 3, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "CK", (Object[]) new Integer[]{3, 2, 1, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "CL", (Object[]) new Integer[]{1, 1, 2, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "CM", (Object[]) new Integer[]{3, 4, 3, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "CN", (Object[]) new Integer[]{2, 2, 2, 1, 3});
        builder.putAll((ImmutableListMultimap.Builder) "CO", (Object[]) new Integer[]{2, 4, 3, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "CR", (Object[]) new Integer[]{2, 3, 4, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "CU", (Object[]) new Integer[]{4, 4, 2, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "CV", (Object[]) new Integer[]{2, 3, 3, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "CW", (Object[]) new Integer[]{1, 2, 0, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "CY", (Object[]) new Integer[]{1, 2, 0, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "CZ", (Object[]) new Integer[]{0, 1, 0, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "DE", (Object[]) new Integer[]{0, 1, 1, 2, 0});
        builder.putAll((ImmutableListMultimap.Builder) "DJ", (Object[]) new Integer[]{4, 1, 4, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "DK", (Object[]) new Integer[]{0, 0, 1, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "DM", (Object[]) new Integer[]{1, 2, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "DO", (Object[]) new Integer[]{3, 4, 4, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "DZ", (Object[]) new Integer[]{3, 2, 4, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "EC", (Object[]) new Integer[]{2, 4, 3, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "EE", (Object[]) new Integer[]{0, 0, 0, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "EG", (Object[]) new Integer[]{3, 4, 2, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "EH", (Object[]) new Integer[]{2, 2, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "ER", (Object[]) new Integer[]{4, 2, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "ES", (Object[]) new Integer[]{0, 1, 2, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "ET", (Object[]) new Integer[]{4, 4, 4, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "FI", (Object[]) new Integer[]{0, 0, 1, 0, 0});
        builder.putAll((ImmutableListMultimap.Builder) "FJ", (Object[]) new Integer[]{3, 0, 3, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "FK", (Object[]) new Integer[]{2, 2, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "FM", (Object[]) new Integer[]{4, 2, 4, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "FO", (Object[]) new Integer[]{0, 2, 0, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "FR", (Object[]) new Integer[]{1, 0, 2, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "GA", (Object[]) new Integer[]{3, 3, 1, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "GB", (Object[]) new Integer[]{0, 0, 1, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "GD", (Object[]) new Integer[]{1, 2, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "GE", (Object[]) new Integer[]{1, 0, 1, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "GF", (Object[]) new Integer[]{2, 2, 2, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "GG", (Object[]) new Integer[]{0, 2, 0, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "GH", (Object[]) new Integer[]{3, 2, 3, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "GI", (Object[]) new Integer[]{0, 2, 0, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "GL", (Object[]) new Integer[]{1, 2, 2, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "GM", (Object[]) new Integer[]{4, 3, 2, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "GN", (Object[]) new Integer[]{4, 3, 4, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "GP", (Object[]) new Integer[]{2, 2, 3, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "GQ", (Object[]) new Integer[]{4, 2, 3, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "GR", (Object[]) new Integer[]{1, 1, 0, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "GT", (Object[]) new Integer[]{3, 2, 3, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "GU", (Object[]) new Integer[]{1, 2, 4, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "GW", (Object[]) new Integer[]{3, 4, 4, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "GY", (Object[]) new Integer[]{3, 3, 1, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "HK", (Object[]) new Integer[]{0, 2, 3, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "HN", (Object[]) new Integer[]{3, 0, 3, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "HR", (Object[]) new Integer[]{1, 1, 0, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "HT", (Object[]) new Integer[]{4, 3, 4, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "HU", (Object[]) new Integer[]{0, 1, 0, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "ID", (Object[]) new Integer[]{3, 2, 2, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "IE", (Object[]) new Integer[]{0, 0, 1, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "IL", (Object[]) new Integer[]{1, 0, 2, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "IM", (Object[]) new Integer[]{0, 2, 0, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "IN", (Object[]) new Integer[]{2, 1, 3, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "IO", (Object[]) new Integer[]{4, 2, 2, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "IQ", (Object[]) new Integer[]{3, 2, 4, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "IR", (Object[]) new Integer[]{4, 2, 3, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "IS", (Object[]) new Integer[]{0, 2, 0, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "IT", (Object[]) new Integer[]{0, 0, 1, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "JE", (Object[]) new Integer[]{2, 2, 0, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "JM", (Object[]) new Integer[]{3, 3, 4, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "JO", (Object[]) new Integer[]{1, 2, 1, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "JP", (Object[]) new Integer[]{0, 2, 0, 1, 3});
        builder.putAll((ImmutableListMultimap.Builder) "KE", (Object[]) new Integer[]{3, 4, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) ExpandedProductParsedResult.KILOGRAM, (Object[]) new Integer[]{1, 0, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "KH", (Object[]) new Integer[]{2, 0, 4, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "KI", (Object[]) new Integer[]{4, 2, 3, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "KM", (Object[]) new Integer[]{4, 2, 2, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "KN", (Object[]) new Integer[]{1, 2, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "KP", (Object[]) new Integer[]{4, 2, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "KR", (Object[]) new Integer[]{0, 2, 1, 1, 1});
        builder.putAll((ImmutableListMultimap.Builder) "KW", (Object[]) new Integer[]{2, 3, 1, 1, 1});
        builder.putAll((ImmutableListMultimap.Builder) "KY", (Object[]) new Integer[]{1, 2, 0, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "KZ", (Object[]) new Integer[]{1, 2, 2, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "LA", (Object[]) new Integer[]{2, 2, 1, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) ExpandedProductParsedResult.POUND, (Object[]) new Integer[]{3, 2, 0, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "LC", (Object[]) new Integer[]{1, 1, 0, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "LI", (Object[]) new Integer[]{0, 2, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "LK", (Object[]) new Integer[]{2, 0, 2, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "LR", (Object[]) new Integer[]{3, 4, 3, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "LS", (Object[]) new Integer[]{3, 3, 2, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "LT", (Object[]) new Integer[]{0, 0, 0, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "LU", (Object[]) new Integer[]{0, 0, 0, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "LV", (Object[]) new Integer[]{0, 0, 0, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "LY", (Object[]) new Integer[]{4, 2, 4, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "MA", (Object[]) new Integer[]{2, 1, 2, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "MC", (Object[]) new Integer[]{0, 2, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "MD", (Object[]) new Integer[]{1, 2, 0, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "ME", (Object[]) new Integer[]{1, 2, 1, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "MF", (Object[]) new Integer[]{1, 2, 1, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "MG", (Object[]) new Integer[]{3, 4, 3, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "MH", (Object[]) new Integer[]{4, 2, 2, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "MK", (Object[]) new Integer[]{1, 0, 0, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "ML", (Object[]) new Integer[]{4, 4, 1, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "MM", (Object[]) new Integer[]{2, 3, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "MN", (Object[]) new Integer[]{2, 4, 1, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "MO", (Object[]) new Integer[]{0, 2, 4, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "MP", (Object[]) new Integer[]{0, 2, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "MQ", (Object[]) new Integer[]{2, 2, 2, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "MR", (Object[]) new Integer[]{3, 0, 4, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "MS", (Object[]) new Integer[]{1, 2, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "MT", (Object[]) new Integer[]{0, 2, 0, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "MU", (Object[]) new Integer[]{3, 1, 2, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "MV", (Object[]) new Integer[]{4, 3, 1, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "MW", (Object[]) new Integer[]{4, 1, 1, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "MX", (Object[]) new Integer[]{2, 4, 3, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "MY", (Object[]) new Integer[]{2, 0, 3, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "MZ", (Object[]) new Integer[]{3, 3, 2, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "NA", (Object[]) new Integer[]{4, 3, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "NC", (Object[]) new Integer[]{2, 0, 4, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "NE", (Object[]) new Integer[]{4, 4, 4, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "NF", (Object[]) new Integer[]{2, 2, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "NG", (Object[]) new Integer[]{3, 3, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "NI", (Object[]) new Integer[]{3, 1, 4, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "NL", (Object[]) new Integer[]{0, 2, 4, 2, 0});
        builder.putAll((ImmutableListMultimap.Builder) "NO", (Object[]) new Integer[]{0, 1, 1, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "NP", (Object[]) new Integer[]{2, 0, 4, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "NR", (Object[]) new Integer[]{4, 2, 3, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "NU", (Object[]) new Integer[]{4, 2, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "NZ", (Object[]) new Integer[]{0, 2, 1, 2, 4});
        builder.putAll((ImmutableListMultimap.Builder) "OM", (Object[]) new Integer[]{2, 2, 0, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "PA", (Object[]) new Integer[]{1, 3, 3, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "PE", (Object[]) new Integer[]{2, 4, 4, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "PF", (Object[]) new Integer[]{2, 2, 1, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "PG", (Object[]) new Integer[]{4, 3, 3, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "PH", (Object[]) new Integer[]{3, 0, 3, 4, 4});
        builder.putAll((ImmutableListMultimap.Builder) "PK", (Object[]) new Integer[]{3, 2, 3, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "PL", (Object[]) new Integer[]{1, 0, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "PM", (Object[]) new Integer[]{0, 2, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "PR", (Object[]) new Integer[]{1, 2, 2, 3, 4});
        builder.putAll((ImmutableListMultimap.Builder) "PS", (Object[]) new Integer[]{3, 3, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "PT", (Object[]) new Integer[]{1, 1, 0, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "PW", (Object[]) new Integer[]{1, 2, 3, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "PY", (Object[]) new Integer[]{2, 0, 3, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "QA", (Object[]) new Integer[]{2, 3, 1, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "RE", (Object[]) new Integer[]{1, 0, 2, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "RO", (Object[]) new Integer[]{1, 1, 1, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "RS", (Object[]) new Integer[]{1, 2, 0, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "RU", (Object[]) new Integer[]{0, 1, 0, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "RW", (Object[]) new Integer[]{4, 3, 3, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "SA", (Object[]) new Integer[]{2, 2, 2, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "SB", (Object[]) new Integer[]{4, 2, 4, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "SC", (Object[]) new Integer[]{4, 2, 0, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "SD", (Object[]) new Integer[]{4, 4, 4, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "SE", (Object[]) new Integer[]{0, 0, 0, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "SG", (Object[]) new Integer[]{0, 0, 3, 3, 4});
        builder.putAll((ImmutableListMultimap.Builder) "SH", (Object[]) new Integer[]{4, 2, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "SI", (Object[]) new Integer[]{0, 1, 0, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "SJ", (Object[]) new Integer[]{2, 2, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "SK", (Object[]) new Integer[]{0, 1, 0, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "SL", (Object[]) new Integer[]{4, 3, 3, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "SM", (Object[]) new Integer[]{0, 2, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "SN", (Object[]) new Integer[]{4, 4, 4, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "SO", (Object[]) new Integer[]{3, 4, 4, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "SR", (Object[]) new Integer[]{3, 2, 3, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "SS", (Object[]) new Integer[]{4, 1, 4, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "ST", (Object[]) new Integer[]{2, 2, 1, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "SV", (Object[]) new Integer[]{2, 1, 4, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "SX", (Object[]) new Integer[]{2, 2, 1, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "SY", (Object[]) new Integer[]{4, 3, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "SZ", (Object[]) new Integer[]{3, 4, 3, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "TC", (Object[]) new Integer[]{1, 2, 1, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "TD", (Object[]) new Integer[]{4, 4, 4, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "TG", (Object[]) new Integer[]{3, 2, 1, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "TH", (Object[]) new Integer[]{1, 3, 4, 3, 0});
        builder.putAll((ImmutableListMultimap.Builder) "TJ", (Object[]) new Integer[]{4, 4, 4, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "TL", (Object[]) new Integer[]{4, 1, 4, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "TM", (Object[]) new Integer[]{4, 2, 1, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "TN", (Object[]) new Integer[]{2, 1, 1, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "TO", (Object[]) new Integer[]{3, 3, 4, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "TR", (Object[]) new Integer[]{1, 2, 1, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "TT", (Object[]) new Integer[]{1, 3, 1, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "TV", (Object[]) new Integer[]{3, 2, 2, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "TW", (Object[]) new Integer[]{0, 0, 0, 0, 1});
        builder.putAll((ImmutableListMultimap.Builder) "TZ", (Object[]) new Integer[]{3, 3, 3, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "UA", (Object[]) new Integer[]{0, 3, 0, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "UG", (Object[]) new Integer[]{3, 2, 2, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "US", (Object[]) new Integer[]{0, 1, 3, 3, 3});
        builder.putAll((ImmutableListMultimap.Builder) "UY", (Object[]) new Integer[]{2, 1, 1, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "UZ", (Object[]) new Integer[]{2, 0, 3, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "VC", (Object[]) new Integer[]{2, 2, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "VE", (Object[]) new Integer[]{4, 4, 4, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "VG", (Object[]) new Integer[]{2, 2, 1, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "VI", (Object[]) new Integer[]{1, 2, 2, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "VN", (Object[]) new Integer[]{0, 1, 4, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "VU", (Object[]) new Integer[]{4, 1, 3, 1, 2});
        builder.putAll((ImmutableListMultimap.Builder) "WS", (Object[]) new Integer[]{3, 1, 4, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "XK", (Object[]) new Integer[]{1, 1, 1, 0, 2});
        builder.putAll((ImmutableListMultimap.Builder) "YE", (Object[]) new Integer[]{4, 4, 4, 4, 2});
        builder.putAll((ImmutableListMultimap.Builder) "YT", (Object[]) new Integer[]{3, 2, 1, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "ZA", (Object[]) new Integer[]{2, 3, 2, 2, 2});
        builder.putAll((ImmutableListMultimap.Builder) "ZM", (Object[]) new Integer[]{3, 2, 2, 3, 2});
        builder.putAll((ImmutableListMultimap.Builder) "ZW", (Object[]) new Integer[]{3, 3, 3, 3, 2});
        return builder.build();
    }
}
