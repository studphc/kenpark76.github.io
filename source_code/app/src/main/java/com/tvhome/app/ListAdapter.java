package com.tvhome.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.tvhome.app.databinding.ListItemBinding;
import com.tvhome.app.models.TVGroupModel;
import com.tvhome.app.models.TVListModel;
import com.tvhome.app.models.TVModel;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
/* compiled from: ListAdapter.kt */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 02\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0003012B%\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0006\u0010!\u001a\u00020\"J\u000e\u0010#\u001a\u00020\"2\u0006\u0010$\u001a\u00020\u0013J\b\u0010%\u001a\u00020\u0011H\u0016J\u0018\u0010&\u001a\u00020\"2\u0006\u0010'\u001a\u00020\u00022\u0006\u0010(\u001a\u00020\u0011H\u0016J\u0018\u0010)\u001a\u00020\u00022\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020\u0011H\u0016J\u000e\u0010-\u001a\u00020\"2\u0006\u0010\u0016\u001a\u00020\u0017J\u000e\u0010.\u001a\u00020\"2\u0006\u0010(\u001a\u00020\u0011J\u000e\u0010/\u001a\u00020\"2\u0006\u0010\u0007\u001a\u00020\bR\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 ¨\u00063"}, d2 = {"Lcom/pjy/koreatv/ListAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/pjy/koreatv/ListAdapter$ViewHolder;", "context", "Landroid/content/Context;", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "tvListModel", "Lcom/pjy/koreatv/models/TVListModel;", "tvGroupModel", "Lcom/pjy/koreatv/models/TVGroupModel;", "(Landroid/content/Context;Landroidx/recyclerview/widget/RecyclerView;Lcom/pjy/koreatv/models/TVListModel;Lcom/pjy/koreatv/models/TVGroupModel;)V", "application", "Lcom/pjy/koreatv/MyTVApplication;", "getApplication", "()Lcom/pjy/koreatv/MyTVApplication;", "defaultFocus", "", "defaultFocused", "", "focused", "Landroid/view/View;", "listener", "Lcom/pjy/koreatv/ListAdapter$ItemListener;", "getTvListModel", "()Lcom/pjy/koreatv/models/TVListModel;", "setTvListModel", "(Lcom/pjy/koreatv/models/TVListModel;)V", "visiable", "getVisiable", "()Z", "setVisiable", "(Z)V", "clear", "", "focusable", "able", "getItemCount", "onBindViewHolder", "viewHolder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "setItemListener", "toPosition", "update", "Companion", "ItemListener", "ViewHolder", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class ListAdapter extends RecyclerView.Adapter<ViewHolder> {
    public static final Companion Companion = new Companion(null);
    private static final String TAG = "ListAdapter";
    private final MyTVApplication application;
    private final Context context;
    private int defaultFocus;
    private boolean defaultFocused;
    private View focused;
    private ItemListener listener;
    private final RecyclerView recyclerView;
    private TVGroupModel tvGroupModel;
    private TVListModel tvListModel;
    private boolean visiable;

    /* compiled from: ListAdapter.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0018\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH&J\u0018\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH&¨\u0006\u000e"}, d2 = {"Lcom/pjy/koreatv/ListAdapter$ItemListener;", "", "onItemClicked", "", "tvModel", "Lcom/pjy/koreatv/models/TVModel;", "onItemFocusChange", "hasFocus", "", "onKey", "listAdapter", "Lcom/pjy/koreatv/ListAdapter;", "keyCode", "", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    public interface ItemListener {
        void onItemClicked(TVModel tVModel);

        void onItemFocusChange(TVModel tVModel, boolean z);

        boolean onKey(ListAdapter listAdapter, int i);
    }

    public final TVListModel getTvListModel() {
        return this.tvListModel;
    }

    public final void setTvListModel(TVListModel tVListModel) {
        Intrinsics.checkNotNullParameter(tVListModel, "<set-?>");
        this.tvListModel = tVListModel;
    }

    public ListAdapter(Context context, RecyclerView recyclerView, TVListModel tvListModel, TVGroupModel tvGroupModel) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
        Intrinsics.checkNotNullParameter(tvListModel, "tvListModel");
        Intrinsics.checkNotNullParameter(tvGroupModel, "tvGroupModel");
        this.context = context;
        this.recyclerView = recyclerView;
        this.tvListModel = tvListModel;
        this.tvGroupModel = tvGroupModel;
        this.defaultFocus = -1;
        Context applicationContext = context.getApplicationContext();
        Intrinsics.checkNotNull(applicationContext, "null cannot be cast to non-null type com.pjy.koreatv.MyTVApplication");
        this.application = (MyTVApplication) applicationContext;
    }

    public final boolean getVisiable() {
        return this.visiable;
    }

    public final void setVisiable(boolean z) {
        this.visiable = z;
    }

    public final MyTVApplication getApplication() {
        return this.application;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        ListItemBinding inflate = ListItemBinding.inflate(LayoutInflater.from(this.context), parent, false);
        Intrinsics.checkNotNullExpressionValue(inflate, "inflate(...)");
        inflate.icon.getLayoutParams().width = this.application.px2Px(inflate.icon.getLayoutParams().width);
        inflate.icon.getLayoutParams().height = this.application.px2Px(inflate.icon.getLayoutParams().height);
        ImageView icon = inflate.icon;
        Intrinsics.checkNotNullExpressionValue(icon, "icon");
        int px2Px = this.application.px2Px(inflate.icon.getPaddingTop());
        icon.setPadding(px2Px, px2Px, px2Px, px2Px);
        ViewGroup.LayoutParams layoutParams = inflate.title.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        MyTVApplication myTVApplication = this.application;
        TextView title = inflate.title;
        Intrinsics.checkNotNullExpressionValue(title, "title");
        ViewGroup.LayoutParams layoutParams2 = title.getLayoutParams();
        marginLayoutParams.setMarginStart(myTVApplication.px2Px(layoutParams2 instanceof ViewGroup.MarginLayoutParams ? ((ViewGroup.MarginLayoutParams) layoutParams2).getMarginStart() : 0));
        inflate.title.setLayoutParams(marginLayoutParams);
        inflate.title.setTextSize(this.application.px2PxFont(inflate.title.getTextSize()));
        ViewGroup.LayoutParams layoutParams3 = inflate.description.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams3, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) layoutParams3;
        MyTVApplication myTVApplication2 = this.application;
        TextView description = inflate.description;
        Intrinsics.checkNotNullExpressionValue(description, "description");
        ViewGroup.LayoutParams layoutParams4 = description.getLayoutParams();
        marginLayoutParams2.setMarginStart(myTVApplication2.px2Px(layoutParams4 instanceof ViewGroup.MarginLayoutParams ? ((ViewGroup.MarginLayoutParams) layoutParams4).getMarginStart() : 0));
        inflate.description.setLayoutParams(marginLayoutParams2);
        inflate.description.setTextSize(this.application.px2PxFont(inflate.description.getTextSize()));
        ViewGroup.LayoutParams layoutParams5 = inflate.heart.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams5, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) layoutParams5;
        MyTVApplication myTVApplication3 = this.application;
        ImageView heart = inflate.heart;
        Intrinsics.checkNotNullExpressionValue(heart, "heart");
        ViewGroup.LayoutParams layoutParams6 = heart.getLayoutParams();
        marginLayoutParams3.setMarginStart(myTVApplication3.px2Px(layoutParams6 instanceof ViewGroup.MarginLayoutParams ? ((ViewGroup.MarginLayoutParams) layoutParams6).getMarginStart() : 0));
        inflate.heart.setLayoutParams(marginLayoutParams3);
        inflate.heart.getLayoutParams().width = this.application.px2Px(inflate.heart.getLayoutParams().width);
        inflate.heart.getLayoutParams().height = this.application.px2Px(inflate.heart.getLayoutParams().height);
        return new ViewHolder(this.context, inflate);
    }

    public final void focusable(boolean z) {
        this.recyclerView.setFocusable(z);
        this.recyclerView.setFocusableInTouchMode(z);
        if (z) {
            this.recyclerView.setDescendantFocusability(131072);
        } else {
            this.recyclerView.setDescendantFocusability(393216);
        }
    }

    public final void update(TVListModel tvListModel) {
        Intrinsics.checkNotNullParameter(tvListModel, "tvListModel");
        this.tvListModel = tvListModel;
        this.recyclerView.post(new Runnable() { // from class: com.pjy.koreatv.ListAdapter$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                ListAdapter.update$lambda$0(ListAdapter.this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$0(ListAdapter this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.notifyDataSetChanged();
    }

    public final void clear() {
        View view = this.focused;
        if (view != null) {
            view.clearFocus();
        }
        this.recyclerView.invalidate();
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x00e3  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0127  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0168  */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onBindViewHolder(final com.pjy.koreatv.ListAdapter.ViewHolder r14, final int r15) {
        /*
            Method dump skipped, instructions count: 405
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.pjy.koreatv.ListAdapter.onBindViewHolder(com.pjy.koreatv.ListAdapter$ViewHolder, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$1(TVModel tvModel, ViewHolder viewHolder, View view) {
        Intrinsics.checkNotNullParameter(tvModel, "$tvModel");
        Intrinsics.checkNotNullParameter(viewHolder, "$viewHolder");
        Boolean value = tvModel.getLike().getValue();
        Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Boolean");
        tvModel.setLike(!value.booleanValue());
        Boolean value2 = tvModel.getLike().getValue();
        Intrinsics.checkNotNull(value2, "null cannot be cast to non-null type kotlin.Boolean");
        viewHolder.like(value2.booleanValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$2(ListAdapter this$0, TVModel tvModel, ViewHolder viewHolder, View view, int i, View view2, boolean z) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(tvModel, "$tvModel");
        Intrinsics.checkNotNullParameter(viewHolder, "$viewHolder");
        Intrinsics.checkNotNullParameter(view, "$view");
        ItemListener itemListener = this$0.listener;
        if (itemListener != null) {
            itemListener.onItemFocusChange(tvModel, z);
        }
        if (z) {
            viewHolder.focus(true);
            this$0.focused = view;
            if (this$0.visiable) {
                Integer value = this$0.tvListModel.getPosition().getValue();
                if (value != null && i == value.intValue()) {
                    return;
                }
                this$0.tvListModel.setPosition(i);
                return;
            }
            this$0.visiable = true;
            return;
        }
        viewHolder.focus(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$3(ListAdapter this$0, TVModel tvModel, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(tvModel, "$tvModel");
        ItemListener itemListener = this$0.listener;
        if (itemListener != null) {
            itemListener.onItemClicked(tvModel);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean onBindViewHolder$lambda$6(int i, final ListAdapter this$0, TVModel tvModel, ViewHolder viewHolder, View view, int i2, KeyEvent keyEvent) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(tvModel, "$tvModel");
        Intrinsics.checkNotNullParameter(viewHolder, "$viewHolder");
        if (keyEvent != null && keyEvent.getAction() == 0) {
            if (i2 == 19 && i == 0) {
                final int itemCount = this$0.getItemCount() - 1;
                RecyclerView.LayoutManager layoutManager = this$0.recyclerView.getLayoutManager();
                LinearLayoutManager linearLayoutManager = layoutManager instanceof LinearLayoutManager ? (LinearLayoutManager) layoutManager : null;
                if (linearLayoutManager != null) {
                    linearLayoutManager.scrollToPositionWithOffset(itemCount, 0);
                }
                this$0.recyclerView.postDelayed(new Runnable() { // from class: com.pjy.koreatv.ListAdapter$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        ListAdapter.onBindViewHolder$lambda$6$lambda$4(ListAdapter.this, itemCount);
                    }
                }, 0L);
            }
            if (i2 == 20 && i == this$0.getItemCount() - 1) {
                RecyclerView.LayoutManager layoutManager2 = this$0.recyclerView.getLayoutManager();
                LinearLayoutManager linearLayoutManager2 = layoutManager2 instanceof LinearLayoutManager ? (LinearLayoutManager) layoutManager2 : null;
                if (linearLayoutManager2 != null) {
                    linearLayoutManager2.scrollToPositionWithOffset(0, 0);
                }
                this$0.recyclerView.postDelayed(new Runnable() { // from class: com.pjy.koreatv.ListAdapter$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        ListAdapter.onBindViewHolder$lambda$6$lambda$5(ListAdapter.this, r2);
                    }
                }, 0L);
            }
            if (i2 == 22) {
                Boolean value = tvModel.getLike().getValue();
                Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Boolean");
                tvModel.setLike(!value.booleanValue());
                Boolean value2 = tvModel.getLike().getValue();
                Intrinsics.checkNotNull(value2, "null cannot be cast to non-null type kotlin.Boolean");
                viewHolder.like(value2.booleanValue());
            }
            ItemListener itemListener = this$0.listener;
            if (itemListener != null) {
                return itemListener.onKey(this$0, i2);
            }
            return false;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$6$lambda$4(ListAdapter this$0, int i) {
        View view;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        RecyclerView.ViewHolder findViewHolderForAdapterPosition = this$0.recyclerView.findViewHolderForAdapterPosition(i);
        View view2 = findViewHolderForAdapterPosition != null ? findViewHolderForAdapterPosition.itemView : null;
        if (view2 != null) {
            view2.setSelected(true);
        }
        if (findViewHolderForAdapterPosition == null || (view = findViewHolderForAdapterPosition.itemView) == null) {
            return;
        }
        view.requestFocus();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$6$lambda$5(ListAdapter this$0, int i) {
        View view;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        RecyclerView.ViewHolder findViewHolderForAdapterPosition = this$0.recyclerView.findViewHolderForAdapterPosition(i);
        View view2 = findViewHolderForAdapterPosition != null ? findViewHolderForAdapterPosition.itemView : null;
        if (view2 != null) {
            view2.setSelected(true);
        }
        if (findViewHolderForAdapterPosition == null || (view = findViewHolderForAdapterPosition.itemView) == null) {
            return;
        }
        view.requestFocus();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.tvListModel.size();
    }

    /* compiled from: ListAdapter.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u0018\u0010\r\u001a\u00020\n2\b\u0010\u000e\u001a\u0004\u0018\u00010\f2\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0011\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\u0014R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lcom/pjy/koreatv/ListAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "context", "Landroid/content/Context;", "binding", "Lcom/pjy/koreatv/databinding/ListItemBinding;", "(Landroid/content/Context;Lcom/pjy/koreatv/databinding/ListItemBinding;)V", "getBinding", "()Lcom/pjy/koreatv/databinding/ListItemBinding;", "bindEPG", "", "text", "", "bindImage", "url", "id", "", "bindTitle", "focus", "hasFocus", "", "like", "liked", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    public static final class ViewHolder extends RecyclerView.ViewHolder {
        private final ListItemBinding binding;
        private final Context context;

        public final ListItemBinding getBinding() {
            return this.binding;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ViewHolder(Context context, ListItemBinding binding) {
            super(binding.getRoot());
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(binding, "binding");
            this.context = context;
            this.binding = binding;
        }

        public final void bindTitle(String text) {
            Intrinsics.checkNotNullParameter(text, "text");
            this.binding.title.setText(text);
        }

        public final void bindEPG(String text) {
            Intrinsics.checkNotNullParameter(text, "text");
            this.binding.description.setText(text);
        }

        public final void bindImage(String str, int i) {
            int dpToPx = Utils.INSTANCE.dpToPx(90);
            int dpToPx2 = Utils.INSTANCE.dpToPx(60);
            Bitmap createBitmap = Bitmap.createBitmap(dpToPx, dpToPx2, Bitmap.Config.ARGB_8888);
            Intrinsics.checkNotNullExpressionValue(createBitmap, "createBitmap(...)");
            Canvas canvas = new Canvas(createBitmap);
            Paint paint = new Paint();
            paint.setColor(-1);
            if (i > 98) {
                paint.setTextSize(63.0f);
            } else {
                paint.setTextSize(70.0f);
            }
            paint.setTextAlign(Paint.Align.CENTER);
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String format = String.format("%d", Arrays.copyOf(new Object[]{Integer.valueOf(i + 1)}, 1));
            Intrinsics.checkNotNullExpressionValue(format, "format(...)");
            canvas.drawText(format, dpToPx / 2.0f, (dpToPx2 / 2.0f) - ((paint.descent() + paint.ascent()) / 2), paint);
            Glide.with(this.context).load((Drawable) new BitmapDrawable(this.context.getResources(), createBitmap)).fitCenter().into(this.binding.icon);
        }

        public final void focus(boolean z) {
            if (z) {
                this.binding.title.setTextColor(ContextCompat.getColor(this.context, R.color.white));
                this.binding.description.setTextColor(ContextCompat.getColor(this.context, R.color.description_blur));
                this.binding.getRoot().setBackgroundResource(R.color.menu_focus_background);
                return;
            }
            this.binding.title.setTextColor(ContextCompat.getColor(this.context, R.color.title_blur));
            this.binding.description.setTextColor(ContextCompat.getColor(this.context, R.color.description_blur));
            this.binding.getRoot().setBackgroundResource(R.color.menu_background);
        }

        public final void like(boolean z) {
            if (z) {
                this.binding.heart.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.ic_heart));
            } else {
                this.binding.heart.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.ic_heart_empty));
            }
        }
    }

    public final void toPosition(final int i) {
        this.recyclerView.post(new Runnable() { // from class: com.pjy.koreatv.ListAdapter$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                ListAdapter.toPosition$lambda$9(ListAdapter.this, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void toPosition$lambda$9(final ListAdapter this$0, final int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        RecyclerView.LayoutManager layoutManager = this$0.recyclerView.getLayoutManager();
        LinearLayoutManager linearLayoutManager = layoutManager instanceof LinearLayoutManager ? (LinearLayoutManager) layoutManager : null;
        if (linearLayoutManager != null) {
            linearLayoutManager.scrollToPositionWithOffset(i, 0);
        }
        this$0.recyclerView.postDelayed(new Runnable() { // from class: com.pjy.koreatv.ListAdapter$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ListAdapter.toPosition$lambda$9$lambda$8(ListAdapter.this, i);
            }
        }, 0L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void toPosition$lambda$9$lambda$8(ListAdapter this$0, int i) {
        View view;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        RecyclerView.ViewHolder findViewHolderForAdapterPosition = this$0.recyclerView.findViewHolderForAdapterPosition(i);
        View view2 = findViewHolderForAdapterPosition != null ? findViewHolderForAdapterPosition.itemView : null;
        if (view2 != null) {
            view2.setSelected(true);
        }
        if (findViewHolderForAdapterPosition == null || (view = findViewHolderForAdapterPosition.itemView) == null) {
            return;
        }
        view.requestFocus();
    }

    public final void setItemListener(ItemListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.listener = listener;
    }

    /* compiled from: ListAdapter.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/pjy/koreatv/ListAdapter$Companion;", "", "()V", "TAG", "", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
