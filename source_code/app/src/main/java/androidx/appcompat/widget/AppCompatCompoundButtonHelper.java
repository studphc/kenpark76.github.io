package androidx.appcompat.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.widget.CompoundButton;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.CompoundButtonCompat;
/* loaded from: classes.dex */
class AppCompatCompoundButtonHelper {
    private ColorStateList mButtonTintList = null;
    private PorterDuff.Mode mButtonTintMode = null;
    private boolean mHasButtonTint = false;
    private boolean mHasButtonTintMode = false;
    private boolean mSkipNextApply;
    private final CompoundButton mView;

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getCompoundPaddingLeft(int i) {
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AppCompatCompoundButtonHelper(CompoundButton compoundButton) {
        this.mView = compoundButton;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0067 A[Catch: all -> 0x008f, TryCatch #1 {all -> 0x008f, blocks: (B:3:0x001f, B:5:0x0027, B:7:0x002f, B:12:0x0042, B:14:0x004a, B:16:0x0052, B:17:0x005f, B:19:0x0067, B:20:0x0072, B:22:0x007a), top: B:30:0x001f }] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x007a A[Catch: all -> 0x008f, TRY_LEAVE, TryCatch #1 {all -> 0x008f, blocks: (B:3:0x001f, B:5:0x0027, B:7:0x002f, B:12:0x0042, B:14:0x004a, B:16:0x0052, B:17:0x005f, B:19:0x0067, B:20:0x0072, B:22:0x007a), top: B:30:0x001f }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void loadFromAttributes(android.util.AttributeSet r11, int r12) {
        /*
            r10 = this;
            android.widget.CompoundButton r0 = r10.mView
            android.content.Context r0 = r0.getContext()
            int[] r1 = androidx.appcompat.R.styleable.CompoundButton
            r2 = 0
            androidx.appcompat.widget.TintTypedArray r0 = androidx.appcompat.widget.TintTypedArray.obtainStyledAttributes(r0, r11, r1, r12, r2)
            android.widget.CompoundButton r3 = r10.mView
            android.content.Context r4 = r3.getContext()
            int[] r5 = androidx.appcompat.R.styleable.CompoundButton
            android.content.res.TypedArray r7 = r0.getWrappedTypeArray()
            r9 = 0
            r6 = r11
            r8 = r12
            androidx.core.view.ViewCompat.saveAttributeDataForStyleable(r3, r4, r5, r6, r7, r8, r9)
            int r11 = androidx.appcompat.R.styleable.CompoundButton_buttonCompat     // Catch: java.lang.Throwable -> L8f
            boolean r11 = r0.hasValue(r11)     // Catch: java.lang.Throwable -> L8f
            if (r11 == 0) goto L3f
            int r11 = androidx.appcompat.R.styleable.CompoundButton_buttonCompat     // Catch: java.lang.Throwable -> L8f
            int r11 = r0.getResourceId(r11, r2)     // Catch: java.lang.Throwable -> L8f
            if (r11 == 0) goto L3f
            android.widget.CompoundButton r12 = r10.mView     // Catch: android.content.res.Resources.NotFoundException -> L3e java.lang.Throwable -> L8f
            android.content.Context r1 = r12.getContext()     // Catch: android.content.res.Resources.NotFoundException -> L3e java.lang.Throwable -> L8f
            android.graphics.drawable.Drawable r11 = androidx.appcompat.content.res.AppCompatResources.getDrawable(r1, r11)     // Catch: android.content.res.Resources.NotFoundException -> L3e java.lang.Throwable -> L8f
            r12.setButtonDrawable(r11)     // Catch: android.content.res.Resources.NotFoundException -> L3e java.lang.Throwable -> L8f
            r11 = 1
            goto L40
        L3e:
        L3f:
            r11 = 0
        L40:
            if (r11 != 0) goto L5f
            int r11 = androidx.appcompat.R.styleable.CompoundButton_android_button     // Catch: java.lang.Throwable -> L8f
            boolean r11 = r0.hasValue(r11)     // Catch: java.lang.Throwable -> L8f
            if (r11 == 0) goto L5f
            int r11 = androidx.appcompat.R.styleable.CompoundButton_android_button     // Catch: java.lang.Throwable -> L8f
            int r11 = r0.getResourceId(r11, r2)     // Catch: java.lang.Throwable -> L8f
            if (r11 == 0) goto L5f
            android.widget.CompoundButton r12 = r10.mView     // Catch: java.lang.Throwable -> L8f
            android.content.Context r1 = r12.getContext()     // Catch: java.lang.Throwable -> L8f
            android.graphics.drawable.Drawable r11 = androidx.appcompat.content.res.AppCompatResources.getDrawable(r1, r11)     // Catch: java.lang.Throwable -> L8f
            r12.setButtonDrawable(r11)     // Catch: java.lang.Throwable -> L8f
        L5f:
            int r11 = androidx.appcompat.R.styleable.CompoundButton_buttonTint     // Catch: java.lang.Throwable -> L8f
            boolean r11 = r0.hasValue(r11)     // Catch: java.lang.Throwable -> L8f
            if (r11 == 0) goto L72
            android.widget.CompoundButton r11 = r10.mView     // Catch: java.lang.Throwable -> L8f
            int r12 = androidx.appcompat.R.styleable.CompoundButton_buttonTint     // Catch: java.lang.Throwable -> L8f
            android.content.res.ColorStateList r12 = r0.getColorStateList(r12)     // Catch: java.lang.Throwable -> L8f
            androidx.core.widget.CompoundButtonCompat.setButtonTintList(r11, r12)     // Catch: java.lang.Throwable -> L8f
        L72:
            int r11 = androidx.appcompat.R.styleable.CompoundButton_buttonTintMode     // Catch: java.lang.Throwable -> L8f
            boolean r11 = r0.hasValue(r11)     // Catch: java.lang.Throwable -> L8f
            if (r11 == 0) goto L8b
            android.widget.CompoundButton r11 = r10.mView     // Catch: java.lang.Throwable -> L8f
            int r12 = androidx.appcompat.R.styleable.CompoundButton_buttonTintMode     // Catch: java.lang.Throwable -> L8f
            r1 = -1
            int r12 = r0.getInt(r12, r1)     // Catch: java.lang.Throwable -> L8f
            r1 = 0
            android.graphics.PorterDuff$Mode r12 = androidx.appcompat.widget.DrawableUtils.parseTintMode(r12, r1)     // Catch: java.lang.Throwable -> L8f
            androidx.core.widget.CompoundButtonCompat.setButtonTintMode(r11, r12)     // Catch: java.lang.Throwable -> L8f
        L8b:
            r0.recycle()
            return
        L8f:
            r11 = move-exception
            r0.recycle()
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.AppCompatCompoundButtonHelper.loadFromAttributes(android.util.AttributeSet, int):void");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setSupportButtonTintList(ColorStateList colorStateList) {
        this.mButtonTintList = colorStateList;
        this.mHasButtonTint = true;
        applyButtonTint();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ColorStateList getSupportButtonTintList() {
        return this.mButtonTintList;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setSupportButtonTintMode(PorterDuff.Mode mode) {
        this.mButtonTintMode = mode;
        this.mHasButtonTintMode = true;
        applyButtonTint();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public PorterDuff.Mode getSupportButtonTintMode() {
        return this.mButtonTintMode;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onSetButtonDrawable() {
        if (this.mSkipNextApply) {
            this.mSkipNextApply = false;
            return;
        }
        this.mSkipNextApply = true;
        applyButtonTint();
    }

    void applyButtonTint() {
        Drawable buttonDrawable = CompoundButtonCompat.getButtonDrawable(this.mView);
        if (buttonDrawable != null) {
            if (this.mHasButtonTint || this.mHasButtonTintMode) {
                Drawable mutate = DrawableCompat.wrap(buttonDrawable).mutate();
                if (this.mHasButtonTint) {
                    DrawableCompat.setTintList(mutate, this.mButtonTintList);
                }
                if (this.mHasButtonTintMode) {
                    DrawableCompat.setTintMode(mutate, this.mButtonTintMode);
                }
                if (mutate.isStateful()) {
                    mutate.setState(this.mView.getDrawableState());
                }
                this.mView.setButtonDrawable(mutate);
            }
        }
    }
}
