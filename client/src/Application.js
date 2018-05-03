import Cookies from "universal-cookie";
import React, {Component} from 'react';
import Options from './Options';
import Destinations from './Destinations';
import UserEditing from './UserEditing';
import StaffPage from './StaffPage';
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
        options : {units: "miles", optimization : "0.0", userUnit: "", userRadius: "", map: "svg"},
        places: [],
        distances: [],
        map: "<svg width=\"1920\" height=\"20\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:svg=\"http://www.w3.org/2000/svg\"><g></g></svg>"
      }
    }

    var cookie = new Cookies();
    cookie = cookie.get("stateCookie")
    if(cookie != null){
      console.log(cookie);
      this.state = cookie;
    }
    this.saveCookie = this.saveCookie.bind(this);

    this.printConfig = this.printConfig.bind(this);
    this.updateTrip = this.updateTrip.bind(this);
    this.changeTab = this.changeTab.bind(this);
    this.plan = this.plan.bind(this);

    this.printConfig();

  }

  saveCookie(){
    const cookies = new Cookies();
    //cookies.set("stateCookie", this.state);
    console.log(cookies.get("stateCookie"));
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
    let key;
    if(this.props.config.version > 2){
      for (key in this.props.config.optimizations) {
        if (this.props.config.optimizations.hasOwnProperty(key)) {
          console.log(this.props.config.optimizations[key].label + ": "
              + this.props.config.optimizations[key].description);
        }
      }
      console.log("Supported Maps:");
      for (key in this.props.config.maps) {
        if (this.props.config.maps.hasOwnProperty(key)) {
          console.log(this.props.config.maps[key]);
        }
      }
      console.log("Supported Distance Units:");
      for (key in this.props.config.units) {
        if (this.props.config.units.hasOwnProperty(key)) {
          console.log(this.props.config.units[key]);
        }
      }
      console.log("Supported Filters:");
      for (key in this.props.config.filters) {
        if (this.props.config.filters.hasOwnProperty(key)) {
          console.log(this.props.config.filters[key]);
        }
      }
    }
  }

  updateTrip(tffi){
    let new_tffi = this.state.trip;

    for(let key in tffi){
      if(tffi.hasOwnProperty(key)){
        if(typeof tffi[key] !== "undefined" && tffi[key] !== 0 && tffi[key] !== ""){
          new_tffi[key] = tffi[key];
        }
      }
    }

    console.log(new_tffi);
    this.setState({trip: new_tffi}, () => {this.saveCookie()});


  }

  /* Sends a request to the server with the destinations and options.
 * Receives a response containing the map and itinerary to update the
 * state for this object.
 */
  fetchResponse(){
    // need to get the request body from the trip in state object.
    let requestBody = {
      "version"  : this.state.trip.version,
      "type"     : this.state.trip.type,
      "title"    : this.state.trip.title,
      "options"  : this.state.trip.options,
      "places"   : this.state.trip.places,
      "distances": this.state.trip.distances,
      "map"      : this.state.trip.map
    };
    // unsure if map or distances should be included above! ^
    console.log(process.env.SERVICE_URL);
    console.log(requestBody);

    return fetch('http://' + this.props.host + '/plan', {
      header: {'Access-Control-Allow-Origin':'*'},
      method:"POST",
      body: JSON.stringify(requestBody)
    });
  }

  async plan(){
    try {
      let serverResponse = await this.fetchResponse();
      let tffi = await serverResponse.json();

      console.log("Status " + serverResponse.status + ": " + serverResponse.statusText);
      if(serverResponse.status >= 200 && serverResponse.status < 300) {
        console.log(tffi);
        this.updateTrip(tffi);
      } else {
        alert("Error " + serverResponse.status + ": " + serverResponse.statusText);
      }

    } catch(err) {
      console.error(err);
      alert(err);
    }
  }

  changeTab() {

  }

  render() {
    return(
        <div id="application" className="container">
          <nav>
            <div className="nav nav-tabs" id="nav-tab" role="tablist">
              <a className="nav-item nav-link active" id="nav-home-tab" data-toggle="tab" href="#nav-home" role="tab" aria-controls="nav-home" aria-selected="true"><h5>Home</h5></a>
              <a className="nav-item nav-link" id="nav-staff-tab" data-toggle="tab" href="#nav-staff" role="tab" aria-controls="nav-staff" aria-selected="false"><h5>Staff</h5></a>
            </div>
          </nav>
          <div className="tab-content" id="nav-tabContent">
            <div className="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
              <div className="row">
                <div className="col-12">
                  <Options trip={this.state.trip} config={this.props.config} plan={this.plan} updateTrip={this.updateTrip} host={this.props.host} updateHost={this.props.updateHost}/>
                </div>
                <div className="col-12">
                  <Destinations trip={this.state.trip} config={this.props.config} updateTrip={this.updateTrip} plan={this.plan} host={this.props.host}/>
                </div>
                <div className="col-12">
                  <UserEditing trip={this.state.trip} config={this.props.config} plan={this.plan} updateTrip={this.updateTrip}/>
                </div>
                <div className="col-12">
                  <Trip trip={this.state.trip} updateTrip={this.updateTrip} plan={this.plan}/>
                </div>
              </div>
            </div>
            <div className="tab-pane fade" id="nav-staff" role="tabpanel" aria-labelledby="nav-staff-tab">
              <h5>STAFF</h5>
            </div>
          </div>
        </div>
    )
  }
}
export default Application;
