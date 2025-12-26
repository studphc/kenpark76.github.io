package j$.util.concurrent;
/* loaded from: classes2.dex */
abstract class Helpers {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static String mapEntryToString(Object obj, Object obj2) {
        String objectToString = objectToString(obj);
        int length = objectToString.length();
        String objectToString2 = objectToString(obj2);
        int length2 = objectToString2.length();
        char[] cArr = new char[length + length2 + 1];
        objectToString.getChars(0, length, cArr, 0);
        cArr[length] = '=';
        objectToString2.getChars(0, length2, cArr, length + 1);
        return new String(cArr);
    }

    private static String objectToString(Object obj) {
        String obj2;
        return (obj == null || (obj2 = obj.toString()) == null) ? "null" : obj2;
    }
}
