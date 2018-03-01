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
        <div id="footer" className="jumbotron container-fluid">
                <div className="row">
                    <div className="col-8 align-self-center">
                        <h4 className="text-white">© TripCo t{this.props.number} {this.props.name} 2018</h4>
                    </div>
                    <div className="col-4">
                        <img src="https://raw.githubusercontent.com/csu314sp18/tripco/setup/sprint3/CSU-Official-wrdmrk-357-617_Rev.png?token=Abgsf1PkOTR0A9RK8uV-N1wwo6z-d3Qrks5aoHjawA%3D%3D" className="img-fluid" alt="CSU"/>
                    </div>
                </div>
        </div>
    )
  }
}

export default Footer;