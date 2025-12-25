package androidx.media3.decoder.ffmpeg;

import androidx.media3.common.MediaLibraryInfo;
import androidx.media3.common.util.LibraryLoader;
import androidx.media3.common.util.Log;
/* loaded from: classes.dex */
public final class FfmpegLibrary {
    private static final LibraryLoader LOADER;
    private static final String TAG = "FfmpegLibrary";
    private static int inputBufferPaddingSize;
    private static String version;

    private static native int ffmpegGetInputBufferPaddingSize();

    private static native String ffmpegGetVersion();

    private static native boolean ffmpegHasDecoder(String str);

    static {
        MediaLibraryInfo.registerModule("media3.decoder.ffmpeg");
        LOADER = new LibraryLoader("ffmpegJNI") { // from class: androidx.media3.decoder.ffmpeg.FfmpegLibrary.1
            @Override // androidx.media3.common.util.LibraryLoader
            protected void loadLibrary(String str) {
                System.loadLibrary(str);
            }
        };
        inputBufferPaddingSize = -1;
    }

    private FfmpegLibrary() {
    }

    public static void setLibraries(String... strArr) {
        LOADER.setLibraries(strArr);
    }

    public static boolean isAvailable() {
        return LOADER.isAvailable();
    }

    public static String getVersion() {
        if (isAvailable()) {
            if (version == null) {
                version = ffmpegGetVersion();
            }
            return version;
        }
        return null;
    }

    public static int getInputBufferPaddingSize() {
        if (isAvailable()) {
            if (inputBufferPaddingSize == -1) {
                inputBufferPaddingSize = ffmpegGetInputBufferPaddingSize();
            }
            return inputBufferPaddingSize;
        }
        return -1;
    }

    public static boolean supportsFormat(String str) {
        String codecName;
        if (isAvailable() && (codecName = getCodecName(str)) != null) {
            if (ffmpegHasDecoder(codecName)) {
                return true;
            }
            Log.w(TAG, "No " + codecName + " decoder available. Check the FFmpeg build configuration.");
            return false;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String getCodecName(String str) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2123537834:
                if (str.equals("audio/eac3-joc")) {
                    c = 0;
                    break;
                }
                break;
            case -1662541442:
                if (str.equals("video/hevc")) {
                    c = 1;
                    break;
                }
                break;
            case -1606874997:
                if (str.equals("audio/amr-wb")) {
                    c = 2;
                    break;
                }
                break;
            case -1095064472:
                if (str.equals("audio/vnd.dts")) {
                    c = 3;
                    break;
                }
                break;
            case -1003765268:
                if (str.equals("audio/vorbis")) {
                    c = 4;
                    break;
                }
                break;
            case -432837260:
                if (str.equals("audio/mpeg-L1")) {
                    c = 5;
                    break;
                }
                break;
            case -432837259:
                if (str.equals("audio/mpeg-L2")) {
                    c = 6;
                    break;
                }
                break;
            case -53558318:
                if (str.equals("audio/mp4a-latm")) {
                    c = 7;
                    break;
                }
                break;
            case 187078296:
                if (str.equals("audio/ac3")) {
                    c = '\b';
                    break;
                }
                break;
            case 1331836730:
                if (str.equals("video/avc")) {
                    c = '\t';
                    break;
                }
                break;
            case 1503095341:
                if (str.equals("audio/3gpp")) {
                    c = '\n';
                    break;
                }
                break;
            case 1504470054:
                if (str.equals("audio/alac")) {
                    c = 11;
                    break;
                }
                break;
            case 1504578661:
                if (str.equals("audio/eac3")) {
                    c = '\f';
                    break;
                }
                break;
            case 1504619009:
                if (str.equals("audio/flac")) {
                    c = '\r';
                    break;
                }
                break;
            case 1504831518:
                if (str.equals("audio/mpeg")) {
                    c = 14;
                    break;
                }
                break;
            case 1504891608:
                if (str.equals("audio/opus")) {
                    c = 15;
                    break;
                }
                break;
            case 1505942594:
                if (str.equals("audio/vnd.dts.hd")) {
                    c = 16;
                    break;
                }
                break;
            case 1556697186:
                if (str.equals("audio/true-hd")) {
                    c = 17;
                    break;
                }
                break;
            case 1903231877:
                if (str.equals("audio/g711-alaw")) {
                    c = 18;
                    break;
                }
                break;
            case 1903589369:
                if (str.equals("audio/g711-mlaw")) {
                    c = 19;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case '\f':
                return "eac3";
            case 1:
                return "hevc";
            case 2:
                return "amrwb";
            case 3:
            case 16:
                return "dca";
            case 4:
                return "vorbis";
            case 5:
            case 6:
            case 14:
                return "mp3";
            case 7:
                return "aac";
            case '\b':
                return "ac3";
            case '\t':
                return "h264";
            case '\n':
                return "amrnb";
            case 11:
                return "alac";
            case '\r':
                return "flac";
            case 15:
                return "opus";
            case 17:
                return "truehd";
            case 18:
                return "pcm_alaw";
            case 19:
                return "pcm_mulaw";
            default:
                return null;
        }
    }
}
