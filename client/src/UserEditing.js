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
        latitude: "",
      }
    }
    this.handleCreation = this.handleCreation.bind(this);
    this.handleCustomName = this.handleCustomName.bind(this);
    this.handleCustomLongitude = this.handleCustomLongitude.bind(this);
    this.handleCustomLatitude = this.handleCustomLatitude().bind(this);
    this.handleCustomOrder = this.handleCustomOrder.bind(this);
  }

  handleCreation(event){
    if(this.state.selected == 0){
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

  handleCustomLongitude(){
    var new_place = this.state.place;
    new_place.longitude = event.target.value;
    this.setState({place: new_place});
  }

  handleCustomLatitude(){
    var new_place = this.state.place;
    new_place.latitude = event.target.value;
    this.setState({place: new_place});
  }

  handleCustomOrder(){
    this.setState({order:0});
  }

  handleSubmit(){
    this.props.addPlace(this.state.place, location);
  }

  render(){
    var hasName = this.state.place.name != "";
    var hasLongitude = this.state.place.longitude != "";
    var hasLatitude = this.state.place.latitude != "";
    var hasOrder = this.state.order != 0;
    return(
      <div id="options" className="card">
        <div className="card-header bg-success text-white">
          Edit Destinations
        </div>
        <span className="input-group-btn">
          <button className="btn btn-primary " onClick={this.handleCreation} type="button">Create New Destination</button>
        </span>
        {this.state.selected == 1 &&
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

export default Application;