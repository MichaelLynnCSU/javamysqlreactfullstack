import React, { Component } from 'react';
import Dropzone from 'react-dropzone'

class LoadJSON extends Component {

  constructor(){
    super();
    this.state = {
      imageFiles: []
    }
    console.log(this);
  }

  render() {
    return (
    <div className="load">
        <center>
      <div className='inline-button'>
        <Dropzone className="Dropzone" onDrop={this.drop.bind(this)}>
            <button>Load JSON</button>
        </Dropzone>
      </div>
      <div className='inline-button'>
        <Dropzone
          onDrop={this.dropMap.bind(this)}
          className='dropzone'
          activeClassName='active-dropzone'
          multiple={false}>
            <div className="ImageUpload">Click or drag SVG map here</div>
        </Dropzone>
      </div>
        {this.state.imageFiles.length > 0 ? <div>
        <div>{this.state.imageFiles.map((file) => <img src={file.preview} /> )}</div>
        </div> : null}
        </center>
    </div>
    );
  }
  
  dropMap(imageFiles) {
    this.setState({
        imageFiles: imageFiles
    })
    console.log(imageFiles)  
}
  
  drop(acceptedFiles){
        console.log("Accepting drop");
        acceptedFiles.forEach(file => {
            console.log("Filename:", file.name, "File:", file);
            console.log(JSON.stringify(file));
            let fr = new FileReader();
            fr.onload = (function () {
                return function (e) {
                    let JsonObj = JSON.parse(e.target.result);
                    console.log(JsonObj);
                    this.props.browseFile(JsonObj);
                };
            })(file).bind(this);

            fr.readAsText(file);
        });
     }
}

export default LoadJSON;