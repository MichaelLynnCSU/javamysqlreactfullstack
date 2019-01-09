import React from 'react';
import Home from './Home/Home.jsx';
import Pair from './Home/Pair/Pair.jsx';


export default class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            allPairs: [],
            sysFile: [],
            keys: []
        }
    };

    render() {
        let pairs = this.state.allPairs;
        let ps = pairs.map((pp) => {
            return <Pair {...pp}/>;
        });
        return (
            <div className="app-container">
                <Home
                    browseFile={this.browseFile.bind(this)}
                    renderFile={this.renderFile.bind(this)}
                    pairs={ps}
                    keys={this.state.keys}
                />
            </div>
        )
    }

    async browseFile(file, exclude) {
        console.log("Got file:", file);
        //For loop that goes through all pairs,
        let pairs = [];
		let myKeys = [];

		var count = 0;
		for(var key in file[0]) {
		    console.log("in here i am", key);
		    myKeys[count] = key;
		    count++
		};

		console.log(myKeys[0], file[0][myKeys[0]]);
		
		
        for (let i = 0; i < Object.values(file).length; i++) {
        	var obj = [];
		for (let j = 0; j < myKeys.length; j++) {
              obj[myKeys[j]] = file[i][myKeys[j]];
		}
            obj['keys'] = myKeys;
            obj['exclude_keys'] = exclude;
            obj['all'] = obj;
            pairs.push(obj); //add object to pairs array
        }

        //Here we will update the state of app.
        // Anything component (i.e. pairs) referencing it will be re-rendered
        this.setState({
            allPairs: pairs,
            sysFile: file,
            keys: myKeys
        });
    }

    async renderFile() {
        this.setState({
            allPairs: this.state.allPairs,
            sysFile: this.state.sysFile,
            keys: this.state.keys
        });
    }
}
