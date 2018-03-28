import React, {Component} from 'react';

/* Renders a text heading above the application with useful information.
 */
class Header extends Component{
  constructor(props) {
    super(props);
  }

  render() {
    return(
        <div id="header" className="jumbotron container-fluid bg-success">
          <div className="row">
            <div className="col-md-6">
              <a href="https://www.cs.colostate.edu/cstop/index">
              <img src="http://www.cs.colostate.edu/~cs314/images/CompSci-NS-CSU-1-Hrev.png" className="img-fluid" alt="CSU CS Dept."/>
              </a>
            </div>
          </div>
        </div>
    )
  }

  title() {
    return( <h3 className="text-white">TripCo <small>t{this.props.number} {this.props.name}</small></h3> )
  }
}

export default Header;
