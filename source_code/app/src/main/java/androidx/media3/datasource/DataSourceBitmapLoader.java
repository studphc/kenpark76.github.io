package androidx.media3.datasource;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import androidx.exifinterface.media.ExifInterface;
import androidx.media3.common.MediaMetadata;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.BitmapLoader;
import androidx.media3.common.util.Util;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DefaultDataSource;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
/* loaded from: classes.dex */
public final class DataSourceBitmapLoader implements BitmapLoader {
    public static final Supplier<ListeningExecutorService> DEFAULT_EXECUTOR_SERVICE = Suppliers.memoize(new Supplier() { // from class: androidx.media3.datasource.DataSourceBitmapLoader$$ExternalSyntheticLambda1
        @Override // com.google.common.base.Supplier
        public final Object get() {
            ListeningExecutorService listeningDecorator;
            listeningDecorator = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
            return listeningDecorator;
        }
    });
    private final DataSource.Factory dataSourceFactory;
    private final ListeningExecutorService listeningExecutorService;
    private final BitmapFactory.Options options;

    @Override // androidx.media3.common.util.BitmapLoader
    public /* synthetic */ ListenableFuture loadBitmapFromMetadata(MediaMetadata mediaMetadata) {
        return BitmapLoader.CC.$default$loadBitmapFromMetadata(this, mediaMetadata);
    }

    public DataSourceBitmapLoader(Context context) {
        this((ListeningExecutorService) Assertions.checkStateNotNull(DEFAULT_EXECUTOR_SERVICE.get()), new DefaultDataSource.Factory(context));
    }

    public DataSourceBitmapLoader(ListeningExecutorService listeningExecutorService, DataSource.Factory factory) {
        this(listeningExecutorService, factory, null);
    }

    public DataSourceBitmapLoader(ListeningExecutorService listeningExecutorService, DataSource.Factory factory, BitmapFactory.Options options) {
        this.listeningExecutorService = listeningExecutorService;
        this.dataSourceFactory = factory;
        this.options = options;
    }

    @Override // androidx.media3.common.util.BitmapLoader
    public boolean supportsMimeType(String str) {
        return Util.isBitmapFactorySupportedMimeType(str);
    }

    @Override // androidx.media3.common.util.BitmapLoader
    public ListenableFuture<Bitmap> decodeBitmap(final byte[] bArr) {
        return this.listeningExecutorService.submit(new Callable() { // from class: androidx.media3.datasource.DataSourceBitmapLoader$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return DataSourceBitmapLoader.this.m173xcc09b2d8(bArr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$decodeBitmap$1$androidx-media3-datasource-DataSourceBitmapLoader  reason: not valid java name */
    public /* synthetic */ Bitmap m173xcc09b2d8(byte[] bArr) throws Exception {
        return decode(bArr, this.options);
    }

    @Override // androidx.media3.common.util.BitmapLoader
    public ListenableFuture<Bitmap> loadBitmap(final Uri uri) {
        return this.listeningExecutorService.submit(new Callable() { // from class: androidx.media3.datasource.DataSourceBitmapLoader$$ExternalSyntheticLambda2
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return DataSourceBitmapLoader.this.m174x731908d1(uri);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$loadBitmap$2$androidx-media3-datasource-DataSourceBitmapLoader  reason: not valid java name */
    public /* synthetic */ Bitmap m174x731908d1(Uri uri) throws Exception {
        return load(this.dataSourceFactory.createDataSource(), uri, this.options);
    }

    private static Bitmap decode(byte[] bArr, BitmapFactory.Options options) throws IOException {
        Bitmap decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
        Assertions.checkArgument(decodeByteArray != null, "Could not decode image data");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        try {
            ExifInterface exifInterface = new ExifInterface(byteArrayInputStream);
            byteArrayInputStream.close();
            int rotationDegrees = exifInterface.getRotationDegrees();
            if (rotationDegrees != 0) {
                Matrix matrix = new Matrix();
                matrix.postRotate(rotationDegrees);
                return Bitmap.createBitmap(decodeByteArray, 0, 0, decodeByteArray.getWidth(), decodeByteArray.getHeight(), matrix, false);
            }
            return decodeByteArray;
        } catch (Throwable th) {
            try {
                byteArrayInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private static Bitmap load(DataSource dataSource, Uri uri, BitmapFactory.Options options) throws IOException {
        try {
            dataSource.open(new DataSpec(uri));
            return decode(DataSourceUtil.readToEnd(dataSource), options);
        } finally {
            dataSource.close();
        }
    }
}
