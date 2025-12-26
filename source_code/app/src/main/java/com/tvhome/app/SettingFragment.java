package com.tvhome.app;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.tvhome.app.databinding.SettingBinding;
import com.tvhome.app.models.TVList;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
/* compiled from: SettingFragment.kt */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0005\u0018\u0000 %2\u00020\u0001:\u0001%B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\f\u001a\u00020\rH\u0002J$\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J\b\u0010\u0016\u001a\u00020\rH\u0016J\u0010\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J-\u0010\u001a\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\u001c2\u000e\u0010\u001d\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001f0\u001e2\u0006\u0010 \u001a\u00020!H\u0016¢\u0006\u0002\u0010\"J\b\u0010#\u001a\u00020\rH\u0002J\b\u0010$\u001a\u00020\rH\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lcom/pjy/koreatv/SettingFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/pjy/koreatv/databinding/SettingBinding;", "binding", "getBinding", "()Lcom/pjy/koreatv/databinding/SettingBinding;", "updateManager", "Lcom/pjy/koreatv/UpdateManager;", "uri", "Landroid/net/Uri;", "hideSelf", "", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onHiddenChanged", "hidden", "", "onRequestPermissionsResult", "requestCode", "", "permissions", "", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "requestInstallPermissions", "requestReadPermissions", "Companion", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class SettingFragment extends Fragment {
    public static final Companion Companion = new Companion(null);
    public static final int PERMISSIONS_REQUEST_CODE = 1;
    public static final int PERMISSION_READ_EXTERNAL_STORAGE_REQUEST_CODE = 2;
    public static final String TAG = "SettingFragment";
    private SettingBinding _binding;
    private UpdateManager updateManager;
    private Uri uri;

    private final SettingBinding getBinding() {
        SettingBinding settingBinding = this._binding;
        Intrinsics.checkNotNull(settingBinding);
        return settingBinding;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Editable newEditable;
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        Context requireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(requireContext, "requireContext(...)");
        this._binding = SettingBinding.inflate(inflater, viewGroup, false);
        getBinding().versionName.setText("当前版本: v" + ExtKt.getAppVersionName(requireContext));
        getBinding().versionName.setVisibility(8);
        getBinding().version.setVisibility(8);
        SettingBinding settingBinding = this._binding;
        Switch r13 = settingBinding != null ? settingBinding.switchChannelReversal : null;
        if (r13 != null) {
            r13.setChecked(SP.INSTANCE.getChannelReversal());
        }
        if (r13 != null) {
            r13.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.pjy.koreatv.SettingFragment$$ExternalSyntheticLambda1
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SettingFragment.onCreateView$lambda$0(SettingFragment.this, compoundButton, z);
                }
            });
        }
        SettingBinding settingBinding2 = this._binding;
        Switch r132 = settingBinding2 != null ? settingBinding2.switchChannelNum : null;
        if (r132 != null) {
            r132.setChecked(SP.INSTANCE.getChannelNum());
        }
        if (r132 != null) {
            r132.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.pjy.koreatv.SettingFragment$$ExternalSyntheticLambda2
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SettingFragment.onCreateView$lambda$1(SettingFragment.this, compoundButton, z);
                }
            });
        }
        SettingBinding settingBinding3 = this._binding;
        Switch r133 = settingBinding3 != null ? settingBinding3.switchTime : null;
        if (r133 != null) {
            r133.setChecked(SP.INSTANCE.getTime());
        }
        if (r133 != null) {
            r133.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.pjy.koreatv.SettingFragment$$ExternalSyntheticLambda3
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SettingFragment.onCreateView$lambda$2(SettingFragment.this, compoundButton, z);
                }
            });
        }
        SettingBinding settingBinding4 = this._binding;
        Switch r134 = settingBinding4 != null ? settingBinding4.switchBootStartup : null;
        if (r134 != null) {
            r134.setChecked(SP.INSTANCE.getBootStartup());
        }
        if (r134 != null) {
            r134.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.pjy.koreatv.SettingFragment$$ExternalSyntheticLambda4
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SettingFragment.onCreateView$lambda$3(SettingFragment.this, compoundButton, z);
                }
            });
        }
        EditText config = getBinding().config;
        Intrinsics.checkNotNullExpressionValue(config, "config");
        config.setVisibility(8);
        String config2 = SP.INSTANCE.getConfig();
        if (config2 == null || (newEditable = Editable.Factory.getInstance().newEditable(config2)) == null) {
            newEditable = Editable.Factory.getInstance().newEditable("");
        }
        config.setText(newEditable);
        Uri parse = Uri.parse("https://kenpark76.github.io/koreatv.json");
        Intrinsics.checkNotNullExpressionValue(parse, "parse(...)");
        this.uri = parse;
        if (parse == null) {
            Intrinsics.throwUninitializedPropertyAccessException("uri");
            parse = null;
        }
        if (Intrinsics.areEqual(parse.getScheme(), "")) {
            Uri uri = this.uri;
            if (uri == null) {
                Intrinsics.throwUninitializedPropertyAccessException("uri");
                uri = null;
            }
            Uri build = uri.buildUpon().scheme("http").build();
            Intrinsics.checkNotNullExpressionValue(build, "build(...)");
            this.uri = build;
        }
        StringBuilder sb = new StringBuilder("my Uri ");
        Uri uri2 = this.uri;
        if (uri2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("uri");
            uri2 = null;
        }
        sb.append(uri2);
        Log.i(TAG, sb.toString());
        Uri uri3 = this.uri;
        if (uri3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("uri");
            uri3 = null;
        }
        if (uri3.isAbsolute()) {
            Log.i(TAG, "Uri ok");
            Uri uri4 = this.uri;
            if (uri4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("uri");
                uri4 = null;
            }
            if (Intrinsics.areEqual(uri4.getScheme(), "file")) {
                requestReadPermissions();
            } else {
                TVList tVList = TVList.INSTANCE;
                Uri uri5 = this.uri;
                if (uri5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("uri");
                    uri5 = null;
                }
                tVList.parseUri(uri5);
            }
        }
        Log.i(TAG, "tvlist " + SP.INSTANCE.getPosition());
        FragmentActivity activity = getActivity();
        Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type com.pjy.koreatv.MainActivity");
        ((MainActivity) activity).settingActive();
        getBinding().confirmConfig.setVisibility(8);
        final EditText channel = getBinding().channel;
        Intrinsics.checkNotNullExpressionValue(channel, "channel");
        Editable newEditable2 = Editable.Factory.getInstance().newEditable(String.valueOf(SP.INSTANCE.getChannel()));
        if (newEditable2 == null) {
            newEditable2 = Editable.Factory.getInstance().newEditable("");
        }
        channel.setText(newEditable2);
        getBinding().confirmChannel.setOnClickListener(new View.OnClickListener() { // from class: com.pjy.koreatv.SettingFragment$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingFragment.onCreateView$lambda$8(channel, this, view);
            }
        });
        getBinding().setting.setOnClickListener(new View.OnClickListener() { // from class: com.pjy.koreatv.SettingFragment$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingFragment.onCreateView$lambda$9(SettingFragment.this, view);
            }
        });
        getBinding().exit.setOnClickListener(new View.OnClickListener() { // from class: com.pjy.koreatv.SettingFragment$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingFragment.onCreateView$lambda$10(SettingFragment.this, view);
            }
        });
        Context applicationContext = requireActivity().getApplicationContext();
        Intrinsics.checkNotNull(applicationContext, "null cannot be cast to non-null type com.pjy.koreatv.MyTVApplication");
        MyTVApplication myTVApplication = (MyTVApplication) applicationContext;
        getBinding().content.getLayoutParams().width = myTVApplication.px2Px(getBinding().content.getLayoutParams().width);
        getBinding().content.setPadding(myTVApplication.px2Px(getBinding().content.getPaddingLeft()), myTVApplication.px2Px(getBinding().content.getPaddingTop()), myTVApplication.px2Px(getBinding().content.getPaddingRight()), myTVApplication.px2Px(getBinding().content.getPaddingBottom()));
        getBinding().name.setTextSize(myTVApplication.px2PxFont(getBinding().name.getTextSize()));
        getBinding().version.setTextSize(myTVApplication.px2PxFont(getBinding().version.getTextSize()));
        ViewGroup.LayoutParams layoutParams = getBinding().version.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        TextView version = getBinding().version;
        Intrinsics.checkNotNullExpressionValue(version, "version");
        ViewGroup.LayoutParams layoutParams2 = version.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams2 = layoutParams2 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams2 : null;
        marginLayoutParams.topMargin = myTVApplication.px2Px(marginLayoutParams2 != null ? marginLayoutParams2.topMargin : 0);
        getBinding().version.setLayoutParams(marginLayoutParams);
        int px2Px = myTVApplication.px2Px(getBinding().confirmConfig.getLayoutParams().width);
        int px2Px2 = myTVApplication.px2Px(getBinding().confirmConfig.getLayoutParams().height);
        float px2PxFont = myTVApplication.px2PxFont(getBinding().confirmConfig.getTextSize());
        ViewGroup.LayoutParams layoutParams3 = getBinding().confirmConfig.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams3, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) layoutParams3;
        Button confirmConfig = getBinding().confirmConfig;
        Intrinsics.checkNotNullExpressionValue(confirmConfig, "confirmConfig");
        ViewGroup.LayoutParams layoutParams4 = confirmConfig.getLayoutParams();
        marginLayoutParams3.setMarginEnd(myTVApplication.px2Px(layoutParams4 instanceof ViewGroup.MarginLayoutParams ? ((ViewGroup.MarginLayoutParams) layoutParams4).getMarginEnd() : 0));
        int px2Px3 = myTVApplication.px2Px(getBinding().config.getLayoutParams().width);
        float px2PxFont2 = myTVApplication.px2PxFont(getBinding().config.getTextSize());
        getBinding().confirmConfig.getLayoutParams().width = px2Px;
        getBinding().confirmConfig.getLayoutParams().height = px2Px2;
        getBinding().confirmConfig.setTextSize(px2PxFont);
        getBinding().confirmConfig.setLayoutParams(marginLayoutParams3);
        getBinding().config.getLayoutParams().width = px2Px3;
        getBinding().config.setTextSize(px2PxFont2);
        int i = (int) (px2Px * 1.5d);
        getBinding().confirmChannel.getLayoutParams().width = i;
        int i2 = (int) (px2Px2 * 1.5d);
        getBinding().confirmChannel.getLayoutParams().height = i2;
        getBinding().confirmChannel.setTextSize(px2PxFont);
        getBinding().channel.getLayoutParams().width = px2Px3;
        getBinding().channel.setTextSize(px2PxFont2);
        getBinding().exit.getLayoutParams().width = i;
        getBinding().exit.getLayoutParams().height = i2;
        getBinding().exit.setTextSize(px2PxFont);
        float px2PxFont3 = myTVApplication.px2PxFont(getBinding().switchChannelReversal.getTextSize());
        ViewGroup.LayoutParams layoutParams5 = getBinding().switchChannelReversal.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams5, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        ViewGroup.MarginLayoutParams marginLayoutParams4 = (ViewGroup.MarginLayoutParams) layoutParams5;
        Switch switchChannelReversal = getBinding().switchChannelReversal;
        Intrinsics.checkNotNullExpressionValue(switchChannelReversal, "switchChannelReversal");
        ViewGroup.LayoutParams layoutParams6 = switchChannelReversal.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams5 = layoutParams6 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams6 : null;
        marginLayoutParams4.topMargin = myTVApplication.px2Px(marginLayoutParams5 != null ? marginLayoutParams5.topMargin : 0);
        getBinding().switchChannelReversal.setTextSize(px2PxFont3);
        ViewGroup.MarginLayoutParams marginLayoutParams6 = marginLayoutParams4;
        getBinding().switchChannelReversal.setLayoutParams(marginLayoutParams6);
        getBinding().switchChannelNum.setTextSize(px2PxFont3);
        getBinding().switchChannelNum.setLayoutParams(marginLayoutParams6);
        getBinding().switchTime.setTextSize(px2PxFont3);
        getBinding().switchTime.setLayoutParams(marginLayoutParams6);
        getBinding().switchBootStartup.setTextSize(px2PxFont3);
        getBinding().switchBootStartup.setLayoutParams(marginLayoutParams6);
        this.updateManager = new UpdateManager(requireContext, ExtKt.getAppVersionCode(requireContext));
        LinearLayout root = getBinding().getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "getRoot(...)");
        return root;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreateView$lambda$0(SettingFragment this$0, CompoundButton compoundButton, boolean z) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        SP.INSTANCE.setChannelReversal(z);
        FragmentActivity activity = this$0.getActivity();
        Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type com.pjy.koreatv.MainActivity");
        ((MainActivity) activity).settingActive();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreateView$lambda$1(SettingFragment this$0, CompoundButton compoundButton, boolean z) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        SP.INSTANCE.setChannelNum(z);
        FragmentActivity activity = this$0.getActivity();
        Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type com.pjy.koreatv.MainActivity");
        ((MainActivity) activity).settingActive();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreateView$lambda$2(SettingFragment this$0, CompoundButton compoundButton, boolean z) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        SP.INSTANCE.setTime(z);
        FragmentActivity activity = this$0.getActivity();
        Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type com.pjy.koreatv.MainActivity");
        ((MainActivity) activity).settingActive();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreateView$lambda$3(SettingFragment this$0, CompoundButton compoundButton, boolean z) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        SP.INSTANCE.setBootStartup(z);
        FragmentActivity activity = this$0.getActivity();
        Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type com.pjy.koreatv.MainActivity");
        ((MainActivity) activity).settingActive();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreateView$lambda$8(EditText defaultChannel, SettingFragment this$0, View view) {
        int i;
        Intrinsics.checkNotNullParameter(defaultChannel, "$defaultChannel");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String obj = StringsKt.trim((CharSequence) defaultChannel.getText().toString()).toString();
        boolean z = false;
        try {
            i = Integer.parseInt(obj);
        } catch (NumberFormatException e) {
            defaultChannel.setError(ContextCompat.getString(MyTVApplication.Companion.getInstance(), R.string.invalid_channel) + ':' + obj);
            Editable newEditable = Editable.Factory.getInstance().newEditable(String.valueOf(SP.INSTANCE.getChannel()));
            if (newEditable == null) {
                newEditable = Editable.Factory.getInstance().newEditable("");
            }
            defaultChannel.setText(newEditable);
            System.out.println(e);
            i = 0;
            z = true;
        }
        if (!z) {
            if (i >= 0 && i <= TVList.INSTANCE.getListModel().size()) {
                SP.INSTANCE.setChannel(i);
                defaultChannel.setError(ContextCompat.getString(MyTVApplication.Companion.getInstance(), R.string.setting_success));
            } else {
                defaultChannel.setError(ContextCompat.getString(MyTVApplication.Companion.getInstance(), R.string.invalid_channel) + ':' + i);
                Editable newEditable2 = Editable.Factory.getInstance().newEditable(String.valueOf(SP.INSTANCE.getChannel()));
                if (newEditable2 == null) {
                    newEditable2 = Editable.Factory.getInstance().newEditable("");
                }
                defaultChannel.setText(newEditable2);
            }
        }
        FragmentActivity activity = this$0.getActivity();
        Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type com.pjy.koreatv.MainActivity");
        ((MainActivity) activity).settingActive();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreateView$lambda$9(SettingFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.hideSelf();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreateView$lambda$10(SettingFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.requireActivity().finishAffinity();
    }

    private final void hideSelf() {
        requireActivity().getSupportFragmentManager().beginTransaction().hide(this).commit();
        FragmentActivity activity = getActivity();
        Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type com.pjy.koreatv.MainActivity");
        ((MainActivity) activity).showTime();
    }

    @Override // androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        Editable newEditable;
        super.onHiddenChanged(z);
        if (z) {
            return;
        }
        EditText config = getBinding().config;
        Intrinsics.checkNotNullExpressionValue(config, "config");
        String config2 = SP.INSTANCE.getConfig();
        if (config2 == null || (newEditable = Editable.Factory.getInstance().newEditable(config2)) == null) {
            newEditable = Editable.Factory.getInstance().newEditable("");
        }
        config.setText(newEditable);
    }

    private final void requestInstallPermissions() {
        boolean canRequestPackageInstalls;
        Context requireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(requireContext, "requireContext(...)");
        ArrayList arrayList = new ArrayList();
        if (Build.VERSION.SDK_INT >= 26) {
            canRequestPackageInstalls = requireContext.getPackageManager().canRequestPackageInstalls();
            if (!canRequestPackageInstalls) {
                arrayList.add("android.permission.REQUEST_INSTALL_PACKAGES");
            }
        }
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(requireContext, "android.permission.READ_EXTERNAL_STORAGE") != 0) {
            arrayList.add("android.permission.READ_EXTERNAL_STORAGE");
        }
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(requireContext, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            arrayList.add("android.permission.WRITE_EXTERNAL_STORAGE");
        }
        ArrayList arrayList2 = arrayList;
        if (!arrayList2.isEmpty()) {
            ActivityCompat.requestPermissions(requireActivity(), (String[]) arrayList2.toArray(new String[0]), 1);
            return;
        }
        UpdateManager updateManager = this.updateManager;
        if (updateManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("updateManager");
            updateManager = null;
        }
        updateManager.checkAndUpdate();
    }

    private final void requestReadPermissions() {
        Context requireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(requireContext, "requireContext(...)");
        ArrayList arrayList = new ArrayList();
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(requireContext, "android.permission.READ_EXTERNAL_STORAGE") != 0) {
            arrayList.add("android.permission.READ_EXTERNAL_STORAGE");
        }
        ArrayList arrayList2 = arrayList;
        if (!arrayList2.isEmpty()) {
            ActivityCompat.requestPermissions(requireActivity(), (String[]) arrayList2.toArray(new String[0]), 1);
            return;
        }
        TVList tVList = TVList.INSTANCE;
        Uri uri = this.uri;
        if (uri == null) {
            Intrinsics.throwUninitializedPropertyAccessException("uri");
            uri = null;
        }
        tVList.parseUri(uri);
    }

    @Override // androidx.fragment.app.Fragment
    public void onRequestPermissionsResult(int i, String[] permissions, int[] grantResults) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        Intrinsics.checkNotNullParameter(grantResults, "grantResults");
        super.onRequestPermissionsResult(i, permissions, grantResults);
        UpdateManager updateManager = null;
        boolean z = false;
        if (i == 2) {
            if ((!(grantResults.length == 0)) && grantResults[0] == 0) {
                TVList tVList = TVList.INSTANCE;
                Uri uri = this.uri;
                if (uri == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("uri");
                    uri = null;
                }
                tVList.parseUri(uri);
            } else {
                ExtKt.showToast("Authentication Fail", 1);
            }
        }
        if (i == 1) {
            int length = grantResults.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    z = true;
                    break;
                } else if (grantResults[i2] != 0) {
                    break;
                } else {
                    i2++;
                }
            }
            if (z) {
                UpdateManager updateManager2 = this.updateManager;
                if (updateManager2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("updateManager");
                } else {
                    updateManager = updateManager2;
                }
                updateManager.checkAndUpdate();
                return;
            }
            ExtKt.showToast("Authentication Fail", 1);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this._binding = null;
    }

    /* compiled from: SettingFragment.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lcom/pjy/koreatv/SettingFragment$Companion;", "", "()V", "PERMISSIONS_REQUEST_CODE", "", "PERMISSION_READ_EXTERNAL_STORAGE_REQUEST_CODE", "TAG", "", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
