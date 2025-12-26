package com.google.common.graph;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public interface MutableNetwork<N, E> extends Network<N, E> {
    boolean addEdge(EndpointPair<N> endpoints, E edge);

    boolean addEdge(N nodeU, N nodeV, E edge);

    boolean addNode(N node);

    boolean removeEdge(E edge);

    boolean removeNode(N node);
}
