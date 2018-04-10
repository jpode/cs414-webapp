import React, {Component} from 'react';

class UserEditing extends Component {
  constructor(props) {
    super(props);
    this.state={
      insertSelected: 0,
      removeSelected: 0,
      reverseSelected: 0,
      newStartSelected: 0,
      editType: "",
      targetIndex: 0,
      destIndex: -1,
      new_place: {
        id: "",
        name: "",
        longitude: "",
        latitude: ""
      }
    }

    this.handleSelect = this.handleSelect.bind(this);

    this.insert = this.insert.bind(this);
    this.handleCustomName = this.handleCustomName.bind(this);
    this.handleCustomLongitude = this.handleCustomLongitude.bind(this);
    this.handleCustomLatitude = this.handleCustomLatitude.bind(this);
    this.handleCustomOrder = this.handleCustomOrder.bind(this);

    this.handleRemove = this.handleRemove.bind(this);
    this.handleReverse = this.handleReverse.bind(this);
    this.handleNewStart = this.handleNewStart.bind(this);


  }

  handleSelect(arg){
    this.setState({editType : arg});
    if(arg === "insert") {
      if (this.state.insertSelected === 0) {
        this.setState({insertSelected: 1});
      } else {
        this.setState({insertSelected: 0});
      }
    }
    if(arg === "remove") {
      if (this.state.removeSelected === 0) {
        this.setState({removeSelected: 1});
      } else {
        this.setState({removeSelected: 0});
      }
    }
    if(arg === "reverse") {
      if (this.state.reverseSelected === 0) {
        this.setState({reverseSelected: 1});
      } else {
        this.setState({reverseSelected: 0});
      }
    }
    if(arg === "newStart") {
      if (this.state.newStartSelected === 0) {
        this.setState({newStartSelected: 1});
      } else {
        this.setState({newStartSelected: 0});
      }
    }
  }

  insert(){
    this.props.editTrip(this.state);
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

  handleRemove(event){
    alert("Not implemented");
  }

  handleReverse(event){
    alert("Not implemented");
  }

  handleNewStart(event){
    alert("Not implemented");
  }

  render(){
    let hasName = this.state.new_place.name !== "";
    let hasLongitude = this.state.new_place.longitude !== "";
    let hasLatitude = this.state.new_place.latitude !== "";
    let hasDestIndex = this.state.destIndex >= 0;

    return(
      <div id="options" className="card">
        <div className="card-header bg-success text-white">
          Edit Destinations
        </div>

        <div className="btn-group btn-group-toggle" data-toggle="buttons">
          <label className={"btn btn-outline-dark btn-success".concat((this.state.insertSelected === 1) ? " active" : "")}>
            <input type="radio" id="new_place" name="new_place" value="on" onClick={this.handleSelect("insert")} />Create New Destination
          </label>
          <label className={"btn btn-outline-dark btn-success".concat((this.state.removeSelected === 1) ? " active" : "")}>
            <input type="radio" id="remove" name="remove" value="on" onClick={this.handleSelect("remove")} />Remove A Place
          </label>
          <label className={"btn btn-outline-dark btn-success".concat((this.state.reverseSelected === 1) ? " active" : "")}>
            <input type="radio" id="reverse" name="reverse" value="on" onClick={this.handleSelect("reverse")} />Reverse The Trip
          </label>
          <label className={"btn btn-outline-dark btn-success".concat((this.state.newStartSelected === 1) ? " active" : "")}>
            <input type="radio" id="newStart" name="newStart" value="on" onClick={this.handleSelect("newStart")} />Select New Start Location
          </label>
        </div>

        {this.state.insertSelected === 1 &&
          <div className="input-group" role="group">
            <span>
              <input type="text" className="form-control" onChange={this.handleCustomName} placeholder="Destination Name"/>
              <input type="text" className="form-control" onChange={this.handleCustomLongitude} placeholder="Destination Longitude"/>
              <input type="text" className="form-control" onChange={this.handleCustomLatitude} placeholder="Destination Latitude"/>
              <input type="text" className="form-control" onChange={this.handleCustomOrder} placeholder="Order In Trip"/>
              <span className="input-group-btn">
              <button disabled={!hasName || !hasLongitude || !hasLatitude || !hasDestIndex}
                      className="btn btn-primary " onClick={this.handleInsert} type="button">Submit</button>
              </span>
            </span>
          </div>
        }
      </div>
    )
  }
}

export default UserEditing;