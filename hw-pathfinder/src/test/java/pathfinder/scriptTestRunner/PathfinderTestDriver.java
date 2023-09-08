/*
 * Copyright (C) 2023 Soham Pardeshi.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Autumn Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package pathfinder.scriptTestRunner;

import graph.LabeledGraph;
import pathfinder.DAlgo;
import pathfinder.datastructures.Path;

import java.io.*;
import java.util.*;

/**
 * This class implements a test driver that uses a script file format
 * to test an implementation of Dijkstra's algorithm on a graph.
 */
public class PathfinderTestDriver {
    private final Map<String, LabeledGraph<String, Double>> graphs = new HashMap<String, LabeledGraph<String, Double>>();

    private final PrintWriter output;
    private final BufferedReader input;

    // Leave this constructor public
    public PathfinderTestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    public void runTests() throws IOException {
        String inputLine;
        while((inputLine = input.readLine()) != null) {
            if((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if(st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<>();
                    while(st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }
    // Leave this method public

    /**
     * @throws IOException if the input or output sources encounter an IOException
     * @spec.effects Executes the commands read from the input and writes results to the output
     **/
    // Leave this method public

    private void executeCommand(String command, List<String> arguments) {
        try {
            switch(command) {
                case "CreateGraph":
                    createGraph(arguments);
                    break;
                case "AddNode":
                    addNode(arguments);
                    break;
                case "AddEdge":
                    addEdge(arguments);
                    break;
                case "ListNodes":
                    listNodes(arguments);
                    break;
                case "ListChildren":
                    listChildren(arguments);
                    break;
                case "FindPath":
                    findPath(arguments);
                    break;
                default:
                    output.println("Unrecognized command: " + command);
                    break;
            }
        } catch(Exception e) {
            String formattedCommand = command;
            formattedCommand += arguments.stream().reduce("", (a, b) -> a + " " + b);
            output.println("Exception while running command: " + formattedCommand);
            e.printStackTrace(output);
        }
    }

    private void createGraph(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {

        graphs.put(graphName, new LabeledGraph<String, Double>());
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {

        LabeledGraph<String, Double> graph = graphs.get(graphName);
        LabeledGraph<String, Double>.Nodes node = graph.new Nodes (nodeName);
        graph.addNode(node);
        output.println("added node " + nodeName + " to " + graphName);

    }

    private void addEdge(List<String> arguments) {
        if(arguments.size() != 4) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        Double edgeLabel = Double.parseDouble(arguments.get(3));

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
                         Double edgeLabel) {

        LabeledGraph<String, Double> graph = graphs.get(graphName);
        LabeledGraph<String, Double>.Nodes parentNode = graph.new Nodes(parentName);
        LabeledGraph<String, Double>.Nodes childNode = graph.new Nodes(childName);
        graph.addEdge(parentNode, childNode, edgeLabel);
        String label = String.format("%.3f", edgeLabel);
        output.println("added edge " + label + " from " + parentName + " to " + childName + " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to ListNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {

        LabeledGraph<String, Double> graph = graphs.get(graphName);
        String res = "";

        HashSet<LabeledGraph<String, Double>.Nodes> nodes = graph.getNodes();
        for (LabeledGraph<String, Double>.Nodes node : nodes) {
            res += " " + node;
        }

        output.println(graphName + " contains:" + res);
    }

    private void listChildren(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {

        LabeledGraph<String, Double> graph = graphs.get(graphName);
        String res = "";

        LabeledGraph<String, Double>.Nodes parentNode = graph.new Nodes(parentName);
        HashSet<LabeledGraph<String, Double>.Edges> edges = graph.getEdges(parentNode);
        for (LabeledGraph<String, Double>.Edges e : edges) {
            Double label = e.getLabel();
            String stringLabel = String.format("%.3f", label);
            res += " " + e.getChild() + "(" + stringLabel + ")";
        }

        output.println("the children of " + parentName + " in " + graphName + " are:" + res);

    }

    private void findPath(List<String> arguments) {
        if(arguments.size() != 3) {
            throw new CommandException("Bad arguments to ListNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        findPath(graphName, parentName, childName);
    }

    private void findPath(String graphName, String startName, String destName) {
        LabeledGraph<String, Double> graph = graphs.get(graphName);
        LabeledGraph<String, Double>.Nodes startNode = graph.new Nodes(startName);
        LabeledGraph<String, Double>.Nodes destNode = graph.new Nodes(destName);

        Map<String, Map<String, Double>> graphMap = new HashMap<>();

        for (LabeledGraph<String, Double>.Nodes parent : graph.getNodes()) {
            Map<String, Double> childMap = new HashMap<>();
            for (LabeledGraph<String, Double>.Edges edge : graph.getEdges(parent)) {
                childMap.put(edge.getChild().getData(), edge.getLabel());
            }
            graphMap.put(parent.getData(), childMap);
        }

        if (!graph.hasNode(startNode) || !graph.hasNode(destNode)) {
            output.println("path from " + startName + " to " + destName + ":");
            output.println("unknown: " + (graph.hasNode(startNode) ? destName : startName));
            output.println("unknown: " + (graph.hasNode(destNode) ? startName : destName));
        } else {
            DAlgo<String> algorithm = new DAlgo<>(startName, destName);
            Path<String> path = algorithm.method(graphMap);

            if (path == null) {
                output.println("path from " + startName + " to " + destName + ":");
                output.println("no path found");
            } else {
                Double totalCost = 0.000;
                output.println("path from " + startName + " to " + path.getEnd() + ":");

                for (Path<String>.Segment segment : path) {
                    String from = segment.getStart();
                    String to = segment.getEnd();

                    double edgeWeight = graph.getEdgeWeight(graph.new Nodes(from), graph.new Nodes(to));

                    output.println(from + " to " + to + " with weight " + String.format("%.3f", edgeWeight));
                    totalCost += edgeWeight;
                }

                output.println("total cost: " + String.format("%.3f", totalCost));
            }
        }
    }



    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }







}

