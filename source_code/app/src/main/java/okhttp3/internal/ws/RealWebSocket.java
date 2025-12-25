package okhttp3.internal.ws;

import com.google.common.net.HttpHeaders;
import java.io.Closeable;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.EventListener;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.connection.Exchange;
import okhttp3.internal.ws.WebSocketReader;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
/* loaded from: classes3.dex */
public final class RealWebSocket implements WebSocket, WebSocketReader.FrameCallback {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long CANCEL_AFTER_CLOSE_MILLIS = 60000;
    private static final long MAX_QUEUE_SIZE = 16777216;
    private static final List<Protocol> ONLY_HTTP1 = Collections.singletonList(Protocol.HTTP_1_1);
    private boolean awaitingPong;
    private Call call;
    private ScheduledFuture<?> cancelFuture;
    private boolean enqueuedClose;
    private ScheduledExecutorService executor;
    private boolean failed;
    private final String key;
    final WebSocketListener listener;
    private final Request originalRequest;
    private final long pingIntervalMillis;
    private long queueSize;
    private final Random random;
    private WebSocketReader reader;
    private String receivedCloseReason;
    private int receivedPingCount;
    private int receivedPongCount;
    private int sentPingCount;
    private Streams streams;
    private WebSocketWriter writer;
    private final Runnable writerRunnable;
    private final ArrayDeque<ByteString> pongQueue = new ArrayDeque<>();
    private final ArrayDeque<Object> messageAndCloseQueue = new ArrayDeque<>();
    private int receivedCloseCode = -1;

    public RealWebSocket(Request request, WebSocketListener webSocketListener, Random random, long j) {
        if (!"GET".equals(request.method())) {
            throw new IllegalArgumentException("Request must be GET: " + request.method());
        }
        this.originalRequest = request;
        this.listener = webSocketListener;
        this.random = random;
        this.pingIntervalMillis = j;
        byte[] bArr = new byte[16];
        random.nextBytes(bArr);
        this.key = ByteString.of(bArr).base64();
        this.writerRunnable = new Runnable() { // from class: okhttp3.internal.ws.RealWebSocket$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                RealWebSocket.this.m2109lambda$new$0$okhttp3internalwsRealWebSocket();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$new$0$okhttp3-internal-ws-RealWebSocket  reason: not valid java name */
    public /* synthetic */ void m2109lambda$new$0$okhttp3internalwsRealWebSocket() {
        do {
            try {
            } catch (IOException e) {
                failWebSocket(e, null);
                return;
            }
        } while (writeOneFrame());
    }

    @Override // okhttp3.WebSocket
    public Request request() {
        return this.originalRequest;
    }

    @Override // okhttp3.WebSocket
    public synchronized long queueSize() {
        return this.queueSize;
    }

    @Override // okhttp3.WebSocket
    public void cancel() {
        this.call.cancel();
    }

    public void connect(OkHttpClient okHttpClient) {
        OkHttpClient build = okHttpClient.newBuilder().eventListener(EventListener.NONE).protocols(ONLY_HTTP1).build();
        final Request build2 = this.originalRequest.newBuilder().header(HttpHeaders.UPGRADE, "websocket").header("Connection", HttpHeaders.UPGRADE).header(HttpHeaders.SEC_WEBSOCKET_KEY, this.key).header(HttpHeaders.SEC_WEBSOCKET_VERSION, "13").build();
        Call newWebSocketCall = Internal.instance.newWebSocketCall(build, build2);
        this.call = newWebSocketCall;
        newWebSocketCall.enqueue(new Callback() { // from class: okhttp3.internal.ws.RealWebSocket.1
            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) {
                Exchange exchange = Internal.instance.exchange(response);
                try {
                    RealWebSocket.this.checkUpgradeSuccess(response, exchange);
                    Streams newWebSocketStreams = exchange.newWebSocketStreams();
                    try {
                        RealWebSocket.this.initReaderAndWriter("OkHttp WebSocket " + build2.url().redact(), newWebSocketStreams);
                        RealWebSocket.this.listener.onOpen(RealWebSocket.this, response);
                        RealWebSocket.this.loopReader();
                    } catch (Exception e) {
                        RealWebSocket.this.failWebSocket(e, null);
                    }
                } catch (IOException e2) {
                    if (exchange != null) {
                        exchange.webSocketUpgradeFailed();
                    }
                    RealWebSocket.this.failWebSocket(e2, response);
                    Util.closeQuietly(response);
                }
            }

            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                RealWebSocket.this.failWebSocket(iOException, null);
            }
        });
    }

