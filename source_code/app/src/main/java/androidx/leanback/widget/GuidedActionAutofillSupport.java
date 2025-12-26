package androidx.leanback.widget;

import android.view.View;
/* loaded from: classes.dex */
public interface GuidedActionAutofillSupport {

    /* loaded from: classes.dex */
    public interface OnAutofillListener {
        void onAutofill(View view);
    }

    void setOnAutofillListener(OnAutofillListener onAutofillListener);
}
