package com.pjy.koreatv;

import com.google.gson.Gson;
import com.pjy.koreatv.requests.TimeResponse;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
/* compiled from: Utils.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 9, 0})
@DebugMetadata(c = "com.pjy.koreatv.Utils$getTimestampFromServer$2", f = "Utils.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes3.dex */
final class Utils$getTimestampFromServer$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Long>, Object> {
    int label;

    /* JADX INFO: Access modifiers changed from: package-private */
    public Utils$getTimestampFromServer$2(Continuation<? super Utils$getTimestampFromServer$2> continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new Utils$getTimestampFromServer$2(continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Long> continuation) {
        return ((Utils$getTimestampFromServer$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        try {
            Response execute = new OkHttpClient.Builder().build().newCall(new Request.Builder().url("https://api.m.taobao.com/rest/api3.do?api=mtop.common.getTimestamp").build()).execute();
            Response response = execute;
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            ResponseBody body = response.body();
            long parseLong = Long.parseLong(((TimeResponse) new Gson().fromJson(body != null ? body.string() : null, (Class<Object>) TimeResponse.class)).getData().getT());
            CloseableKt.closeFinally(execute, null);
            return Boxing.boxLong(parseLong);
        } catch (IOException e) {
            throw new IOException("Error during network request", e);
        }
    }
}
