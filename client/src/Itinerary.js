import React, {Component} from 'react';

class Itinerary extends Component {
  constructor(props) {
    super(props);

    this.createTable = this.createTable.bind(this);
  }

  createTable () {

    let distance = 0;  // need to sum this from real the trip
    let units = "miles";
    let dests = [];
    let dists = [];

    if(typeof this.props.trip.options.distance != "undefined") {
      units = this.props.trip.options.distance;
    }

    if(typeof this.props.trip.distances != "undefined" && this.props.trip.distances.length > 0) {
      dists = this.props.trip.distances.map((item) => <td>{item}</td>);
      dists.unshift(<td>{0}</td>)
    }

    if(typeof this.props.trip.places[0] != "undefined") {
      dests = this.props.trip.places.map((item) => <td>{item.name}</td>);
      dests.push(<td>{this.props.trip.places[0].name}</td>);
    }

    for(var i = 0; i < this.props.trip.distances.length; i++){
      distance += this.props.trip.distances[i];
    }

    return {distance, units, dests, dists};
  }

  render() {
    let table = this.createTable();

    return(
        <div id="itinerary">
          <h4>Round trip distance of {table.distance} {table.units}. </h4>
          <table className="table table-responsive table-bordered">
            <thead>
            <tr className="table-info">
              <th className="align-middle">Destination</th>
              {table.dests}
            </tr>
            </thead>
            <tbody>
            <tr>
              <th className="table-info align-middle">{table.units}</th>
              {table.dists}
            </tr>
            </tbody>
          </table>
        </div>
    )
  }
}

export default Itinerary;
