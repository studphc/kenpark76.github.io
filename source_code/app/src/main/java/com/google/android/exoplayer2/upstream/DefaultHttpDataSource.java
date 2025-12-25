package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Predicate;
import com.google.common.net.HttpHeaders;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.NoRouteToHostException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
/* loaded from: classes3.dex */
public class DefaultHttpDataSource extends BaseDataSource implements HttpDataSource {
    private static final Pattern CONTENT_RANGE_HEADER = Pattern.compile("^bytes (\\d+)-(\\d+)/(\\d+)$");
    public static final int DEFAULT_CONNECT_TIMEOUT_MILLIS = 8000;
    public static final int DEFAULT_READ_TIMEOUT_MILLIS = 8000;
    private static final int HTTP_STATUS_PERMANENT_REDIRECT = 308;
    private static final int HTTP_STATUS_TEMPORARY_REDIRECT = 307;
    private static final long MAX_BYTES_TO_DRAIN = 2048;
    private static final int MAX_REDIRECTS = 20;
    private static final String TAG = "DefaultHttpDataSource";
    private final boolean allowCrossProtocolRedirects;
    private long bytesRead;
    private long bytesSkipped;
    private long bytesToRead;
    private long bytesToSkip;
    private final int connectTimeoutMillis;
    private HttpURLConnection connection;
    private Predicate<String> contentTypePredicate;
    private DataSpec dataSpec;
    private final HttpDataSource.RequestProperties defaultRequestProperties;
    private InputStream inputStream;
    private boolean opened;
    private final int readTimeoutMillis;
    private final HttpDataSource.RequestProperties requestProperties;
    private int responseCode;
    private byte[] skipBuffer;
    private final String userAgent;

    /* loaded from: classes3.dex */
    public static final class Factory implements HttpDataSource.Factory {
        private boolean allowCrossProtocolRedirects;
        private Predicate<String> contentTypePredicate;
        private TransferListener transferListener;
        private String userAgent;
        private final HttpDataSource.RequestProperties defaultRequestProperties = new HttpDataSource.RequestProperties();
        private int connectTimeoutMs = 8000;
        private int readTimeoutMs = 8000;

        @Override // com.google.android.exoplayer2.upstream.HttpDataSource.Factory
        public /* bridge */ /* synthetic */ HttpDataSource.Factory setDefaultRequestProperties(Map map) {
            return setDefaultRequestProperties((Map<String, String>) map);
        }

        @Override // com.google.android.exoplayer2.upstream.HttpDataSource.Factory
        @Deprecated
        public final HttpDataSource.RequestProperties getDefaultRequestProperties() {
            return this.defaultRequestProperties;
        }

        @Override // com.google.android.exoplayer2.upstream.HttpDataSource.Factory
        public final Factory setDefaultRequestProperties(Map<String, String> map) {
            this.defaultRequestProperties.clearAndSet(map);
            return this;
        }

        public Factory setUserAgent(String str) {
            this.userAgent = str;
            return this;
        }

        public Factory setConnectTimeoutMs(int i) {
            this.connectTimeoutMs = i;
            return this;
        }

        public Factory setReadTimeoutMs(int i) {
            this.readTimeoutMs = i;
            return this;
        }

        public Factory setAllowCrossProtocolRedirects(boolean z) {
            this.allowCrossProtocolRedirects = z;
            return this;
        }

        public Factory setContentTypePredicate(Predicate<String> predicate) {
            this.contentTypePredicate = predicate;
            return this;
        }

        public Factory setTransferListener(TransferListener transferListener) {
            this.transferListener = transferListener;
            return this;
        }

        @Override // com.google.android.exoplayer2.upstream.HttpDataSource.Factory, com.google.android.exoplayer2.upstream.DataSource.Factory
        public DefaultHttpDataSource createDataSource() {
            DefaultHttpDataSource defaultHttpDataSource = new DefaultHttpDataSource(this.userAgent, this.connectTimeoutMs, this.readTimeoutMs, this.allowCrossProtocolRedirects, this.defaultRequestProperties, this.contentTypePredicate);
            TransferListener transferListener = this.transferListener;
            if (transferListener != null) {
                defaultHttpDataSource.addTransferListener(transferListener);
            }
            return defaultHttpDataSource;
        }
    }

    @Deprecated
    public DefaultHttpDataSource() {
        this(null, 8000, 8000);
    }

    @Deprecated
    public DefaultHttpDataSource(String str) {
        this(str, 8000, 8000);
    }

