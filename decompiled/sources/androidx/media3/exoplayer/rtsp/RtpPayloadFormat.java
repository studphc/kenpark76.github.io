package androidx.media3.exoplayer.rtsp;

import androidx.media3.common.Format;
import androidx.media3.common.util.Assertions;
import com.google.common.base.Ascii;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
/* loaded from: classes.dex */
public final class RtpPayloadFormat {
    public static final String RTP_MEDIA_AC3 = "AC3";
    public static final String RTP_MEDIA_AMR = "AMR";
    public static final String RTP_MEDIA_AMR_WB = "AMR-WB";
    public static final String RTP_MEDIA_H263_1998 = "H263-1998";
    public static final String RTP_MEDIA_H263_2000 = "H263-2000";
    public static final String RTP_MEDIA_H264 = "H264";
    public static final String RTP_MEDIA_H265 = "H265";
    public static final String RTP_MEDIA_MPEG4_GENERIC = "MPEG4-GENERIC";
    public static final String RTP_MEDIA_MPEG4_LATM_AUDIO = "MP4A-LATM";
    public static final String RTP_MEDIA_MPEG4_VIDEO = "MP4V-ES";
    public static final String RTP_MEDIA_OPUS = "OPUS";
    public static final String RTP_MEDIA_PCMA = "PCMA";
    public static final String RTP_MEDIA_PCMU = "PCMU";
    public static final String RTP_MEDIA_PCM_L16 = "L16";
    public static final String RTP_MEDIA_PCM_L8 = "L8";
    public static final String RTP_MEDIA_VP8 = "VP8";
    public static final String RTP_MEDIA_VP9 = "VP9";
    public final int clockRate;
    public final ImmutableMap<String, String> fmtpParameters;
    public final Format format;
    public final String mediaEncoding;
    public final int rtpPayloadType;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean isFormatSupported(MediaDescription mediaDescription) {
        String upperCase = Ascii.toUpperCase(mediaDescription.rtpMapAttribute.mediaEncoding);
        upperCase.hashCode();
        char c = 65535;
        switch (upperCase.hashCode()) {
            case -1922091719:
                if (upperCase.equals(RTP_MEDIA_MPEG4_GENERIC)) {
                    c = 0;
                    break;
                }
                break;
            case 2412:
                if (upperCase.equals(RTP_MEDIA_PCM_L8)) {
                    c = 1;
                    break;
                }
                break;
            case 64593:
                if (upperCase.equals(RTP_MEDIA_AC3)) {
                    c = 2;
                    break;
                }
                break;
            case 64934:
                if (upperCase.equals(RTP_MEDIA_AMR)) {
                    c = 3;
                    break;
                }
                break;
            case 74609:
                if (upperCase.equals(RTP_MEDIA_PCM_L16)) {
                    c = 4;
                    break;
                }
                break;
            case 85182:
                if (upperCase.equals(RTP_MEDIA_VP8)) {
                    c = 5;
                    break;
                }
                break;
            case 85183:
                if (upperCase.equals(RTP_MEDIA_VP9)) {
                    c = 6;
                    break;
                }
                break;
            case 2194728:
                if (upperCase.equals(RTP_MEDIA_H264)) {
                    c = 7;
                    break;
                }
                break;
            case 2194729:
                if (upperCase.equals(RTP_MEDIA_H265)) {
                    c = '\b';
                    break;
                }
                break;
            case 2433087:
                if (upperCase.equals(RTP_MEDIA_OPUS)) {
                    c = '\t';
                    break;
                }
                break;
            case 2450119:
                if (upperCase.equals(RTP_MEDIA_PCMA)) {
                    c = '\n';
                    break;
                }
                break;
            case 2450139:
                if (upperCase.equals(RTP_MEDIA_PCMU)) {
                    c = 11;
                    break;
                }
                break;
            case 1061166827:
                if (upperCase.equals(RTP_MEDIA_MPEG4_LATM_AUDIO)) {
                    c = '\f';
                    break;
                }
                break;
            case 1934494802:
                if (upperCase.equals(RTP_MEDIA_AMR_WB)) {
                    c = '\r';
                    break;
                }
                break;
            case 1959269366:
                if (upperCase.equals(RTP_MEDIA_MPEG4_VIDEO)) {
                    c = 14;
                    break;
                }
                break;
            case 2137188397:
                if (upperCase.equals(RTP_MEDIA_H263_1998)) {
                    c = 15;
                    break;
                }
                break;
            case 2137209252:
                if (upperCase.equals(RTP_MEDIA_H263_2000)) {
                    c = 16;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case '\b':
            case '\t':
            case '\n':
            case 11:
            case '\f':
            case '\r':
            case 14:
            case 15:
            case 16:
                return true;
            default:
                return false;
        }
    }

    public static String getMimeTypeFromRtpMediaType(String str) {
        String upperCase = Ascii.toUpperCase(str);
        upperCase.hashCode();
        char c = 65535;
        switch (upperCase.hashCode()) {
            case -1922091719:
                if (upperCase.equals(RTP_MEDIA_MPEG4_GENERIC)) {
                    c = 0;
                    break;
                }
                break;
            case 2412:
                if (upperCase.equals(RTP_MEDIA_PCM_L8)) {
                    c = 1;
                    break;
                }
                break;
            case 64593:
                if (upperCase.equals(RTP_MEDIA_AC3)) {
                    c = 2;
                    break;
                }
                break;
            case 64934:
                if (upperCase.equals(RTP_MEDIA_AMR)) {
                    c = 3;
                    break;
                }
                break;
            case 74609:
                if (upperCase.equals(RTP_MEDIA_PCM_L16)) {
                    c = 4;
                    break;
                }
                break;
            case 85182:
                if (upperCase.equals(RTP_MEDIA_VP8)) {
                    c = 5;
                    break;
                }
                break;
            case 85183:
                if (upperCase.equals(RTP_MEDIA_VP9)) {
                    c = 6;
                    break;
                }
                break;
            case 2194728:
                if (upperCase.equals(RTP_MEDIA_H264)) {
                    c = 7;
                    break;
                }
                break;
            case 2194729:
                if (upperCase.equals(RTP_MEDIA_H265)) {
                    c = '\b';
                    break;
                }
                break;
            case 2433087:
                if (upperCase.equals(RTP_MEDIA_OPUS)) {
                    c = '\t';
                    break;
                }
                break;
            case 2450119:
                if (upperCase.equals(RTP_MEDIA_PCMA)) {
                    c = '\n';
                    break;
                }
                break;
            case 2450139:
                if (upperCase.equals(RTP_MEDIA_PCMU)) {
                    c = 11;
                    break;
                }
                break;
            case 1061166827:
                if (upperCase.equals(RTP_MEDIA_MPEG4_LATM_AUDIO)) {
                    c = '\f';
                    break;
                }
                break;
            case 1934494802:
                if (upperCase.equals(RTP_MEDIA_AMR_WB)) {
                    c = '\r';
                    break;
                }
                break;
            case 1959269366:
                if (upperCase.equals(RTP_MEDIA_MPEG4_VIDEO)) {
                    c = 14;
                    break;
                }
                break;
            case 2137188397:
                if (upperCase.equals(RTP_MEDIA_H263_1998)) {
                    c = 15;
                    break;
                }
                break;
            case 2137209252:
                if (upperCase.equals(RTP_MEDIA_H263_2000)) {
                    c = 16;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case '\f':
                return "audio/mp4a-latm";
            case 1:
            case 4:
                return "audio/raw";
            case 2:
                return "audio/ac3";
            case 3:
                return "audio/3gpp";
            case 5:
                return "video/x-vnd.on2.vp8";
            case 6:
                return "video/x-vnd.on2.vp9";
            case 7:
                return "video/avc";
            case '\b':
                return "video/hevc";
            case '\t':
                return "audio/opus";
            case '\n':
                return "audio/g711-alaw";
            case 11:
                return "audio/g711-mlaw";
            case '\r':
                return "audio/amr-wb";
            case 14:
                return "video/mp4v-es";
            case 15:
            case 16:
                return "video/3gpp";
            default:
                throw new IllegalArgumentException(str);
        }
    }

    public static int getRawPcmEncodingType(String str) {
        Assertions.checkArgument(str.equals(RTP_MEDIA_PCM_L8) || str.equals(RTP_MEDIA_PCM_L16));
        return str.equals(RTP_MEDIA_PCM_L8) ? 3 : 268435456;
    }

    public RtpPayloadFormat(Format format, int i, int i2, Map<String, String> map, String str) {
        this.rtpPayloadType = i;
        this.clockRate = i2;
        this.format = format;
        this.fmtpParameters = ImmutableMap.copyOf((Map) map);
        this.mediaEncoding = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        RtpPayloadFormat rtpPayloadFormat = (RtpPayloadFormat) obj;
        return this.rtpPayloadType == rtpPayloadFormat.rtpPayloadType && this.clockRate == rtpPayloadFormat.clockRate && this.format.equals(rtpPayloadFormat.format) && this.fmtpParameters.equals(rtpPayloadFormat.fmtpParameters) && this.mediaEncoding.equals(rtpPayloadFormat.mediaEncoding);
    }

    public int hashCode() {
        return ((((((((217 + this.rtpPayloadType) * 31) + this.clockRate) * 31) + this.format.hashCode()) * 31) + this.fmtpParameters.hashCode()) * 31) + this.mediaEncoding.hashCode();
    }
}
