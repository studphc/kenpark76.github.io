package com.google.common.graph;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import j$.util.Objects;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
/* JADX INFO: Access modifiers changed from: package-private */
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public class StandardNetwork<N, E> extends AbstractNetwork<N, E> {
    private final boolean allowsParallelEdges;
    private final boolean allowsSelfLoops;
    private final ElementOrder<E> edgeOrder;
    final MapIteratorCache<E, N> edgeToReferenceNode;
    private final boolean isDirected;
    final MapIteratorCache<N, NetworkConnections<N, E>> nodeConnections;
    private final ElementOrder<N> nodeOrder;

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.PredecessorsFunction
    public /* bridge */ /* synthetic */ Iterable predecessors(Object node) {
        return predecessors((StandardNetwork<N, E>) node);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.SuccessorsFunction
    public /* bridge */ /* synthetic */ Iterable successors(Object node) {
        return successors((StandardNetwork<N, E>) node);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public StandardNetwork(NetworkBuilder<? super N, ? super E> builder) {
        this(builder, builder.nodeOrder.createMap(builder.expectedNodeCount.or((Optional<Integer>) 10).intValue()), builder.edgeOrder.createMap(builder.expectedEdgeCount.or((Optional<Integer>) 20).intValue()));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public StandardNetwork(NetworkBuilder<? super N, ? super E> builder, Map<N, NetworkConnections<N, E>> nodeConnections, Map<E, N> edgeToReferenceNode) {
        MapIteratorCache<N, NetworkConnections<N, E>> mapIteratorCache;
        this.isDirected = builder.directed;
        this.allowsParallelEdges = builder.allowsParallelEdges;
        this.allowsSelfLoops = builder.allowsSelfLoops;
        this.nodeOrder = (ElementOrder<N>) builder.nodeOrder.cast();
        this.edgeOrder = (ElementOrder<E>) builder.edgeOrder.cast();
        if (nodeConnections instanceof TreeMap) {
            mapIteratorCache = new MapRetrievalCache<>(nodeConnections);
        } else {
            mapIteratorCache = new MapIteratorCache<>(nodeConnections);
        }
        this.nodeConnections = mapIteratorCache;
        this.edgeToReferenceNode = new MapIteratorCache<>(edgeToReferenceNode);
    }

    @Override // com.google.common.graph.Network
    public Set<N> nodes() {
        return this.nodeConnections.unmodifiableKeySet();
    }

    @Override // com.google.common.graph.Network
    public Set<E> edges() {
        return this.edgeToReferenceNode.unmodifiableKeySet();
    }

    @Override // com.google.common.graph.Network
    public boolean isDirected() {
        return this.isDirected;
    }

    @Override // com.google.common.graph.Network
    public boolean allowsParallelEdges() {
        return this.allowsParallelEdges;
    }

    @Override // com.google.common.graph.Network
    public boolean allowsSelfLoops() {
        return this.allowsSelfLoops;
    }

    @Override // com.google.common.graph.Network
    public ElementOrder<N> nodeOrder() {
        return this.nodeOrder;
    }

    @Override // com.google.common.graph.Network
    public ElementOrder<E> edgeOrder() {
        return this.edgeOrder;
    }

    @Override // com.google.common.graph.Network
    public Set<E> incidentEdges(N node) {
        return checkedConnections(node).incidentEdges();
    }

    @Override // com.google.common.graph.Network
    public EndpointPair<N> incidentNodes(E edge) {
        N checkedReferenceNode = checkedReferenceNode(edge);
        return EndpointPair.of(this, checkedReferenceNode, ((NetworkConnections) Objects.requireNonNull(this.nodeConnections.get(checkedReferenceNode))).adjacentNode(edge));
    }

    @Override // com.google.common.graph.Network
    public Set<N> adjacentNodes(N node) {
        return checkedConnections(node).adjacentNodes();
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
    public Set<E> edgesConnecting(N nodeU, N nodeV) {
        NetworkConnections<N, E> checkedConnections = checkedConnections(nodeU);
        if (!this.allowsSelfLoops && nodeU == nodeV) {
            return ImmutableSet.of();
        }
        Preconditions.checkArgument(containsNode(nodeV), "Node %s is not an element of this graph.", nodeV);
        return checkedConnections.edgesConnecting(nodeV);
    }

    @Override // com.google.common.graph.Network
    public Set<E> inEdges(N node) {
        return checkedConnections(node).inEdges();
    }

    @Override // com.google.common.graph.Network
    public Set<E> outEdges(N node) {
        return checkedConnections(node).outEdges();
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.PredecessorsFunction
    public Set<N> predecessors(N node) {
        return checkedConnections(node).predecessors();
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.SuccessorsFunction
    public Set<N> successors(N node) {
        return checkedConnections(node).successors();
    }

    final NetworkConnections<N, E> checkedConnections(N node) {
        NetworkConnections<N, E> networkConnections = this.nodeConnections.get(node);
        if (networkConnections != null) {
            return networkConnections;
        }
        Preconditions.checkNotNull(node);
        throw new IllegalArgumentException(String.format("Node %s is not an element of this graph.", node));
    }

    final N checkedReferenceNode(E edge) {
        N n = this.edgeToReferenceNode.get(edge);
        if (n != null) {
            return n;
        }
        Preconditions.checkNotNull(edge);
        throw new IllegalArgumentException(String.format("Edge %s is not an element of this graph.", edge));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean containsNode(N node) {
        return this.nodeConnections.containsKey(node);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean containsEdge(E edge) {
        return this.edgeToReferenceNode.containsKey(edge);
    }
}
