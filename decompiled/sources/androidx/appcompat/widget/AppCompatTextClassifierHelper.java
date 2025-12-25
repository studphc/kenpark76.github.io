package androidx.appcompat.widget;

import android.view.textclassifier.TextClassificationManager;
import android.view.textclassifier.TextClassifier;
import android.widget.TextView;
import androidx.core.util.Preconditions;
/* loaded from: classes.dex */
final class AppCompatTextClassifierHelper {
    private TextClassifier mTextClassifier;
    private TextView mTextView;

    /* JADX INFO: Access modifiers changed from: package-private */
    public AppCompatTextClassifierHelper(TextView textView) {
        this.mTextView = (TextView) Preconditions.checkNotNull(textView);
    }

    public void setTextClassifier(TextClassifier textClassifier) {
        this.mTextClassifier = textClassifier;
    }

    public TextClassifier getTextClassifier() {
        Object systemService;
        TextClassifier textClassifier;
        TextClassifier textClassifier2;
        TextClassifier textClassifier3 = this.mTextClassifier;
        if (textClassifier3 == null) {
            systemService = this.mTextView.getContext().getSystemService(SearchView$$ExternalSyntheticApiModelOutline0.m11m());
            TextClassificationManager m9m = SearchView$$ExternalSyntheticApiModelOutline0.m9m(systemService);
            if (m9m != null) {
                textClassifier2 = m9m.getTextClassifier();
                return textClassifier2;
            }
            textClassifier = TextClassifier.NO_OP;
            return textClassifier;
        }
        return textClassifier3;
    }
}
