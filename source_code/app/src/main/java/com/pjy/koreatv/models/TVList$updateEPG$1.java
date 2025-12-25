package com.pjy.koreatv.models;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: TVList.kt */
@Metadata(k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@DebugMetadata(c = "com.pjy.koreatv.models.TVList", f = "TVList.kt", i = {0, 0}, l = {107}, m = "updateEPG", n = {"epgParts", "epgPart"}, s = {"L$0", "L$2"})
/* loaded from: classes3.dex */
public final class TVList$updateEPG$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ TVList this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TVList$updateEPG$1(TVList tVList, Continuation<? super TVList$updateEPG$1> continuation) {
        super(continuation);
        this.this$0 = tVList;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object updateEPG;
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        updateEPG = this.this$0.updateEPG(this);
        return updateEPG;
    }
}
