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



   // this.optimize = this.optimize.bind(this);
    this.handleSlider = this.handleSlider.bind(this);
    this.changeDistance = this.changeDistance.bind(this);
    //this.changeDistanceCustom = this.changeDistanceCustom.bind(this);
    //this.handleCustomDistanceName = this.handleCustomDistanceName.bind(this);
    //this.handleCustomDistanceRadius = this.handleCustomDistanceRadius.bind(this);
   // this.updateCustomDistance = this.updateCustomDistance.bind(this);
  }

  handleSlider(event){
    this.setState({sliderval : event.target.value});

    var newTFFI = this.props.trip;
    newTFFI.options.optimization = event.target.value;
    this.props.updateTrip(newTFFI);
    this.optimize();

  }

  changeDistance(arg) {
      console.log("Non user defined unit: " + arg);
      //this.setState({distanceUnit : ""});

      var newTFFI = this.props.trip;
      newTFFI.options.distance = arg;

      this.props.updateTrip(newTFFI);
      this.plan();
  }
/*
  changeDistanceCustom(arg) {
    console.log("distance is custom");
    this.setState({distanceUnit : "new"});
  }

  handleCustomDistanceName(event){
    console.log("changing custom distance name");
    this.setState({distanceUnit : event.target.value});
  }

  handleCustomDistanceRadius(event){
    console.log("changing custom distance radius");
    this.setState({distanceRadius : event.target.value});
  }

  updateCustomDistance(){
    console.log("updating with custom user distance");
    var newTFFI = this.props.trip;
    newTFFI.options.distance = "user defined1";
    newTFFI.options.userUnit = this.state.distanceUnit;
    newTFFI.options.userRadius = this.state.distanceRadius;

    //this.props.updateTrip(newTFFI);
    //this.plan();

  }
*/
  //NOTE: need to implement nautical miles, and a way for users to define/name distance units

  /* Sends a request to the server with all state information except type.
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

  /////////////////////////
    fetchResponse2(){
        // need to get the request body from the trip in state object.
        let requestBody = {
            "version"  : this.props.trip.version,
            "type"     : this.props.trip.type,
            "query"    : this.props.trip.version,
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
            let serverResponse = await this.fetchResponse2();
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
    ////////////


  render() {
    /*
    const isCustomUnit = this.state.distanceUnit != "";
    const hasTitle = this.state.distanceUnit != "" && this.state.distanceUnit != "new";
    const hasRadius = this.state.distanceRadius != "";
    */

    return(
        <div id="options" className="card">
          <div className="card-header bg-info text-white">
            Options
          </div>
          <div className="card-body">
            <p>Select the options you want to add to your trip.</p>
            <div>
              <p>&nbsp;&nbsp;Level of Optimization</p>
              <p><small><i>&nbsp;&nbsp;Warning: optimized trips can take time to generate. Please be patient when selecting high levels of optimization.</i></small></p>
              <sup>Less</sup> &nbsp;&nbsp; <input type="range" name="optimization" value={this.state.sliderval} onChange={this.handleSlider} min="0" max="1" step=".5" /> &nbsp;&nbsp; <sup>More</sup>
            </div>
            <div className="btn-group btn-group-toggle" data-toggle="buttons">
              <label className={"btn btn-outline-dark".concat((this.props.trip.options.distance === "miles") ? " active" : "")}>
                <input type="radio" id="miles" name="distance" value="on" onClick={() => { this.changeDistance("miles") }} /> Miles
              </label>
              <label className={"btn btn-outline-dark".concat((this.props.trip.options.distance === "kilometers") ? " active" : "")}>
                <input type="radio" id="kilometers" name="distance" value="on" onClick={() => { this.changeDistance("kilometers") }} /> Kilometers
              </label>
              <label className={"btn btn-outline-dark".concat((this.props.trip.options.distance === "nautical miles") ? " active" : "")}>
                <input type="radio" id="nautical miles" name="distance" value="on" onClick={() => { this.changeDistance("nautical miles") }} /> Nautical Miles
              </label>
              {/* <label className={"btn btn-outline-dark".concat((this.props.trip.options.distance === "user defined") ? " active" : "")}>
                <input type="radio" id="user defined" name="distance" value="on" onClick={() => { this.changeDistanceCustom("user defined2") }} /> Custom Unit
              </label> */}
            </div>
            {/* {isCustomUnit &&
            <div className="input-group" role="group">
              <span>
                <input type="text" className="form-control" onChange = {this.handleCustomDistanceName} placeholder="Unit name..."/>
                <input type="text" className="form-control" onChange = {this.handleCustomDistanceRadius} placeholder="Unit radius..."/>
                <span className="input-group-btn">
                <button disabled = {!hasTitle && !hasRadius} className="btn btn-primary " onClick={this.updateCustomDistance} type="button">Submit</button>
                </span>
              </span>
            </div>
            } */}
          </div>
        </div>
    )
  }
}

export default Options;