    @Deprecated
    public DefaultHttpDataSource(String str, int i, int i2) {
        this(str, i, i2, false, null);
    }

    @Deprecated
    public DefaultHttpDataSource(String str, int i, int i2, boolean z, HttpDataSource.RequestProperties requestProperties) {
        this(str, i, i2, z, requestProperties, null);
    }

    private DefaultHttpDataSource(String str, int i, int i2, boolean z, HttpDataSource.RequestProperties requestProperties, Predicate<String> predicate) {
        super(true);
        this.userAgent = str;
        this.connectTimeoutMillis = i;
        this.readTimeoutMillis = i2;
        this.allowCrossProtocolRedirects = z;
        this.defaultRequestProperties = requestProperties;
        this.contentTypePredicate = predicate;
        this.requestProperties = new HttpDataSource.RequestProperties();
    }

    @Deprecated
    public void setContentTypePredicate(Predicate<String> predicate) {
        this.contentTypePredicate = predicate;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public Uri getUri() {
        HttpURLConnection httpURLConnection = this.connection;
        if (httpURLConnection == null) {
            return null;
        }
        return Uri.parse(httpURLConnection.getURL().toString());
    }

    @Override // com.google.android.exoplayer2.upstream.HttpDataSource
    public int getResponseCode() {
        int i;
        if (this.connection == null || (i = this.responseCode) <= 0) {
            return -1;
        }
        return i;
    }

    @Override // com.google.android.exoplayer2.upstream.BaseDataSource, com.google.android.exoplayer2.upstream.DataSource
    public Map<String, List<String>> getResponseHeaders() {
        HttpURLConnection httpURLConnection = this.connection;
        return httpURLConnection == null ? Collections.emptyMap() : httpURLConnection.getHeaderFields();
    }

    @Override // com.google.android.exoplayer2.upstream.HttpDataSource
    public void setRequestProperty(String str, String str2) {
        Assertions.checkNotNull(str);
        Assertions.checkNotNull(str2);
        this.requestProperties.set(str, str2);
    }

    @Override // com.google.android.exoplayer2.upstream.HttpDataSource
    public void clearRequestProperty(String str) {
        Assertions.checkNotNull(str);
        this.requestProperties.remove(str);
    }

    @Override // com.google.android.exoplayer2.upstream.HttpDataSource
    public void clearAllRequestProperties() {
        this.requestProperties.clear();
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public long open(DataSpec dataSpec) throws HttpDataSource.HttpDataSourceException {
        byte[] bArr;
        this.dataSpec = dataSpec;
        long j = 0;
        this.bytesRead = 0L;
        this.bytesSkipped = 0L;
        transferInitializing(dataSpec);
        try {
            HttpURLConnection makeConnection = makeConnection(dataSpec);
            this.connection = makeConnection;
            try {
                this.responseCode = makeConnection.getResponseCode();
                String responseMessage = makeConnection.getResponseMessage();
                int i = this.responseCode;
                if (i < 200 || i > 299) {
                    Map<String, List<String>> headerFields = makeConnection.getHeaderFields();
                    InputStream errorStream = makeConnection.getErrorStream();
                    try {
                        bArr = errorStream != null ? Util.toByteArray(errorStream) : Util.EMPTY_BYTE_ARRAY;
                    } catch (IOException unused) {
                        bArr = Util.EMPTY_BYTE_ARRAY;
                    }
                    byte[] bArr2 = bArr;
                    closeConnectionQuietly();
                    HttpDataSource.InvalidResponseCodeException invalidResponseCodeException = new HttpDataSource.InvalidResponseCodeException(this.responseCode, responseMessage, headerFields, dataSpec, bArr2);
                    if (this.responseCode == 416) {
                        invalidResponseCodeException.initCause(new DataSourceException(0));
                    }
                    throw invalidResponseCodeException;
                }
                String contentType = makeConnection.getContentType();
                Predicate<String> predicate = this.contentTypePredicate;
                if (predicate != null && !predicate.apply(contentType)) {
                    closeConnectionQuietly();
                    throw new HttpDataSource.InvalidContentTypeException(contentType, dataSpec);
                }
                if (this.responseCode == 200 && dataSpec.position != 0) {
                    j = dataSpec.position;
                }
                this.bytesToSkip = j;
                boolean isCompressed = isCompressed(makeConnection);
                if (!isCompressed) {
                    if (dataSpec.length != -1) {
                        this.bytesToRead = dataSpec.length;
                    } else {
                        long contentLength = getContentLength(makeConnection);
                        this.bytesToRead = contentLength != -1 ? contentLength - this.bytesToSkip : -1L;
                    }
                } else {
                    this.bytesToRead = dataSpec.length;
                }
                try {
                    this.inputStream = makeConnection.getInputStream();
                    if (isCompressed) {
                        this.inputStream = new GZIPInputStream(this.inputStream);
                    }
                    this.opened = true;
                    transferStarted(dataSpec);
                    return this.bytesToRead;
                } catch (IOException e) {
                    closeConnectionQuietly();
                    throw new HttpDataSource.HttpDataSourceException(e, dataSpec, 1);
                }
            } catch (IOException e2) {
                closeConnectionQuietly();
                throw new HttpDataSource.HttpDataSourceException("Unable to connect", e2, dataSpec, 1);
            }
        } catch (IOException e3) {
            String message = e3.getMessage();
            if (message != null && Util.toLowerInvariant(message).matches("cleartext http traffic.*not permitted.*")) {
                throw new HttpDataSource.CleartextNotPermittedException(e3, dataSpec);
            }
            throw new HttpDataSource.HttpDataSourceException("Unable to connect", e3, dataSpec, 1);
        }
    }

    @Override // com.google.android.exoplayer2.upstream.DataReader
    public int read(byte[] bArr, int i, int i2) throws HttpDataSource.HttpDataSourceException {
        try {
            skipInternal();
            return readInternal(bArr, i, i2);
        } catch (IOException e) {
            throw new HttpDataSource.HttpDataSourceException(e, (DataSpec) Util.castNonNull(this.dataSpec), 2);
        }
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void close() throws HttpDataSource.HttpDataSourceException {
        try {
            InputStream inputStream = this.inputStream;
            if (inputStream != null) {
                maybeTerminateInputStream(this.connection, bytesRemaining());
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new HttpDataSource.HttpDataSourceException(e, (DataSpec) Util.castNonNull(this.dataSpec), 3);
                }
            }
        } finally {
            this.inputStream = null;
            closeConnectionQuietly();
            if (this.opened) {
                this.opened = false;
                transferEnded();
            }
        }
    }

    protected final HttpURLConnection getConnection() {
        return this.connection;
    }

    protected final long bytesSkipped() {
        return this.bytesSkipped;
    }

    protected final long bytesRead() {
        return this.bytesRead;
    }

    protected final long bytesRemaining() {
        long j = this.bytesToRead;
        return j == -1 ? j : j - this.bytesRead;
    }

    private HttpURLConnection makeConnection(DataSpec dataSpec) throws IOException {
        HttpURLConnection makeConnection;
        DataSpec dataSpec2 = dataSpec;
        URL url = new URL(dataSpec2.uri.toString());
        int i = dataSpec2.httpMethod;
        byte[] bArr = dataSpec2.httpBody;
        long j = dataSpec2.position;
        long j2 = dataSpec2.length;
        boolean isFlagSet = dataSpec2.isFlagSet(1);
        if (this.allowCrossProtocolRedirects) {
            int i2 = 0;
            while (true) {
                int i3 = i2 + 1;
                if (i2 <= 20) {
                    byte[] bArr2 = bArr;
                    long j3 = j2;
                    long j4 = j;
                    makeConnection = makeConnection(url, i, bArr, j, j2, isFlagSet, false, dataSpec2.httpRequestHeaders);
                    int responseCode = makeConnection.getResponseCode();
                    String headerField = makeConnection.getHeaderField("Location");
                    if ((i == 1 || i == 3) && (responseCode == 300 || responseCode == 301 || responseCode == 302 || responseCode == 303 || responseCode == 307 || responseCode == 308)) {
                        makeConnection.disconnect();
                        url = handleRedirect(url, headerField);
                    } else if (i != 2 || (responseCode != 300 && responseCode != 301 && responseCode != 302 && responseCode != 303)) {
                        break;
                    } else {
                        makeConnection.disconnect();
                        url = handleRedirect(url, headerField);
                        bArr2 = null;
                        i = 1;
                    }
                    i2 = i3;
                    bArr = bArr2;
                    j2 = j3;
                    j = j4;
                    dataSpec2 = dataSpec;
                } else {
                    throw new NoRouteToHostException("Too many redirects: " + i3);
                }
            }
            return makeConnection;
        }
        return makeConnection(url, i, bArr, j, j2, isFlagSet, true, dataSpec2.httpRequestHeaders);
    }

    private HttpURLConnection makeConnection(URL url, int i, byte[] bArr, long j, long j2, boolean z, boolean z2, Map<String, String> map) throws IOException {
        HttpURLConnection openConnection = openConnection(url);
        openConnection.setConnectTimeout(this.connectTimeoutMillis);
        openConnection.setReadTimeout(this.readTimeoutMillis);
        HashMap hashMap = new HashMap();
        HttpDataSource.RequestProperties requestProperties = this.defaultRequestProperties;
        if (requestProperties != null) {
            hashMap.putAll(requestProperties.getSnapshot());
        }
        hashMap.putAll(this.requestProperties.getSnapshot());
        hashMap.putAll(map);
        for (Map.Entry entry : hashMap.entrySet()) {
            openConnection.setRequestProperty((String) entry.getKey(), (String) entry.getValue());
        }
        if (j != 0 || j2 != -1) {
            String str = "bytes=" + j + "-";
            if (j2 != -1) {
                str = str + ((j + j2) - 1);
            }
            openConnection.setRequestProperty("Range", str);
        }
        String str2 = this.userAgent;
        if (str2 != null) {
            openConnection.setRequestProperty("User-Agent", str2);
        }
        openConnection.setRequestProperty(HttpHeaders.ACCEPT_ENCODING, z ? "gzip" : "identity");
        openConnection.setInstanceFollowRedirects(z2);
        openConnection.setDoOutput(bArr != null);
        openConnection.setRequestMethod(DataSpec.getStringForHttpMethod(i));
        if (bArr != null) {
            openConnection.setFixedLengthStreamingMode(bArr.length);
            openConnection.connect();
            OutputStream outputStream = openConnection.getOutputStream();
            outputStream.write(bArr);
            outputStream.close();
        } else {
            openConnection.connect();
        }
        return openConnection;
    }

    HttpURLConnection openConnection(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }

    private static URL handleRedirect(URL url, String str) throws IOException {
        if (str == null) {
            throw new ProtocolException("Null location redirect");
        }
        URL url2 = new URL(url, str);
        String protocol = url2.getProtocol();
        if ("https".equals(protocol) || "http".equals(protocol)) {
            return url2;
        }
        throw new ProtocolException("Unsupported protocol redirect: " + protocol);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static long getContentLength(java.net.HttpURLConnection r11) {
        /*
            java.lang.String r0 = "Inconsistent headers ["
            java.lang.String r1 = "Content-Length"
            java.lang.String r1 = r11.getHeaderField(r1)
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            java.lang.String r3 = "]"
            java.lang.String r4 = "DefaultHttpDataSource"
            if (r2 != 0) goto L2b
            long r5 = java.lang.Long.parseLong(r1)     // Catch: java.lang.NumberFormatException -> L17
            goto L2d
        L17:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r5 = "Unexpected Content-Length ["
            r2.<init>(r5)
            r2.append(r1)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.google.android.exoplayer2.util.Log.e(r4, r2)
        L2b:
            r5 = -1
        L2d:
            java.lang.String r2 = "Content-Range"
            java.lang.String r11 = r11.getHeaderField(r2)
            boolean r2 = android.text.TextUtils.isEmpty(r11)
            if (r2 != 0) goto La7
            java.util.regex.Pattern r2 = com.google.android.exoplayer2.upstream.DefaultHttpDataSource.CONTENT_RANGE_HEADER
            java.util.regex.Matcher r2 = r2.matcher(r11)
            boolean r7 = r2.find()
            if (r7 == 0) goto La7
            r7 = 2
            java.lang.String r7 = r2.group(r7)     // Catch: java.lang.NumberFormatException -> L93
            java.lang.Object r7 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r7)     // Catch: java.lang.NumberFormatException -> L93
            java.lang.String r7 = (java.lang.String) r7     // Catch: java.lang.NumberFormatException -> L93
            long r7 = java.lang.Long.parseLong(r7)     // Catch: java.lang.NumberFormatException -> L93
            r9 = 1
            java.lang.String r2 = r2.group(r9)     // Catch: java.lang.NumberFormatException -> L93
            java.lang.Object r2 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r2)     // Catch: java.lang.NumberFormatException -> L93
            java.lang.String r2 = (java.lang.String) r2     // Catch: java.lang.NumberFormatException -> L93
            long r9 = java.lang.Long.parseLong(r2)     // Catch: java.lang.NumberFormatException -> L93
            long r7 = r7 - r9
            r9 = 1
            long r7 = r7 + r9
            r9 = 0
            int r2 = (r5 > r9 ? 1 : (r5 == r9 ? 0 : -1))
            if (r2 >= 0) goto L6f
            r5 = r7
            goto La7
        L6f:
            int r2 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r2 == 0) goto La7
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.NumberFormatException -> L93
            r2.<init>(r0)     // Catch: java.lang.NumberFormatException -> L93
            r2.append(r1)     // Catch: java.lang.NumberFormatException -> L93
            java.lang.String r0 = "] ["
            r2.append(r0)     // Catch: java.lang.NumberFormatException -> L93
            r2.append(r11)     // Catch: java.lang.NumberFormatException -> L93
            r2.append(r3)     // Catch: java.lang.NumberFormatException -> L93
            java.lang.String r0 = r2.toString()     // Catch: java.lang.NumberFormatException -> L93
            com.google.android.exoplayer2.util.Log.w(r4, r0)     // Catch: java.lang.NumberFormatException -> L93
            long r0 = java.lang.Math.max(r5, r7)     // Catch: java.lang.NumberFormatException -> L93
            r5 = r0
            goto La7
        L93:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Unexpected Content-Range ["
            r0.<init>(r1)
            r0.append(r11)
            r0.append(r3)
            java.lang.String r11 = r0.toString()
            com.google.android.exoplayer2.util.Log.e(r4, r11)
        La7:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.DefaultHttpDataSource.getContentLength(java.net.HttpURLConnection):long");
    }

    private void skipInternal() throws IOException {
        if (this.bytesSkipped == this.bytesToSkip) {
            return;
        }
        if (this.skipBuffer == null) {
            this.skipBuffer = new byte[4096];
        }
        while (true) {
            long j = this.bytesSkipped;
            long j2 = this.bytesToSkip;
            if (j == j2) {
                return;
            }
            int read = ((InputStream) Util.castNonNull(this.inputStream)).read(this.skipBuffer, 0, (int) Math.min(j2 - j, this.skipBuffer.length));
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedIOException();
            }
            if (read == -1) {
                throw new EOFException();
            }
            this.bytesSkipped += read;
            bytesTransferred(read);
        }
    }

    private int readInternal(byte[] bArr, int i, int i2) throws IOException {
        if (i2 == 0) {
            return 0;
        }
        long j = this.bytesToRead;
        if (j != -1) {
            long j2 = j - this.bytesRead;
            if (j2 == 0) {
                return -1;
            }
            i2 = (int) Math.min(i2, j2);
        }
        int read = ((InputStream) Util.castNonNull(this.inputStream)).read(bArr, i, i2);
        if (read == -1) {
            if (this.bytesToRead == -1) {
                return -1;
            }
            throw new EOFException();
        }
        this.bytesRead += read;
        bytesTransferred(read);
        return read;
    }

    private static void maybeTerminateInputStream(HttpURLConnection httpURLConnection, long j) {
        if (httpURLConnection != null && Util.SDK_INT >= 19 && Util.SDK_INT <= 20) {
            try {
                InputStream inputStream = httpURLConnection.getInputStream();
                if (j == -1) {
                    if (inputStream.read() == -1) {
                        return;
                    }
                } else if (j <= 2048) {
                    return;
                }
                String name = inputStream.getClass().getName();
                if (!"com.android.okhttp.internal.http.HttpTransport$ChunkedInputStream".equals(name) && !"com.android.okhttp.internal.http.HttpTransport$FixedLengthInputStream".equals(name)) {
                    return;
                }
                Method declaredMethod = ((Class) Assertions.checkNotNull(inputStream.getClass().getSuperclass())).getDeclaredMethod("unexpectedEndOfInput", new Class[0]);
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(inputStream, new Object[0]);
            } catch (Exception unused) {
            }
        }
    }

    private void closeConnectionQuietly() {
        HttpURLConnection httpURLConnection = this.connection;
        if (httpURLConnection != null) {
            try {
                httpURLConnection.disconnect();
            } catch (Exception e) {
                Log.e(TAG, "Unexpected error while disconnecting", e);
            }
            this.connection = null;
        }
    }

    private static boolean isCompressed(HttpURLConnection httpURLConnection) {
        return "gzip".equalsIgnoreCase(httpURLConnection.getHeaderField("Content-Encoding"));
    }
}
