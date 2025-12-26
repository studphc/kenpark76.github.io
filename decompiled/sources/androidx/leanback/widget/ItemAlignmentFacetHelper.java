package androidx.leanback.widget;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import androidx.leanback.widget.GridLayoutManager;
import androidx.leanback.widget.ItemAlignmentFacet;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class ItemAlignmentFacetHelper {
    private static Rect sRect = new Rect();

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getAlignmentPosition(View view, ItemAlignmentFacet.ItemAlignmentDef itemAlignmentDef, int i) {
        View view2;
        int i2;
        int width;
        int width2;
        int width3;
        GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        if (itemAlignmentDef.mViewId == 0 || (view2 = view.findViewById(itemAlignmentDef.mViewId)) == null) {
            view2 = view;
        }
        int i3 = itemAlignmentDef.mOffset;
        if (i == 0) {
            if (view.getLayoutDirection() == 1) {
                if (view2 == view) {
                    width2 = layoutParams.getOpticalWidth(view2);
                } else {
                    width2 = view2.getWidth();
                }
                int i4 = width2 - i3;
                if (itemAlignmentDef.mOffsetWithPadding) {
                    if (itemAlignmentDef.mOffsetPercent == 0.0f) {
                        i4 -= view2.getPaddingRight();
                    } else if (itemAlignmentDef.mOffsetPercent == 100.0f) {
                        i4 += view2.getPaddingLeft();
                    }
                }
                if (itemAlignmentDef.mOffsetPercent != -1.0f) {
                    if (view2 == view) {
                        width3 = layoutParams.getOpticalWidth(view2);
                    } else {
                        width3 = view2.getWidth();
                    }
                    i4 -= (int) ((width3 * itemAlignmentDef.mOffsetPercent) / 100.0f);
                }
                if (view != view2) {
                    sRect.right = i4;
                    ((ViewGroup) view).offsetDescendantRectToMyCoords(view2, sRect);
                    return sRect.right + layoutParams.getOpticalRightInset();
                }
                return i4;
            }
            if (itemAlignmentDef.mOffsetWithPadding) {
                if (itemAlignmentDef.mOffsetPercent == 0.0f) {
                    i3 += view2.getPaddingLeft();
                } else if (itemAlignmentDef.mOffsetPercent == 100.0f) {
                    i3 -= view2.getPaddingRight();
                }
            }
            if (itemAlignmentDef.mOffsetPercent != -1.0f) {
                if (view2 == view) {
                    width = layoutParams.getOpticalWidth(view2);
                } else {
                    width = view2.getWidth();
                }
                i3 += (int) ((width * itemAlignmentDef.mOffsetPercent) / 100.0f);
            }
            int i5 = i3;
            if (view != view2) {
                sRect.left = i5;
                ((ViewGroup) view).offsetDescendantRectToMyCoords(view2, sRect);
                return sRect.left - layoutParams.getOpticalLeftInset();
            }
            return i5;
        }
        if (itemAlignmentDef.mOffsetWithPadding) {
            if (itemAlignmentDef.mOffsetPercent == 0.0f) {
                i3 += view2.getPaddingTop();
            } else if (itemAlignmentDef.mOffsetPercent == 100.0f) {
                i3 -= view2.getPaddingBottom();
            }
        }
        if (itemAlignmentDef.mOffsetPercent != -1.0f) {
            i3 += (int) (((view2 == view ? layoutParams.getOpticalHeight(view2) : view2.getHeight()) * itemAlignmentDef.mOffsetPercent) / 100.0f);
        }
        if (view != view2) {
            sRect.top = i3;
            ((ViewGroup) view).offsetDescendantRectToMyCoords(view2, sRect);
            i2 = sRect.top - layoutParams.getOpticalTopInset();
        } else {
            i2 = i3;
        }
        return itemAlignmentDef.isAlignedToTextViewBaseLine() ? i2 + view2.getBaseline() : i2;
    }

    private ItemAlignmentFacetHelper() {
    }
}
