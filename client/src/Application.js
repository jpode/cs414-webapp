import React, {Component} from 'react';
import Options from './Options';
import Destinations from './Destinations';
import UserEditing from './UserEditing';
import Trip from './Trip';
import "./custom.scss";

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

    this.printConfig = this.printConfig.bind(this);
    this.updateTrip = this.updateTrip.bind(this);

    this.printConfig();
  }

  //Print out information from the config file received from the server.
  printConfig(){
    console.log("Version: " + this.props.config.version);
    if(this.props.config.version > 0) {
      console.log("Version acceptable. Loading client...")
    }
    if(this.props.config.version > 1){
      console.log("Supported Optimization Levels: " + this.props.config.optimization);
    }
    if(this.props.config.version > 2){
      for (var key in this.props.config.optimizations) {
        if (this.props.config.optimizations.hasOwnProperty(key)) {
          console.log(this.props.config.optimizations[key].label + ": "
              + this.props.config.optimizations[key].description);
        }
      }
      console.log("Supported Maps:");
      for (var key in this.props.config.maps) {
        if (this.props.config.maps.hasOwnProperty(key)) {
          console.log(this.props.config.maps[key]);
        }
      }
      console.log("Supported Distances:");
      for (var key in this.props.config.distances) {
        if (this.props.config.distances.hasOwnProperty(key)) {
          console.log(this.props.config.distances[key]);
        }
      }
    }
  }

  updateTrip(tffi){
    var new_tffi = this.state.trip;

    for(var key in tffi){
      if(tffi.hasOwnProperty(key)){
        if(typeof tffi[key] != "undefined" && tffi[key] != 0 && tffi[key] != ""){
          new_tffi[key] = tffi[key];
        }
      }
    }

    console.log(new_tffi)
    this.setState({trip: new_tffi});

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
              <UserEditing trip={this.state.trip} config={this.props.config} updateTrip={this.updateTrip} addPlace={this.addPlace}/>
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
