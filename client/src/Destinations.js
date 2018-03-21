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

    }.bind(this);
  }

  render() {
    // see team 03 for good build trip table
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
            <h5>There are {this.props.trip.places.length} destinations. </h5>
            <hr/>
            <label>
              Search destinations from a database:
              <input type="checkbox" name="searchCheck" value="on"/>
            </label>
            <div className="input-group" role="group">
            <input type="text" className="form-control" onChange = {this.handleSubmit} placeholder="Find"/>
            <span className="input-group-btn">
              <button className="btn btn-primary " type="button">Search</button>
            </span>
            </div>
            <table className="table table-responsive table-bordered">
              <thead>
              <tr className="table-info">
                <th className="align-middle">ID</th>
              </tr>
              </thead>
              <tbody>
              <tr>
                <th className="table-info align-middle">Name</th>
              </tr>
              </tbody>
              <tbody>
              <tr>
                <th className="table-info align-middle">Municipality</th>
              </tr>
              </tbody>
              <tbody>
              <tr>
                <th className="table-info align-middle">Add</th>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
    )
  }
}

export default Destinations;