import React, {Component} from 'react';

class Query extends Component {
  constructor(props) {
    super(props);

    this.state = {
      search: "",
      places: []
    };

    this.handleSubmit = this.handleSubmit.bind(this);
    this.createTable = this.createTable.bind(this);
    this.handleSearch = this.handleSearch.bind(this);
    this.handleClear = this.handleClear.bind(this);
  }

  handleSubmit(event) {
    this.setState({search: event.target.value});
  }

  handleSearch(event) {
    this.query();
  }

  handleClear(event) {
    this.setState({places: []});
  }

  /* Sends a request to the server with all state information except type.
* Receives a response containing an optimized itinerary to update the
* state for this object.
*/
  fetchResponse() {
    // need to get the request body from the trip in state object.
    let requestBody = {
      "version": 2,
      "type": 'query',
      "query": this.state.search,
      "places": []
    };

    console.log(process.env.SERVICE_URL);
    console.log(requestBody);

    return fetch('http://' + location.host + '/query', {
      method: "POST",
      body: JSON.stringify(requestBody)
    });
  }

  async query() {
    try {
      let serverResponse = await this.fetchResponse();
      let tffi = await serverResponse.json();
      console.log("Fetched response from database");
      console.log(tffi);

      this.setState({places: tffi.places});

    } catch (err) {
      console.error(err);
    }
  }

  createTable() {

    let ids = [];
    let names = [];
    let municipalities = [];

    if (typeof this.state.places[0] != "undefined") {
      ids = this.state.places.map((item) => <td>{item.id}</td>);
      names = this.state.places.map((item) => <td>{item.name}</td>);
      municipalities = this.state.places.map(
          (item) => <td>{item.municipality}</td>);
    }

    console.log(ids);
    console.log(names);
    console.log(municipalities);

    return {ids, names, municipalities};
  }

  render() {
    let table = this.createTable();
    const numPlaces = this.state.places.length;
    const configVersion = parseInt(this.props.config.version);

    if (configVersion > 1) {
      return (
          <div>
            <label>
              Search destinations from a database:
            </label>
            <div className="input-group" role="group"><span
                className="input-group-btn"><button
                className="btn btn-outline-dark btn-success"
                onClick={this.handleSearch} type="button">Search</button>
              </span>
              <input type="text" className="form-control"
                     onChange={this.handleSubmit} placeholder="Find"/>
              <span className="input-group-btn">
                <button className="btn btn-outline-dark btn-success"
                        onClick={this.handleClear} type="button">Clear</button>
              </span>
            </div>
            {numPlaces > 0 &&
            <table className="table table-responsive table-bordered">
              <tbody>
              <tr className="table-info">
                <th className="align-middle">ID</th>
                {table.ids}
              </tr>
              </tbody>
              <tbody>
              <tr>
                <th className="table-info align-middle">Name</th>
                {table.names}
              </tr>
              </tbody>
              <tbody>
              <tr>
                <th className="table-info align-middle">Municipality</th>
                {table.municipalities}
              </tr>
              </tbody>
              <tbody>
              <tr>
                <th className="table-info align-middle">Add</th>
              </tr>
              </tbody>
            </table>
            }
          </div>
      )
    } else {
      return null;
    }
  }
}

export default Query;