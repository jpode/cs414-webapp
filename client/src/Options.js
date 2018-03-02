import React, {Component} from 'react';

/* Options allows the user to change the parameters for planning
 * and rendering the trip map and itinerary.
 * The options reside in the parent object so they may be shared with the Trip object.
 * Allows the user to set the options used by the application via a set of buttons.
 */
class Options extends Component{
  constructor(props) {
    super(props);
    this.state = {
      sliderval : 0
    }


    this.handleSlider = this.handleSlider.bind(this);
    this.changeDistance = this.changeDistance.bind(this);


  }

  handleSlider(event){
    this.setState({sliderval : event.target.value});

    var newOption = this.props.options;
    newOption.optimization = event.target.value;
    this.props.updateOptions(newOption);

  }

  changeDistance(arg) {
    var newOption = this.props.options;
    newOption.distance = arg;
    this.props.updateOptions(newOption);
  }



  render() {
    // @todo need to update the options when a button is pressed
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
              <sup>Less</sup> &nbsp;&nbsp; <input type="range" name="optimization" value={this.state.sliderval} onChange={this.handleSlider} min="0" max="1" step=".01" /> &nbsp;&nbsp; <sup>More</sup>
            </div>
            <div className="btn-group btn-group-toggle" data-toggle="buttons">
              <label className={"btn btn-outline-dark".concat((this.props.options.distance === "miles") ? " active" : "")}>
                <input type="radio" id="miles" name="distance" value="on" onClick={() => { this.changeDistance("miles") }} /> Miles
              </label>
              <label className={"btn btn-outline-dark".concat((this.props.options.distance === "kilometers") ? " active" : "")}>
                <input type="radio" id="kilometers" name="distance" value="on" onClick={() => { this.changeDistance("kilometers") }} /> Kilometers
              </label>
            </div>
          </div>
        </div>
    )
  }
}

export default Options;
