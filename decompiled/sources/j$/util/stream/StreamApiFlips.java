package j$.util.stream;

import j$.util.ConversionRuntimeException;
import j$.util.stream.Collector;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collector;
/* loaded from: classes2.dex */
public abstract class StreamApiFlips {
    public static RuntimeException exceptionCharacteristics(Object obj) {
        throw ConversionRuntimeException.exception("java.util.stream.Collector.Characteristics", obj);
    }

    public static Set flipCharacteristicSet(Set set) {
        if (set == null || set.isEmpty()) {
            return set;
        }
        HashSet hashSet = new HashSet();
        Object next = set.iterator().next();
        if (next instanceof Collector.Characteristics) {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                try {
                    hashSet.add(Collector.Characteristics.EnumConversion.convert((Collector.Characteristics) it.next()));
                } catch (ClassCastException e) {
                    throw exceptionCharacteristics(e);
                }
            }
            return hashSet;
        } else if (next instanceof Collector.Characteristics) {
            Iterator it2 = set.iterator();
            while (it2.hasNext()) {
                try {
                    hashSet.add(Collector.Characteristics.EnumConversion.convert((Collector.Characteristics) it2.next()));
                } catch (ClassCastException e2) {
                    throw exceptionCharacteristics(e2);
                }
            }
            return hashSet;
        } else {
            throw exceptionCharacteristics(next.getClass());
        }
    }
}
