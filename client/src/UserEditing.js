import React, {Component} from 'react';

class UserEditing extends Component {
  constructor(props) {
    super(props);
    this.state={
      selected: 0,
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

    this.handleCreation = this.handleCreation.bind(this);
    this.handleCustomName = this.handleCustomName.bind(this);
    this.handleCustomLongitude = this.handleCustomLongitude.bind(this);
    this.handleCustomLatitude = this.handleCustomLatitude.bind(this);
    this.handleCustomOrder = this.handleCustomOrder.bind(this);
    this.handleInsert = this.handleInsert.bind(this);

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

  handleInsert(){
    this.props.editTrip(this.state);
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
          <label className={"btn btn-outline-dark btn-success".concat((this.state.selected === 1) ? " active" : "")}>
            <input type="radio" id="new_place" name="new_place" value="on" onClick={this.handleCreation} />Create New Destination
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