package com.google.android.exoplayer2.source.ads;

import android.view.View;
import android.view.ViewGroup;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
/* loaded from: classes3.dex */
public interface AdsLoader {

    /* loaded from: classes3.dex */
    public interface EventListener {

        /* renamed from: com.google.android.exoplayer2.source.ads.AdsLoader$EventListener$-CC  reason: invalid class name */
        /* loaded from: classes3.dex */
        public final /* synthetic */ class CC {
            public static void $default$onAdClicked(EventListener _this) {
            }

            public static void $default$onAdLoadError(EventListener _this, AdsMediaSource.AdLoadException adLoadException, DataSpec dataSpec) {
            }

            public static void $default$onAdPlaybackState(EventListener _this, AdPlaybackState adPlaybackState) {
            }

            public static void $default$onAdTapped(EventListener _this) {
            }
        }

        void onAdClicked();

        void onAdLoadError(AdsMediaSource.AdLoadException adLoadException, DataSpec dataSpec);

        void onAdPlaybackState(AdPlaybackState adPlaybackState);

        void onAdTapped();
    }

    void handlePrepareComplete(AdsMediaSource adsMediaSource, int i, int i2);

    void handlePrepareError(AdsMediaSource adsMediaSource, int i, int i2, IOException iOException);

    void release();

    void setPlayer(Player player);

    void setSupportedContentTypes(int... iArr);

    void start(AdsMediaSource adsMediaSource, DataSpec dataSpec, Object obj, AdViewProvider adViewProvider, EventListener eventListener);

    void stop(AdsMediaSource adsMediaSource, EventListener eventListener);

    /* loaded from: classes3.dex */
    public interface AdViewProvider {
        List<OverlayInfo> getAdOverlayInfos();

        @Deprecated
        View[] getAdOverlayViews();

        ViewGroup getAdViewGroup();

        /* renamed from: com.google.android.exoplayer2.source.ads.AdsLoader$AdViewProvider$-CC  reason: invalid class name */
        /* loaded from: classes3.dex */
        public final /* synthetic */ class CC {
            @Deprecated
            public static View[] $default$getAdOverlayViews(AdViewProvider _this) {
                return new View[0];
            }

            public static List $default$getAdOverlayInfos(AdViewProvider _this) {
                ImmutableList.Builder builder = new ImmutableList.Builder();
                for (View view : _this.getAdOverlayViews()) {
                    builder.add((ImmutableList.Builder) new OverlayInfo(view, 0));
                }
                return builder.build();
            }
        }
    }

    /* loaded from: classes3.dex */
    public static final class OverlayInfo {
        public static final int PURPOSE_CLOSE_AD = 1;
        public static final int PURPOSE_CONTROLS = 0;
        public static final int PURPOSE_NOT_VISIBLE = 3;
        public static final int PURPOSE_OTHER = 2;
        public final int purpose;
        public final String reasonDetail;
        public final View view;

        @Documented
        @Retention(RetentionPolicy.SOURCE)
        /* loaded from: classes3.dex */
        public @interface Purpose {
        }

        public OverlayInfo(View view, int i) {
            this(view, i, null);
        }

        public OverlayInfo(View view, int i, String str) {
            this.view = view;
            this.purpose = i;
            this.reasonDetail = str;
        }
    }
}
