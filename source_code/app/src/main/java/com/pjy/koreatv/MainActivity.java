package com.pjy.koreatv;

import android.content.Context;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.pjy.koreatv.models.TVList;
import com.pjy.koreatv.models.TVListModel;
import com.pjy.koreatv.models.TVModel;
import com.pjy.koreatv.requests.HttpClient;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
/* compiled from: MainActivity.kt */
@Metadata(d1 = {"\u0000®\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0013\u0018\u0000 U2\u00020\u0001:\u0002UVB\u0005¢\u0006\u0002\u0010\u0002J\b\u0010'\u001a\u00020(H\u0002J\b\u0010)\u001a\u00020(H\u0002J\b\u0010*\u001a\u00020(H\u0002J\b\u0010+\u001a\u00020,H\u0002J\u0010\u0010-\u001a\u00020(2\u0006\u0010.\u001a\u00020/H\u0002J\u0006\u00100\u001a\u00020(J\u0006\u00101\u001a\u00020(J\b\u00102\u001a\u00020(H\u0002J\u0006\u00103\u001a\u00020(J\u0006\u00104\u001a\u00020(J\u0010\u00105\u001a\u00020(2\u0006\u00106\u001a\u000207H\u0016J\u0012\u00108\u001a\u00020(2\b\u00109\u001a\u0004\u0018\u00010:H\u0014J\b\u0010;\u001a\u00020(H\u0014J\u000e\u0010<\u001a\u00020\t2\u0006\u0010=\u001a\u00020\u001aJ\u001a\u0010>\u001a\u00020\t2\u0006\u0010=\u001a\u00020\u001a2\b\u0010?\u001a\u0004\u0018\u00010@H\u0016J\u0006\u0010A\u001a\u00020(J\b\u0010B\u001a\u00020(H\u0014J\u0012\u0010C\u001a\u00020\t2\b\u0010?\u001a\u0004\u0018\u00010DH\u0016J\u000e\u0010E\u001a\u00020(2\u0006\u0010F\u001a\u00020\u001aJ\u0006\u0010G\u001a\u00020(J\u000e\u0010H\u001a\u00020(2\u0006\u0010I\u001a\u00020,J\u0006\u0010J\u001a\u00020(J\b\u0010K\u001a\u00020(H\u0002J\u0010\u0010L\u001a\u00020(2\u0006\u0010M\u001a\u00020,H\u0002J\u0010\u0010N\u001a\u00020(2\u0006\u0010.\u001a\u00020/H\u0002J\b\u0010O\u001a\u00020(H\u0002J\b\u0010P\u001a\u00020(H\u0002J\b\u0010Q\u001a\u00020(H\u0002J\u0006\u0010R\u001a\u00020(J\b\u0010S\u001a\u00020(H\u0002J\b\u0010T\u001a\u00020(H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u0004\u0018\u00010 X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010#\u001a\u0004\u0018\u00010$X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020&X\u0082.¢\u0006\u0002\n\u0000¨\u0006W"}, d2 = {"Lcom/pjy/koreatv/MainActivity;", "Landroidx/fragment/app/FragmentActivity;", "()V", "channelFragment", "Lcom/pjy/koreatv/ChannelFragment;", "delayHideMenu", "", "delayHideSetting", "doubleBackToExitPressedOnce", "", "errorFragment", "Lcom/pjy/koreatv/ErrorFragment;", "gestureDetector", "Landroid/view/GestureDetector;", "handler", "Landroid/os/Handler;", "hideMenu", "Ljava/lang/Runnable;", "hideSetting", "infoFragment", "Lcom/pjy/koreatv/InfoFragment;", "loadingFragment", "Lcom/pjy/koreatv/LoadingFragment;", "menuFragment", "Lcom/pjy/koreatv/MenuFragment;", "ok", "", "playerFragment", "Lcom/pjy/koreatv/PlayerFragment;", "settingFragment", "Lcom/pjy/koreatv/SettingFragment;", "task", "Ljava/util/TimerTask;", "timeFragment", "Lcom/pjy/koreatv/TimeFragment;", "timer", "Ljava/util/Timer;", "uri", "Landroid/net/Uri;", "back", "", "channelDown", "channelUp", "getAlertDialogText", "", "hideFragment", "fragment", "Landroidx/fragment/app/Fragment;", "hideInfoFragment", "hideMenuFragment", "hideSettingFragment", "menuActive", "next", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onKey", "keyCode", "onKeyDown", NotificationCompat.CATEGORY_EVENT, "Landroid/view/KeyEvent;", "onPlayEnd", "onStop", "onTouchEvent", "Landroid/view/MotionEvent;", "play", "position", "prev", "ready", "tag", "settingActive", "showAlertDialog", "showChannel", "channel", "showFragment", "showInfo", "showInfoDoubleClick", "showSetting", "showTime", "startAutoUpdateTimer", "watch", "Companion", "GestureListener", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class MainActivity extends FragmentActivity {
    public static final Companion Companion = new Companion(null);
    private static final String TAG = "MainActivity1";
    private final long delayHideMenu;
    private final long delayHideSetting;
    private boolean doubleBackToExitPressedOnce;
    private GestureDetector gestureDetector;
    private final Handler handler;
    private final Runnable hideMenu;
    private final Runnable hideSetting;
    private int ok;
    private TimerTask task;
    private Timer timer;
    private Uri uri;
    private PlayerFragment playerFragment = new PlayerFragment();
    private final ErrorFragment errorFragment = new ErrorFragment();
    private final LoadingFragment loadingFragment = new LoadingFragment();
    private InfoFragment infoFragment = new InfoFragment();
    private ChannelFragment channelFragment = new ChannelFragment();
    private TimeFragment timeFragment = new TimeFragment();
    private MenuFragment menuFragment = new MenuFragment();
    private SettingFragment settingFragment = new SettingFragment();
    
    // PATCH: 즐겨찾기 모드 추적 변수 추가
    private boolean isInFavoriteMode = false;

    public MainActivity() {
        Looper myLooper = Looper.myLooper();
        Intrinsics.checkNotNull(myLooper);
        this.handler = new Handler(myLooper);
        this.delayHideMenu = 60000L;
        this.delayHideSetting = 60000L;
        this.hideMenu = new Runnable() { // from class: com.pjy.koreatv.MainActivity$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.hideMenu$lambda$3(MainActivity.this);
            }
        };
        this.hideSetting = new Runnable() { // from class: com.pjy.koreatv.MainActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.hideSetting$lambda$4(MainActivity.this);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        WindowInsetsControllerCompat insetsController = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        Intrinsics.checkNotNullExpressionValue(insetsController, "getInsetsController(...)");
        if (Build.VERSION.SDK_INT >= 27) {
            insetsController.setAppearanceLightNavigationBars(true);
            insetsController.setAppearanceLightStatusBars(true);
            insetsController.hide(WindowInsetsCompat.Type.statusBars());
            insetsController.hide(WindowInsetsCompat.Type.navigationBars());
            insetsController.setSystemBarsBehavior(2);
        }
        getWindow().setStatusBarColor(0);
        getWindow().setNavigationBarColor(0);
        if (Build.VERSION.SDK_INT >= 28) {
            getWindow().setNavigationBarDividerColor(0);
        }
        if (Build.VERSION.SDK_INT >= 28) {
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.layoutInDisplayCutoutMode = 1;
            getWindow().setAttributes(attributes);
        }
        getWindow().getDecorView().setSystemUiVisibility(3846);
        getWindow().addFlags(128);
        setContentView(R.layout.activity_main);
        if (bundle == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.main_browse_fragment, this.playerFragment).add(R.id.main_browse_fragment, this.errorFragment).add(R.id.main_browse_fragment, this.loadingFragment).add(R.id.main_browse_fragment, this.timeFragment).add(R.id.main_browse_fragment, this.infoFragment).add(R.id.main_browse_fragment, this.channelFragment).add(R.id.main_browse_fragment, this.menuFragment).add(R.id.main_browse_fragment, this.settingFragment).hide(this.menuFragment).hide(this.settingFragment).hide(this.errorFragment).hide(this.loadingFragment).hide(this.timeFragment).commitNow();
        }
        MainActivity mainActivity = this;
        this.gestureDetector = new GestureDetector(mainActivity, new GestureListener(this, mainActivity));
        showTime();
        startAutoUpdateTimer();
        showAlertDialog();
    }

    private final void showAlertDialog() {
        BuildersKt__Builders_commonKt.launch$default(GlobalScope.INSTANCE, Dispatchers.getMain(), null, new MainActivity$showAlertDialog$1(this, null), 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String getAlertDialogText() {
        String str = Intrinsics.areEqual(Locale.getDefault().getLanguage(), "zh") ? "https://kenpark76.github.io/message/message_zh.txt" : "https://kenpark76.github.io/message/message.txt";
        if (Intrinsics.areEqual(Locale.getDefault().getLanguage(), "ko")) {
            str = "https://kenpark76.github.io/message/message_ko.txt";
        }
        try {
            Response execute = HttpClient.INSTANCE.getOkHttpClient().newCall(new Request.Builder().url(str).build()).execute();
            if (execute.isSuccessful()) {
                ResponseBody body = execute.body();
                Intrinsics.checkNotNull(body);
                String string = body.string();
                Intrinsics.checkNotNull(string);
                return string;
            }
            Log.i(TAG, "showAlertDialog: No Dialog");
            return "";
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return "";
        }
    }

    private final void startAutoUpdateTimer() {
        this.timer = new Timer();
        TimerTask timerTask = new TimerTask() { // from class: com.pjy.koreatv.MainActivity$startAutoUpdateTimer$1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                Uri uri;
                TVList.INSTANCE.setFirstBoot(false);
                TVList.INSTANCE.setFirstRun(false);
                TVList.INSTANCE.setChannelUpdateSuccess(false);
                MainActivity mainActivity = MainActivity.this;
                Uri parse = Uri.parse("https://kenpark76.github.io/koreatv.json");
                Intrinsics.checkNotNullExpressionValue(parse, "parse(...)");
                mainActivity.uri = parse;
                TVList tVList = TVList.INSTANCE;
                uri = MainActivity.this.uri;
                if (uri == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("uri");
                    uri = null;
                }
                tVList.parseUri(uri);
            }
        };
        this.task = timerTask;
        Timer timer = this.timer;
        if (timer != null) {
            timer.schedule(timerTask, 3600000L, 3600000L);
        }
    }

    public final void ready(String tag) {
        int position;
        Intrinsics.checkNotNullParameter(tag, "tag");
        int i = this.ok + 1;
        this.ok = i;
        if (i == 3) {
            TVList.INSTANCE.getGroupModel().getChange().observe(this, new MainActivity$sam$androidx_lifecycle_Observer$0(new Function1<Boolean, Unit>() { // from class: com.pjy.koreatv.MainActivity$ready$1
                /* JADX INFO: Access modifiers changed from: package-private */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                    invoke2(bool);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: avoid collision after fix types in other method */
                public final void invoke2(Boolean bool) {
                    int i2;
                    if (TVList.INSTANCE.getGroupModel().getTvGroupModel().getValue() != null) {
                        MainActivity.this.watch();
                        i2 = MainActivity.this.ok;
                        if (i2 == 3) {
                            MainActivity.this.menuFragment.update();
                            MainActivity.this.ok = 0;
                        }
                    }
                }
            }));
            if (SP.INSTANCE.getChannel() > 0) {
                if (SP.INSTANCE.getChannel() < TVList.INSTANCE.getListModel().size()) {
                    position = SP.INSTANCE.getChannel() - 1;
                } else {
                    SP.INSTANCE.setChannel(0);
                    position = 0;
                }
            } else {
                position = SP.INSTANCE.getPosition();
            }
            if (!TVList.INSTANCE.setPosition(position)) {
                TVList.INSTANCE.setPosition(0);
                return;
            }
            TVList.INSTANCE.setVideoUrlBeforeUpdate(String.valueOf(TVList.INSTANCE.getTVModel(position).m490getVideoUrl().getValue()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void watch() {
        for (final TVModel tVModel : TVList.INSTANCE.getListModel()) {
            MainActivity mainActivity = this;
            tVModel.getErrInfo().observe(mainActivity, new MainActivity$sam$androidx_lifecycle_Observer$0(new Function1<String, Unit>() { // from class: com.pjy.koreatv.MainActivity$watch$1$1
                /* JADX INFO: Access modifiers changed from: package-private */
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(String str) {
                    invoke2(str);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: avoid collision after fix types in other method */
                public final void invoke2(String str) {
                    LoadingFragment loadingFragment;
                    PlayerFragment playerFragment;
                    ErrorFragment errorFragment;
                    ErrorFragment errorFragment2;
                    ErrorFragment errorFragment3;
                    PlayerFragment playerFragment2;
                    if (TVModel.this.getErrInfo().getValue() != null) {
                        int id = TVModel.this.getTv().getId();
                        Integer value = TVList.INSTANCE.getPosition().getValue();
                        if (value != null && id == value.intValue()) {
                            MainActivity mainActivity2 = this;
                            loadingFragment = mainActivity2.loadingFragment;
                            mainActivity2.hideFragment(loadingFragment);
                            if (Intrinsics.areEqual(TVModel.this.getErrInfo().getValue(), "")) {
                                MainActivity mainActivity3 = this;
                                errorFragment3 = mainActivity3.errorFragment;
                                mainActivity3.hideFragment(errorFragment3);
                                MainActivity mainActivity4 = this;
                                playerFragment2 = mainActivity4.playerFragment;
                                mainActivity4.showFragment(playerFragment2);
                                return;
                            }
                            MainActivity mainActivity5 = this;
                            playerFragment = mainActivity5.playerFragment;
                            mainActivity5.hideFragment(playerFragment);
                            errorFragment = this.errorFragment;
                            errorFragment.setMsg(String.valueOf(TVModel.this.getErrInfo().getValue()));
                            MainActivity mainActivity6 = this;
                            errorFragment2 = mainActivity6.errorFragment;
                            mainActivity6.showFragment(errorFragment2);
                        }
                    }
                }
            }));
            tVModel.getReady().observe(mainActivity, new MainActivity$sam$androidx_lifecycle_Observer$0(new Function1<Boolean, Unit>() { // from class: com.pjy.koreatv.MainActivity$watch$1$2
                /* JADX INFO: Access modifiers changed from: package-private */
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                    invoke2(bool);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: avoid collision after fix types in other method */
                public final void invoke2(Boolean bool) {
                    ErrorFragment errorFragment;
                    LoadingFragment loadingFragment;
                    LoadingFragment loadingFragment2;
                    ErrorFragment errorFragment2;
                    ErrorFragment errorFragment3;
                    ChannelFragment channelFragment;
                    PlayerFragment playerFragment;
                    if (TVModel.this.getReady().getValue() != null) {
                        int id = TVModel.this.getTv().getId();
                        Integer value = TVList.INSTANCE.getPosition().getValue();
                        if (value != null && id == value.intValue()) {
                            Log.i("MainActivity1", "loading " + TVModel.this.getTv().getTitle());
                            TVList.INSTANCE.setVideoUrlBeforeUpdate(String.valueOf(TVModel.this.m490getVideoUrl().getValue()));
                            MainActivity mainActivity2 = this;
                            errorFragment = mainActivity2.errorFragment;
                            mainActivity2.hideFragment(errorFragment);
                            MainActivity mainActivity3 = this;
                            loadingFragment = mainActivity3.loadingFragment;
                            mainActivity3.showFragment(loadingFragment);
                            try {
                                playerFragment = this.playerFragment;
                                playerFragment.play(TVModel.this);
                            } catch (Exception e) {
                                e.printStackTrace();
                                MainActivity mainActivity4 = this;
                                loadingFragment2 = mainActivity4.loadingFragment;
                                mainActivity4.hideFragment(loadingFragment2);
                                errorFragment2 = this.errorFragment;
                                errorFragment2.setMsg(this.getString(R.string.play_error) + ':' + e);
                                MainActivity mainActivity5 = this;
                                errorFragment3 = mainActivity5.errorFragment;
                                mainActivity5.showFragment(errorFragment3);
                            }
                            this.showInfo();
                            if (SP.INSTANCE.getChannelNum()) {
                                channelFragment = this.channelFragment;
                                channelFragment.show(TVModel.this);
                            }
                        }
                    }
                }
            }));
            tVModel.getLike().observe(mainActivity, new MainActivity$sam$androidx_lifecycle_Observer$0(new Function1<Boolean, Unit>() { // from class: com.pjy.koreatv.MainActivity$watch$1$3
                /* JADX INFO: Access modifiers changed from: package-private */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                    invoke2(bool);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: avoid collision after fix types in other method */
                public final void invoke2(Boolean bool) {
                    if (TVModel.this.getLike().getValue() == null || TVModel.this.getTv().getId() == -1) {
                        return;
                    }
                    Boolean value = TVModel.this.getLike().getValue();
                    Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Boolean");
                    boolean booleanValue = value.booleanValue();
                    if (booleanValue) {
                        TVListModel tVListModel = TVList.INSTANCE.getGroupModel().getTVListModel(0);
                        if (tVListModel != null) {
                            tVListModel.replaceTVModel(TVModel.this);
                        }
                    } else {
                        TVListModel tVListModel2 = TVList.INSTANCE.getGroupModel().getTVListModel(0);
                        if (tVListModel2 != null) {
                            tVListModel2.removeTVModel(TVModel.this.getTv().getId());
                        }
                    }
                    SP.INSTANCE.setLike(TVModel.this.getTv().getName(), booleanValue);
                }
            }));
        }
    }

    @Override // android.app.Activity
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent != null) {
            GestureDetector gestureDetector = this.gestureDetector;
            if (gestureDetector == null) {
                Intrinsics.throwUninitializedPropertyAccessException("gestureDetector");
                gestureDetector = null;
            }
            gestureDetector.onTouchEvent(motionEvent);
        }
        return super.onTouchEvent(motionEvent);
    }

    /* compiled from: MainActivity.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J*\u0010\u000f\u001a\u00020\f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\nH\u0016J\u0010\u0010\u0014\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u0015\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lcom/pjy/koreatv/MainActivity$GestureListener;", "Landroid/view/GestureDetector$SimpleOnGestureListener;", "context", "Landroid/content/Context;", "(Lcom/pjy/koreatv/MainActivity;Landroid/content/Context;)V", "audioManager", "Landroid/media/AudioManager;", "adjustVolume", "", "deltaY", "", "onDoubleTap", "", "e", "Landroid/view/MotionEvent;", "onFling", "e1", "e2", "velocityX", "velocityY", "onLongPress", "onSingleTapConfirmed", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private final AudioManager audioManager;
        private final Context context;
        final /* synthetic */ MainActivity this$0;

        public GestureListener(MainActivity mainActivity, Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            this.this$0 = mainActivity;
            this.context = context;
            Object systemService = context.getSystemService("audio");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.media.AudioManager");
            this.audioManager = (AudioManager) systemService;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Intrinsics.checkNotNullParameter(e, "e");
            if (TVList.INSTANCE.getEpgReceived()) {
                MainActivity mainActivity = this.this$0;
                mainActivity.showFragment(mainActivity.menuFragment);
                return true;
            }
            Toast.makeText(this.context, this.this$0.getString(R.string.updating_please_wait), 0).show();
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTap(MotionEvent e) {
            Intrinsics.checkNotNullParameter(e, "e");
            this.this$0.showInfoDoubleClick();
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent e) {
            Intrinsics.checkNotNullParameter(e, "e");
            this.this$0.showSetting();
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent motionEvent, MotionEvent e2, float f, float f2) {
            Intrinsics.checkNotNullParameter(e2, "e2");
            if ((motionEvent != null ? motionEvent.getX() : 0.0f) > this.this$0.getWindowManager().getDefaultDisplay().getWidth() / 3) {
                if ((motionEvent != null ? motionEvent.getX() : 0.0f) < (this.this$0.getWindowManager().getDefaultDisplay().getWidth() * 2) / 3) {
                    if (f2 > 0.0f && this.this$0.menuFragment.isHidden() && this.this$0.settingFragment.isHidden()) {
                        this.this$0.prev();
                    }
                    if (f2 < 0.0f && this.this$0.menuFragment.isHidden() && this.this$0.settingFragment.isHidden()) {
                        this.this$0.next();
                    }
                }
            }
            return super.onFling(motionEvent, e2, f, f2);
        }

        private final void adjustVolume(float f) {
            int streamMaxVolume = this.audioManager.getStreamMaxVolume(3);
            float f2 = streamMaxVolume;
            float streamVolume = this.audioManager.getStreamVolume(3) + (((f / 1000) * f2) / this.this$0.getWindowManager().getDefaultDisplay().getHeight());
            if (streamVolume < 0.0f) {
                f2 = 0.0f;
            } else if (streamVolume <= f2) {
                f2 = streamVolume;
            }
            this.audioManager.setStreamVolume(3, (int) f2, 0);
            Toast.makeText(this.context, "Volume: " + f2 + " / " + streamMaxVolume, 0).show();
        }
    }

    public final void onPlayEnd() {
        TVModel tVModel = TVList.INSTANCE.getTVModel();
        if (SP.INSTANCE.getRepeatInfo()) {
            showInfo();
            if (SP.INSTANCE.getChannelNum()) {
                this.channelFragment.show(tVModel);
            }
        }
    }

    public final void play(int i) {
        int groupIndex = TVList.INSTANCE.getTVModel().getGroupIndex();
        if (i > -1 && i < TVList.INSTANCE.size()) {
            TVList.INSTANCE.setPosition(i);
            int groupIndex2 = TVList.INSTANCE.getTVModel().getGroupIndex();
            if (groupIndex2 != groupIndex) {
                this.menuFragment.updateList(groupIndex2);
                return;
            }
            return;
        }
        Toast.makeText(this, getString(R.string.channel_request_error), 1).show();
    }

    public final void prev() {
        int groupIndex = TVList.INSTANCE.getTVModel().getGroupIndex();
        
        // PATCH: 즐겨찾기 모드일 때는 즐겨찾기 채널 내에서만 이동
        if (groupIndex == 0) {  // 0 = 즐겨찾기 카테고리
            isInFavoriteMode = true;
            TVListModel favoriteList = TVList.INSTANCE.getGroupModel().getTVListModel(0);
            if (favoriteList != null && favoriteList.size() > 0) {
                int currentPos = findCurrentPositionInFavorites();
                int newPos = currentPos - 1;
                if (newPos < 0) {
                    newPos = favoriteList.size() - 1;
                }
                TVList.INSTANCE.setPosition(favoriteList.getTv(newPos).getId());
                return;
            }
        } else {
            isInFavoriteMode = false;
        }
        
        // 기존 로직 (전체 채널 이동)
        Integer value = TVList.INSTANCE.getPosition().getValue();
        int intValue = value != null ? value.intValue() - 1 : 0;
        if (intValue == -1) {
            intValue = TVList.INSTANCE.size() - 1;
        }
        TVList.INSTANCE.setPosition(intValue);
        int groupIndex2 = TVList.INSTANCE.getTVModel().getGroupIndex();
        if (groupIndex2 != groupIndex) {
            this.menuFragment.updateList(groupIndex2);
        }
    }

    public final void next() {
        int groupIndex = TVList.INSTANCE.getTVModel().getGroupIndex();
        
        // PATCH: 즐겨찾기 모드일 때는 즐겨찾기 채널 내에서만 이동
        if (groupIndex == 0) {  // 0 = 즐겨찾기 카테고리
            isInFavoriteMode = true;
            TVListModel favoriteList = TVList.INSTANCE.getGroupModel().getTVListModel(0);
            if (favoriteList != null && favoriteList.size() > 0) {
                int currentPos = findCurrentPositionInFavorites();
                int newPos = currentPos + 1;
                if (newPos >= favoriteList.size()) {
                    newPos = 0;
                }
                TVList.INSTANCE.setPosition(favoriteList.getTv(newPos).getId());
                return;
            }
        } else {
            isInFavoriteMode = false;
        }
        
        // 기존 로직 (전체 채널 이동)
        Integer value = TVList.INSTANCE.getPosition().getValue();
        int intValue = value != null ? value.intValue() + 1 : 0;
        TVList.INSTANCE.setPosition(intValue != TVList.INSTANCE.size() ? intValue : 0);
        int groupIndex2 = TVList.INSTANCE.getTVModel().getGroupIndex();
        if (groupIndex2 != groupIndex) {
            this.menuFragment.updateList(groupIndex2);
        }
    }

    // PATCH: 즐겨찾기 내 위치 찾기 헬퍼 메서드 추가
    private int findCurrentPositionInFavorites() {
        TVListModel favoriteList = TVList.INSTANCE.getGroupModel().getTVListModel(0);
        if (favoriteList == null) return 0;
        
        int currentId = TVList.INSTANCE.getTVModel().getTv().getId();
        for (int i = 0; i < favoriteList.size(); i++) {
            if (favoriteList.getTv(i).getId() == currentId) {
                return i;
            }
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showFragment(Fragment fragment) {
        if (fragment.isHidden()) {
            getSupportFragmentManager().beginTransaction().show(fragment).commitNow();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void hideFragment(Fragment fragment) {
        if (fragment.isHidden()) {
            return;
        }
        getSupportFragmentManager().beginTransaction().hide(fragment).commitNow();
    }

    public final void menuActive() {
        this.handler.removeCallbacks(this.hideMenu);
        this.handler.postDelayed(this.hideMenu, this.delayHideMenu);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void hideMenu$lambda$3(MainActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.isFinishing() || this$0.getSupportFragmentManager().isStateSaved() || this$0.menuFragment.isHidden()) {
            return;
        }
        this$0.getSupportFragmentManager().beginTransaction().hide(this$0.menuFragment).commit();
    }

    public final void settingActive() {
        this.handler.removeCallbacks(this.hideSetting);
        this.handler.postDelayed(this.hideSetting, this.delayHideSetting);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void hideSetting$lambda$4(MainActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.settingFragment.isHidden()) {
            return;
        }
        this$0.getSupportFragmentManager().beginTransaction().hide(this$0.settingFragment).commitNow();
        this$0.showTime();
    }

    public final void showTime() {
        if (SP.INSTANCE.getTime()) {
            showFragment(this.timeFragment);
        } else {
            hideFragment(this.timeFragment);
        }
    }

    private final void showChannel(String str) {
        if (this.menuFragment.isHidden() && !this.settingFragment.isVisible()) {
            this.channelFragment.show(str);
        }
    }

    private final void channelUp() {
        if (this.menuFragment.isHidden() && this.settingFragment.isHidden()) {
            if (SP.INSTANCE.getChannelReversal()) {
                next();
            } else {
                prev();
            }
        }
    }

    private final void channelDown() {
        if (this.menuFragment.isHidden() && this.settingFragment.isHidden()) {
            if (SP.INSTANCE.getChannelReversal()) {
                prev();
            } else {
                next();
            }
        }
    }

    private final void back() {
        if (!this.menuFragment.isHidden()) {
            hideMenuFragment();
        } else if (!this.settingFragment.isHidden()) {
            hideSettingFragment();
        } else if (!this.infoFragment.isHidden()) {
            hideInfoFragment();
        } else if (this.doubleBackToExitPressedOnce) {
            super.onBackPressed();
            Process.killProcess(Process.myPid());
        } else {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getString(R.string.press_again_to_exit), 0).show();
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.pjy.koreatv.MainActivity$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    MainActivity.back$lambda$5(MainActivity.this);
                }
            }, 2000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void back$lambda$5(MainActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.doubleBackToExitPressedOnce = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showSetting() {
        if (this.menuFragment.isHidden() && this.settingFragment.isHidden()) {
            getSupportFragmentManager().beginTransaction().show(this.settingFragment).commit();
            settingActive();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showInfoDoubleClick() {
        try {
            if (!this.infoFragment.isVisible() && TVList.INSTANCE.getEpgReceived()) {
                this.infoFragment.show(TVList.INSTANCE.getTVModel());
                getSupportFragmentManager().beginTransaction().show(this.infoFragment).commit();
                return;
            }
            getSupportFragmentManager().beginTransaction().hide(this.infoFragment).commitNow();
        } catch (Exception e) {
            Log.i(TAG, "showInfo error " + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showInfo() {
        try {
            if (TVList.INSTANCE.getEpgReceived()) {
                this.infoFragment.show(TVList.INSTANCE.getTVModel());
                getSupportFragmentManager().beginTransaction().show(this.infoFragment).commit();
                return;
            }
            getSupportFragmentManager().beginTransaction().hide(this.infoFragment).commitNow();
        } catch (Exception e) {
            Log.i(TAG, "showInfo error " + e);
        }
    }

    public final void hideMenuFragment() {
        getSupportFragmentManager().beginTransaction().hide(this.menuFragment).commit();
    }

    public final void hideInfoFragment() {
        getSupportFragmentManager().beginTransaction().hide(this.infoFragment).commit();
    }

    private final void hideSettingFragment() {
        getSupportFragmentManager().beginTransaction().hide(this.settingFragment).commit();
        showTime();
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean onKey(int r4) {
        /*
            r3 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "keyCode "
            r0.<init>(r1)
            r0.append(r4)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "MainActivity1"
            android.util.Log.d(r1, r0)
            r0 = 1
            if (r4 == 0) goto Lc2
            r1 = 4
            if (r4 == r1) goto Lbe
            r1 = 66
            if (r4 == r1) goto Lc2
            r1 = 82
            if (r4 == r1) goto Lba
            r1 = 111(0x6f, float:1.56E-43)
            if (r4 == r1) goto Lbe
            r1 = 174(0xae, float:2.44E-43)
            if (r4 == r1) goto Lba
            r1 = 176(0xb0, float:2.47E-43)
            if (r4 == r1) goto Lba
            r1 = 259(0x103, float:3.63E-43)
            if (r4 == r1) goto Lba
            r1 = 92
            r2 = 0
            if (r4 == r1) goto Lb6
            r1 = 93
            if (r4 == r1) goto Lb2
            switch(r4) {
                case 7: goto Lac;
                case 8: goto La6;
                case 9: goto La0;
                case 10: goto L9a;
                case 11: goto L94;
                case 12: goto L8e;
                case 13: goto L88;
                case 14: goto L82;
                case 15: goto L7c;
                case 16: goto L76;
                default: goto L3d;
            }
        L3d:
            switch(r4) {
                case 19: goto Lb6;
                case 20: goto Lb2;
                case 21: goto L4b;
                case 22: goto L47;
                case 23: goto Lc2;
                default: goto L40;
            }
        L40:
            switch(r4) {
                case 165: goto Lc2;
                case 166: goto Lb6;
                case 167: goto Lb2;
                default: goto L43;
            }
        L43:
            switch(r4) {
                case 280: goto Lb6;
                case 281: goto Lb2;
                case 282: goto L4b;
                case 283: goto L47;
                default: goto L46;
            }
        L46:
            goto Lb9
        L47:
            r3.showSetting()
            goto Lb9
        L4b:
            com.pjy.koreatv.SettingFragment r4 = r3.settingFragment
            boolean r4 = r4.isHidden()
            if (r4 == 0) goto Lb9
            // PATCH: 즐겨찾기 모드일 때는 즐겨찾기 카테고리로 이동
            if (r3.isInFavoriteMode) {
                com.pjy.koreatv.models.TVList.INSTANCE.getGroupModel().setPosition(0);
            }
            com.pjy.koreatv.models.TVList r4 = com.pjy.koreatv.models.TVList.INSTANCE
            boolean r4 = r4.getEpgReceived()
            if (r4 == 0) goto L63
            com.pjy.koreatv.MenuFragment r4 = r3.menuFragment
            androidx.fragment.app.Fragment r4 = (androidx.fragment.app.Fragment) r4
            r3.showFragment(r4)
            goto Lb9
        L63:
            r4 = r3
            android.content.Context r4 = (android.content.Context) r4
            int r0 = com.pjy.koreatv.R.string.updating_please_wait
            java.lang.String r0 = r3.getString(r0)
            java.lang.CharSequence r0 = (java.lang.CharSequence) r0
            android.widget.Toast r4 = android.widget.Toast.makeText(r4, r0, r2)
            r4.show()
            goto Lb9
        L76:
            java.lang.String r4 = "9"
            r3.showChannel(r4)
            return r0
        L7c:
            java.lang.String r4 = "8"
            r3.showChannel(r4)
            return r0
        L82:
            java.lang.String r4 = "7"
            r3.showChannel(r4)
            return r0
        L88:
            java.lang.String r4 = "6"
            r3.showChannel(r4)
            return r0
        L8e:
            java.lang.String r4 = "5"
            r3.showChannel(r4)
            return r0
        L94:
            java.lang.String r4 = "4"
            r3.showChannel(r4)
            return r0
        L9a:
            java.lang.String r4 = "3"
            r3.showChannel(r4)
            return r0
        La0:
            java.lang.String r4 = "2"
            r3.showChannel(r4)
            return r0
        La6:
            java.lang.String r4 = "1"
            r3.showChannel(r4)
            return r0
        Lac:
            java.lang.String r4 = "0"
            r3.showChannel(r4)
            return r0
        Lb2:
            r3.channelDown()
            goto Lb9
        Lb6:
            r3.channelUp()
        Lb9:
            return r2
        Lba:
            r3.showSetting()
            return r0
        Lbe:
            r3.back()
            return r0
        Lc2:
            com.pjy.koreatv.models.TVList r4 = com.pjy.koreatv.models.TVList.INSTANCE
            r4.getTVModel()
            r3.showInfoDoubleClick()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.pjy.koreatv.MainActivity.onKey(int):boolean");
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (onKey(i)) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        Process.killProcess(Process.myPid());
    }

    /* compiled from: MainActivity.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/pjy/koreatv/MainActivity$Companion;", "", "()V", "TAG", "", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
    }
}
