package com.tvhome.app.requests;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.SafeContinuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.Dispatchers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/* compiled from: ReleaseRequest.kt */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0005¢\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010\u0005J\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0004H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\b"}, d2 = {"Lcom/pjy/koreatv/requests/ReleaseRequest;", "", "()V", "fetchRelease", "Lcom/pjy/koreatv/requests/ReleaseResponse;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRelease", "Companion", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class ReleaseRequest {
    public static final Companion Companion = new Companion(null);
    private static final String TAG = "ReleaseRequest";

    public final Object getRelease(Continuation<? super ReleaseResponse> continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new ReleaseRequest$getRelease$2(this, null), continuation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object fetchRelease(Continuation<? super ReleaseResponse> continuation) {
        SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt.intercepted(continuation));
        final SafeContinuation safeContinuation2 = safeContinuation;
        HttpClient.INSTANCE.getReleaseService().getRelease().enqueue(new Callback<ReleaseResponse>() { // from class: com.pjy.koreatv.requests.ReleaseRequest$fetchRelease$2$1
            @Override // retrofit2.Callback
            public void onResponse(Call<ReleaseResponse> call, Response<ReleaseResponse> response) {
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(response, "response");
                if (response.isSuccessful()) {
                    Continuation<ReleaseResponse> continuation2 = safeContinuation2;
                    Result.Companion companion = Result.Companion;
                    continuation2.resumeWith(Result.m536constructorimpl(response.body()));
                    return;
                }
                Continuation<ReleaseResponse> continuation3 = safeContinuation2;
                Result.Companion companion2 = Result.Companion;
                continuation3.resumeWith(Result.m536constructorimpl(null));
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<ReleaseResponse> call, Throwable t) {
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(t, "t");
                Continuation<ReleaseResponse> continuation2 = safeContinuation2;
                Result.Companion companion = Result.Companion;
                continuation2.resumeWith(Result.m536constructorimpl(null));
            }
        });
        Object orThrow = safeContinuation.getOrThrow();
        if (orThrow == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return orThrow;
    }

    /* compiled from: ReleaseRequest.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/pjy/koreatv/requests/ReleaseRequest$Companion;", "", "()V", "TAG", "", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
