import React, {Component} from 'react';

class UserEditing extends Component {
  constructor(props) {
    super(props);
    this.state={
      selected: 0,
      type: "",
      targetIndex: 0,
      destinationIndex: 0,
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


    console.log(this.props.trip);
  }

  handleCreation(event){
    if(this.state.selected === 0){
      this.setState({selected : 1});
    } else {
      this.setState({selected : 0});
    }
  }

  handleCustomName(event){
    var new_place = this.state.place;
    new_place.name = event.target.value;
    new_place.id = event.target.value.substring(0,4);
    this.setState({place: new_place});

  }

  handleCustomLongitude(event){
    var new_place = this.state.place;
    new_place.longitude = event.target.value;
    this.setState({place: new_place});
  }

  handleCustomLatitude(event){
    var new_place = this.state.place;
    new_place.latitude = event.target.value;
    this.setState({place: new_place});
  }

  handleCustomOrder(event){
    this.setState({order: event.target.value});
  }

  handleSubmit(){
    /*
    let new_places = this.props.trip;

    console.log("Place: " + this.state.place.name);
    console.log("Location: " + this.state.order);

    if(this.props.trip.places.length < 1){
      new_places.places = [this.state.place];
    } else if(this.state.order <= 1){
      new_places.places.splice(0, 0, this.state.place);
    } else if(this.state.order > this.props.trip.places.length){
      new_places.places = new_places.places.concat(this.state.place);
    } else {
      new_places.places.splice(this.state.order - 1, 0, this.state.place);
    }

    console.log(new_places);
    this.props.updateTrip(new_places);
    */
  }

  /* Sends a request to the server with the destinations and options.
  * Receives a response containing the map and itinerary to update the
  * state for this object.
  */
  fetchResponse(){
    // need to get the request body from the trip in state object.
    let requestBody = {
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
    let hasOrder = this.state.order === 0;
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