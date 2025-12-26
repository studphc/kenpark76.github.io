package com.tvhome.app.models;

import android.net.Uri;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.media3.common.MediaItem;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.exoplayer.dash.DashMediaSource;
import androidx.media3.exoplayer.hls.HlsMediaSource;
import androidx.media3.exoplayer.rtsp.RtspMediaSource;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.ProgressiveMediaSource;
import com.tvhome.app.SP;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
/* compiled from: TVModel.kt */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000f\u0018\u0000 ]2\u00020\u0001:\u0001]B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010I\u001a\u00020J2\u0006\u0010K\u001a\u00020@H\u0002J\b\u0010L\u001a\u00020JH\u0007J\u0006\u0010M\u001a\u00020JJ\n\u0010N\u001a\u0004\u0018\u00010OH\u0007J\u0006\u0010P\u001a\u00020@J\u0006\u0010Q\u001a\u00020@J\n\u0010H\u001a\u0004\u0018\u00010\nH\u0002J\u0006\u0010R\u001a\u00020JJ\u0014\u0010S\u001a\u00020J2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\b0\u0007J\u000e\u0010T\u001a\u00020J2\u0006\u0010U\u001a\u00020\nJ\u000e\u0010V\u001a\u00020J2\u0006\u0010W\u001a\u00020\fJ\u0006\u0010X\u001a\u00020JJ\u000e\u0010Y\u001a\u00020J2\u0006\u0010Z\u001a\u00020\nJ\u000e\u0010[\u001a\u00020J2\u0006\u0010\\\u001a\u00020\u0003R\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u00070\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\f0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\n0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00158F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u00158F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0017R\u001a\u0010\u001a\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u000e\u0010\u001f\u001a\u00020 X\u0082.¢\u0006\u0002\n\u0000R\u0017\u0010!\u001a\b\u0012\u0004\u0012\u00020\f0\u00158F¢\u0006\u0006\u001a\u0004\b\"\u0010\u0017R\u001a\u0010#\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u001c\"\u0004\b%\u0010\u001eR\u001a\u0010&\u001a\u00020'X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u0017\u0010,\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00158F¢\u0006\u0006\u001a\u0004\b-\u0010\u0017R\u001d\u0010.\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u00070\u00158F¢\u0006\u0006\u001a\u0004\b/\u0010\u0017R\u001a\u00100\u001a\u000201X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u00103\"\u0004\b4\u00105R\u0017\u00106\u001a\b\u0012\u0004\u0012\u00020\f0\u00158F¢\u0006\u0006\u001a\u0004\b7\u0010\u0017R\u001a\u00108\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010\u001c\"\u0004\b:\u0010\u001eR\u001a\u0010;\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010\u001c\"\u0004\b=\u0010\u001eR\u000e\u0010>\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010?\u001a\b\u0012\u0004\u0012\u00020@0\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bA\u0010B\"\u0004\bC\u0010\u0004R\u000e\u0010D\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010E\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00158BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bF\u0010\u0017R\u0017\u0010G\u001a\b\u0012\u0004\u0012\u00020\n0\u00158F¢\u0006\u0006\u001a\u0004\bH\u0010\u0017¨\u0006^"}, d2 = {"Lcom/pjy/koreatv/models/TVModel;", "Landroidx/lifecycle/ViewModel;", "tv", "Lcom/pjy/koreatv/models/TV;", "(Lcom/pjy/koreatv/models/TV;)V", "_epg", "Landroidx/lifecycle/MutableLiveData;", "", "Lcom/pjy/koreatv/models/EPG;", "_errInfo", "", "_like", "", "_position", "", "_program", "Lcom/pjy/koreatv/models/Program;", "_ready", "_videoIndex", "_videoUrl", "epg", "Landroidx/lifecycle/LiveData;", "getEpg", "()Landroidx/lifecycle/LiveData;", "errInfo", "getErrInfo", "groupIndex", "getGroupIndex", "()I", "setGroupIndex", "(I)V", "httpDataSource", "Landroidx/media3/datasource/DefaultHttpDataSource$Factory;", "like", "getLike", "listIndex", "getListIndex", "setListIndex", "mediaItem", "Landroidx/media3/common/MediaItem;", "getMediaItem", "()Landroidx/media3/common/MediaItem;", "setMediaItem", "(Landroidx/media3/common/MediaItem;)V", "position", "getPosition", "program", "getProgram", "programUpdateTime", "", "getProgramUpdateTime", "()J", "setProgramUpdateTime", "(J)V", "ready", "getReady", "retryMaxTimes", "getRetryMaxTimes", "setRetryMaxTimes", "retryTimes", "getRetryTimes", "setRetryTimes", "sourceIndex", "sources", "Lcom/pjy/koreatv/models/SourceType;", "getTv", "()Lcom/pjy/koreatv/models/TV;", "setTv", "userAgent", "videoIndex", "getVideoIndex", "videoUrl", "getVideoUrl", "addSource", "", "sourceType", "buildSource", "confirmSourceType", "getSource", "Landroidx/media3/exoplayer/source/MediaSource;", "getSourceType", "getSourceTypeCurrent", "nextSource", "setEpg", "setErrInfo", "info", "setLike", "liked", "setReady", "setVideoUrl", "url", "update", "t", "Companion", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class TVModel extends ViewModel {
    public static final Companion Companion = new Companion(null);
    private static final String TAG = "TVModel";
    private MutableLiveData<List<EPG>> _epg;
    private final MutableLiveData<String> _errInfo;
    private final MutableLiveData<Boolean> _like;
    private final MutableLiveData<Integer> _position;
    private MutableLiveData<List<Program>> _program;
    private final MutableLiveData<Boolean> _ready;
    private final MutableLiveData<Integer> _videoIndex;
    private final MutableLiveData<String> _videoUrl;
    private int groupIndex;
    private DefaultHttpDataSource.Factory httpDataSource;
    private int listIndex;
    public MediaItem mediaItem;
    private long programUpdateTime;
    private int retryMaxTimes;
    private int retryTimes;
    private int sourceIndex;
    private List<SourceType> sources;
    private TV tv;
    private String userAgent;

    /* compiled from: TVModel.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SourceType.values().length];
            try {
                iArr[SourceType.HLS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SourceType.RTSP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SourceType.DASH.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[SourceType.PROGRESSIVE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public TVModel(TV tv) {
        Intrinsics.checkNotNullParameter(tv, "tv");
        this.tv = tv;
        MutableLiveData<Integer> mutableLiveData = new MutableLiveData<>();
        this._position = mutableLiveData;
        this.retryMaxTimes = 5;
        this.sources = CollectionsKt.mutableListOf(SourceType.UNKNOWN);
        this.sourceIndex = -1;
        this._errInfo = new MutableLiveData<>();
        this._epg = new MutableLiveData<>();
        this._program = new MutableLiveData<>();
        MutableLiveData<String> mutableLiveData2 = new MutableLiveData<>();
        this._videoUrl = mutableLiveData2;
        MutableLiveData<Boolean> mutableLiveData3 = new MutableLiveData<>();
        this._like = mutableLiveData3;
        this._ready = new MutableLiveData<>();
        MutableLiveData<Integer> mutableLiveData4 = new MutableLiveData<>();
        this._videoIndex = mutableLiveData4;
        this.userAgent = "";
        mutableLiveData.setValue(0);
        mutableLiveData4.setValue(0);
        mutableLiveData3.setValue(Boolean.valueOf(SP.INSTANCE.getLike(this.tv.getName())));
        mutableLiveData2.setValue(getVideoUrl());
        this._program.setValue(new ArrayList());
        buildSource();
    }

    public final TV getTv() {
        return this.tv;
    }

    public final void setTv(TV tv) {
        Intrinsics.checkNotNullParameter(tv, "<set-?>");
        this.tv = tv;
    }

    public final LiveData<Integer> getPosition() {
        return this._position;
    }

    public final int getRetryTimes() {
        return this.retryTimes;
    }

    public final void setRetryTimes(int i) {
        this.retryTimes = i;
    }

    public final int getRetryMaxTimes() {
        return this.retryMaxTimes;
    }

    public final void setRetryMaxTimes(int i) {
        this.retryMaxTimes = i;
    }

    public final long getProgramUpdateTime() {
        return this.programUpdateTime;
    }

    public final void setProgramUpdateTime(long j) {
        this.programUpdateTime = j;
    }

    public final int getGroupIndex() {
        return this.groupIndex;
    }

    public final void setGroupIndex(int i) {
        this.groupIndex = i;
    }

    public final int getListIndex() {
        return this.listIndex;
    }

    public final void setListIndex(int i) {
        this.listIndex = i;
    }

    public final LiveData<String> getErrInfo() {
        return this._errInfo;
    }

    public final void setErrInfo(String info) {
        Intrinsics.checkNotNullParameter(info, "info");
        this._errInfo.setValue(info);
    }

    public final LiveData<List<EPG>> getEpg() {
        return this._epg;
    }

    public final void setEpg(List<EPG> epg) {
        Intrinsics.checkNotNullParameter(epg, "epg");
        this._epg.setValue(epg);
    }

    public final LiveData<List<Program>> getProgram() {
        return this._program;
    }

    /* renamed from: getVideoUrl  reason: collision with other method in class */
    public final LiveData<String> m490getVideoUrl() {
        return this._videoUrl;
    }

    public final void setVideoUrl(String url) {
        Intrinsics.checkNotNullParameter(url, "url");
        this._videoUrl.setValue(url);
    }

    private final String getVideoUrl() {
        if (this._videoIndex.getValue() == null || this.tv.getUris().isEmpty()) {
            return null;
        }
        Integer value = getVideoIndex().getValue();
        Intrinsics.checkNotNull(value);
        if (value.intValue() >= this.tv.getUris().size()) {
            return null;
        }
        List<String> uris = this.tv.getUris();
        Integer value2 = this._videoIndex.getValue();
        Intrinsics.checkNotNull(value2);
        return uris.get(value2.intValue());
    }

    public final LiveData<Boolean> getLike() {
        return this._like;
    }

    public final void setLike(boolean z) {
        this._like.setValue(Boolean.valueOf(z));
    }

    public final LiveData<Boolean> getReady() {
        return this._ready;
    }

    public final void setReady() {
        this._ready.setValue(true);
    }

    private final LiveData<Integer> getVideoIndex() {
        return this._videoIndex;
    }

    public final MediaItem getMediaItem() {
        MediaItem mediaItem = this.mediaItem;
        if (mediaItem != null) {
            return mediaItem;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mediaItem");
        return null;
    }

    public final void setMediaItem(MediaItem mediaItem) {
        Intrinsics.checkNotNullParameter(mediaItem, "<set-?>");
        this.mediaItem = mediaItem;
    }

    public final void update(TV t) {
        Intrinsics.checkNotNullParameter(t, "t");
        this.tv = t;
    }

    public final void buildSource() {
        Uri parse;
        String path;
        String scheme;
        String videoUrl = getVideoUrl();
        if (videoUrl == null || (parse = Uri.parse(videoUrl)) == null || (path = parse.getPath()) == null || (scheme = parse.getScheme()) == null) {
            return;
        }
        DefaultHttpDataSource.Factory factory = new DefaultHttpDataSource.Factory();
        this.httpDataSource = factory;
        factory.setKeepPostFor302Redirects(true);
        DefaultHttpDataSource.Factory factory2 = this.httpDataSource;
        if (factory2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("httpDataSource");
            factory2 = null;
        }
        factory2.setAllowCrossProtocolRedirects(true);
        Map<String, String> headers = this.tv.getHeaders();
        if (headers != null) {
            DefaultHttpDataSource.Factory factory3 = this.httpDataSource;
            if (factory3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("httpDataSource");
                factory3 = null;
            }
            factory3.setDefaultRequestProperties(headers);
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                String value = entry.getValue();
                if (StringsKt.equals(entry.getKey(), "user-agent", true)) {
                    this.userAgent = value;
                }
            }
        }
        MediaItem fromUri = MediaItem.fromUri(parse.toString());
        Intrinsics.checkNotNullExpressionValue(fromUri, "fromUri(...)");
        setMediaItem(fromUri);
        String lowerCase = path.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        if (StringsKt.endsWith$default(lowerCase, ".m3u8", false, 2, (Object) null)) {
            addSource(SourceType.HLS);
        } else {
            String lowerCase2 = path.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase2, "toLowerCase(...)");
            if (StringsKt.endsWith$default(lowerCase2, ".mpd", false, 2, (Object) null)) {
                addSource(SourceType.DASH);
            } else {
                String lowerCase3 = scheme.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase3, "toLowerCase(...)");
                if (Intrinsics.areEqual(lowerCase3, "rtsp")) {
                    addSource(SourceType.RTSP);
                } else {
                    addSource(SourceType.HLS);
                }
            }
        }
        nextSource();
    }

    private final void addSource(SourceType sourceType) {
        this.sources.set(0, sourceType);
        for (SourceType sourceType2 : CollectionsKt.listOf((Object[]) new SourceType[]{SourceType.PROGRESSIVE, SourceType.HLS, SourceType.RTSP, SourceType.DASH, SourceType.UNKNOWN})) {
            if (sourceType2 != sourceType) {
                this.sources.add(sourceType2);
            }
        }
    }

    public final SourceType getSourceType() {
        return this.tv.getSourceType();
    }

    public final SourceType getSourceTypeCurrent() {
        return this.sources.get(this.sourceIndex);
    }

    public final void nextSource() {
        this.sourceIndex = (this.sourceIndex + 1) % this.sources.size();
    }

    public final MediaSource getSource() {
        RtspMediaSource createMediaSource;
        DefaultHttpDataSource.Factory factory = null;
        if (this.sources.isEmpty()) {
            return null;
        }
        int max = Math.max(0, this.sourceIndex);
        this.sourceIndex = max;
        int min = Math.min(max, this.sources.size() - 1);
        this.sourceIndex = min;
        int i = WhenMappings.$EnumSwitchMapping$0[this.sources.get(min).ordinal()];
        if (i == 1) {
            DefaultHttpDataSource.Factory factory2 = this.httpDataSource;
            if (factory2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("httpDataSource");
            } else {
                factory = factory2;
            }
            return new HlsMediaSource.Factory(factory).createMediaSource(getMediaItem());
        } else if (i == 2) {
            if (this.userAgent.length() == 0) {
                createMediaSource = new RtspMediaSource.Factory().createMediaSource(getMediaItem());
            } else {
                createMediaSource = new RtspMediaSource.Factory().setUserAgent(this.userAgent).createMediaSource(getMediaItem());
            }
            return createMediaSource;
        } else if (i == 3) {
            DefaultHttpDataSource.Factory factory3 = this.httpDataSource;
            if (factory3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("httpDataSource");
            } else {
                factory = factory3;
            }
            return new DashMediaSource.Factory(factory).createMediaSource(getMediaItem());
        } else if (i != 4) {
            return null;
        } else {
            DefaultHttpDataSource.Factory factory4 = this.httpDataSource;
            if (factory4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("httpDataSource");
            } else {
                factory = factory4;
            }
            return new ProgressiveMediaSource.Factory(factory).createMediaSource(getMediaItem());
        }
    }

    public final void confirmSourceType() {
        this.tv.setSourceType(this.sources.get(this.sourceIndex));
    }

    /* compiled from: TVModel.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/pjy/koreatv/models/TVModel$Companion;", "", "()V", "TAG", "", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
