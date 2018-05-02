import React, {Component} from 'react';
import Map from './Map';
import Itinerary from './Itinerary';
import KmlMap from './KmlMap';

/* Trip computes the map an intinerary based on a set of destinations and options.
 * The destinations and options reside in the parent object so they may be set by
 * the Destinations and Options classes.
 * The map and itinerary reside in this object so they can be passed to the Map and Itinerary classes.
 */
class Trip extends Component {
  constructor(props) {
    super(props);

    //this.plan = this.plan.bind(this);
    this.saveTFFI = this.saveTFFI.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  /* Saves the map and itinerary to the local file system.
   */
  saveTFFI(){
    let element = document.createElement('a');
    element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(JSON.stringify(this.props.trip)));
    element.setAttribute('download', this.props.trip.title + ".json");

    element.style.display = 'none';
    document.body.appendChild(element);

    element.click();

    document.body.removeChild(element);
  }

  handleSubmit(event){
    let tffi = this.props.trip;
    tffi.title = event.target.value;
    this.props.updateTrip({tffi});
  }

  /* Renders the buttons, map, and itinerary.
   * The title should be specified before the plan or save buttons are valid.
   */
  render(){
    const hasTitle = this.props.trip.title.length > 0;
    return(
        <div id="trip" className="card">
          <div className="card-header bg-success text-white">
            Trip
          </div>
          <div className="card-body">
            <p>Give your trip a title before planning or saving.</p>
            <div className="input-group" role="group">
              <span className="input-group-btn">
              <button disabled = {!hasTitle} className="btn btn-outline-dark btn-success" onClick={this.props.plan} type="button">Plan</button>
            </span>
              <input type="text" value={this.props.trip.title} className="form-control" onChange = {this.handleSubmit} placeholder="Trip title"/>
              <span className="input-group-btn">
              <button disabled = {!hasTitle} className="btn btn-outline-dark btn-success" onClick={this.saveTFFI} type="button">Save</button>
            </span>
            </div>
            {/*<KmlMap trip={this.props.trip}/>*/}
            <Map trip={this.props.trip} />
            <Itinerary trip={this.props.trip} />
          </div>
        </div>
    )
  }
}

export default Trip;
