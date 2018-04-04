import React, {Component} from 'react';
import Options from './Options';
import Destinations from './Destinations';
import Trip from './Trip';
//import "./custom.scss";

/* Renders the application.
 * Holds the destinations and options state shared with the trip.
 */
class Application extends Component {
  constructor(props){
    super(props);
    this.state = {
      trip: { // default TFFI
        version: this.props.config.version,
        type: "trip",
        query: "",
        title: "",
        options : {distance: "miles", optimization : "0.0", userUnit: "", userRadius: ""},
        places: [],
        distances: [],
        map: "<svg width=\"1920\" height=\"20\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:svg=\"http://www.w3.org/2000/svg\"><g></g></svg>"
      }
    }

    console.log("Version: " + this.props.config.version);
    console.log("Supported Distances: " + this.props.config.distances);
    console.log("Supported Optimization Levels: " + this.props.config.optimization);

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
        new_tffi.options = tffi.options;
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
    console.log(new_tffi)
    this.setState({trip:new_tffi});

  }

  render() {

    return(
        <div id="application" className="container">
          <div className="row">
            <div className="col-12">
                <Options trip={this.state.trip} config={this.props.config} updateTrip={this.updateTrip}/>
            </div>
            <div className="col-12">
                <Destinations trip={this.state.trip} config={this.props.config} updateTrip={this.updateTrip}/>
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
