import React, {Component} from 'react';
import Dropzone from 'react-dropzone'

class Home extends React.Component {
    render() {

        let total = 0; //update the total here
		let title = "T30 - Consilium";
		this.props.pairs.map(listValue => total += listValue.props.dist)



		
        return <div className="home-container">
         <div className="inner">
		 
		 <h4>{title}</h4>
			 
			<h3>Itinerary</h3>
				
                <Dropzone className="dropzone-style" onDrop={this.drop.bind(this)}>
                    <button>Open JSON File</button>
                </Dropzone>

			  <table className="pair-table">
					<tbody>		
					<tr>
					<td>{ this.props.pairs }</td>	
					</tr>			
					<tr>						
					<td>Total:  {total}</td>
					</tr>
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
