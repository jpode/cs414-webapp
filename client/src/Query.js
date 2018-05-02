import React, {Component} from 'react';

class Query extends Component {
  constructor(props) {
    super(props);

    this.state={
      selected: 0,
      editType: "insert",
      search: "",
      limit: 0,
      filters: [],
      filterHold: [],
      newFilter: {
        attribute : "",
        values : []
      },
      places: [],
      newPlace: {
        id: "",
        name: "",
        longitude: "",
        latitude: ""
      }
    };

    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleLimit = this.handleLimit.bind(this);
    this.createTable = this.createTable.bind(this);
    this.handleSearch = this.handleSearch.bind(this);
    this.handleClear = this.handleClear.bind(this);
    this.handleAdd = this.handleAdd.bind(this);
    this.handleCreation = this.handleCreation.bind(this);

    //These functions all handle adding/removing filters.
    //It looks like a bad hack because it is, but it works.
    this.addTypeFilter = this.addTypeFilter.bind(this);
    this.addRegionFilter = this.addRegionFilter.bind(this);
    this.addCountryFilter = this.addCountryFilter.bind(this);
    this.addContinentFilter = this.addContinentFilter.bind(this);
    this.holdFilter = this.holdFilter.bind(this);
    this.addHeldFilters = this.addHeldFilters.bind(this);
    this.handleClearFilters = this.handleClearFilters.bind(this);
  }

  handleSubmit(event) {
    this.setState({search: event.target.value});
  }

  handleLimit(event) {
    this.setState({limit: parseInt(event.target.value)});
  }

  handleSearch(event) {
    this.query();
  }

  handleClear(event) {
    this.setState({places: []});
  }

  /*
   * Sends a request to the server with a query object constructed from state.
   * Receives a response containing an optimized itinerary to update the
   * state for this object.
   */
  fetchResponse() {
    // need to get the request body from the trip in state object.
    let requestBody = {
      "version": 2,
      "type": 'query',
      "limit": this.state.limit,
      "query": this.state.search,
      "filters": this.state.filters,
      "places": []
    };

    console.log(process.env.SERVICE_URL);
    console.log(requestBody);

    return fetch('http://' + this.props.host + '/query', {
      header: {'Access-Control-Allow-Origin':'*'},
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
    this.addPlace();
  }

  addPlace(){
    let editedTrip = this.props.trip;

    editedTrip.places.splice(editedTrip.places.length, 0, this.state.newPlace);

    this.props.updateTrip(editedTrip);
    this.props.plan();
  }

  handleCreation(){
    if(this.state.selected === 0){
      this.setState({selected : 1});
    } else {
      this.setState({selected : 0});
    }
  }

  addTypeFilter() {
    var strUser;
  }

  handleApplyFilters(){
    let e = document.getElementById("typeSelect");
    let strUser;
    if(e.options[e.selectedIndex].value !== "(none)") {
      strUser = e.options[e.selectedIndex].value;
      this.setState({newFilter: {attribute : "type", values: [strUser]}}, () => {this.holdFilter()});
    }
  }

  addRegionFilter(){
    var strUser;

    var e = document.getElementById("regionSelect");
    if(e.options[e.selectedIndex].value !== "(none)") {
      strUser = e.options[e.selectedIndex].value;
      this.setState({newFilter: {attribute : "region", values: [strUser]}}, () => {this.holdFilter()});
    }
  }

  addCountryFilter(){
    var strUser;

    var e = document.getElementById("countrySelect");
    if(e.options[e.selectedIndex].value !== "(none)") {
      strUser = e.options[e.selectedIndex].value;
      this.setState({newFilter: {attribute : "country", values: [strUser]}}, () => {this.holdFilter()});
    }
  }

  addContinentFilter(){
    var strUser;

    var e = document.getElementById("continentSelect");
    if(e.options[e.selectedIndex].value !== "(none)") {
      strUser = e.options[e.selectedIndex].value;
      this.setState({newFilter: {attribute : "continent", values: [strUser]}}, () => {this.holdFilter()});
    }
  }

  holdFilter(){
    let newFilters = this.state.filterHold;
    newFilters.push(this.state.newFilter);
    this.setState({filterHold: newFilters});
  }

  addHeldFilters(){
    this.setState({filters: this.state.filterHold});
    console.log(this.state.filters);
  }

  handleClearFilters() {
    this.setState({filterHold: [], filters: [], selected: 0});
  }

  createTable()
  {

    let ids = [];
    let names = [];
    let municipalities = [];
    let btns = [];
    let i = 0;

    if (typeof this.state.places[0] !== "undefined") {
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

    if(typeof this.props.config.filters.length !== 'undefined' && this.props.config.filters.length > 0) {
      for(var i = 0; i < this.props.config.filters.length; i++){
        if(this.props.config.filters[i].attribute === "type"){
          types = this.props.config.filters[i].values.map((item) => <option value={item} > {item}</option>);
        } else if(this.props.config.filters[i].attribute === "country"){
          countries = this.props.config.filters[i].values.map((item) => <option value={item} > {item}</option>);
        } else if(this.props.config.filters[i].attribute === "region"){
          regions = this.props.config.filters[i].values.map((item) => <option value={item} > {item}</option>);
        } else if(this.props.config.filters[i].attribute === "continents"){
          continents = this.props.config.filters[i].values.map((item) => <option value={item} > {item}</option>);
        }
      }
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
              <input type="text" className="form-control input-success"
                     onChange={this.handleLimit} placeholder="Limit number of returned places"/>
              <span className="input-group-btn">
                <button className="btn btn-outline-dark btn-success"
                        onClick={this.handleClear} type="button">Clear</button>
                <div className="btn-group btn-group-toggle" data-toggle="buttons">
                  <label className={"btn btn-outline-dark btn-success".concat((this.state.selected === 1) ? " active" : "")}>
                    <input type="radio" id="new_place" name="new_place" value="on" onClick={this.handleCreation} />Add Filters
                  </label>
                </div>
              </span>
            </div>
            {this.state.selected === 1 &&
            <div>
              <span className="input-group">
                Type:
                <select id="typeSelect" onChange={this.addTypeFilter} style={{width:'211px'}}>
                  <option value="(none)"> (none)</option>
                  {filter.types}
                </select>
                Region:
                <select id="regionSelect" onChange={this.addRegionFilter} style={{width:'211px'}}>
                  <option value="(none)"> (none)</option>
                  {filter.regions}
                </select>
                Country:
                <select id="countrySelect" onChange={this.addCountryFilter} style={{width:'211px'}}>
                  <option value="(none)"> (none)</option>
                  {filter.countries}
                </select>
                Continent:
                <select id="continentSelect" onChange={this.addContinentFilter} style={{width:'211px'}}>
                  <option value="(none)"> (none)</option>
                  {filter.continents}
                </select>
              </span>
              <span>
               <button className="btn btn-outline-dark btn-success" onClick={this.addHeldFilters} > Apply Filters </button>
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
