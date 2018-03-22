import React, {Component} from 'react';

class Query extends Component {

  render() {
    // see team 03 for good build trip table
    return (
        <div>
          <label>
            Search destinations from a database:
            <input type="checkbox" name="searchCheck" value="on"/>
          </label>
          <div className="input-group" role="group">
            <input type="text" className="form-control"
                   onChange={this.handleSubmit} placeholder="Find"/>
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
    )
  }
}

export default Query;