package androidx.leanback.widget;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.leanback.R;
import androidx.leanback.widget.RowPresenter;
/* loaded from: classes.dex */
public abstract class AbstractMediaListHeaderPresenter extends RowPresenter {
    private int mBackgroundColor;
    private boolean mBackgroundColorSet;
    private final Context mContext;

    @Override // androidx.leanback.widget.RowPresenter
    public boolean isUsingDefaultSelectEffect() {
        return false;
    }

    protected abstract void onBindMediaListHeaderViewHolder(ViewHolder viewHolder, Object obj);

    /* loaded from: classes.dex */
    public static class ViewHolder extends RowPresenter.ViewHolder {
        private final TextView mHeaderView;

        public ViewHolder(View view) {
            super(view);
            this.mHeaderView = (TextView) view.findViewById(R.id.mediaListHeader);
        }

        public TextView getHeaderView() {
            return this.mHeaderView;
        }
    }

    public AbstractMediaListHeaderPresenter(Context context, int i) {
        this.mBackgroundColor = 0;
        this.mContext = new ContextThemeWrapper(context.getApplicationContext(), i);
        setHeaderPresenter(null);
    }

    public AbstractMediaListHeaderPresenter() {
        this.mBackgroundColor = 0;
        this.mContext = null;
        setHeaderPresenter(null);
    }

    @Override // androidx.leanback.widget.RowPresenter
    protected RowPresenter.ViewHolder createRowViewHolder(ViewGroup viewGroup) {
        Context context = this.mContext;
        if (context == null) {
            context = viewGroup.getContext();
        }
        View inflate = LayoutInflater.from(context).inflate(R.layout.lb_media_list_header, viewGroup, false);
        inflate.setFocusable(false);
        inflate.setFocusableInTouchMode(false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        if (this.mBackgroundColorSet) {
            viewHolder.view.setBackgroundColor(this.mBackgroundColor);
        }
        return viewHolder;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.leanback.widget.RowPresenter
    public void onBindRowViewHolder(RowPresenter.ViewHolder viewHolder, Object obj) {
        super.onBindRowViewHolder(viewHolder, obj);
        onBindMediaListHeaderViewHolder((ViewHolder) viewHolder, obj);
    }

    public void setBackgroundColor(int i) {
        this.mBackgroundColorSet = true;
        this.mBackgroundColor = i;
    }
}
