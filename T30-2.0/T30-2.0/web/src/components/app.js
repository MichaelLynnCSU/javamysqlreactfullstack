import React from 'react';
import Home from './Home/Home.jsx';
import Pair from './Home/Pair/Pair.jsx';


export default class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            allPairs: [],
            sysFile: []
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
                    pairs={ps}
                />
            </div>
        )
    }

    async browseFile(file) {
        console.log("Got file:", file);
        //For loop that goes through all pairs,
        let pairs = [];
		var myKeys = [];

		var count = 0;
		for(var key in file[0]) {
		console.log("in here i am", key);
		myKeys[count] = key;
		count++
		};
		
		console.log("Keys", myKeys);		
		console.log(myKeys[0], file[0][myKeys[0]]);
		
		
        for (let i = 0; i < Object.values(file).length; i++) {
        	var obj = [];
		for (let j = 0; j < 11; j++) {
              obj[myKeys[j]] = file[i][myKeys[j]];
		}
            
            pairs.push(obj); //add object to pairs array
            console.log("Pushing pair: ", obj); //log to console
        }

        //Here we will update the state of app.
        // Anything component (i.e. pairs) referencing it will be re-rendered
        this.setState({
            allPairs: pairs,
            sysFile: file
        });
    }
}
