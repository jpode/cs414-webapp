import React, {Component} from 'react';
import { Modal } from 'react-bootstrap'; // npm install react-bootstrap

/* Renders a text footer below the application with copyright
 * and other useful information.
 */
class Footer extends Component {
  constructor(props) {
    super(props);

    this.handleShow = this.handleShow.bind(this);
    this.handleClose = this.handleClose.bind(this);

    this.state = {
        show: false
    };
  }

  handleClose() {
      this.setState({ show: false });
  }

  handleShow() {
      this.setState({ show: true });
  }

  render() {
    return (
        <div id="footer" className="jumbotron container-fluid bg-success">
                <div className="row">
                    <div className="col-md-4 align-self-center">
                        <h4 className="text-white">© 2018 TripCo t{this.props.number} Over9000</h4>
                    </div>
                    <div className="col-md-4">
                    </div>
                    <div className="col-md-4">
                        <a href="http://colostate.edu">
                        <img src="http://www.cs.colostate.edu/~cs314/images/CSU-Official-wrdmrk-357-617_Rev.png" className="img-fluid" alt="CSU"/>
                        </a>
                    </div>
                </div>
        </div>
    )
  }
}

export default Footer;