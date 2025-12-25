package com.google.common.io;

import com.google.common.base.Preconditions;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public abstract class ByteSink {
    public abstract OutputStream openStream() throws IOException;

    public CharSink asCharSink(Charset charset) {
        return new AsCharSink(charset);
    }

    public OutputStream openBufferedStream() throws IOException {
        OutputStream openStream = openStream();
        if (openStream instanceof BufferedOutputStream) {
            return (BufferedOutputStream) openStream;
        }
        return new BufferedOutputStream(openStream);
    }

    public void write(byte[] bytes) throws IOException {
        Preconditions.checkNotNull(bytes);
        try {
            OutputStream outputStream = (OutputStream) Closer.create().register(openStream());
            outputStream.write(bytes);
            outputStream.flush();
        } finally {
        }
    }

    public long writeFrom(InputStream input) throws IOException {
        Preconditions.checkNotNull(input);
        try {
            OutputStream outputStream = (OutputStream) Closer.create().register(openStream());
            long copy = ByteStreams.copy(input, outputStream);
            outputStream.flush();
            return copy;
        } finally {
        }
    }

    /* loaded from: classes3.dex */
    private final class AsCharSink extends CharSink {
        private final Charset charset;

        private AsCharSink(Charset charset) {
            this.charset = (Charset) Preconditions.checkNotNull(charset);
        }

        @Override // com.google.common.io.CharSink
        public Writer openStream() throws IOException {
            return new OutputStreamWriter(ByteSink.this.openStream(), this.charset);
        }

        public String toString() {
            return ByteSink.this.toString() + ".asCharSink(" + this.charset + ")";
        }
    }
}
