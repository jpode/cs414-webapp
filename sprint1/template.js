class SinglePairOfLocations extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      /* if any other global variables are needed, add here */
      dist: "",
      /* these coord values will be in floating point form, with correct polarity (negative / positive) */
      A_Raw_Input: "",
      B_Raw_Input: "",
      A_Coord_NS: "",
      A_Coord_EW: "",
      B_Coord_NS: "",
      B_Coord_EW: ""
    };

    /* NOTE::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
 WILL NEED TO BIND ALL FUNCTIONS (BY NAME) HERE FOLLOWING FORMAT */
    this.handleSubmit = this.handleSubmit.bind(this);
    this.updateA = this.updateA.bind(this);
    this.updateB = this.updateB.bind(this);
  }

  /* updates A's coord (as string) value */
  updateA(event) {
    this.setState({ A_Raw_Input: event.target.value });
  }

  /* updates B's coord (as string) value */
  updateB(event) {
    this.setState({ B_Raw_Input: event.target.value });
  }

  handleSubmit(event) {
    event.preventDefault();
    /*parsing A_Raw_Input */
    var tempArray = splitInput(this.state.A_Raw_Input);
    var temp_A_NS = processInput(tempArray[0]);
    var temp_A_EW = processInput(tempArray[1]);
    this.setState({ A_Coord_NS: temp_A_NS });
    this.setState({ A_Coord_EW: temp_A_EW });
    /*parsing B_Raw_Input */
    var tempArray = splitInput(this.state.B_Raw_Input);
    var temp_B_NS = processInput(tempArray[0]);
    var temp_B_EW = processInput(tempArray[1]);
    this.setState({ B_Coord_NS: temp_B_NS });
    this.setState({ B_Coord_EW: temp_B_EW });
    /*finding actual distance: */
    /* note: tempDist is called with temp variables, instead of "state" variables: this is because
    of the way React does not immediately update state (i.e. is not asynchronous ) */
    var tempDist = get_distance(
        temp_A_NS,
        temp_A_EW,
        temp_B_NS,
        temp_B_EW,
        "M"
      )
    this.setState({dist: tempDist}); 
  }

  /* basic forms for entry (formerly seperate UI class) */
  render() {
    return (
        <form className="form-inline" onSubmit={this.handleSubmit}>
        <input
          type="text"
          className="text-left form-control mr-sm-2"
          placeholder="Location 1"
          value={this.state.A_Raw_Input}
          onChange={this.updateA}
        />
        
        <input
          type="text"
          className="text-left form-control mr-sm-2"
          placeholder="Location 2"
          value={this.state.B_Raw_Input}
          onChange={this.updateB}
        />
         <button
          className="btn btn-primary mr-sm-2"
          type="submit"
          value="submit"
        >
          =
        </button>
        <input
          type="text"
          className="text-left form-control mr-sm-2"
          placeholder="Distance"
          value={this.state.dist}
          disabled
        />
      </form>
    );
  }
}

/* SplitInput(props) takes raw coordinate input, removes degree, minute, and 
second notation, seperates by latitude and longitude, and returns array containing raw data */
/* NEED to add error checking functionality */
/* NEED to add if no N/S or E/W are present (values already in floating point form): */
function splitInput(value) {
  var coords = value.trim().split(" ");
  var stringTemp = "";
  var tempArray = [];
  for (var i = 0; i < coords.length; i++) {
    coords[i] = coords[i].replace("°", "");
    coords[i] = coords[i].replace("'", "");
    coords[i] = coords[i].replace('"', "");
    stringTemp = stringTemp + coords[i] + " ";
  }
  /*now to split string by longitude / latitude: to do this, searched for index of either N or S, then substring based on that index +1 */
  var pos = stringTemp.indexOf("N") + 1;
  /* note: pos == 0 is same as pos == -1 in most cases (not found), because we add 1 to pos in line above. */
  if (pos == 0) {
    pos = stringTemp.indexOf("S") + 1;
  }
  if (pos == 0) {
    pos = stringTemp.indexOf("n") + 1;
  }
  if (pos == 0) {
    pos = stringTemp.indexOf("s") + 1;
  }
  
  /* checks for if input is say, "-87.222 92.101" without N/S or E/W */
  if (pos == -1) {
    tempArray = stringTemp.split(" ");
  } else {
    tempArray[0] = stringTemp.slice(0, pos);
    tempArray[1] = stringTemp.slice(pos, stringTemp.length);
  }
  return tempArray;
}

/* function for converting pre-processed coords. to floating point values */
/* need to add error checking functionality */
function processInput(value) {
  var coords = value.trim().split(" ");
  var finalValue = 0.0;
  var minutes = 0.0;
  var seconds = 0.0;
  var negate = false;

  for (var i = 0; i < coords.length; i++) {
    var temp = coords[i];
    if (temp === "N" || temp === "n" || temp === "E" || temp === "e") {
      break;
    } else if (temp === "S" || temp === "s" || temp === "W" || temp === "w") {
      negate = true;
      break;
    }
    /* if not at end of coordinates (designated by one of NSEW), first will be degrees, second minutes, last seconds */
    if (i == 0) {
      finalValue += Number(coords[i]);
    } else if (i == 1) {
      minutes += Number(coords[i]);
    } else {
      seconds += Number(coords[i]);
    }
  }
  /* final calculation to floating point number: */
  seconds += 60 * minutes;
  seconds /= 3600;
  finalValue += seconds;
  if (negate) {
    finalValue *= -1;
  }
  return finalValue;
}

function get_distance(phi_1, lambda_1, phi_2, lambda_2, dist_type) {
  var delta_x =
    Math.cos(phi_2) * Math.cos(lambda_2) - Math.cos(phi_1) * Math.cos(lambda_1);
  var delta_y =
    Math.cos(phi_2) * Math.sin(lambda_2) - Math.cos(phi_1) * Math.sin(lambda_1);
  var delta_z = Math.sin(phi_2) - Math.sin(phi_1);
  var chord_length = Math.sqrt(
    Math.pow(delta_x, 2) + Math.pow(delta_y, 2) + Math.pow(delta_z, 2)
  );
  var cent_angle = 2 * Math.asin(chord_length / 2);
  if (dist_type == "K") {
    return 6371.0088 * cent_angle;
  } else {
    return 3958.7613 * cent_angle;
  }
}

/* Main is the "main" driver class for this app */
class Main extends React.Component {
  render() {
    return (
      <div className="jumbotron">
        <h3> TripCo Distance Calculator </h3>
        <hr />
        <SinglePairOfLocations />
        {/* HERE IS WHERE VARIABLES SHOULD BE UPDATED ON EVENT CALL, ETC*/}
        {/*CALL TO CALCULATION: CURRENT PARAMETERS ARE PLACEHOLDERS*/}
        {/* get_distance(0,0,0,0,M); */}
      </div>
    );
  }
}

ReactDOM.render(<Main />, document.getElementById("Calculator"));

