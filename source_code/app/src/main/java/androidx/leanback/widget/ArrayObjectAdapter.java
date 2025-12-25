package androidx.leanback.widget;

import android.util.Log;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListUpdateCallback;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
/* loaded from: classes.dex */
public class ArrayObjectAdapter extends ObjectAdapter {
    private static final Boolean DEBUG = false;
    private static final String TAG = "ArrayObjectAdapter";
    private final List mItems;
    ListUpdateCallback mListUpdateCallback;
    final List mOldItems;
    private List mUnmodifiableItems;

    @Override // androidx.leanback.widget.ObjectAdapter
    public boolean isImmediateNotifySupported() {
        return true;
    }

    public ArrayObjectAdapter(PresenterSelector presenterSelector) {
        super(presenterSelector);
        this.mItems = new ArrayList();
        this.mOldItems = new ArrayList();
    }

    public ArrayObjectAdapter(Presenter presenter) {
        super(presenter);
        this.mItems = new ArrayList();
        this.mOldItems = new ArrayList();
    }

    public ArrayObjectAdapter() {
        this.mItems = new ArrayList();
        this.mOldItems = new ArrayList();
    }

    @Override // androidx.leanback.widget.ObjectAdapter
    public int size() {
        return this.mItems.size();
    }

    @Override // androidx.leanback.widget.ObjectAdapter
    public Object get(int i) {
        return this.mItems.get(i);
    }

    public int indexOf(Object obj) {
        return this.mItems.indexOf(obj);
    }

    public void notifyArrayItemRangeChanged(int i, int i2) {
        notifyItemRangeChanged(i, i2);
    }

    public void add(Object obj) {
        add(this.mItems.size(), obj);
    }

    public void add(int i, Object obj) {
        this.mItems.add(i, obj);
        notifyItemRangeInserted(i, 1);
    }

    public void addAll(int i, Collection collection) {
        int size = collection.size();
        if (size == 0) {
            return;
        }
        this.mItems.addAll(i, collection);
        notifyItemRangeInserted(i, size);
    }

    public boolean remove(Object obj) {
        int indexOf = this.mItems.indexOf(obj);
        if (indexOf >= 0) {
            this.mItems.remove(indexOf);
            notifyItemRangeRemoved(indexOf, 1);
        }
        return indexOf >= 0;
    }

    public void move(int i, int i2) {
        if (i == i2) {
            return;
        }
        this.mItems.add(i2, this.mItems.remove(i));
        notifyItemMoved(i, i2);
    }

    public void replace(int i, Object obj) {
        this.mItems.set(i, obj);
        notifyItemRangeChanged(i, 1);
    }

    public int removeItems(int i, int i2) {
        int min = Math.min(i2, this.mItems.size() - i);
        if (min <= 0) {
            return 0;
        }
        for (int i3 = 0; i3 < min; i3++) {
            this.mItems.remove(i);
        }
        notifyItemRangeRemoved(i, min);
        return min;
    }

    public void clear() {
        int size = this.mItems.size();
        if (size == 0) {
            return;
        }
        this.mItems.clear();
        notifyItemRangeRemoved(0, size);
    }

    public <E> List<E> unmodifiableList() {
        if (this.mUnmodifiableItems == null) {
            this.mUnmodifiableItems = Collections.unmodifiableList(this.mItems);
        }
        return this.mUnmodifiableItems;
    }

    public void setItems(final List list, final DiffCallback diffCallback) {
        if (diffCallback == null) {
            this.mItems.clear();
            this.mItems.addAll(list);
            notifyChanged();
            return;
        }
        this.mOldItems.clear();
        this.mOldItems.addAll(this.mItems);
        DiffUtil.DiffResult calculateDiff = DiffUtil.calculateDiff(new DiffUtil.Callback() { // from class: androidx.leanback.widget.ArrayObjectAdapter.1
            @Override // androidx.recyclerview.widget.DiffUtil.Callback
            public int getOldListSize() {
                return ArrayObjectAdapter.this.mOldItems.size();
            }

            @Override // androidx.recyclerview.widget.DiffUtil.Callback
            public int getNewListSize() {
                return list.size();
            }

            @Override // androidx.recyclerview.widget.DiffUtil.Callback
            public boolean areItemsTheSame(int i, int i2) {
                return diffCallback.areItemsTheSame(ArrayObjectAdapter.this.mOldItems.get(i), list.get(i2));
            }

            @Override // androidx.recyclerview.widget.DiffUtil.Callback
            public boolean areContentsTheSame(int i, int i2) {
                return diffCallback.areContentsTheSame(ArrayObjectAdapter.this.mOldItems.get(i), list.get(i2));
            }

            @Override // androidx.recyclerview.widget.DiffUtil.Callback
            public Object getChangePayload(int i, int i2) {
                return diffCallback.getChangePayload(ArrayObjectAdapter.this.mOldItems.get(i), list.get(i2));
            }
        });
        this.mItems.clear();
        this.mItems.addAll(list);
        if (this.mListUpdateCallback == null) {
            this.mListUpdateCallback = new ListUpdateCallback() { // from class: androidx.leanback.widget.ArrayObjectAdapter.2
                @Override // androidx.recyclerview.widget.ListUpdateCallback
                public void onInserted(int i, int i2) {
                    if (ArrayObjectAdapter.DEBUG.booleanValue()) {
                        Log.d(ArrayObjectAdapter.TAG, "onInserted");
                    }
                    ArrayObjectAdapter.this.notifyItemRangeInserted(i, i2);
                }

                @Override // androidx.recyclerview.widget.ListUpdateCallback
                public void onRemoved(int i, int i2) {
                    if (ArrayObjectAdapter.DEBUG.booleanValue()) {
                        Log.d(ArrayObjectAdapter.TAG, "onRemoved");
                    }
                    ArrayObjectAdapter.this.notifyItemRangeRemoved(i, i2);
                }

                @Override // androidx.recyclerview.widget.ListUpdateCallback
                public void onMoved(int i, int i2) {
                    if (ArrayObjectAdapter.DEBUG.booleanValue()) {
                        Log.d(ArrayObjectAdapter.TAG, "onMoved");
                    }
                    ArrayObjectAdapter.this.notifyItemMoved(i, i2);
                }

                @Override // androidx.recyclerview.widget.ListUpdateCallback
                public void onChanged(int i, int i2, Object obj) {
                    if (ArrayObjectAdapter.DEBUG.booleanValue()) {
                        Log.d(ArrayObjectAdapter.TAG, "onChanged");
                    }
                    ArrayObjectAdapter.this.notifyItemRangeChanged(i, i2, obj);
                }
            };
        }
        calculateDiff.dispatchUpdatesTo(this.mListUpdateCallback);
        this.mOldItems.clear();
    }
}
