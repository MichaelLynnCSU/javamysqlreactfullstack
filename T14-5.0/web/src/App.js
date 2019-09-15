import React, { Component } from 'react';
import Itinerary from './Components/Itinerary';
import LoadJSON from './Components/LoadJSON';
import './App.scss';
import Map from './Components/SampleMap';
import Dropzone from 'react-dropzone';
let ReactCSSTransitionGroup = require('react-addons-css-transition-group'); 

class App extends Component {
	constructor(){
		super();
	  	this.toggleMenu = this.toggleMenu.bind(this);
	  	this.downMenu = this.downMenu.bind(this);
		this.url = "http://localhost:4567";
	  	this.state = {
			itinerary: [],
		  	allPairs: [],
		  	columns: [],
		  	total: 0,
		  	query: "",
		  	queryResults: {locations: []},
		  	dataSend: {locations: []},
		  	dataSendFriendly: {locations: []},
			menuActive: false,
		  	taskActive: false,
			milesKilo: 1,
		  	opt: 2,
		  	sysFile: null,
			menuActive: false,
			selectedOption: 'option1',
			startLoc: "",
	  }

	  this.handleItemClick = this.handleItemClick.bind(this);
	  this.handleItemDeselect = this.handleItemDeselect.bind(this);
	  this.checkIfIn = this.checkIfIn.bind(this);
  	}	
    toggleMenu() {
        let menuState = !this.state.menuActive;
        this.setState({
            menuActive: menuState
        });
    }	
    downMenu() {
        let menuSt = !this.state.taskActive;
        this.setState({
            taskActive: menuSt
        });
    }
	setQuery(e){
		this.setState({query: e.target.value});
	}
	sendQuery(){
		this.Queryfetch(this.state.query);
	}
	async Queryfetch(type, input) {
		let data = {
			query: this.state.query,
			id: 1
		}
        try {
            let jsonReturned = await fetch(this.url + "/api/testing",
                {
                    method: "POST",
                    body: JSON.stringify(data)
                });

			let ret = await jsonReturned.json();
            let returnedJson = JSON.parse(ret);

			this.setState({queryResults: returnedJson});
        } catch (e) {
            console.error("Error talking to server");
            console.error(e);
        }
   }
	searchStringInArray (strArray) {
             if (strArray.match(/Fort/gi)) return strArray;
        return -1;
    }
	async fetchItinerary() {
		let queryData = [];
		let startingLoc = [];
		console.log(this.state.dataSend);
		for (let i = 0 ; i < this.state.dataSend.locations.length ; i++){
			if(this.searchStringInArray(this.state.dataSendFriendly.locations[i].name) != -1)
                 startingLoc.push(this.searchStringInArray(this.state.dataSend.locations[i].name));
			queryData.push(this.state.dataSend.locations[i].id);
		}
		console.log(queryData);
		let data = {
			query: queryData,
			opt: this.state.opt,
			milesKilo: this.state.milesKilo,
            startingLoc: startingLoc
		}

		try {
			let jsonReturned = await fetch(this.url + "/api/build",
				{
					method: "POST",
					body: JSON.stringify(data)
				});

			let ret = await jsonReturned.json();
			let returnedJson = ret;

			let cords=[];
			for (let i = 0 ; i < returnedJson.itinerary.length ; i++){
				cords.push({lat: parseFloat(returnedJson.itinerary[i].startData.latitude), lng: parseFloat(returnedJson.itinerary[i].startData.longitude)});
			}
			cords.push({lat: parseFloat(returnedJson.itinerary[0].startData.latitude), lng: parseFloat(returnedJson.itinerary[0].startData.longitude)});

			this.setState({columns: returnedJson.columns});
			this.setState({allPairs: returnedJson.itinerary});
			this.setState({cords: cords});
		} catch (e) {
			console.error("Error talking to server");
			console.error(e);
		}
	}
	async fetchQueryResults() {
		let queryData = [];

		try {
			for (let i = 0 ; i < this.state.sysFile.destinations.length; i++){
				queryData.push(this.state.sysFile.destinations[i]);
			}

			let data = {
				query: queryData,
			}

			let jsonReturned = await fetch(this.url + "/api/load",
				{
					method: "POST",
					body: JSON.stringify(data)
				});

			let ret = await jsonReturned.json();
			let returnedJson = ret;

			this.setState({dataSendFriendly: {locations: []}});
			this.setState({dataSendFriendly: returnedJson});
			let dataBack = {locations: []};

			for (let i = 0 ; i < returnedJson.locations.length ; i++){
				dataBack.locations.push({id: returnedJson.locations[i].id});
			}
			this.setState({dataSend: dataBack});
		} catch (e) {
			console.error("Error talking to server");
			console.error(e);
		}

	}
    async getFile() {

		let queryData = [];
        for (let i = 0 ; i <  this.state.allPairs.length ; i++){
            queryData.push(this.state.allPairs[i].startData.code);
        }
		
		let locationFile = {
			title: "My Trip",
			destinations: queryData
		};

		let locationID = JSON.stringify(locationFile);
		let pom = document.createElement('a');
		pom.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(locationID));
		pom.setAttribute('download', "download.json");

