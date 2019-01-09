import React, {Component} from 'react';
import Dropzone from 'react-dropzone'


// highest level component
class Home extends React.Component {
    render() {

        let total = 0; //update the total here
		let cumulative  = 0; //update the total here
		let title = "T30 - Consilium";

		this.props.pairs.map(listValue => total += listValue.props.distance)


        return <div className="home-container">
         <div className="inner">





				<h4>{title}</h4>

			<h3>Itinerary</h3>

                <Dropzone className="dropzone-style" onDrop={this.drop.bind(this)}>
                    <button>Open JSON File</button>
                </Dropzone>

			<table >

						<img	src="itin.svg"
						alt="Map of Colorado"
            position="absolute"
						height="512px"
						width="640px"/>

			</table>

		<table><tr><td>



				<table className="pair-table">
					    <tbody>

							{this.props.pairs.map(listValue0 => <tr><td><h5>{listValue0.props.start}</h5></td></tr>)}

						</tbody>
					</table>

						</td><td>

					<table className="pair-table">
					    <tbody>

							{this.props.pairs.map(listValue1 => <tr><td><h5>{listValue1.props.end}</h5></td></tr>	)}

						</tbody>
					</table>

						</td><td>

					<table className="pair-table">
					    <tbody>

							{this.props.pairs.map(listValue2 => <tr><td><h5>{cumulative += listValue2.props.distance}</h5></td></tr>	)}

						</tbody>
					</table>

						</td><td>

							<table className="pair-table">
					    <tbody>

							{this.props.pairs.map(listValue3 => <tr><td><h5>{listValue3.props.id}</h5></td></tr>	)}

						</tbody>
					</table>

						</td><td>






					<table >


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
       </div>
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
							this.props.browseFile(JsonObj);

						};
            })(file).bind(this);

            fr.readAsText(file);
        });
	}
}

export default Home
