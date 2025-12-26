package com.pjy.koreatv;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.pjy.koreatv.GroupAdapter;
import com.pjy.koreatv.ListAdapter;
import com.pjy.koreatv.databinding.MenuBinding;
import com.pjy.koreatv.models.TVGroupModel;
import com.pjy.koreatv.models.TVList;
import com.pjy.koreatv.models.TVListModel;
import com.pjy.koreatv.models.TVModel;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: MenuFragment.kt */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 +2\u00020\u00012\u00020\u00022\u00020\u0003:\u0001+B\u0005¢\u0006\u0002\u0010\u0004J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\u0012\u0010\u0010\u001a\u00020\u000f2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016J$\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016J\b\u0010\u0019\u001a\u00020\u000fH\u0016J\u0010\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0010\u0010\u001d\u001a\u00020\u000f2\u0006\u0010 \u001a\u00020!H\u0016J\u0018\u0010\"\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\u001cH\u0016J\u0018\u0010\"\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010%\u001a\u00020\u001cH\u0016J\u0018\u0010&\u001a\u00020\u001c2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010'\u001a\u00020!H\u0016J\u0010\u0010&\u001a\u00020\u001c2\u0006\u0010'\u001a\u00020!H\u0016J\b\u0010(\u001a\u00020\u000fH\u0016J\u0006\u0010)\u001a\u00020\u000fJ\u000e\u0010*\u001a\u00020\u000f2\u0006\u0010 \u001a\u00020!R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000¨\u0006,"}, d2 = {"Lcom/pjy/koreatv/MenuFragment;", "Landroidx/fragment/app/Fragment;", "Lcom/pjy/koreatv/GroupAdapter$ItemListener;", "Lcom/pjy/koreatv/ListAdapter$ItemListener;", "()V", "_binding", "Lcom/pjy/koreatv/databinding/MenuBinding;", "binding", "getBinding", "()Lcom/pjy/koreatv/databinding/MenuBinding;", "groupAdapter", "Lcom/pjy/koreatv/GroupAdapter;", "listAdapter", "Lcom/pjy/koreatv/ListAdapter;", "hideSelf", "", "onActivityCreated", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onDestroyView", "onHiddenChanged", "hidden", "", "onItemClicked", "tvModel", "Lcom/pjy/koreatv/models/TVModel;", "position", "", "onItemFocusChange", "tvListModel", "Lcom/pjy/koreatv/models/TVListModel;", "hasFocus", "onKey", "keyCode", "onResume", "update", "updateList", "Companion", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class MenuFragment extends Fragment implements GroupAdapter.ItemListener, ListAdapter.ItemListener {
    public static final Companion Companion = new Companion(null);
    private static final String TAG = "MenuFragment";
    private MenuBinding _binding;
    private GroupAdapter groupAdapter;
    private ListAdapter listAdapter;

    private final MenuBinding getBinding() {
        MenuBinding menuBinding = this._binding;
        Intrinsics.checkNotNull(menuBinding);
        return menuBinding;
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        Context requireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(requireContext, "requireContext(...)");
        this._binding = MenuBinding.inflate(inflater, viewGroup, false);
        RecyclerView group = getBinding().group;
        Intrinsics.checkNotNullExpressionValue(group, "group");
        this.groupAdapter = new GroupAdapter(requireContext, group, TVList.INSTANCE.getGroupModel());
        RecyclerView recyclerView = getBinding().group;
        GroupAdapter groupAdapter = this.groupAdapter;
        ListAdapter listAdapter = null;
        if (groupAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("groupAdapter");
            groupAdapter = null;
        }
        recyclerView.setAdapter(groupAdapter);
        getBinding().group.setLayoutManager(new LinearLayoutManager(requireContext));
        GroupAdapter groupAdapter2 = this.groupAdapter;
        if (groupAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("groupAdapter");
            groupAdapter2 = null;
        }
        groupAdapter2.setItemListener(this);
        TVGroupModel groupModel = TVList.INSTANCE.getGroupModel();
        Integer value = TVList.INSTANCE.getGroupModel().getPosition().getValue();
        Intrinsics.checkNotNull(value);
        if (groupModel.getTVListModel(value.intValue()) == null) {
            TVList.INSTANCE.getGroupModel().setPosition(0);
        }
        TVGroupModel groupModel2 = TVList.INSTANCE.getGroupModel();
        Integer value2 = TVList.INSTANCE.getGroupModel().getPosition().getValue();
        Intrinsics.checkNotNull(value2);
        TVListModel tVListModel = groupModel2.getTVListModel(value2.intValue());
        RecyclerView list = getBinding().list;
        Intrinsics.checkNotNullExpressionValue(list, "list");
        Intrinsics.checkNotNull(tVListModel);
        this.listAdapter = new ListAdapter(requireContext, list, tVListModel, TVList.INSTANCE.getGroupModel());
        RecyclerView recyclerView2 = getBinding().list;
        ListAdapter listAdapter2 = this.listAdapter;
        if (listAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("listAdapter");
            listAdapter2 = null;
        }
        recyclerView2.setAdapter(listAdapter2);
        getBinding().list.setLayoutManager(new LinearLayoutManager(requireContext));
        ListAdapter listAdapter3 = this.listAdapter;
        if (listAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("listAdapter");
            listAdapter3 = null;
        }
        listAdapter3.focusable(false);
        ListAdapter listAdapter4 = this.listAdapter;
        if (listAdapter4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("listAdapter");
        } else {
            listAdapter = listAdapter4;
        }
        listAdapter.setItemListener(this);
        getBinding().menu.setOnClickListener(new View.OnClickListener() { // from class: com.pjy.koreatv.MenuFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MenuFragment.onCreateView$lambda$0(MenuFragment.this, view);
            }
        });
        LinearLayout root = getBinding().getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "getRoot(...)");
        return root;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreateView$lambda$0(MenuFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.hideSelf();
    }

    public final void update() {
        View view = getView();
        if (view != null) {
            view.post(new Runnable() { // from class: com.pjy.koreatv.MenuFragment$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    MenuFragment.update$lambda$1(MenuFragment.this);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$1(MenuFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        GroupAdapter groupAdapter = this$0.groupAdapter;
        if (groupAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("groupAdapter");
            groupAdapter = null;
        }
        groupAdapter.update(TVList.INSTANCE.getGroupModel());
        TVGroupModel groupModel = TVList.INSTANCE.getGroupModel();
        Integer value = TVList.INSTANCE.getGroupModel().getPosition().getValue();
        Intrinsics.checkNotNull(value);
        if (groupModel.getTVListModel(value.intValue()) == null) {
            TVList.INSTANCE.getGroupModel().setPosition(0);
        }
        TVGroupModel groupModel2 = TVList.INSTANCE.getGroupModel();
        Integer value2 = TVList.INSTANCE.getGroupModel().getPosition().getValue();
        Intrinsics.checkNotNull(value2);
        TVListModel tVListModel = groupModel2.getTVListModel(value2.intValue());
        if (tVListModel != null) {
            RecyclerView.Adapter adapter = this$0.getBinding().list.getAdapter();
            Intrinsics.checkNotNull(adapter, "null cannot be cast to non-null type com.pjy.koreatv.ListAdapter");
            ((ListAdapter) adapter).update(tVListModel);
        }
    }

    public final void updateList(int i) {
        TVList.INSTANCE.getGroupModel().setPosition(i);
        SP.INSTANCE.setPositionGroup(i);
        TVListModel tVListModel = TVList.INSTANCE.getGroupModel().getTVListModel();
        if (tVListModel != null) {
            RecyclerView.Adapter adapter = getBinding().list.getAdapter();
            Intrinsics.checkNotNull(adapter, "null cannot be cast to non-null type com.pjy.koreatv.ListAdapter");
            ((ListAdapter) adapter).update(tVListModel);
        }
    }

    private final void hideSelf() {
        requireActivity().getSupportFragmentManager().beginTransaction().hide(this).commit();
    }

    @Override // com.pjy.koreatv.GroupAdapter.ItemListener
    public void onItemFocusChange(TVListModel tvListModel, boolean z) {
        Intrinsics.checkNotNullParameter(tvListModel, "tvListModel");
        if (z) {
            RecyclerView.Adapter adapter = getBinding().list.getAdapter();
            Intrinsics.checkNotNull(adapter, "null cannot be cast to non-null type com.pjy.koreatv.ListAdapter");
            ((ListAdapter) adapter).update(tvListModel);
            FragmentActivity activity = getActivity();
            Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type com.pjy.koreatv.MainActivity");
            ((MainActivity) activity).menuActive();
        }
    }

    @Override // com.pjy.koreatv.GroupAdapter.ItemListener
    public void onItemClicked(int i) {
        Log.i("favorite", "MF onItemClicked Group Position: " + i);
    }

    @Override // com.pjy.koreatv.ListAdapter.ItemListener
    public void onItemFocusChange(TVModel tvModel, boolean z) {
        Intrinsics.checkNotNullParameter(tvModel, "tvModel");
        if (z) {
            FragmentActivity activity = getActivity();
            Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type com.pjy.koreatv.MainActivity");
            ((MainActivity) activity).menuActive();
        }
    }

    @Override // com.pjy.koreatv.ListAdapter.ItemListener
    public void onItemClicked(TVModel tvModel) {
        Intrinsics.checkNotNullParameter(tvModel, "tvModel");
        TVList.INSTANCE.setPosition(tvModel.getTv().getId());
        FragmentActivity activity = getActivity();
        Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type com.pjy.koreatv.MainActivity");
        ((MainActivity) activity).hideMenuFragment();
    }

    @Override // com.pjy.koreatv.GroupAdapter.ItemListener
    public boolean onKey(int i) {
        if (i != 21) {
            if (i != 22) {
                return false;
            }
            ListAdapter listAdapter = this.listAdapter;
            ListAdapter listAdapter2 = null;
            if (listAdapter == null) {
                Intrinsics.throwUninitializedPropertyAccessException("listAdapter");
                listAdapter = null;
            }
            if (listAdapter.getItemCount() == 0) {
                String string = getString(R.string.channel_request_error);
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                ExtKt.showToast$default(string, 0, 1, null);
                return true;
            }
            GroupAdapter groupAdapter = this.groupAdapter;
            if (groupAdapter == null) {
                Intrinsics.throwUninitializedPropertyAccessException("groupAdapter");
                groupAdapter = null;
            }
            groupAdapter.focusable(false);
            ListAdapter listAdapter3 = this.listAdapter;
            if (listAdapter3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("listAdapter");
                listAdapter3 = null;
            }
            listAdapter3.focusable(true);
            ListAdapter listAdapter4 = this.listAdapter;
            if (listAdapter4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("listAdapter");
                listAdapter4 = null;
            }
            listAdapter4.toPosition(TVList.INSTANCE.getTVModel().getListIndex());
            int groupIndex = TVList.INSTANCE.getTVModel().getGroupIndex();
            Integer value = TVList.INSTANCE.getGroupModel().getPosition().getValue();
            Intrinsics.checkNotNull(value);
            if (groupIndex == value.intValue()) {
                GroupAdapter groupAdapter2 = this.groupAdapter;
                if (groupAdapter2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("groupAdapter");
                    groupAdapter2 = null;
                }
                groupAdapter2.toPosition(TVList.INSTANCE.getTVModel().getGroupIndex());
                ListAdapter listAdapter5 = this.listAdapter;
                if (listAdapter5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("listAdapter");
                } else {
                    listAdapter2 = listAdapter5;
                }
                listAdapter2.toPosition(TVList.INSTANCE.getTVModel().getListIndex());
            } else {
                GroupAdapter groupAdapter3 = this.groupAdapter;
                if (groupAdapter3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("groupAdapter");
                    groupAdapter3 = null;
                }
                Integer value2 = TVList.INSTANCE.getGroupModel().getPosition().getValue();
                Intrinsics.checkNotNull(value2);
                groupAdapter3.toPosition(value2.intValue());
                ListAdapter listAdapter6 = this.listAdapter;
                if (listAdapter6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("listAdapter");
                } else {
                    listAdapter2 = listAdapter6;
                }
                listAdapter2.toPosition(0);
            }
        }
        return true;
    }

    @Override // com.pjy.koreatv.ListAdapter.ItemListener
    public boolean onKey(ListAdapter listAdapter, int i) {
        Intrinsics.checkNotNullParameter(listAdapter, "listAdapter");
        if (i == 21) {
            getBinding().group.setVisibility(0);
            GroupAdapter groupAdapter = this.groupAdapter;
            GroupAdapter groupAdapter2 = null;
            if (groupAdapter == null) {
                Intrinsics.throwUninitializedPropertyAccessException("groupAdapter");
                groupAdapter = null;
            }
            groupAdapter.focusable(true);
            listAdapter.focusable(false);
            getBinding().list.setVisibility(0);
            GroupAdapter groupAdapter3 = this.groupAdapter;
            if (groupAdapter3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("groupAdapter");
            } else {
                groupAdapter2 = groupAdapter3;
            }
            Integer value = TVList.INSTANCE.getGroupModel().getPosition().getValue();
            Intrinsics.checkNotNull(value);
            groupAdapter2.toPosition(value.intValue());
            return true;
        }
        return false;
    }

    @Override // androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        if (!z) {
            RecyclerView list = getBinding().list;
            Intrinsics.checkNotNullExpressionValue(list, "list");
            GroupAdapter groupAdapter = null;
            if (list.getVisibility() == 0) {
                if (TVList.INSTANCE.size() == 0) {
                    String string = getString(R.string.channel_request_error);
                    Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                    ExtKt.showToast$default(string, 0, 1, null);
                    return;
                }
                int groupIndex = TVList.INSTANCE.getTVModel().getGroupIndex();
                if (TVList.INSTANCE.getEpgReceived()) {
                    updateList(groupIndex);
                }
                Integer value = TVList.INSTANCE.getGroupModel().getPosition().getValue();
                Intrinsics.checkNotNull(value);
                if (groupIndex == value.intValue()) {
                    ListAdapter listAdapter = this.listAdapter;
                    if (listAdapter == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("listAdapter");
                        listAdapter = null;
                    }
                    if (listAdapter.getTvListModel().getIndex() != TVList.INSTANCE.getTVModel().getGroupIndex()) {
                        updateList(groupIndex);
                    }
                    ListAdapter listAdapter2 = this.listAdapter;
                    if (listAdapter2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("listAdapter");
                        listAdapter2 = null;
                    }
                    listAdapter2.toPosition(TVList.INSTANCE.getTVModel().getListIndex());
                }
            }
            RecyclerView group = getBinding().group;
            Intrinsics.checkNotNullExpressionValue(group, "group");
            if (group.getVisibility() == 0) {
                int groupIndex2 = TVList.INSTANCE.getTVModel().getGroupIndex();
                GroupAdapter groupAdapter2 = this.groupAdapter;
                if (groupAdapter2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("groupAdapter");
                } else {
                    groupAdapter = groupAdapter2;
                }
                groupAdapter.toPosition(groupIndex2);
                Log.i("favorite", "MF onHiddenChanged: group on show toPosition " + groupIndex2 + '/' + TVList.INSTANCE.getGroupModel().getPosition().getValue());
            }
            FragmentActivity activity = getActivity();
            Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type com.pjy.koreatv.MainActivity");
            ((MainActivity) activity).menuActive();
            return;
        }
        View view = getView();
        if (view != null) {
            view.post(new Runnable() { // from class: com.pjy.koreatv.MenuFragment$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    MenuFragment.onHiddenChanged$lambda$2(MenuFragment.this);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onHiddenChanged$lambda$2(MenuFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        GroupAdapter groupAdapter = this$0.groupAdapter;
        ListAdapter listAdapter = null;
        if (groupAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("groupAdapter");
            groupAdapter = null;
        }
        groupAdapter.setVisiable(false);
        ListAdapter listAdapter2 = this$0.listAdapter;
        if (listAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("listAdapter");
        } else {
            listAdapter = listAdapter2;
        }
        listAdapter.setVisiable(false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this._binding = null;
    }

    /* compiled from: MenuFragment.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/pjy/koreatv/MenuFragment$Companion;", "", "()V", "TAG", "", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
