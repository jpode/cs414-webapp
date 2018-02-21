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
        type: "trip",
        title: "Trip_1",
        options : {distance: "miles", optimization : "none"},
        places: [],
        distances: [],
        map: "<svg width=\"1920\" height=\"20\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:svg=\"http://www.w3.org/2000/svg\"><g></g></svg>"
      }
    }
    this.updateTrip = this.updateTrip.bind(this);
    this.updateOptions = this.updateOptions.bind(this);
  }

  updateTrip(tffi){
    var new_tffi = this.state.trip;

    if(typeof tffi.type != "undefined"){
      new_tffi.type = tffi.type;
    }
    if(typeof tffi.title != "undefined"){
      new_tffi.title = tffi.title;
    }
    if(typeof tffi.options != "undefined"){
      new_tffi.options = tffi.options;
    }
    if(typeof tffi.places != "undefined"){
      new_tffi.places = tffi.places;
    }
    if(typeof tffi.distances != "undefined"){
      new_tffi.distances = tffi.distances;
    }
    if(typeof tffi.map != "undefined"){
      new_tffi.map = tffi.map;
    }

    console.log(new_tffi);
    this.setState({trip:new_tffi});

  }

  updateOptions(options){
    console.log(options);
    // update the options in the trip.
  }

  render() {
    return(
        <div id="application" className="container">
          <div className="row">
            <div className="col-12">
                <Options options={this.state.trip.options} updateOptions={this.updateOptions}/>
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