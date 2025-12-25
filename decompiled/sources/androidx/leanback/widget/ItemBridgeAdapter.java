package androidx.leanback.widget;

import android.view.View;
import android.view.ViewGroup;
import androidx.leanback.widget.ObjectAdapter;
import androidx.leanback.widget.Presenter;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public class ItemBridgeAdapter extends RecyclerView.Adapter implements FacetProviderAdapter {
    static final boolean DEBUG = false;
    static final String TAG = "ItemBridgeAdapter";
    private ObjectAdapter mAdapter;
    private AdapterListener mAdapterListener;
    private ObjectAdapter.DataObserver mDataObserver;
    FocusHighlightHandler mFocusHighlight;
    private PresenterSelector mPresenterSelector;
    private ArrayList<Presenter> mPresenters;
    Wrapper mWrapper;

    /* loaded from: classes.dex */
    public static abstract class Wrapper {
        public abstract View createWrapper(View view);

        public abstract void wrap(View view, View view2);
    }

    protected void onAddPresenter(Presenter presenter, int i) {
    }

    protected void onAttachedToWindow(ViewHolder viewHolder) {
    }

    protected void onBind(ViewHolder viewHolder) {
    }

    protected void onCreate(ViewHolder viewHolder) {
    }

    protected void onDetachedFromWindow(ViewHolder viewHolder) {
    }

    protected void onUnbind(ViewHolder viewHolder) {
    }

    /* loaded from: classes.dex */
    public static class AdapterListener {
        public void onAddPresenter(Presenter presenter, int i) {
        }

        public void onAttachedToWindow(ViewHolder viewHolder) {
        }

        public void onBind(ViewHolder viewHolder) {
        }

        public void onCreate(ViewHolder viewHolder) {
        }

        public void onDetachedFromWindow(ViewHolder viewHolder) {
        }

        public void onUnbind(ViewHolder viewHolder) {
        }

        public void onBind(ViewHolder viewHolder, List list) {
            onBind(viewHolder);
        }
    }

    /* loaded from: classes.dex */
    final class OnFocusChangeListener implements View.OnFocusChangeListener {
        View.OnFocusChangeListener mChainedListener;

        OnFocusChangeListener() {
        }

        @Override // android.view.View.OnFocusChangeListener
        public void onFocusChange(View view, boolean z) {
            if (ItemBridgeAdapter.this.mWrapper != null) {
                view = (View) view.getParent();
            }
            if (ItemBridgeAdapter.this.mFocusHighlight != null) {
                ItemBridgeAdapter.this.mFocusHighlight.onItemFocused(view, z);
            }
            View.OnFocusChangeListener onFocusChangeListener = this.mChainedListener;
            if (onFocusChangeListener != null) {
                onFocusChangeListener.onFocusChange(view, z);
            }
        }
    }

    /* loaded from: classes.dex */
    public class ViewHolder extends RecyclerView.ViewHolder implements FacetProvider {
        Object mExtraObject;
        final OnFocusChangeListener mFocusChangeListener;
        final Presenter.ViewHolder mHolder;
        Object mItem;
        final Presenter mPresenter;

        public final Presenter getPresenter() {
            return this.mPresenter;
        }

        public final Presenter.ViewHolder getViewHolder() {
            return this.mHolder;
        }

        public final Object getItem() {
            return this.mItem;
        }

        public final Object getExtraObject() {
            return this.mExtraObject;
        }

        public void setExtraObject(Object obj) {
            this.mExtraObject = obj;
        }

        @Override // androidx.leanback.widget.FacetProvider
        public Object getFacet(Class<?> cls) {
            return this.mHolder.getFacet(cls);
        }

        ViewHolder(Presenter presenter, View view, Presenter.ViewHolder viewHolder) {
            super(view);
            this.mFocusChangeListener = new OnFocusChangeListener();
            this.mPresenter = presenter;
            this.mHolder = viewHolder;
        }
    }

    public ItemBridgeAdapter(ObjectAdapter objectAdapter, PresenterSelector presenterSelector) {
        this.mPresenters = new ArrayList<>();
        this.mDataObserver = new ObjectAdapter.DataObserver() { // from class: androidx.leanback.widget.ItemBridgeAdapter.1
            @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
            public void onChanged() {
                ItemBridgeAdapter.this.notifyDataSetChanged();
            }

            @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
            public void onItemRangeChanged(int i, int i2) {
                ItemBridgeAdapter.this.notifyItemRangeChanged(i, i2);
            }

            @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
            public void onItemRangeChanged(int i, int i2, Object obj) {
                ItemBridgeAdapter.this.notifyItemRangeChanged(i, i2, obj);
            }

            @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
            public void onItemRangeInserted(int i, int i2) {
                ItemBridgeAdapter.this.notifyItemRangeInserted(i, i2);
            }

            @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
            public void onItemRangeRemoved(int i, int i2) {
                ItemBridgeAdapter.this.notifyItemRangeRemoved(i, i2);
            }

            @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
            public void onItemMoved(int i, int i2) {
                ItemBridgeAdapter.this.notifyItemMoved(i, i2);
            }
        };
        setAdapter(objectAdapter);
        this.mPresenterSelector = presenterSelector;
    }

    public ItemBridgeAdapter(ObjectAdapter objectAdapter) {
        this(objectAdapter, null);
    }

    public ItemBridgeAdapter() {
        this.mPresenters = new ArrayList<>();
        this.mDataObserver = new ObjectAdapter.DataObserver() { // from class: androidx.leanback.widget.ItemBridgeAdapter.1
            @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
            public void onChanged() {
                ItemBridgeAdapter.this.notifyDataSetChanged();
            }

            @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
            public void onItemRangeChanged(int i, int i2) {
                ItemBridgeAdapter.this.notifyItemRangeChanged(i, i2);
            }

            @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
            public void onItemRangeChanged(int i, int i2, Object obj) {
                ItemBridgeAdapter.this.notifyItemRangeChanged(i, i2, obj);
            }

            @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
            public void onItemRangeInserted(int i, int i2) {
                ItemBridgeAdapter.this.notifyItemRangeInserted(i, i2);
            }

            @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
            public void onItemRangeRemoved(int i, int i2) {
                ItemBridgeAdapter.this.notifyItemRangeRemoved(i, i2);
            }

            @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
            public void onItemMoved(int i, int i2) {
                ItemBridgeAdapter.this.notifyItemMoved(i, i2);
            }
        };
    }

    public void setAdapter(ObjectAdapter objectAdapter) {
        ObjectAdapter objectAdapter2 = this.mAdapter;
        if (objectAdapter == objectAdapter2) {
            return;
        }
        if (objectAdapter2 != null) {
            objectAdapter2.unregisterObserver(this.mDataObserver);
        }
        this.mAdapter = objectAdapter;
        if (objectAdapter == null) {
            notifyDataSetChanged();
            return;
        }
        objectAdapter.registerObserver(this.mDataObserver);
        if (hasStableIds() != this.mAdapter.hasStableIds()) {
            setHasStableIds(this.mAdapter.hasStableIds());
        }
        notifyDataSetChanged();
    }

    public void setPresenter(PresenterSelector presenterSelector) {
        this.mPresenterSelector = presenterSelector;
        notifyDataSetChanged();
    }

    public void setWrapper(Wrapper wrapper) {
        this.mWrapper = wrapper;
    }

    public Wrapper getWrapper() {
        return this.mWrapper;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setFocusHighlight(FocusHighlightHandler focusHighlightHandler) {
        this.mFocusHighlight = focusHighlightHandler;
    }

    public void clear() {
        setAdapter(null);
    }

    public void setPresenterMapper(ArrayList<Presenter> arrayList) {
        this.mPresenters = arrayList;
    }

    public ArrayList<Presenter> getPresenterMapper() {
        return this.mPresenters;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        ObjectAdapter objectAdapter = this.mAdapter;
        if (objectAdapter != null) {
            return objectAdapter.size();
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        PresenterSelector presenterSelector = this.mPresenterSelector;
        if (presenterSelector == null) {
            presenterSelector = this.mAdapter.getPresenterSelector();
        }
        Presenter presenter = presenterSelector.getPresenter(this.mAdapter.get(i));
        int indexOf = this.mPresenters.indexOf(presenter);
        if (indexOf < 0) {
            this.mPresenters.add(presenter);
            indexOf = this.mPresenters.indexOf(presenter);
            onAddPresenter(presenter, indexOf);
            AdapterListener adapterListener = this.mAdapterListener;
            if (adapterListener != null) {
                adapterListener.onAddPresenter(presenter, indexOf);
            }
        }
        return indexOf;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Presenter.ViewHolder onCreateViewHolder;
        View view;
        Presenter presenter = this.mPresenters.get(i);
        Wrapper wrapper = this.mWrapper;
        if (wrapper != null) {
            view = wrapper.createWrapper(viewGroup);
            onCreateViewHolder = presenter.onCreateViewHolder(viewGroup);
            this.mWrapper.wrap(view, onCreateViewHolder.view);
        } else {
            onCreateViewHolder = presenter.onCreateViewHolder(viewGroup);
            view = onCreateViewHolder.view;
        }
        ViewHolder viewHolder = new ViewHolder(presenter, view, onCreateViewHolder);
        onCreate(viewHolder);
        AdapterListener adapterListener = this.mAdapterListener;
        if (adapterListener != null) {
            adapterListener.onCreate(viewHolder);
        }
        View view2 = viewHolder.mHolder.view;
        if (view2 != null) {
            viewHolder.mFocusChangeListener.mChainedListener = view2.getOnFocusChangeListener();
            view2.setOnFocusChangeListener(viewHolder.mFocusChangeListener);
        }
        FocusHighlightHandler focusHighlightHandler = this.mFocusHighlight;
        if (focusHighlightHandler != null) {
            focusHighlightHandler.onInitializeView(view);
        }
        return viewHolder;
    }

    public void setAdapterListener(AdapterListener adapterListener) {
        this.mAdapterListener = adapterListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        viewHolder2.mItem = this.mAdapter.get(i);
        viewHolder2.mPresenter.onBindViewHolder(viewHolder2.mHolder, viewHolder2.mItem);
        onBind(viewHolder2);
        AdapterListener adapterListener = this.mAdapterListener;
        if (adapterListener != null) {
            adapterListener.onBind(viewHolder2);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i, List list) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        viewHolder2.mItem = this.mAdapter.get(i);
        viewHolder2.mPresenter.onBindViewHolder(viewHolder2.mHolder, viewHolder2.mItem, list);
        onBind(viewHolder2);
        AdapterListener adapterListener = this.mAdapterListener;
        if (adapterListener != null) {
            adapterListener.onBind(viewHolder2, list);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        viewHolder2.mPresenter.onUnbindViewHolder(viewHolder2.mHolder);
        onUnbind(viewHolder2);
        AdapterListener adapterListener = this.mAdapterListener;
        if (adapterListener != null) {
            adapterListener.onUnbind(viewHolder2);
        }
        viewHolder2.mItem = null;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final boolean onFailedToRecycleView(RecyclerView.ViewHolder viewHolder) {
        onViewRecycled(viewHolder);
        return false;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onViewAttachedToWindow(RecyclerView.ViewHolder viewHolder) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        onAttachedToWindow(viewHolder2);
        AdapterListener adapterListener = this.mAdapterListener;
        if (adapterListener != null) {
            adapterListener.onAttachedToWindow(viewHolder2);
        }
        viewHolder2.mPresenter.onViewAttachedToWindow(viewHolder2.mHolder);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onViewDetachedFromWindow(RecyclerView.ViewHolder viewHolder) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        viewHolder2.mPresenter.onViewDetachedFromWindow(viewHolder2.mHolder);
        onDetachedFromWindow(viewHolder2);
        AdapterListener adapterListener = this.mAdapterListener;
        if (adapterListener != null) {
            adapterListener.onDetachedFromWindow(viewHolder2);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        return this.mAdapter.getId(i);
    }

    @Override // androidx.leanback.widget.FacetProviderAdapter
    public FacetProvider getFacetProvider(int i) {
        return this.mPresenters.get(i);
    }
}
