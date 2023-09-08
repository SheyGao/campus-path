package pathfinder;

import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import java.util.*;

public class DAlgo<T> {
    private T start;
    private T dest;
    private PriorityQueue<Path<T>> active;
    private Set<T> finished;
    private Path<T> shortestPath; // Store the shortest path found

    public DAlgo(T start, T dest) {
        this.start = start;
        this.dest = dest;
        this.active = new PriorityQueue<>();
        this.finished = new HashSet<>();
        this.shortestPath = null;
    }

    public Path<T> method(Map<T, Map<T, Double>> graph) {
        active.add(new Path<>(start));

        while (!active.isEmpty()) {
            Path<T> minPath = active.poll();
            T minDest = minPath.getEnd();

            if (minDest.equals(dest)) {
                if (shortestPath == null || minPath.getCost() < shortestPath.getCost()) {
                    shortestPath = minPath; // Update shortestPath if a shorter path is found
                }
            }

            if (finished.contains(minDest)) {
                continue;
            }

            for (T child : graph.get(minDest).keySet()) {
                if (!finished.contains(child)) {
                    double w = graph.get(minDest).get(child);
                    double totalCost = minPath.getCost() + w;
                    Path<T> newPath = minPath.extend(child, totalCost);
                    active.add(newPath);
                }
            }
            finished.add(minDest);
        }

        return shortestPath;
    }
}
