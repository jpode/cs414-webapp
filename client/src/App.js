import React, {Component} from 'react';
import Header from './Header';
import Application from './Application';
import Footer from './Footer';

class App extends Component {
  constructor (props){
    super(props);
    this.state = {
      number: "09",
      name: "OVER NINE THOUSAND!?",
      version: "",
      distances: "",
      optimization: ""
    }

    this.config = this.config.bind(this);

    console.log("fetching config from server");
    this.config();
    console.log("request ending");
  }

  updateFromConfig(config){
    this.setState({version: config.version, distances : config.distances, optimization : config.optimization});
  }

  fetchConfig(){
    return fetch('http://' + location.host + '/config', {
      method:"GET",
    });
  }

  async config(){
    try {
      console.log("fetching...");
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
    const active = this.state.version != "";
    return(
          <div id="tripco">

            <Header number={this.state.number} name={this.state.name}/>
            {active &&
              <Application version={this.state.version} distances={this.state.distances} optimization={this.state.optimization}/>
            }
            <Footer number={this.state.number} name={this.state.name}/>
          </div>
    );
  }
}

export default App;
