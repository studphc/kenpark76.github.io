package com.google.common.base;

import javax.annotation.CheckForNull;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class Defaults {
    private static final Double DOUBLE_DEFAULT = Double.valueOf(0.0d);
    private static final Float FLOAT_DEFAULT = Float.valueOf(0.0f);

    private Defaults() {
    }

    @CheckForNull
    public static <T> T defaultValue(Class<T> type) {
        Preconditions.checkNotNull(type);
        if (type.isPrimitive()) {
            if (type == Boolean.TYPE) {
                return (T) Boolean.FALSE;
            }
            if (type == Character.TYPE) {
                return (T) (char) 0;
            }
            if (type == Byte.TYPE) {
                return (T) (byte) 0;
            }
            if (type == Short.TYPE) {
                return (T) (short) 0;
            }
            if (type == Integer.TYPE) {
                return (T) 0;
            }
            if (type == Long.TYPE) {
                return (T) 0L;
            }
            if (type == Float.TYPE) {
                return (T) FLOAT_DEFAULT;
            }
            if (type == Double.TYPE) {
                return (T) DOUBLE_DEFAULT;
            }
            return null;
        }
        return null;
    }
}
