import React, { Component } from 'react';
import './ItineraryItem.scss';

class ItineraryItem extends Component{

    render(){
    let options = this.props.options.map(item => {
        return <li>{item}: {this.props.item.startData[item.toLowerCase()]} to {this.props.item.endData[item.toLowerCase()]}</li>
    });
        return(
            <div className="ItineraryItem">
                <div className="from"><h3>{this.props.item.start}</h3></div>
                <div className="total"><h3>{this.props.distance} {this.props.milesKilo}</h3></div>
                <div className="to"><h3>{this.props.item.end}</h3></div>
                <div className="well">
                    <ul>
                        {options}
                    </ul>
                </div>
            </div>
        );
    }
}

export default ItineraryItem;