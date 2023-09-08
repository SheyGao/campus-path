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

package pathfinder;

import pathfinder.parser.CampusBuilding;
import pathfinder.datastructures.Point;
import pathfinder.datastructures.Path;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class CampusMap implements ModelAPI {
    private List<CampusBuilding> buildings;
    private List<CampusPath> paths;
    private Map<String, String> buildingsNames;

    public CampusMap(List<CampusBuilding>buildings, List<CampusPath>paths){
        this.buildings = buildings;
        this.paths = paths;
        this.buildingsNames = new HashMap<>();

        for (CampusBuilding b: buildings){
            buildingsNames.put(b.getShortName(), b.getLongName());
        }
    }

    @Override
    public boolean shortNameExists(String shortName) {
        for (CampusBuilding b : buildings) {
            if (b.getShortName().equals(shortName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String longNameForShort(String shortName) {
        for (CampusBuilding b : buildings) {
            if (b.getShortName().equals(shortName)) {
                return b.getLongName();
            }
        }
        throw new IllegalArgumentException("short name provided does not exist");
    }

    @Override
    public Map<String, String> buildingNames() {
        for (CampusBuilding b : buildings) {
            if (!buildingsNames.containsKey(b.getShortName())) {
                buildingsNames.put(b.getShortName(), b.getLongName());
            }
        }
        return this.buildingsNames;
    }

    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        CampusBuilding startBuilding = null;
        CampusBuilding endBuilding = null;

        for (CampusBuilding b : buildings) {
            if (b.getShortName().equals(startShortName)) {
                startBuilding = b;
            }
            if (b.getShortName().equals(endShortName)) {
                endBuilding = b;
            }
        }

        if (startBuilding == null || endBuilding == null) {
            throw new IllegalArgumentException("Start or destination building not found.");
        }

        // Create graphMap and populate it with appropriate data
        Map<Point, Map<Point, Double>> graphMap = new HashMap<>();
        for (CampusPath path : paths) {
            Point startPoint = new Point(path.getX1(), path.getY1());
            Point endPoint = new Point(path.getX2(), path.getY2());
            double distance = path.getDistance();

            graphMap.computeIfAbsent(startPoint, k -> new HashMap<>()).put(endPoint, distance);
            graphMap.computeIfAbsent(endPoint, k -> new HashMap<>()).put(startPoint, distance);
        }

        // Create points for start and end buildings
        Point startPoint = new Point(startBuilding.getX(), startBuilding.getY());
        Point endPoint = new Point(endBuilding.getX(), endBuilding.getY());

        DAlgo<Point> algorithm = new DAlgo<>(startPoint, endPoint);
        Path<Point> shortestPath = algorithm.method(graphMap);

        return shortestPath;
    }






}
