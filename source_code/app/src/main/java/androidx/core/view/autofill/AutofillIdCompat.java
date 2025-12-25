package androidx.core.view.autofill;

import android.view.autofill.AutofillId;
import androidx.leanback.app.FragmentUtil$$ExternalSyntheticApiModelOutline0;
/* loaded from: classes.dex */
public class AutofillIdCompat {
    private final Object mWrappedObj;

    private AutofillIdCompat(AutofillId autofillId) {
        this.mWrappedObj = autofillId;
    }

    public static AutofillIdCompat toAutofillIdCompat(AutofillId autofillId) {
        return new AutofillIdCompat(autofillId);
    }

    public AutofillId toAutofillId() {
        return FragmentUtil$$ExternalSyntheticApiModelOutline0.m130m(this.mWrappedObj);
    }
}
