package j$.util.stream;

import j$.util.Collection;
import j$.util.DesugarArrays;
import j$.util.Objects;
import j$.util.Spliterator;
import j$.util.Spliterators;
import j$.util.function.BiFunction$CC;
import j$.util.function.Consumer$CC;
import j$.util.function.DoubleConsumer$CC;
import j$.util.function.IntConsumer$CC;
import j$.util.function.LongConsumer$CC;
import j$.util.stream.Node;
import j$.util.stream.Nodes;
import j$.util.stream.Sink;
import j$.util.stream.SpinedBuffer;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Deque;
import java.util.concurrent.CountedCompleter;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public abstract class Nodes {
    private static final Node EMPTY_NODE = new EmptyNode.OfRef();
    private static final Node.OfInt EMPTY_INT_NODE = new EmptyNode.OfInt();
    private static final Node.OfLong EMPTY_LONG_NODE = new EmptyNode.OfLong();
    private static final Node.OfDouble EMPTY_DOUBLE_NODE = new EmptyNode.OfDouble();
    private static final int[] EMPTY_INT_ARRAY = new int[0];
    private static final long[] EMPTY_LONG_ARRAY = new long[0];
    private static final double[] EMPTY_DOUBLE_ARRAY = new double[0];

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: j$.util.stream.Nodes$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$java$util$stream$StreamShape;

        static {
            int[] iArr = new int[StreamShape.values().length];
            $SwitchMap$java$util$stream$StreamShape = iArr;
            try {
                iArr[StreamShape.REFERENCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$util$stream$StreamShape[StreamShape.INT_VALUE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$java$util$stream$StreamShape[StreamShape.LONG_VALUE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$java$util$stream$StreamShape[StreamShape.DOUBLE_VALUE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static abstract class AbstractConcNode implements Node {
        protected final Node left;
        protected final Node right;
        private final long size;

        AbstractConcNode(Node node, Node node2) {
            this.left = node;
            this.right = node2;
            this.size = node.count() + node2.count();
        }

        @Override // j$.util.stream.Node
        public long count() {
            return this.size;
        }

        @Override // j$.util.stream.Node
        public Node getChild(int i) {
            if (i == 0) {
                return this.left;
            }
            if (i == 1) {
                return this.right;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override // j$.util.stream.Node
        public int getChildCount() {
            return 2;
        }

        public /* synthetic */ StreamShape getShape() {
            StreamShape streamShape;
            streamShape = StreamShape.REFERENCE;
            return streamShape;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class ArrayNode implements Node {
        final Object[] array;
        int curSize;

        ArrayNode(long j, IntFunction intFunction) {
            if (j >= 2147483639) {
                throw new IllegalArgumentException("Stream size exceeds max array size");
            }
            this.array = (Object[]) intFunction.apply((int) j);
            this.curSize = 0;
        }

        ArrayNode(Object[] objArr) {
            this.array = objArr;
            this.curSize = objArr.length;
        }

        @Override // j$.util.stream.Node
        public Object[] asArray(IntFunction intFunction) {
            Object[] objArr = this.array;
            if (objArr.length == this.curSize) {
                return objArr;
            }
            throw new IllegalStateException();
        }

        @Override // j$.util.stream.Node
        public void copyInto(Object[] objArr, int i) {
            System.arraycopy(this.array, 0, objArr, i, this.curSize);
        }

        @Override // j$.util.stream.Node
        public long count() {
            return this.curSize;
        }

        @Override // j$.util.stream.Node
        public void forEach(Consumer consumer) {
            for (int i = 0; i < this.curSize; i++) {
                consumer.accept(this.array[i]);
            }
        }

        @Override // j$.util.stream.Node
        public /* synthetic */ Node getChild(int i) {
            return Node.CC.$default$getChild(this, i);
        }

        @Override // j$.util.stream.Node
        public /* synthetic */ int getChildCount() {
            return Node.CC.$default$getChildCount(this);
        }

        @Override // j$.util.stream.Node
        public Spliterator spliterator() {
            return DesugarArrays.spliterator(this.array, 0, this.curSize);
        }

        public String toString() {
            return String.format("ArrayNode[%d][%s]", Integer.valueOf(this.array.length - this.curSize), Arrays.toString(this.array));
        }

        @Override // j$.util.stream.Node
        public /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
            return Node.CC.$default$truncate(this, j, j2, intFunction);
        }
    }

    /* loaded from: classes2.dex */
    private static final class CollectionNode implements Node {
        private final Collection c;

        CollectionNode(Collection collection) {
            this.c = collection;
        }

        @Override // j$.util.stream.Node
        public Object[] asArray(IntFunction intFunction) {
            Collection collection = this.c;
            return collection.toArray((Object[]) intFunction.apply(collection.size()));
        }

        @Override // j$.util.stream.Node
        public void copyInto(Object[] objArr, int i) {
            for (Object obj : this.c) {
                objArr[i] = obj;
                i++;
            }
        }

        @Override // j$.util.stream.Node
        public long count() {
            return this.c.size();
        }

        @Override // j$.util.stream.Node
        public void forEach(Consumer consumer) {
            Collection.EL.forEach(this.c, consumer);
        }

        @Override // j$.util.stream.Node
        public /* synthetic */ Node getChild(int i) {
            return Node.CC.$default$getChild(this, i);
        }

        @Override // j$.util.stream.Node
        public /* synthetic */ int getChildCount() {
            return Node.CC.$default$getChildCount(this);
        }

        @Override // j$.util.stream.Node
        public Spliterator spliterator() {
            return Collection.EL.stream(this.c).spliterator();
        }

        public String toString() {
            return String.format("CollectionNode[%d][%s]", Integer.valueOf(this.c.size()), this.c);
        }

        @Override // j$.util.stream.Node
        public /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
            return Node.CC.$default$truncate(this, j, j2, intFunction);
        }
    }

    /* loaded from: classes2.dex */
    private static class CollectorTask extends AbstractTask {
        protected final LongFunction builderFactory;
        protected final BinaryOperator concFactory;
        protected final PipelineHelper helper;

        /* loaded from: classes2.dex */
        private static final class OfDouble extends CollectorTask {
            OfDouble(PipelineHelper pipelineHelper, Spliterator spliterator) {
                super(pipelineHelper, spliterator, new LongFunction() { // from class: j$.util.stream.Nodes$CollectorTask$OfDouble$$ExternalSyntheticLambda0
                    @Override // java.util.function.LongFunction
                    public final Object apply(long j) {
                        return Nodes.doubleBuilder(j);
                    }
                }, new BinaryOperator() { // from class: j$.util.stream.Nodes$CollectorTask$OfDouble$$ExternalSyntheticLambda1
                    public /* synthetic */ BiFunction andThen(Function function) {
                        return BiFunction$CC.$default$andThen(this, function);
                    }

                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj, Object obj2) {
                        return new Nodes.ConcNode.OfDouble((Node.OfDouble) obj, (Node.OfDouble) obj2);
                    }
                });
            }

            @Override // j$.util.stream.Nodes.CollectorTask, j$.util.stream.AbstractTask
            protected /* bridge */ /* synthetic */ Object doLeaf() {
                return super.doLeaf();
            }

            @Override // j$.util.stream.Nodes.CollectorTask, j$.util.stream.AbstractTask
            protected /* bridge */ /* synthetic */ AbstractTask makeChild(Spliterator spliterator) {
                return super.makeChild(spliterator);
            }
        }

        /* loaded from: classes2.dex */
        private static final class OfInt extends CollectorTask {
            OfInt(PipelineHelper pipelineHelper, Spliterator spliterator) {
                super(pipelineHelper, spliterator, new LongFunction() { // from class: j$.util.stream.Nodes$CollectorTask$OfInt$$ExternalSyntheticLambda0
                    @Override // java.util.function.LongFunction
                    public final Object apply(long j) {
                        return Nodes.intBuilder(j);
                    }
                }, new BinaryOperator() { // from class: j$.util.stream.Nodes$CollectorTask$OfInt$$ExternalSyntheticLambda1
                    public /* synthetic */ BiFunction andThen(Function function) {
                        return BiFunction$CC.$default$andThen(this, function);
                    }

                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj, Object obj2) {
                        return new Nodes.ConcNode.OfInt((Node.OfInt) obj, (Node.OfInt) obj2);
                    }
                });
            }

            @Override // j$.util.stream.Nodes.CollectorTask, j$.util.stream.AbstractTask
            protected /* bridge */ /* synthetic */ Object doLeaf() {
                return super.doLeaf();
            }

            @Override // j$.util.stream.Nodes.CollectorTask, j$.util.stream.AbstractTask
            protected /* bridge */ /* synthetic */ AbstractTask makeChild(Spliterator spliterator) {
                return super.makeChild(spliterator);
            }
        }

        /* loaded from: classes2.dex */
        private static final class OfLong extends CollectorTask {
            OfLong(PipelineHelper pipelineHelper, Spliterator spliterator) {
                super(pipelineHelper, spliterator, new LongFunction() { // from class: j$.util.stream.Nodes$CollectorTask$OfLong$$ExternalSyntheticLambda0
                    @Override // java.util.function.LongFunction
                    public final Object apply(long j) {
                        return Nodes.longBuilder(j);
                    }
                }, new BinaryOperator() { // from class: j$.util.stream.Nodes$CollectorTask$OfLong$$ExternalSyntheticLambda1
                    public /* synthetic */ BiFunction andThen(Function function) {
                        return BiFunction$CC.$default$andThen(this, function);
                    }

                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj, Object obj2) {
                        return new Nodes.ConcNode.OfLong((Node.OfLong) obj, (Node.OfLong) obj2);
                    }
                });
            }

            @Override // j$.util.stream.Nodes.CollectorTask, j$.util.stream.AbstractTask
            protected /* bridge */ /* synthetic */ Object doLeaf() {
                return super.doLeaf();
            }

            @Override // j$.util.stream.Nodes.CollectorTask, j$.util.stream.AbstractTask
            protected /* bridge */ /* synthetic */ AbstractTask makeChild(Spliterator spliterator) {
                return super.makeChild(spliterator);
            }
        }

        /* loaded from: classes2.dex */
        private static final class OfRef extends CollectorTask {
            OfRef(PipelineHelper pipelineHelper, final IntFunction intFunction, Spliterator spliterator) {
                super(pipelineHelper, spliterator, new LongFunction() { // from class: j$.util.stream.Nodes$CollectorTask$OfRef$$ExternalSyntheticLambda0
                    @Override // java.util.function.LongFunction
                    public final Object apply(long j) {
                        Node.Builder builder;
                        builder = Nodes.builder(j, IntFunction.this);
                        return builder;
                    }
                }, new BinaryOperator() { // from class: j$.util.stream.Nodes$CollectorTask$OfRef$$ExternalSyntheticLambda1
                    public /* synthetic */ BiFunction andThen(Function function) {
                        return BiFunction$CC.$default$andThen(this, function);
                    }

                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj, Object obj2) {
                        return new Nodes.ConcNode((Node) obj, (Node) obj2);
                    }
                });
            }

            @Override // j$.util.stream.Nodes.CollectorTask, j$.util.stream.AbstractTask
            protected /* bridge */ /* synthetic */ Object doLeaf() {
                return super.doLeaf();
            }

            @Override // j$.util.stream.Nodes.CollectorTask, j$.util.stream.AbstractTask
            protected /* bridge */ /* synthetic */ AbstractTask makeChild(Spliterator spliterator) {
                return super.makeChild(spliterator);
            }
        }

        CollectorTask(CollectorTask collectorTask, Spliterator spliterator) {
            super(collectorTask, spliterator);
            this.helper = collectorTask.helper;
            this.builderFactory = collectorTask.builderFactory;
            this.concFactory = collectorTask.concFactory;
        }

        CollectorTask(PipelineHelper pipelineHelper, Spliterator spliterator, LongFunction longFunction, BinaryOperator binaryOperator) {
            super(pipelineHelper, spliterator);
            this.helper = pipelineHelper;
            this.builderFactory = longFunction;
            this.concFactory = binaryOperator;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.AbstractTask
        public Node doLeaf() {
            return ((Node.Builder) this.helper.wrapAndCopyInto((Node.Builder) this.builderFactory.apply(this.helper.exactOutputSizeIfKnown(this.spliterator)), this.spliterator)).build();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.AbstractTask
        public CollectorTask makeChild(Spliterator spliterator) {
            return new CollectorTask(this, spliterator);
        }

        @Override // j$.util.stream.AbstractTask, java.util.concurrent.CountedCompleter
        public void onCompletion(CountedCompleter countedCompleter) {
            if (!isLeaf()) {
                setLocalResult((Node) this.concFactory.apply((Node) ((CollectorTask) this.leftChild).getLocalResult(), (Node) ((CollectorTask) this.rightChild).getLocalResult()));
            }
            super.onCompletion(countedCompleter);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static final class ConcNode extends AbstractConcNode {

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes2.dex */
        public static final class OfDouble extends OfPrimitive implements Node.OfDouble {
            /* JADX INFO: Access modifiers changed from: package-private */
            public OfDouble(Node.OfDouble ofDouble, Node.OfDouble ofDouble2) {
                super(ofDouble, ofDouble2);
            }

            @Override // j$.util.stream.Node.OfDouble
            public /* synthetic */ void copyInto(Double[] dArr, int i) {
                Node.OfDouble.CC.$default$copyInto((Node.OfDouble) this, dArr, i);
            }

            @Override // j$.util.stream.Node
            public /* bridge */ /* synthetic */ void copyInto(Object[] objArr, int i) {
                copyInto((Double[]) objArr, i);
            }

            @Override // j$.util.stream.Node
            public /* synthetic */ void forEach(Consumer consumer) {
                Node.OfDouble.CC.$default$forEach(this, consumer);
            }

            @Override // j$.util.stream.Node.OfPrimitive
            public /* bridge */ /* synthetic */ Object newArray(int i) {
                Object newArray;
                newArray = newArray(i);
                return newArray;
            }

            @Override // j$.util.stream.Node.OfPrimitive
            public /* synthetic */ double[] newArray(int i) {
                return Node.OfDouble.CC.m514$default$newArray((Node.OfDouble) this, i);
            }

            @Override // j$.util.stream.Node
            public Spliterator.OfDouble spliterator() {
                return new InternalNodeSpliterator.OfDouble(this);
            }

            @Override // j$.util.stream.Node
            public /* synthetic */ Node.OfDouble truncate(long j, long j2, IntFunction intFunction) {
                return Node.OfDouble.CC.$default$truncate((Node.OfDouble) this, j, j2, intFunction);
            }

            @Override // j$.util.stream.Node
            public /* bridge */ /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
                Node truncate;
                truncate = truncate(j, j2, intFunction);
                return truncate;
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes2.dex */
        public static final class OfInt extends OfPrimitive implements Node.OfInt {
            /* JADX INFO: Access modifiers changed from: package-private */
            public OfInt(Node.OfInt ofInt, Node.OfInt ofInt2) {
                super(ofInt, ofInt2);
            }

            @Override // j$.util.stream.Node.OfInt
            public /* synthetic */ void copyInto(Integer[] numArr, int i) {
                Node.OfInt.CC.$default$copyInto((Node.OfInt) this, numArr, i);
            }

            @Override // j$.util.stream.Node
            public /* bridge */ /* synthetic */ void copyInto(Object[] objArr, int i) {
                copyInto((Integer[]) objArr, i);
            }

            @Override // j$.util.stream.Node
            public /* synthetic */ void forEach(Consumer consumer) {
                Node.OfInt.CC.$default$forEach(this, consumer);
            }

            @Override // j$.util.stream.Node.OfPrimitive
            public /* bridge */ /* synthetic */ Object newArray(int i) {
                Object newArray;
                newArray = newArray(i);
                return newArray;
            }

            @Override // j$.util.stream.Node.OfPrimitive
            public /* synthetic */ int[] newArray(int i) {
                return Node.OfInt.CC.m516$default$newArray((Node.OfInt) this, i);
            }

            @Override // j$.util.stream.Node
            public Spliterator.OfInt spliterator() {
                return new InternalNodeSpliterator.OfInt(this);
            }

            @Override // j$.util.stream.Node
            public /* synthetic */ Node.OfInt truncate(long j, long j2, IntFunction intFunction) {
                return Node.OfInt.CC.$default$truncate((Node.OfInt) this, j, j2, intFunction);
            }

            @Override // j$.util.stream.Node
            public /* bridge */ /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
                Node truncate;
                truncate = truncate(j, j2, intFunction);
                return truncate;
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes2.dex */
        public static final class OfLong extends OfPrimitive implements Node.OfLong {
            /* JADX INFO: Access modifiers changed from: package-private */
            public OfLong(Node.OfLong ofLong, Node.OfLong ofLong2) {
                super(ofLong, ofLong2);
            }

            @Override // j$.util.stream.Node.OfLong
            public /* synthetic */ void copyInto(Long[] lArr, int i) {
                Node.OfLong.CC.$default$copyInto((Node.OfLong) this, lArr, i);
            }

            @Override // j$.util.stream.Node
            public /* bridge */ /* synthetic */ void copyInto(Object[] objArr, int i) {
                copyInto((Long[]) objArr, i);
            }

            @Override // j$.util.stream.Node
            public /* synthetic */ void forEach(Consumer consumer) {
                Node.OfLong.CC.$default$forEach(this, consumer);
            }

            @Override // j$.util.stream.Node.OfPrimitive
            public /* bridge */ /* synthetic */ Object newArray(int i) {
                Object newArray;
                newArray = newArray(i);
                return newArray;
            }

            @Override // j$.util.stream.Node.OfPrimitive
            public /* synthetic */ long[] newArray(int i) {
                return Node.OfLong.CC.m518$default$newArray((Node.OfLong) this, i);
            }

            @Override // j$.util.stream.Node
            public Spliterator.OfLong spliterator() {
                return new InternalNodeSpliterator.OfLong(this);
            }

            @Override // j$.util.stream.Node
            public /* synthetic */ Node.OfLong truncate(long j, long j2, IntFunction intFunction) {
                return Node.OfLong.CC.$default$truncate((Node.OfLong) this, j, j2, intFunction);
            }

            @Override // j$.util.stream.Node
            public /* bridge */ /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
                Node truncate;
                truncate = truncate(j, j2, intFunction);
                return truncate;
            }
        }

        /* loaded from: classes2.dex */
        private static abstract class OfPrimitive extends AbstractConcNode implements Node.OfPrimitive {
            OfPrimitive(Node.OfPrimitive ofPrimitive, Node.OfPrimitive ofPrimitive2) {
                super(ofPrimitive, ofPrimitive2);
            }

            @Override // j$.util.stream.Node
            public /* synthetic */ Object[] asArray(IntFunction intFunction) {
                return Node.OfPrimitive.CC.$default$asArray(this, intFunction);
            }

            @Override // j$.util.stream.Node.OfPrimitive
            public Object asPrimitiveArray() {
                long count = count();
                if (count < 2147483639) {
                    Object newArray = newArray((int) count);
                    copyInto(newArray, 0);
                    return newArray;
                }
                throw new IllegalArgumentException("Stream size exceeds max array size");
            }

            @Override // j$.util.stream.Node.OfPrimitive
            public void copyInto(Object obj, int i) {
                ((Node.OfPrimitive) this.left).copyInto(obj, i);
                ((Node.OfPrimitive) this.right).copyInto(obj, i + ((int) ((Node.OfPrimitive) this.left).count()));
            }

            @Override // j$.util.stream.Node.OfPrimitive
            public void forEach(Object obj) {
                ((Node.OfPrimitive) this.left).forEach(obj);
                ((Node.OfPrimitive) this.right).forEach(obj);
            }

            @Override // j$.util.stream.Nodes.AbstractConcNode, j$.util.stream.Node
            public /* bridge */ /* synthetic */ Node.OfPrimitive getChild(int i) {
                return (Node.OfPrimitive) super.getChild(i);
            }

            public String toString() {
                return count() < 32 ? String.format("%s[%s.%s]", getClass().getName(), this.left, this.right) : String.format("%s[size=%d]", getClass().getName(), Long.valueOf(count()));
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public ConcNode(Node node, Node node2) {
            super(node, node2);
        }

        @Override // j$.util.stream.Node
        public Object[] asArray(IntFunction intFunction) {
            long count = count();
            if (count < 2147483639) {
                Object[] objArr = (Object[]) intFunction.apply((int) count);
                copyInto(objArr, 0);
                return objArr;
            }
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }

        @Override // j$.util.stream.Node
        public void copyInto(Object[] objArr, int i) {
            Objects.requireNonNull(objArr);
            this.left.copyInto(objArr, i);
            this.right.copyInto(objArr, i + ((int) this.left.count()));
        }

        @Override // j$.util.stream.Node
        public void forEach(Consumer consumer) {
            this.left.forEach(consumer);
            this.right.forEach(consumer);
        }

        @Override // j$.util.stream.Node
        public Spliterator spliterator() {
            return new InternalNodeSpliterator.OfRef(this);
        }

        public String toString() {
            return count() < 32 ? String.format("ConcNode[%s.%s]", this.left, this.right) : String.format("ConcNode[size=%d]", Long.valueOf(count()));
        }

        @Override // j$.util.stream.Node
        public Node truncate(long j, long j2, IntFunction intFunction) {
            if (j == 0 && j2 == count()) {
                return this;
            }
            long count = this.left.count();
            return j >= count ? this.right.truncate(j - count, j2 - count, intFunction) : j2 <= count ? this.left.truncate(j, j2, intFunction) : Nodes.conc(getShape(), this.left.truncate(j, count, intFunction), this.right.truncate(0L, j2 - count, intFunction));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class DoubleArrayNode implements Node.OfDouble {
        final double[] array;
        int curSize;

        DoubleArrayNode(long j) {
            if (j >= 2147483639) {
                throw new IllegalArgumentException("Stream size exceeds max array size");
            }
            this.array = new double[(int) j];
            this.curSize = 0;
        }

        DoubleArrayNode(double[] dArr) {
            this.array = dArr;
            this.curSize = dArr.length;
        }

        @Override // j$.util.stream.Node
        public /* synthetic */ Object[] asArray(IntFunction intFunction) {
            return Node.OfPrimitive.CC.$default$asArray(this, intFunction);
        }

        @Override // j$.util.stream.Node.OfPrimitive
        public double[] asPrimitiveArray() {
            double[] dArr = this.array;
            int length = dArr.length;
            int i = this.curSize;
            return length == i ? dArr : Arrays.copyOf(dArr, i);
        }

        @Override // j$.util.stream.Node.OfPrimitive
        public void copyInto(double[] dArr, int i) {
            System.arraycopy(this.array, 0, dArr, i, this.curSize);
        }

        @Override // j$.util.stream.Node.OfDouble
        public /* synthetic */ void copyInto(Double[] dArr, int i) {
            Node.OfDouble.CC.$default$copyInto((Node.OfDouble) this, dArr, i);
        }

        @Override // j$.util.stream.Node
        public /* bridge */ /* synthetic */ void copyInto(Object[] objArr, int i) {
            copyInto((Double[]) objArr, i);
        }

        @Override // j$.util.stream.Node
        public long count() {
            return this.curSize;
        }

        @Override // j$.util.stream.Node
        public /* synthetic */ void forEach(Consumer consumer) {
            Node.OfDouble.CC.$default$forEach(this, consumer);
        }

        @Override // j$.util.stream.Node.OfPrimitive
        public void forEach(DoubleConsumer doubleConsumer) {
            for (int i = 0; i < this.curSize; i++) {
                doubleConsumer.accept(this.array[i]);
            }
        }

        @Override // j$.util.stream.Node.OfPrimitive, j$.util.stream.Node
        public /* synthetic */ Node.OfPrimitive getChild(int i) {
            return Node.OfPrimitive.CC.$default$getChild((Node.OfPrimitive) this, i);
        }

        @Override // j$.util.stream.Node
        public /* bridge */ /* synthetic */ Node getChild(int i) {
            Node child;
            child = getChild(i);
            return child;
        }

        @Override // j$.util.stream.Node
        public /* synthetic */ int getChildCount() {
            return Node.CC.$default$getChildCount(this);
        }

        @Override // j$.util.stream.Node.OfDouble, j$.util.stream.Node.OfPrimitive
        public /* synthetic */ double[] newArray(int i) {
            return Node.OfDouble.CC.m514$default$newArray((Node.OfDouble) this, i);
        }

        @Override // j$.util.stream.Node
        public Spliterator.OfDouble spliterator() {
            return DesugarArrays.spliterator(this.array, 0, this.curSize);
        }

        public String toString() {
            return String.format("DoubleArrayNode[%d][%s]", Integer.valueOf(this.array.length - this.curSize), Arrays.toString(this.array));
        }

        @Override // j$.util.stream.Node.OfDouble, j$.util.stream.Node
        public /* synthetic */ Node.OfDouble truncate(long j, long j2, IntFunction intFunction) {
            return Node.OfDouble.CC.$default$truncate((Node.OfDouble) this, j, j2, intFunction);
        }

        @Override // j$.util.stream.Node
        public /* bridge */ /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
            Node truncate;
            truncate = truncate(j, j2, intFunction);
            return truncate;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class DoubleFixedNodeBuilder extends DoubleArrayNode implements Node.Builder.OfDouble {
        DoubleFixedNodeBuilder(long j) {
            super(j);
        }

        @Override // j$.util.stream.Sink, java.util.function.DoubleConsumer
        public void accept(double d) {
            int i = this.curSize;
            double[] dArr = this.array;
            if (i >= dArr.length) {
                throw new IllegalStateException(String.format("Accept exceeded fixed size of %d", Integer.valueOf(this.array.length)));
            }
            this.curSize = i + 1;
            dArr[i] = d;
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void accept(int i) {
            Sink.CC.$default$accept((Sink) this, i);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void accept(long j) {
            Sink.CC.$default$accept((Sink) this, j);
        }

        @Override // j$.util.stream.Sink.OfDouble
        public /* synthetic */ void accept(Double d) {
            Sink.OfDouble.CC.$default$accept((Sink.OfDouble) this, d);
        }

        @Override // java.util.function.Consumer
        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfDouble.CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer$CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
            return DoubleConsumer$CC.$default$andThen(this, doubleConsumer);
        }

        @Override // j$.util.stream.Sink
        public void begin(long j) {
            if (j != this.array.length) {
                throw new IllegalStateException(String.format("Begin size %d is not equal to fixed size %d", Long.valueOf(j), Integer.valueOf(this.array.length)));
            }
            this.curSize = 0;
        }

        @Override // j$.util.stream.Node.Builder
        public Node.OfDouble build() {
            if (this.curSize >= this.array.length) {
                return this;
            }
            throw new IllegalStateException(String.format("Current size %d is less than fixed size %d", Integer.valueOf(this.curSize), Integer.valueOf(this.array.length)));
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ boolean cancellationRequested() {
            return Sink.CC.$default$cancellationRequested(this);
        }

        @Override // j$.util.stream.Sink
        public void end() {
            if (this.curSize < this.array.length) {
                throw new IllegalStateException(String.format("End size %d is less than fixed size %d", Integer.valueOf(this.curSize), Integer.valueOf(this.array.length)));
            }
        }

        @Override // j$.util.stream.Nodes.DoubleArrayNode
        public String toString() {
            return String.format("DoubleFixedNodeBuilder[%d][%s]", Integer.valueOf(this.array.length - this.curSize), Arrays.toString(this.array));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class DoubleSpinedNodeBuilder extends SpinedBuffer.OfDouble implements Node.OfDouble, Node.Builder.OfDouble {
        private boolean building = false;

        DoubleSpinedNodeBuilder() {
        }

        @Override // j$.util.stream.SpinedBuffer.OfDouble, java.util.function.DoubleConsumer
        public void accept(double d) {
            super.accept(d);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void accept(int i) {
            Sink.CC.$default$accept((Sink) this, i);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void accept(long j) {
            Sink.CC.$default$accept((Sink) this, j);
        }

        @Override // j$.util.stream.Sink.OfDouble
        public /* synthetic */ void accept(Double d) {
            Sink.OfDouble.CC.$default$accept((Sink.OfDouble) this, d);
        }

        @Override // java.util.function.Consumer
        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfDouble.CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer$CC.$default$andThen(this, consumer);
        }

        @Override // j$.util.stream.Node
        public /* synthetic */ Object[] asArray(IntFunction intFunction) {
            return Node.OfPrimitive.CC.$default$asArray(this, intFunction);
        }

        @Override // j$.util.stream.SpinedBuffer.OfPrimitive, j$.util.stream.Node.OfPrimitive
        public double[] asPrimitiveArray() {
            return (double[]) super.asPrimitiveArray();
        }

        @Override // j$.util.stream.Sink
        public void begin(long j) {
            this.building = true;
            clear();
            ensureCapacity(j);
        }

        @Override // j$.util.stream.Node.Builder
        public Node.OfDouble build() {
            return this;
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ boolean cancellationRequested() {
            return Sink.CC.$default$cancellationRequested(this);
        }

        @Override // j$.util.stream.SpinedBuffer.OfPrimitive, j$.util.stream.Node.OfPrimitive
        public void copyInto(double[] dArr, int i) {
            super.copyInto((Object) dArr, i);
        }

        @Override // j$.util.stream.Node.OfDouble
        public /* synthetic */ void copyInto(Double[] dArr, int i) {
            Node.OfDouble.CC.$default$copyInto((Node.OfDouble) this, dArr, i);
        }

        @Override // j$.util.stream.Node
        public /* bridge */ /* synthetic */ void copyInto(Object[] objArr, int i) {
            copyInto((Double[]) objArr, i);
        }

        @Override // j$.util.stream.Sink
        public void end() {
            this.building = false;
        }

        @Override // j$.util.stream.SpinedBuffer.OfPrimitive, j$.util.stream.Node.OfPrimitive
        public void forEach(DoubleConsumer doubleConsumer) {
            super.forEach((Object) doubleConsumer);
        }

        @Override // j$.util.stream.Node.OfPrimitive, j$.util.stream.Node
        public /* synthetic */ Node.OfPrimitive getChild(int i) {
            return Node.OfPrimitive.CC.$default$getChild((Node.OfPrimitive) this, i);
        }

        @Override // j$.util.stream.Node
        public /* bridge */ /* synthetic */ Node getChild(int i) {
            Node child;
            child = getChild(i);
            return child;
        }

        @Override // j$.util.stream.Node
        public /* synthetic */ int getChildCount() {
            return Node.CC.$default$getChildCount(this);
        }

        @Override // j$.util.stream.SpinedBuffer.OfDouble, j$.util.stream.SpinedBuffer.OfPrimitive, java.lang.Iterable, j$.util.stream.Node.OfPrimitive, j$.util.stream.Node
        public Spliterator.OfDouble spliterator() {
            return super.spliterator();
        }

        @Override // j$.util.stream.Node.OfDouble, j$.util.stream.Node
        public /* synthetic */ Node.OfDouble truncate(long j, long j2, IntFunction intFunction) {
            return Node.OfDouble.CC.$default$truncate((Node.OfDouble) this, j, j2, intFunction);
        }

        @Override // j$.util.stream.Node
        public /* bridge */ /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
            Node truncate;
            truncate = truncate(j, j2, intFunction);
            return truncate;
        }
    }

    /* loaded from: classes2.dex */
    private static abstract class EmptyNode implements Node {

        /* loaded from: classes2.dex */
        private static final class OfDouble extends EmptyNode implements Node.OfDouble {
            OfDouble() {
            }

            @Override // j$.util.stream.Node.OfPrimitive
            public double[] asPrimitiveArray() {
                return Nodes.EMPTY_DOUBLE_ARRAY;
            }

            @Override // j$.util.stream.Node.OfDouble
            public /* synthetic */ void copyInto(Double[] dArr, int i) {
                Node.OfDouble.CC.$default$copyInto((Node.OfDouble) this, dArr, i);
            }

            @Override // j$.util.stream.Node
            public /* bridge */ /* synthetic */ void copyInto(Object[] objArr, int i) {
                copyInto((Double[]) objArr, i);
            }

            @Override // j$.util.stream.Node
            public /* synthetic */ void forEach(Consumer consumer) {
                Node.OfDouble.CC.$default$forEach(this, consumer);
            }

            @Override // j$.util.stream.Nodes.EmptyNode, j$.util.stream.Node
            public /* synthetic */ Node.OfPrimitive getChild(int i) {
                return Node.OfPrimitive.CC.$default$getChild((Node.OfPrimitive) this, i);
            }

            @Override // j$.util.stream.Nodes.EmptyNode, j$.util.stream.Node
            public /* bridge */ /* synthetic */ Node getChild(int i) {
                Node child;
                child = getChild(i);
                return child;
            }

            @Override // j$.util.stream.Node.OfDouble, j$.util.stream.Node.OfPrimitive
            public /* synthetic */ double[] newArray(int i) {
                return Node.OfDouble.CC.m514$default$newArray((Node.OfDouble) this, i);
            }

            @Override // j$.util.stream.Node
            public Spliterator.OfDouble spliterator() {
                return Spliterators.emptyDoubleSpliterator();
            }

            @Override // j$.util.stream.Nodes.EmptyNode, j$.util.stream.Node
            public /* synthetic */ Node.OfDouble truncate(long j, long j2, IntFunction intFunction) {
                return Node.OfDouble.CC.$default$truncate((Node.OfDouble) this, j, j2, intFunction);
            }

            @Override // j$.util.stream.Nodes.EmptyNode, j$.util.stream.Node
            public /* bridge */ /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
                Node truncate;
                truncate = truncate(j, j2, intFunction);
                return truncate;
            }
        }

        /* loaded from: classes2.dex */
        private static final class OfInt extends EmptyNode implements Node.OfInt {
            OfInt() {
            }

            @Override // j$.util.stream.Node.OfPrimitive
            public int[] asPrimitiveArray() {
                return Nodes.EMPTY_INT_ARRAY;
            }

            @Override // j$.util.stream.Node.OfInt
            public /* synthetic */ void copyInto(Integer[] numArr, int i) {
                Node.OfInt.CC.$default$copyInto((Node.OfInt) this, numArr, i);
            }

            @Override // j$.util.stream.Node
            public /* bridge */ /* synthetic */ void copyInto(Object[] objArr, int i) {
                copyInto((Integer[]) objArr, i);
            }

            @Override // j$.util.stream.Node
            public /* synthetic */ void forEach(Consumer consumer) {
                Node.OfInt.CC.$default$forEach(this, consumer);
            }

            @Override // j$.util.stream.Nodes.EmptyNode, j$.util.stream.Node
            public /* synthetic */ Node.OfPrimitive getChild(int i) {
                return Node.OfPrimitive.CC.$default$getChild((Node.OfPrimitive) this, i);
            }

            @Override // j$.util.stream.Nodes.EmptyNode, j$.util.stream.Node
            public /* bridge */ /* synthetic */ Node getChild(int i) {
                Node child;
                child = getChild(i);
                return child;
            }

            @Override // j$.util.stream.Node.OfInt, j$.util.stream.Node.OfPrimitive
            public /* synthetic */ int[] newArray(int i) {
                return Node.OfInt.CC.m516$default$newArray((Node.OfInt) this, i);
            }

            @Override // j$.util.stream.Node
            public Spliterator.OfInt spliterator() {
                return Spliterators.emptyIntSpliterator();
            }

            @Override // j$.util.stream.Nodes.EmptyNode, j$.util.stream.Node
            public /* synthetic */ Node.OfInt truncate(long j, long j2, IntFunction intFunction) {
                return Node.OfInt.CC.$default$truncate((Node.OfInt) this, j, j2, intFunction);
            }

            @Override // j$.util.stream.Nodes.EmptyNode, j$.util.stream.Node
            public /* bridge */ /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
                Node truncate;
                truncate = truncate(j, j2, intFunction);
                return truncate;
            }
        }

        /* loaded from: classes2.dex */
        private static final class OfLong extends EmptyNode implements Node.OfLong {
            OfLong() {
            }

            @Override // j$.util.stream.Node.OfPrimitive
            public long[] asPrimitiveArray() {
                return Nodes.EMPTY_LONG_ARRAY;
            }

            @Override // j$.util.stream.Node.OfLong
            public /* synthetic */ void copyInto(Long[] lArr, int i) {
                Node.OfLong.CC.$default$copyInto((Node.OfLong) this, lArr, i);
            }

            @Override // j$.util.stream.Node
            public /* bridge */ /* synthetic */ void copyInto(Object[] objArr, int i) {
                copyInto((Long[]) objArr, i);
            }

            @Override // j$.util.stream.Node
            public /* synthetic */ void forEach(Consumer consumer) {
                Node.OfLong.CC.$default$forEach(this, consumer);
            }

            @Override // j$.util.stream.Nodes.EmptyNode, j$.util.stream.Node
            public /* synthetic */ Node.OfPrimitive getChild(int i) {
                return Node.OfPrimitive.CC.$default$getChild((Node.OfPrimitive) this, i);
            }

            @Override // j$.util.stream.Nodes.EmptyNode, j$.util.stream.Node
            public /* bridge */ /* synthetic */ Node getChild(int i) {
                Node child;
                child = getChild(i);
                return child;
            }

            @Override // j$.util.stream.Node.OfLong, j$.util.stream.Node.OfPrimitive
            public /* synthetic */ long[] newArray(int i) {
                return Node.OfLong.CC.m518$default$newArray((Node.OfLong) this, i);
            }

            @Override // j$.util.stream.Node
            public Spliterator.OfLong spliterator() {
                return Spliterators.emptyLongSpliterator();
            }

            @Override // j$.util.stream.Nodes.EmptyNode, j$.util.stream.Node
            public /* synthetic */ Node.OfLong truncate(long j, long j2, IntFunction intFunction) {
                return Node.OfLong.CC.$default$truncate((Node.OfLong) this, j, j2, intFunction);
            }

            @Override // j$.util.stream.Nodes.EmptyNode, j$.util.stream.Node
            public /* bridge */ /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
                Node truncate;
                truncate = truncate(j, j2, intFunction);
                return truncate;
            }
        }

        /* loaded from: classes2.dex */
        private static class OfRef extends EmptyNode {
            private OfRef() {
            }

            @Override // j$.util.stream.Node
            public /* bridge */ /* synthetic */ void copyInto(Object[] objArr, int i) {
                super.copyInto((Object) objArr, i);
            }

            @Override // j$.util.stream.Node
            public /* bridge */ /* synthetic */ void forEach(Consumer consumer) {
                super.forEach((Object) consumer);
            }

            @Override // j$.util.stream.Node
            public Spliterator spliterator() {
                return Spliterators.emptySpliterator();
            }
        }

        EmptyNode() {
        }

        @Override // j$.util.stream.Node
        public Object[] asArray(IntFunction intFunction) {
            return (Object[]) intFunction.apply(0);
        }

        public void copyInto(Object obj, int i) {
        }

        @Override // j$.util.stream.Node
        public long count() {
            return 0L;
        }

        public void forEach(Object obj) {
        }

        @Override // j$.util.stream.Node
        public /* synthetic */ Node getChild(int i) {
            return Node.CC.$default$getChild(this, i);
        }

        @Override // j$.util.stream.Node
        public /* synthetic */ int getChildCount() {
            return Node.CC.$default$getChildCount(this);
        }

        @Override // j$.util.stream.Node
        public /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
            return Node.CC.$default$truncate(this, j, j2, intFunction);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class FixedNodeBuilder extends ArrayNode implements Node.Builder {
        FixedNodeBuilder(long j, IntFunction intFunction) {
            super(j, intFunction);
        }

        @Override // j$.util.stream.Sink, java.util.function.DoubleConsumer
        public /* synthetic */ void accept(double d) {
            Sink.CC.$default$accept(this, d);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void accept(int i) {
            Sink.CC.$default$accept((Sink) this, i);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void accept(long j) {
            Sink.CC.$default$accept((Sink) this, j);
        }

        @Override // java.util.function.Consumer
        public void accept(Object obj) {
            int i = this.curSize;
            Object[] objArr = this.array;
            if (i >= objArr.length) {
                throw new IllegalStateException(String.format("Accept exceeded fixed size of %d", Integer.valueOf(this.array.length)));
            }
            this.curSize = i + 1;
            objArr[i] = obj;
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer$CC.$default$andThen(this, consumer);
        }

        @Override // j$.util.stream.Sink
        public void begin(long j) {
            if (j != this.array.length) {
                throw new IllegalStateException(String.format("Begin size %d is not equal to fixed size %d", Long.valueOf(j), Integer.valueOf(this.array.length)));
            }
            this.curSize = 0;
        }

        @Override // j$.util.stream.Node.Builder
        public Node build() {
            if (this.curSize >= this.array.length) {
                return this;
            }
            throw new IllegalStateException(String.format("Current size %d is less than fixed size %d", Integer.valueOf(this.curSize), Integer.valueOf(this.array.length)));
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ boolean cancellationRequested() {
            return Sink.CC.$default$cancellationRequested(this);
        }

        @Override // j$.util.stream.Sink
        public void end() {
            if (this.curSize < this.array.length) {
                throw new IllegalStateException(String.format("End size %d is less than fixed size %d", Integer.valueOf(this.curSize), Integer.valueOf(this.array.length)));
            }
        }

        @Override // j$.util.stream.Nodes.ArrayNode
        public String toString() {
            return String.format("FixedNodeBuilder[%d][%s]", Integer.valueOf(this.array.length - this.curSize), Arrays.toString(this.array));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class IntArrayNode implements Node.OfInt {
        final int[] array;
        int curSize;

        IntArrayNode(long j) {
            if (j >= 2147483639) {
                throw new IllegalArgumentException("Stream size exceeds max array size");
            }
            this.array = new int[(int) j];
            this.curSize = 0;
        }

        IntArrayNode(int[] iArr) {
            this.array = iArr;
            this.curSize = iArr.length;
        }

        @Override // j$.util.stream.Node
        public /* synthetic */ Object[] asArray(IntFunction intFunction) {
            return Node.OfPrimitive.CC.$default$asArray(this, intFunction);
        }

        @Override // j$.util.stream.Node.OfPrimitive
        public int[] asPrimitiveArray() {
            int[] iArr = this.array;
            int length = iArr.length;
            int i = this.curSize;
            return length == i ? iArr : Arrays.copyOf(iArr, i);
        }

        @Override // j$.util.stream.Node.OfPrimitive
        public void copyInto(int[] iArr, int i) {
            System.arraycopy(this.array, 0, iArr, i, this.curSize);
        }

        @Override // j$.util.stream.Node.OfInt
        public /* synthetic */ void copyInto(Integer[] numArr, int i) {
            Node.OfInt.CC.$default$copyInto((Node.OfInt) this, numArr, i);
        }

        @Override // j$.util.stream.Node
        public /* bridge */ /* synthetic */ void copyInto(Object[] objArr, int i) {
            copyInto((Integer[]) objArr, i);
        }

        @Override // j$.util.stream.Node
        public long count() {
            return this.curSize;
        }

        @Override // j$.util.stream.Node
        public /* synthetic */ void forEach(Consumer consumer) {
            Node.OfInt.CC.$default$forEach(this, consumer);
        }

        @Override // j$.util.stream.Node.OfPrimitive
        public void forEach(IntConsumer intConsumer) {
            for (int i = 0; i < this.curSize; i++) {
                intConsumer.accept(this.array[i]);
            }
        }

        @Override // j$.util.stream.Node.OfPrimitive, j$.util.stream.Node
        public /* synthetic */ Node.OfPrimitive getChild(int i) {
            return Node.OfPrimitive.CC.$default$getChild((Node.OfPrimitive) this, i);
        }

        @Override // j$.util.stream.Node
        public /* bridge */ /* synthetic */ Node getChild(int i) {
            Node child;
            child = getChild(i);
            return child;
        }

        @Override // j$.util.stream.Node
        public /* synthetic */ int getChildCount() {
            return Node.CC.$default$getChildCount(this);
        }

        @Override // j$.util.stream.Node.OfInt, j$.util.stream.Node.OfPrimitive
        public /* synthetic */ int[] newArray(int i) {
            return Node.OfInt.CC.m516$default$newArray((Node.OfInt) this, i);
        }

        @Override // j$.util.stream.Node
        public Spliterator.OfInt spliterator() {
            return DesugarArrays.spliterator(this.array, 0, this.curSize);
        }

        public String toString() {
            return String.format("IntArrayNode[%d][%s]", Integer.valueOf(this.array.length - this.curSize), Arrays.toString(this.array));
        }

        @Override // j$.util.stream.Node.OfInt, j$.util.stream.Node
        public /* synthetic */ Node.OfInt truncate(long j, long j2, IntFunction intFunction) {
            return Node.OfInt.CC.$default$truncate((Node.OfInt) this, j, j2, intFunction);
        }

        @Override // j$.util.stream.Node
        public /* bridge */ /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
            Node truncate;
            truncate = truncate(j, j2, intFunction);
            return truncate;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class IntFixedNodeBuilder extends IntArrayNode implements Node.Builder.OfInt {
        IntFixedNodeBuilder(long j) {
            super(j);
        }

        @Override // j$.util.stream.Sink, java.util.function.DoubleConsumer
        public /* synthetic */ void accept(double d) {
            Sink.CC.$default$accept(this, d);
        }

        @Override // j$.util.stream.Sink
        public void accept(int i) {
            int i2 = this.curSize;
            int[] iArr = this.array;
            if (i2 >= iArr.length) {
                throw new IllegalStateException(String.format("Accept exceeded fixed size of %d", Integer.valueOf(this.array.length)));
            }
            this.curSize = i2 + 1;
            iArr[i2] = i;
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void accept(long j) {
            Sink.CC.$default$accept((Sink) this, j);
        }

        @Override // j$.util.stream.Sink.OfInt
        public /* synthetic */ void accept(Integer num) {
            Sink.OfInt.CC.$default$accept((Sink.OfInt) this, num);
        }

        @Override // java.util.function.Consumer
        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfInt.CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer$CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
            return IntConsumer$CC.$default$andThen(this, intConsumer);
        }

        @Override // j$.util.stream.Sink
        public void begin(long j) {
            if (j != this.array.length) {
                throw new IllegalStateException(String.format("Begin size %d is not equal to fixed size %d", Long.valueOf(j), Integer.valueOf(this.array.length)));
            }
            this.curSize = 0;
        }

        @Override // j$.util.stream.Node.Builder
        public Node.OfInt build() {
            if (this.curSize >= this.array.length) {
                return this;
            }
            throw new IllegalStateException(String.format("Current size %d is less than fixed size %d", Integer.valueOf(this.curSize), Integer.valueOf(this.array.length)));
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ boolean cancellationRequested() {
            return Sink.CC.$default$cancellationRequested(this);
        }

        @Override // j$.util.stream.Sink
        public void end() {
            if (this.curSize < this.array.length) {
                throw new IllegalStateException(String.format("End size %d is less than fixed size %d", Integer.valueOf(this.curSize), Integer.valueOf(this.array.length)));
            }
        }

        @Override // j$.util.stream.Nodes.IntArrayNode
        public String toString() {
            return String.format("IntFixedNodeBuilder[%d][%s]", Integer.valueOf(this.array.length - this.curSize), Arrays.toString(this.array));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class IntSpinedNodeBuilder extends SpinedBuffer.OfInt implements Node.OfInt, Node.Builder.OfInt {
        private boolean building = false;

        IntSpinedNodeBuilder() {
        }

        @Override // j$.util.stream.Sink, java.util.function.DoubleConsumer
        public /* synthetic */ void accept(double d) {
            Sink.CC.$default$accept(this, d);
        }

        @Override // j$.util.stream.SpinedBuffer.OfInt, java.util.function.IntConsumer
        public void accept(int i) {
            super.accept(i);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void accept(long j) {
            Sink.CC.$default$accept((Sink) this, j);
        }

        @Override // j$.util.stream.Sink.OfInt
        public /* synthetic */ void accept(Integer num) {
            Sink.OfInt.CC.$default$accept((Sink.OfInt) this, num);
        }

        @Override // java.util.function.Consumer
        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfInt.CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer$CC.$default$andThen(this, consumer);
        }

        @Override // j$.util.stream.Node
        public /* synthetic */ Object[] asArray(IntFunction intFunction) {
            return Node.OfPrimitive.CC.$default$asArray(this, intFunction);
        }

        @Override // j$.util.stream.SpinedBuffer.OfPrimitive, j$.util.stream.Node.OfPrimitive
        public int[] asPrimitiveArray() {
            return (int[]) super.asPrimitiveArray();
        }

        @Override // j$.util.stream.Sink
        public void begin(long j) {
            this.building = true;
            clear();
            ensureCapacity(j);
        }

        @Override // j$.util.stream.Node.Builder
        public Node.OfInt build() {
            return this;
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ boolean cancellationRequested() {
            return Sink.CC.$default$cancellationRequested(this);
        }

        @Override // j$.util.stream.SpinedBuffer.OfPrimitive, j$.util.stream.Node.OfPrimitive
        public void copyInto(int[] iArr, int i) {
            super.copyInto((Object) iArr, i);
        }

        @Override // j$.util.stream.Node.OfInt
        public /* synthetic */ void copyInto(Integer[] numArr, int i) {
            Node.OfInt.CC.$default$copyInto((Node.OfInt) this, numArr, i);
        }

        @Override // j$.util.stream.Node
        public /* bridge */ /* synthetic */ void copyInto(Object[] objArr, int i) {
            copyInto((Integer[]) objArr, i);
        }

        @Override // j$.util.stream.Sink
        public void end() {
            this.building = false;
        }

        @Override // j$.util.stream.SpinedBuffer.OfPrimitive, j$.util.stream.Node.OfPrimitive
        public void forEach(IntConsumer intConsumer) {
            super.forEach((Object) intConsumer);
        }

        @Override // j$.util.stream.Node.OfPrimitive, j$.util.stream.Node
        public /* synthetic */ Node.OfPrimitive getChild(int i) {
            return Node.OfPrimitive.CC.$default$getChild((Node.OfPrimitive) this, i);
        }

        @Override // j$.util.stream.Node
        public /* bridge */ /* synthetic */ Node getChild(int i) {
            Node child;
            child = getChild(i);
            return child;
        }

        @Override // j$.util.stream.Node
        public /* synthetic */ int getChildCount() {
            return Node.CC.$default$getChildCount(this);
        }

        @Override // j$.util.stream.SpinedBuffer.OfInt, j$.util.stream.SpinedBuffer.OfPrimitive, java.lang.Iterable, j$.util.stream.Node.OfPrimitive, j$.util.stream.Node
        public Spliterator.OfInt spliterator() {
            return super.spliterator();
        }

        @Override // j$.util.stream.Node.OfInt, j$.util.stream.Node
        public /* synthetic */ Node.OfInt truncate(long j, long j2, IntFunction intFunction) {
            return Node.OfInt.CC.$default$truncate((Node.OfInt) this, j, j2, intFunction);
        }

        @Override // j$.util.stream.Node
        public /* bridge */ /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
            Node truncate;
            truncate = truncate(j, j2, intFunction);
            return truncate;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static abstract class InternalNodeSpliterator implements Spliterator {
        int curChildIndex;
        Node curNode;
        Spliterator lastNodeSpliterator;
        Spliterator tryAdvanceSpliterator;
        Deque tryAdvanceStack;

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes2.dex */
        public static final class OfDouble extends OfPrimitive implements Spliterator.OfDouble {
            OfDouble(Node.OfDouble ofDouble) {
                super(ofDouble);
            }

            @Override // j$.util.Spliterator
            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                Spliterator.OfDouble.CC.$default$forEachRemaining(this, consumer);
            }

            @Override // j$.util.Spliterator.OfDouble
            public /* bridge */ /* synthetic */ void forEachRemaining(DoubleConsumer doubleConsumer) {
                super.forEachRemaining((Object) doubleConsumer);
            }

            @Override // j$.util.Spliterator
            public /* synthetic */ boolean tryAdvance(Consumer consumer) {
                return Spliterator.OfDouble.CC.$default$tryAdvance(this, consumer);
            }

            @Override // j$.util.Spliterator.OfDouble
            public /* bridge */ /* synthetic */ boolean tryAdvance(DoubleConsumer doubleConsumer) {
                return super.tryAdvance((Object) doubleConsumer);
            }

            @Override // j$.util.stream.Nodes.InternalNodeSpliterator.OfPrimitive, j$.util.stream.Nodes.InternalNodeSpliterator, j$.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfDouble trySplit() {
                return (Spliterator.OfDouble) super.trySplit();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes2.dex */
        public static final class OfInt extends OfPrimitive implements Spliterator.OfInt {
            OfInt(Node.OfInt ofInt) {
                super(ofInt);
            }

            @Override // j$.util.Spliterator
            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                Spliterator.OfInt.CC.$default$forEachRemaining(this, consumer);
            }

            @Override // j$.util.Spliterator.OfInt
            public /* bridge */ /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
                super.forEachRemaining((Object) intConsumer);
            }

            @Override // j$.util.Spliterator
            public /* synthetic */ boolean tryAdvance(Consumer consumer) {
                return Spliterator.OfInt.CC.$default$tryAdvance(this, consumer);
            }

            @Override // j$.util.Spliterator.OfInt
            public /* bridge */ /* synthetic */ boolean tryAdvance(IntConsumer intConsumer) {
                return super.tryAdvance((Object) intConsumer);
            }

            @Override // j$.util.stream.Nodes.InternalNodeSpliterator.OfPrimitive, j$.util.stream.Nodes.InternalNodeSpliterator, j$.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfInt trySplit() {
                return (Spliterator.OfInt) super.trySplit();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes2.dex */
        public static final class OfLong extends OfPrimitive implements Spliterator.OfLong {
            OfLong(Node.OfLong ofLong) {
                super(ofLong);
            }

            @Override // j$.util.Spliterator
            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                Spliterator.OfLong.CC.$default$forEachRemaining(this, consumer);
            }

            @Override // j$.util.Spliterator.OfLong
            public /* bridge */ /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
                super.forEachRemaining((Object) longConsumer);
            }

            @Override // j$.util.Spliterator
            public /* synthetic */ boolean tryAdvance(Consumer consumer) {
                return Spliterator.OfLong.CC.$default$tryAdvance(this, consumer);
            }

            @Override // j$.util.Spliterator.OfLong
            public /* bridge */ /* synthetic */ boolean tryAdvance(LongConsumer longConsumer) {
                return super.tryAdvance((Object) longConsumer);
            }

            @Override // j$.util.stream.Nodes.InternalNodeSpliterator.OfPrimitive, j$.util.stream.Nodes.InternalNodeSpliterator, j$.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfLong trySplit() {
                return (Spliterator.OfLong) super.trySplit();
            }
        }

        /* loaded from: classes2.dex */
        private static abstract class OfPrimitive extends InternalNodeSpliterator implements Spliterator.OfPrimitive {
            OfPrimitive(Node.OfPrimitive ofPrimitive) {
                super(ofPrimitive);
            }

            @Override // j$.util.Spliterator.OfPrimitive
            public void forEachRemaining(Object obj) {
                if (this.curNode == null) {
                    return;
                }
                if (this.tryAdvanceSpliterator != null) {
                    do {
                    } while (tryAdvance(obj));
                    return;
                }
                Spliterator spliterator = this.lastNodeSpliterator;
                if (spliterator != null) {
                    ((Spliterator.OfPrimitive) spliterator).forEachRemaining(obj);
                    return;
                }
                Deque initStack = initStack();
                while (true) {
                    Node.OfPrimitive ofPrimitive = (Node.OfPrimitive) findNextLeafNode(initStack);
                    if (ofPrimitive == null) {
                        this.curNode = null;
                        return;
                    }
                    ofPrimitive.forEach(obj);
                }
            }

            @Override // j$.util.Spliterator.OfPrimitive
            public boolean tryAdvance(Object obj) {
                Node.OfPrimitive ofPrimitive;
                if (initTryAdvance()) {
                    boolean tryAdvance = ((Spliterator.OfPrimitive) this.tryAdvanceSpliterator).tryAdvance(obj);
                    if (!tryAdvance) {
                        if (this.lastNodeSpliterator == null && (ofPrimitive = (Node.OfPrimitive) findNextLeafNode(this.tryAdvanceStack)) != null) {
                            Spliterator.OfPrimitive spliterator = ofPrimitive.spliterator();
                            this.tryAdvanceSpliterator = spliterator;
                            return spliterator.tryAdvance(obj);
                        }
                        this.curNode = null;
                    }
                    return tryAdvance;
                }
                return false;
            }

            @Override // j$.util.stream.Nodes.InternalNodeSpliterator, j$.util.Spliterator
            public /* bridge */ /* synthetic */ Spliterator.OfPrimitive trySplit() {
                return (Spliterator.OfPrimitive) super.trySplit();
            }
        }

        /* loaded from: classes2.dex */
        private static final class OfRef extends InternalNodeSpliterator {
            OfRef(Node node) {
                super(node);
            }

            @Override // j$.util.Spliterator
            public void forEachRemaining(Consumer consumer) {
                if (this.curNode == null) {
                    return;
                }
                if (this.tryAdvanceSpliterator != null) {
                    do {
                    } while (tryAdvance(consumer));
                    return;
                }
                Spliterator spliterator = this.lastNodeSpliterator;
                if (spliterator != null) {
                    spliterator.forEachRemaining(consumer);
                    return;
                }
                Deque initStack = initStack();
                while (true) {
                    Node findNextLeafNode = findNextLeafNode(initStack);
                    if (findNextLeafNode == null) {
                        this.curNode = null;
                        return;
                    }
                    findNextLeafNode.forEach(consumer);
                }
            }

            @Override // j$.util.Spliterator
            public boolean tryAdvance(Consumer consumer) {
                Node findNextLeafNode;
                if (initTryAdvance()) {
                    boolean tryAdvance = this.tryAdvanceSpliterator.tryAdvance(consumer);
                    if (!tryAdvance) {
                        if (this.lastNodeSpliterator == null && (findNextLeafNode = findNextLeafNode(this.tryAdvanceStack)) != null) {
                            Spliterator spliterator = findNextLeafNode.spliterator();
                            this.tryAdvanceSpliterator = spliterator;
                            return spliterator.tryAdvance(consumer);
                        }
                        this.curNode = null;
                    }
                    return tryAdvance;
                }
                return false;
            }
        }

        InternalNodeSpliterator(Node node) {
            this.curNode = node;
        }

        @Override // j$.util.Spliterator
        public final int characteristics() {
            return 64;
        }

        @Override // j$.util.Spliterator
        public final long estimateSize() {
            long j = 0;
            if (this.curNode == null) {
                return 0L;
            }
            Spliterator spliterator = this.lastNodeSpliterator;
            if (spliterator != null) {
                return spliterator.estimateSize();
            }
            for (int i = this.curChildIndex; i < this.curNode.getChildCount(); i++) {
                j += this.curNode.getChild(i).count();
            }
            return j;
        }

        protected final Node findNextLeafNode(Deque deque) {
            while (true) {
                Node node = (Node) deque.pollFirst();
                if (node == null) {
                    return null;
                }
                if (node.getChildCount() != 0) {
                    for (int childCount = node.getChildCount() - 1; childCount >= 0; childCount--) {
                        deque.addFirst(node.getChild(childCount));
                    }
                } else if (node.count() > 0) {
                    return node;
                }
            }
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ Comparator getComparator() {
            return Spliterator.CC.$default$getComparator(this);
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ long getExactSizeIfKnown() {
            return Spliterator.CC.$default$getExactSizeIfKnown(this);
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.CC.$default$hasCharacteristics(this, i);
        }

        protected final Deque initStack() {
            ArrayDeque arrayDeque = new ArrayDeque(8);
            int childCount = this.curNode.getChildCount();
            while (true) {
                childCount--;
                if (childCount < this.curChildIndex) {
                    return arrayDeque;
                }
                arrayDeque.addFirst(this.curNode.getChild(childCount));
            }
        }

        protected final boolean initTryAdvance() {
            if (this.curNode == null) {
                return false;
            }
            if (this.tryAdvanceSpliterator == null) {
                Spliterator spliterator = this.lastNodeSpliterator;
                if (spliterator == null) {
                    Deque initStack = initStack();
                    this.tryAdvanceStack = initStack;
                    Node findNextLeafNode = findNextLeafNode(initStack);
                    if (findNextLeafNode == null) {
                        this.curNode = null;
                        return false;
                    }
                    spliterator = findNextLeafNode.spliterator();
                }
                this.tryAdvanceSpliterator = spliterator;
                return true;
            }
            return true;
        }

        @Override // j$.util.Spliterator
        public final Spliterator trySplit() {
            Node node = this.curNode;
            if (node == null || this.tryAdvanceSpliterator != null) {
                return null;
            }
            Spliterator spliterator = this.lastNodeSpliterator;
            if (spliterator != null) {
                return spliterator.trySplit();
            }
            if (this.curChildIndex < node.getChildCount() - 1) {
                Node node2 = this.curNode;
                int i = this.curChildIndex;
                this.curChildIndex = i + 1;
                return node2.getChild(i).spliterator();
            }
            Node child = this.curNode.getChild(this.curChildIndex);
            this.curNode = child;
            if (child.getChildCount() == 0) {
                Spliterator spliterator2 = this.curNode.spliterator();
                this.lastNodeSpliterator = spliterator2;
                return spliterator2.trySplit();
            }
            Node node3 = this.curNode;
            this.curChildIndex = 0 + 1;
            return node3.getChild(0).spliterator();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class LongArrayNode implements Node.OfLong {
        final long[] array;
        int curSize;

        LongArrayNode(long j) {
            if (j >= 2147483639) {
                throw new IllegalArgumentException("Stream size exceeds max array size");
            }
            this.array = new long[(int) j];
            this.curSize = 0;
        }

        LongArrayNode(long[] jArr) {
            this.array = jArr;
            this.curSize = jArr.length;
        }

        @Override // j$.util.stream.Node
        public /* synthetic */ Object[] asArray(IntFunction intFunction) {
            return Node.OfPrimitive.CC.$default$asArray(this, intFunction);
        }

        @Override // j$.util.stream.Node.OfPrimitive
        public long[] asPrimitiveArray() {
            long[] jArr = this.array;
            int length = jArr.length;
            int i = this.curSize;
            return length == i ? jArr : Arrays.copyOf(jArr, i);
        }

        @Override // j$.util.stream.Node.OfPrimitive
        public void copyInto(long[] jArr, int i) {
            System.arraycopy(this.array, 0, jArr, i, this.curSize);
        }

        @Override // j$.util.stream.Node.OfLong
        public /* synthetic */ void copyInto(Long[] lArr, int i) {
            Node.OfLong.CC.$default$copyInto((Node.OfLong) this, lArr, i);
        }

        @Override // j$.util.stream.Node
        public /* bridge */ /* synthetic */ void copyInto(Object[] objArr, int i) {
            copyInto((Long[]) objArr, i);
        }

        @Override // j$.util.stream.Node
        public long count() {
            return this.curSize;
        }

        @Override // j$.util.stream.Node
        public /* synthetic */ void forEach(Consumer consumer) {
            Node.OfLong.CC.$default$forEach(this, consumer);
        }

        @Override // j$.util.stream.Node.OfPrimitive
        public void forEach(LongConsumer longConsumer) {
            for (int i = 0; i < this.curSize; i++) {
                longConsumer.accept(this.array[i]);
            }
        }

        @Override // j$.util.stream.Node.OfPrimitive, j$.util.stream.Node
        public /* synthetic */ Node.OfPrimitive getChild(int i) {
            return Node.OfPrimitive.CC.$default$getChild((Node.OfPrimitive) this, i);
        }

        @Override // j$.util.stream.Node
        public /* bridge */ /* synthetic */ Node getChild(int i) {
            Node child;
            child = getChild(i);
            return child;
        }

        @Override // j$.util.stream.Node
        public /* synthetic */ int getChildCount() {
            return Node.CC.$default$getChildCount(this);
        }

        @Override // j$.util.stream.Node.OfLong, j$.util.stream.Node.OfPrimitive
        public /* synthetic */ long[] newArray(int i) {
            return Node.OfLong.CC.m518$default$newArray((Node.OfLong) this, i);
        }

        @Override // j$.util.stream.Node
        public Spliterator.OfLong spliterator() {
            return DesugarArrays.spliterator(this.array, 0, this.curSize);
        }

        public String toString() {
            return String.format("LongArrayNode[%d][%s]", Integer.valueOf(this.array.length - this.curSize), Arrays.toString(this.array));
        }

        @Override // j$.util.stream.Node.OfLong, j$.util.stream.Node
        public /* synthetic */ Node.OfLong truncate(long j, long j2, IntFunction intFunction) {
            return Node.OfLong.CC.$default$truncate((Node.OfLong) this, j, j2, intFunction);
        }

        @Override // j$.util.stream.Node
        public /* bridge */ /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
            Node truncate;
            truncate = truncate(j, j2, intFunction);
            return truncate;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class LongFixedNodeBuilder extends LongArrayNode implements Node.Builder.OfLong {
        LongFixedNodeBuilder(long j) {
            super(j);
        }

        @Override // j$.util.stream.Sink, java.util.function.DoubleConsumer
        public /* synthetic */ void accept(double d) {
            Sink.CC.$default$accept(this, d);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void accept(int i) {
            Sink.CC.$default$accept((Sink) this, i);
        }

        @Override // j$.util.stream.Sink
        public void accept(long j) {
            int i = this.curSize;
            long[] jArr = this.array;
            if (i >= jArr.length) {
                throw new IllegalStateException(String.format("Accept exceeded fixed size of %d", Integer.valueOf(this.array.length)));
            }
            this.curSize = i + 1;
            jArr[i] = j;
        }

        @Override // j$.util.stream.Sink.OfLong
        public /* synthetic */ void accept(Long l) {
            Sink.OfLong.CC.$default$accept((Sink.OfLong) this, l);
        }

        @Override // java.util.function.Consumer
        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfLong.CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer$CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
            return LongConsumer$CC.$default$andThen(this, longConsumer);
        }

        @Override // j$.util.stream.Sink
        public void begin(long j) {
            if (j != this.array.length) {
                throw new IllegalStateException(String.format("Begin size %d is not equal to fixed size %d", Long.valueOf(j), Integer.valueOf(this.array.length)));
            }
            this.curSize = 0;
        }

        @Override // j$.util.stream.Node.Builder
        public Node.OfLong build() {
            if (this.curSize >= this.array.length) {
                return this;
            }
            throw new IllegalStateException(String.format("Current size %d is less than fixed size %d", Integer.valueOf(this.curSize), Integer.valueOf(this.array.length)));
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ boolean cancellationRequested() {
            return Sink.CC.$default$cancellationRequested(this);
        }

        @Override // j$.util.stream.Sink
        public void end() {
            if (this.curSize < this.array.length) {
                throw new IllegalStateException(String.format("End size %d is less than fixed size %d", Integer.valueOf(this.curSize), Integer.valueOf(this.array.length)));
            }
        }

        @Override // j$.util.stream.Nodes.LongArrayNode
        public String toString() {
            return String.format("LongFixedNodeBuilder[%d][%s]", Integer.valueOf(this.array.length - this.curSize), Arrays.toString(this.array));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class LongSpinedNodeBuilder extends SpinedBuffer.OfLong implements Node.OfLong, Node.Builder.OfLong {
        private boolean building = false;

        LongSpinedNodeBuilder() {
        }

        @Override // j$.util.stream.Sink, java.util.function.DoubleConsumer
        public /* synthetic */ void accept(double d) {
            Sink.CC.$default$accept(this, d);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void accept(int i) {
            Sink.CC.$default$accept((Sink) this, i);
        }

        @Override // j$.util.stream.SpinedBuffer.OfLong, java.util.function.LongConsumer
        public void accept(long j) {
            super.accept(j);
        }

        @Override // j$.util.stream.Sink.OfLong
        public /* synthetic */ void accept(Long l) {
            Sink.OfLong.CC.$default$accept((Sink.OfLong) this, l);
        }

        @Override // java.util.function.Consumer
        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfLong.CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer$CC.$default$andThen(this, consumer);
        }

        @Override // j$.util.stream.Node
        public /* synthetic */ Object[] asArray(IntFunction intFunction) {
            return Node.OfPrimitive.CC.$default$asArray(this, intFunction);
        }

        @Override // j$.util.stream.SpinedBuffer.OfPrimitive, j$.util.stream.Node.OfPrimitive
        public long[] asPrimitiveArray() {
            return (long[]) super.asPrimitiveArray();
        }

        @Override // j$.util.stream.Sink
        public void begin(long j) {
            this.building = true;
            clear();
            ensureCapacity(j);
        }

        @Override // j$.util.stream.Node.Builder
        public Node.OfLong build() {
            return this;
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ boolean cancellationRequested() {
            return Sink.CC.$default$cancellationRequested(this);
        }

        @Override // j$.util.stream.SpinedBuffer.OfPrimitive, j$.util.stream.Node.OfPrimitive
        public void copyInto(long[] jArr, int i) {
            super.copyInto((Object) jArr, i);
        }

        @Override // j$.util.stream.Node.OfLong
        public /* synthetic */ void copyInto(Long[] lArr, int i) {
            Node.OfLong.CC.$default$copyInto((Node.OfLong) this, lArr, i);
        }

        @Override // j$.util.stream.Node
        public /* bridge */ /* synthetic */ void copyInto(Object[] objArr, int i) {
            copyInto((Long[]) objArr, i);
        }

        @Override // j$.util.stream.Sink
        public void end() {
            this.building = false;
        }

        @Override // j$.util.stream.SpinedBuffer.OfPrimitive, j$.util.stream.Node.OfPrimitive
        public void forEach(LongConsumer longConsumer) {
            super.forEach((Object) longConsumer);
        }

        @Override // j$.util.stream.Node.OfPrimitive, j$.util.stream.Node
        public /* synthetic */ Node.OfPrimitive getChild(int i) {
            return Node.OfPrimitive.CC.$default$getChild((Node.OfPrimitive) this, i);
        }

        @Override // j$.util.stream.Node
        public /* bridge */ /* synthetic */ Node getChild(int i) {
            Node child;
            child = getChild(i);
            return child;
        }

        @Override // j$.util.stream.Node
        public /* synthetic */ int getChildCount() {
            return Node.CC.$default$getChildCount(this);
        }

        @Override // j$.util.stream.SpinedBuffer.OfLong, j$.util.stream.SpinedBuffer.OfPrimitive, java.lang.Iterable, j$.util.stream.Node.OfPrimitive, j$.util.stream.Node
        public Spliterator.OfLong spliterator() {
            return super.spliterator();
        }

        @Override // j$.util.stream.Node.OfLong, j$.util.stream.Node
        public /* synthetic */ Node.OfLong truncate(long j, long j2, IntFunction intFunction) {
            return Node.OfLong.CC.$default$truncate((Node.OfLong) this, j, j2, intFunction);
        }

        @Override // j$.util.stream.Node
        public /* bridge */ /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
            Node truncate;
            truncate = truncate(j, j2, intFunction);
            return truncate;
        }
    }

    /* loaded from: classes2.dex */
    private static abstract class SizedCollectorTask extends CountedCompleter implements Sink {
        protected int fence;
        protected final PipelineHelper helper;
        protected int index;
        protected long length;
        protected long offset;
        protected final Spliterator spliterator;
        protected final long targetSize;

        /* loaded from: classes2.dex */
        static final class OfDouble extends SizedCollectorTask implements Sink.OfDouble {
            private final double[] array;

            OfDouble(Spliterator spliterator, PipelineHelper pipelineHelper, double[] dArr) {
                super(spliterator, pipelineHelper, dArr.length);
                this.array = dArr;
            }

            OfDouble(OfDouble ofDouble, Spliterator spliterator, long j, long j2) {
                super(ofDouble, spliterator, j, j2, ofDouble.array.length);
                this.array = ofDouble.array;
            }

            @Override // j$.util.stream.Nodes.SizedCollectorTask, j$.util.stream.Sink, java.util.function.DoubleConsumer
            public void accept(double d) {
                int i = this.index;
                if (i >= this.fence) {
                    throw new IndexOutOfBoundsException(Integer.toString(this.index));
                }
                double[] dArr = this.array;
                this.index = i + 1;
                dArr[i] = d;
            }

            @Override // j$.util.stream.Sink.OfDouble
            public /* synthetic */ void accept(Double d) {
                Sink.OfDouble.CC.$default$accept((Sink.OfDouble) this, d);
            }

            @Override // java.util.function.Consumer
            public /* bridge */ /* synthetic */ void accept(Object obj) {
                Sink.OfDouble.CC.$default$accept(this, obj);
            }

            public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
                return DoubleConsumer$CC.$default$andThen(this, doubleConsumer);
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // j$.util.stream.Nodes.SizedCollectorTask
            public OfDouble makeChild(Spliterator spliterator, long j, long j2) {
                return new OfDouble(this, spliterator, j, j2);
            }
        }

        /* loaded from: classes2.dex */
        static final class OfInt extends SizedCollectorTask implements Sink.OfInt {
            private final int[] array;

            OfInt(Spliterator spliterator, PipelineHelper pipelineHelper, int[] iArr) {
                super(spliterator, pipelineHelper, iArr.length);
                this.array = iArr;
            }

            OfInt(OfInt ofInt, Spliterator spliterator, long j, long j2) {
                super(ofInt, spliterator, j, j2, ofInt.array.length);
                this.array = ofInt.array;
            }

            @Override // j$.util.stream.Nodes.SizedCollectorTask, j$.util.stream.Sink
            public void accept(int i) {
                int i2 = this.index;
                if (i2 >= this.fence) {
                    throw new IndexOutOfBoundsException(Integer.toString(this.index));
                }
                int[] iArr = this.array;
                this.index = i2 + 1;
                iArr[i2] = i;
            }

            @Override // j$.util.stream.Sink.OfInt
            public /* synthetic */ void accept(Integer num) {
                Sink.OfInt.CC.$default$accept((Sink.OfInt) this, num);
            }

            @Override // java.util.function.Consumer
            public /* bridge */ /* synthetic */ void accept(Object obj) {
                Sink.OfInt.CC.$default$accept(this, obj);
            }

            public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
                return IntConsumer$CC.$default$andThen(this, intConsumer);
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // j$.util.stream.Nodes.SizedCollectorTask
            public OfInt makeChild(Spliterator spliterator, long j, long j2) {
                return new OfInt(this, spliterator, j, j2);
            }
        }

        /* loaded from: classes2.dex */
        static final class OfLong extends SizedCollectorTask implements Sink.OfLong {
            private final long[] array;

            OfLong(Spliterator spliterator, PipelineHelper pipelineHelper, long[] jArr) {
                super(spliterator, pipelineHelper, jArr.length);
                this.array = jArr;
            }

            OfLong(OfLong ofLong, Spliterator spliterator, long j, long j2) {
                super(ofLong, spliterator, j, j2, ofLong.array.length);
                this.array = ofLong.array;
            }

            @Override // j$.util.stream.Nodes.SizedCollectorTask, j$.util.stream.Sink
            public void accept(long j) {
                int i = this.index;
                if (i >= this.fence) {
                    throw new IndexOutOfBoundsException(Integer.toString(this.index));
                }
                long[] jArr = this.array;
                this.index = i + 1;
                jArr[i] = j;
            }

            @Override // j$.util.stream.Sink.OfLong
            public /* synthetic */ void accept(Long l) {
                Sink.OfLong.CC.$default$accept((Sink.OfLong) this, l);
            }

            @Override // java.util.function.Consumer
            public /* bridge */ /* synthetic */ void accept(Object obj) {
                Sink.OfLong.CC.$default$accept(this, obj);
            }

            public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
                return LongConsumer$CC.$default$andThen(this, longConsumer);
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // j$.util.stream.Nodes.SizedCollectorTask
            public OfLong makeChild(Spliterator spliterator, long j, long j2) {
                return new OfLong(this, spliterator, j, j2);
            }
        }

        /* loaded from: classes2.dex */
        static final class OfRef extends SizedCollectorTask {
            private final Object[] array;

            OfRef(Spliterator spliterator, PipelineHelper pipelineHelper, Object[] objArr) {
                super(spliterator, pipelineHelper, objArr.length);
                this.array = objArr;
            }

            OfRef(OfRef ofRef, Spliterator spliterator, long j, long j2) {
                super(ofRef, spliterator, j, j2, ofRef.array.length);
                this.array = ofRef.array;
            }

            @Override // java.util.function.Consumer
            public void accept(Object obj) {
                int i = this.index;
                if (i >= this.fence) {
                    throw new IndexOutOfBoundsException(Integer.toString(this.index));
                }
                Object[] objArr = this.array;
                this.index = i + 1;
                objArr[i] = obj;
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // j$.util.stream.Nodes.SizedCollectorTask
            public OfRef makeChild(Spliterator spliterator, long j, long j2) {
                return new OfRef(this, spliterator, j, j2);
            }
        }

        SizedCollectorTask(Spliterator spliterator, PipelineHelper pipelineHelper, int i) {
            this.spliterator = spliterator;
            this.helper = pipelineHelper;
            this.targetSize = AbstractTask.suggestTargetSize(spliterator.estimateSize());
            this.offset = 0L;
            this.length = i;
        }

        SizedCollectorTask(SizedCollectorTask sizedCollectorTask, Spliterator spliterator, long j, long j2, int i) {
            super(sizedCollectorTask);
            this.spliterator = spliterator;
            this.helper = sizedCollectorTask.helper;
            this.targetSize = sizedCollectorTask.targetSize;
            this.offset = j;
            this.length = j2;
            if (j < 0 || j2 < 0 || (j + j2) - 1 >= i) {
                throw new IllegalArgumentException(String.format("offset and length interval [%d, %d + %d) is not within array size interval [0, %d)", Long.valueOf(j), Long.valueOf(j), Long.valueOf(j2), Integer.valueOf(i)));
            }
        }

        @Override // j$.util.stream.Sink, java.util.function.DoubleConsumer
        public /* synthetic */ void accept(double d) {
            Sink.CC.$default$accept(this, d);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void accept(int i) {
            Sink.CC.$default$accept((Sink) this, i);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void accept(long j) {
            Sink.CC.$default$accept((Sink) this, j);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer$CC.$default$andThen(this, consumer);
        }

        @Override // j$.util.stream.Sink
        public void begin(long j) {
            long j2 = this.length;
            if (j > j2) {
                throw new IllegalStateException("size passed to Sink.begin exceeds array length");
            }
            int i = (int) this.offset;
            this.index = i;
            this.fence = i + ((int) j2);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ boolean cancellationRequested() {
            return Sink.CC.$default$cancellationRequested(this);
        }

        @Override // java.util.concurrent.CountedCompleter
        public void compute() {
            Spliterator trySplit;
            Spliterator spliterator = this.spliterator;
            SizedCollectorTask sizedCollectorTask = this;
            while (spliterator.estimateSize() > sizedCollectorTask.targetSize && (trySplit = spliterator.trySplit()) != null) {
                sizedCollectorTask.setPendingCount(1);
                long estimateSize = trySplit.estimateSize();
                sizedCollectorTask.makeChild(trySplit, sizedCollectorTask.offset, estimateSize).fork();
                sizedCollectorTask = sizedCollectorTask.makeChild(spliterator, sizedCollectorTask.offset + estimateSize, sizedCollectorTask.length - estimateSize);
            }
            sizedCollectorTask.helper.wrapAndCopyInto(sizedCollectorTask, spliterator);
            sizedCollectorTask.propagateCompletion();
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void end() {
            Sink.CC.$default$end(this);
        }

        abstract SizedCollectorTask makeChild(Spliterator spliterator, long j, long j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class SpinedNodeBuilder extends SpinedBuffer implements Node, Node.Builder {
        private boolean building = false;

        SpinedNodeBuilder() {
        }

        @Override // j$.util.stream.Sink, java.util.function.DoubleConsumer
        public /* synthetic */ void accept(double d) {
            Sink.CC.$default$accept(this, d);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void accept(int i) {
            Sink.CC.$default$accept((Sink) this, i);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void accept(long j) {
            Sink.CC.$default$accept((Sink) this, j);
        }

        @Override // j$.util.stream.SpinedBuffer, java.util.function.Consumer
        public void accept(Object obj) {
            super.accept(obj);
        }

        @Override // j$.util.stream.SpinedBuffer, j$.util.stream.Node
        public Object[] asArray(IntFunction intFunction) {
            return super.asArray(intFunction);
        }

        @Override // j$.util.stream.Sink
        public void begin(long j) {
            this.building = true;
            clear();
            ensureCapacity(j);
        }

        @Override // j$.util.stream.Node.Builder
        public Node build() {
            return this;
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ boolean cancellationRequested() {
            return Sink.CC.$default$cancellationRequested(this);
        }

        @Override // j$.util.stream.SpinedBuffer, j$.util.stream.Node
        public void copyInto(Object[] objArr, int i) {
            super.copyInto(objArr, i);
        }

        @Override // j$.util.stream.Sink
        public void end() {
            this.building = false;
        }

        @Override // j$.util.stream.SpinedBuffer, java.lang.Iterable, j$.util.stream.Node
        public void forEach(Consumer consumer) {
            super.forEach(consumer);
        }

        @Override // j$.util.stream.Node
        public /* synthetic */ Node getChild(int i) {
            return Node.CC.$default$getChild(this, i);
        }

        @Override // j$.util.stream.Node
        public /* synthetic */ int getChildCount() {
            return Node.CC.$default$getChildCount(this);
        }

        @Override // j$.util.stream.SpinedBuffer, java.lang.Iterable, j$.util.stream.Node
        public Spliterator spliterator() {
            return super.spliterator();
        }

        @Override // j$.util.stream.Node
        public /* synthetic */ Node truncate(long j, long j2, IntFunction intFunction) {
            return Node.CC.$default$truncate(this, j, j2, intFunction);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static abstract class ToArrayTask extends CountedCompleter {
        protected final Node node;
        protected final int offset;

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes2.dex */
        public static final class OfDouble extends OfPrimitive {
            private OfDouble(Node.OfDouble ofDouble, double[] dArr, int i) {
                super(ofDouble, dArr, i);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes2.dex */
        public static final class OfInt extends OfPrimitive {
            private OfInt(Node.OfInt ofInt, int[] iArr, int i) {
                super(ofInt, iArr, i);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes2.dex */
        public static final class OfLong extends OfPrimitive {
            private OfLong(Node.OfLong ofLong, long[] jArr, int i) {
                super(ofLong, jArr, i);
            }
        }

        /* loaded from: classes2.dex */
        private static class OfPrimitive extends ToArrayTask {
            private final Object array;

            private OfPrimitive(Node.OfPrimitive ofPrimitive, Object obj, int i) {
                super(ofPrimitive, i);
                this.array = obj;
            }

            private OfPrimitive(OfPrimitive ofPrimitive, Node.OfPrimitive ofPrimitive2, int i) {
                super(ofPrimitive, ofPrimitive2, i);
                this.array = ofPrimitive.array;
            }

            @Override // j$.util.stream.Nodes.ToArrayTask
            void copyNodeToArray() {
                ((Node.OfPrimitive) this.node).copyInto(this.array, this.offset);
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // j$.util.stream.Nodes.ToArrayTask
            public OfPrimitive makeChild(int i, int i2) {
                return new OfPrimitive(this, ((Node.OfPrimitive) this.node).getChild(i), i2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes2.dex */
        public static final class OfRef extends ToArrayTask {
            private final Object[] array;

            private OfRef(Node node, Object[] objArr, int i) {
                super(node, i);
                this.array = objArr;
            }

            private OfRef(OfRef ofRef, Node node, int i) {
                super(ofRef, node, i);
                this.array = ofRef.array;
            }

            @Override // j$.util.stream.Nodes.ToArrayTask
            void copyNodeToArray() {
                this.node.copyInto(this.array, this.offset);
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // j$.util.stream.Nodes.ToArrayTask
            public OfRef makeChild(int i, int i2) {
                return new OfRef(this, this.node.getChild(i), i2);
            }
        }

        ToArrayTask(Node node, int i) {
            this.node = node;
            this.offset = i;
        }

        ToArrayTask(ToArrayTask toArrayTask, Node node, int i) {
            super(toArrayTask);
            this.node = node;
            this.offset = i;
        }

        @Override // java.util.concurrent.CountedCompleter
        public void compute() {
            ToArrayTask toArrayTask = this;
            while (toArrayTask.node.getChildCount() != 0) {
                toArrayTask.setPendingCount(toArrayTask.node.getChildCount() - 1);
                int i = 0;
                int i2 = 0;
                while (i < toArrayTask.node.getChildCount() - 1) {
                    ToArrayTask makeChild = toArrayTask.makeChild(i, toArrayTask.offset + i2);
                    i2 = (int) (i2 + makeChild.node.count());
                    makeChild.fork();
                    i++;
                }
                toArrayTask = toArrayTask.makeChild(i, toArrayTask.offset + i2);
            }
            toArrayTask.copyNodeToArray();
            toArrayTask.propagateCompletion();
        }

        abstract void copyNodeToArray();

        abstract ToArrayTask makeChild(int i, int i2);
    }

    static Node.Builder builder() {
        return new SpinedNodeBuilder();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Node.Builder builder(long j, IntFunction intFunction) {
        return (j < 0 || j >= 2147483639) ? builder() : new FixedNodeBuilder(j, intFunction);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static IntFunction castingArray() {
        return new IntFunction() { // from class: j$.util.stream.Nodes$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return Nodes.lambda$castingArray$0(i);
            }
        };
    }

    public static Node collect(PipelineHelper pipelineHelper, Spliterator spliterator, boolean z, IntFunction intFunction) {
        long exactOutputSizeIfKnown = pipelineHelper.exactOutputSizeIfKnown(spliterator);
        if (exactOutputSizeIfKnown < 0 || !spliterator.hasCharacteristics(16384)) {
            Node node = (Node) new CollectorTask.OfRef(pipelineHelper, intFunction, spliterator).invoke();
            return z ? flatten(node, intFunction) : node;
        } else if (exactOutputSizeIfKnown < 2147483639) {
            Object[] objArr = (Object[]) intFunction.apply((int) exactOutputSizeIfKnown);
            new SizedCollectorTask.OfRef(spliterator, pipelineHelper, objArr).invoke();
            return node(objArr);
        } else {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
    }

    public static Node.OfDouble collectDouble(PipelineHelper pipelineHelper, Spliterator spliterator, boolean z) {
        long exactOutputSizeIfKnown = pipelineHelper.exactOutputSizeIfKnown(spliterator);
        if (exactOutputSizeIfKnown < 0 || !spliterator.hasCharacteristics(16384)) {
            Node.OfDouble ofDouble = (Node.OfDouble) new CollectorTask.OfDouble(pipelineHelper, spliterator).invoke();
            return z ? flattenDouble(ofDouble) : ofDouble;
        } else if (exactOutputSizeIfKnown < 2147483639) {
            double[] dArr = new double[(int) exactOutputSizeIfKnown];
            new SizedCollectorTask.OfDouble(spliterator, pipelineHelper, dArr).invoke();
            return node(dArr);
        } else {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
    }

    public static Node.OfInt collectInt(PipelineHelper pipelineHelper, Spliterator spliterator, boolean z) {
        long exactOutputSizeIfKnown = pipelineHelper.exactOutputSizeIfKnown(spliterator);
        if (exactOutputSizeIfKnown < 0 || !spliterator.hasCharacteristics(16384)) {
            Node.OfInt ofInt = (Node.OfInt) new CollectorTask.OfInt(pipelineHelper, spliterator).invoke();
            return z ? flattenInt(ofInt) : ofInt;
        } else if (exactOutputSizeIfKnown < 2147483639) {
            int[] iArr = new int[(int) exactOutputSizeIfKnown];
            new SizedCollectorTask.OfInt(spliterator, pipelineHelper, iArr).invoke();
            return node(iArr);
        } else {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
    }

    public static Node.OfLong collectLong(PipelineHelper pipelineHelper, Spliterator spliterator, boolean z) {
        long exactOutputSizeIfKnown = pipelineHelper.exactOutputSizeIfKnown(spliterator);
        if (exactOutputSizeIfKnown < 0 || !spliterator.hasCharacteristics(16384)) {
            Node.OfLong ofLong = (Node.OfLong) new CollectorTask.OfLong(pipelineHelper, spliterator).invoke();
            return z ? flattenLong(ofLong) : ofLong;
        } else if (exactOutputSizeIfKnown < 2147483639) {
            long[] jArr = new long[(int) exactOutputSizeIfKnown];
            new SizedCollectorTask.OfLong(spliterator, pipelineHelper, jArr).invoke();
            return node(jArr);
        } else {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Node conc(StreamShape streamShape, Node node, Node node2) {
        int i = AnonymousClass1.$SwitchMap$java$util$stream$StreamShape[streamShape.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i == 4) {
                        return new ConcNode.OfDouble((Node.OfDouble) node, (Node.OfDouble) node2);
                    }
                    throw new IllegalStateException("Unknown shape " + streamShape);
                }
                return new ConcNode.OfLong((Node.OfLong) node, (Node.OfLong) node2);
            }
            return new ConcNode.OfInt((Node.OfInt) node, (Node.OfInt) node2);
        }
        return new ConcNode(node, node2);
    }

    static Node.Builder.OfDouble doubleBuilder() {
        return new DoubleSpinedNodeBuilder();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Node.Builder.OfDouble doubleBuilder(long j) {
        return (j < 0 || j >= 2147483639) ? doubleBuilder() : new DoubleFixedNodeBuilder(j);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Node emptyNode(StreamShape streamShape) {
        int i = AnonymousClass1.$SwitchMap$java$util$stream$StreamShape[streamShape.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i == 4) {
                        return EMPTY_DOUBLE_NODE;
                    }
                    throw new IllegalStateException("Unknown shape " + streamShape);
                }
                return EMPTY_LONG_NODE;
            }
            return EMPTY_INT_NODE;
        }
        return EMPTY_NODE;
    }

    public static Node flatten(Node node, IntFunction intFunction) {
        if (node.getChildCount() > 0) {
            long count = node.count();
            if (count < 2147483639) {
                Object[] objArr = (Object[]) intFunction.apply((int) count);
                new ToArrayTask.OfRef(node, objArr, 0).invoke();
                return node(objArr);
            }
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        return node;
    }

    public static Node.OfDouble flattenDouble(Node.OfDouble ofDouble) {
        if (ofDouble.getChildCount() > 0) {
            long count = ofDouble.count();
            if (count < 2147483639) {
                double[] dArr = new double[(int) count];
                new ToArrayTask.OfDouble(ofDouble, dArr, 0).invoke();
                return node(dArr);
            }
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        return ofDouble;
    }

    public static Node.OfInt flattenInt(Node.OfInt ofInt) {
        if (ofInt.getChildCount() > 0) {
            long count = ofInt.count();
            if (count < 2147483639) {
                int[] iArr = new int[(int) count];
                new ToArrayTask.OfInt(ofInt, iArr, 0).invoke();
                return node(iArr);
            }
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        return ofInt;
    }

    public static Node.OfLong flattenLong(Node.OfLong ofLong) {
        if (ofLong.getChildCount() > 0) {
            long count = ofLong.count();
            if (count < 2147483639) {
                long[] jArr = new long[(int) count];
                new ToArrayTask.OfLong(ofLong, jArr, 0).invoke();
                return node(jArr);
            }
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        return ofLong;
    }

    static Node.Builder.OfInt intBuilder() {
        return new IntSpinedNodeBuilder();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Node.Builder.OfInt intBuilder(long j) {
        return (j < 0 || j >= 2147483639) ? intBuilder() : new IntFixedNodeBuilder(j);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ Object[] lambda$castingArray$0(int i) {
        return new Object[i];
    }

    static Node.Builder.OfLong longBuilder() {
        return new LongSpinedNodeBuilder();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Node.Builder.OfLong longBuilder(long j) {
        return (j < 0 || j >= 2147483639) ? longBuilder() : new LongFixedNodeBuilder(j);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Node.OfDouble node(double[] dArr) {
        return new DoubleArrayNode(dArr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Node.OfInt node(int[] iArr) {
        return new IntArrayNode(iArr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Node.OfLong node(long[] jArr) {
        return new LongArrayNode(jArr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Node node(java.util.Collection collection) {
        return new CollectionNode(collection);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Node node(Object[] objArr) {
        return new ArrayNode(objArr);
    }
}
