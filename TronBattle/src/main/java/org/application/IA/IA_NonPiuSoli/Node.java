package org.application.IA.IA_NonPiuSoli;

import java.util.*;

public class Node implements Comparable<Node> {
    public final int x, y;
    public int gCost, hCost;
    public Node parent;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getFCost() {
        return gCost + hCost;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.getFCost(), other.getFCost());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x && y == node.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}