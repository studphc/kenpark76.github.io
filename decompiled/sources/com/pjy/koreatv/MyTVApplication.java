package com.pjy.koreatv;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: MyTVApplication.kt */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\r\n\u0002\b\u0003\u0018\u0000 #2\u00020\u0001:\u0001#B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0014J\u000e\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\bJ\u0006\u0010\u0016\u001a\u00020\u0006J\b\u0010\u0017\u001a\u00020\u0011H\u0016J\u000e\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\bJ\u000e\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u0004J\u0006\u0010\u001b\u001a\u00020\bJ\u0006\u0010\u001c\u001a\u00020\bJ\u000e\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u0004J\u001a\u0010\u001f\u001a\u00020\u00112\b\b\u0002\u0010 \u001a\u00020!2\b\b\u0002\u0010\"\u001a\u00020\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lcom/pjy/koreatv/MyTVApplication;", "Landroidx/multidex/MultiDexApplication;", "()V", "density", "", "displayMetrics", "Landroid/util/DisplayMetrics;", "height", "", "ratio", "", "realDisplayMetrics", "scale", "shouldHeight", "shouldWidth", "width", "attachBaseContext", "", "base", "Landroid/content/Context;", "dp2Px", "dp", "getDisplayMetrics", "onCreate", "px2Px", "px", "px2PxFont", "shouldHeightPx", "shouldWidthPx", "sp2Px", "sp", "toast", "message", "", TypedValues.TransitionType.S_DURATION, "Companion", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class MyTVApplication extends MultiDexApplication {
    public static final Companion Companion = new Companion(null);
    private static final String TAG = "MyTVApplication";
    private static MyTVApplication instance;
    private DisplayMetrics displayMetrics;
    private int height;
    private DisplayMetrics realDisplayMetrics;
    private int shouldHeight;
    private int shouldWidth;
    private int width;
    private double ratio = 1.0d;
    private float density = 2.0f;
    private float scale = 1.0f;

    @JvmStatic
    public static final MyTVApplication getInstance() {
        return Companion.getInstance();
    }

    /* compiled from: MyTVApplication.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\u0006H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lcom/pjy/koreatv/MyTVApplication$Companion;", "", "()V", "TAG", "", "instance", "Lcom/pjy/koreatv/MyTVApplication;", "getInstance", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final MyTVApplication getInstance() {
            MyTVApplication myTVApplication = MyTVApplication.instance;
            if (myTVApplication == null) {
                Intrinsics.throwUninitializedPropertyAccessException("instance");
                return null;
            }
            return myTVApplication;
        }
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        instance = this;
        this.displayMetrics = new DisplayMetrics();
        this.realDisplayMetrics = new DisplayMetrics();
        Object systemService = getSystemService("window");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        WindowManager windowManager = (WindowManager) systemService;
        Display defaultDisplay = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = this.displayMetrics;
        DisplayMetrics displayMetrics2 = null;
        if (displayMetrics == null) {
            Intrinsics.throwUninitializedPropertyAccessException("displayMetrics");
            displayMetrics = null;
        }
        defaultDisplay.getMetrics(displayMetrics);
        Display defaultDisplay2 = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics3 = this.realDisplayMetrics;
        if (displayMetrics3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("realDisplayMetrics");
            displayMetrics3 = null;
        }
        defaultDisplay2.getRealMetrics(displayMetrics3);
        DisplayMetrics displayMetrics4 = this.realDisplayMetrics;
        if (displayMetrics4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("realDisplayMetrics");
            displayMetrics4 = null;
        }
        int i = displayMetrics4.heightPixels;
        DisplayMetrics displayMetrics5 = this.realDisplayMetrics;
        if (displayMetrics5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("realDisplayMetrics");
            displayMetrics5 = null;
        }
        if (i > displayMetrics5.widthPixels) {
            DisplayMetrics displayMetrics6 = this.realDisplayMetrics;
            if (displayMetrics6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("realDisplayMetrics");
                displayMetrics6 = null;
            }
            this.width = displayMetrics6.heightPixels;
            DisplayMetrics displayMetrics7 = this.realDisplayMetrics;
            if (displayMetrics7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("realDisplayMetrics");
                displayMetrics7 = null;
            }
            this.height = displayMetrics7.widthPixels;
        } else {
            DisplayMetrics displayMetrics8 = this.realDisplayMetrics;
            if (displayMetrics8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("realDisplayMetrics");
                displayMetrics8 = null;
            }
            this.width = displayMetrics8.widthPixels;
            DisplayMetrics displayMetrics9 = this.realDisplayMetrics;
            if (displayMetrics9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("realDisplayMetrics");
                displayMetrics9 = null;
            }
            this.height = displayMetrics9.heightPixels;
        }
        this.density = Resources.getSystem().getDisplayMetrics().density;
        DisplayMetrics displayMetrics10 = this.displayMetrics;
        if (displayMetrics10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("displayMetrics");
        } else {
            displayMetrics2 = displayMetrics10;
        }
        this.scale = displayMetrics2.scaledDensity;
        int i2 = this.width;
        int i3 = this.height;
        if (i2 / i3 < 1.7777777777777777d) {
            this.ratio = ((i2 * 2) / 1920.0d) / this.density;
            this.shouldWidth = i2;
            this.shouldHeight = (int) ((i2 * 9.0d) / 16.0d);
            return;
        }
        this.ratio = ((i3 * 2) / 1080.0d) / this.density;
        this.shouldHeight = i3;
        this.shouldWidth = (int) ((i3 * 16.0d) / 9.0d);
    }

    public final DisplayMetrics getDisplayMetrics() {
        DisplayMetrics displayMetrics = this.displayMetrics;
        if (displayMetrics == null) {
            Intrinsics.throwUninitializedPropertyAccessException("displayMetrics");
            return null;
        }
        return displayMetrics;
    }

    public static /* synthetic */ void toast$default(MyTVApplication myTVApplication, CharSequence charSequence, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
        }
        if ((i2 & 2) != 0) {
            i = 0;
        }
        myTVApplication.toast(charSequence, i);
    }

    public final void toast(final CharSequence message, final int i) {
        Intrinsics.checkNotNullParameter(message, "message");
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.pjy.koreatv.MyTVApplication$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MyTVApplication.toast$lambda$0(MyTVApplication.this, message, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void toast$lambda$0(MyTVApplication this$0, CharSequence message, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(message, "$message");
        Toast.makeText(this$0.getApplicationContext(), message, i).show();
    }

    public final int shouldWidthPx() {
        return this.shouldWidth;
    }

    public final int shouldHeightPx() {
        return this.shouldHeight;
    }

    public final int dp2Px(int i) {
        return (int) ((i * this.ratio * this.density) + 0.5f);
    }

    public final int px2Px(int i) {
        return (int) ((i * this.ratio) + 0.5f);
    }

    public final float px2PxFont(float f) {
        return (float) ((f * this.ratio) / this.scale);
    }

    public final float sp2Px(float f) {
        return (float) (f * this.ratio * this.scale);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.multidex.MultiDexApplication, android.content.ContextWrapper
    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }
}
