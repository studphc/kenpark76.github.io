package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Booleans;
import java.io.Serializable;
import java.lang.Comparable;
import java.util.NoSuchElementException;
import javax.annotation.CheckForNull;
/* JADX INFO: Access modifiers changed from: package-private */
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public abstract class Cut<C extends Comparable> implements Comparable<Cut<C>>, Serializable {
    private static final long serialVersionUID = 0;
    final C endpoint;

    /* JADX INFO: Access modifiers changed from: package-private */
    public Cut<C> canonical(DiscreteDomain<C> domain) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void describeAsLowerBound(StringBuilder sb);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void describeAsUpperBound(StringBuilder sb);

    /* JADX INFO: Access modifiers changed from: package-private */
    @CheckForNull
    public abstract C greatestValueBelow(DiscreteDomain<C> domain);

    public abstract int hashCode();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract boolean isLessThan(C value);

    /* JADX INFO: Access modifiers changed from: package-private */
    @CheckForNull
    public abstract C leastValueAbove(DiscreteDomain<C> domain);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract BoundType typeAsLowerBound();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract BoundType typeAsUpperBound();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract Cut<C> withLowerBoundType(BoundType boundType, DiscreteDomain<C> domain);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract Cut<C> withUpperBoundType(BoundType boundType, DiscreteDomain<C> domain);

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(Object that) {
        return compareTo((Cut) ((Cut) that));
    }

    Cut(C endpoint) {
        this.endpoint = endpoint;
    }

    public int compareTo(Cut<C> that) {
        if (that == belowAll()) {
            return 1;
        }
        if (that == aboveAll()) {
            return -1;
        }
        int compareOrThrow = Range.compareOrThrow(this.endpoint, that.endpoint);
        return compareOrThrow != 0 ? compareOrThrow : Booleans.compare(this instanceof AboveValue, that instanceof AboveValue);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public C endpoint() {
        return this.endpoint;
    }

    public boolean equals(@CheckForNull Object obj) {
        if (obj instanceof Cut) {
            try {
                return compareTo((Cut) ((Cut) obj)) == 0;
            } catch (ClassCastException unused) {
                return false;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <C extends Comparable> Cut<C> belowAll() {
        return BelowAll.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static final class BelowAll extends Cut<Comparable<?>> {
        private static final BelowAll INSTANCE = new BelowAll();
        private static final long serialVersionUID = 0;

        @Override // com.google.common.collect.Cut, java.lang.Comparable
        public int compareTo(Cut<Comparable<?>> o) {
            return o == this ? 0 : -1;
        }

        @Override // com.google.common.collect.Cut
        boolean isLessThan(Comparable<?> value) {
            return true;
        }

        public String toString() {
            return "-∞";
        }

        private BelowAll() {
            super("");
        }

        @Override // com.google.common.collect.Cut
        Comparable<?> endpoint() {
            throw new IllegalStateException("range unbounded on this side");
        }

        @Override // com.google.common.collect.Cut
        BoundType typeAsLowerBound() {
            throw new IllegalStateException();
        }

        @Override // com.google.common.collect.Cut
        BoundType typeAsUpperBound() {
            throw new AssertionError("this statement should be unreachable");
        }

        @Override // com.google.common.collect.Cut
        Cut<Comparable<?>> withLowerBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> domain) {
            throw new IllegalStateException();
        }

        @Override // com.google.common.collect.Cut
        Cut<Comparable<?>> withUpperBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> domain) {
            throw new AssertionError("this statement should be unreachable");
        }

        @Override // com.google.common.collect.Cut
        void describeAsLowerBound(StringBuilder sb) {
            sb.append("(-∞");
        }

        @Override // com.google.common.collect.Cut
        void describeAsUpperBound(StringBuilder sb) {
            throw new AssertionError();
        }

        @Override // com.google.common.collect.Cut
        Comparable<?> leastValueAbove(DiscreteDomain<Comparable<?>> domain) {
            return domain.minValue();
        }

        @Override // com.google.common.collect.Cut
        Comparable<?> greatestValueBelow(DiscreteDomain<Comparable<?>> domain) {
            throw new AssertionError();
        }

        @Override // com.google.common.collect.Cut
        Cut<Comparable<?>> canonical(DiscreteDomain<Comparable<?>> domain) {
            try {
                return Cut.belowValue(domain.minValue());
            } catch (NoSuchElementException unused) {
                return this;
            }
        }

        @Override // com.google.common.collect.Cut
        public int hashCode() {
            return System.identityHashCode(this);
        }

        private Object readResolve() {
            return INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <C extends Comparable> Cut<C> aboveAll() {
        return AboveAll.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static final class AboveAll extends Cut<Comparable<?>> {
        private static final AboveAll INSTANCE = new AboveAll();
        private static final long serialVersionUID = 0;

        @Override // com.google.common.collect.Cut, java.lang.Comparable
        public int compareTo(Cut<Comparable<?>> o) {
            return o == this ? 0 : 1;
        }

        @Override // com.google.common.collect.Cut
        boolean isLessThan(Comparable<?> value) {
            return false;
        }

        public String toString() {
            return "+∞";
        }

        private AboveAll() {
            super("");
        }

        @Override // com.google.common.collect.Cut
        Comparable<?> endpoint() {
            throw new IllegalStateException("range unbounded on this side");
        }

        @Override // com.google.common.collect.Cut
        BoundType typeAsLowerBound() {
            throw new AssertionError("this statement should be unreachable");
        }

        @Override // com.google.common.collect.Cut
        BoundType typeAsUpperBound() {
            throw new IllegalStateException();
        }

        @Override // com.google.common.collect.Cut
        Cut<Comparable<?>> withLowerBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> domain) {
            throw new AssertionError("this statement should be unreachable");
        }

        @Override // com.google.common.collect.Cut
        Cut<Comparable<?>> withUpperBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> domain) {
            throw new IllegalStateException();
        }

        @Override // com.google.common.collect.Cut
        void describeAsLowerBound(StringBuilder sb) {
            throw new AssertionError();
        }

        @Override // com.google.common.collect.Cut
        void describeAsUpperBound(StringBuilder sb) {
            sb.append("+∞)");
        }

        @Override // com.google.common.collect.Cut
        Comparable<?> leastValueAbove(DiscreteDomain<Comparable<?>> domain) {
            throw new AssertionError();
        }

        @Override // com.google.common.collect.Cut
        Comparable<?> greatestValueBelow(DiscreteDomain<Comparable<?>> domain) {
            return domain.maxValue();
        }

        @Override // com.google.common.collect.Cut
        public int hashCode() {
            return System.identityHashCode(this);
        }

        private Object readResolve() {
            return INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <C extends Comparable> Cut<C> belowValue(C endpoint) {
        return new BelowValue(endpoint);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static final class BelowValue<C extends Comparable> extends Cut<C> {
        private static final long serialVersionUID = 0;

        @Override // com.google.common.collect.Cut, java.lang.Comparable
        public /* bridge */ /* synthetic */ int compareTo(Object that) {
            return super.compareTo((Cut) ((Cut) that));
        }

        BelowValue(C endpoint) {
            super((Comparable) Preconditions.checkNotNull(endpoint));
        }

        @Override // com.google.common.collect.Cut
        boolean isLessThan(C value) {
            return Range.compareOrThrow(this.endpoint, value) <= 0;
        }

        @Override // com.google.common.collect.Cut
        BoundType typeAsLowerBound() {
            return BoundType.CLOSED;
        }

        @Override // com.google.common.collect.Cut
        BoundType typeAsUpperBound() {
            return BoundType.OPEN;
        }

        @Override // com.google.common.collect.Cut
        Cut<C> withLowerBoundType(BoundType boundType, DiscreteDomain<C> domain) {
            int i = AnonymousClass1.$SwitchMap$com$google$common$collect$BoundType[boundType.ordinal()];
            if (i != 1) {
                if (i == 2) {
                    C previous = domain.previous(this.endpoint);
                    return previous == null ? Cut.belowAll() : new AboveValue(previous);
                }
                throw new AssertionError();
            }
            return this;
        }

        @Override // com.google.common.collect.Cut
        Cut<C> withUpperBoundType(BoundType boundType, DiscreteDomain<C> domain) {
            int i = AnonymousClass1.$SwitchMap$com$google$common$collect$BoundType[boundType.ordinal()];
            if (i == 1) {
                C previous = domain.previous(this.endpoint);
                return previous == null ? Cut.aboveAll() : new AboveValue(previous);
            } else if (i == 2) {
                return this;
            } else {
                throw new AssertionError();
            }
        }

        @Override // com.google.common.collect.Cut
        void describeAsLowerBound(StringBuilder sb) {
            sb.append('[');
            sb.append(this.endpoint);
        }

        @Override // com.google.common.collect.Cut
        void describeAsUpperBound(StringBuilder sb) {
            sb.append(this.endpoint);
            sb.append(')');
        }

        @Override // com.google.common.collect.Cut
        C leastValueAbove(DiscreteDomain<C> domain) {
            return this.endpoint;
        }

        @Override // com.google.common.collect.Cut
        @CheckForNull
        C greatestValueBelow(DiscreteDomain<C> domain) {
            return domain.previous(this.endpoint);
        }

        @Override // com.google.common.collect.Cut
        public int hashCode() {
            return this.endpoint.hashCode();
        }

        public String toString() {
            return "\\" + this.endpoint + "/";
        }
    }

    /* renamed from: com.google.common.collect.Cut$1  reason: invalid class name */
    /* loaded from: classes3.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$common$collect$BoundType;

        static {
            int[] iArr = new int[BoundType.values().length];
            $SwitchMap$com$google$common$collect$BoundType = iArr;
            try {
                iArr[BoundType.CLOSED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$common$collect$BoundType[BoundType.OPEN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <C extends Comparable> Cut<C> aboveValue(C endpoint) {
        return new AboveValue(endpoint);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static final class AboveValue<C extends Comparable> extends Cut<C> {
        private static final long serialVersionUID = 0;

        @Override // com.google.common.collect.Cut, java.lang.Comparable
        public /* bridge */ /* synthetic */ int compareTo(Object that) {
            return super.compareTo((Cut) ((Cut) that));
        }

        AboveValue(C endpoint) {
            super((Comparable) Preconditions.checkNotNull(endpoint));
        }

        @Override // com.google.common.collect.Cut
        boolean isLessThan(C value) {
            return Range.compareOrThrow(this.endpoint, value) < 0;
        }

        @Override // com.google.common.collect.Cut
        BoundType typeAsLowerBound() {
            return BoundType.OPEN;
        }

        @Override // com.google.common.collect.Cut
        BoundType typeAsUpperBound() {
            return BoundType.CLOSED;
        }

        @Override // com.google.common.collect.Cut
        Cut<C> withLowerBoundType(BoundType boundType, DiscreteDomain<C> domain) {
            int i = AnonymousClass1.$SwitchMap$com$google$common$collect$BoundType[boundType.ordinal()];
            if (i == 1) {
                C next = domain.next(this.endpoint);
                return next == null ? Cut.belowAll() : belowValue(next);
            } else if (i == 2) {
                return this;
            } else {
                throw new AssertionError();
            }
        }

        @Override // com.google.common.collect.Cut
        Cut<C> withUpperBoundType(BoundType boundType, DiscreteDomain<C> domain) {
            int i = AnonymousClass1.$SwitchMap$com$google$common$collect$BoundType[boundType.ordinal()];
            if (i != 1) {
                if (i == 2) {
                    C next = domain.next(this.endpoint);
                    return next == null ? Cut.aboveAll() : belowValue(next);
                }
                throw new AssertionError();
            }
            return this;
        }

        @Override // com.google.common.collect.Cut
        void describeAsLowerBound(StringBuilder sb) {
            sb.append('(');
            sb.append(this.endpoint);
        }

        @Override // com.google.common.collect.Cut
        void describeAsUpperBound(StringBuilder sb) {
            sb.append(this.endpoint);
            sb.append(']');
        }

        @Override // com.google.common.collect.Cut
        @CheckForNull
        C leastValueAbove(DiscreteDomain<C> domain) {
            return domain.next(this.endpoint);
        }

        @Override // com.google.common.collect.Cut
        C greatestValueBelow(DiscreteDomain<C> domain) {
            return this.endpoint;
        }

        @Override // com.google.common.collect.Cut
        Cut<C> canonical(DiscreteDomain<C> domain) {
            C leastValueAbove = leastValueAbove(domain);
            return leastValueAbove != null ? belowValue(leastValueAbove) : Cut.aboveAll();
        }

        @Override // com.google.common.collect.Cut
        public int hashCode() {
            return ~this.endpoint.hashCode();
        }

        public String toString() {
            return "/" + this.endpoint + "\\";
        }
    }
}
