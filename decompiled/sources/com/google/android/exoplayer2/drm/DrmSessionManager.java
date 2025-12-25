package com.google.android.exoplayer2.drm;

import android.os.Looper;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
/* loaded from: classes3.dex */
public interface DrmSessionManager {
    public static final DrmSessionManager DRM_UNSUPPORTED;
    @Deprecated
    public static final DrmSessionManager DUMMY;

    DrmSession acquireSession(Looper looper, DrmSessionEventListener.EventDispatcher eventDispatcher, Format format);

    Class<? extends ExoMediaCrypto> getExoMediaCryptoType(Format format);

    void prepare();

    void release();

    static {
        DrmSessionManager drmSessionManager = new DrmSessionManager() { // from class: com.google.android.exoplayer2.drm.DrmSessionManager.1
            @Override // com.google.android.exoplayer2.drm.DrmSessionManager
            public /* synthetic */ void prepare() {
                CC.$default$prepare(this);
            }

            @Override // com.google.android.exoplayer2.drm.DrmSessionManager
            public /* synthetic */ void release() {
                CC.$default$release(this);
            }

            @Override // com.google.android.exoplayer2.drm.DrmSessionManager
            public DrmSession acquireSession(Looper looper, DrmSessionEventListener.EventDispatcher eventDispatcher, Format format) {
                if (format.drmInitData == null) {
                    return null;
                }
                return new ErrorStateDrmSession(new DrmSession.DrmSessionException(new UnsupportedDrmException(1)));
            }

            @Override // com.google.android.exoplayer2.drm.DrmSessionManager
            public Class<UnsupportedMediaCrypto> getExoMediaCryptoType(Format format) {
                if (format.drmInitData != null) {
                    return UnsupportedMediaCrypto.class;
                }
                return null;
            }
        };
        DRM_UNSUPPORTED = drmSessionManager;
        DUMMY = drmSessionManager;
    }

    /* renamed from: com.google.android.exoplayer2.drm.DrmSessionManager$-CC  reason: invalid class name */
    /* loaded from: classes3.dex */
    public final /* synthetic */ class CC {
        public static void $default$prepare(DrmSessionManager _this) {
        }

        public static void $default$release(DrmSessionManager _this) {
        }

        static {
            DrmSessionManager drmSessionManager = DrmSessionManager.DRM_UNSUPPORTED;
        }

        @Deprecated
        public static DrmSessionManager getDummyDrmSessionManager() {
            return DrmSessionManager.DRM_UNSUPPORTED;
        }
    }
}
