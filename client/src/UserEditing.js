import React, {Component} from 'react';

class UserEditing extends Component {
  constructor(props) {
    super(props);
    this.state={
      insertSelected: 0,
      removeSelected: 0,
      reverseSelected: 0,
      newStartSelected: 0,
      movePlaceSelected: 0,
      editType: "",
      targetIndex: -1,
      destIndex: -1,
      newPlace: {
        id: "",
        name: "",
        longitude: "",
        latitude: ""
      }
    }

    this.handleSelect = this.handleSelect.bind(this);
    this.handleDestIndex = this.handleDestIndex.bind(this);
    this.handleTargetIndex = this.handleTargetIndex.bind(this);
    this.handleCustomID = this.handleCustomID.bind(this);
    this.handleCustomName = this.handleCustomName.bind(this);
    this.handleCustomLongitude = this.handleCustomLongitude.bind(this);
    this.handleCustomLatitude = this.handleCustomLatitude.bind(this);
    this.resetState = this.resetState.bind(this);

    this.changeStartLocation = this.changeStartLocation.bind(this);
    this.removePlace = this.removePlace.bind(this);
    this.addPlace = this.addPlace.bind(this);
    this.movePlace = this.movePlace.bind(this);
    this.reverseTrip = this.reverseTrip.bind(this);

  }

  handleSelect(arg){
    this.resetState();
    this.setState({editType : arg});
    if(arg === "insert") {
      if (this.state.insertSelected === 0) {
        this.setState({insertSelected: 1});
      } else {
        this.setState({insertSelected: 0});
      }
    } else if(arg === "remove") {
      if (this.state.removeSelected === 0) {
        this.setState({removeSelected: 1});
      } else {
        this.setState({removeSelected: 0});
      }
    } else if(arg === "reverse") {
      if (this.state.reverseSelected === 0) {
        this.setState({reverseSelected: 1});
        this.setState({editType : arg}, this.edit);
      } else {
        this.setState({reverseSelected: 0});
      }
    } else if(arg === "newStart") {
      if (this.state.newStartSelected === 0) {
        this.setState({newStartSelected: 1});
      } else {
        this.setState({newStartSelected: 0});
      }
    } else if(arg === "movePlace") {
      if (this.state.movePlaceSelected === 0) {
        this.setState({movePlaceSelected: 1});
      } else {
        this.setState({movePlaceSelected: 0});
      }
    }


  }

  handleDestIndex(event){
    if(event.target.value > this.props.trip.places.length){
      this.setState({destIndex: this.props.trip.places.length - 1});
    } else if(event.target.value < 1) {
      this.setState({destIndex: 0});
    } else {
      this.setState({destIndex: event.target.value - 1});
    }
  }

  handleTargetIndex(event){
    if(event.target.value > this.props.trip.places.length){
      this.setState({targetIndex: this.props.trip.places.length - 1});
    } else if(event.target.value < 1) {
      this.setState({targetIndex: 0});
    } else {
      this.setState({targetIndex: event.target.value - 1});
    }
  }

  handleCustomID(event){
    let place = this.state.newPlace;
    place.id = event.target.value;
    this.setState({newPlace: place});
  }

  handleCustomName(event){
    let place = this.state.newPlace;
    place.name = event.target.value;
    this.setState({newPlace: place});
  }

  handleCustomLongitude(event){
    let place = this.state.newPlace;
    place.longitude = event.target.value;
    this.setState({newPlace: place});
  }

  handleCustomLatitude(event){
    let place = this.state.newPlace;
    place.latitude = event.target.value;
    this.setState({newPlace: place});
  }

  resetState(){
    this.setState({insertSelected : 0});
    this.setState({removeSelected : 0});
    this.setState({reverseSelected : 0});
    this.setState({newStartSelected : 0});
    this.setState({movePlaceSelected : 0});
    this.setState({editType : ""});
    this.setState({targetIndex : 0});
    this.setState({destIndex : -1});
    this.setState({targetIndex : -1});
    this.setState({newPlace : {id : "", name : "", longitude: "", latitude: ""}});
  }

  changeStartLocation(){
    let editedTrip = this.props.trip;
    editedTrip.places = editedTrip.places.concat(editedTrip.places.splice(0, this.state.targetIndex));
    editedTrip.distances = editedTrip.distances.concat(editedTrip.distances.splice(0, this.state.targetIndex));
    this.props.updateTrip(editedTrip);
    this.resetState();
  }

  removePlace(){
    let editedTrip = this.props.trip;
    editedTrip.places.splice(this.state.targetIndex, 1);
    this.props.updateTrip(editedTrip);
    this.props.plan();
    this.resetState();
  }

  addPlace(){
    let editedTrip = this.props.trip;
    editedTrip.places.splice(this.state.destIndex, 0, this.state.newPlace);
    this.props.updateTrip(editedTrip);
    this.props.plan();
    this.resetState();
  }

