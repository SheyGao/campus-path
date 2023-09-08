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

import React, {Component} from 'react';

interface Edge {
    x1: number;
    y1: number;
    x2: number;
    y2: number;
    color: string;
}

interface EdgeListProps {
    onChange: (edges: Edge[]) => void;
    clear : () => void;
}

interface EdgeListState {
    inputEdge: string;
    edgeList : Edge[];
}

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps, EdgeListState> {

    constructor(props: EdgeListProps) {
        super(props);

        this.state = {
            inputEdge: ``,
            edgeList : []
        };
    }

    updateText = (edge: string) => {
        this.setState({inputEdge: edge});
    };

    handleDrawClick = () => {
        let edgeLines = this.state.inputEdge.split('\n');
        let edges: Edge[] = edgeLines.map((edgeLine, index) => {
            edgeLine = edgeLine.trim();
            let [ x1, y1, x2, y2, color] = edgeLine.split(' ');

            if (x1 === undefined || y1 === undefined || x2 === undefined || y2 === undefined || color === undefined) {
                throw new Error(`Invalid input format at line ${index + 1}. Use the format: x1 y1 x2 y2 COLOR`);
            }

            let range = (value: number, min: number, max: number) =>
                Math.min(Math.max(value, min), max);

            let X1 = range(Number(x1), 0, 4000);
            let Y1 = range(Number(y1), 0, 4000);
            let X2 = range(Number(x2), 0, 4000);
            let Y2 = range(Number(y2), 0, 4000);

            if (Number(x1) !== X1 || Number(y1) !== Y1 || Number(x2) !== X2 || Number(y2) !== Y2) {
                throw new Error('Coordinate out of range.');
            }

            return {
                x1: X1,
                y1: Y1,
                x2: X2,
                y2: Y2,
                color: color,
            };
        });
        this.props.onChange(edges);
    };

    render() {
        return (
            <div id="edge-list">
                Edges <br/>
                <textarea
                    rows={5}
                    cols={30}
                    onChange={(event: any) => this.updateText(event.target.value)}
                    value={this.state.inputEdge}
                /> <br/>
                <button onClick={() => this.handleDrawClick()}>Draw</button>
                <button onClick={() => this.updateText("") }>Clear</button>
                <button onClick={() => {this.props.clear()}}>Clear Map</button>
            </div>
        );
    }
}

export default EdgeList;
