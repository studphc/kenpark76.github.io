package com.google.common.base;

import java.io.Serializable;
import javax.annotation.CheckForNull;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class FunctionalEquivalence<F, T> extends Equivalence<F> implements Serializable {
    private static final long serialVersionUID = 0;
    private final Function<? super F, ? extends T> function;
    private final Equivalence<T> resultEquivalence;

    /* JADX INFO: Access modifiers changed from: package-private */
    public FunctionalEquivalence(Function<? super F, ? extends T> function, Equivalence<T> resultEquivalence) {
        this.function = (Function) Preconditions.checkNotNull(function);
        this.resultEquivalence = (Equivalence) Preconditions.checkNotNull(resultEquivalence);
    }

    @Override // com.google.common.base.Equivalence
    protected boolean doEquivalent(F a, F b) {
        return this.resultEquivalence.equivalent(this.function.apply(a), this.function.apply(b));
    }

    @Override // com.google.common.base.Equivalence
    protected int doHash(F a) {
        return this.resultEquivalence.hash(this.function.apply(a));
    }

    public boolean equals(@CheckForNull Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof FunctionalEquivalence) {
            FunctionalEquivalence functionalEquivalence = (FunctionalEquivalence) obj;
            return this.function.equals(functionalEquivalence.function) && this.resultEquivalence.equals(functionalEquivalence.resultEquivalence);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hashCode(this.function, this.resultEquivalence);
    }

    public String toString() {
        return this.resultEquivalence + ".onResultOf(" + this.function + ")";
    }
}
