package androidx.media3.decoder.ffmpeg;

import android.os.Handler;
import androidx.media3.common.Format;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.audio.AudioProcessor;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.TraceUtil;
import androidx.media3.common.util.Util;
import androidx.media3.decoder.CryptoConfig;
import androidx.media3.exoplayer.audio.AudioRendererEventListener;
import androidx.media3.exoplayer.audio.AudioSink;
import androidx.media3.exoplayer.audio.DecoderAudioRenderer;
import androidx.media3.exoplayer.audio.DefaultAudioSink;
/* loaded from: classes.dex */
public final class FfmpegAudioRenderer extends DecoderAudioRenderer<FfmpegAudioDecoder> {
    private static final int DEFAULT_INPUT_BUFFER_SIZE = 5760;
    private static final int NUM_BUFFERS = 16;
    private static final String TAG = "FfmpegAudioRenderer";

    @Override // androidx.media3.exoplayer.Renderer, androidx.media3.exoplayer.RendererCapabilities
    public String getName() {
        return TAG;
    }

    @Override // androidx.media3.exoplayer.BaseRenderer, androidx.media3.exoplayer.RendererCapabilities
    public int supportsMixedMimeTypeAdaptation() {
        return 8;
    }

    public FfmpegAudioRenderer() {
        this((Handler) null, (AudioRendererEventListener) null, new AudioProcessor[0]);
    }

    public FfmpegAudioRenderer(Handler handler, AudioRendererEventListener audioRendererEventListener, AudioProcessor... audioProcessorArr) {
        this(handler, audioRendererEventListener, new DefaultAudioSink.Builder().setAudioProcessors(audioProcessorArr).build());
    }

    public FfmpegAudioRenderer(Handler handler, AudioRendererEventListener audioRendererEventListener, AudioSink audioSink) {
        super(handler, audioRendererEventListener, audioSink);
    }

    @Override // androidx.media3.exoplayer.audio.DecoderAudioRenderer
    protected int supportsFormatInternal(Format format) {
        String str = (String) Assertions.checkNotNull(format.sampleMimeType);
        if (FfmpegLibrary.isAvailable() && MimeTypes.isAudio(str)) {
            if (FfmpegLibrary.supportsFormat(str)) {
                if (sinkSupportsFormat(format, 2) || sinkSupportsFormat(format, 4)) {
                    return format.cryptoType != 0 ? 2 : 4;
                }
                return 1;
            }
            return 1;
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.media3.exoplayer.audio.DecoderAudioRenderer
    public FfmpegAudioDecoder createDecoder(Format format, CryptoConfig cryptoConfig) throws FfmpegDecoderException {
        TraceUtil.beginSection("createFfmpegAudioDecoder");
        FfmpegAudioDecoder ffmpegAudioDecoder = new FfmpegAudioDecoder(format, 16, 16, format.maxInputSize != -1 ? format.maxInputSize : DEFAULT_INPUT_BUFFER_SIZE, shouldOutputFloat(format));
        TraceUtil.endSection();
        return ffmpegAudioDecoder;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.media3.exoplayer.audio.DecoderAudioRenderer
    public Format getOutputFormat(FfmpegAudioDecoder ffmpegAudioDecoder) {
        Assertions.checkNotNull(ffmpegAudioDecoder);
        return new Format.Builder().setSampleMimeType("audio/raw").setChannelCount(ffmpegAudioDecoder.getChannelCount()).setSampleRate(ffmpegAudioDecoder.getSampleRate()).setPcmEncoding(ffmpegAudioDecoder.getEncoding()).build();
    }

    private boolean sinkSupportsFormat(Format format, int i) {
        return sinkSupportsFormat(Util.getPcmFormat(i, format.channelCount, format.sampleRate));
    }

    private boolean shouldOutputFloat(Format format) {
        if (sinkSupportsFormat(format, 2)) {
            if (getSinkFormatSupport(Util.getPcmFormat(4, format.channelCount, format.sampleRate)) != 2) {
                return false;
            }
            return !"audio/ac3".equals(format.sampleMimeType);
        }
        return true;
    }
}
