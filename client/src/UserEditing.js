import React, {Component} from 'react';

class UserEditing extends Component {
  constructor(props) {
    super(props);
    this.state={
      selected: 0,
      order: 0,
      place: {
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
    console.log("props.trip=");
    console.log(this.props.trip);

    let new_places = this.props.trip;

    console.log("Place: " + this.state.place.name);
    console.log("Location: " + this.state.order);

    if(this.props.trip.places.length < 1){
      console.log("No current places in array");
      new_places.places = [this.state.place];
    } else if(this.state.order <= 1){
      console.log("Adding to beginning of array");
      new_places.places.splice(0, 0, this.state.place);
    } else if(this.state.order > this.props.trip.places.length){
      console.log("Adding to end of array");
      new_places.places = new_places.places.concat(this.state.place);
    } else {
      console.log("Inserting into index " + this.state.order + " of array");
      new_places.places.splice(this.state.order - 1, 0, this.state.place);
    }

    console.log(new_places);
    this.props.updateTrip(new_places);
  }

  render(){
    let hasName = this.state.place.name !== "";
    let hasLongitude = this.state.place.longitude !== "";
    let hasLatitude = this.state.place.latitude !== "";
    let hasOrder = this.state.order !== 0;
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
              <button disabled={!hasName && !hasLongitude && !hasLatitude && !hasOrder} className="btn btn-primary " onClick={this.handleSubmit} type="button">Submit</button>
              </span>
            </span>
          </div>
        }
      </div>
    )
  }
}

export default UserEditing;