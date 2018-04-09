import React, {Component} from 'react';
import Query from './Query';
class UserEditing extends Component {
  constructor(props) {
    super(props);
    this.state={
      place: {
        id: "",
        name: "",
        longitude: "",
        latitude: "",
      }
    }
  }

  render(){
    return(
      <div id="options" className="card">
        <div className="card-header bg-success text-white">
          Edit Destinations
        </div>
        <div className="btn-group btn-group-toggle" data-toggle="buttons">
          <label className={"btn btn-outline-dark btn-success".concat((this.props.trip.options.distance === "miles") ? " active" : "")}>
            <input type="radio" id="miles" name="distance" value="on" onClick={() => { this.changeDistance("miles") }} /> Miles
          </label>
        <div className="input-group" role="group">
              <span>
                <input type="text" className="form-control" onChange = {this.handleCustomDistanceName} placeholder="Unit"/>
                <input type="text" className="form-control" onChange = {this.handleCustomDistanceRadius} placeholder="Radius of the Earth in unit"/>
                <span className="input-group-btn">
                <button disabled = {!hasTitle && !hasRadius} className="btn btn-primary " onClick={this.updateCustomDistance} type="button">Submit</button>
                </span>
              </span>
        </div>
      </div>
    )
  }
}

export default Application;