package com.tvhome.app.models;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: TVListModel.kt */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u000e\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\rJ\u0006\u0010\u0019\u001a\u00020\u0017J\u0006\u0010\u001a\u001a\u00020\u0005J\u0006\u0010\u001b\u001a\u00020\u0003J\b\u0010\u001c\u001a\u0004\u0018\u00010\rJ\u0010\u0010\u001c\u001a\u0004\u0018\u00010\r2\u0006\u0010\u001d\u001a\u00020\u0005J\u000e\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u001f\u001a\u00020\u0005J\u000e\u0010 \u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\rJ\u0006\u0010!\u001a\u00020\u0017J\u000e\u0010\"\u001a\u00020\u00172\u0006\u0010\u0012\u001a\u00020\u0005J\u0014\u0010#\u001a\u00020\u00172\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\r0\fJ\u0006\u0010$\u001a\u00020\u0005R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u000f8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\u000f8F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0011R\u001d\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000f8F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0011¨\u0006%"}, d2 = {"Lcom/pjy/koreatv/models/TVListModel;", "Landroidx/lifecycle/ViewModel;", "name", "", "index", "", "(Ljava/lang/String;I)V", "_change", "Landroidx/lifecycle/MutableLiveData;", "", "_position", "_tvListModel", "", "Lcom/pjy/koreatv/models/TVModel;", "change", "Landroidx/lifecycle/LiveData;", "getChange", "()Landroidx/lifecycle/LiveData;", "position", "getPosition", "tvListModel", "getTvListModel", "addTVModel", "", "tvModel", "clear2", "getIndex", "getName", "getTVModel", "idx", "removeTVModel", "id", "replaceTVModel", "setChange", "setPosition", "setTVListModel", "size", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class TVListModel extends ViewModel {
    private final MutableLiveData<Boolean> _change;
    private final MutableLiveData<Integer> _position;
    private final MutableLiveData<List<TVModel>> _tvListModel;
    private final int index;
    private final String name;

    public TVListModel(String name, int i) {
        Intrinsics.checkNotNullParameter(name, "name");
        this.name = name;
        this.index = i;
        this._tvListModel = new MutableLiveData<>();
        MutableLiveData<Integer> mutableLiveData = new MutableLiveData<>();
        this._position = mutableLiveData;
        this._change = new MutableLiveData<>();
        mutableLiveData.setValue(0);
    }

    public final String getName() {
        return this.name;
    }

    public final int getIndex() {
        return this.index;
    }

    public final LiveData<List<TVModel>> getTvListModel() {
        return this._tvListModel;
    }

    public final LiveData<Integer> getPosition() {
        return this._position;
    }

    public final void setPosition(int i) {
        this._position.setValue(Integer.valueOf(i));
    }

    public final LiveData<Boolean> getChange() {
        return this._change;
    }

    public final void setChange() {
        this._change.setValue(true);
    }

    public final void setTVListModel(List<TVModel> tvListModel) {
        Intrinsics.checkNotNullParameter(tvListModel, "tvListModel");
        this._tvListModel.setValue(tvListModel);
    }

    public final void addTVModel(TVModel tvModel) {
        Intrinsics.checkNotNullParameter(tvModel, "tvModel");
        if (this._tvListModel.getValue() == null) {
            this._tvListModel.setValue(CollectionsKt.mutableListOf(tvModel));
            return;
        }
        List<TVModel> value = this._tvListModel.getValue();
        Intrinsics.checkNotNull(value);
        List<TVModel> mutableList = CollectionsKt.toMutableList((Collection) value);
        mutableList.add(tvModel);
        this._tvListModel.setValue(mutableList);
    }

    public final void removeTVModel(int i) {
        if (this._tvListModel.getValue() == null) {
            return;
        }
        List<TVModel> value = this._tvListModel.getValue();
        Intrinsics.checkNotNull(value);
        List<TVModel> mutableList = CollectionsKt.toMutableList((Collection) value);
        Iterator<TVModel> it = mutableList.iterator();
        while (it.hasNext()) {
            if (it.next().getTv().getId() == i) {
                it.remove();
            }
        }
        this._tvListModel.setValue(mutableList);
    }

    public final void replaceTVModel(TVModel tvModel) {
        Intrinsics.checkNotNullParameter(tvModel, "tvModel");
        boolean z = false;
        if (this._tvListModel.getValue() == null) {
            this._tvListModel.setValue(CollectionsKt.mutableListOf(tvModel));
            return;
        }
        List<TVModel> value = this._tvListModel.getValue();
        Intrinsics.checkNotNull(value);
        List<TVModel> mutableList = CollectionsKt.toMutableList((Collection) value);
        for (TVModel tVModel : mutableList) {
            if (tVModel.getTv().getId() == tvModel.getTv().getId()) {
                z = true;
            }
        }
        if (z) {
            return;
        }
        mutableList.add(tvModel);
        this._tvListModel.setValue(mutableList);
    }

    public final void clear2() {
        this._tvListModel.setValue(new ArrayList());
        setPosition(0);
    }

    public final TVModel getTVModel() {
        Integer value = getPosition().getValue();
        Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Int");
        return getTVModel(value.intValue());
    }

    public final TVModel getTVModel(int i) {
        List<TVModel> value = this._tvListModel.getValue();
        if (value != null) {
            return value.get(i);
        }
        return null;
    }

    public final int size() {
        if (this._tvListModel.getValue() == null) {
            return 0;
        }
        List<TVModel> value = this._tvListModel.getValue();
        Intrinsics.checkNotNull(value);
        return value.size();
    }
}
