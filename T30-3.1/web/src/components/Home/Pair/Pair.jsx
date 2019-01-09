import React, {Component} from 'react';


function buildHTML(keys, all){
    var key = keys.pop();
    if (keys.length == 0) {
        return (<b> <em>{key + ": "} </em> {eval("all."+key+";")} </b>);
    }
    return (<b> <em>{key + ": "} </em> {eval("all."+key+";")} <br /> {buildHTML(keys, all)}</b>);
}

function displayInfo(all){
    var keys = all.keys.slice();
    for(let i = 0; i < all.exclude_keys.length; i++){
        if (keys.includes(all.exclude_keys[i])){
            keys.splice(keys.indexOf(all.exclude_keys[i]), 1);
        }
    }
    return buildHTML(keys, all);
}

var total = 0;

let Pair = ({start, end, distance, keys, exclude_keys, all}) => <tbody className="pair">
    <tr>
        <td>
            <h5>{start}</h5>
        </td>
        <td>
            <h5>{end}</h5>
            <small> {displayInfo(all)} </small>
        </td>
        <td>
            <h5>{distance}</h5>
        </td>
        <td>
            <h5>{total += distance}</h5>
        </td>
    </tr>
</tbody>;

export default Pair;