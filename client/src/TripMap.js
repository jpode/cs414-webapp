import React, {Component} from 'react'
import {compose, withProps} from 'recompose'
import {withScriptjs, withGoogleMap, GoogleMap, Polyline, Marker} from 'react-google-maps'

class InnerMap extends React.Component {
    constructor(props) {
        super(props);
    }

    // Create our path from the places array
    makePath(places) {
        let path = places.map(
            x => ({lat: x.latitude, lng: x.longitude})
        );
        path.push({lat: places[0].latitude, lng: places[0].longitude});
        return path;
    }

    // Create our markers
    makeMarkers(places) {
        let markers = places.map(
            x => <Marker position={{lat: x.latitude, lng: x.longitude}}/>
        );
        return markers;
    }

    render() {
        const places = this.props.trip.places;
        return (
            <GoogleMap
                defaultCenter={{lat: 0, lng: 0}}
                defaultZoom={1}
            >
                <Polyline path={this.makePath(places)}
                          options={{strokeColor: 'DeepSkyBlue'}}
                />
                {this.makeMarkers(places)}
            </GoogleMap>
        );
    }
}

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
)(InnerMap);  // (2)

export default TripMap;