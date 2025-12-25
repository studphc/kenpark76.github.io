package com.google.common.collect;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckForNull;
/* JADX INFO: Access modifiers changed from: package-private */
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public class FilteredEntryMultimap<K, V> extends AbstractMultimap<K, V> implements FilteredMultimap<K, V> {
    final Predicate<? super Map.Entry<K, V>> predicate;
    final Multimap<K, V> unfiltered;

    /* JADX INFO: Access modifiers changed from: package-private */
    public FilteredEntryMultimap(Multimap<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> predicate) {
        this.unfiltered = (Multimap) Preconditions.checkNotNull(unfiltered);
        this.predicate = (Predicate) Preconditions.checkNotNull(predicate);
    }

    @Override // com.google.common.collect.FilteredMultimap
    public Multimap<K, V> unfiltered() {
        return this.unfiltered;
    }

    @Override // com.google.common.collect.FilteredMultimap
    public Predicate<? super Map.Entry<K, V>> entryPredicate() {
        return this.predicate;
    }

    @Override // com.google.common.collect.Multimap
    public int size() {
        return entries().size();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean satisfies(@ParametricNullness K key, @ParametricNullness V value) {
        return this.predicate.apply(Maps.immutableEntry(key, value));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public final class ValuePredicate implements Predicate<V> {
        @ParametricNullness
        private final K key;

        ValuePredicate(@ParametricNullness K key) {
            this.key = key;
        }

        @Override // com.google.common.base.Predicate
        public boolean apply(@ParametricNullness V value) {
            return FilteredEntryMultimap.this.satisfies(this.key, value);
        }
    }

    static <E> Collection<E> filterCollection(Collection<E> collection, Predicate<? super E> predicate) {
        if (collection instanceof Set) {
            return Sets.filter((Set) collection, predicate);
        }
        return Collections2.filter(collection, predicate);
    }

    @Override // com.google.common.collect.Multimap
    public boolean containsKey(@CheckForNull Object key) {
        return asMap().get(key) != null;
    }

    @Override // com.google.common.collect.Multimap
    public Collection<V> removeAll(@CheckForNull Object key) {
        return (Collection) MoreObjects.firstNonNull(asMap().remove(key), unmodifiableEmptyCollection());
    }

    Collection<V> unmodifiableEmptyCollection() {
        if (this.unfiltered instanceof SetMultimap) {
            return Collections.emptySet();
        }
        return Collections.emptyList();
    }

    @Override // com.google.common.collect.Multimap
    public void clear() {
        entries().clear();
    }

    @Override // com.google.common.collect.Multimap
    public Collection<V> get(@ParametricNullness K key) {
        return filterCollection(this.unfiltered.get(key), new ValuePredicate(key));
    }

    @Override // com.google.common.collect.AbstractMultimap
    Collection<Map.Entry<K, V>> createEntries() {
        return filterCollection(this.unfiltered.entries(), this.predicate);
    }

    @Override // com.google.common.collect.AbstractMultimap
    Collection<V> createValues() {
        return new FilteredMultimapValues(this);
    }

    @Override // com.google.common.collect.AbstractMultimap
    Iterator<Map.Entry<K, V>> entryIterator() {
        throw new AssertionError("should never be called");
    }

    @Override // com.google.common.collect.AbstractMultimap
    Map<K, Collection<V>> createAsMap() {
        return new AsMap();
    }

    @Override // com.google.common.collect.AbstractMultimap
    Set<K> createKeySet() {
        return asMap().keySet();
    }

    boolean removeEntriesIf(Predicate<? super Map.Entry<K, Collection<V>>> predicate) {
        Iterator<Map.Entry<K, Collection<V>>> it = this.unfiltered.asMap().entrySet().iterator();
        boolean z = false;
        while (it.hasNext()) {
            Map.Entry<K, Collection<V>> next = it.next();
            K key = next.getKey();
            Collection filterCollection = filterCollection(next.getValue(), new ValuePredicate(key));
            if (!filterCollection.isEmpty() && predicate.apply(Maps.immutableEntry(key, filterCollection))) {
                if (filterCollection.size() == next.getValue().size()) {
                    it.remove();
                } else {
                    filterCollection.clear();
                }
                z = true;
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class AsMap extends Maps.ViewCachingAbstractMap<K, Collection<V>> {
        AsMap() {
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(@CheckForNull Object key) {
            return get(key) != null;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public void clear() {
            FilteredEntryMultimap.this.clear();
        }

        @Override // java.util.AbstractMap, java.util.Map
        @CheckForNull
        public Collection<V> get(@CheckForNull Object key) {
            Collection<V> collection = FilteredEntryMultimap.this.unfiltered.asMap().get(key);
            if (collection == null) {
                return null;
            }
            Collection<V> filterCollection = FilteredEntryMultimap.filterCollection(collection, new ValuePredicate(key));
            if (filterCollection.isEmpty()) {
                return null;
            }
            return filterCollection;
        }

        @Override // java.util.AbstractMap, java.util.Map
        @CheckForNull
        public Collection<V> remove(@CheckForNull Object key) {
            Collection<V> collection = FilteredEntryMultimap.this.unfiltered.asMap().get(key);
            if (collection == null) {
                return null;
            }
            ArrayList newArrayList = Lists.newArrayList();
            Iterator<V> it = collection.iterator();
            while (it.hasNext()) {
                V next = it.next();
                if (FilteredEntryMultimap.this.satisfies(key, next)) {
                    it.remove();
                    newArrayList.add(next);
                }
            }
            if (newArrayList.isEmpty()) {
                return null;
            }
            if (FilteredEntryMultimap.this.unfiltered instanceof SetMultimap) {
                return Collections.unmodifiableSet(Sets.newLinkedHashSet(newArrayList));
            }
            return Collections.unmodifiableList(newArrayList);
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap
        Set<K> createKeySet() {
            return new Maps.KeySet<K, Collection<V>>() { // from class: com.google.common.collect.FilteredEntryMultimap.AsMap.1KeySetImpl
                @Override // com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
                public boolean removeAll(Collection<?> c) {
                    return FilteredEntryMultimap.this.removeEntriesIf(Maps.keyPredicateOnEntries(Predicates.in(c)));
                }

                @Override // com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
                public boolean retainAll(Collection<?> c) {
                    return FilteredEntryMultimap.this.removeEntriesIf(Maps.keyPredicateOnEntries(Predicates.not(Predicates.in(c))));
                }

                @Override // com.google.common.collect.Maps.KeySet, java.util.AbstractCollection, java.util.Collection, java.util.Set
                public boolean remove(@CheckForNull Object o) {
                    return AsMap.this.remove(o) != null;
                }
            };
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap
        Set<Map.Entry<K, Collection<V>>> createEntrySet() {
            return new Maps.EntrySet<K, Collection<V>>() { // from class: com.google.common.collect.FilteredEntryMultimap.AsMap.1EntrySetImpl
                @Override // com.google.common.collect.Maps.EntrySet
                Map<K, Collection<V>> map() {
                    return AsMap.this;
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
                public Iterator<Map.Entry<K, Collection<V>>> iterator() {
                    return new AbstractIterator<Map.Entry<K, Collection<V>>>() { // from class: com.google.common.collect.FilteredEntryMultimap.AsMap.1EntrySetImpl.1
                        final Iterator<Map.Entry<K, Collection<V>>> backingIterator;

                        {
                            this.backingIterator = FilteredEntryMultimap.this.unfiltered.asMap().entrySet().iterator();
                        }

                        /* JADX INFO: Access modifiers changed from: protected */
                        @Override // com.google.common.collect.AbstractIterator
                        @CheckForNull
                        public Map.Entry<K, Collection<V>> computeNext() {
                            while (this.backingIterator.hasNext()) {
                                Map.Entry<K, Collection<V>> next = this.backingIterator.next();
                                K key = next.getKey();
                                Collection filterCollection = FilteredEntryMultimap.filterCollection(next.getValue(), new ValuePredicate(key));
                                if (!filterCollection.isEmpty()) {
                                    return Maps.immutableEntry(key, filterCollection);
                                }
                            }
                            return endOfData();
                        }
                    };
                }

                @Override // com.google.common.collect.Maps.EntrySet, com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
                public boolean removeAll(Collection<?> c) {
                    return FilteredEntryMultimap.this.removeEntriesIf(Predicates.in(c));
                }

                @Override // com.google.common.collect.Maps.EntrySet, com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
                public boolean retainAll(Collection<?> c) {
                    return FilteredEntryMultimap.this.removeEntriesIf(Predicates.not(Predicates.in(c)));
                }

                @Override // com.google.common.collect.Maps.EntrySet, java.util.AbstractCollection, java.util.Collection, java.util.Set
                public int size() {
                    return Iterators.size(iterator());
                }
            };
        }

        @Override // com.google.common.collect.Maps.ViewCachingAbstractMap
        Collection<Collection<V>> createValues() {
            return new Maps.Values<K, Collection<V>>() { // from class: com.google.common.collect.FilteredEntryMultimap.AsMap.1ValuesImpl
                @Override // com.google.common.collect.Maps.Values, java.util.AbstractCollection, java.util.Collection
                public boolean remove(@CheckForNull Object o) {
                    if (o instanceof Collection) {
                        Collection collection = (Collection) o;
                        Iterator<Map.Entry<K, Collection<V>>> it = FilteredEntryMultimap.this.unfiltered.asMap().entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry<K, Collection<V>> next = it.next();
                            Collection filterCollection = FilteredEntryMultimap.filterCollection(next.getValue(), new ValuePredicate(next.getKey()));
                            if (!filterCollection.isEmpty() && collection.equals(filterCollection)) {
                                if (filterCollection.size() == next.getValue().size()) {
                                    it.remove();
                                    return true;
                                }
                                filterCollection.clear();
                                return true;
                            }
                        }
                        return false;
                    }
                    return false;
                }

                @Override // com.google.common.collect.Maps.Values, java.util.AbstractCollection, java.util.Collection
                public boolean removeAll(Collection<?> c) {
                    return FilteredEntryMultimap.this.removeEntriesIf(Maps.valuePredicateOnEntries(Predicates.in(c)));
                }

                @Override // com.google.common.collect.Maps.Values, java.util.AbstractCollection, java.util.Collection
                public boolean retainAll(Collection<?> c) {
                    return FilteredEntryMultimap.this.removeEntriesIf(Maps.valuePredicateOnEntries(Predicates.not(Predicates.in(c))));
                }
            };
        }
    }

    @Override // com.google.common.collect.AbstractMultimap
    Multiset<K> createKeys() {
        return new Keys();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class Keys extends Multimaps.Keys<K, V> {
        Keys() {
            super(FilteredEntryMultimap.this);
        }

        @Override // com.google.common.collect.Multimaps.Keys, com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
        public int remove(@CheckForNull Object key, int occurrences) {
            CollectPreconditions.checkNonnegative(occurrences, "occurrences");
            if (occurrences == 0) {
                return count(key);
            }
            Collection<V> collection = FilteredEntryMultimap.this.unfiltered.asMap().get(key);
            int i = 0;
            if (collection == null) {
                return 0;
            }
            Iterator<V> it = collection.iterator();
            while (it.hasNext()) {
                if (FilteredEntryMultimap.this.satisfies(key, it.next()) && (i = i + 1) <= occurrences) {
                    it.remove();
                }
            }
            return i;
        }

        /* renamed from: com.google.common.collect.FilteredEntryMultimap$Keys$1  reason: invalid class name */
        /* loaded from: classes3.dex */
        class AnonymousClass1 extends Multisets.EntrySet<K> {
            AnonymousClass1() {
            }

            @Override // com.google.common.collect.Multisets.EntrySet
            Multiset<K> multiset() {
                return Keys.this;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Multiset.Entry<K>> iterator() {
                return Keys.this.entryIterator();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return FilteredEntryMultimap.this.keySet().size();
            }

            private boolean removeEntriesIf(final Predicate<? super Multiset.Entry<K>> predicate) {
                return FilteredEntryMultimap.this.removeEntriesIf(new Predicate() { // from class: com.google.common.collect.FilteredEntryMultimap$Keys$1$$ExternalSyntheticLambda0
                    @Override // com.google.common.base.Predicate
                    public final boolean apply(Object obj) {
                        boolean apply;
                        apply = Predicate.this.apply(Multisets.immutableEntry(r2.getKey(), ((Collection) ((Map.Entry) obj).getValue()).size()));
                        return apply;
                    }
                });
            }

            @Override // com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean removeAll(Collection<?> c) {
                return removeEntriesIf(Predicates.in(c));
            }

            @Override // com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean retainAll(Collection<?> c) {
                return removeEntriesIf(Predicates.not(Predicates.in(c)));
            }
        }

        @Override // com.google.common.collect.AbstractMultiset, com.google.common.collect.Multiset
        public Set<Multiset.Entry<K>> entrySet() {
            return new AnonymousClass1();
        }
    }
}
