package com.google.common.io;

import com.google.common.base.Preconditions;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public abstract class CharSink {
    public abstract Writer openStream() throws IOException;

    public Writer openBufferedStream() throws IOException {
        Writer openStream = openStream();
        if (openStream instanceof BufferedWriter) {
            return (BufferedWriter) openStream;
        }
        return new BufferedWriter(openStream);
    }

    public void write(CharSequence charSequence) throws IOException {
        Preconditions.checkNotNull(charSequence);
        try {
            Writer writer = (Writer) Closer.create().register(openStream());
            writer.append(charSequence);
            writer.flush();
        } finally {
        }
    }

    public void writeLines(Iterable<? extends CharSequence> lines) throws IOException {
        writeLines(lines, System.getProperty("line.separator"));
    }

    public void writeLines(Iterable<? extends CharSequence> lines, String lineSeparator) throws IOException {
        Preconditions.checkNotNull(lines);
        Preconditions.checkNotNull(lineSeparator);
        try {
            Writer writer = (Writer) Closer.create().register(openBufferedStream());
            for (CharSequence charSequence : lines) {
                writer.append(charSequence).append((CharSequence) lineSeparator);
            }
            writer.flush();
        } finally {
        }
    }

    public long writeFrom(Readable readable) throws IOException {
        Preconditions.checkNotNull(readable);
        try {
            Writer writer = (Writer) Closer.create().register(openStream());
            long copy = CharStreams.copy(readable, writer);
            writer.flush();
            return copy;
        } finally {
        }
    }
}
