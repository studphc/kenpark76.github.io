package com.tvhome.app;

import android.graphics.Bitmap;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.util.Hashtable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: QrCodeUtil.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002JR\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\u00062\b\b\u0002\u0010\u000b\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\u00062\b\b\u0003\u0010\r\u001a\u00020\b2\b\b\u0003\u0010\u000e\u001a\u00020\b¨\u0006\u000f"}, d2 = {"Lcom/pjy/koreatv/QrCodeUtil;", "", "()V", "createQRCodeBitmap", "Landroid/graphics/Bitmap;", "content", "", "width", "", "height", "characterSet", "errorCorrection", "margin", "colorBlack", "colorWhite", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class QrCodeUtil {
    public final Bitmap createQRCodeBitmap(String content, int i, int i2, String characterSet, String errorCorrection, String margin, int i3, int i4) {
        Intrinsics.checkNotNullParameter(content, "content");
        Intrinsics.checkNotNullParameter(characterSet, "characterSet");
        Intrinsics.checkNotNullParameter(errorCorrection, "errorCorrection");
        Intrinsics.checkNotNullParameter(margin, "margin");
        if (i >= 0 && i2 >= 0) {
            try {
                Hashtable hashtable = new Hashtable();
                boolean z = true;
                if (characterSet.length() > 0) {
                    hashtable.put(EncodeHintType.CHARACTER_SET, characterSet);
                }
                if (errorCorrection.length() > 0) {
                    hashtable.put(EncodeHintType.ERROR_CORRECTION, errorCorrection);
                }
                if (margin.length() <= 0) {
                    z = false;
                }
                if (z) {
                    hashtable.put(EncodeHintType.MARGIN, margin);
                }
                BitMatrix encode = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, i, i2, hashtable);
                int[] iArr = new int[i * i2];
                for (int i5 = 0; i5 < i2; i5++) {
                    for (int i6 = 0; i6 < i; i6++) {
                        if (encode.get(i6, i5)) {
                            iArr[(i5 * i) + i6] = i3;
                        } else {
                            iArr[(i5 * i) + i6] = i4;
                        }
                    }
                }
                Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
                Intrinsics.checkNotNullExpressionValue(createBitmap, "createBitmap(...)");
                createBitmap.setPixels(iArr, 0, i, 0, 0, i, i2);
                return createBitmap;
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
