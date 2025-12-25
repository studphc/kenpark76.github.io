package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.CheckForNull;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class ObjectArrays {
    private ObjectArrays() {
    }

    public static <T> T[] newArray(Class<T> type, int length) {
        return (T[]) ((Object[]) Array.newInstance((Class<?>) type, length));
    }

    public static <T> T[] newArray(T[] reference, int length) {
        return (T[]) Platform.newArray(reference, length);
    }

    public static <T> T[] concat(T[] first, T[] second, Class<T> type) {
        T[] tArr = (T[]) newArray(type, first.length + second.length);
        System.arraycopy(first, 0, tArr, 0, first.length);
        System.arraycopy(second, 0, tArr, first.length, second.length);
        return tArr;
    }

    public static <T> T[] concat(@ParametricNullness T element, T[] array) {
        T[] tArr = (T[]) newArray(array, array.length + 1);
        tArr[0] = element;
        System.arraycopy(array, 0, tArr, 1, array.length);
        return tArr;
    }

    public static <T> T[] concat(T[] array, @ParametricNullness T element) {
        T[] tArr = (T[]) Arrays.copyOf(array, array.length + 1);
        tArr[array.length] = element;
        return tArr;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> T[] toArrayImpl(Collection<?> c, T[] array) {
        int size = c.size();
        if (array.length < size) {
            array = (T[]) newArray(array, size);
        }
        fillArray(c, array);
        if (array.length > size) {
            array[size] = null;
        }
        return array;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> T[] toArrayImpl(Object[] src, int offset, int len, T[] dst) {
        Preconditions.checkPositionIndexes(offset, offset + len, src.length);
        if (dst.length < len) {
            dst = (T[]) newArray(dst, len);
        } else if (dst.length > len) {
            dst[len] = null;
        }
        System.arraycopy(src, offset, dst, 0, len);
        return dst;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Object[] toArrayImpl(Collection<?> c) {
        return fillArray(c, new Object[c.size()]);
    }

    static Object[] copyAsObjectArray(Object[] elements, int offset, int length) {
        Preconditions.checkPositionIndexes(offset, offset + length, elements.length);
        if (length == 0) {
            return new Object[0];
        }
        Object[] objArr = new Object[length];
        System.arraycopy(elements, offset, objArr, 0, length);
        return objArr;
    }

    private static Object[] fillArray(Iterable<?> elements, Object[] array) {
        Iterator<?> it = elements.iterator();
        int i = 0;
        while (it.hasNext()) {
            array[i] = it.next();
            i++;
        }
        return array;
    }

    static void swap(Object[] array, int i, int j) {
        Object obj = array[i];
        array[i] = array[j];
        array[j] = obj;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Object[] checkElementsNotNull(Object... array) {
        checkElementsNotNull(array, array.length);
        return array;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Object[] checkElementsNotNull(Object[] array, int length) {
        for (int i = 0; i < length; i++) {
            checkElementNotNull(array[i], i);
        }
        return array;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Object checkElementNotNull(@CheckForNull Object element, int index) {
        if (element != null) {
            return element;
        }
        throw new NullPointerException("at index " + index);
    }
}
