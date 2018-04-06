import React, {Component} from 'react';

/* Options allows the user to change the parameters for planning
 * and rendering the trip map and itinerary.
 * The options reside in the parent object so they may be shared with the Trip object.
 * Allows the user to set the options used by the application via a set of buttons and sliders.
 * The optimization slider can be used both before a trip is planned and after and will update
 * state when it is moved.
 */
class Options extends Component{
  constructor(props) {
    super(props);
    this.state = {
      sliderval : 0,
      distanceUnit : "",
      distanceRadius : ""
    }

    this.handleSlider = this.handleSlider.bind(this);
    this.changeDistance = this.changeDistance.bind(this);
    this.changeDistanceCustom = this.changeDistanceCustom.bind(this);
    this.handleCustomDistanceName = this.handleCustomDistanceName.bind(this);
    this.handleCustomDistanceRadius = this.handleCustomDistanceRadius.bind(this);
    this.updateCustomDistance = this.updateCustomDistance.bind(this);
  }

  handleSlider(event){
    this.setState({sliderval : event.target.value});

    var newTFFI = this.props.trip;
    newTFFI.options.optimization = event.target.value;
    this.props.updateTrip(newTFFI);
    this.optimize();

  }

  changeDistance(arg) {
      this.setState({distanceUnit : ""});

      var newTFFI = this.props.trip;
      newTFFI.options.distance = arg;

      this.props.updateTrip(newTFFI);
      this.plan();
  }

  changeDistanceCustom(arg) {
    this.setState({distanceUnit : "new"});
  }

  handleCustomDistanceName(event){
    this.setState({distanceUnit : event.target.value});
  }

  handleCustomDistanceRadius(event){
    this.setState({distanceRadius : event.target.value});
  }

  updateCustomDistance(){
    var newTFFI = this.props.trip;
    newTFFI.options.distance = "user defined";
    newTFFI.options.userUnit = this.state.distanceUnit;
    newTFFI.options.userRadius = this.state.distanceRadius;

    this.props.updateTrip(newTFFI);
    this.plan();

  }

  /* Sends a bas request to the server with only necessary objects.
 * Receives a response containing an optimized itinerary to update the
 * state for this object.
 */
  fetchResponse(){
    // need to get the request body from the trip in state object.
    let requestBody = {
      "title"   : this.props.trip.title,
      "options" : this.props.trip.options,
      "places"  : this.props.trip.places,
      "distances" : this.props.trip.distances
    };

    console.log(process.env.SERVICE_URL);
    console.log(requestBody);

    return fetch('http://' + location.host + '/optimize', {
      method:"POST",
      body: JSON.stringify(requestBody)
    });
  }

  async optimize(){
    try {
      let serverResponse = await this.fetchResponse();
      let tffi = await serverResponse.json();

      console.log(tffi);
      this.props.updateTrip(tffi);

    } catch(err) {
      console.error(err);
    }
  }

  fetchResponse_V2(){
      // need to get the request body from the trip in state object.
      let requestBody = {
          "version"  : this.props.trip.version,
          "type"     : this.props.trip.type,
          "title"    : this.props.trip.title,
          "options"  : this.props.trip.options,
          "places"   : this.props.trip.places,
          "distances": this.props.trip.distances,
          "map"      : this.props.trip.map
      };
      // unsure if map or distances should be included above! ^
      console.log(process.env.SERVICE_URL);
      console.log(requestBody);

      return fetch('http://' + location.host + '/plan', {
          method:"POST",
          body: JSON.stringify(requestBody)
      });
  }

  async plan(){
      try {
          let serverResponse = await this.fetchResponse_V2();
          let tffi = await serverResponse.json();

          console.log("Status " + serverResponse.status + ": " + serverResponse.statusText);
          if(serverResponse.status >= 200 && serverResponse.status < 300) {
              console.log(tffi);
              this.props.updateTrip(tffi);
          } else {
              alert("Error " + serverResponse.status + ": " + serverResponse.statusText);
          }

      } catch(err) {
          console.error(err);
          alert(err);
      }
  }


  render() {

    const isCustomUnit = this.state.distanceUnit != "";
    const hasTitle = this.state.distanceUnit != "" && this.state.distanceUnit != "new";
    const hasRadius = this.state.distanceRadius != "";

    const distances = this.props.config.distances;
    const configNMiles= distances.includes("nautical miles");
    const configUD= distances.includes("user defined");
    const configOptLevel = this.props.config.optimization;

    var configOptStep;
    if(configOptLevel > 0) {
      configOptStep = (1.0 / configOptLevel).toString();
    } else {
      configOptStep = (0).toString();
    }

    return(
        <div id="options" className="card">
          <div className="card-header bg-success text-white">
            Options
          </div>
          <div className="card-body">
            <p>Select the options you want to add to your trip.</p>
            {configOptLevel > 0 &&
            <div>
              <p>&nbsp;&nbsp;Level of Optimization</p>
              <p>
                <small><i>&nbsp;&nbsp;Warning: optimized trips can take time to
                  generate. Please be patient when selecting high levels of
                  optimization.</i></small>
              </p>
              <sup>Less</sup> &nbsp;&nbsp; <input type="range" name="optimization" value={this.state.sliderval} onChange={this.handleSlider} min="0" max="1" step={configOptStep}/> &nbsp;&nbsp;
              <sup>More</sup>
            </div>
            }
            <div className="btn-group btn-group-toggle" data-toggle="buttons">
              <label className={"btn btn-outline-dark btn-success".concat((this.props.trip.options.distance === "miles") ? " active" : "")}>
                <input type="radio" id="miles" name="distance" value="on" onClick={() => { this.changeDistance("miles") }} /> Miles
              </label>
              <label className={"btn btn-outline-dark btn-success".concat((this.props.trip.options.distance === "kilometers") ? " active" : "")}>
                <input type="radio" id="kilometers" name="distance" value="on" onClick={() => { this.changeDistance("kilometers") }} /> Kilometers
              </label>
              {configNMiles &&
              <label className={"btn btn-outline-dark btn-success".concat((this.props.trip.options.distance === "nautical miles") ? " active" : "")}>
                <input type="radio" id="nautical miles" name="distance" value="on" onClick={() => {this.changeDistance("nautical miles")}}/> Nautical Miles
              </label>
              }
              {configUD &&
              <label className={"btn btn-outline-dark btn-success".concat((this.props.trip.options.distance === "user defined") ? " active" : "")}>
                <input type="radio" id="user defined" name="distance" value="on" onClick={() => { this.changeDistanceCustom("user defined") }} /> Custom Unit
              </label>
              }
            </div>
            {isCustomUnit &&
            <div className="input-group" role="group">
              <span>
                <input type="text" className="form-control" onChange = {this.handleCustomDistanceName} placeholder="Unit"/>
                <input type="text" className="form-control" onChange = {this.handleCustomDistanceRadius} placeholder="Radius of the Earth in unit"/>
                <span className="input-group-btn">
                <button disabled = {!hasTitle && !hasRadius} className="btn btn-primary " onClick={this.updateCustomDistance} type="button">Submit</button>
                </span>
              </span>
            </div>
            }
          </div>
        </div>
    )
  }
}

export default Options;
