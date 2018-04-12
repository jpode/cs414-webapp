import React, {Component} from 'react';

class Query extends Component {
  constructor(props) {
    super(props);

    this.state={
      selected: 0,
      editType: "insert",
      search: "",
      filters: [],
      places: [],
      targetIndex: 1,
      destIndex: 1,
      newPlace: {
        id: "",
        name: "",
        longitude: "",
        latitude: ""
      }
    };

    this.handleSubmit = this.handleSubmit.bind(this);
    this.createTable = this.createTable.bind(this);
    this.handleSearch = this.handleSearch.bind(this);
    this.handleClear = this.handleClear.bind(this);
    this.handleAdd = this.handleAdd.bind(this);
    this.handleCreation = this.handleCreation.bind(this);
    this.handleApplyFilters = this.handleApplyFilters.bind(this);
    this.handleClearFilters = this.handleClearFilters.bind(this);
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
      "filters": this.state.filters,
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
  //add the place to the current trip
  handleAdd(e)
  {
    console.log(e);
    let place = this.state.newPlace;
    place.id = e.id;
    place.name = e.name;
    place.latitude = e.latitude;
    place.longitude = e.longitude;
    console.log(place);
    this.setState({newPlace: place});
    this.props.editTrip(this.state);
  }

  handleCreation(){
    if(this.state.selected === 0){
      this.setState({selected : 1});
    } else {
      this.setState({selected : 0});
    }
  }

  handleApplyFilters() {
    let filters = this.state.filters;
    var e = document.getElementById("typeSelect");
    if(e.options[e.selectedIndex].value != "(none)") {
      var strUser = e.options[e.selectedIndex].value;
      // console.log(strUser);
      // if(!filters.includes(strUser)) {
        filters = filters.concat(strUser);
      }
    // }
    // if(e.options[e.selectedIndex].value != "(none)") {
    //   e = document.getElementById("countriesSelect");
    //   strUser = e.options[e.selectedIndex].value;
    //   console.log(strUser);
    //   if(!filters.includes(strUser)) {
    //     filters = filters.concat(strUser);
    //   }
    // }
    // if(e.options[e.selectedIndex].value != "(none)") {
    //   e = document.getElementById("regionSelect");
    //   strUser = e.options[e.selectedIndex].value;
    //   console.log(strUser);
    //   if(!filters.includes(strUser)) {
    //     filters = filters.concat(strUser);
    //   }
    // }
    // if(e.options[e.selectedIndex].value != "(none)") {
    //   e = document.getElementById("continentSelect");
    //   strUser = e.options[e.selectedIndex].value;
    //   console.log(strUser);
    //   if(!filters.includes(strUser)) {
    //     filters = filters.concat(strUser);
    //   }
    // }
    this.setState({filters: filters});
    console.log(this.state.filters);
  }

  handleClearFilters() {
    this.setState({filters: []});
  }

  createTable()
  {

    let ids = [];
    let names = [];
    let municipalities = [];
    let btns = [];
    let i = 0;

    if (typeof this.state.places[0] != "undefined") {
      ids = this.state.places.map((item) => <td>{item.id}</td>);
      names = this.state.places.map((item) => <td>{item.name}</td>);
      municipalities = this.state.places.map(
          (item) => <td>{item.municipality}</td>);
      btns = this.state.places.map(
          (i) => <td><button className="btn btn-outline-dark btn-success" onClick={() => this.handleAdd(i)} >Add</button></td>);
    }

    return {ids, names, municipalities, btns};
  }

  createFilters()
  {
    let types = [];
    let countries = [];
    let regions = [];
    let continents = [];

    if(this.props.config.filters.length > 0) {
      types = this.props.config.filters[0].values.map((item) => <option value={item} > {item}</option>);
      countries = this.props.config.filters[1].values.map((item) => <option value={item} > {item}</option>);
      regions = this.props.config.filters[2].values.map((item) => <option value={item} > {item}</option>);
      continents = this.props.config.filters[3].values.map((item) => <option value={item} > {item}</option>);
    }
    return {types,countries,regions,continents};
  }

  render() {
    let table = this.createTable();
    let filter = this.createFilters();
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
              <input type="text" className="form-control input-success"
                     onChange={this.handleSubmit} placeholder="Find"/>
              <span className="input-group-btn">
                <button className="btn btn-outline-dark btn-success"
                        onClick={this.handleClear} type="button">Clear</button>
                <div className="btn-group btn-group-toggle" data-toggle="buttons">
                  <label className={"btn btn-outline-dark btn-success".concat((this.state.selected == 1) ? " active" : "")}>
                    <input type="radio" id="new_place" name="new_place" value="on" onClick={this.handleCreation} />Add Filters
                  </label>
                </div>
              </span>
            </div>
            {this.state.selected == 1 &&
            <div>
              <span className="input-group">
                Type:
                <select id="typeSelect">
                  <option value="(none)"> (none)</option>
                  {filter.types}
                </select>
              </span>
              <span className="input-group">
                Country:
                <select id="countriesSelect">
                  <option value="(none)"> (none)</option>
                  {filter.countries}
                </select>
              </span>
              <span className="input-group">
                Region:
                <select id="regionSelect">
                  <option value="(none)"> (none)</option>
                  {filter.regions}
                </select>
              </span>
              <span className="input-group">
                Continent:
                <select id="continentSelect">
                  <option value="(none)"> (none)</option>
                  {filter.continents}
                </select>
              </span>
              <span>
               <button className="btn btn-outline-dark btn-success" onClick={this.handleApplyFilters} > Apply Filters </button>
               <button className="btn btn-outline-dark btn-success" onClick={this.handleClearFilters} > Clear Filters </button>
              </span>
              <hr/>
            </div>
            }
            {numPlaces > 0 &&
            <table className="table table-responsive table-bordered">
              <tbody>
              <tr>
                <th className="table-success align-middle">ID</th>
                {table.ids}
              </tr>
              </tbody>
              <tbody>
              <tr>
                <th className="table-success align-middle">Name</th>
                {table.names}
              </tr>
              </tbody>
              <tbody>
              <tr>
                <th className="table-success align-middle">Municipality</th>
                {table.municipalities}
              </tr>
              </tbody>
              <tbody>
              <tr>
                <th className="table-success align-middle">Add</th>
                {table.btns}
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
