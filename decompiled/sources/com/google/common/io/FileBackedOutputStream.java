package com.google.common.io;

import com.google.common.base.Preconditions;
import j$.util.Objects;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.annotation.CheckForNull;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class FileBackedOutputStream extends OutputStream {
    @CheckForNull
    private File file;
    private final int fileThreshold;
    @CheckForNull
    private MemoryOutput memory;
    private OutputStream out;
    private final boolean resetOnFinalize;
    private final ByteSource source;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class MemoryOutput extends ByteArrayOutputStream {
        private MemoryOutput() {
        }

        byte[] getBuffer() {
            return this.buf;
        }

        int getCount() {
            return this.count;
        }
    }

    @CheckForNull
    synchronized File getFile() {
        return this.file;
    }

    public FileBackedOutputStream(int fileThreshold) {
        this(fileThreshold, false);
    }

    public FileBackedOutputStream(int fileThreshold, boolean resetOnFinalize) {
        Preconditions.checkArgument(fileThreshold >= 0, "fileThreshold must be non-negative, but was %s", fileThreshold);
        this.fileThreshold = fileThreshold;
        this.resetOnFinalize = resetOnFinalize;
        MemoryOutput memoryOutput = new MemoryOutput();
        this.memory = memoryOutput;
        this.out = memoryOutput;
        if (resetOnFinalize) {
            this.source = new ByteSource() { // from class: com.google.common.io.FileBackedOutputStream.1
                @Override // com.google.common.io.ByteSource
                public InputStream openStream() throws IOException {
                    return FileBackedOutputStream.this.openInputStream();
                }

                protected void finalize() {
                    try {
                        FileBackedOutputStream.this.reset();
                    } catch (Throwable th) {
                        th.printStackTrace(System.err);
                    }
                }
            };
        } else {
            this.source = new ByteSource() { // from class: com.google.common.io.FileBackedOutputStream.2
                @Override // com.google.common.io.ByteSource
                public InputStream openStream() throws IOException {
                    return FileBackedOutputStream.this.openInputStream();
                }
            };
        }
    }

    public ByteSource asByteSource() {
        return this.source;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized InputStream openInputStream() throws IOException {
        if (this.file != null) {
            return new FileInputStream(this.file);
        }
        Objects.requireNonNull(this.memory);
        return new ByteArrayInputStream(this.memory.getBuffer(), 0, this.memory.getCount());
    }

    public synchronized void reset() throws IOException {
        close();
        MemoryOutput memoryOutput = this.memory;
        if (memoryOutput == null) {
            this.memory = new MemoryOutput();
        } else {
            memoryOutput.reset();
        }
        this.out = this.memory;
        File file = this.file;
        if (file != null) {
            this.file = null;
            if (!file.delete()) {
                throw new IOException("Could not delete: " + file);
            }
        }
    }

    @Override // java.io.OutputStream
    public synchronized void write(int b) throws IOException {
        update(1);
        this.out.write(b);
    }

    @Override // java.io.OutputStream
    public synchronized void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    @Override // java.io.OutputStream
    public synchronized void write(byte[] b, int off, int len) throws IOException {
        update(len);
        this.out.write(b, off, len);
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() throws IOException {
        this.out.close();
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public synchronized void flush() throws IOException {
        this.out.flush();
    }

    private void update(int len) throws IOException {
        MemoryOutput memoryOutput = this.memory;
        if (memoryOutput == null || memoryOutput.getCount() + len <= this.fileThreshold) {
            return;
        }
        File createTempFile = TempFileCreator.INSTANCE.createTempFile("FileBackedOutputStream");
        if (this.resetOnFinalize) {
            createTempFile.deleteOnExit();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(createTempFile);
            fileOutputStream.write(this.memory.getBuffer(), 0, this.memory.getCount());
            fileOutputStream.flush();
            this.out = fileOutputStream;
            this.file = createTempFile;
            this.memory = null;
        } catch (IOException e) {
            createTempFile.delete();
            throw e;
        }
    }
}
