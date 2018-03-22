import React, {Component} from 'react';

class Query extends Component {
  constructor(props) {
    super(props);

    this.state = {
      search: "",
      places : []
    };

    this.handleSubmit = this.handleSubmit.bind(this);
    this.createTable = this.createTable.bind(this);
  }

  handleSubmit(event)
  {
    this.setState({search: event.target.value});
    this.query()
  }

  /* Sends a request to the server with all state information except type.
* Receives a response containing an optimized itinerary to update the
* state for this object.
*/
  fetchResponse(){
    // need to get the request body from the trip in state object.
    let requestBody = {
      "version" : 2,
      "type"   : 'query',
      "query" : this.state.search,
      "places"  : []
    };

    console.log(process.env.SERVICE_URL);
    console.log(requestBody);

    return fetch('http://' + location.host + '/query', {
      method:"POST",
      body: JSON.stringify(requestBody)
    });
  }

  async query(){
    try {
      let serverResponse = await this.fetchResponse();
      let tffi = await serverResponse.json();

      console.log(tffi);
      this.state.places = tffi.trip.places;

    } catch(err) {
      console.error(err);
    }
  }

  createTable () {

    let ids = [];
    let names = [];
    let municipalities = [];

    if(typeof this.state.places[0] != "undefined") {
      ids = this.state.places.map((item) => <td>{item.id}</td>);
      names = this.state.places.map((item) => <td>{item.name}</td>);
      municipalities = this.state.places.map((item) => <td>{item.municipality}</td>);
    }

    return {ids, names, municipalities};
  }

  render() {
    let table = this.createTable();
    return (
        <div>
          <label>
            Search destinations from a database:
            <input type="checkbox" name="searchCheck" value="on"/>
          </label>
          <div className="input-group" role="group">
            <input type="text" className="form-control" placeholder="Find"/>
            <span className="input-group-btn">
              <button className="btn btn-primary " onClick={this.handleSubmit} type="button">Search</button>
            </span>
          </div>
          <table className="table table-responsive table-bordered">
            <thead>
            <tr className="table-info">
              <th className="align-middle">ID</th>
              {table.ids}
            </tr>
            </thead>
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
        </div>
    )
  }
}

export default Query;