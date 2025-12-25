package com.google.common.primitives;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class ParseRequest {
    final int radix;
    final String rawValue;

    private ParseRequest(String rawValue, int radix) {
        this.rawValue = rawValue;
        this.radix = radix;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ParseRequest fromString(String stringValue) {
        if (stringValue.length() == 0) {
            throw new NumberFormatException("empty string");
        }
        char charAt = stringValue.charAt(0);
        int i = 16;
        if (stringValue.startsWith("0x") || stringValue.startsWith("0X")) {
            stringValue = stringValue.substring(2);
        } else if (charAt == '#') {
            stringValue = stringValue.substring(1);
        } else if (charAt != '0' || stringValue.length() <= 1) {
            i = 10;
        } else {
            stringValue = stringValue.substring(1);
            i = 8;
        }
        return new ParseRequest(stringValue, i);
    }
}
