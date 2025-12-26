package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.annotation.CheckForNull;
/* JADX INFO: Access modifiers changed from: package-private */
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class TopKSelector<T> {
    private final T[] buffer;
    private int bufferSize;
    private final Comparator<? super T> comparator;
    private final int k;
    @CheckForNull
    private T threshold;

    public static <T extends Comparable<? super T>> TopKSelector<T> least(int k) {
        return least(k, Ordering.natural());
    }

    public static <T> TopKSelector<T> least(int k, Comparator<? super T> comparator) {
        return new TopKSelector<>(comparator, k);
    }

    public static <T extends Comparable<? super T>> TopKSelector<T> greatest(int k) {
        return greatest(k, Ordering.natural());
    }

    public static <T> TopKSelector<T> greatest(int k, Comparator<? super T> comparator) {
        return new TopKSelector<>(Ordering.from(comparator).reverse(), k);
    }

    private TopKSelector(Comparator<? super T> comparator, int k) {
        this.comparator = (Comparator) Preconditions.checkNotNull(comparator, "comparator");
        this.k = k;
        Preconditions.checkArgument(k >= 0, "k (%s) must be >= 0", k);
        Preconditions.checkArgument(k <= 1073741823, "k (%s) must be <= Integer.MAX_VALUE / 2", k);
        this.buffer = (T[]) new Object[IntMath.checkedMultiply(k, 2)];
        this.bufferSize = 0;
        this.threshold = null;
    }

    public void offer(@ParametricNullness T elem) {
        int i = this.k;
        if (i == 0) {
            return;
        }
        int i2 = this.bufferSize;
        if (i2 == 0) {
            this.buffer[0] = elem;
            this.threshold = elem;
            this.bufferSize = 1;
        } else if (i2 < i) {
            T[] tArr = this.buffer;
            this.bufferSize = i2 + 1;
            tArr[i2] = elem;
            if (this.comparator.compare(elem, (Object) NullnessCasts.uncheckedCastNullableTToT(this.threshold)) > 0) {
                this.threshold = elem;
            }
        } else if (this.comparator.compare(elem, (Object) NullnessCasts.uncheckedCastNullableTToT(this.threshold)) < 0) {
            T[] tArr2 = this.buffer;
            int i3 = this.bufferSize;
            int i4 = i3 + 1;
            this.bufferSize = i4;
            tArr2[i3] = elem;
            if (i4 == this.k * 2) {
                trim();
            }
        }
    }

    private void trim() {
        int i = (this.k * 2) - 1;
        int log2 = IntMath.log2(i + 0, RoundingMode.CEILING) * 3;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i2 < i) {
                int partition = partition(i2, i, ((i2 + i) + 1) >>> 1);
                int i5 = this.k;
                if (partition <= i5) {
                    if (partition >= i5) {
                        break;
                    }
                    i2 = Math.max(partition, i2 + 1);
                    i4 = partition;
                } else {
                    i = partition - 1;
                }
                i3++;
                if (i3 >= log2) {
                    Arrays.sort(this.buffer, i2, i + 1, this.comparator);
                    break;
                }
            } else {
                break;
            }
        }
        this.bufferSize = this.k;
        this.threshold = (T) NullnessCasts.uncheckedCastNullableTToT(this.buffer[i4]);
        while (true) {
            i4++;
            if (i4 >= this.k) {
                return;
            }
            if (this.comparator.compare((Object) NullnessCasts.uncheckedCastNullableTToT(this.buffer[i4]), (Object) NullnessCasts.uncheckedCastNullableTToT(this.threshold)) > 0) {
                this.threshold = this.buffer[i4];
            }
        }
    }

    private int partition(int left, int right, int pivotIndex) {
        Object uncheckedCastNullableTToT = NullnessCasts.uncheckedCastNullableTToT(this.buffer[pivotIndex]);
        T[] tArr = this.buffer;
        tArr[pivotIndex] = tArr[right];
        int i = left;
        while (left < right) {
            if (this.comparator.compare((Object) NullnessCasts.uncheckedCastNullableTToT(this.buffer[left]), uncheckedCastNullableTToT) < 0) {
                swap(i, left);
                i++;
            }
            left++;
        }
        T[] tArr2 = this.buffer;
        tArr2[right] = tArr2[i];
        tArr2[i] = uncheckedCastNullableTToT;
        return i;
    }

    private void swap(int i, int j) {
        T[] tArr = this.buffer;
        T t = tArr[i];
        tArr[i] = tArr[j];
        tArr[j] = t;
    }

    public void offerAll(Iterable<? extends T> elements) {
        offerAll(elements.iterator());
    }

    public void offerAll(Iterator<? extends T> elements) {
        while (elements.hasNext()) {
            offer(elements.next());
        }
    }

    public List<T> topK() {
        T[] tArr = this.buffer;
        Arrays.sort(tArr, 0, this.bufferSize, this.comparator);
        int i = this.bufferSize;
        int i2 = this.k;
        if (i > i2) {
            T[] tArr2 = this.buffer;
            Arrays.fill(tArr2, i2, tArr2.length, (Object) null);
            int i3 = this.k;
            this.bufferSize = i3;
            this.threshold = this.buffer[i3 - 1];
        }
        return Collections.unmodifiableList(Arrays.asList(Arrays.copyOf(tArr, this.bufferSize)));
    }
}
