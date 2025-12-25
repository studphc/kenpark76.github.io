package androidx.leanback.graphics;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.SparseArray;
/* loaded from: classes.dex */
public final class ColorFilterCache {
    private static final SparseArray<ColorFilterCache> sColorToFiltersMap = new SparseArray<>();
    private final PorterDuffColorFilter[] mFilters = new PorterDuffColorFilter[256];

    public static ColorFilterCache getColorFilterCache(int i) {
        int red = Color.red(i);
        int green = Color.green(i);
        int blue = Color.blue(i);
        int rgb = Color.rgb(red, green, blue);
        SparseArray<ColorFilterCache> sparseArray = sColorToFiltersMap;
        ColorFilterCache colorFilterCache = sparseArray.get(rgb);
        if (colorFilterCache == null) {
            ColorFilterCache colorFilterCache2 = new ColorFilterCache(red, green, blue);
            sparseArray.put(rgb, colorFilterCache2);
            return colorFilterCache2;
        }
        return colorFilterCache;
    }

    private ColorFilterCache(int i, int i2, int i3) {
        for (int i4 = 0; i4 <= 255; i4++) {
            this.mFilters[i4] = new PorterDuffColorFilter(Color.argb(i4, i, i2, i3), PorterDuff.Mode.SRC_ATOP);
        }
    }

    public ColorFilter getFilterForLevel(float f) {
        if (f < 0.0f || f > 1.0d) {
            return null;
        }
        return this.mFilters[(int) (f * 255.0f)];
    }
}
