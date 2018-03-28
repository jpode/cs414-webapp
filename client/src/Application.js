import React, {Component} from 'react';
import Options from './Options';
import Destinations from './Destinations';
import Trip from './Trip';

/* Renders the application.
 * Holds the destinations and options state shared with the trip.
 */
class Application extends Component {
  constructor(props){
    super(props);
    this.state = {
      trip: { // default TFFI
        version: 2,
        type: "trip",
        query: "",
        title: "",
        options : {distance: "miles", optimization : "0.0", userUnit: "", userRadius: ""},
        places: [],
        distances: [],
        map: "<svg width=\"1920\" height=\"20\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:svg=\"http://www.w3.org/2000/svg\"><g></g></svg>"
      }
    }
    this.updateTrip = this.updateTrip.bind(this);
  }

  updateTrip(tffi){
    var new_tffi = this.state.trip;

    if(typeof tffi.version != "undefined" && tffi.version != 0) {
        new_tffi.version = tffi.version;
    }
    if(typeof tffi.type != "undefined" && tffi.type != ""){
      new_tffi.type = tffi.type;
    }
    if(typeof tffi.title != "undefined" && tffi.title != ""){
      new_tffi.title = tffi.title;
    }
    if(typeof tffi.options != "undefined" && tffi.options != {}){
      if(typeof tffi.options.distance != "undefined" && tffi.options.distance != ""){
        console.log(tffi.options.distance);
        new_tffi.options.distance = tffi.options.distance;
        console.log(new_tffi.options.distance);
      }
      if(typeof tffi.options.userUnit != "undefined" && tffi.options.userUnit != ""){
        new_tffi.options.userUnit = tffi.options.userUnit;
        console.log(tffi.options.distance);
      }
      if(typeof tffi.options.userRadius != "undefined" && tffi.options.userRadius != ""){
        new_tffi.options.userRadius = tffi.options.userRadius;
      }
      if(typeof tffi.options.optimization != "undefined" && tffi.options.optimization != ""){
        new_tffi.options.optimization = tffi.options.optimization;
      }
    }
    if(typeof tffi.places != "undefined" && tffi.places != []){
      new_tffi.places = tffi.places;
    }
    if(typeof tffi.distances != "undefined" && tffi.distances != []){
      new_tffi.distances = tffi.distances;
    }
    if(typeof tffi.map != "undefined" && tffi.map != ""){
      new_tffi.map = tffi.map;
    }
    console.log("Updating trip to new object: ");
    console.log(new_tffi.options.distance);
    console.log(new_tffi)
    console.log(new_tffi.options.distance);
    this.setState({trip:new_tffi});

  }

  render() {
    return(
        <div id="application" className="container">
          <div className="row">
            <div className="col-12">
                <Options trip={this.state.trip} updateTrip={this.updateTrip}/>
            </div>
            <div className="col-12">
                <Destinations trip={this.state.trip} updateTrip={this.updateTrip}/>
            </div>
            <div className="col-12">
                <Trip trip={this.state.trip} updateTrip={this.updateTrip} />
            </div>
          </div>
        </div>
    )
  }
}

export default Application;
