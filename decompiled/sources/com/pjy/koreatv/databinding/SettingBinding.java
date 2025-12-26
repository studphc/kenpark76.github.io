package com.pjy.koreatv.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.pjy.koreatv.R;
/* loaded from: classes3.dex */
public final class SettingBinding implements ViewBinding {
    public final EditText channel;
    public final EditText config;
    public final Button confirmChannel;
    public final Button confirmConfig;
    public final ScrollView container;
    public final LinearLayout content;
    public final Button exit;
    public final TextView name;
    private final LinearLayout rootView;
    public final LinearLayout setting;
    public final Switch switchBootStartup;
    public final Switch switchChannelNum;
    public final Switch switchChannelReversal;
    public final Switch switchTime;
    public final TextView version;
    public final TextView versionName;

    private SettingBinding(LinearLayout linearLayout, EditText editText, EditText editText2, Button button, Button button2, ScrollView scrollView, LinearLayout linearLayout2, Button button3, TextView textView, LinearLayout linearLayout3, Switch r13, Switch r14, Switch r15, Switch r16, TextView textView2, TextView textView3) {
        this.rootView = linearLayout;
        this.channel = editText;
        this.config = editText2;
        this.confirmChannel = button;
        this.confirmConfig = button2;
        this.container = scrollView;
        this.content = linearLayout2;
        this.exit = button3;
        this.name = textView;
        this.setting = linearLayout3;
        this.switchBootStartup = r13;
        this.switchChannelNum = r14;
        this.switchChannelReversal = r15;
        this.switchTime = r16;
        this.version = textView2;
        this.versionName = textView3;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static SettingBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static SettingBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.setting, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static SettingBinding bind(View view) {
        int i = R.id.channel;
        EditText editText = (EditText) ViewBindings.findChildViewById(view, i);
        if (editText != null) {
            i = R.id.config;
            EditText editText2 = (EditText) ViewBindings.findChildViewById(view, i);
            if (editText2 != null) {
                i = R.id.confirm_channel;
                Button button = (Button) ViewBindings.findChildViewById(view, i);
                if (button != null) {
                    i = R.id.confirm_config;
                    Button button2 = (Button) ViewBindings.findChildViewById(view, i);
                    if (button2 != null) {
                        i = R.id.container;
                        ScrollView scrollView = (ScrollView) ViewBindings.findChildViewById(view, i);
                        if (scrollView != null) {
                            i = R.id.content;
                            LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i);
                            if (linearLayout != null) {
                                i = R.id.exit;
                                Button button3 = (Button) ViewBindings.findChildViewById(view, i);
                                if (button3 != null) {
                                    i = R.id.name;
                                    TextView textView = (TextView) ViewBindings.findChildViewById(view, i);
                                    if (textView != null) {
                                        LinearLayout linearLayout2 = (LinearLayout) view;
                                        i = R.id.switch_boot_startup;
                                        Switch r14 = (Switch) ViewBindings.findChildViewById(view, i);
                                        if (r14 != null) {
                                            i = R.id.switch_channel_num;
                                            Switch r15 = (Switch) ViewBindings.findChildViewById(view, i);
                                            if (r15 != null) {
                                                i = R.id.switch_channel_reversal;
                                                Switch r16 = (Switch) ViewBindings.findChildViewById(view, i);
                                                if (r16 != null) {
                                                    i = R.id.switch_time;
                                                    Switch r17 = (Switch) ViewBindings.findChildViewById(view, i);
                                                    if (r17 != null) {
                                                        i = R.id.version;
                                                        TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i);
                                                        if (textView2 != null) {
                                                            i = R.id.version_name;
                                                            TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i);
                                                            if (textView3 != null) {
                                                                return new SettingBinding(linearLayout2, editText, editText2, button, button2, scrollView, linearLayout, button3, textView, linearLayout2, r14, r15, r16, r17, textView2, textView3);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