    void checkUpgradeSuccess(Response response, @Nullable Exchange exchange) throws IOException {
        if (response.code() != 101) {
            throw new ProtocolException("Expected HTTP 101 response but was '" + response.code() + " " + response.message() + "'");
        }
        String header = response.header("Connection");
        if (!HttpHeaders.UPGRADE.equalsIgnoreCase(header)) {
            throw new ProtocolException("Expected 'Connection' header value 'Upgrade' but was '" + header + "'");
        }
        String header2 = response.header(HttpHeaders.UPGRADE);
        if (!"websocket".equalsIgnoreCase(header2)) {
            throw new ProtocolException("Expected 'Upgrade' header value 'websocket' but was '" + header2 + "'");
        }
        String header3 = response.header(HttpHeaders.SEC_WEBSOCKET_ACCEPT);
        String base64 = ByteString.encodeUtf8(this.key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").sha1().base64();
        if (base64.equals(header3)) {
            if (exchange == null) {
                throw new ProtocolException("Web Socket exchange missing: bad interceptor?");
            }
            return;
        }
        throw new ProtocolException("Expected 'Sec-WebSocket-Accept' header value '" + base64 + "' but was '" + header3 + "'");
    }

    public void initReaderAndWriter(String str, Streams streams) throws IOException {
        synchronized (this) {
            this.streams = streams;
            this.writer = new WebSocketWriter(streams.client, streams.sink, this.random);
            ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, Util.threadFactory(str, false));
            this.executor = scheduledThreadPoolExecutor;
            if (this.pingIntervalMillis != 0) {
                PingRunnable pingRunnable = new PingRunnable();
                long j = this.pingIntervalMillis;
                scheduledThreadPoolExecutor.scheduleAtFixedRate(pingRunnable, j, j, TimeUnit.MILLISECONDS);
            }
            if (!this.messageAndCloseQueue.isEmpty()) {
                runWriter();
            }
        }
        this.reader = new WebSocketReader(streams.client, streams.source, this);
    }

    public void loopReader() throws IOException {
        while (this.receivedCloseCode == -1) {
            this.reader.processNextFrame();
        }
    }

    boolean processNextFrame() throws IOException {
        try {
            this.reader.processNextFrame();
            return this.receivedCloseCode == -1;
        } catch (Exception e) {
            failWebSocket(e, null);
            return false;
        }
    }

    void awaitTermination(int i, TimeUnit timeUnit) throws InterruptedException {
        this.executor.awaitTermination(i, timeUnit);
    }

