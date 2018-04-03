import React, {Component} from 'react';
import Destinations from './Destinations';

class Query extends Component {
  constructor(props) {
    super(props);

    this.state = {
      search: "",
      places : []
    };

    this.handleSubmit = this.handleSubmit.bind(this);
    this.createTable = this.createTable.bind(this);
    this.handleSearch = this.handleSearch.bind(this);
    this.handleClear = this.handleClear.bind(this);
  }

  handleSubmit(event)
  {
    this.setState({search: event.target.value});
  }

  handleSearch(event)
  {
    this.query();
  }

  handleClear(event)
  {
    this.setState({places: []});
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
      console.log("Fetched response from database");
      tffi = JSON.parse(tffi);
      console.log(tffi);

      this.setState({places: tffi.places});

    } catch(err) {
      console.error(err);
    }
  }
  //temporary for testing btns
  outputBtn(e)
  {
    console.log(e);
  }

  createTable () {

    let ids = [];
    let names = [];
    let municipalities = [];
    let btns = [];

    if (typeof this.state.places[0] != "undefined") {
      ids = this.state.places.map((item) => <td>{item.id}</td>);
      names = this.state.places.map((item) => <td>{item.name}</td>);
      municipalities = this.state.places.map(
          (item) => <td>{item.municipality}</td>);
      btns = this.state.places.map((item) => <td><button className="btn " onClick={() => this.outputBtn(item.id)} >Add</button></td>)
    }

    console.log(ids);
    console.log(names);
    console.log(municipalities);

    return {ids, names, municipalities, btns};
  }

  render() {
    let table = this.createTable();
    const numPlaces = this.state.places.length;

    return (
        <div>
          <label>
            Search destinations from a database:
          </label>
          <div className="input-group" role="group">
            <span className="input-group-btn">
              <button className="btn btn-outline-dark btn-success" onClick={this.handleSearch} >Search</button>
            </span>
            <input type="text" className="form-control" onChange = {this.handleSubmit} placeholder="Find"/>
            <span className="input-group-btn">
              <button className="btn btn-outline-dark btn-success" onClick={this.handleClear} >Clear</button>
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
                {table.btns}
              </tr>
              </tbody>
            </table>
          }
        </div>
    )
  }
}

export default Query;