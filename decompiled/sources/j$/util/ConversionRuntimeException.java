package j$.util;
/* loaded from: classes2.dex */
public class ConversionRuntimeException extends RuntimeException {
    public ConversionRuntimeException(String str) {
        super(str);
    }

    public static RuntimeException exception(String str, Object obj) {
        throw new ConversionRuntimeException("Unsupported " + str + " :" + obj);
    }
}
