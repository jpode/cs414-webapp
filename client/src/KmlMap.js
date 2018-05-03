import React, {Component} from 'react'
import {compose, withProps} from 'recompose'
import {withScriptjs, withGoogleMap,
  GoogleMap, Polyline, Marker} from 'react-google-maps'

class KmlMap extends React.Component {
  constructor(props) {
    super(props);
  }

  // react-google-maps expects all of it's data in the form {lat: x, lng: y}
  // more information can be found at https://tomchentw.github.io/react-google-maps/#introduction
  // IMPORTANT: since our trip.places array contains strings, we'll need to convert them somewhere in this
  //  class. I'll leave that to you (it only works since I've hard coded lat/lng in places as Numbers)

  // Create our path from the places array
  makePath(places) {
    try {
      let path = places.map(
          x => ({lat: parseFloat(x.latitude), lng: parseFloat(x.longitude)}));
      path.push({
        lat: parseFloat(places[0].latitude),
        lng: parseFloat(places[0].longitude)
      });
      return path;
    }catch(err) {
      console.log("no kml defined yet...")
    }
  }

  // Create our markers
  makeMarkers(places) {
    let markers = places.map(
        x => <Marker position={{lat: parseFloat(x.latitude), lng: parseFloat(x.longitude)}}/>);
    return markers;
  }

  render() {
    return (
        <GoogleMap
            defaultCenter={{lat: 0, lng: 0}}
            defaultZoom={1}>
          <Polyline path={this.makePath(this.props.trip.places)}
                    options={{strokeColor: 'DeepSkyBlue'}}/>
          {this.makeMarkers(this.props.trip.places)}
        </GoogleMap>
    );
  }
}

// This uses a complicated feature of React called Higher Order Components, the recompose library
//  makes this more succinct. HOC's can be thought of as wrappers for functions.
// IMPORTANT: lastly to use this file, I urge you to create an API key and paste it here(1)
//  Visit https://developers.google.com/maps/documentation/javascript/get-api-key and press Get A Key.
// Notice this (2) is the name of the above component.
const TripMap = compose(
    withProps({
      googleMapURL: 'https://maps.googleapis.com/maps/api/js?' +
      'key=AIzaSyA74zEKh7jhLOV-WsminxVB95M9iMnWtf8' + // (1) should be key=<your API key>
      '&v=3.exp' +
      '&libraries=geometry,drawing,places',
      loadingElement: <div />,
      containerElement: <div/>,
      mapElement: <div style={{ height: `30%` }} />
    }),
    withScriptjs,
    withGoogleMap,
)(KmlMap);

export default TripMap;