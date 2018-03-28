import React, {Component} from 'react';
import Query from './Query';

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
    this.loadTFFI = this.loadTFFI.bind(this);
  }

  loadTFFI(event) {
    console.log(event.target.files[0].name);

    var reader = new FileReader();
    reader.readAsText(event.target.files[0]);

    reader.onload = function(event){

      var tffi = JSON.parse(event.target.result);
      this.props.updateTrip(tffi);

    }.bind(this);
  }

  render() {
    return (
        <div id="destinations" className="card">
          <div className="card-header bg-success text-white">
            Destinations
          </div>
          <div className="card-body">
            <p>Load destinations from a file.</p>
            <div className="form-group" role="group">
                <input type="file" className="form-control-file" onChange={this.loadTFFI} id="tffifile" />
            </div>
            <h5>There are {this.props.trip.places.length} destinations. </h5>
            <hr/>
            <Query query={this.props.query}/>
          </div>
        </div>
    )
  }
}

export default Destinations;