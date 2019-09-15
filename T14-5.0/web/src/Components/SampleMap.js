
import React, { Component } from 'react';

// This imports all of the external functionality we want from react-google-maps
import {GoogleMap, Polyline, Marker, withGoogleMap} from 'react-google-maps';


class Map extends Component {

	constructor(){
		super();
		console.log(this.props);
	}
	
    // Render method of the Map component
    render() {

        let exampleCoordinates = this.props.cords;
        // Return the stuff we actually want rendered on the page
        return (
            { /*GoogleMap is an imported component from react-google-maps*/ },
            <GoogleMap
                defaultCenter={{lat: 0, lng: 0} /*Sets the default center for the map to start at */}
                defaultZoom={1 /* Sets the default zoom ie how much of the world is on the screen*/}
            >
				<Marker
				  position={this.props.marker}
				></Marker>
            {/* Everything that is in between <GoogleMap> and </GoogleMap> get rendered onto the
                map. Polyline is an easy google library that draws lines from coordiates.*/ }
                <Polyline
                    visible={true /*Make sure the map is visable on screen*/}

                    path={exampleCoordinates /* Set polyline path to the coordiates array*/}
					
                    options={{
                        /* This is a list of optional things line line color and line weight this does not
                        need to be included. See documentation for more options*/
                        strokeColor: '#ff2527',
                        strokeWeight: 4,
                    }}
                />

            { /*Close our GoogleMap*/}
            </GoogleMap>
        )
    }
}
// This is important what this does is it wraps the Map module in
// a withGoogleMap module. Without this the map will not load
export default withGoogleMap(Map)