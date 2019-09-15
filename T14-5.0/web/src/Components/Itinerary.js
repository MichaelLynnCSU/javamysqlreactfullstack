import React, { Component } from 'react';
import ItineraryItem from './ItineraryItem';
class Itinerary extends Component {

  constructor(){
    super();
    console.log(this);
    this.state = {
        ItineraryItems: [],
        options: []
    }
  }

  handleClick(id){
    let options = this.state.options;
    if(options.includes(id)){
        options.splice(options.indexOf(id), 1);
    }else{
        options.push(id);
    }
    this.setState({options: options});
  }

  render() {
    let columns = this.props.columns.map(item => {
        return <div>
            <input
                type="checkbox"
                key={item.name}
                onChange={this.handleClick.bind(this, item.name)}
            ></input>
            <label>{item.name}</label>
        </div>
    });
    let ItineraryItems;
    let total = 0;
    if (this.props.itinerary.length !== 0){
      
		ItineraryItems = this.props.itinerary.map(item => {
          this.state.ItineraryItems.push(item);
          total += item.distance;
          return (
            <div className="divlet">
			    <ItineraryItem key={item.start} item={item} milesKilo={this.props.milesKilo} options={this.state.options} distance={item.distance} />
			</div>
          );
      });
    }

    return (
        <div className="Itinerary">
            {columns}
            {ItineraryItems}
			<div className="whiteBack"><center><div className="totalDistance">Cumulative Total: {total}</div></center></div>
        </div>
    );
  }
}

export default Itinerary;