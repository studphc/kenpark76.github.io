package retrofit2;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: KotlinExtensions.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "retrofit2.KotlinExtensions", f = "KotlinExtensions.kt", i = {0}, l = {119}, m = "suspendAndThrow", n = {"$this$suspendAndThrow"}, s = {"L$0"})
/* loaded from: classes3.dex */
public final class KotlinExtensions$suspendAndThrow$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;

    /* JADX INFO: Access modifiers changed from: package-private */
    public KotlinExtensions$suspendAndThrow$1(Continuation<? super KotlinExtensions$suspendAndThrow$1> continuation) {
        super(continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return KotlinExtensions.suspendAndThrow(null, this);
    }
}
