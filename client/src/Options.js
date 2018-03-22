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
      sliderval : 0
    }

    

   // this.optimize = this.optimize.bind(this);
    this.handleSlider = this.handleSlider.bind(this);
    this.changeDistance = this.changeDistance.bind(this);

  }

  handleSlider(event){
    this.setState({sliderval : event.target.value});

    var newTFFI = this.props.trip;
    newTFFI.options.optimization = event.target.value;
    this.props.updateTrip(newTFFI);

  }

  changeDistance(arg) {
    var newTFFI = this.props.trip;
    newTFFI.options.distance = arg;

    this.props.updateTrip(newTFFI);
  }
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


  render() {
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
            </div>
          </div>
        </div>
    )
  }
}

export default Options;
