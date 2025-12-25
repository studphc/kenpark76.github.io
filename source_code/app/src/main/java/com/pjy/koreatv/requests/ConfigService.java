package com.pjy.koreatv.requests;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import retrofit2.Call;
import retrofit2.http.Url;
/* compiled from: ConfigService.kt */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0004H&¨\u0006\u0006"}, d2 = {"Lcom/pjy/koreatv/requests/ConfigService;", "", "getConfig", "Lretrofit2/Call;", "", "url", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public interface ConfigService {
    Call<String> getConfig(@Url String str);
}