  movePlace(){
    let editedTrip = this.props.trip;
    editedTrip.places.splice(this.state.destIndex, 0, editedTrip.places.splice(this.state.targetIndex, 1)[0]);
    this.props.updateTrip(editedTrip);
    this.props.plan();
    this.resetState();
  }

  reverseTrip(){
    let editedTrip = this.props.trip;
    editedTrip.places = editedTrip.places.splice(0,1).concat(editedTrip.places.reverse());
    editedTrip.distances.reverse();
    this.props.updateTrip(editedTrip);
    this.resetState();
  }

  render(){
    let hasId = this.state.newPlace.id !== "";
    let hasName = this.state.newPlace.name !== "";
    let hasLongitude = this.state.newPlace.longitude !== "";
    let hasLatitude = this.state.newPlace.latitude !== "";
    let hasDestIndex = this.state.destIndex >= 0;
    let hasTargetIndex = this.state.targetIndex >= 0;

    return(
      <div id="options" className="card">
        <div className="card-header bg-success text-white">
          Edit Destinations
        </div>
        <div className="card-body">
          <div className="btn-group btn-group-toggle" data-toggle="buttons">
            <label className={"btn btn-outline-dark btn-success".concat((this.state.insertSelected === 1) ? " active" : "")}>
              <input type="radio" id="new_place" name="new_place" value="on" onClick={() => { this.handleSelect("insert")}} />Create New Destination
            </label>
            <label className={"btn btn-outline-dark btn-success".concat((this.state.newStartSelected === 1) ? " active" : "")}>
              <input type="radio" id="newStart" name="newStart" value="on" onClick={() => { this.handleSelect("newStart")}} />Select New Start Location
            </label>
          </div>

          {this.state.insertSelected === 1 &&
            <div className="input-group" role="group">
              <span>
                <input type="text" className="form-control" onChange={this.handleCustomID} placeholder="Destination ID"/>
                <input type="text" className="form-control" onChange={this.handleCustomName} placeholder="Destination Name"/>
                <input type="text" className="form-control" onChange={this.handleCustomLongitude} placeholder="Destination Longitude"/>
                <input type="text" className="form-control" onChange={this.handleCustomLatitude} placeholder="Destination Latitude"/>
                <input type="text" className="form-control" onChange={this.handleDestIndex} placeholder="Order In Trip"/>
                <span className="input-group-btn">
                  <button disabled={!hasId || !hasName || !hasLongitude || !hasLatitude || !hasDestIndex}
                   className="btn btn-primary " onClick={() => {this.addPlace()}} type="button">Submit</button>
                </span>
              </span>
            </div>
          }
          {this.state.newStartSelected === 1 &&
          <div className="input-group" role="group">
              <span>
                <input type="text" className="form-control" onChange={this.handleTargetIndex} placeholder="Index of New Start"/>
                <span className="input-group-btn">
                  <button disabled={!hasTargetIndex} className="btn btn-primary " onClick={() => {this.changeStartLocation()}} type="button">Submit</button>
                </span>
              </span>
          </div>
          }
        </div>
        <div className="card-body">
          <div className="btn-group btn-group-toggle" data-toggle="buttons">
            <label className={"btn btn-outline-dark btn-success".concat((this.state.removeSelected === 1) ? " active" : "")}>
              <input type="radio" id="remove" name="remove" value="on" onClick={() => { this.handleSelect("remove")}} />Remove A Place
            </label>
            <label className={"btn btn-outline-dark btn-success".concat((this.state.reverseSelected === 1) ? " active" : "")}>
              <input type="radio" id="reverse" name="reverse" value="on" onClick={() => { this.reverseTrip()}} />Reverse The Trip
            </label>
            <label className={"btn btn-outline-dark btn-success".concat((this.state.movePlaceSelected === 1) ? " active" : "")}>
              <input type="radio" id="movePlace" name="movePlace" value="on" onClick={() => { this.handleSelect("movePlace")}} />Move a Place
            </label>
          </div>
          {this.state.removeSelected === 1 &&
          <div className="input-group" role="group">
              <span>
                <input type="text" className="form-control" onChange={this.handleTargetIndex} placeholder="Index to Remove"/>
                <span className="input-group-btn">
                  <button disabled={!hasTargetIndex} className="btn btn-primary " onClick={() => {this.removePlace()}} type="button">Submit</button>
                </span>
              </span>
          </div>
          }
          {this.state.movePlaceSelected === 1 &&
          <div className="input-group" role="group">
              <span>
                <input type="text" className="form-control" onChange={this.handleTargetIndex} placeholder="Index to Move"/>
                <input type="text" className="form-control" onChange={this.handleDestIndex} placeholder="Index to Move To"/>
                <span className="input-group-btn">
                  <button disabled={!hasDestIndex || !hasTargetIndex} className="btn btn-primary " onClick={() => {this.movePlace()}} type="button">Submit</button>
                </span>
              </span>
          </div>
          }
        </div>
      </div>
    )
  }
}

export default UserEditing;