    void tearDown() throws InterruptedException {
        ScheduledFuture<?> scheduledFuture = this.cancelFuture;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }
        this.executor.shutdown();
        this.executor.awaitTermination(10L, TimeUnit.SECONDS);
    }

    synchronized int sentPingCount() {
        return this.sentPingCount;
    }

    synchronized int receivedPingCount() {
        return this.receivedPingCount;
    }

    synchronized int receivedPongCount() {
        return this.receivedPongCount;
    }

    @Override // okhttp3.internal.ws.WebSocketReader.FrameCallback
    public void onReadMessage(String str) throws IOException {
        this.listener.onMessage(this, str);
    }

    @Override // okhttp3.internal.ws.WebSocketReader.FrameCallback
    public void onReadMessage(ByteString byteString) throws IOException {
        this.listener.onMessage(this, byteString);
    }

    @Override // okhttp3.internal.ws.WebSocketReader.FrameCallback
    public synchronized void onReadPing(ByteString byteString) {
        if (!this.failed && (!this.enqueuedClose || !this.messageAndCloseQueue.isEmpty())) {
            this.pongQueue.add(byteString);
            runWriter();
            this.receivedPingCount++;
        }
    }

    @Override // okhttp3.internal.ws.WebSocketReader.FrameCallback
    public synchronized void onReadPong(ByteString byteString) {
        this.receivedPongCount++;
        this.awaitingPong = false;
    }

    @Override // okhttp3.internal.ws.WebSocketReader.FrameCallback
    public void onReadClose(int i, String str) {
        Streams streams;
        if (i == -1) {
            throw new IllegalArgumentException();
        }
        synchronized (this) {
            if (this.receivedCloseCode != -1) {
                throw new IllegalStateException("already closed");
            }
            this.receivedCloseCode = i;
            this.receivedCloseReason = str;
            streams = null;
            if (this.enqueuedClose && this.messageAndCloseQueue.isEmpty()) {
                Streams streams2 = this.streams;
                this.streams = null;
                ScheduledFuture<?> scheduledFuture = this.cancelFuture;
                if (scheduledFuture != null) {
                    scheduledFuture.cancel(false);
                }
                this.executor.shutdown();
                streams = streams2;
            }
        }
        try {
            this.listener.onClosing(this, i, str);
            if (streams != null) {
                this.listener.onClosed(this, i, str);
            }
        } finally {
            Util.closeQuietly(streams);
        }
    }

    @Override // okhttp3.WebSocket
    public boolean send(String str) {
        if (str == null) {
            throw new NullPointerException("text == null");
        }
        return send(ByteString.encodeUtf8(str), 1);
    }

    @Override // okhttp3.WebSocket
    public boolean send(ByteString byteString) {
        if (byteString == null) {
            throw new NullPointerException("bytes == null");
        }
        return send(byteString, 2);
    }

    private synchronized boolean send(ByteString byteString, int i) {
        if (!this.failed && !this.enqueuedClose) {
            if (this.queueSize + byteString.size() > MAX_QUEUE_SIZE) {
                close(1001, null);
                return false;
            }
            this.queueSize += byteString.size();
            this.messageAndCloseQueue.add(new Message(i, byteString));
            runWriter();
            return true;
        }
        return false;
    }

    synchronized boolean pong(ByteString byteString) {
        if (!this.failed && (!this.enqueuedClose || !this.messageAndCloseQueue.isEmpty())) {
            this.pongQueue.add(byteString);
            runWriter();
            return true;
        }
        return false;
    }

    @Override // okhttp3.WebSocket
    public boolean close(int i, String str) {
        return close(i, str, 60000L);
    }

    synchronized boolean close(int i, String str, long j) {
        ByteString byteString;
        WebSocketProtocol.validateCloseCode(i);
        if (str != null) {
            byteString = ByteString.encodeUtf8(str);
            if (byteString.size() > 123) {
                throw new IllegalArgumentException("reason.size() > 123: " + str);
            }
        } else {
            byteString = null;
        }
        if (!this.failed && !this.enqueuedClose) {
            this.enqueuedClose = true;
            this.messageAndCloseQueue.add(new Close(i, byteString, j));
            runWriter();
            return true;
        }
        return false;
    }

    private void runWriter() {
        ScheduledExecutorService scheduledExecutorService = this.executor;
        if (scheduledExecutorService != null) {
            scheduledExecutorService.execute(this.writerRunnable);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0055 A[Catch: all -> 0x00a8, TRY_ENTER, TryCatch #1 {all -> 0x00a8, blocks: (B:24:0x0055, B:25:0x0059, B:27:0x005d, B:28:0x0079, B:35:0x0089, B:37:0x008d, B:39:0x0098, B:42:0x00a2, B:43:0x00a7, B:29:0x007a, B:30:0x0084), top: B:50:0x0053 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0059 A[Catch: all -> 0x00a8, TryCatch #1 {all -> 0x00a8, blocks: (B:24:0x0055, B:25:0x0059, B:27:0x005d, B:28:0x0079, B:35:0x0089, B:37:0x008d, B:39:0x0098, B:42:0x00a2, B:43:0x00a7, B:29:0x007a, B:30:0x0084), top: B:50:0x0053 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    boolean writeOneFrame() throws java.io.IOException {
        /*
            r11 = this;
            monitor-enter(r11)
            boolean r0 = r11.failed     // Catch: java.lang.Throwable -> Lad
            r1 = 0
            if (r0 == 0) goto L8
            monitor-exit(r11)     // Catch: java.lang.Throwable -> Lad
            return r1
        L8:
            okhttp3.internal.ws.WebSocketWriter r0 = r11.writer     // Catch: java.lang.Throwable -> Lad
            java.util.ArrayDeque<okio.ByteString> r2 = r11.pongQueue     // Catch: java.lang.Throwable -> Lad
            java.lang.Object r2 = r2.poll()     // Catch: java.lang.Throwable -> Lad
            okio.ByteString r2 = (okio.ByteString) r2     // Catch: java.lang.Throwable -> Lad
            r3 = 0
            r4 = -1
            if (r2 != 0) goto L4f
            java.util.ArrayDeque<java.lang.Object> r5 = r11.messageAndCloseQueue     // Catch: java.lang.Throwable -> Lad
            java.lang.Object r5 = r5.poll()     // Catch: java.lang.Throwable -> Lad
            boolean r6 = r5 instanceof okhttp3.internal.ws.RealWebSocket.Close     // Catch: java.lang.Throwable -> Lad
            if (r6 == 0) goto L47
            int r1 = r11.receivedCloseCode     // Catch: java.lang.Throwable -> Lad
            java.lang.String r6 = r11.receivedCloseReason     // Catch: java.lang.Throwable -> Lad
            if (r1 == r4) goto L31
            okhttp3.internal.ws.RealWebSocket$Streams r4 = r11.streams     // Catch: java.lang.Throwable -> Lad
            r11.streams = r3     // Catch: java.lang.Throwable -> Lad
            java.util.concurrent.ScheduledExecutorService r3 = r11.executor     // Catch: java.lang.Throwable -> Lad
            r3.shutdown()     // Catch: java.lang.Throwable -> Lad
        L2f:
            r3 = r5
            goto L52
        L31:
            java.util.concurrent.ScheduledExecutorService r4 = r11.executor     // Catch: java.lang.Throwable -> Lad
            okhttp3.internal.ws.RealWebSocket$CancelRunnable r7 = new okhttp3.internal.ws.RealWebSocket$CancelRunnable     // Catch: java.lang.Throwable -> Lad
            r7.<init>()     // Catch: java.lang.Throwable -> Lad
            r8 = r5
            okhttp3.internal.ws.RealWebSocket$Close r8 = (okhttp3.internal.ws.RealWebSocket.Close) r8     // Catch: java.lang.Throwable -> Lad
            long r8 = r8.cancelAfterCloseMillis     // Catch: java.lang.Throwable -> Lad
            java.util.concurrent.TimeUnit r10 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch: java.lang.Throwable -> Lad
            java.util.concurrent.ScheduledFuture r4 = r4.schedule(r7, r8, r10)     // Catch: java.lang.Throwable -> Lad
            r11.cancelFuture = r4     // Catch: java.lang.Throwable -> Lad
            r4 = r3
            goto L2f
        L47:
            if (r5 != 0) goto L4b
            monitor-exit(r11)     // Catch: java.lang.Throwable -> Lad
            return r1
        L4b:
            r4 = r3
            r6 = r4
            r3 = r5
            goto L51
        L4f:
            r4 = r3
            r6 = r4
        L51:
            r1 = -1
        L52:
            monitor-exit(r11)     // Catch: java.lang.Throwable -> Lad
            if (r2 == 0) goto L59
            r0.writePong(r2)     // Catch: java.lang.Throwable -> La8
            goto L9d
        L59:
            boolean r2 = r3 instanceof okhttp3.internal.ws.RealWebSocket.Message     // Catch: java.lang.Throwable -> La8
            if (r2 == 0) goto L89
            r1 = r3
            okhttp3.internal.ws.RealWebSocket$Message r1 = (okhttp3.internal.ws.RealWebSocket.Message) r1     // Catch: java.lang.Throwable -> La8
            okio.ByteString r1 = r1.data     // Catch: java.lang.Throwable -> La8
            okhttp3.internal.ws.RealWebSocket$Message r3 = (okhttp3.internal.ws.RealWebSocket.Message) r3     // Catch: java.lang.Throwable -> La8
            int r2 = r3.formatOpcode     // Catch: java.lang.Throwable -> La8
            int r3 = r1.size()     // Catch: java.lang.Throwable -> La8
            long r5 = (long) r3     // Catch: java.lang.Throwable -> La8
            okio.Sink r0 = r0.newMessageSink(r2, r5)     // Catch: java.lang.Throwable -> La8
            okio.BufferedSink r0 = okio.Okio.buffer(r0)     // Catch: java.lang.Throwable -> La8
            r0.write(r1)     // Catch: java.lang.Throwable -> La8
            r0.close()     // Catch: java.lang.Throwable -> La8
            monitor-enter(r11)     // Catch: java.lang.Throwable -> La8
            long r2 = r11.queueSize     // Catch: java.lang.Throwable -> L86
            int r0 = r1.size()     // Catch: java.lang.Throwable -> L86
            long r0 = (long) r0     // Catch: java.lang.Throwable -> L86
            long r2 = r2 - r0
            r11.queueSize = r2     // Catch: java.lang.Throwable -> L86
            monitor-exit(r11)     // Catch: java.lang.Throwable -> L86
            goto L9d
        L86:
            r0 = move-exception
            monitor-exit(r11)     // Catch: java.lang.Throwable -> L86
            throw r0     // Catch: java.lang.Throwable -> La8
        L89:
            boolean r2 = r3 instanceof okhttp3.internal.ws.RealWebSocket.Close     // Catch: java.lang.Throwable -> La8
            if (r2 == 0) goto La2
            okhttp3.internal.ws.RealWebSocket$Close r3 = (okhttp3.internal.ws.RealWebSocket.Close) r3     // Catch: java.lang.Throwable -> La8
            int r2 = r3.code     // Catch: java.lang.Throwable -> La8
            okio.ByteString r3 = r3.reason     // Catch: java.lang.Throwable -> La8
            r0.writeClose(r2, r3)     // Catch: java.lang.Throwable -> La8
            if (r4 == 0) goto L9d
            okhttp3.WebSocketListener r0 = r11.listener     // Catch: java.lang.Throwable -> La8
            r0.onClosed(r11, r1, r6)     // Catch: java.lang.Throwable -> La8
        L9d:
            okhttp3.internal.Util.closeQuietly(r4)
            r0 = 1
            return r0
        La2:
            java.lang.AssertionError r0 = new java.lang.AssertionError     // Catch: java.lang.Throwable -> La8
            r0.<init>()     // Catch: java.lang.Throwable -> La8
            throw r0     // Catch: java.lang.Throwable -> La8
        La8:
            r0 = move-exception
            okhttp3.internal.Util.closeQuietly(r4)
            throw r0
        Lad:
            r0 = move-exception
            monitor-exit(r11)     // Catch: java.lang.Throwable -> Lad
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.ws.RealWebSocket.writeOneFrame():boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public final class PingRunnable implements Runnable {
        PingRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            RealWebSocket.this.writePingFrame();
        }
    }

    void writePingFrame() {
        synchronized (this) {
            if (this.failed) {
                return;
            }
            WebSocketWriter webSocketWriter = this.writer;
            int i = this.awaitingPong ? this.sentPingCount : -1;
            this.sentPingCount++;
            this.awaitingPong = true;
            if (i != -1) {
                failWebSocket(new SocketTimeoutException("sent ping but didn't receive pong within " + this.pingIntervalMillis + "ms (after " + (i - 1) + " successful ping/pongs)"), null);
                return;
            }
            try {
                webSocketWriter.writePing(ByteString.EMPTY);
            } catch (IOException e) {
                failWebSocket(e, null);
            }
        }
    }

    public void failWebSocket(Exception exc, @Nullable Response response) {
        synchronized (this) {
            if (this.failed) {
                return;
            }
            this.failed = true;
            Streams streams = this.streams;
            this.streams = null;
            ScheduledFuture<?> scheduledFuture = this.cancelFuture;
            if (scheduledFuture != null) {
                scheduledFuture.cancel(false);
            }
            ScheduledExecutorService scheduledExecutorService = this.executor;
            if (scheduledExecutorService != null) {
                scheduledExecutorService.shutdown();
            }
            try {
                this.listener.onFailure(this, exc, response);
            } finally {
                Util.closeQuietly(streams);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static final class Message {
        final ByteString data;
        final int formatOpcode;

        Message(int i, ByteString byteString) {
            this.formatOpcode = i;
            this.data = byteString;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static final class Close {
        final long cancelAfterCloseMillis;
        final int code;
        final ByteString reason;

        Close(int i, ByteString byteString, long j) {
            this.code = i;
            this.reason = byteString;
            this.cancelAfterCloseMillis = j;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Streams implements Closeable {
        public final boolean client;
        public final BufferedSink sink;
        public final BufferedSource source;

        public Streams(boolean z, BufferedSource bufferedSource, BufferedSink bufferedSink) {
            this.client = z;
            this.source = bufferedSource;
            this.sink = bufferedSink;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public final class CancelRunnable implements Runnable {
        CancelRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            RealWebSocket.this.cancel();
        }
    }
}
