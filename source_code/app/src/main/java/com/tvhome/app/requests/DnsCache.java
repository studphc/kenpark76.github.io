package com.tvhome.app.requests;

import android.text.TextUtils;
import androidx.constraintlayout.widget.ConstraintLayout;
import j$.util.concurrent.ConcurrentHashMap;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Dns;
/* compiled from: DnsCache.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\t\u001a\u00020\u0005H\u0016R \u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/pjy/koreatv/requests/DnsCache;", "Lokhttp3/Dns;", "()V", "dnsCache", "", "", "", "Ljava/net/InetAddress;", "lookup", "hostname", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class DnsCache implements Dns {
    private final Map<String, List<InetAddress>> dnsCache = new ConcurrentHashMap();

    @Override // okhttp3.Dns
    public List<InetAddress> lookup(String hostname) {
        Intrinsics.checkNotNullParameter(hostname, "hostname");
        if (TextUtils.isEmpty(hostname)) {
            List<InetAddress> lookup = Dns.SYSTEM.lookup(hostname);
            Intrinsics.checkNotNullExpressionValue(lookup, "lookup(...)");
            return lookup;
        }
        List<InetAddress> list = this.dnsCache.get(hostname);
        if (list != null) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        InetAddress[] allByName = InetAddress.getAllByName(hostname);
        Intrinsics.checkNotNullExpressionValue(allByName, "getAllByName(...)");
        for (InetAddress inetAddress : ArraysKt.toList(allByName)) {
            if (inetAddress instanceof Inet4Address) {
                arrayList.add(0, inetAddress);
            } else {
                Intrinsics.checkNotNull(inetAddress);
                arrayList.add(inetAddress);
            }
        }
        if (!arrayList.isEmpty()) {
            this.dnsCache.put(hostname, arrayList);
        }
        return arrayList;
    }
}
