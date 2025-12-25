package androidx.leanback.widget;

import android.util.SparseArray;
/* loaded from: classes.dex */
public class SparseArrayObjectAdapter extends ObjectAdapter {
    private SparseArray<Object> mItems;

    @Override // androidx.leanback.widget.ObjectAdapter
    public boolean isImmediateNotifySupported() {
        return true;
    }

    public SparseArrayObjectAdapter(PresenterSelector presenterSelector) {
        super(presenterSelector);
        this.mItems = new SparseArray<>();
    }

    public SparseArrayObjectAdapter(Presenter presenter) {
        super(presenter);
        this.mItems = new SparseArray<>();
    }

    public SparseArrayObjectAdapter() {
        this.mItems = new SparseArray<>();
    }

    @Override // androidx.leanback.widget.ObjectAdapter
    public int size() {
        return this.mItems.size();
    }

    @Override // androidx.leanback.widget.ObjectAdapter
    public Object get(int i) {
        return this.mItems.valueAt(i);
    }

    public int indexOf(Object obj) {
        return this.mItems.indexOfValue(obj);
    }

    public int indexOf(int i) {
        return this.mItems.indexOfKey(i);
    }

    public void notifyArrayItemRangeChanged(int i, int i2) {
        notifyItemRangeChanged(i, i2);
    }

    public void set(int i, Object obj) {
        int indexOfKey = this.mItems.indexOfKey(i);
        if (indexOfKey >= 0) {
            if (this.mItems.valueAt(indexOfKey) != obj) {
                this.mItems.setValueAt(indexOfKey, obj);
                notifyItemRangeChanged(indexOfKey, 1);
                return;
            }
            return;
        }
        this.mItems.append(i, obj);
        notifyItemRangeInserted(this.mItems.indexOfKey(i), 1);
    }

    public void clear(int i) {
        int indexOfKey = this.mItems.indexOfKey(i);
        if (indexOfKey >= 0) {
            this.mItems.removeAt(indexOfKey);
            notifyItemRangeRemoved(indexOfKey, 1);
        }
    }

    public void clear() {
        int size = this.mItems.size();
        if (size == 0) {
            return;
        }
        this.mItems.clear();
        notifyItemRangeRemoved(0, size);
    }

    public Object lookup(int i) {
        return this.mItems.get(i);
    }
}
