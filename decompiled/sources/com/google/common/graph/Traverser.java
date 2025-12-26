package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.DoNotMock;
import j$.util.Objects;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import javax.annotation.CheckForNull;
@DoNotMock("Call forGraph or forTree, passing a lambda or a Graph with the desired edges (built with GraphBuilder)")
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public abstract class Traverser<N> {
    private final SuccessorsFunction<N> successorFunction;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public enum InsertionOrder {
        FRONT { // from class: com.google.common.graph.Traverser.InsertionOrder.1
            @Override // com.google.common.graph.Traverser.InsertionOrder
            <T> void insertInto(Deque<T> deque, T value) {
                deque.addFirst(value);
            }
        },
        BACK { // from class: com.google.common.graph.Traverser.InsertionOrder.2
            @Override // com.google.common.graph.Traverser.InsertionOrder
            <T> void insertInto(Deque<T> deque, T value) {
                deque.addLast(value);
            }
        };

        abstract <T> void insertInto(Deque<T> deque, T value);
    }

    abstract Traversal<N> newTraversal();

    private Traverser(SuccessorsFunction<N> successorFunction) {
        this.successorFunction = (SuccessorsFunction) Preconditions.checkNotNull(successorFunction);
    }

    public static <N> Traverser<N> forGraph(final SuccessorsFunction<N> graph) {
        return new Traverser<N>(graph) { // from class: com.google.common.graph.Traverser.1
            @Override // com.google.common.graph.Traverser
            Traversal<N> newTraversal() {
                return Traversal.inGraph(graph);
            }
        };
    }

    public static <N> Traverser<N> forTree(final SuccessorsFunction<N> tree) {
        if (tree instanceof BaseGraph) {
            Preconditions.checkArgument(((BaseGraph) tree).isDirected(), "Undirected graphs can never be trees.");
        }
        if (tree instanceof Network) {
            Preconditions.checkArgument(((Network) tree).isDirected(), "Undirected networks can never be trees.");
        }
        return new Traverser<N>(tree) { // from class: com.google.common.graph.Traverser.2
            @Override // com.google.common.graph.Traverser
            Traversal<N> newTraversal() {
                return Traversal.inTree(tree);
            }
        };
    }

    public final Iterable<N> breadthFirst(N startNode) {
        return breadthFirst((Iterable) ImmutableSet.of(startNode));
    }

    public final Iterable<N> breadthFirst(Iterable<? extends N> startNodes) {
        final ImmutableSet<N> validate = validate(startNodes);
        return new Iterable<N>() { // from class: com.google.common.graph.Traverser.3
            @Override // java.lang.Iterable
            public Iterator<N> iterator() {
                return Traverser.this.newTraversal().breadthFirst(validate.iterator());
            }
        };
    }

    public final Iterable<N> depthFirstPreOrder(N startNode) {
        return depthFirstPreOrder((Iterable) ImmutableSet.of(startNode));
    }

    public final Iterable<N> depthFirstPreOrder(Iterable<? extends N> startNodes) {
        final ImmutableSet<N> validate = validate(startNodes);
        return new Iterable<N>() { // from class: com.google.common.graph.Traverser.4
            @Override // java.lang.Iterable
            public Iterator<N> iterator() {
                return Traverser.this.newTraversal().preOrder(validate.iterator());
            }
        };
    }

    public final Iterable<N> depthFirstPostOrder(N startNode) {
        return depthFirstPostOrder((Iterable) ImmutableSet.of(startNode));
    }

    public final Iterable<N> depthFirstPostOrder(Iterable<? extends N> startNodes) {
        final ImmutableSet<N> validate = validate(startNodes);
        return new Iterable<N>() { // from class: com.google.common.graph.Traverser.5
            @Override // java.lang.Iterable
            public Iterator<N> iterator() {
                return Traverser.this.newTraversal().postOrder(validate.iterator());
            }
        };
    }

    private ImmutableSet<N> validate(Iterable<? extends N> startNodes) {
        ImmutableSet<N> copyOf = ImmutableSet.copyOf(startNodes);
        UnmodifiableIterator<N> it = copyOf.iterator();
        while (it.hasNext()) {
            this.successorFunction.successors(it.next());
        }
        return copyOf;
    }

    /* loaded from: classes3.dex */
    private static abstract class Traversal<N> {
        final SuccessorsFunction<N> successorFunction;

        @CheckForNull
        abstract N visitNext(Deque<Iterator<? extends N>> horizon);

        Traversal(SuccessorsFunction<N> successorFunction) {
            this.successorFunction = successorFunction;
        }

        static <N> Traversal<N> inGraph(SuccessorsFunction<N> graph) {
            final HashSet hashSet = new HashSet();
            return new Traversal<N>(graph) { // from class: com.google.common.graph.Traverser.Traversal.1
                @Override // com.google.common.graph.Traverser.Traversal
                @CheckForNull
                N visitNext(Deque<Iterator<? extends N>> horizon) {
                    Iterator<? extends N> first = horizon.getFirst();
                    while (first.hasNext()) {
                        N next = first.next();
                        Objects.requireNonNull(next);
                        if (hashSet.add(next)) {
                            return next;
                        }
                    }
                    horizon.removeFirst();
                    return null;
                }
            };
        }

        static <N> Traversal<N> inTree(SuccessorsFunction<N> tree) {
            return new Traversal<N>(tree) { // from class: com.google.common.graph.Traverser.Traversal.2
                @Override // com.google.common.graph.Traverser.Traversal
                @CheckForNull
                N visitNext(Deque<Iterator<? extends N>> horizon) {
                    Iterator<? extends N> first = horizon.getFirst();
                    if (first.hasNext()) {
                        return (N) Preconditions.checkNotNull(first.next());
                    }
                    horizon.removeFirst();
                    return null;
                }
            };
        }

        final Iterator<N> breadthFirst(Iterator<? extends N> startNodes) {
            return topDown(startNodes, InsertionOrder.BACK);
        }

        final Iterator<N> preOrder(Iterator<? extends N> startNodes) {
            return topDown(startNodes, InsertionOrder.FRONT);
        }

        private Iterator<N> topDown(Iterator<? extends N> startNodes, final InsertionOrder order) {
            final ArrayDeque arrayDeque = new ArrayDeque();
            arrayDeque.add(startNodes);
            return new AbstractIterator<N>() { // from class: com.google.common.graph.Traverser.Traversal.3
                @Override // com.google.common.collect.AbstractIterator
                @CheckForNull
                protected N computeNext() {
                    do {
                        N n = (N) Traversal.this.visitNext(arrayDeque);
                        if (n != null) {
                            Iterator<? extends N> it = Traversal.this.successorFunction.successors(n).iterator();
                            if (it.hasNext()) {
                                order.insertInto(arrayDeque, it);
                            }
                            return n;
                        }
                    } while (!arrayDeque.isEmpty());
                    return endOfData();
                }
            };
        }

        final Iterator<N> postOrder(Iterator<? extends N> startNodes) {
            final ArrayDeque arrayDeque = new ArrayDeque();
            final ArrayDeque arrayDeque2 = new ArrayDeque();
            arrayDeque2.add(startNodes);
            return new AbstractIterator<N>() { // from class: com.google.common.graph.Traverser.Traversal.4
                @Override // com.google.common.collect.AbstractIterator
                @CheckForNull
                protected N computeNext() {
                    while (true) {
                        N n = (N) Traversal.this.visitNext(arrayDeque2);
                        if (n != null) {
                            Iterator<? extends N> it = Traversal.this.successorFunction.successors(n).iterator();
                            if (!it.hasNext()) {
                                return n;
                            }
                            arrayDeque2.addFirst(it);
                            arrayDeque.push(n);
                        } else if (!arrayDeque.isEmpty()) {
                            return (N) arrayDeque.pop();
                        } else {
                            return endOfData();
                        }
                    }
                }
            };
        }
    }
}
