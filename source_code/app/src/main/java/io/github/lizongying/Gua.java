package io.github.lizongying;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import kotlin.UByte$$ExternalSyntheticBackport0;
/* loaded from: classes3.dex */
public class Gua {
    String gua = "䷁䷖䷇䷓䷏䷢䷬䷋䷎䷳䷦䷴䷽䷷䷞䷠䷆䷃䷜䷺䷧䷿䷮䷅䷭䷑䷯䷸䷟䷱䷛䷫䷗䷚䷂䷩䷲䷔䷐䷘䷣䷕䷾䷤䷶䷝䷰䷌䷒䷨䷻䷼䷵䷥䷹䷉䷊䷙䷄䷈䷡䷍䷪䷀";
    Map<String, Integer> gua64dict = new HashMap();
    String[] gua64list = this.gua.split("");

    public Gua() {
        for (int i = 0; i < 64; i++) {
            this.gua64dict.put(this.gua64list[i], Integer.valueOf(i));
        }
    }

    public String encode(String str) {
        ArrayList arrayList = new ArrayList();
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        int length = bytes.length;
        for (int i = 0; i < length; i += 3) {
            arrayList.add(this.gua64list[(bytes[i] & 255) >> 2]);
            if (i == length - 1) {
                arrayList.add(this.gua64list[((bytes[i] & 255) & 3) << 4]);
                arrayList.add("〇");
                arrayList.add("〇");
            } else {
                int i2 = i + 1;
                arrayList.add(this.gua64list[(((bytes[i] & 255) & 3) << 4) | ((bytes[i2] & 255) >> 4)]);
                if (i == length - 2) {
                    arrayList.add(this.gua64list[((bytes[i2] & 255) & 15) << 2]);
                    arrayList.add("〇");
                } else {
                    int i3 = i + 2;
                    arrayList.add(this.gua64list[(((bytes[i2] & 255) & 15) << 2) | ((bytes[i3] & 255) >> 6)]);
                    arrayList.add(this.gua64list[bytes[i3] & 255 & 63]);
                }
            }
        }
        return UByte$$ExternalSyntheticBackport0.m("", arrayList);
    }

    public String decode(String str) {
        int length = str.length();
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            if (this.gua64dict.containsKey(String.valueOf(str.charAt(i)))) {
                iArr[i] = this.gua64dict.get(String.valueOf(str.charAt(i))).intValue();
            } else {
                iArr[i] = 255;
            }
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < length; i2 += 4) {
            int i3 = i2 + 1;
            arrayList.add(Byte.valueOf((byte) (((iArr[i2] & 63) << 2) | ((iArr[i3] >> 4) & 3))));
            int i4 = i2 + 2;
            int i5 = iArr[i4];
            if (i5 != 255) {
                arrayList.add(Byte.valueOf((byte) (((iArr[i3] & 15) << 4) | ((i5 >> 2) & 15))));
            }
            int i6 = iArr[i2 + 3];
            if (i6 != 255) {
                arrayList.add(Byte.valueOf((byte) (((iArr[i4] & 3) << 6) | (i6 & 63))));
            }
        }
        int size = arrayList.size();
        byte[] bArr = new byte[size];
        for (int i7 = 0; i7 < size; i7++) {
            bArr[i7] = ((Byte) arrayList.get(i7)).byteValue();
        }
        return new String(bArr, StandardCharsets.UTF_8);
    }

    public boolean verify(String str) {
        HashSet hashSet = new HashSet();
        for (char c : this.gua.toCharArray()) {
            hashSet.add(Character.valueOf(c));
        }
        hashSet.add((char) 12295);
        for (char c2 : str.toCharArray()) {
            if (!hashSet.contains(Character.valueOf(c2))) {
                return false;
            }
        }
        return true;
    }
}
