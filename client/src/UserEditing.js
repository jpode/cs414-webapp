import React, {Component} from 'react';

class UserEditing extends Component {
  constructor(props) {
    super(props);
    this.state={
      selected: 0,
      editType: "",
      targetIndex: 0,
      destIndex: -1,
      newPlace: {
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
    this.handleInsert = this.handleInsert.bind(this);
    this.resetState = this.resetState.bind(this);

  }

  handleCreation(event){
    this.setState({editType : "insert"});
    if(this.state.selected === 0){
      this.setState({selected : 1});
    } else {
      this.setState({selected : 0});
    }
  }

  handleCustomName(event){
    var place = this.state.newPlace;
    place.name = event.target.value;
    place.id = event.target.value.substring(0,4);
    this.setState({newPlace: place});

  }

  handleCustomLongitude(event){
    var place = this.state.newPlace;
    place.longitude = event.target.value;
    this.setState({newPlace: place});
  }

  handleCustomLatitude(event){
    var place = this.state.newPlace;
    place.latitude = event.target.value;
    this.setState({newPlace: place});
  }

  handleCustomOrder(event){
    if(event.target.value < 1){
      alert("Please enter a value greater than 0.")
    } else {
      this.setState({destIndex: event.target.value});
    }
  }

  handleInsert(){
    this.props.editTrip(this.state);
    this.resetState();
  }

  resetState(){
    this.setState({selected : 0});
    this.setState({editType : ""});
    this.setState({targetIndex : 0});
    this.setState({destIndex : -1});
    this.setState({newPlace : {id : "", name : "", longitude: "", latitude: ""}});
  }

  render(){
    let hasName = this.state.newPlace.name !== "";
    let hasLongitude = this.state.newPlace.longitude !== "";
    let hasLatitude = this.state.newPlace.latitude !== "";
    let hasDestIndex = this.state.destIndex >= 0;

    return(
      <div id="options" className="card">
        <div className="card-header bg-success text-white">
          Edit Destinations
        </div>

        <div className="btn-group btn-group-toggle" data-toggle="buttons">
          <label className={"btn btn-outline-dark btn-success".concat((this.state.selected === 1) ? " active" : "")}>
            <input type="radio" id="newPlace" name="newPlace" value="on" onClick={this.handleCreation} />Create New Destination
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
              <button disabled={!hasName || !hasLongitude || !hasLatitude || !hasDestIndex}
                      className="btn btn-outline-dark btn-success" onClick={this.handleInsert} type="button">Submit</button>
              </span>
            </span>
          </div>
        }
      </div>
    )
  }
}

export default UserEditing;