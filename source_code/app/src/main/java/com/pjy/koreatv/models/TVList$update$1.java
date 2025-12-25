package com.pjy.koreatv.models;

import android.util.Log;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import com.pjy.koreatv.ExtKt;
import com.pjy.koreatv.MyTVApplication;
import com.pjy.koreatv.R;
import com.pjy.koreatv.SP;
import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: TVList.kt */
@Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 9, 0})
@DebugMetadata(c = "com.pjy.koreatv.models.TVList$update$1", f = "TVList.kt", i = {0, 0, 1}, l = {186, 304}, m = "invokeSuspend", n = {"serverUrlParts", "serverUrlPart", "serverUrlParts"}, s = {"L$0", "L$2", "L$0"})
/* loaded from: classes3.dex */
public final class TVList$update$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX INFO: Access modifiers changed from: package-private */
    public TVList$update$1(Continuation<? super TVList$update$1> continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new TVList$update$1(continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((TVList$update$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01c0  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x01fd  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0249  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0272  */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v20 */
    /* JADX WARN: Type inference failed for: r3v21 */
    /* JADX WARN: Type inference failed for: r3v22 */
    /* JADX WARN: Type inference failed for: r3v23 */
    /* JADX WARN: Type inference failed for: r3v24 */
    /* JADX WARN: Type inference failed for: r3v25 */
    /* JADX WARN: Type inference failed for: r3v27 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:53:0x018a -> B:68:0x01fa). Please submit an issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:66:0x01f4 -> B:67:0x01f5). Please submit an issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:83:0x028b -> B:68:0x01fa). Please submit an issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r19) {
        /*
            Method dump skipped, instructions count: 657
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.pjy.koreatv.models.TVList$update$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: TVList.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\u0010\u0002\u001a\u0004\u0018\u00010\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 9, 0})
    @DebugMetadata(c = "com.pjy.koreatv.models.TVList$update$1$1", f = "TVList.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.pjy.koreatv.models.TVList$update$1$1  reason: invalid class name */
    /* loaded from: classes3.dex */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Object>, Object> {
        final /* synthetic */ File $file;
        final /* synthetic */ String $serverUrlPart;
        final /* synthetic */ List<String> $serverUrlParts;
        final /* synthetic */ String $str;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(String str, File file, String str2, List<String> list, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$str = str;
            this.$file = file;
            this.$serverUrlPart = str2;
            this.$serverUrlParts = list;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$str, this.$file, this.$serverUrlPart, this.$serverUrlParts, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(CoroutineScope coroutineScope, Continuation<? super Object> continuation) {
            return invoke2(coroutineScope, (Continuation<Object>) continuation);
        }

        /* renamed from: invoke  reason: avoid collision after fix types in other method */
        public final Object invoke2(CoroutineScope coroutineScope, Continuation<Object> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            String str;
            String str2;
            MutableLiveData mutableLiveData;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            TVList tVList = TVList.INSTANCE;
            String str3 = this.$str;
            Intrinsics.checkNotNullExpressionValue(str3, "$str");
            if (tVList.str2List(str3)) {
                File file = this.$file;
                String str4 = this.$str;
                Intrinsics.checkNotNullExpressionValue(str4, "$str");
                FilesKt.writeText$default(file, str4, null, 2, null);
                SP sp = SP.INSTANCE;
                str = TVList.serverUrl;
                if (str == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("serverUrl");
                    str = null;
                }
                sp.setConfig(str);
                Log.e("TVList", "Channel Update Success " + this.$serverUrlPart);
                str2 = TVList.epg;
                String str5 = str2;
                if (!(str5 == null || str5.length() == 0)) {
                    BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new C00291(null), 3, null);
                }
                TVList.INSTANCE.setChannelUpdateSuccess(true);
                if (!TVList.INSTANCE.getFirstBoot()) {
                    mutableLiveData = TVList._position;
                    Integer num = (Integer) mutableLiveData.getValue();
                    if (num != null) {
                        String valueOf = String.valueOf(TVList.INSTANCE.getTVModel(num.intValue()).m490getVideoUrl().getValue());
                        if (!Intrinsics.areEqual(valueOf, TVList.INSTANCE.getVideoUrlBeforeUpdate()) || StringsKt.contains$default((CharSequence) valueOf, (CharSequence) "tving.com", false, 2, (Object) null)) {
                            TVList.INSTANCE.setPosition(num.intValue());
                            TVList.INSTANCE.setVideoUrlBeforeUpdate(valueOf);
                        }
                        return Unit.INSTANCE;
                    }
                    return null;
                }
                return Boxing.boxBoolean(TVList.INSTANCE.setPosition(0));
            }
            if (Intrinsics.areEqual(this.$serverUrlPart, CollectionsKt.last((List<? extends Object>) this.$serverUrlParts))) {
                String string = ContextCompat.getString(MyTVApplication.Companion.getInstance(), R.string.channel_request_error);
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                ExtKt.showToast$default(string, 0, 1, null);
                TVList.INSTANCE.setEpgReceived(true);
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: TVList.kt */
        @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 9, 0})
        @DebugMetadata(c = "com.pjy.koreatv.models.TVList$update$1$1$1", f = "TVList.kt", i = {}, l = {198}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.pjy.koreatv.models.TVList$update$1$1$1  reason: invalid class name and collision with other inner class name */
        /* loaded from: classes3.dex */
        public static final class C00291 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;

            C00291(Continuation<? super C00291> continuation) {
                super(2, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00291(continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00291) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Object updateEPG;
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    this.label = 1;
                    updateEPG = TVList.INSTANCE.updateEPG(this);
                    if (updateEPG == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                } else {
                    ResultKt.throwOnFailure(obj);
                }
                return Unit.INSTANCE;
            }
        }
    }
}
