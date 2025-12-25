package j$.util.stream;

import j$.util.Spliterator;
import j$.util.concurrent.ConcurrentHashMap$SearchEntriesTask$$ExternalSyntheticBackportWithForwarding0;
import java.util.concurrent.atomic.AtomicReference;
/* loaded from: classes2.dex */
abstract class AbstractShortCircuitTask extends AbstractTask {
    protected volatile boolean canceled;
    protected final AtomicReference sharedResult;

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractShortCircuitTask(AbstractShortCircuitTask abstractShortCircuitTask, Spliterator spliterator) {
        super(abstractShortCircuitTask, spliterator);
        this.sharedResult = abstractShortCircuitTask.sharedResult;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractShortCircuitTask(PipelineHelper pipelineHelper, Spliterator spliterator) {
        super(pipelineHelper, spliterator);
        this.sharedResult = new AtomicReference(null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void cancel() {
        this.canceled = true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void cancelLaterNodes() {
        AbstractShortCircuitTask abstractShortCircuitTask = this;
        for (AbstractShortCircuitTask abstractShortCircuitTask2 = (AbstractShortCircuitTask) getParent(); abstractShortCircuitTask2 != null; abstractShortCircuitTask2 = (AbstractShortCircuitTask) abstractShortCircuitTask2.getParent()) {
            if (abstractShortCircuitTask2.leftChild == abstractShortCircuitTask) {
                AbstractShortCircuitTask abstractShortCircuitTask3 = (AbstractShortCircuitTask) abstractShortCircuitTask2.rightChild;
                if (!abstractShortCircuitTask3.canceled) {
                    abstractShortCircuitTask3.cancel();
                }
            }
            abstractShortCircuitTask = abstractShortCircuitTask2;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x004f, code lost:
        r8 = r7.doLeaf();
     */
    @Override // j$.util.stream.AbstractTask, java.util.concurrent.CountedCompleter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void compute() {
        /*
            r10 = this;
            j$.util.Spliterator r0 = r10.spliterator
            long r1 = r0.estimateSize()
            long r3 = r10.getTargetSize(r1)
            java.util.concurrent.atomic.AtomicReference r5 = r10.sharedResult
            r6 = 0
            r7 = r10
        Le:
            java.lang.Object r8 = r5.get()
            if (r8 != 0) goto L53
            boolean r8 = r7.taskCanceled()
            if (r8 == 0) goto L1f
            java.lang.Object r8 = r7.getEmptyResult()
            goto L53
        L1f:
            int r8 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r8 <= 0) goto L4f
            j$.util.Spliterator r1 = r0.trySplit()
            if (r1 != 0) goto L2a
            goto L4f
        L2a:
            j$.util.stream.AbstractTask r2 = r7.makeChild(r1)
            j$.util.stream.AbstractShortCircuitTask r2 = (j$.util.stream.AbstractShortCircuitTask) r2
            r7.leftChild = r2
            j$.util.stream.AbstractTask r8 = r7.makeChild(r0)
            j$.util.stream.AbstractShortCircuitTask r8 = (j$.util.stream.AbstractShortCircuitTask) r8
            r7.rightChild = r8
            r9 = 1
            r7.setPendingCount(r9)
            if (r6 == 0) goto L44
            r0 = r1
            r7 = r2
            r2 = r8
            goto L45
        L44:
            r7 = r8
        L45:
            r6 = r6 ^ 1
            r2.fork()
            long r1 = r0.estimateSize()
            goto Le
        L4f:
            java.lang.Object r8 = r7.doLeaf()
        L53:
            r7.setLocalResult(r8)
            r7.tryComplete()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.stream.AbstractShortCircuitTask.compute():void");
    }

    protected abstract Object getEmptyResult();

    @Override // j$.util.stream.AbstractTask
    public Object getLocalResult() {
        if (isRoot()) {
            Object obj = this.sharedResult.get();
            return obj == null ? getEmptyResult() : obj;
        }
        return super.getLocalResult();
    }

    @Override // j$.util.stream.AbstractTask, java.util.concurrent.CountedCompleter, java.util.concurrent.ForkJoinTask
    public Object getRawResult() {
        return getLocalResult();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // j$.util.stream.AbstractTask
    public void setLocalResult(Object obj) {
        if (!isRoot()) {
            super.setLocalResult(obj);
        } else if (obj != null) {
            ConcurrentHashMap$SearchEntriesTask$$ExternalSyntheticBackportWithForwarding0.m(this.sharedResult, null, obj);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void shortCircuit(Object obj) {
        if (obj != null) {
            ConcurrentHashMap$SearchEntriesTask$$ExternalSyntheticBackportWithForwarding0.m(this.sharedResult, null, obj);
        }
    }

    protected boolean taskCanceled() {
        boolean z = this.canceled;
        if (!z) {
            AbstractTask parent = getParent();
            while (true) {
                AbstractShortCircuitTask abstractShortCircuitTask = (AbstractShortCircuitTask) parent;
                if (z || abstractShortCircuitTask == null) {
                    break;
                }
                z = abstractShortCircuitTask.canceled;
                parent = abstractShortCircuitTask.getParent();
            }
        }
        return z;
    }
}
