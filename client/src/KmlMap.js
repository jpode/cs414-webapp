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

  convertCords(places) {
    let newPlaces = places.map(
        
    );
  }

  // Create our path from the places array
  makePath(places) {
    let path = places.map(
        x => ({lat: x.latitude, lng: x.longitude}));
    path.push({lat: places[0].latitude, lng: places[0].longitude});
    return path;
  }

  // Create our markers
  makeMarkers(places) {
    let markers = places.map(
        x => <Marker position={{lat: x.latitude, lng: x.longitude}}/>);
    return markers;
  }

  render() {
    const places = this.convertCords(props.trip.places);
    return (
        <GoogleMap
            defaultCenter={{lat: 0, lng: 0}}
            defaultZoom={1}>
          <Polyline path={this.makePath(places)}
                    options={{strokeColor: 'DeepSkyBlue'}}/>
          {this.makeMarkers(places)}
        </GoogleMap>
    );
  }
}

// This uses a complicated feature of React called Higher Order Components, the recompose library
//  makes this more succinct. HOC's can be thought of as wrappers for functions.
// More information on HOC's can be found https://reactjs.org/docs/higher-order-components.html
// Information on recompose can be found https://github.com/acdlite/recompose
// IMPORTANT: lastly to use this file, I urge you to create an API key and paste it here(1)
//  Visit https://developers.google.com/maps/documentation/javascript/get-api-key and press Get A Key.
// Notice this (2) is the name of the above component.
const TripMap = compose(
    withProps({
      googleMapURL: 'https://maps.googleapis.com/maps/api/js?' +
      'key=' + // (1) should be key=<your API key>
      '&v=3.exp' +
      '&libraries=geometry,drawing,places',
      loadingElement: <div />,
      containerElement: <div/>,
      mapElement: <div style={{ height: `30%` }} />
    }),
    withScriptjs,
    withGoogleMap,
)(KmlMap);  // (2)

export default TripMap;