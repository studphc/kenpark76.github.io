package androidx.leanback.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.widget.TextView;
import androidx.core.widget.TextViewCompat;
import androidx.leanback.R;
/* loaded from: classes.dex */
class ResizingTextView extends TextView {
    public static final int TRIGGER_MAX_LINES = 1;
    private float mDefaultLineSpacingExtra;
    private int mDefaultPaddingBottom;
    private int mDefaultPaddingTop;
    private int mDefaultTextSize;
    private boolean mDefaultsInitialized;
    private boolean mIsResized;
    private boolean mMaintainLineSpacing;
    private int mResizedPaddingAdjustmentBottom;
    private int mResizedPaddingAdjustmentTop;
    private int mResizedTextSize;
    private int mTriggerConditions;

    public ResizingTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i);
        this.mIsResized = false;
        this.mDefaultsInitialized = false;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.lbResizingTextView, i, i2);
        try {
            this.mTriggerConditions = obtainStyledAttributes.getInt(R.styleable.lbResizingTextView_resizeTrigger, 1);
            this.mResizedTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.lbResizingTextView_resizedTextSize, -1);
            this.mMaintainLineSpacing = obtainStyledAttributes.getBoolean(R.styleable.lbResizingTextView_maintainLineSpacing, false);
            this.mResizedPaddingAdjustmentTop = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.lbResizingTextView_resizedPaddingAdjustmentTop, 0);
            this.mResizedPaddingAdjustmentBottom = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.lbResizingTextView_resizedPaddingAdjustmentBottom, 0);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    public ResizingTextView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ResizingTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842884);
    }

    public ResizingTextView(Context context) {
        this(context, null);
    }

    public int getTriggerConditions() {
        return this.mTriggerConditions;
    }

    public void setTriggerConditions(int i) {
        if (this.mTriggerConditions != i) {
            this.mTriggerConditions = i;
            requestLayout();
        }
    }

    public int getResizedTextSize() {
        return this.mResizedTextSize;
    }

    public void setResizedTextSize(int i) {
        if (this.mResizedTextSize != i) {
            this.mResizedTextSize = i;
            resizeParamsChanged();
        }
    }

    public boolean getMaintainLineSpacing() {
        return this.mMaintainLineSpacing;
    }

    public void setMaintainLineSpacing(boolean z) {
        if (this.mMaintainLineSpacing != z) {
            this.mMaintainLineSpacing = z;
            resizeParamsChanged();
        }
    }

    public int getResizedPaddingAdjustmentTop() {
        return this.mResizedPaddingAdjustmentTop;
    }

    public void setResizedPaddingAdjustmentTop(int i) {
        if (this.mResizedPaddingAdjustmentTop != i) {
            this.mResizedPaddingAdjustmentTop = i;
            resizeParamsChanged();
        }
    }

    public int getResizedPaddingAdjustmentBottom() {
        return this.mResizedPaddingAdjustmentBottom;
    }

    public void setResizedPaddingAdjustmentBottom(int i) {
        if (this.mResizedPaddingAdjustmentBottom != i) {
            this.mResizedPaddingAdjustmentBottom = i;
            resizeParamsChanged();
        }
    }

    private void resizeParamsChanged() {
        if (this.mIsResized) {
            requestLayout();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:51:? A[RETURN, SYNTHETIC] */
    @Override // android.widget.TextView, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void onMeasure(int r7, int r8) {
        /*
            Method dump skipped, instructions count: 228
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.ResizingTextView.onMeasure(int, int):void");
    }

    private void setPaddingTopAndBottom(int i, int i2) {
        if (isPaddingRelative()) {
            setPaddingRelative(getPaddingStart(), i, getPaddingEnd(), i2);
        } else {
            setPadding(getPaddingLeft(), i, getPaddingRight(), i2);
        }
    }

    @Override // android.widget.TextView
    public void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(TextViewCompat.wrapCustomSelectionActionModeCallback(this, callback));
    }
}
