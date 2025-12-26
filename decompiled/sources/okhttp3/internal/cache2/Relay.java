package okhttp3.internal.cache2;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.ByteString;
import okio.Source;
import okio.Timeout;
/* loaded from: classes3.dex */
final class Relay {
    private static final long FILE_HEADER_SIZE = 32;
    static final ByteString PREFIX_CLEAN = ByteString.encodeUtf8("OkHttp cache v1\n");
    static final ByteString PREFIX_DIRTY = ByteString.encodeUtf8("OkHttp DIRTY :(\n");
    private static final int SOURCE_FILE = 2;
    private static final int SOURCE_UPSTREAM = 1;
    final long bufferMaxSize;
    boolean complete;
    RandomAccessFile file;
    private final ByteString metadata;
    int sourceCount;
    Source upstream;
    long upstreamPos;
    Thread upstreamReader;
    final Buffer upstreamBuffer = new Buffer();
    final Buffer buffer = new Buffer();

    private Relay(RandomAccessFile randomAccessFile, Source source, long j, ByteString byteString, long j2) {
        this.file = randomAccessFile;
        this.upstream = source;
        this.complete = source == null;
        this.upstreamPos = j;
        this.metadata = byteString;
        this.bufferMaxSize = j2;
    }

    public static Relay edit(File file, Source source, ByteString byteString, long j) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        Relay relay = new Relay(randomAccessFile, source, 0L, byteString, j);
        randomAccessFile.setLength(0L);
        relay.writeHeader(PREFIX_DIRTY, -1L, -1L);
        return relay;
    }

    public static Relay read(File file) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        FileOperator fileOperator = new FileOperator(randomAccessFile.getChannel());
        Buffer buffer = new Buffer();
        fileOperator.read(0L, buffer, 32L);
        ByteString byteString = PREFIX_CLEAN;
        if (!buffer.readByteString(byteString.size()).equals(byteString)) {
            throw new IOException("unreadable cache file");
        }
        long readLong = buffer.readLong();
        long readLong2 = buffer.readLong();
        Buffer buffer2 = new Buffer();
        fileOperator.read(readLong + 32, buffer2, readLong2);
        return new Relay(randomAccessFile, null, readLong, buffer2.readByteString(), 0L);
    }

    private void writeHeader(ByteString byteString, long j, long j2) throws IOException {
        Buffer buffer = new Buffer();
        buffer.write(byteString);
        buffer.writeLong(j);
        buffer.writeLong(j2);
        if (buffer.size() != 32) {
            throw new IllegalArgumentException();
        }
        new FileOperator(this.file.getChannel()).write(0L, buffer, 32L);
    }

    private void writeMetadata(long j) throws IOException {
        Buffer buffer = new Buffer();
        buffer.write(this.metadata);
        new FileOperator(this.file.getChannel()).write(32 + j, buffer, this.metadata.size());
    }

    void commit(long j) throws IOException {
        writeMetadata(j);
        this.file.getChannel().force(false);
        writeHeader(PREFIX_CLEAN, j, this.metadata.size());
        this.file.getChannel().force(false);
        synchronized (this) {
            this.complete = true;
        }
        Util.closeQuietly(this.upstream);
        this.upstream = null;
    }

    boolean isClosed() {
        return this.file == null;
    }

    public ByteString metadata() {
        return this.metadata;
    }

    public Source newSource() {
        synchronized (this) {
            if (this.file == null) {
                return null;
            }
            this.sourceCount++;
            return new RelaySource();
        }
    }

    /* loaded from: classes3.dex */
    class RelaySource implements Source {
        private FileOperator fileOperator;
        private long sourcePos;
        private final Timeout timeout = new Timeout();

        RelaySource() {
            this.fileOperator = new FileOperator(Relay.this.file.getChannel());
        }

        /* JADX WARN: Code restructure failed: missing block: B:23:0x004d, code lost:
            if (r4 != 2) goto L22;
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x004f, code lost:
            r2 = java.lang.Math.min(r22, r7 - r20.sourcePos);
            r20.fileOperator.read(r20.sourcePos + 32, r21, r2);
            r20.sourcePos += r2;
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x0067, code lost:
            return r2;
         */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x0069, code lost:
            r11 = r20.this$0.upstream.read(r20.this$0.upstreamBuffer, r20.this$0.bufferMaxSize);
         */
        /* JADX WARN: Code restructure failed: missing block: B:28:0x007b, code lost:
            if (r11 != (-1)) goto L37;
         */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x007d, code lost:
            r20.this$0.commit(r7);
         */
        /* JADX WARN: Code restructure failed: missing block: B:30:0x0082, code lost:
            r2 = r20.this$0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x0084, code lost:
            monitor-enter(r2);
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x0085, code lost:
            r20.this$0.upstreamReader = null;
            r20.this$0.notifyAll();
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x008e, code lost:
            monitor-exit(r2);
         */
        /* JADX WARN: Code restructure failed: missing block: B:34:0x008f, code lost:
            return -1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x0093, code lost:
            r2 = java.lang.Math.min(r11, r22);
            r20.this$0.upstreamBuffer.copyTo(r21, 0, r2);
            r20.sourcePos += r2;
            r20.fileOperator.write(r7 + 32, r20.this$0.upstreamBuffer.clone(), r11);
            r5 = r20.this$0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x00bc, code lost:
            monitor-enter(r5);
         */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x00bd, code lost:
            r20.this$0.buffer.write(r20.this$0.upstreamBuffer, r11);
         */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x00d6, code lost:
            if (r20.this$0.buffer.size() <= r20.this$0.bufferMaxSize) goto L42;
         */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x00d8, code lost:
            r20.this$0.buffer.skip(r20.this$0.buffer.size() - r20.this$0.bufferMaxSize);
         */
        /* JADX WARN: Code restructure failed: missing block: B:43:0x00ec, code lost:
            r20.this$0.upstreamPos += r11;
         */
        /* JADX WARN: Code restructure failed: missing block: B:44:0x00f3, code lost:
            monitor-exit(r5);
         */
        /* JADX WARN: Code restructure failed: missing block: B:45:0x00f4, code lost:
            r6 = r20.this$0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:46:0x00f6, code lost:
            monitor-enter(r6);
         */
        /* JADX WARN: Code restructure failed: missing block: B:47:0x00f7, code lost:
            r20.this$0.upstreamReader = null;
            r20.this$0.notifyAll();
         */
        /* JADX WARN: Code restructure failed: missing block: B:48:0x0100, code lost:
            monitor-exit(r6);
         */
        /* JADX WARN: Code restructure failed: missing block: B:49:0x0101, code lost:
            return r2;
         */
        /* JADX WARN: Code restructure failed: missing block: B:56:0x0108, code lost:
            r0 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:58:0x010b, code lost:
            monitor-enter(r20.this$0);
         */
        /* JADX WARN: Code restructure failed: missing block: B:59:0x010c, code lost:
            r20.this$0.upstreamReader = null;
            r20.this$0.notifyAll();
         */
        /* JADX WARN: Code restructure failed: missing block: B:61:0x0116, code lost:
            throw r0;
         */
        @Override // okio.Source
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public long read(okio.Buffer r21, long r22) throws java.io.IOException {
            /*
                Method dump skipped, instructions count: 319
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.cache2.Relay.RelaySource.read(okio.Buffer, long):long");
        }

        @Override // okio.Source
        public Timeout timeout() {
            return this.timeout;
        }

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (this.fileOperator == null) {
                return;
            }
            RandomAccessFile randomAccessFile = null;
            this.fileOperator = null;
            synchronized (Relay.this) {
                Relay relay = Relay.this;
                relay.sourceCount--;
                if (Relay.this.sourceCount == 0) {
                    RandomAccessFile randomAccessFile2 = Relay.this.file;
                    Relay.this.file = null;
                    randomAccessFile = randomAccessFile2;
                }
            }
            if (randomAccessFile != null) {
                Util.closeQuietly(randomAccessFile);
            }
        }
    }
}
