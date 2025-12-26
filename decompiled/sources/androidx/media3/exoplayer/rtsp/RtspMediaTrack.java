package androidx.media3.exoplayer.rtsp;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Pair;
import androidx.media3.common.ColorInfo;
import androidx.media3.common.Format;
import androidx.media3.common.ParserException;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.CodecSpecificDataUtil;
import androidx.media3.common.util.ParsableBitArray;
import androidx.media3.common.util.Util;
import androidx.media3.container.NalUnitUtil;
import androidx.media3.extractor.AacUtil;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class RtspMediaTrack {
    private static final String AAC_CODECS_PREFIX = "mp4a.40.";
    private static final int DEFAULT_H263_HEIGHT = 288;
    private static final int DEFAULT_H263_WIDTH = 352;
    private static final int DEFAULT_MP4V_HEIGHT = 288;
    private static final int DEFAULT_MP4V_WIDTH = 352;
    private static final int DEFAULT_VP8_HEIGHT = 240;
    private static final int DEFAULT_VP8_WIDTH = 320;
    private static final int DEFAULT_VP9_HEIGHT = 240;
    private static final int DEFAULT_VP9_WIDTH = 320;
    private static final String GENERIC_CONTROL_ATTR = "*";
    private static final String H264_CODECS_PREFIX = "avc1.";
    private static final String MPEG4_CODECS_PREFIX = "mp4v.";
    private static final int OPUS_CLOCK_RATE = 48000;
    private static final String PARAMETER_AMR_INTERLEAVING = "interleaving";
    private static final String PARAMETER_AMR_OCTET_ALIGN = "octet-align";
    private static final String PARAMETER_H265_SPROP_MAX_DON_DIFF = "sprop-max-don-diff";
    private static final String PARAMETER_H265_SPROP_PPS = "sprop-pps";
    private static final String PARAMETER_H265_SPROP_SPS = "sprop-sps";
    private static final String PARAMETER_H265_SPROP_VPS = "sprop-vps";
    private static final String PARAMETER_MP4A_CONFIG = "config";
    private static final String PARAMETER_MP4A_C_PRESENT = "cpresent";
    private static final String PARAMETER_PROFILE_LEVEL_ID = "profile-level-id";
    private static final String PARAMETER_SPROP_PARAMS = "sprop-parameter-sets";
    public final RtpPayloadFormat payloadFormat;
    public final Uri uri;

    public RtspMediaTrack(RtspHeaders rtspHeaders, MediaDescription mediaDescription, Uri uri) {
        Assertions.checkArgument(mediaDescription.attributes.containsKey(SessionDescription.ATTR_CONTROL), "missing attribute control");
        this.payloadFormat = generatePayloadFormat(mediaDescription);
        this.uri = extractTrackUri(rtspHeaders, uri, (String) Util.castNonNull(mediaDescription.attributes.get(SessionDescription.ATTR_CONTROL)));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        RtspMediaTrack rtspMediaTrack = (RtspMediaTrack) obj;
        return this.payloadFormat.equals(rtspMediaTrack.payloadFormat) && this.uri.equals(rtspMediaTrack.uri);
    }

    public int hashCode() {
        return ((217 + this.payloadFormat.hashCode()) * 31) + this.uri.hashCode();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    static RtpPayloadFormat generatePayloadFormat(MediaDescription mediaDescription) {
        int i;
        char c;
        Format.Builder builder = new Format.Builder();
        if (mediaDescription.bitrate > 0) {
            builder.setAverageBitrate(mediaDescription.bitrate);
        }
        int i2 = mediaDescription.rtpMapAttribute.payloadType;
        String str = mediaDescription.rtpMapAttribute.mediaEncoding;
        String mimeTypeFromRtpMediaType = RtpPayloadFormat.getMimeTypeFromRtpMediaType(str);
        builder.setSampleMimeType(mimeTypeFromRtpMediaType);
        int i3 = mediaDescription.rtpMapAttribute.clockRate;
        if ("audio".equals(mediaDescription.mediaType)) {
            i = inferChannelCount(mediaDescription.rtpMapAttribute.encodingParameters, mimeTypeFromRtpMediaType);
            builder.setSampleRate(i3).setChannelCount(i);
        } else {
            i = -1;
        }
        ImmutableMap<String, String> fmtpParametersAsMap = mediaDescription.getFmtpParametersAsMap();
        switch (mimeTypeFromRtpMediaType.hashCode()) {
            case -1664118616:
                if (mimeTypeFromRtpMediaType.equals("video/3gpp")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1662541442:
                if (mimeTypeFromRtpMediaType.equals("video/hevc")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -1606874997:
                if (mimeTypeFromRtpMediaType.equals("audio/amr-wb")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -53558318:
                if (mimeTypeFromRtpMediaType.equals("audio/mp4a-latm")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 187078296:
                if (mimeTypeFromRtpMediaType.equals("audio/ac3")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 187094639:
                if (mimeTypeFromRtpMediaType.equals("audio/raw")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 1187890754:
                if (mimeTypeFromRtpMediaType.equals("video/mp4v-es")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 1331836730:
                if (mimeTypeFromRtpMediaType.equals("video/avc")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 1503095341:
                if (mimeTypeFromRtpMediaType.equals("audio/3gpp")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1504891608:
                if (mimeTypeFromRtpMediaType.equals("audio/opus")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1599127256:
                if (mimeTypeFromRtpMediaType.equals("video/x-vnd.on2.vp8")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 1599127257:
                if (mimeTypeFromRtpMediaType.equals("video/x-vnd.on2.vp9")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 1903231877:
                if (mimeTypeFromRtpMediaType.equals("audio/g711-alaw")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 1903589369:
                if (mimeTypeFromRtpMediaType.equals("audio/g711-mlaw")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                Assertions.checkArgument(i != -1);
                Assertions.checkArgument(!fmtpParametersAsMap.isEmpty(), "missing attribute fmtp");
                if (str.equals(RtpPayloadFormat.RTP_MEDIA_MPEG4_LATM_AUDIO)) {
                    Assertions.checkArgument(fmtpParametersAsMap.containsKey(PARAMETER_MP4A_C_PRESENT) && fmtpParametersAsMap.get(PARAMETER_MP4A_C_PRESENT).equals(SessionDescription.SUPPORTED_SDP_VERSION), "Only supports cpresent=0 in AAC audio.");
                    String str2 = fmtpParametersAsMap.get(PARAMETER_MP4A_CONFIG);
                    Assertions.checkNotNull(str2, "AAC audio stream must include config fmtp parameter");
                    boolean z = str2.length() % 2 == 0;
                    Assertions.checkArgument(z, "Malformat MPEG4 config: " + str2);
                    AacUtil.Config parseAacStreamMuxConfig = parseAacStreamMuxConfig(str2);
                    builder.setSampleRate(parseAacStreamMuxConfig.sampleRateHz).setChannelCount(parseAacStreamMuxConfig.channelCount).setCodecs(parseAacStreamMuxConfig.codecs);
                }
                processAacFmtpAttribute(builder, fmtpParametersAsMap, str, i, i3);
                break;
            case 1:
            case 2:
                Assertions.checkArgument(i == 1, "Multi channel AMR is not currently supported.");
                Assertions.checkArgument(!fmtpParametersAsMap.isEmpty(), "fmtp parameters must include octet-align.");
                Assertions.checkArgument(fmtpParametersAsMap.containsKey(PARAMETER_AMR_OCTET_ALIGN), "Only octet aligned mode is currently supported.");
                Assertions.checkArgument(!fmtpParametersAsMap.containsKey(PARAMETER_AMR_INTERLEAVING), "Interleaving mode is not currently supported.");
                break;
            case 3:
                Assertions.checkArgument(i != -1);
                Assertions.checkArgument(i3 == 48000, "Invalid OPUS clock rate.");
                break;
            case 4:
                Assertions.checkArgument(!fmtpParametersAsMap.isEmpty());
                processMPEG4FmtpAttribute(builder, fmtpParametersAsMap);
                break;
            case 5:
                builder.setWidth(352).setHeight(288);
                break;
            case 6:
                Assertions.checkArgument(!fmtpParametersAsMap.isEmpty(), "missing attribute fmtp");
                processH264FmtpAttribute(builder, fmtpParametersAsMap);
                break;
            case 7:
                Assertions.checkArgument(!fmtpParametersAsMap.isEmpty(), "missing attribute fmtp");
                processH265FmtpAttribute(builder, fmtpParametersAsMap);
                break;
            case '\b':
                builder.setWidth(320).setHeight(240);
                break;
            case '\t':
                builder.setWidth(320).setHeight(240);
                break;
            case '\n':
                builder.setPcmEncoding(RtpPayloadFormat.getRawPcmEncodingType(str));
                break;
        }
        Assertions.checkArgument(i3 > 0);
        return new RtpPayloadFormat(builder.build(), i2, i3, fmtpParametersAsMap, str);
    }

    private static int inferChannelCount(int i, String str) {
        return i != -1 ? i : str.equals("audio/ac3") ? 6 : 1;
    }

    private static void processAacFmtpAttribute(Format.Builder builder, ImmutableMap<String, String> immutableMap, String str, int i, int i2) {
        String str2 = immutableMap.get(PARAMETER_PROFILE_LEVEL_ID);
        if (str2 == null && str.equals(RtpPayloadFormat.RTP_MEDIA_MPEG4_LATM_AUDIO)) {
            str2 = "30";
        }
        Assertions.checkArgument((str2 == null || str2.isEmpty()) ? false : true, "missing profile-level-id param");
        builder.setCodecs(AAC_CODECS_PREFIX + str2);
        builder.setInitializationData(ImmutableList.of(AacUtil.buildAacLcAudioSpecificConfig(i2, i)));
    }

    private static AacUtil.Config parseAacStreamMuxConfig(String str) {
        ParsableBitArray parsableBitArray = new ParsableBitArray(Util.getBytesFromHexString(str));
        Assertions.checkArgument(parsableBitArray.readBits(1) == 0, "Only supports audio mux version 0.");
        Assertions.checkArgument(parsableBitArray.readBits(1) == 1, "Only supports allStreamsSameTimeFraming.");
        parsableBitArray.skipBits(6);
        Assertions.checkArgument(parsableBitArray.readBits(4) == 0, "Only supports one program.");
        Assertions.checkArgument(parsableBitArray.readBits(3) == 0, "Only supports one numLayer.");
        try {
            return AacUtil.parseAudioSpecificConfig(parsableBitArray, false);
        } catch (ParserException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static void processMPEG4FmtpAttribute(Format.Builder builder, ImmutableMap<String, String> immutableMap) {
        String str = immutableMap.get(PARAMETER_MP4A_CONFIG);
        if (str != null) {
            byte[] bytesFromHexString = Util.getBytesFromHexString(str);
            builder.setInitializationData(ImmutableList.of(bytesFromHexString));
            Pair<Integer, Integer> videoResolutionFromMpeg4VideoConfig = CodecSpecificDataUtil.getVideoResolutionFromMpeg4VideoConfig(bytesFromHexString);
            builder.setWidth(((Integer) videoResolutionFromMpeg4VideoConfig.first).intValue()).setHeight(((Integer) videoResolutionFromMpeg4VideoConfig.second).intValue());
        } else {
            builder.setWidth(352).setHeight(288);
        }
        String str2 = immutableMap.get(PARAMETER_PROFILE_LEVEL_ID);
        StringBuilder sb = new StringBuilder(MPEG4_CODECS_PREFIX);
        if (str2 == null) {
            str2 = "1";
        }
        sb.append(str2);
        builder.setCodecs(sb.toString());
    }

    private static byte[] getInitializationDataFromParameterSet(String str) {
        byte[] decode = Base64.decode(str, 0);
        byte[] bArr = new byte[decode.length + NalUnitUtil.NAL_START_CODE.length];
        System.arraycopy(NalUnitUtil.NAL_START_CODE, 0, bArr, 0, NalUnitUtil.NAL_START_CODE.length);
        System.arraycopy(decode, 0, bArr, NalUnitUtil.NAL_START_CODE.length, decode.length);
        return bArr;
    }

    private static void processH264FmtpAttribute(Format.Builder builder, ImmutableMap<String, String> immutableMap) {
        Assertions.checkArgument(immutableMap.containsKey(PARAMETER_SPROP_PARAMS), "missing sprop parameter");
        String[] split = Util.split((String) Assertions.checkNotNull(immutableMap.get(PARAMETER_SPROP_PARAMS)), ",");
        Assertions.checkArgument(split.length == 2, "empty sprop value");
        ImmutableList of = ImmutableList.of(getInitializationDataFromParameterSet(split[0]), getInitializationDataFromParameterSet(split[1]));
        builder.setInitializationData(of);
        byte[] bArr = (byte[]) of.get(0);
        NalUnitUtil.SpsData parseSpsNalUnit = NalUnitUtil.parseSpsNalUnit(bArr, NalUnitUtil.NAL_START_CODE.length, bArr.length);
        builder.setPixelWidthHeightRatio(parseSpsNalUnit.pixelWidthHeightRatio);
        builder.setHeight(parseSpsNalUnit.height);
        builder.setWidth(parseSpsNalUnit.width);
        builder.setColorInfo(new ColorInfo.Builder().setColorSpace(parseSpsNalUnit.colorSpace).setColorRange(parseSpsNalUnit.colorRange).setColorTransfer(parseSpsNalUnit.colorTransfer).setLumaBitdepth(parseSpsNalUnit.bitDepthLumaMinus8 + 8).setChromaBitdepth(parseSpsNalUnit.bitDepthChromaMinus8 + 8).build());
        String str = immutableMap.get(PARAMETER_PROFILE_LEVEL_ID);
        if (str != null) {
            builder.setCodecs(H264_CODECS_PREFIX + str);
            return;
        }
        builder.setCodecs(CodecSpecificDataUtil.buildAvcCodecString(parseSpsNalUnit.profileIdc, parseSpsNalUnit.constraintsFlagsAndReservedZero2Bits, parseSpsNalUnit.levelIdc));
    }

    private static void processH265FmtpAttribute(Format.Builder builder, ImmutableMap<String, String> immutableMap) {
        if (immutableMap.containsKey(PARAMETER_H265_SPROP_MAX_DON_DIFF)) {
            int parseInt = Integer.parseInt((String) Assertions.checkNotNull(immutableMap.get(PARAMETER_H265_SPROP_MAX_DON_DIFF)));
            boolean z = parseInt == 0;
            Assertions.checkArgument(z, "non-zero sprop-max-don-diff " + parseInt + " is not supported");
        }
        Assertions.checkArgument(immutableMap.containsKey(PARAMETER_H265_SPROP_VPS), "missing sprop-vps parameter");
        Assertions.checkArgument(immutableMap.containsKey(PARAMETER_H265_SPROP_SPS), "missing sprop-sps parameter");
        Assertions.checkArgument(immutableMap.containsKey(PARAMETER_H265_SPROP_PPS), "missing sprop-pps parameter");
        ImmutableList of = ImmutableList.of(getInitializationDataFromParameterSet((String) Assertions.checkNotNull(immutableMap.get(PARAMETER_H265_SPROP_VPS))), getInitializationDataFromParameterSet((String) Assertions.checkNotNull(immutableMap.get(PARAMETER_H265_SPROP_SPS))), getInitializationDataFromParameterSet((String) Assertions.checkNotNull(immutableMap.get(PARAMETER_H265_SPROP_PPS))));
        builder.setInitializationData(of);
        byte[] bArr = (byte[]) of.get(1);
        NalUnitUtil.H265SpsData parseH265SpsNalUnit = NalUnitUtil.parseH265SpsNalUnit(bArr, NalUnitUtil.NAL_START_CODE.length, bArr.length);
        builder.setPixelWidthHeightRatio(parseH265SpsNalUnit.pixelWidthHeightRatio);
        builder.setHeight(parseH265SpsNalUnit.height).setWidth(parseH265SpsNalUnit.width);
        builder.setColorInfo(new ColorInfo.Builder().setColorSpace(parseH265SpsNalUnit.colorSpace).setColorRange(parseH265SpsNalUnit.colorRange).setColorTransfer(parseH265SpsNalUnit.colorTransfer).setLumaBitdepth(parseH265SpsNalUnit.bitDepthLumaMinus8 + 8).setChromaBitdepth(parseH265SpsNalUnit.bitDepthChromaMinus8 + 8).build());
        builder.setCodecs(CodecSpecificDataUtil.buildHevcCodecString(parseH265SpsNalUnit.generalProfileSpace, parseH265SpsNalUnit.generalTierFlag, parseH265SpsNalUnit.generalProfileIdc, parseH265SpsNalUnit.generalProfileCompatibilityFlags, parseH265SpsNalUnit.constraintBytes, parseH265SpsNalUnit.generalLevelIdc));
    }

    private static Uri extractTrackUri(RtspHeaders rtspHeaders, Uri uri, String str) {
        Uri parse = Uri.parse(str);
        if (parse.isAbsolute()) {
            return parse;
        }
        if (!TextUtils.isEmpty(rtspHeaders.get(RtspHeaders.CONTENT_BASE))) {
            uri = Uri.parse(rtspHeaders.get(RtspHeaders.CONTENT_BASE));
        } else if (!TextUtils.isEmpty(rtspHeaders.get("Content-Location"))) {
            uri = Uri.parse(rtspHeaders.get("Content-Location"));
        }
        return str.equals(GENERIC_CONTROL_ATTR) ? uri : uri.buildUpon().appendEncodedPath(str).build();
    }
}
