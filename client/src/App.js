import React, {Component} from 'react';
import Header from './Header';
import Application from './Application';
import Footer from './Footer';

class App extends Component {
  constructor (props){
    super(props);
    this.state = {
      config: {
        number: "09",
        name: "OVER NINE THOUSAND!?",
        version: 0,
        maps : [],
        optimization: 0,
        optimizations: [],
        distances: [],
        filters: []
      },
      host: location.host
    };

    this.config = this.config.bind(this);
    this.updateHost = this.updateHost.bind(this);

    console.log(this.state.host);
    console.log("fetching config from server");
    this.config();
    console.log("request ending");

  }

  updateHost(newHost){
    console.log("Updating host to " + this.state.host);
    this.setState({host: newHost}, () => {this.config()});
  }

  updateFromConfig(config){
    let newConfig = this.state.config;

    for(let key in config){
      if(config.hasOwnProperty(key)){
        if(typeof config[key] !== "undefined" && config[key] !== 0 && config[key] !== ""){
          newConfig[key] = config[key];
        }
      }
    }

    this.setState({config:newConfig});
  }

  fetchConfig(){
    return fetch('http://' + this.state.host + '/config', {
      header: {'Access-Control-Allow-Origin':'*'},
      method:"GET"
    });
  }

  async config(){
    try {
      console.log("fetching from "+ this.state.host + "...");
      let serverResponse = await this.fetchConfig();
      let config = await serverResponse.json();

      console.log("response recieved: ");
      console.log(config);
      this.updateFromConfig(config);

    } catch(err) {
      console.error(err);
    }
  }

  render() {
    const active = this.state.config.version > 0;
    return(
          <div id="tripco">

            <Header number={this.state.config.number} name={this.state.config.name}/>
            {active &&
              <Application config={this.state.config} host={this.state.host} updateHost={this.updateHost}/>
            }
            <Footer number={this.state.config.number} name={this.state.config.name}/>
          </div>
    );
  }
}

export default App;
