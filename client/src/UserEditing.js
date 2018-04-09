import React, {Component} from 'react';

class UserEditing extends Component {
  constructor(props) {
    super(props);
    this.state={
      selected: 0,
      editType: "",
      targetIndex: 0,
      destIndex: 0,
      new_place: {
        id: "",
        name: "",
        longitude: "",
        latitude: ""
      }
    }

    this.handleCreation = this.handleCreation.bind(this);
    this.handleCustomName = this.handleCustomName.bind(this);
    this.handleCustomLongitude = this.handleCustomLongitude.bind(this);
    this.handleCustomLatitude = this.handleCustomLatitude.bind(this);
    this.handleCustomOrder = this.handleCustomOrder.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);

  }

  handleCreation(event){
    if(this.state.selected === 0){
      this.setState({selected : 1});
    } else {
      this.setState({selected : 0});
    }
  }

  handleCustomName(event){
    var place = this.state.new_place;
    place.name = event.target.value;
    place.id = event.target.value.substring(0,4);
    this.setState({new_place: place});

  }

  handleCustomLongitude(event){
    var place = this.state.new_place;
    place.longitude = event.target.value;
    this.setState({new_place: place});
  }

  handleCustomLatitude(event){
    var place = this.state.new_place;
    place.latitude = event.target.value;
    this.setState({new_place: place});
  }

  handleCustomOrder(event){
    this.setState({destIndex: event.target.value});
  }

  handleSubmit(){
    this.edit();
  }

  /* Sends a request to the server with the destinations and options.
  * Receives a response containing the map and itinerary to update the
  * state for this object.
  */
  fetchResponse(){
    // need to get the request body from the trip in state object.
    let requestBody = {
      "editType"     : this.state.type,
      "optimization"  : this.optimization,
      "new_place" : this.state.new_place,
      "targetIndex" : this.state.targetIndex,
      "destIndex" : this.state.destIndex,
      "places"   : this.props.trip.places,
      "distances": this.props.trip.distances,
    };
    // unsure if map or distances should be included above! ^
    console.log(process.env.SERVICE_URL);
    console.log(requestBody);

    return fetch('http://' + location.host + '/edit', {
      method:"POST",
      body: JSON.stringify(requestBody)
    });
  }

  async edit(){
    try {
      let serverResponse = await this.fetchResponse();
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

  render(){
    let hasName = this.state.place.name === "";
    let hasLongitude = this.state.place.longitude === "";
    let hasLatitude = this.state.place.latitude === "";
    let hasDestIndex = this.state.destIndex === 0;
    return(
      <div id="options" className="card">
        <div className="card-header bg-success text-white">
          Edit Destinations
        </div>

        <div className="btn-group btn-group-toggle" data-toggle="buttons">
          <label className={"btn btn-outline-dark btn-success".concat((this.state.selected === 1) ? " active" : "")}>
            <input type="radio" id="new_place" name="new_place" value="on" onClick={this.handleCreation} /> Create New Destination
          </label>
        </div>

        {this.state.selected === 1 &&
          <div className="input-group" role="group">
            <span>
              <input type="text" className="form-control" onChange={this.handleCustomName} placeholder="Destination Name"/>
              <input type="text" className="form-control" onChange={this.handleCustomLongitude} placeholder="Destination Longitude"/>
              <input type="text" className="form-control" onChange={this.handleCustomLatitude} placeholder="Destination Latitude"/>
              <input type="text" className="form-control" onChange={this.handleCustomOrder} placeholder="Order In Trip"/>
              <span className="input-group-btn">
              <button className="btn btn-primary " onClick={this.handleSubmit} type="button">Submit</button>
              </span>
            </span>
          </div>
        }
      </div>
    )
  }
}

//disabled={!hasName && !hasLongitude && !hasLatitude && !hasOrder}
export default UserEditing;