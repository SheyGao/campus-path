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

package campuspaths;

import campuspaths.utils.CORSFilter;
import pathfinder.CampusMap;
import pathfinder.datastructures.Point;
import pathfinder.datastructures.Path;
import pathfinder.parser.CampusPathsParser;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SparkServer {

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        // The above two lines help set up some settings that allow the
        // React application to make requests to the Spark server, even though it
        // comes from a different server.

        // create an instance of CampusMap that you can use to fulfill requests that are
        // sent to your server.
        String buildingsFile = "campus_buildings.csv";
        String pathsFile = "campus_paths.csv";
        List<CampusBuilding> buildings = CampusPathsParser.parseCampusBuildings(buildingsFile);
        List<CampusPath> paths = CampusPathsParser.parseCampusPaths(pathsFile);
        CampusMap map = new CampusMap(buildings, paths);

        // findPath route
        Spark.get("/findPath", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {

                String start = request.queryParams("start");
                String end = request.queryParams("end");

                // check if the user input of start building is a valid short name
                boolean startExists = map.shortNameExists(start);
                if (!startExists){
                    Spark.halt(400, "The input start building does not exist or it's not the correct short name of it.");
                }

                // check if the user input of end building is a valid short name
                boolean endExists = map.shortNameExists(end);
                if(!endExists) {
                    Spark.halt(400, "The input end building does not exist or it's not the correct short name of it");
                }

                // Call findShortestPath method from the CampusMap
                Path<Point> shortestPath = map.findShortestPath(start, end);

                // Gson is Google's library for dealing with JSON. It'll take any Java Object
                // and convert it to a JSON string that represents the data inside that object.
                Gson gson = new Gson();
                String jsonResponse = gson.toJson(shortestPath);
                return jsonResponse;
            }
        });



    }

}
