import React, {Component} from 'react';

/* Destinations reside in the parent object so they may be shared
 * with the Trip object.
 * Renders the current destination list.
 * Loads destinations from files.
 * Finds destinations in a database.
 * Displays the current number of destinations
 */
class Destinations extends Component {
  constructor(props) {
    super(props);
    this.count = 0;
    this.loadTFFI = this.loadTFFI.bind(this);
  }

  loadTFFI(event) {
    console.log(event.target.files[0].name);
    // now you need to read the file and create a JSON.
    // then you need to set the trip property
    // this.props.updateTrip(??);

    var reader = new FileReader();
    reader.readAsText(event.target.files[0]);

    reader.onload = function(event){

      var tffi = JSON.parse(event.target.result);

      this.props.updateTrip(tffi);
      this.count = this.props.trip.places.length;

    }.bind(this);
  }

  render() {
    // need to clean up the button
    // need to count the number in the trip
    return (
        <div id="destinations" className="card">
          <div className="card-header bg-info text-white">
            Destinations
          </div>
          <div className="card-body">
            <p>Load destinations from a file.</p>
            <div className="form-group" role="group">
                <input type="file" className="form-control-file" onChange={this.loadTFFI} id="tffifile" />
            </div>
            <h5>There are {this.count} destinations. </h5>
          </div>
        </div>
    )
  }
}

export default Destinations;