package com.tvhome.app.models;

import android.content.Context;
import android.net.Uri;
import android.os.Process;
import android.util.Log;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.net.UriKt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tvhome.app.ExtKt;
import com.tvhome.app.MyTVApplication;
import com.tvhome.app.R;
import com.tvhome.app.SP;
import io.github.lizongying.Gua;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
/* compiled from: TVList.kt */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u00106\u001a\u00020'J\u000e\u00106\u001a\u00020'2\u0006\u00107\u001a\u00020\u0006J\u000e\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020;J\u000e\u0010<\u001a\u0002092\u0006\u0010=\u001a\u00020>J\u000e\u0010?\u001a\u00020\r2\u0006\u0010,\u001a\u00020\u0006J\u0006\u0010@\u001a\u00020\u0006J\u000e\u0010A\u001a\u00020\r2\u0006\u0010B\u001a\u00020\u0004J\b\u0010C\u001a\u000209H\u0002J\u000e\u0010C\u001a\u0002092\u0006\u00100\u001a\u00020\u0004J\u0011\u0010D\u001a\u000209H\u0082@ø\u0001\u0000¢\u0006\u0002\u0010ER\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u000f\"\u0004\b\u0015\u0010\u0011R\u001a\u0010\u0016\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u000f\"\u0004\b\u0018\u0010\u0011R\u001a\u0010\u0019\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u000f\"\u0004\b\u001b\u0010\u0011R\u001a\u0010\u001c\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u000f\"\u0004\b\u001e\u0010\u0011R\u0011\u0010\u001f\u001a\u00020 ¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0014\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$X\u0082.¢\u0006\u0002\n\u0000R \u0010&\u001a\b\u0012\u0004\u0012\u00020'0$X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u0017\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00060-8F¢\u0006\u0006\u001a\u0004\b.\u0010/R\u000e\u00100\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u001a\u00101\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u00103\"\u0004\b4\u00105\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006F"}, d2 = {"Lcom/pjy/koreatv/models/TVList;", "", "()V", "FILE_NAME", "", "MAX_RETRY", "", "TAG", "_position", "Landroidx/lifecycle/MutableLiveData;", "appDirectory", "Ljava/io/File;", "channelUpdateSuccess", "", "getChannelUpdateSuccess", "()Z", "setChannelUpdateSuccess", "(Z)V", "epg", "epgReceived", "getEpgReceived", "setEpgReceived", "epgReceivedSucces", "getEpgReceivedSucces", "setEpgReceivedSucces", "firstBoot", "getFirstBoot", "setFirstBoot", "firstRun", "getFirstRun", "setFirstRun", "groupModel", "Lcom/pjy/koreatv/models/TVGroupModel;", "getGroupModel", "()Lcom/pjy/koreatv/models/TVGroupModel;", "list", "", "Lcom/pjy/koreatv/models/TV;", "listModel", "Lcom/pjy/koreatv/models/TVModel;", "getListModel", "()Ljava/util/List;", "setListModel", "(Ljava/util/List;)V", "position", "Landroidx/lifecycle/LiveData;", "getPosition", "()Landroidx/lifecycle/LiveData;", "serverUrl", "videoUrlBeforeUpdate", "getVideoUrlBeforeUpdate", "()Ljava/lang/String;", "setVideoUrlBeforeUpdate", "(Ljava/lang/String;)V", "getTVModel", "idx", "init", "", "context", "Landroid/content/Context;", "parseUri", "uri", "Landroid/net/Uri;", "setPosition", "size", "str2List", "str", "update", "updateEPG", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class TVList {
    public static final String FILE_NAME = "channels.txt";
    private static final String TAG = "TVList";
    private static File appDirectory;
    private static boolean channelUpdateSuccess;
    private static boolean epgReceived;
    private static boolean epgReceivedSucces;
    private static boolean firstBoot;
    private static List<TV> list;
    private static String serverUrl;
    public static final TVList INSTANCE = new TVList();
    private static List<TVModel> listModel = CollectionsKt.emptyList();
    private static final TVGroupModel groupModel = new TVGroupModel();
    private static String epg = SP.INSTANCE.getEpg();
    private static boolean firstRun = true;
    private static String videoUrlBeforeUpdate = "";
    private static final MutableLiveData<Integer> _position = new MutableLiveData<>();
    private static int MAX_RETRY = 5;

    private TVList() {
    }

    public final List<TVModel> getListModel() {
        return listModel;
    }

    public final void setListModel(List<TVModel> list2) {
        Intrinsics.checkNotNullParameter(list2, "<set-?>");
        listModel = list2;
    }

    public final TVGroupModel getGroupModel() {
        return groupModel;
    }

    public final boolean getFirstBoot() {
        return firstBoot;
    }

    public final void setFirstBoot(boolean z) {
        firstBoot = z;
    }

    public final boolean getFirstRun() {
        return firstRun;
    }

    public final void setFirstRun(boolean z) {
        firstRun = z;
    }

    public final boolean getEpgReceived() {
        return epgReceived;
    }

    public final void setEpgReceived(boolean z) {
        epgReceived = z;
    }

    public final boolean getEpgReceivedSucces() {
        return epgReceivedSucces;
    }

    public final void setEpgReceivedSucces(boolean z) {
        epgReceivedSucces = z;
    }

    public final boolean getChannelUpdateSuccess() {
        return channelUpdateSuccess;
    }

    public final void setChannelUpdateSuccess(boolean z) {
        channelUpdateSuccess = z;
    }

    public final String getVideoUrlBeforeUpdate() {
        return videoUrlBeforeUpdate;
    }

    public final void setVideoUrlBeforeUpdate(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        videoUrlBeforeUpdate = str;
    }

    public final LiveData<Integer> getPosition() {
        return _position;
    }

    public final void init(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        boolean z = false;
        _position.setValue(0);
        TVGroupModel tVGroupModel = groupModel;
        String string = ContextCompat.getString(context, R.string.my_favorite);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        tVGroupModel.addTVListModel(new TVListModel(string, 0));
        String string2 = ContextCompat.getString(context, R.string.all_channels);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
        tVGroupModel.addTVListModel(new TVListModel(string2, 1));
        File filesDir = context.getFilesDir();
        Intrinsics.checkNotNullExpressionValue(filesDir, "getFilesDir(...)");
        appDirectory = filesDir;
        File file = appDirectory;
        if (file == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appDirectory");
            file = null;
        }
        File file2 = new File(file, FILE_NAME);
        if (!file2.exists()) {
            firstBoot = true;
            return;
        }
        try {
            str2List(FilesKt.readText$default(file2, null, 1, null));
        } catch (Exception e) {
            Log.e("", "error " + e);
            file2.deleteOnExit();
            String string3 = ContextCompat.getString(MyTVApplication.Companion.getInstance(), R.string.channel_request_error);
            Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
            ExtKt.showToast$default(string3, 0, 1, null);
        }
        SP.INSTANCE.setConfig("https://gitee.com/kenpark76/kenpark76/raw/master/koreatv.json#https://kenpark76.github.io/koreatv.json#https://raw.githubusercontent.com/kenpark76/kenpark76.github.io/refs/heads/main/koreatv.json");
        if (SP.INSTANCE.getConfigAutoLoad()) {
            String config = SP.INSTANCE.getConfig();
            if (config != null) {
                INSTANCE.update(config);
                return;
            }
            return;
        }
        String str = epg;
        if ((str == null || str.length() == 0) ? true : true) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new TVList$init$2(null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0028  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00df A[Catch: Exception -> 0x003b, TryCatch #0 {Exception -> 0x003b, blocks: (B:12:0x0036, B:29:0x00db, B:31:0x00df, B:32:0x00f5, B:24:0x007b, B:26:0x00a6, B:33:0x00f9, B:35:0x00fd, B:37:0x0107), top: B:46:0x0036 }] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:27:0x00d8 -> B:29:0x00db). Please submit an issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object updateEPG(kotlin.coroutines.Continuation<? super kotlin.Unit> r14) {
        /*
            Method dump skipped, instructions count: 328
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.pjy.koreatv.models.TVList.updateEPG(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void update() {
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new TVList$update$1(null), 3, null);
    }

    public final void update(String serverUrl2) {
        Intrinsics.checkNotNullParameter(serverUrl2, "serverUrl");
        serverUrl = serverUrl2;
        update();
    }

    public final void parseUri(Uri uri) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        if (Intrinsics.areEqual(uri.getScheme(), "file")) {
            File file = UriKt.toFile(uri);
            if (!file.exists()) {
                String string = ContextCompat.getString(MyTVApplication.Companion.getInstance(), R.string.channel_request_error);
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                ExtKt.showToast$default(string, 0, 1, null);
                return;
            }
            try {
                if (str2List(FilesKt.readText$default(file, null, 1, null))) {
                    SP.INSTANCE.setConfig(uri.toString());
                    String string2 = ContextCompat.getString(MyTVApplication.Companion.getInstance(), R.string.channel_request_error);
                    Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                    ExtKt.showToast$default(string2, 0, 1, null);
                    if (firstBoot) {
                        Process.killProcess(Process.myPid());
                    }
                } else {
                    String string3 = ContextCompat.getString(MyTVApplication.Companion.getInstance(), R.string.channel_request_error);
                    Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
                    ExtKt.showToast$default(string3, 0, 1, null);
                }
                return;
            } catch (Exception e) {
                Log.e("", "error " + e);
                file.deleteOnExit();
                String string4 = ContextCompat.getString(MyTVApplication.Companion.getInstance(), R.string.channel_request_error);
                Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
                ExtKt.showToast$default(string4, 0, 1, null);
                return;
            }
        }
        String uri2 = uri.toString();
        Intrinsics.checkNotNullExpressionValue(uri2, "toString(...)");
        update(uri2);
    }

    public final boolean str2List(String str) {
        List<String> groupValues;
        String str2;
        List<String> groupValues2;
        String str3;
        List<String> groupValues3;
        String str4;
        List<String> groupValues4;
        String str5;
        String str6 = str;
        Intrinsics.checkNotNullParameter(str6, "str");
        Gua gua = new Gua();
        if (gua.verify(str6)) {
            str6 = gua.decode(str6);
            Intrinsics.checkNotNullExpressionValue(str6, "decode(...)");
        }
        String str7 = str6;
        String str8 = str7;
        if (StringsKt.isBlank(str8)) {
            return false;
        }
        char charAt = str7.charAt(0);
        int i = 2;
        List<TV> list2 = null;
        int i2 = 1;
        if (charAt == '[') {
            try {
                Object fromJson = new Gson().fromJson(str7, new TypeToken<List<? extends TV>>() { // from class: com.pjy.koreatv.models.TVList$str2List$type$1
                }.getType());
                Intrinsics.checkNotNullExpressionValue(fromJson, "fromJson(...)");
                list = (List) fromJson;
            } catch (Exception e) {
                Log.i(TAG, "parse error " + str7);
                Log.i(TAG, e.getMessage(), e);
                return false;
            }
        } else {
            String str9 = "";
            if (charAt == '#') {
                List<String> lines = StringsKt.lines(str8);
                Regex regex = new Regex("tvg-name=\"([^\"]+)\"");
                Regex regex2 = new Regex("tvg-logo=\"([^\"]+)\"");
                Regex regex3 = new Regex("x-tvg-url=\"([^\"]+)\"");
                Regex regex4 = new Regex("group-title=\"([^\"]+)\"");
                ArrayList arrayList = new ArrayList();
                int i3 = 0;
                for (String str10 : lines) {
                    i3 += i2;
                    String obj = StringsKt.trim((CharSequence) str10).toString();
                    if (StringsKt.startsWith$default(obj, "#EXTM3U", false, 2, (Object) null)) {
                        MatchResult find$default = Regex.find$default(regex3, obj, 0, 2, null);
                        epg = (find$default == null || (groupValues4 = find$default.getGroupValues()) == null || (str5 = groupValues4.get(i2)) == null) ? null : StringsKt.trim((CharSequence) str5).toString();
                        SP.INSTANCE.setEpg(epg);
                    } else if (StringsKt.startsWith$default(obj, "#EXTINF", false, 2, (Object) null)) {
                        List split$default = StringsKt.split$default((CharSequence) obj, new String[]{","}, false, 0, 6, (Object) null);
                        String obj2 = StringsKt.trim((CharSequence) ((String) CollectionsKt.last((List<? extends Object>) split$default))).toString();
                        MatchResult find$default2 = Regex.find$default(regex, (CharSequence) CollectionsKt.first((List<? extends Object>) split$default), 0, 2, null);
                        String obj3 = (find$default2 == null || (groupValues3 = find$default2.getGroupValues()) == null || (str4 = groupValues3.get(i2)) == null) ? null : StringsKt.trim((CharSequence) str4).toString();
                        MatchResult find$default3 = Regex.find$default(regex4, (CharSequence) CollectionsKt.first((List<? extends Object>) split$default), 0, 2, null);
                        String obj4 = (find$default3 == null || (groupValues2 = find$default3.getGroupValues()) == null || (str3 = groupValues2.get(i2)) == null) ? null : StringsKt.trim((CharSequence) str3).toString();
                        MatchResult find$default4 = Regex.find$default(regex2, (CharSequence) CollectionsKt.first((List<? extends Object>) split$default), 0, 2, null);
                        String obj5 = (find$default4 == null || (groupValues = find$default4.getGroupValues()) == null || (str2 = groupValues.get(i2)) == null) ? null : StringsKt.trim((CharSequence) str2).toString();
                        arrayList.add(new TV(0, obj3 == null ? "" : obj3, obj2, "", obj5 == null ? "" : obj5, "", i3 < lines.size() ? CollectionsKt.listOf(StringsKt.trim((CharSequence) lines.get(i3)).toString()) : CollectionsKt.emptyList(), MapsKt.emptyMap(), obj4 == null ? "" : obj4, SourceType.UNKNOWN, CollectionsKt.emptyList()));
                    }
                    i2 = 1;
                }
                list = arrayList;
            } else {
                List<String> lines2 = StringsKt.lines(str8);
                ArrayList arrayList2 = new ArrayList();
                for (String str11 : lines2) {
                    String obj6 = StringsKt.trim((CharSequence) str11).toString();
                    if (obj6.length() > 0) {
                        if (StringsKt.contains$default((CharSequence) obj6, (CharSequence) "#genre#", false, 2, (Object) null)) {
                            str9 = StringsKt.trim((CharSequence) ((String) StringsKt.split$default((CharSequence) obj6, new char[]{','}, false, 2, 2, (Object) null).get(0))).toString();
                        } else {
                            List<String> split$default2 = StringsKt.split$default((CharSequence) obj6, new char[]{','}, false, 0, 6, (Object) null);
                            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(split$default2, 10));
                            for (String str12 : split$default2) {
                                arrayList3.add(StringsKt.trim((CharSequence) str12).toString());
                            }
                            ArrayList arrayList4 = arrayList3;
                            arrayList2.add(new TV(0, "", StringsKt.trim((CharSequence) ((String) CollectionsKt.first((List<? extends Object>) arrayList4))).toString(), "", "", "", CollectionsKt.drop(arrayList4, 1), MapsKt.emptyMap(), str9, SourceType.UNKNOWN, CollectionsKt.emptyList()));
                        }
                    }
                }
                list = arrayList2;
            }
        }
        groupModel.clear2();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        List<TV> list3 = list;
        if (list3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("list");
        } else {
            list2 = list3;
        }
        for (TV tv : list2) {
            if (!linkedHashMap.containsKey(tv.getGroup())) {
                linkedHashMap.put(tv.getGroup(), new ArrayList());
            }
            List list4 = (List) linkedHashMap.get(tv.getGroup());
            if (list4 != null) {
                list4.add(new TVModel(tv));
            }
        }
        ArrayList arrayList5 = new ArrayList();
        int i4 = 0;
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            TVListModel tVListModel = new TVListModel((String) entry.getKey(), i);
            int i5 = 0;
            for (TVModel tVModel : (List) entry.getValue()) {
                tVModel.getTv().setId(i4);
                tVModel.setGroupIndex(i);
                tVModel.setListIndex(i5);
                tVListModel.addTVModel(tVModel);
                arrayList5.add(tVModel);
                i4++;
                i5++;
            }
            groupModel.addTVListModel(tVListModel);
            i++;
        }
        listModel = arrayList5;
        TVGroupModel tVGroupModel = groupModel;
        TVListModel tVListModel2 = tVGroupModel.getTVListModel(1);
        if (tVListModel2 != null) {
            tVListModel2.setTVListModel(listModel);
        }
        tVGroupModel.setChange();
        return true;
    }

    public final TVModel getTVModel() {
        Integer value = getPosition().getValue();
        Intrinsics.checkNotNull(value);
        return getTVModel(value.intValue());
    }

    public final TVModel getTVModel(int i) {
        return listModel.get(i);
    }

    public final boolean setPosition(int i) {
        if (i >= size()) {
            return false;
        }
        MutableLiveData<Integer> mutableLiveData = _position;
        Integer value = mutableLiveData.getValue();
        if (value == null || value.intValue() != i) {
            mutableLiveData.setValue(Integer.valueOf(i));
        }
        TVModel tVModel = getTVModel(i);
        tVModel.setReady();
        groupModel.setPosition(tVModel.getGroupIndex());
        SP.INSTANCE.setPositionGroup(tVModel.getGroupIndex());
        SP.INSTANCE.setPosition(i);
        return true;
    }

    public final int size() {
        return listModel.size();
    }
}
