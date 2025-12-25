package com.pjy.koreatv.models;

import android.util.Xml;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.media3.exoplayer.upstream.CmcdData;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import org.xmlpull.v1.XmlPullParser;
/* compiled from: EPGXmlParser.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0005H\u0002J \u0010\r\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u00042\u0006\u0010\u000e\u001a\u00020\u000fR \u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/pjy/koreatv/models/EPGXmlParser;", "", "()V", "epg", "", "", "", "Lcom/pjy/koreatv/models/EPG;", "epgChannel2Id", "ns", "formatFTime", "", CmcdData.Factory.STREAMING_FORMAT_SS, "parse", "inputStream", "Ljava/io/InputStream;", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class EPGXmlParser {
    private final Map<String, List<EPG>> epg = new LinkedHashMap();
    private final Map<String, String> epgChannel2Id = new LinkedHashMap();
    private final String ns;

    private final int formatFTime(String str) {
        Date parse = new SimpleDateFormat("yyyyMMddHHmmss Z", Locale.getDefault()).parse(str);
        if (parse != null) {
            return (int) (parse.getTime() / 1000);
        }
        return 0;
    }

    public final Map<String, List<EPG>> parse(InputStream inputStream) {
        Intrinsics.checkNotNullParameter(inputStream, "inputStream");
        InputStream inputStream2 = inputStream;
        try {
            XmlPullParser newPullParser = Xml.newPullParser();
            Intrinsics.checkNotNullExpressionValue(newPullParser, "newPullParser(...)");
            newPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", false);
            newPullParser.setInput(inputStream2, null);
            newPullParser.nextTag();
            while (newPullParser.getEventType() != 1) {
                if (newPullParser.getEventType() != 2) {
                    newPullParser.next();
                } else {
                    if (Intrinsics.areEqual(newPullParser.getName(), "channel")) {
                        String attributeValue = newPullParser.getAttributeValue(null, "id");
                        Intrinsics.checkNotNullExpressionValue(attributeValue, "getAttributeValue(...)");
                        newPullParser.nextTag();
                        String nextText = newPullParser.nextText();
                        Intrinsics.checkNotNullExpressionValue(nextText, "nextText(...)");
                        this.epg.put(nextText, new ArrayList());
                        this.epgChannel2Id.put(attributeValue, nextText);
                    } else if (Intrinsics.areEqual(newPullParser.getName(), "programme")) {
                        String attributeValue2 = newPullParser.getAttributeValue(this.ns, "start");
                        String attributeValue3 = newPullParser.getAttributeValue(this.ns, "stop");
                        String attributeValue4 = newPullParser.getAttributeValue(null, "channel");
                        Intrinsics.checkNotNullExpressionValue(attributeValue4, "getAttributeValue(...)");
                        newPullParser.nextTag();
                        String nextText2 = newPullParser.nextText();
                        Intrinsics.checkNotNull(attributeValue2);
                        formatFTime(attributeValue2);
                        List<EPG> list = this.epg.get(this.epgChannel2Id.get(attributeValue4));
                        if (list != null) {
                            Intrinsics.checkNotNull(nextText2);
                            int formatFTime = formatFTime(attributeValue2);
                            Intrinsics.checkNotNull(attributeValue3);
                            list.add(new EPG(nextText2, formatFTime, formatFTime(attributeValue3)));
                        }
                    }
                    newPullParser.next();
                }
            }
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(inputStream2, null);
            return this.epg;
        } finally {
        }
    }
}
