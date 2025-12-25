package com.google.zxing.common;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes3.dex */
public final class ECIEncoderSet {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final List<CharsetEncoder> ENCODERS = new ArrayList();
    private final CharsetEncoder[] encoders;
    private final int priorityEncoderIndex;

    static {
        String[] strArr = {"IBM437", "ISO-8859-2", "ISO-8859-3", "ISO-8859-4", "ISO-8859-5", "ISO-8859-6", "ISO-8859-7", "ISO-8859-8", "ISO-8859-9", "ISO-8859-10", "ISO-8859-11", "ISO-8859-13", "ISO-8859-14", "ISO-8859-15", "ISO-8859-16", "windows-1250", "windows-1251", "windows-1252", "windows-1256", "Shift_JIS"};
        for (int i = 0; i < 20; i++) {
            String str = strArr[i];
            if (CharacterSetECI.getCharacterSetECIByName(str) != null) {
                try {
                    ENCODERS.add(Charset.forName(str).newEncoder());
                } catch (UnsupportedCharsetException unused) {
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0035  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public ECIEncoderSet(java.lang.String r10, java.nio.charset.Charset r11, int r12) {
        /*
            r9 = this;
            r9.<init>()
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.nio.charset.Charset r1 = java.nio.charset.StandardCharsets.ISO_8859_1
            java.nio.charset.CharsetEncoder r1 = r1.newEncoder()
            r0.add(r1)
            r1 = 0
            r2 = 1
            if (r11 == 0) goto L23
            java.lang.String r3 = r11.name()
            java.lang.String r4 = "UTF"
            boolean r3 = r3.startsWith(r4)
            if (r3 == 0) goto L23
            r3 = 1
            goto L24
        L23:
            r3 = 0
        L24:
            r4 = 0
        L25:
            int r5 = r10.length()
            if (r4 >= r5) goto L72
            java.util.Iterator r5 = r0.iterator()
        L2f:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L49
            java.lang.Object r6 = r5.next()
            java.nio.charset.CharsetEncoder r6 = (java.nio.charset.CharsetEncoder) r6
            char r7 = r10.charAt(r4)
            if (r7 == r12) goto L47
            boolean r6 = r6.canEncode(r7)
            if (r6 == 0) goto L2f
        L47:
            r5 = 1
            goto L4a
        L49:
            r5 = 0
        L4a:
            if (r5 != 0) goto L6c
            java.util.List<java.nio.charset.CharsetEncoder> r6 = com.google.zxing.common.ECIEncoderSet.ENCODERS
            java.util.Iterator r6 = r6.iterator()
        L52:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto L6c
            java.lang.Object r7 = r6.next()
            java.nio.charset.CharsetEncoder r7 = (java.nio.charset.CharsetEncoder) r7
            char r8 = r10.charAt(r4)
            boolean r8 = r7.canEncode(r8)
            if (r8 == 0) goto L52
            r0.add(r7)
            r5 = 1
        L6c:
            if (r5 != 0) goto L6f
            r3 = 1
        L6f:
            int r4 = r4 + 1
            goto L25
        L72:
            int r10 = r0.size()
            if (r10 != r2) goto L87
            if (r3 != 0) goto L87
            java.nio.charset.CharsetEncoder[] r10 = new java.nio.charset.CharsetEncoder[r2]
            java.lang.Object r12 = r0.get(r1)
            java.nio.charset.CharsetEncoder r12 = (java.nio.charset.CharsetEncoder) r12
            r10[r1] = r12
            r9.encoders = r10
            goto Lbf
        L87:
            int r10 = r0.size()
            int r10 = r10 + 2
            java.nio.charset.CharsetEncoder[] r10 = new java.nio.charset.CharsetEncoder[r10]
            r9.encoders = r10
            java.util.Iterator r10 = r0.iterator()
            r12 = 0
        L96:
            boolean r0 = r10.hasNext()
            if (r0 == 0) goto Laa
            java.lang.Object r0 = r10.next()
            java.nio.charset.CharsetEncoder r0 = (java.nio.charset.CharsetEncoder) r0
            java.nio.charset.CharsetEncoder[] r3 = r9.encoders
            int r4 = r12 + 1
            r3[r12] = r0
            r12 = r4
            goto L96
        Laa:
            java.nio.charset.CharsetEncoder[] r10 = r9.encoders
            java.nio.charset.Charset r0 = java.nio.charset.StandardCharsets.UTF_8
            java.nio.charset.CharsetEncoder r0 = r0.newEncoder()
            r10[r12] = r0
            java.nio.charset.CharsetEncoder[] r10 = r9.encoders
            int r12 = r12 + r2
            java.nio.charset.Charset r0 = java.nio.charset.StandardCharsets.UTF_16BE
            java.nio.charset.CharsetEncoder r0 = r0.newEncoder()
            r10[r12] = r0
        Lbf:
            if (r11 == 0) goto Le4
        Lc1:
            java.nio.charset.CharsetEncoder[] r10 = r9.encoders
            int r12 = r10.length
            if (r1 >= r12) goto Le4
            r10 = r10[r1]
            if (r10 == 0) goto Le1
            java.lang.String r10 = r11.name()
            java.nio.charset.CharsetEncoder[] r12 = r9.encoders
            r12 = r12[r1]
            java.nio.charset.Charset r12 = r12.charset()
            java.lang.String r12 = r12.name()
            boolean r10 = r10.equals(r12)
            if (r10 == 0) goto Le1
            goto Le5
        Le1:
            int r1 = r1 + 1
            goto Lc1
        Le4:
            r1 = -1
        Le5:
            r9.priorityEncoderIndex = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.common.ECIEncoderSet.<init>(java.lang.String, java.nio.charset.Charset, int):void");
    }

    public int length() {
        return this.encoders.length;
    }

    public String getCharsetName(int i) {
        return this.encoders[i].charset().name();
    }

    public Charset getCharset(int i) {
        return this.encoders[i].charset();
    }

    public int getECIValue(int i) {
        return CharacterSetECI.getCharacterSetECI(this.encoders[i].charset()).getValue();
    }

    public int getPriorityEncoderIndex() {
        return this.priorityEncoderIndex;
    }

    public boolean canEncode(char c, int i) {
        CharsetEncoder charsetEncoder = this.encoders[i];
        return charsetEncoder.canEncode("" + c);
    }

    public byte[] encode(char c, int i) {
        CharsetEncoder charsetEncoder = this.encoders[i];
        return ("" + c).getBytes(charsetEncoder.charset());
    }

    public byte[] encode(String str, int i) {
        return str.getBytes(this.encoders[i].charset());
    }
}
