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

package pathfinder.textInterface;

import pathfinder.CampusMap;
import pathfinder.parser.CampusPathsParser;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import java.util.List;

/**
 * Pathfinder represents a complete application capable of responding to user prompts to provide
 * a variety of information about campus buildings and paths between them.
 */
public class Pathfinder {

    // This class does not represent an ADT.

    /**
     * The main entry point for this application. Initializes and launches the application.
     *
     * @param args The command-line arguments provided to the system.
     */



    public static void main(String[] args) {
        String buildingsFilePath = "campus_buildings.csv";
        String pathsFilePath = "campus_paths.csv";

        List<CampusBuilding> buildings = CampusPathsParser.parseCampusBuildings(buildingsFilePath);
        List<CampusPath> paths = CampusPathsParser.parseCampusPaths(pathsFilePath);

        CampusMap map = new CampusMap(buildings, paths);

        TextInterfaceView view = new TextInterfaceView();
        TextInterfaceController controller = new TextInterfaceController(map, view);

        view.setInputHandler(controller);
        controller.launchApplication();
    }

}
