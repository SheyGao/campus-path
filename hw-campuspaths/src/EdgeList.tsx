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

// define interface EdgeListProps
interface EdgeListProps {
    clear : () => void;
    onDrawClick: (start: string, end: string) => void;
}

// define interface EdgeListState
interface EdgeListState {
    start: string;
    end: string;
}

/**
 * A text field that allows the user to enter the start and end building short names.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps, EdgeListState> {
    constructor(props: EdgeListProps) {
        super(props);

        // initialize the text box
        this.state = {
            start: ``,
            end: '',
        };
    }

    // update the text box for new string input
    updateText = (start: string, end: string) => {
        this.setState({start: start, end: end});
    };

    render() {
        return (
            <div id="edge-list">
                Start Building:
                <input
                    type="text"
                    value={this.state.start}
                    onChange={(event) => this.setState({ start: event.target.value })}
                />
                <br />
                End Building:
                <input
                    type="text"
                    value={this.state.end}
                    onChange={(event) => this.setState({ end: event.target.value })}
                />
                <br />
                <button
                    onClick={() => {
                        if (this.state.start && this.state.end) {
                            this.props.onDrawClick(this.state.start, this.state.end);
                        }
                    }}
                >Draw Path</button>
                <button onClick={() => { this.props.clear(); }}>Clear Map</button>
                <button onClick={() => {this.updateText('','');}}>Clear Input</button>
            </div>
        );
    }
}

export default EdgeList;
