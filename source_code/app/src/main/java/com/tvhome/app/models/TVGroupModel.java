package com.tvhome.app.models;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.tvhome.app.SP;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: TVGroupModel.kt */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\nJ\u0006\u0010\u0016\u001a\u00020\u0014J\b\u0010\u0017\u001a\u0004\u0018\u00010\nJ\u0010\u0010\u0017\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0018\u001a\u00020\u0007J\u0006\u0010\u0019\u001a\u00020\u0014J\u000e\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u000f\u001a\u00020\u0007J\u0014\u0010\u001b\u001a\u00020\u00142\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\n0\tJ\u0006\u0010\u001d\u001a\u00020\u0007R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\f8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\f8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u000eR\u001d\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\f8F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u000e¨\u0006\u001e"}, d2 = {"Lcom/pjy/koreatv/models/TVGroupModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_change", "Landroidx/lifecycle/MutableLiveData;", "", "_position", "", "_tvGroupModel", "", "Lcom/pjy/koreatv/models/TVListModel;", "change", "Landroidx/lifecycle/LiveData;", "getChange", "()Landroidx/lifecycle/LiveData;", "position", "getPosition", "tvGroupModel", "getTvGroupModel", "addTVListModel", "", "tvListModel", "clear2", "getTVListModel", "idx", "setChange", "setPosition", "setTVListModelList", "tvListModelList", "size", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class TVGroupModel extends ViewModel {
    private final MutableLiveData<Boolean> _change;
    private final MutableLiveData<Integer> _position;
    private final MutableLiveData<List<TVListModel>> _tvGroupModel = new MutableLiveData<>();

    public TVGroupModel() {
        MutableLiveData<Integer> mutableLiveData = new MutableLiveData<>();
        this._position = mutableLiveData;
        this._change = new MutableLiveData<>();
        mutableLiveData.setValue(Integer.valueOf(SP.INSTANCE.getPositionGroup()));
    }

    public final LiveData<List<TVListModel>> getTvGroupModel() {
        return this._tvGroupModel;
    }

    public final LiveData<Integer> getPosition() {
        return this._position;
    }

    public final LiveData<Boolean> getChange() {
        return this._change;
    }

    public final void setPosition(int i) {
        this._position.setValue(Integer.valueOf(i));
    }

    public final void setChange() {
        this._change.setValue(true);
    }

    public final void setTVListModelList(List<TVListModel> tvListModelList) {
        Intrinsics.checkNotNullParameter(tvListModelList, "tvListModelList");
        this._tvGroupModel.setValue(tvListModelList);
    }

    public final void addTVListModel(TVListModel tvListModel) {
        Intrinsics.checkNotNullParameter(tvListModel, "tvListModel");
        if (this._tvGroupModel.getValue() == null) {
            this._tvGroupModel.setValue(CollectionsKt.mutableListOf(tvListModel));
            return;
        }
        List<TVListModel> value = this._tvGroupModel.getValue();
        Intrinsics.checkNotNull(value);
        List<TVListModel> mutableList = CollectionsKt.toMutableList((Collection) value);
        mutableList.add(tvListModel);
        this._tvGroupModel.setValue(mutableList);
    }

    public final void clear2() {
        MutableLiveData<List<TVListModel>> mutableLiveData = this._tvGroupModel;
        TVListModel tVListModel = getTVListModel(0);
        Intrinsics.checkNotNull(tVListModel);
        TVListModel tVListModel2 = getTVListModel(1);
        Intrinsics.checkNotNull(tVListModel2);
        mutableLiveData.setValue(CollectionsKt.mutableListOf(tVListModel, tVListModel2));
        setPosition(0);
        TVListModel tVListModel3 = getTVListModel(1);
        if (tVListModel3 != null) {
            tVListModel3.clear2();
        }
    }

    public final TVListModel getTVListModel() {
        Integer value = getPosition().getValue();
        Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Int");
        return getTVListModel(value.intValue());
    }

    public final TVListModel getTVListModel(int i) {
        List<TVListModel> value;
        if (i < size() && (value = this._tvGroupModel.getValue()) != null) {
            return value.get(i);
        }
        return null;
    }

    public final int size() {
        if (this._tvGroupModel.getValue() == null) {
            return 0;
        }
        List<TVListModel> value = this._tvGroupModel.getValue();
        Intrinsics.checkNotNull(value);
        return value.size();
    }
}
