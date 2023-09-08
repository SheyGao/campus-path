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

import React, { Component } from "react";
import EdgeList from "./EdgeList";
import Map from "./Map";

// Allows us to write CSS styles inside App.css, any styles will apply to all components inside <App />
import "./App.css";

interface Edge {
    color: string;
    x1: number;
    y1: number;
    x2: number;
    y2: number;
}

interface AppState {
    edges: Edge[];
}

class App extends Component<{}, AppState> { // <- {} means no props.

  constructor(props: any) {
    super(props);

    this.state = {
      edges:[],
    };
  }

  updateState (additionalEdges: Edge[]) {
      let updatedEdges: Edge[] = this.state.edges;
      for (let i = 0; i < additionalEdges.length; i++) {
          updatedEdges.push(additionalEdges[i]);
      }
      this.setState({
          edges: updatedEdges
      });
      console.log('Finished updating edges.')

  }

    clear() : void {
        this.setState({
            edges: []
        });
        console.log('Finished clearing map.');
    }

    render() {
    return (
      <div>
        <h1 id="app-title">Line Mapper!</h1>
        <div>
          <Map edges={this.state.edges}/>
        </div>
        <EdgeList
            clear={() => {this.clear()}}
            onChange={(edges) => {
            this.updateState(edges)
          }}

        />
      </div>
    );
  }
}

export default App;
