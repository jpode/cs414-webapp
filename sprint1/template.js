class SinglePairOfLocations extends React.Component{
  constructor(props){
    super(props)
    this.state = {
      /* if any other global variables are needed, add here */
      dist: "",
      /* these coord values will be in floating point form, with correct polarity (negative / positive) */
      A_Coord_NS: "",
      A_Coord_EW: "",
      B_Coord_NS: "",
      B_Coord_EW: ""
    };
 
/* NOTE::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
 WILL NEED TO BIND ALL FUNCTIONS (BY NAME) HERE FOLLOWING FORMAT */
    this.processInput = this.processInput.bind(this);
    this.splitInput = this.splitInput.bind(this);
    this.updateA = this.updateA.bind(this);
    this.updateB = this.updateB.bind(this);
  }
 
 /* updates A's coord (as string) value */
  updateA(event) {
    var tempArray = splitInput(event.target.value); 
    this.setState({A_Coord_NW : processInput(tempArray[0])})
    this.setState({A_Coord_EW : ProcessInput(tempArray[1])})
  }

/* updates B's coord (as string) value */
  updateB(event) {
    var tempArray = splitInput(event.target.value);
    this.setState({B_Coord_NW : processInput(tempArray[0])})
    this.setState({B_Coord_EW : processInput(tempArray[1])})
  } 
}

/* SplitInput(props) takes raw coordinate input, removes degree, minute, and 
second notation, seperates by latitude and longitude, and returns array containing raw data */
/* need to add error checking functionality */
function splitInput(props){
  var coords = props.target.value.split(" ");
  var toReturn = [];
  var stringTemp = "";
  for(var i = 0; i < coords.length; i++){
    coords[i] = coords[i].replace("°", "");
    coords[i] = coords[i].replace("'", "");
    coords[i] = coords[i].replace("\"", "");  
    stringTemp = stringTemp + coords[i] + " ";
  } 
  /*now to split string by longitude / latitude: to do this, searched for index of either N or S, then substring based on that index +1 */
  var pos = stringTemp.indexOf("N") + 1;
  if(pos == -1){
    pos = stringTemp.indexOf("S") + 1;
  }
  toReturn[0] = stringTemp.slice(0, pos);
  toReturn[1] = stringTemp.slice(pos, stringTemp.length);
 
  return toReturn;
}

/* function for converting pre-processed coords. to floating point values */
/* need to add error checking functionality */
function processInput(props){
  var coords = props.target.value.split(" ");
  var finalValue = 0.0;
  var minutes = 0.0;
  var seconds = 0.0;
  var negate = false;
  
  for (var i = 0; i < coords.length; i++){
    var temp = coords[i];
    if(temp === "N" || temp === "n"){
      break;
    }
    else if (temp === "S" || temp === "s"){
      negate = true;
      break;
    }
    else if(temp ==="E" || temp === "e"){
      break;
    }
    else if(temp === "W" || temp === "w"){
      negate = true;
      break;
    }
    /* if not at end of coordinates (designated by one of NSEW), first will be degrees, second minutes, last seconds */
    if(i == 0){
      finalValue += Number(coords[i]);
    }
    else if (i == 1){
      minutes += Number(coords[i]);
    }
    else{
      seconds += Number(coords[i]);
    }
  }
  /* final calculation to floating point number: */
  seconds += 60 * minutes;
  seconds /= 3600;
  finalValue += seconds;
  if(!negate){
    finalValue *= (-1);
  }
  return finalValue;
}

function get_distance(phi_1, lambda_1, phi_2, lambda_2, dist_type){
  var delta_x = Math.cos(phi_2) * Math.cos(lambda_2) - Math.cos(phi_1) * Math.cos(lambda_1);
  var delta_y = Math.cos(phi_2) * Math.sin(lambda_2) - Math.cos(phi_1) * Math.sin(lambda_1); 
  var delta_z = Math.sin(phi_2) - Math.sin(phi_1);
  var chord_length = Math.sqrt(Math.pow(delta_x, 2) + Math.pow(delta_y, 2) + Math.pow(delta_z, 2));
  var cent_angle = 2 * Math.asin(chord_length / 2);
    
  if(dist_type == "K"){
    return 6371.0088 * cent_angle;
  } else {
    return 3958.7613 * cent_angle;
  }
}

class UI extends React.Component{
  render() {
    return (
    <form className="form-inline" onSubmit={this.get_distance}>
        
        <input type="text" className="text-right form-control mr-sm-2" 
          value={this.state.operand1} onChange={this.updateOperand1}/> 

        <button className="btn btn-secondary mr-sm-2" disabled>+</button>

        <input type="text" className="text-right form-control mr-sm-2" 
          value={this.state.operand2} onChange={this.updateOperand2}/> 

        <button className="btn btn-primary mr-sm-2" type="submit" value="submit" 
          disabled>=</button>

        <input type="text" className="text-right form-control mr-sm-2" 
          value={this.state.sum} disabled/>
      </form>
    );
  }
}

/* Main is the "main" driver class for this app */
class Main extends React.Component{
  render(){
    return(
      <div className="jumbotron">
        <h3> Distance Calculator </h3>
      <hr/>
        <UI />
        {/* HERE IS WHERE VARIABLES SHOULD BE UPDATED ON EVENT CALL, ETC*/}
          {/*CALL TO CALCULATION: CURRENT PARAMETERS ARE PLACEHOLDERS*/}
		  get_distance(0,0,0,0,M);
      </div>
    )
  }
}

ReactDOM.render(<Main />, document.getElementById("Calculator"));
