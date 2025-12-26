package com.google.zxing.common;

import com.google.zxing.DecodeHintType;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Map;
/* loaded from: classes3.dex */
public final class StringUtils {
    private static final boolean ASSUME_SHIFT_JIS;
    private static final Charset EUC_JP;
    public static final String GB2312 = "GB2312";
    public static final Charset GB2312_CHARSET;
    private static final Charset PLATFORM_DEFAULT_ENCODING = Charset.defaultCharset();
    public static final String SHIFT_JIS = "SJIS";
    public static final Charset SHIFT_JIS_CHARSET;

    static {
        Charset charset;
        Charset charset2;
        Charset charset3 = null;
        try {
            charset = Charset.forName(SHIFT_JIS);
        } catch (UnsupportedCharsetException unused) {
            charset = null;
        }
        SHIFT_JIS_CHARSET = charset;
        try {
            charset2 = Charset.forName(GB2312);
        } catch (UnsupportedCharsetException unused2) {
            charset2 = null;
        }
        GB2312_CHARSET = charset2;
        try {
            charset3 = Charset.forName("EUC_JP");
        } catch (UnsupportedCharsetException unused3) {
        }
        EUC_JP = charset3;
        Charset charset4 = SHIFT_JIS_CHARSET;
        ASSUME_SHIFT_JIS = (charset4 != null && charset4.equals(PLATFORM_DEFAULT_ENCODING)) || (charset3 != null && charset3.equals(PLATFORM_DEFAULT_ENCODING));
    }

    private StringUtils() {
    }

    public static String guessEncoding(byte[] bArr, Map<DecodeHintType, ?> map) {
        Charset guessCharset = guessCharset(bArr, map);
        return guessCharset.equals(SHIFT_JIS_CHARSET) ? SHIFT_JIS : guessCharset.equals(StandardCharsets.UTF_8) ? "UTF8" : guessCharset.equals(StandardCharsets.ISO_8859_1) ? "ISO8859_1" : guessCharset.name();
    }

    public static Charset guessCharset(byte[] bArr, Map<DecodeHintType, ?> map) {
        byte b;
        byte[] bArr2 = bArr;
        if (map != null && map.containsKey(DecodeHintType.CHARACTER_SET)) {
            return Charset.forName(map.get(DecodeHintType.CHARACTER_SET).toString());
        }
        boolean z = true;
        int i = 0;
        if (bArr2.length > 2 && (((b = bArr2[0]) == -2 && bArr2[1] == -1) || (b == -1 && bArr2[1] == -2))) {
            return StandardCharsets.UTF_16;
        }
        int length = bArr2.length;
        boolean z2 = SHIFT_JIS_CHARSET != null;
        boolean z3 = bArr2.length > 3 && bArr2[0] == -17 && bArr2[1] == -69 && bArr2[2] == -65;
        boolean z4 = z2;
        int i2 = 0;
        boolean z5 = true;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        int i11 = 0;
        int i12 = 0;
        while (i3 < length && (z || z4 || z5)) {
            int i13 = bArr2[i3] & 255;
            if (z5) {
                if (i4 > 0) {
                    if ((i13 & 128) != 0) {
                        i4--;
                    }
                    z5 = false;
                } else if ((i13 & 128) != 0) {
                    if ((i13 & 64) != 0) {
                        i4++;
                        if ((i13 & 32) == 0) {
                            i6++;
                        } else {
                            i4++;
                            if ((i13 & 16) == 0) {
                                i7++;
                            } else {
                                i4++;
                                if ((i13 & 8) == 0) {
                                    i8++;
                                }
                            }
                        }
                    }
                    z5 = false;
                }
            }
            if (z) {
                if (i13 > 127 && i13 < 160) {
                    z = false;
                } else if (i13 > 159 && (i13 < 192 || i13 == 215 || i13 == 247)) {
                    i10++;
                }
            }
            if (z4) {
                if (i5 > 0) {
                    if (i13 >= 64 && i13 != 127 && i13 <= 252) {
                        i5--;
                    }
                    z4 = false;
                } else {
                    if (i13 != 128 && i13 != 160 && i13 <= 239) {
                        if (i13 <= 160 || i13 >= 224) {
                            if (i13 > 127) {
                                i5++;
                                int i14 = i11 + 1;
                                if (i14 > i) {
                                    i = i14;
                                    i11 = i;
                                } else {
                                    i11 = i14;
                                }
                            } else {
                                i11 = 0;
                            }
                            i12 = 0;
                        } else {
                            i2++;
                            int i15 = i12 + 1;
                            if (i15 > i9) {
                                i9 = i15;
                                i12 = i9;
                            } else {
                                i12 = i15;
                            }
                            i11 = 0;
                        }
                    }
                    z4 = false;
                }
            }
            i3++;
            bArr2 = bArr;
        }
        if (z5 && i4 > 0) {
            z5 = false;
        }
        if (z4 && i5 > 0) {
            z4 = false;
        }
        if (z5 && (z3 || i6 + i7 + i8 > 0)) {
            return StandardCharsets.UTF_8;
        }
        if (!z4 || (!ASSUME_SHIFT_JIS && i9 < 3 && i < 3)) {
            if (z && z4) {
                return (!(i9 == 2 && i2 == 2) && i10 * 10 < length) ? StandardCharsets.ISO_8859_1 : SHIFT_JIS_CHARSET;
            } else if (z) {
                return StandardCharsets.ISO_8859_1;
            } else {
                if (z4) {
                    return SHIFT_JIS_CHARSET;
                }
                if (z5) {
                    return StandardCharsets.UTF_8;
                }
                return PLATFORM_DEFAULT_ENCODING;
            }
        }
        return SHIFT_JIS_CHARSET;
    }
}
