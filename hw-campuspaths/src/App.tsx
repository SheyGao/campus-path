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

import React, {Component} from "react";
import Map from "./Map";
import EdgeList from "./EdgeList";

// Allows us to write CSS styles inside App.css, any styles will apply to all components inside <App />
import "./App.css";

// define interface Edge
interface Edge {
    color: string;
    x1: number;
    y1: number;
    x2: number;
    y2: number;
}

// define interface AppState
interface AppState {
    edges: Edge[];
    start: string;
    end: string;
}

class App extends Component<{}, AppState> { // <- {} means no props.

    constructor(props: any) {
        super(props);

        // initialize the state
        this.state = {
            edges: [],
            start: "",
            end: "",
        };
    }

    // update the state by adding the new input edge into existing Edge[]
    updateState = (additionalEdges: Edge[]) => {
        let updatedEdges: Edge[] = this.state.edges;
        for (let i = 0; i < additionalEdges.length; i++) {
            updatedEdges.push(additionalEdges[i]);
        }
        this.setState({
            edges: updatedEdges
        });
        console.log('Finished updating edges.')
    }

    // clear map
    clear = (): void => {
        this.setState({
            edges: [],
            start: "",
            end: "",
        });
        console.log('Finished clearing map.');
    }

    // draw line for each Edge[]
    handleDrawClick = async (start: string, end: string) => {
        try {
            // Fetch path segments
            let responsePathSegments = await fetch(
                "http://localhost:4567/findPath?start=" + start + "&end=" + end
            );

            if (!responsePathSegments.ok) {
                alert("The provided short name of the building does not exist.");
                return;
            }
            let pathSegments = await responsePathSegments.json();

            let pathEdges: Edge[] = pathSegments.path.map((segment: any) => ({
                x1: segment.start.x,
                y1: segment.start.y,
                x2: segment.end.x,
                y2: segment.end.y,
                color: 'blue',
            }));
            this.updateState(pathEdges);
        } catch (error) {
            // console.error(error);
            alert("Error fetching data");
            // console.log(error);
        }
    }


    render() {
        return (
            <div>
                <h1 id="app-title">Line Mapper!</h1>
                <div>
                    <Map edges={this.state.edges}/>
                </div>
                <div>
                    <EdgeList
                        clear={this.clear}
                        onDrawClick={this.handleDrawClick}
                    />
                </div>
            </div>
        );
    }


}

export default App;
