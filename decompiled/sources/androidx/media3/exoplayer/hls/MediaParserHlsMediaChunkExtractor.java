package androidx.media3.exoplayer.hls;

import android.media.MediaFormat;
import android.media.MediaParser;
import android.net.Uri;
import android.text.TextUtils;
import androidx.media3.common.FileTypes;
import androidx.media3.common.Format;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.TimestampAdjuster;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.analytics.PlayerId;
import androidx.media3.exoplayer.hls.HlsExtractorFactory;
import androidx.media3.exoplayer.source.mediaparser.InputReaderAdapterV30;
import androidx.media3.exoplayer.source.mediaparser.MediaParserUtil;
import androidx.media3.exoplayer.source.mediaparser.OutputConsumerAdapterV30;
import androidx.media3.extractor.ExtractorInput;
import androidx.media3.extractor.ExtractorOutput;
import androidx.media3.extractor.text.SubtitleParser;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.util.List;
import java.util.Map;
/* loaded from: classes.dex */
public final class MediaParserHlsMediaChunkExtractor implements HlsMediaChunkExtractor {
    public static final HlsExtractorFactory FACTORY = new HlsExtractorFactory() { // from class: androidx.media3.exoplayer.hls.MediaParserHlsMediaChunkExtractor$$ExternalSyntheticLambda7
        @Override // androidx.media3.exoplayer.hls.HlsExtractorFactory
        public final HlsMediaChunkExtractor createExtractor(Uri uri, Format format, List list, TimestampAdjuster timestampAdjuster, Map map, ExtractorInput extractorInput, PlayerId playerId) {
            return MediaParserHlsMediaChunkExtractor.lambda$static$0(uri, format, list, timestampAdjuster, map, extractorInput, playerId);
        }

        @Override // androidx.media3.exoplayer.hls.HlsExtractorFactory
        public /* synthetic */ HlsExtractorFactory experimentalParseSubtitlesDuringExtraction(boolean z) {
            return HlsExtractorFactory.CC.$default$experimentalParseSubtitlesDuringExtraction(this, z);
        }

        @Override // androidx.media3.exoplayer.hls.HlsExtractorFactory
        public /* synthetic */ Format getOutputTextFormat(Format format) {
            return HlsExtractorFactory.CC.$default$getOutputTextFormat(this, format);
        }

        @Override // androidx.media3.exoplayer.hls.HlsExtractorFactory
        public /* synthetic */ HlsExtractorFactory setSubtitleParserFactory(SubtitleParser.Factory factory) {
            return HlsExtractorFactory.CC.$default$setSubtitleParserFactory(this, factory);
        }
    };
    private final Format format;
    private final InputReaderAdapterV30 inputReaderAdapter = new InputReaderAdapterV30();
    private final MediaParser mediaParser;
    private final ImmutableList<MediaFormat> muxedCaptionMediaFormats;
    private final OutputConsumerAdapterV30 outputConsumerAdapter;
    private final boolean overrideInBandCaptionDeclarations;
    private int pendingSkipBytes;
    private final PlayerId playerId;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ HlsMediaChunkExtractor lambda$static$0(Uri uri, Format format, List list, TimestampAdjuster timestampAdjuster, Map map, ExtractorInput extractorInput, PlayerId playerId) throws IOException {
        String parserName;
        if (FileTypes.inferFileTypeFromMimeType(format.sampleMimeType) == 13) {
            return new BundledHlsMediaChunkExtractor(new WebvttExtractor(format.language, timestampAdjuster, SubtitleParser.Factory.UNSUPPORTED, false), format, timestampAdjuster);
        }
        boolean z = list != null;
        ImmutableList.Builder builder = ImmutableList.builder();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                builder.add((ImmutableList.Builder) MediaParserUtil.toCaptionsMediaFormat((Format) list.get(i)));
            }
        } else {
            builder.add((ImmutableList.Builder) MediaParserUtil.toCaptionsMediaFormat(new Format.Builder().setSampleMimeType("application/cea-608").build()));
        }
        ImmutableList build = builder.build();
        OutputConsumerAdapterV30 outputConsumerAdapterV30 = new OutputConsumerAdapterV30();
        if (list == null) {
            list = ImmutableList.of();
        }
        outputConsumerAdapterV30.setMuxedCaptionFormats(list);
        outputConsumerAdapterV30.setTimestampAdjuster(timestampAdjuster);
        MediaParser createMediaParserInstance = createMediaParserInstance(outputConsumerAdapterV30, format, z, build, playerId, "android.media.mediaparser.FragmentedMp4Parser", "android.media.mediaparser.Ac3Parser", "android.media.mediaparser.Ac4Parser", "android.media.mediaparser.AdtsParser", "android.media.mediaparser.Mp3Parser", "android.media.mediaparser.TsParser");
        PeekingInputReader peekingInputReader = new PeekingInputReader(extractorInput);
        createMediaParserInstance.advance(peekingInputReader);
        parserName = createMediaParserInstance.getParserName();
        outputConsumerAdapterV30.setSelectedParserName(parserName);
        return new MediaParserHlsMediaChunkExtractor(createMediaParserInstance, outputConsumerAdapterV30, format, z, build, peekingInputReader.totalPeekedBytes, playerId);
    }

    public MediaParserHlsMediaChunkExtractor(MediaParser mediaParser, OutputConsumerAdapterV30 outputConsumerAdapterV30, Format format, boolean z, ImmutableList<MediaFormat> immutableList, int i, PlayerId playerId) {
        this.mediaParser = mediaParser;
        this.outputConsumerAdapter = outputConsumerAdapterV30;
        this.overrideInBandCaptionDeclarations = z;
        this.muxedCaptionMediaFormats = immutableList;
        this.format = format;
        this.playerId = playerId;
        this.pendingSkipBytes = i;
    }

    @Override // androidx.media3.exoplayer.hls.HlsMediaChunkExtractor
    public void init(ExtractorOutput extractorOutput) {
        this.outputConsumerAdapter.setExtractorOutput(extractorOutput);
    }

    @Override // androidx.media3.exoplayer.hls.HlsMediaChunkExtractor
    public boolean read(ExtractorInput extractorInput) throws IOException {
        boolean advance;
        extractorInput.skipFully(this.pendingSkipBytes);
        this.pendingSkipBytes = 0;
        this.inputReaderAdapter.setDataReader(extractorInput, extractorInput.getLength());
        advance = this.mediaParser.advance(this.inputReaderAdapter);
        return advance;
    }

    @Override // androidx.media3.exoplayer.hls.HlsMediaChunkExtractor
    public boolean isPackedAudioExtractor() {
        String parserName;
        parserName = this.mediaParser.getParserName();
        return "android.media.mediaparser.Ac3Parser".equals(parserName) || "android.media.mediaparser.Ac4Parser".equals(parserName) || "android.media.mediaparser.AdtsParser".equals(parserName) || "android.media.mediaparser.Mp3Parser".equals(parserName);
    }

    @Override // androidx.media3.exoplayer.hls.HlsMediaChunkExtractor
    public boolean isReusable() {
        String parserName;
        parserName = this.mediaParser.getParserName();
        return "android.media.mediaparser.FragmentedMp4Parser".equals(parserName) || "android.media.mediaparser.TsParser".equals(parserName);
    }

    @Override // androidx.media3.exoplayer.hls.HlsMediaChunkExtractor
    public HlsMediaChunkExtractor recreate() {
        String parserName;
        Assertions.checkState(!isReusable());
        OutputConsumerAdapterV30 outputConsumerAdapterV30 = this.outputConsumerAdapter;
        Format format = this.format;
        boolean z = this.overrideInBandCaptionDeclarations;
        ImmutableList<MediaFormat> immutableList = this.muxedCaptionMediaFormats;
        PlayerId playerId = this.playerId;
        parserName = this.mediaParser.getParserName();
        return new MediaParserHlsMediaChunkExtractor(createMediaParserInstance(outputConsumerAdapterV30, format, z, immutableList, playerId, parserName), this.outputConsumerAdapter, this.format, this.overrideInBandCaptionDeclarations, this.muxedCaptionMediaFormats, 0, this.playerId);
    }

    @Override // androidx.media3.exoplayer.hls.HlsMediaChunkExtractor
    public void onTruncatedSegmentParsed() {
        MediaParser.SeekPoint seekPoint;
        MediaParser mediaParser = this.mediaParser;
        seekPoint = MediaParser.SeekPoint.START;
        mediaParser.seek(seekPoint);
    }

    private static MediaParser createMediaParserInstance(MediaParser.OutputConsumer outputConsumer, Format format, boolean z, ImmutableList<MediaFormat> immutableList, PlayerId playerId, String... strArr) {
        MediaParser create;
        if (strArr.length == 1) {
            create = MediaParser.createByName(strArr[0], outputConsumer);
        } else {
            create = MediaParser.create(outputConsumer, strArr);
        }
        create.setParameter("android.media.mediaParser.exposeCaptionFormats", immutableList);
        create.setParameter("android.media.mediaParser.overrideInBandCaptionDeclarations", Boolean.valueOf(z));
        create.setParameter("android.media.mediaparser.inBandCryptoInfo", true);
        create.setParameter("android.media.mediaparser.eagerlyExposeTrackType", true);
        create.setParameter("android.media.mediaparser.ignoreTimestampOffset", true);
        create.setParameter("android.media.mediaparser.ts.ignoreSpliceInfoStream", true);
        create.setParameter("android.media.mediaparser.ts.mode", "hls");
        String str = format.codecs;
        if (!TextUtils.isEmpty(str)) {
            if (!"audio/mp4a-latm".equals(MimeTypes.getAudioMediaMimeType(str))) {
                create.setParameter("android.media.mediaparser.ts.ignoreAacStream", true);
            }
            if (!"video/avc".equals(MimeTypes.getVideoMediaMimeType(str))) {
                create.setParameter("android.media.mediaparser.ts.ignoreAvcStream", true);
            }
        }
        if (Util.SDK_INT >= 31) {
            MediaParserUtil.setLogSessionIdOnMediaParser(create, playerId);
        }
        return create;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class PeekingInputReader implements MediaParser.SeekableInputReader {
        private final ExtractorInput extractorInput;
        private int totalPeekedBytes;

        private PeekingInputReader(ExtractorInput extractorInput) {
            this.extractorInput = extractorInput;
        }

        @Override // android.media.MediaParser.InputReader
        public int read(byte[] bArr, int i, int i2) throws IOException {
            int peek = this.extractorInput.peek(bArr, i, i2);
            this.totalPeekedBytes += peek;
            return peek;
        }

        @Override // android.media.MediaParser.InputReader
        public long getPosition() {
            return this.extractorInput.getPeekPosition();
        }

        @Override // android.media.MediaParser.InputReader
        public long getLength() {
            return this.extractorInput.getLength();
        }

        @Override // android.media.MediaParser.SeekableInputReader
        public void seekToPosition(long j) {
            throw new UnsupportedOperationException();
        }
    }
}
