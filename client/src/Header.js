import React, {Component} from 'react';

/* Renders a text heading above the application with useful information.
 */
class Header extends Component{
  constructor(props) {
    super(props);
  }

  render() {
    return(
        <div id="header" className="jumbotron">
          <img src="https://raw.githubusercontent.com/csu314sp18/tripco/setup/sprint3/CompSci-NS-CSU-1-Hrev.png?token=Abgsf3eBaZyE1_KYpWDtuhuwefmoXOGVks5aoHl8wA%3D%3D" className="img-fluid" alt="CSU CS Dept."/>
          {this.title()}
          <p className="lead text-white">"Want to travel far and wide?"</p>
            <ol >
              <li className="text-white">
                Choose options for trip planning, information to display about locations,
                and how the trip map and itinerary should be saved.</li>
              <li className="text-white">
                Choose your destinations by loading existing sets of destinations or
                find more in an extensive database of locations worldwide.</li>
              <li className="text-white">
                Plan the trip with the options you selected.
                Review and revise the trip origin and order.
                Save the trip map and itinerary for future reference.</li>
            </ol>
        </div>
    )
  }

  title() {
    return( <h3 className="text-white">TripCo <small>t{this.props.number} {this.props.name}</small></h3> )
  }
}

export default Header;
