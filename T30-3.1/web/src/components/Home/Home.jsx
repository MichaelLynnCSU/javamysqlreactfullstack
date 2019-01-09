import React, {Component} from 'react';
import Dropzone from 'react-dropzone';



// highest level component
class Home extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            exclude: ['start', 'end', 'distance'],
            serverReturned: null
        }
    };

    render() {

        let total = 0; //update the total here
		let title = "T30 - Consilium";
		this.props.pairs.map(listValue => total += listValue.props.distance);

		let serverLocations;
        let locs;
        let svg;

        if (this.state.serverReturned) {
            serverLocations = this.state.serverReturned.locations;
            //locs = serverLocations.map((location) => {
            //    return <li>{location.name}</li>;
            //});
            console.log(serverLocations);
            this.props.browseFile(serverLocations, this.state.exclude);
            svg = this.state.serverReturned.svg;
        }

        return (<div className="home-container">
         <div className="inner">

				<h4>{title}</h4>

			<h3>Itinerary</h3>

				<img src={svg}
				alt="Map of Colorado"
				height="512px"
				width="640px"/>

        <br />
        {this.createButtons(this.props.keys.slice())}
        <br />

        <input type="text" type="text" id="q" placeholder="Search..." onKeyDown={this.fetch.bind(this)}/>

		<table><tr><td>
            <table className="pair-table">
			    <tbody>
					{this.props.pairs}
				</tbody>
			</table>
	    </td></tr></table>

		<table className="pair-table">
					   <tbody>
					<tr><td>
				Total: {total}
				</td></tr>
			</tbody>
			</table>

           </div>
       </div>)
    }

    drop(acceptedFiles)
	{
        console.log("Accepting drop");

	   acceptedFiles.forEach(file =>
	   {
            console.log("Filename:", file.name, "File:", file);
            console.log(JSON.stringify(file));
            let fr = new FileReader();
            fr.onload = (function ()
			{
				return function (e)
						{

							let JsonObj = JSON.parse(e.target.result);
							console.log(JsonObj);
							this.props.browseFile(JsonObj, this.state.exclude);

						};
            })(file).bind(this);

            fr.readAsText(file);
        });
	}

	createButtons(keys) {
	    if (keys.length == 0){
	        return;
	    }
	    var rm = ["start", 'end', 'distance'];
	    for(let i = 0; i < rm.length; i++){
                if (keys.includes(rm[i])){
                    keys.splice(keys.indexOf(rm[i]), 1);
                }
            }
	    var key = keys.pop();
	    if (keys.length == 0) {
            return (<button onClick={this.handleClick.bind(this, key)}> Toggle {key} </button>);
        }
        return (<b> <button onClick={this.handleClick.bind(this, key)}> Toggle {key} </button> {this.createButtons(keys)} </b>);
	}

	handleClick(key, e) {
	        var s_exclude = this.state.exclude;
	        if (s_exclude.includes(key)){
	            s_exclude.pop(key);
	        } else {
                s_exclude.push(key);
            }
            this.setState({
                exclude: s_exclude
            });
            this.props.renderFile();
        }

    async fetch(input) {
        console.log(this.state);
        if (input.keyCode == 13){
            input.persist();
            console.log("Query:", input.target.value);
            let newMap = {
                query: input.target.value,
                id: "1",
            };
            //try {
                let jsonReturned = await fetch("http://localhost:4567/testing",
                    {
                        method: "POST",
                        body: JSON.stringify(newMap)
                    });
                let ret = await jsonReturned.json();
                console.log("Got back ", JSON.parse(ret));
                this.setState({
                    serverReturned: JSON.parse(ret)
                });
            //} catch (e) {
                //console.error("Error talking to server");
                //console.error(e);
            //}
            input.target.value = '';
        }
    }
}

export default Home
