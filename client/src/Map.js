import React, {Component} from 'react'
import {compose, withProps} from 'recompose'
import {withScriptjs, withGoogleMap,
  GoogleMap, Polyline, Marker} from 'react-google-maps'
/* Map obtains and renders the map for the trip.
 * Might be an SVG or KML contained in the server response.
 */
class Map extends Component {
  constructor(props){
    super(props);
  }

  render() {
   {
      let svgHeader='data:image/svg+xml;charset=UTF-8,';
      let svgData = this.props.trip.map;

      return (
          <figure className="figure" id="map">
            <img className="figure-img img-fluid" alt="Map"
                 src={svgHeader.concat(svgData)}/>
          </figure>
      )
    }
  }
}

export default Map;
