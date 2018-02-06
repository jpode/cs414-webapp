import React, {Component} from 'react';

/* Renders a text footer below the application with copyright
 * and other useful information.
 */
class Footer extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
        <div id="footer" className="jumbotron">
          <h4>© TripCo t{this.props.number} {this.props.name} 2018</h4>
        </div>
    )
  }
}

export default Footer;