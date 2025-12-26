package com.google.common.graph;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.graph.Graphs;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckForNull;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class Graphs {

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public enum NodeVisitState {
        PENDING,
        COMPLETE
    }

    private Graphs() {
    }

    public static <N> boolean hasCycle(Graph<N> graph) {
        int size = graph.edges().size();
        if (size == 0) {
            return false;
        }
        if (graph.isDirected() || size < graph.nodes().size()) {
            HashMap newHashMapWithExpectedSize = Maps.newHashMapWithExpectedSize(graph.nodes().size());
            for (N n : graph.nodes()) {
                if (subgraphHasCycle(graph, newHashMapWithExpectedSize, n, null)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    public static boolean hasCycle(Network<?, ?> network) {
        if (network.isDirected() || !network.allowsParallelEdges() || network.edges().size() <= network.asGraph().edges().size()) {
            return hasCycle(network.asGraph());
        }
        return true;
    }

    private static <N> boolean subgraphHasCycle(Graph<N> graph, Map<Object, NodeVisitState> visitedNodes, N node, @CheckForNull N previousNode) {
        NodeVisitState nodeVisitState = visitedNodes.get(node);
        if (nodeVisitState == NodeVisitState.COMPLETE) {
            return false;
        }
        if (nodeVisitState == NodeVisitState.PENDING) {
            return true;
        }
        visitedNodes.put(node, NodeVisitState.PENDING);
        for (N n : graph.successors((Graph<N>) node)) {
            if (canTraverseWithoutReusingEdge(graph, n, previousNode) && subgraphHasCycle(graph, visitedNodes, n, node)) {
                return true;
            }
        }
        visitedNodes.put(node, NodeVisitState.COMPLETE);
        return false;
    }

    private static boolean canTraverseWithoutReusingEdge(Graph<?> graph, Object nextNode, @CheckForNull Object previousNode) {
        return graph.isDirected() || !Objects.equal(previousNode, nextNode);
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.google.common.graph.MutableGraph, com.google.common.graph.Graph<N>] */
    public static <N> Graph<N> transitiveClosure(Graph<N> graph) {
        ?? build = GraphBuilder.from(graph).allowsSelfLoops(true).build();
        if (graph.isDirected()) {
            for (N n : graph.nodes()) {
                for (Object obj : reachableNodes(graph, n)) {
                    build.putEdge(n, obj);
                }
            }
        } else {
            HashSet hashSet = new HashSet();
            for (N n2 : graph.nodes()) {
                if (!hashSet.contains(n2)) {
                    Set reachableNodes = reachableNodes(graph, n2);
                    hashSet.addAll(reachableNodes);
                    int i = 1;
                    for (Object obj2 : reachableNodes) {
                        int i2 = i + 1;
                        for (Object obj3 : Iterables.limit(reachableNodes, i)) {
                            build.putEdge(obj2, obj3);
                        }
                        i = i2;
                    }
                }
            }
        }
        return build;
    }

    public static <N> Set<N> reachableNodes(Graph<N> graph, N node) {
        Preconditions.checkArgument(graph.nodes().contains(node), "Node %s is not an element of this graph.", node);
        return ImmutableSet.copyOf(Traverser.forGraph(graph).breadthFirst((Traverser) node));
    }

    public static <N> Graph<N> transpose(Graph<N> graph) {
        if (graph.isDirected()) {
            if (!(graph instanceof TransposedGraph)) {
                return new TransposedGraph(graph);
            }
            return ((TransposedGraph) graph).graph;
        }
        return graph;
    }

    public static <N, V> ValueGraph<N, V> transpose(ValueGraph<N, V> graph) {
        if (graph.isDirected()) {
            if (!(graph instanceof TransposedValueGraph)) {
                return new TransposedValueGraph(graph);
            }
            return ((TransposedValueGraph) graph).graph;
        }
        return graph;
    }

    public static <N, E> Network<N, E> transpose(Network<N, E> network) {
        if (network.isDirected()) {
            if (!(network instanceof TransposedNetwork)) {
                return new TransposedNetwork(network);
            }
            return ((TransposedNetwork) network).network;
        }
        return network;
    }

    static <N> EndpointPair<N> transpose(EndpointPair<N> endpoints) {
        return endpoints.isOrdered() ? EndpointPair.ordered(endpoints.target(), endpoints.source()) : endpoints;
    }

    /* loaded from: classes3.dex */
    private static class TransposedGraph<N> extends ForwardingGraph<N> {
        private final Graph<N> graph;

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.graph.ForwardingGraph, com.google.common.graph.AbstractGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.PredecessorsFunction
        public /* bridge */ /* synthetic */ Iterable predecessors(Object node) {
            return predecessors((TransposedGraph<N>) node);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.graph.ForwardingGraph, com.google.common.graph.AbstractGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.SuccessorsFunction
        public /* bridge */ /* synthetic */ Iterable successors(Object node) {
            return successors((TransposedGraph<N>) node);
        }

        TransposedGraph(Graph<N> graph) {
            this.graph = graph;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.graph.ForwardingGraph
        public Graph<N> delegate() {
            return this.graph;
        }

        @Override // com.google.common.graph.ForwardingGraph, com.google.common.graph.AbstractGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.PredecessorsFunction
        public Set<N> predecessors(N node) {
            return delegate().successors((Graph<N>) node);
        }

        @Override // com.google.common.graph.ForwardingGraph, com.google.common.graph.AbstractGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.SuccessorsFunction
        public Set<N> successors(N node) {
            return delegate().predecessors((Graph<N>) node);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: com.google.common.graph.Graphs$TransposedGraph$1  reason: invalid class name */
        /* loaded from: classes3.dex */
        public class AnonymousClass1 extends IncidentEdgeSet<N> {
            AnonymousClass1(BaseGraph graph, Object node) {
                super(graph, node);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<EndpointPair<N>> iterator() {
                return Iterators.transform(TransposedGraph.this.delegate().incidentEdges(this.node).iterator(), new Function() { // from class: com.google.common.graph.Graphs$TransposedGraph$1$$ExternalSyntheticLambda0
                    @Override // com.google.common.base.Function
                    public final Object apply(Object obj) {
                        return Graphs.TransposedGraph.AnonymousClass1.this.m460x5749e417((EndpointPair) obj);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            /* renamed from: lambda$iterator$0$com-google-common-graph-Graphs$TransposedGraph$1  reason: not valid java name */
            public /* synthetic */ EndpointPair m460x5749e417(EndpointPair endpointPair) {
                return EndpointPair.of((Graph<?>) TransposedGraph.this.delegate(), endpointPair.nodeV(), endpointPair.nodeU());
            }
        }

        @Override // com.google.common.graph.ForwardingGraph, com.google.common.graph.AbstractGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
        public Set<EndpointPair<N>> incidentEdges(N node) {
            return new AnonymousClass1(this, node);
        }

        @Override // com.google.common.graph.ForwardingGraph, com.google.common.graph.AbstractGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
        public int inDegree(N node) {
            return delegate().outDegree(node);
        }

        @Override // com.google.common.graph.ForwardingGraph, com.google.common.graph.AbstractGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
        public int outDegree(N node) {
            return delegate().inDegree(node);
        }

        @Override // com.google.common.graph.ForwardingGraph, com.google.common.graph.AbstractGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
        public boolean hasEdgeConnecting(N nodeU, N nodeV) {
            return delegate().hasEdgeConnecting(nodeV, nodeU);
        }

        @Override // com.google.common.graph.ForwardingGraph, com.google.common.graph.AbstractGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
        public boolean hasEdgeConnecting(EndpointPair<N> endpoints) {
            return delegate().hasEdgeConnecting(Graphs.transpose(endpoints));
        }
    }

    /* loaded from: classes3.dex */
    private static class TransposedValueGraph<N, V> extends ForwardingValueGraph<N, V> {
        private final ValueGraph<N, V> graph;

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.graph.ForwardingValueGraph, com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.PredecessorsFunction
        public /* bridge */ /* synthetic */ Iterable predecessors(Object node) {
            return predecessors((TransposedValueGraph<N, V>) node);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.graph.ForwardingValueGraph, com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.SuccessorsFunction
        public /* bridge */ /* synthetic */ Iterable successors(Object node) {
            return successors((TransposedValueGraph<N, V>) node);
        }

        TransposedValueGraph(ValueGraph<N, V> graph) {
            this.graph = graph;
        }

        @Override // com.google.common.graph.ForwardingValueGraph
        ValueGraph<N, V> delegate() {
            return this.graph;
        }

        @Override // com.google.common.graph.ForwardingValueGraph, com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.PredecessorsFunction
        public Set<N> predecessors(N node) {
            return delegate().successors((ValueGraph<N, V>) node);
        }

        @Override // com.google.common.graph.ForwardingValueGraph, com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.SuccessorsFunction
        public Set<N> successors(N node) {
            return delegate().predecessors((ValueGraph<N, V>) node);
        }

        @Override // com.google.common.graph.ForwardingValueGraph, com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
        public int inDegree(N node) {
            return delegate().outDegree(node);
        }

        @Override // com.google.common.graph.ForwardingValueGraph, com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
        public int outDegree(N node) {
            return delegate().inDegree(node);
        }

        @Override // com.google.common.graph.ForwardingValueGraph, com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
        public boolean hasEdgeConnecting(N nodeU, N nodeV) {
            return delegate().hasEdgeConnecting(nodeV, nodeU);
        }

        @Override // com.google.common.graph.ForwardingValueGraph, com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
        public boolean hasEdgeConnecting(EndpointPair<N> endpoints) {
            return delegate().hasEdgeConnecting(Graphs.transpose(endpoints));
        }

        @Override // com.google.common.graph.ForwardingValueGraph, com.google.common.graph.ValueGraph
        @CheckForNull
        public V edgeValueOrDefault(N nodeU, N nodeV, @CheckForNull V defaultValue) {
            return delegate().edgeValueOrDefault(nodeV, nodeU, defaultValue);
        }

        @Override // com.google.common.graph.ForwardingValueGraph, com.google.common.graph.ValueGraph
        @CheckForNull
        public V edgeValueOrDefault(EndpointPair<N> endpoints, @CheckForNull V defaultValue) {
            return delegate().edgeValueOrDefault(Graphs.transpose(endpoints), defaultValue);
        }
    }

    /* loaded from: classes3.dex */
    private static class TransposedNetwork<N, E> extends ForwardingNetwork<N, E> {
        private final Network<N, E> network;

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.graph.ForwardingNetwork, com.google.common.graph.AbstractNetwork, com.google.common.graph.PredecessorsFunction
        public /* bridge */ /* synthetic */ Iterable predecessors(Object node) {
            return predecessors((TransposedNetwork<N, E>) node);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.graph.ForwardingNetwork, com.google.common.graph.AbstractNetwork, com.google.common.graph.SuccessorsFunction
        public /* bridge */ /* synthetic */ Iterable successors(Object node) {
            return successors((TransposedNetwork<N, E>) node);
        }

        TransposedNetwork(Network<N, E> network) {
            this.network = network;
        }

        @Override // com.google.common.graph.ForwardingNetwork
        Network<N, E> delegate() {
            return this.network;
        }

        @Override // com.google.common.graph.ForwardingNetwork, com.google.common.graph.AbstractNetwork, com.google.common.graph.PredecessorsFunction
        public Set<N> predecessors(N node) {
            return delegate().successors((Network<N, E>) node);
        }

        @Override // com.google.common.graph.ForwardingNetwork, com.google.common.graph.AbstractNetwork, com.google.common.graph.SuccessorsFunction
        public Set<N> successors(N node) {
            return delegate().predecessors((Network<N, E>) node);
        }

        @Override // com.google.common.graph.ForwardingNetwork, com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
        public int inDegree(N node) {
            return delegate().outDegree(node);
        }

        @Override // com.google.common.graph.ForwardingNetwork, com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
        public int outDegree(N node) {
            return delegate().inDegree(node);
        }

        @Override // com.google.common.graph.ForwardingNetwork, com.google.common.graph.Network
        public Set<E> inEdges(N node) {
            return delegate().outEdges(node);
        }

        @Override // com.google.common.graph.ForwardingNetwork, com.google.common.graph.Network
        public Set<E> outEdges(N node) {
            return delegate().inEdges(node);
        }

        @Override // com.google.common.graph.ForwardingNetwork, com.google.common.graph.Network
        public EndpointPair<N> incidentNodes(E edge) {
            EndpointPair<N> incidentNodes = delegate().incidentNodes(edge);
            return EndpointPair.of((Network<?, ?>) this.network, (Object) incidentNodes.nodeV(), (Object) incidentNodes.nodeU());
        }

        @Override // com.google.common.graph.ForwardingNetwork, com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
        public Set<E> edgesConnecting(N nodeU, N nodeV) {
            return delegate().edgesConnecting(nodeV, nodeU);
        }

        @Override // com.google.common.graph.ForwardingNetwork, com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
        public Set<E> edgesConnecting(EndpointPair<N> endpoints) {
            return delegate().edgesConnecting(Graphs.transpose(endpoints));
        }

        @Override // com.google.common.graph.ForwardingNetwork, com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
        @CheckForNull
        public E edgeConnectingOrNull(N nodeU, N nodeV) {
            return delegate().edgeConnectingOrNull(nodeV, nodeU);
        }

        @Override // com.google.common.graph.ForwardingNetwork, com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
        @CheckForNull
        public E edgeConnectingOrNull(EndpointPair<N> endpoints) {
            return delegate().edgeConnectingOrNull(Graphs.transpose(endpoints));
        }

        @Override // com.google.common.graph.ForwardingNetwork, com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
        public boolean hasEdgeConnecting(N nodeU, N nodeV) {
            return delegate().hasEdgeConnecting(nodeV, nodeU);
        }

        @Override // com.google.common.graph.ForwardingNetwork, com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
        public boolean hasEdgeConnecting(EndpointPair<N> endpoints) {
            return delegate().hasEdgeConnecting(Graphs.transpose(endpoints));
        }
    }

    public static <N> MutableGraph<N> inducedSubgraph(Graph<N> graph, Iterable<? extends N> nodes) {
        MutableGraph<N> mutableGraph;
        if (nodes instanceof Collection) {
            mutableGraph = GraphBuilder.from(graph).expectedNodeCount(((Collection) nodes).size()).build();
        } else {
            mutableGraph = GraphBuilder.from(graph).build();
        }
        for (N n : nodes) {
            mutableGraph.addNode(n);
        }
        for (N n2 : mutableGraph.nodes()) {
            for (N n3 : graph.successors((Graph<N>) n2)) {
                if (mutableGraph.nodes().contains(n3)) {
                    mutableGraph.putEdge(n2, n3);
                }
            }
        }
        return mutableGraph;
    }

    /* JADX WARN: Type inference failed for: r4v4, types: [java.lang.Object] */
    public static <N, V> MutableValueGraph<N, V> inducedSubgraph(ValueGraph<N, V> graph, Iterable<? extends N> nodes) {
        MutableValueGraph<N, V> mutableValueGraph;
        if (nodes instanceof Collection) {
            mutableValueGraph = ValueGraphBuilder.from(graph).expectedNodeCount(((Collection) nodes).size()).build();
        } else {
            mutableValueGraph = ValueGraphBuilder.from(graph).build();
        }
        for (N n : nodes) {
            mutableValueGraph.addNode(n);
        }
        for (N n2 : mutableValueGraph.nodes()) {
            for (N n3 : graph.successors((ValueGraph<N, V>) n2)) {
                if (mutableValueGraph.nodes().contains(n3)) {
                    mutableValueGraph.putEdgeValue(n2, n3, j$.util.Objects.requireNonNull(graph.edgeValueOrDefault(n2, n3, null)));
                }
            }
        }
        return mutableValueGraph;
    }

    public static <N, E> MutableNetwork<N, E> inducedSubgraph(Network<N, E> network, Iterable<? extends N> nodes) {
        MutableNetwork<N, E> mutableNetwork;
        if (nodes instanceof Collection) {
            mutableNetwork = NetworkBuilder.from(network).expectedNodeCount(((Collection) nodes).size()).build();
        } else {
            mutableNetwork = NetworkBuilder.from(network).build();
        }
        for (N n : nodes) {
            mutableNetwork.addNode(n);
        }
        for (N n2 : mutableNetwork.nodes()) {
            for (E e : network.outEdges(n2)) {
                N adjacentNode = network.incidentNodes(e).adjacentNode(n2);
                if (mutableNetwork.nodes().contains(adjacentNode)) {
                    mutableNetwork.addEdge(n2, adjacentNode, e);
                }
            }
        }
        return mutableNetwork;
    }

    public static <N> MutableGraph<N> copyOf(Graph<N> graph) {
        MutableGraph<N> mutableGraph = (MutableGraph<N>) GraphBuilder.from(graph).expectedNodeCount(graph.nodes().size()).build();
        for (N n : graph.nodes()) {
            mutableGraph.addNode(n);
        }
        for (EndpointPair<N> endpointPair : graph.edges()) {
            mutableGraph.putEdge(endpointPair.nodeU(), endpointPair.nodeV());
        }
        return mutableGraph;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <N, V> MutableValueGraph<N, V> copyOf(ValueGraph<N, V> graph) {
        MutableValueGraph<N, V> mutableValueGraph = (MutableValueGraph<N, V>) ValueGraphBuilder.from(graph).expectedNodeCount(graph.nodes().size()).build();
        for (N n : graph.nodes()) {
            mutableValueGraph.addNode(n);
        }
        for (EndpointPair<N> endpointPair : graph.edges()) {
            mutableValueGraph.putEdgeValue(endpointPair.nodeU(), endpointPair.nodeV(), j$.util.Objects.requireNonNull(graph.edgeValueOrDefault(endpointPair.nodeU(), endpointPair.nodeV(), null)));
        }
        return mutableValueGraph;
    }

    public static <N, E> MutableNetwork<N, E> copyOf(Network<N, E> network) {
        MutableNetwork<N, E> mutableNetwork = (MutableNetwork<N, E>) NetworkBuilder.from(network).expectedNodeCount(network.nodes().size()).expectedEdgeCount(network.edges().size()).build();
        for (N n : network.nodes()) {
            mutableNetwork.addNode(n);
        }
        for (E e : network.edges()) {
            EndpointPair<N> incidentNodes = network.incidentNodes(e);
            mutableNetwork.addEdge(incidentNodes.nodeU(), incidentNodes.nodeV(), e);
        }
        return mutableNetwork;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int checkNonNegative(int value) {
        Preconditions.checkArgument(value >= 0, "Not true that %s is non-negative.", value);
        return value;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static long checkNonNegative(long value) {
        Preconditions.checkArgument(value >= 0, "Not true that %s is non-negative.", value);
        return value;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int checkPositive(int value) {
        Preconditions.checkArgument(value > 0, "Not true that %s is positive.", value);
        return value;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static long checkPositive(long value) {
        Preconditions.checkArgument(value > 0, "Not true that %s is positive.", value);
        return value;
    }
}
