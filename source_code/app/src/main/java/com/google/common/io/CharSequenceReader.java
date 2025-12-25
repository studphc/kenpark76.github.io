package com.google.common.io;

import com.google.common.base.Preconditions;
import j$.util.Objects;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import javax.annotation.CheckForNull;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class CharSequenceReader extends Reader {
    private int mark;
    private int pos;
    @CheckForNull
    private CharSequence seq;

    @Override // java.io.Reader
    public boolean markSupported() {
        return true;
    }

    public CharSequenceReader(CharSequence seq) {
        this.seq = (CharSequence) Preconditions.checkNotNull(seq);
    }

    private void checkOpen() throws IOException {
        if (this.seq == null) {
            throw new IOException("reader closed");
        }
    }

    private boolean hasRemaining() {
        return remaining() > 0;
    }

    private int remaining() {
        Objects.requireNonNull(this.seq);
        return this.seq.length() - this.pos;
    }

    @Override // java.io.Reader, java.lang.Readable
    public synchronized int read(CharBuffer target) throws IOException {
        Preconditions.checkNotNull(target);
        checkOpen();
        Objects.requireNonNull(this.seq);
        if (hasRemaining()) {
            int min = Math.min(target.remaining(), remaining());
            for (int i = 0; i < min; i++) {
                CharSequence charSequence = this.seq;
                int i2 = this.pos;
                this.pos = i2 + 1;
                target.put(charSequence.charAt(i2));
            }
            return min;
        }
        return -1;
    }

    @Override // java.io.Reader
    public synchronized int read() throws IOException {
        char c;
        checkOpen();
        Objects.requireNonNull(this.seq);
        if (hasRemaining()) {
            CharSequence charSequence = this.seq;
            int i = this.pos;
            this.pos = i + 1;
            c = charSequence.charAt(i);
        } else {
            c = 65535;
        }
        return c;
    }

    @Override // java.io.Reader
    public synchronized int read(char[] cbuf, int off, int len) throws IOException {
        Preconditions.checkPositionIndexes(off, off + len, cbuf.length);
        checkOpen();
        Objects.requireNonNull(this.seq);
        if (hasRemaining()) {
            int min = Math.min(len, remaining());
            for (int i = 0; i < min; i++) {
                CharSequence charSequence = this.seq;
                int i2 = this.pos;
                this.pos = i2 + 1;
                cbuf[off + i] = charSequence.charAt(i2);
            }
            return min;
        }
        return -1;
    }

    @Override // java.io.Reader
    public synchronized long skip(long n) throws IOException {
        int min;
        Preconditions.checkArgument(n >= 0, "n (%s) may not be negative", n);
        checkOpen();
        min = (int) Math.min(remaining(), n);
        this.pos += min;
        return min;
    }

    @Override // java.io.Reader
    public synchronized boolean ready() throws IOException {
        checkOpen();
        return true;
    }

    @Override // java.io.Reader
    public synchronized void mark(int readAheadLimit) throws IOException {
        Preconditions.checkArgument(readAheadLimit >= 0, "readAheadLimit (%s) may not be negative", readAheadLimit);
        checkOpen();
        this.mark = this.pos;
    }

    @Override // java.io.Reader
    public synchronized void reset() throws IOException {
        checkOpen();
        this.pos = this.mark;
    }

    @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() throws IOException {
        this.seq = null;
    }
}
