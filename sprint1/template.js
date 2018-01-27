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

/* Main is the "main" driver class for this app */
class Main extends React.Component{
  render(){
    return(
      <div>
      <div className="container">
      {/* HERE IS WHERE CALL TO STYLE CLASSES GOES*/}
    
        <SinglePairOfLocations />
        {/* HERE IS WHERE VARIABLES SHOULD BE UPDATED ON EVENT CALL, ETC*/}
          {/* HERE IS WHERE CALL TO CALCULATION GOES*/}
      </div>
      </div>
    )
  }
}

ReactDOM.render(<Main />, document.getElementById("Calculator"));
