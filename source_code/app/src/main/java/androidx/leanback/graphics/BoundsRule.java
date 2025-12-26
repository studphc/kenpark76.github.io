package androidx.leanback.graphics;

import android.graphics.Rect;
/* loaded from: classes.dex */
public class BoundsRule {
    public ValueRule bottom;
    public ValueRule left;
    public ValueRule right;
    public ValueRule top;

    /* loaded from: classes.dex */
    public static final class ValueRule {
        int mAbsoluteValue;
        float mFraction;

        public static ValueRule inheritFromParent(float f) {
            return new ValueRule(0, f);
        }

        public static ValueRule absoluteValue(int i) {
            return new ValueRule(i, 0.0f);
        }

        public static ValueRule inheritFromParentWithOffset(float f, int i) {
            return new ValueRule(i, f);
        }

        ValueRule(int i, float f) {
            this.mAbsoluteValue = i;
            this.mFraction = f;
        }

        ValueRule(ValueRule valueRule) {
            this.mFraction = valueRule.mFraction;
            this.mAbsoluteValue = valueRule.mAbsoluteValue;
        }

        public void setFraction(float f) {
            this.mFraction = f;
        }

        public float getFraction() {
            return this.mFraction;
        }

        public void setAbsoluteValue(int i) {
            this.mAbsoluteValue = i;
        }

        public int getAbsoluteValue() {
            return this.mAbsoluteValue;
        }
    }

    public void calculateBounds(Rect rect, Rect rect2) {
        if (this.left == null) {
            rect2.left = rect.left;
        } else {
            rect2.left = doCalculate(rect.left, this.left, rect.width());
        }
        if (this.right == null) {
            rect2.right = rect.right;
        } else {
            rect2.right = doCalculate(rect.left, this.right, rect.width());
        }
        if (this.top == null) {
            rect2.top = rect.top;
        } else {
            rect2.top = doCalculate(rect.top, this.top, rect.height());
        }
        if (this.bottom == null) {
            rect2.bottom = rect.bottom;
        } else {
            rect2.bottom = doCalculate(rect.top, this.bottom, rect.height());
        }
    }

    public BoundsRule() {
    }

    public BoundsRule(BoundsRule boundsRule) {
        ValueRule valueRule = boundsRule.left;
        this.left = valueRule != null ? new ValueRule(valueRule) : null;
        ValueRule valueRule2 = boundsRule.right;
        this.right = valueRule2 != null ? new ValueRule(valueRule2) : null;
        ValueRule valueRule3 = boundsRule.top;
        this.top = valueRule3 != null ? new ValueRule(valueRule3) : null;
        ValueRule valueRule4 = boundsRule.bottom;
        this.bottom = valueRule4 != null ? new ValueRule(valueRule4) : null;
    }

    private int doCalculate(int i, ValueRule valueRule, int i2) {
        return i + valueRule.mAbsoluteValue + ((int) (valueRule.mFraction * i2));
    }
}
