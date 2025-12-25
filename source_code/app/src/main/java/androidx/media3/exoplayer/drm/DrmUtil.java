package androidx.media3.exoplayer.drm;

import android.media.DeniedByServerException;
import android.media.MediaDrm;
import android.media.NotProvisionedException;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.util.Util;
import androidx.media3.common.util.Util$$ExternalSyntheticApiModelOutline0;
import androidx.media3.exoplayer.drm.DefaultDrmSessionManager;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/* loaded from: classes.dex */
public final class DrmUtil {
    public static final int ERROR_SOURCE_EXO_MEDIA_DRM = 1;
    public static final int ERROR_SOURCE_LICENSE_ACQUISITION = 2;
    public static final int ERROR_SOURCE_PROVISIONING = 3;

    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface ErrorSource {
    }

    public static int getErrorCodeForMediaDrmException(Throwable th, int i) {
        if (Util.SDK_INT >= 21 && Api21.isMediaDrmStateException(th)) {
            return Api21.mediaDrmStateExceptionToErrorCode(th);
        }
        if (Util.SDK_INT < 23 || !Api23.isMediaDrmResetException(th)) {
            if ((Util.SDK_INT < 18 || !Api18.isNotProvisionedException(th)) && !isFailureToConstructNotProvisionedException(th)) {
                if (Util.SDK_INT < 18 || !Api18.isDeniedByServerException(th)) {
                    if (th instanceof UnsupportedDrmException) {
                        return PlaybackException.ERROR_CODE_DRM_SCHEME_UNSUPPORTED;
                    }
                    if (Util.SDK_INT < 18 || !Api18.isMissingSchemeDataException(th)) {
                        if (th instanceof KeysExpiredException) {
                            return PlaybackException.ERROR_CODE_DRM_LICENSE_EXPIRED;
                        }
                        if (i == 1) {
                            return PlaybackException.ERROR_CODE_DRM_SYSTEM_ERROR;
                        }
                        if (i == 2) {
                            return PlaybackException.ERROR_CODE_DRM_LICENSE_ACQUISITION_FAILED;
                        }
                        if (i == 3) {
                            return PlaybackException.ERROR_CODE_DRM_PROVISIONING_FAILED;
                        }
                        throw new IllegalArgumentException();
                    }
                    return PlaybackException.ERROR_CODE_DRM_CONTENT_ERROR;
                }
                return PlaybackException.ERROR_CODE_DRM_DEVICE_REVOKED;
            }
            return PlaybackException.ERROR_CODE_DRM_PROVISIONING_FAILED;
        }
        return PlaybackException.ERROR_CODE_DRM_SYSTEM_ERROR;
    }

    public static boolean isFailureToConstructNotProvisionedException(Throwable th) {
        return Util.SDK_INT == 34 && (th instanceof NoSuchMethodError) && th.getMessage() != null && th.getMessage().contains("Landroid/media/NotProvisionedException;.<init>(");
    }

    public static boolean isFailureToConstructResourceBusyException(Throwable th) {
        return Util.SDK_INT == 34 && (th instanceof NoSuchMethodError) && th.getMessage() != null && th.getMessage().contains("Landroid/media/ResourceBusyException;.<init>(");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class Api18 {
        private Api18() {
        }

        public static boolean isNotProvisionedException(Throwable th) {
            return th instanceof NotProvisionedException;
        }

        public static boolean isDeniedByServerException(Throwable th) {
            return th instanceof DeniedByServerException;
        }

        public static boolean isMissingSchemeDataException(Throwable th) {
            return th instanceof DefaultDrmSessionManager.MissingSchemeDataException;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class Api21 {
        private Api21() {
        }

        public static boolean isMediaDrmStateException(Throwable th) {
            return th instanceof MediaDrm.MediaDrmStateException;
        }

        public static int mediaDrmStateExceptionToErrorCode(Throwable th) {
            return Util.getErrorCodeForMediaDrmErrorCode(Util.getErrorCodeFromPlatformDiagnosticsInfo(((MediaDrm.MediaDrmStateException) th).getDiagnosticInfo()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class Api23 {
        private Api23() {
        }

        public static boolean isMediaDrmResetException(Throwable th) {
            return Util$$ExternalSyntheticApiModelOutline0.m170m((Object) th);
        }
    }

    private DrmUtil() {
    }
}
