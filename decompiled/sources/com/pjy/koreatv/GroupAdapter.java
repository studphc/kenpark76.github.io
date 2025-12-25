package com.pjy.koreatv;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.pjy.koreatv.databinding.GroupItemBinding;
import com.pjy.koreatv.models.TVGroupModel;
import com.pjy.koreatv.models.TVList;
import com.pjy.koreatv.models.TVListModel;
import com.pjy.koreatv.models.TVModel;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: GroupAdapter.kt */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 *2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0003*+,B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0006\u0010\u001b\u001a\u00020\u001cJ\u000e\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u0011J\b\u0010\u001f\u001a\u00020\u000fH\u0016J\u0018\u0010 \u001a\u00020\u001c2\u0006\u0010!\u001a\u00020\u00022\u0006\u0010\"\u001a\u00020\u000fH\u0016J\u0018\u0010#\u001a\u00020\u00022\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\u000fH\u0016J\u000e\u0010'\u001a\u00020\u001c2\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010(\u001a\u00020\u001c2\u0006\u0010\"\u001a\u00020\u000fJ\u000e\u0010)\u001a\u00020\u001c2\u0006\u0010\u0007\u001a\u00020\bR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0016\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a¨\u0006-"}, d2 = {"Lcom/pjy/koreatv/GroupAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/pjy/koreatv/GroupAdapter$ViewHolder;", "context", "Landroid/content/Context;", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "tvGroupModel", "Lcom/pjy/koreatv/models/TVGroupModel;", "(Landroid/content/Context;Landroidx/recyclerview/widget/RecyclerView;Lcom/pjy/koreatv/models/TVGroupModel;)V", "application", "Lcom/pjy/koreatv/MyTVApplication;", "getApplication", "()Lcom/pjy/koreatv/MyTVApplication;", "defaultFocus", "", "defaultFocused", "", "focused", "Landroid/view/View;", "listener", "Lcom/pjy/koreatv/GroupAdapter$ItemListener;", "visiable", "getVisiable", "()Z", "setVisiable", "(Z)V", "clear", "", "focusable", "able", "getItemCount", "onBindViewHolder", "viewHolder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "setItemListener", "toPosition", "update", "Companion", "ItemListener", "ViewHolder", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class GroupAdapter extends RecyclerView.Adapter<ViewHolder> {
    public static final Companion Companion = new Companion(null);
    private static final String TAG = "GroupAdapter";
    private final MyTVApplication application;
    private final Context context;
    private int defaultFocus;
    private boolean defaultFocused;
    private View focused;
    private ItemListener listener;
    private final RecyclerView recyclerView;
    private TVGroupModel tvGroupModel;
    private boolean visiable;

    /* compiled from: GroupAdapter.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0018\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH&J\u0010\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u0005H&¨\u0006\r"}, d2 = {"Lcom/pjy/koreatv/GroupAdapter$ItemListener;", "", "onItemClicked", "", "position", "", "onItemFocusChange", "tvListModel", "Lcom/pjy/koreatv/models/TVListModel;", "hasFocus", "", "onKey", "keyCode", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    public interface ItemListener {
        void onItemClicked(int i);

        void onItemFocusChange(TVListModel tVListModel, boolean z);

        boolean onKey(int i);
    }

    public GroupAdapter(Context context, RecyclerView recyclerView, TVGroupModel tvGroupModel) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
        Intrinsics.checkNotNullParameter(tvGroupModel, "tvGroupModel");
        this.context = context;
        this.recyclerView = recyclerView;
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
        GroupItemBinding inflate = GroupItemBinding.inflate(LayoutInflater.from(this.context), parent, false);
        Intrinsics.checkNotNullExpressionValue(inflate, "inflate(...)");
        ViewGroup.LayoutParams layoutParams = inflate.title.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        MyTVApplication myTVApplication = this.application;
        TextView title = inflate.title;
        Intrinsics.checkNotNullExpressionValue(title, "title");
        ViewGroup.LayoutParams layoutParams2 = title.getLayoutParams();
        marginLayoutParams.setMarginStart(myTVApplication.px2Px(layoutParams2 instanceof ViewGroup.MarginLayoutParams ? ((ViewGroup.MarginLayoutParams) layoutParams2).getMarginStart() : 0));
        MyTVApplication myTVApplication2 = this.application;
        TextView title2 = inflate.title;
        Intrinsics.checkNotNullExpressionValue(title2, "title");
        ViewGroup.LayoutParams layoutParams3 = title2.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams2 = layoutParams3 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams3 : null;
        marginLayoutParams.bottomMargin = myTVApplication2.px2Px(marginLayoutParams2 != null ? marginLayoutParams2.bottomMargin : 0);
        inflate.title.setLayoutParams(marginLayoutParams);
        inflate.title.setTextSize(this.application.px2PxFont(inflate.title.getTextSize()));
        inflate.getRoot().setFocusable(true);
        inflate.getRoot().setFocusableInTouchMode(true);
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

    public final void clear() {
        View view = this.focused;
        if (view != null) {
            view.clearFocus();
        }
        this.recyclerView.invalidate();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        Intrinsics.checkNotNullParameter(viewHolder, "viewHolder");
        final TVListModel tVListModel = this.tvGroupModel.getTVListModel(i);
        Intrinsics.checkNotNull(tVListModel);
        final View itemView = viewHolder.itemView;
        Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
        itemView.setTag(Integer.valueOf(i));
        Log.i("favorite", "onBindViewHolder Group Position: " + i);
        try {
            if (i == TVList.INSTANCE.getTVModel().getGroupIndex()) {
                itemView.requestFocus();
                itemView.setBackgroundColor(ContextCompat.getColor(this.context, R.color.menu_focus_background));
                itemView.performClick();
                this.tvGroupModel.setPosition(i);
                this.defaultFocused = true;
            } else {
                itemView.clearFocus();
                itemView.setBackgroundColor(ContextCompat.getColor(this.context, R.color.menu_background));
            }
            itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.pjy.koreatv.GroupAdapter$$ExternalSyntheticLambda4
                @Override // android.view.View.OnFocusChangeListener
                public final void onFocusChange(View view, boolean z) {
                    GroupAdapter.onBindViewHolder$lambda$0(GroupAdapter.this, tVListModel, viewHolder, itemView, i, view, z);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() { // from class: com.pjy.koreatv.GroupAdapter$$ExternalSyntheticLambda5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    GroupAdapter.onBindViewHolder$lambda$1(GroupAdapter.this, i, view);
                }
            });
            itemView.setOnKeyListener(new View.OnKeyListener() { // from class: com.pjy.koreatv.GroupAdapter$$ExternalSyntheticLambda6
                @Override // android.view.View.OnKeyListener
                public final boolean onKey(View view, int i2, KeyEvent keyEvent) {
                    boolean onBindViewHolder$lambda$4;
                    onBindViewHolder$lambda$4 = GroupAdapter.onBindViewHolder$lambda$4(i, this, view, i2, keyEvent);
                    return onBindViewHolder$lambda$4;
                }
            });
            viewHolder.bindTitle(tVListModel.getName());
        } catch (Exception e) {
            Log.i(TAG, "onBindViewHolder error " + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$0(GroupAdapter this$0, TVListModel tvListModel, ViewHolder viewHolder, View view, int i, View view2, boolean z) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(tvListModel, "$tvListModel");
        Intrinsics.checkNotNullParameter(viewHolder, "$viewHolder");
        Intrinsics.checkNotNullParameter(view, "$view");
        ItemListener itemListener = this$0.listener;
        if (itemListener != null) {
            itemListener.onItemFocusChange(tvListModel, z);
        }
        StringBuilder sb = new StringBuilder("onFocusChangeListener Group Position: ");
        TVModel tVModel = tvListModel.getTVModel();
        sb.append(tVModel != null ? Integer.valueOf(tVModel.getListIndex()) : null);
        sb.append('/');
        sb.append(z);
        Log.i("favorite", sb.toString());
        if (z) {
            viewHolder.focus(true);
            this$0.focused = view;
            this$0.visiable = true;
            Integer value = this$0.tvGroupModel.getPosition().getValue();
            if (value != null && i == value.intValue()) {
                return;
            }
            this$0.tvGroupModel.setPosition(i);
            return;
        }
        viewHolder.focus(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$1(GroupAdapter this$0, int i, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ItemListener itemListener = this$0.listener;
        if (itemListener != null) {
            itemListener.onItemClicked(i);
        }
        Log.i("favorite", "setOnClickListener onItemClicked Group Position: " + i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean onBindViewHolder$lambda$4(int i, final GroupAdapter this$0, View view, int i2, KeyEvent keyEvent) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (keyEvent != null && keyEvent.getAction() == 0) {
            if (i2 == 19 && i == 0) {
                Log.i("favorite", "setOnKeyListener up-key Group Position: " + i);
                final int itemCount = this$0.getItemCount() - 1;
                RecyclerView.LayoutManager layoutManager = this$0.recyclerView.getLayoutManager();
                LinearLayoutManager linearLayoutManager = layoutManager instanceof LinearLayoutManager ? (LinearLayoutManager) layoutManager : null;
                if (linearLayoutManager != null) {
                    linearLayoutManager.scrollToPositionWithOffset(itemCount, 0);
                }
                this$0.recyclerView.postDelayed(new Runnable() { // from class: com.pjy.koreatv.GroupAdapter$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        GroupAdapter.onBindViewHolder$lambda$4$lambda$2(GroupAdapter.this, itemCount);
                    }
                }, 0L);
            }
            if (i2 == 20 && i == this$0.getItemCount() - 1) {
                Log.i("favorite", "setOnKeyListener down-key Group Position: " + i);
                RecyclerView.LayoutManager layoutManager2 = this$0.recyclerView.getLayoutManager();
                LinearLayoutManager linearLayoutManager2 = layoutManager2 instanceof LinearLayoutManager ? (LinearLayoutManager) layoutManager2 : null;
                if (linearLayoutManager2 != null) {
                    linearLayoutManager2.scrollToPositionWithOffset(0, 0);
                }
                this$0.recyclerView.postDelayed(new Runnable() { // from class: com.pjy.koreatv.GroupAdapter$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        GroupAdapter.onBindViewHolder$lambda$4$lambda$3(GroupAdapter.this, r2);
                    }
                }, 0L);
            }
            ItemListener itemListener = this$0.listener;
            if (itemListener != null) {
                return itemListener.onKey(i2);
            }
            return false;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$4$lambda$2(GroupAdapter this$0, int i) {
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
    public static final void onBindViewHolder$lambda$4$lambda$3(GroupAdapter this$0, int i) {
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
        return this.tvGroupModel.size();
    }

    /* compiled from: GroupAdapter.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\rR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/pjy/koreatv/GroupAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "context", "Landroid/content/Context;", "binding", "Lcom/pjy/koreatv/databinding/GroupItemBinding;", "(Landroid/content/Context;Lcom/pjy/koreatv/databinding/GroupItemBinding;)V", "bindTitle", "", "text", "", "focus", "hasFocus", "", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    public static final class ViewHolder extends RecyclerView.ViewHolder {
        private final GroupItemBinding binding;
        private final Context context;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ViewHolder(Context context, GroupItemBinding binding) {
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

        public final void focus(boolean z) {
            Log.i("favorite", "focus : " + z);
            if (z) {
                this.binding.title.setTextColor(ContextCompat.getColor(this.context, R.color.white));
                this.binding.groupItem.setBackgroundColor(ContextCompat.getColor(this.context, R.color.menu_focus_background));
                return;
            }
            this.binding.title.setTextColor(ContextCompat.getColor(this.context, R.color.white));
            this.binding.groupItem.setBackgroundColor(ContextCompat.getColor(this.context, R.color.menu_background));
        }
    }

    public final void toPosition(final int i) {
        Log.i("favorite", "toPosition Group Position: " + i);
        this.recyclerView.post(new Runnable() { // from class: com.pjy.koreatv.GroupAdapter$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                GroupAdapter.toPosition$lambda$6(GroupAdapter.this, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void toPosition$lambda$6(final GroupAdapter this$0, final int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        RecyclerView.LayoutManager layoutManager = this$0.recyclerView.getLayoutManager();
        LinearLayoutManager linearLayoutManager = layoutManager instanceof LinearLayoutManager ? (LinearLayoutManager) layoutManager : null;
        if (linearLayoutManager != null) {
            linearLayoutManager.scrollToPositionWithOffset(i, 0);
        }
        this$0.recyclerView.postDelayed(new Runnable() { // from class: com.pjy.koreatv.GroupAdapter$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                GroupAdapter.toPosition$lambda$6$lambda$5(GroupAdapter.this, i);
            }
        }, 0L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void toPosition$lambda$6$lambda$5(GroupAdapter this$0, int i) {
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

    public final void update(TVGroupModel tvGroupModel) {
        Intrinsics.checkNotNullParameter(tvGroupModel, "tvGroupModel");
        this.tvGroupModel = tvGroupModel;
        this.recyclerView.post(new Runnable() { // from class: com.pjy.koreatv.GroupAdapter$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                GroupAdapter.update$lambda$7(GroupAdapter.this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$7(GroupAdapter this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.notifyDataSetChanged();
    }

    /* compiled from: GroupAdapter.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/pjy/koreatv/GroupAdapter$Companion;", "", "()V", "TAG", "", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
