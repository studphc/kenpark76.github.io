package j$.util.stream;

import j$.util.Spliterator;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ForkJoinPool;
/* loaded from: classes2.dex */
abstract class AbstractTask extends CountedCompleter {
    private static final int LEAF_TARGET = ForkJoinPool.getCommonPoolParallelism() << 2;
    protected final PipelineHelper helper;
    protected AbstractTask leftChild;
    private Object localResult;
    protected AbstractTask rightChild;
    protected Spliterator spliterator;
    protected long targetSize;

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractTask(AbstractTask abstractTask, Spliterator spliterator) {
        super(abstractTask);
        this.spliterator = spliterator;
        this.helper = abstractTask.helper;
        this.targetSize = abstractTask.targetSize;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractTask(PipelineHelper pipelineHelper, Spliterator spliterator) {
        super(null);
        this.helper = pipelineHelper;
        this.spliterator = spliterator;
        this.targetSize = 0L;
    }

    public static int getLeafTarget() {
        return LEAF_TARGET;
    }

    public static long suggestTargetSize(long j) {
        long leafTarget = j / getLeafTarget();
        if (leafTarget > 0) {
            return leafTarget;
        }
        return 1L;
    }

    @Override // java.util.concurrent.CountedCompleter
    public void compute() {
        Spliterator trySplit;
        Spliterator spliterator = this.spliterator;
        long estimateSize = spliterator.estimateSize();
        long targetSize = getTargetSize(estimateSize);
        boolean z = false;
        AbstractTask abstractTask = this;
        while (estimateSize > targetSize && (trySplit = spliterator.trySplit()) != null) {
            AbstractTask makeChild = abstractTask.makeChild(trySplit);
            abstractTask.leftChild = makeChild;
            AbstractTask makeChild2 = abstractTask.makeChild(spliterator);
            abstractTask.rightChild = makeChild2;
            abstractTask.setPendingCount(1);
            if (z) {
                spliterator = trySplit;
                abstractTask = makeChild;
                makeChild = makeChild2;
            } else {
                abstractTask = makeChild2;
            }
            z = !z;
            makeChild.fork();
            estimateSize = spliterator.estimateSize();
        }
        abstractTask.setLocalResult(abstractTask.doLeaf());
        abstractTask.tryComplete();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract Object doLeaf();

    /* JADX INFO: Access modifiers changed from: protected */
    public Object getLocalResult() {
        return this.localResult;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractTask getParent() {
        return (AbstractTask) getCompleter();
    }

    @Override // java.util.concurrent.CountedCompleter, java.util.concurrent.ForkJoinTask
    public Object getRawResult() {
        return this.localResult;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final long getTargetSize(long j) {
        long j2 = this.targetSize;
        if (j2 != 0) {
            return j2;
        }
        long suggestTargetSize = suggestTargetSize(j);
        this.targetSize = suggestTargetSize;
        return suggestTargetSize;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isLeaf() {
        return this.leftChild == null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isLeftmostNode() {
        AbstractTask abstractTask = this;
        while (abstractTask != null) {
            AbstractTask parent = abstractTask.getParent();
            if (parent != null && parent.leftChild != abstractTask) {
                return false;
            }
            abstractTask = parent;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isRoot() {
        return getParent() == null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract AbstractTask makeChild(Spliterator spliterator);

    @Override // java.util.concurrent.CountedCompleter
    public void onCompletion(CountedCompleter countedCompleter) {
        this.spliterator = null;
        this.rightChild = null;
        this.leftChild = null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setLocalResult(Object obj) {
        this.localResult = obj;
    }

    @Override // java.util.concurrent.CountedCompleter, java.util.concurrent.ForkJoinTask
    protected void setRawResult(Object obj) {
        if (obj != null) {
            throw new IllegalStateException();
        }
    }
}