		if (document.createEvent) {
			let event = document.createEvent('MouseEvents');
			event.initEvent('click', true, true);
			pom.dispatchEvent(event);
		} else {
			pom.click();
		}
	}
	saveButtonClicked(event) {
        this.getFile();
    }
    handleSelectAll(e){
		let qr = this.state.queryResults;
		let dsf = this.state.dataSendFriendly;
		let ds = this.state.dataSend;
		for (let i = 0 ; i < qr.locations.length ; i++){
			if (this.checkIfIn(qr.locations[i].id)){
				ds.locations.push(qr.locations[i]);
				dsf.locations.push(qr.locations[i])
			}
		}
		this.setState({queryResults: {locations: []}, dataSendFriendly: dsf, dataSend: ds});
	}
	checkIfIn(id){
		for (let i = 0 ; i < this.state.dataSend.locations.length ; i++){
			if (id == this.state.dataSend.locations[i].id)
				return false;
		}
		return true;
	}	
	checkIfInQR(id){
		for (let i = 0 ; i < this.state.queryResults.locations.length ; i++){
			if (id == this.state.queryResults.locations[i].id)
				return false;
		}
		return true;
	}
	handleItemClick(id, name) {
		let dsf = this.state.dataSendFriendly;
		let ds = this.state.dataSend;
		let qr = this.state.queryResults;
		if (this.checkIfIn(id)){
			dsf.locations.push({id: id, name: name});
			ds.locations.push({id: id, name: name});
		}
		for (let i = 0 ; i < qr.locations.length ; i++){
			if (qr.locations[i].id == id)
				qr.locations.splice(i, 1);
		}
		this.setState({dataSendFriendly: dsf, dataSend: ds, queryResults: qr});
	}	
	handleItemDeselect(id, name) {
		let dsf = this.state.dataSendFriendly;
		let ds = this.state.dataSend;
		let qr = this.state.queryResults;
		if (this.checkIfInQR(id)){
			qr.locations.push({id: id, name: name});
		}
		for (let i = 0 ; i < ds.locations.length ; i++){
			if (ds.locations[i].id == id)
				ds.locations.splice(i, 1);
		}
		for (let i = 0 ; i < dsf.locations.length ; i++){
			if (dsf.locations[i].id == id)
				dsf.locations.splice(i, 1);
		}
		this.setState({dataSendFriendly: dsf, dataSend: ds, queryResults: qr});
	}	
	handleDeselectAll(){
		let qr = this.state.queryResults;
		let ds = this.state.dataSend;
		for (let i = 0 ; i < ds.locations.length ; i++){
			if (this.checkIfInQR(ds.locations[i].id)){
				qr.locations.push(ds.locations[i]);
			}
		}
		this.setState({queryResults: qr, dataSendFriendly: {locations: []}, dataSend: {locations: []}});
	}     
    handleAlgoButtons(e){
        this.setState({opt: e.target.value});
        this.setState({selectedOption: e.target.value});
    }
    uploadButtonClicked(acceptedFiles) {
        acceptedFiles.forEach(file => {
            let fr = new FileReader();
            fr.onload = (function () {
                return function (e) {
                    let JsonObj = JSON.parse(e.target.result);
					this.browseFile(JsonObj);
                };
            })(file).bind(this);

            fr.readAsText(file);
        });
    }
	async browseFile(file) {
        this.setState({
            sysFile: file
        })
        this.fetchQueryResults();
    }
    handleMKButtons(e){
		this.setState({milesKilo: e.target.value});
    }
	sendItinerary(){
		this.fetchItinerary(this.state.dataSend,this.state.opt);
	}
    render() {

		let options = this.state.queryResults.locations.map((item) => {
			return <li className="itemClickable" onClick={this.handleItemClick.bind(null, item.id, item.name)}>{item.name}</li>
		});
		let selected = this.state.dataSendFriendly.locations.map((item) => {
			return <li className="itemClickable" onClick={this.handleItemDeselect.bind(null, item.id, item.name)}>{item.name}</li>
		});

		let milesOrKilometers = "";
		if (this.state.milesKilo == 0){
			milesOrKilometers = "kilometers";
		} else {
			milesOrKilometers = "miles";
		}
    
		return (
        <div className="root">
            <div className="container">
                <center><h1>Team 14 - Team XIV</h1></center>
				<div className='col-md-6'>
					<h3 className="header">Optimize my trip</h3> - Mimimize travel distance with higher optimization level!<br />
					<select onChange={this.handleAlgoButtons.bind(this)}>
						<option value="0">No Optimization</option>
						<option value="1">Nearest Neighbor (Low)</option>
						<option value="2">2-Opt (Medium)</option>
						<option value="2">3-Opt (High)</option>
					</select><hr />
					<br />
					<h3 className="header">Miles or Kilometers</h3> - Should we calculate your trip using miles or kilometers?<br />
					<select onChange={this.handleMKButtons.bind(this)}>
						<option value="1">Miles</option>
						<option value="0">Kilometers</option>
					</select><hr />
				</div>
				<center>
					<h3 className="header">Find Destinations</h3><br /><hr />
					<input className="input" onChange={this.setQuery.bind(this)}></input>
					<button onClick={this.sendQuery.bind(this)}>Search</button>
					<br />
					Results
					<ul className="scrollSelect">					
						{options}	
					</ul>
					<br />
					
					<button onClick={this.handleSelectAll.bind(this)}>Select All</button>
					<button onClick={this.handleDeselectAll.bind(this)}>Deselect All</button>
					<hr />
					<br />
					<br />
					Selected
					<ul className="scrollSelect">
						{selected}
					</ul>
					<hr />
					
					<button onClick={this.sendItinerary.bind(this)}>Plan my trip</button>
					<button onClick={this.saveButtonClicked.bind(this)}>Save</button>

					<Dropzone className="dropzone-style" onDrop={this.uploadButtonClicked.bind(this)}>
						<button type="button" >Upload a location file</button>
					</Dropzone>

				</center>
				
				<Map
					cords={this.state.cords}
					containerElement={<div style={{ height: `100%` }} />}
					mapElement={<div style={{ height: `100%` }} />}
				/>
				
                <Itinerary itinerary={this.state.allPairs} milesKilo={milesOrKilometers} columns={this.state.columns}/>
            </div>
        </div>
    );
  }
}

export default App;

