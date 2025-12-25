package com.google.common.collect;

import com.google.common.collect.Multiset;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class Serialization {
    private Serialization() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int readCount(ObjectInputStream stream) throws IOException {
        return stream.readInt();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <K, V> void writeMap(Map<K, V> map, ObjectOutputStream stream) throws IOException {
        stream.writeInt(map.size());
        for (Map.Entry<K, V> entry : map.entrySet()) {
            stream.writeObject(entry.getKey());
            stream.writeObject(entry.getValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <K, V> void populateMap(Map<K, V> map, ObjectInputStream stream) throws IOException, ClassNotFoundException {
        populateMap(map, stream, stream.readInt());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    public static <K, V> void populateMap(Map<K, V> map, ObjectInputStream stream, int size) throws IOException, ClassNotFoundException {
        for (int i = 0; i < size; i++) {
            map.put(stream.readObject(), stream.readObject());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <E> void writeMultiset(Multiset<E> multiset, ObjectOutputStream stream) throws IOException {
        stream.writeInt(multiset.entrySet().size());
        for (Multiset.Entry<E> entry : multiset.entrySet()) {
            stream.writeObject(entry.getElement());
            stream.writeInt(entry.getCount());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <E> void populateMultiset(Multiset<E> multiset, ObjectInputStream stream) throws IOException, ClassNotFoundException {
        populateMultiset(multiset, stream, stream.readInt());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    public static <E> void populateMultiset(Multiset<E> multiset, ObjectInputStream stream, int distinctElements) throws IOException, ClassNotFoundException {
        for (int i = 0; i < distinctElements; i++) {
            multiset.add(stream.readObject(), stream.readInt());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <K, V> void writeMultimap(Multimap<K, V> multimap, ObjectOutputStream stream) throws IOException {
        stream.writeInt(multimap.asMap().size());
        for (Map.Entry<K, Collection<V>> entry : multimap.asMap().entrySet()) {
            stream.writeObject(entry.getKey());
            stream.writeInt(entry.getValue().size());
            for (V v : entry.getValue()) {
                stream.writeObject(v);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <K, V> void populateMultimap(Multimap<K, V> multimap, ObjectInputStream stream) throws IOException, ClassNotFoundException {
        populateMultimap(multimap, stream, stream.readInt());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    public static <K, V> void populateMultimap(Multimap<K, V> multimap, ObjectInputStream stream, int distinctKeys) throws IOException, ClassNotFoundException {
        for (int i = 0; i < distinctKeys; i++) {
            Collection collection = multimap.get(stream.readObject());
            int readInt = stream.readInt();
            for (int i2 = 0; i2 < readInt; i2++) {
                collection.add(stream.readObject());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> FieldSetter<T> getFieldSetter(Class<T> clazz, String fieldName) {
        try {
            return new FieldSetter<>(clazz.getDeclaredField(fieldName));
        } catch (NoSuchFieldException e) {
            throw new AssertionError(e);
        }
    }

    /* loaded from: classes3.dex */
    static final class FieldSetter<T> {
        private final Field field;

        private FieldSetter(Field field) {
            this.field = field;
            field.setAccessible(true);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void set(T instance, Object value) {
            try {
                this.field.set(instance, value);
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void set(T instance, int value) {
            try {
                this.field.set(instance, Integer.valueOf(value));
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            }
        }
    }
}
