package com.tvhome.app.requests;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import retrofit2.Call;
import retrofit2.http.GET;
/* compiled from: ReleaseService.kt */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H'¨\u0006\u0005"}, d2 = {"Lcom/pjy/koreatv/requests/ReleaseService;", "", "getRelease", "Lretrofit2/Call;", "Lcom/pjy/koreatv/requests/ReleaseResponse;", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public interface ReleaseService {
    @GET("main/version.json")
    Call<ReleaseResponse> getRelease();
}
