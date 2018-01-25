class Header extends React.Component{
  render() {
    return (
      <div>
        <h2>Jake Marrapode</h2>
        <p className="subtitle"> 
        (630)251-3417<br />
        jake.marrapode@gmail.com <br />
        <a href="https://github.com/jpode">https://github.com/jpode</a>
        </p>
      </div>
    )
  }
}


class Body extends React.Component{
  render(){
    return(
      <div>
        <h2> Education </h2>
        <div className="row">
          
          <div className="col-xs-6 col-sm-6 col-md-4 col-lg-4 col-xl-4">
            <div className="card-body">
              <h4 className="card-title"> Colorado State University </h4>
              <h6 className="card-subtitle mb-2 text-muted">Fort Collins, Colorado</h6>
              <h6 className="card-subtitle mb-2 text-muted">BS in Computer Science, expected graduation May 2019</h6>
              
              <p className="card-text">
                <ul>
                  <li>GPA: 3.23 </li>
                  <li>Honors and Awards: Academic Recognition Award </li>
                  <li>Relevant Completed Courses</li>
                    <ul>
                      <li>Software Development With C++</li>
                      <li>Operating Systems </li>
                      <li>Computer Organization </li>
                      <li>Object-Oriented Problem Solving </li>
                    </ul>
                </ul>
              </p>
                    
            </div>
          </div>
          
          <div className="col-xs-6 col-sm-6 col-md-4 col-lg-4 col-xl-4">
            <div className="card-body">
              <h4 className="card-title"> University of Otago </h4>
              <h6 className="card-subtitle mb-2 text-muted">Otago, New Zealand</h6>
              <h6 className="card-subtitle mb-2 text-muted">Study Abroad Fall 2017</h6>
              
              <p className="card-text">
                <ul>
                  <li>Relevant Completed Courses</li>
                    <ul>
                      <li>Algorithms and Data structures</li>
                    </ul>
                </ul>
              </p>
                    
            </div>
          </div>
        
          <div className="col-xs-6 col-sm-6 col-md-4 col-lg-4 col-xl-4">
            <div className="card-body">
              <h4 className="card-title"> Neuqua Valley High School </h4>
              <h6 className="card-subtitle mb-2 text-muted">Naperville, Illinois</h6>
              <h6 className="card-subtitle mb-2 text-muted">Graduated May 2015</h6>
              <p className="card-text"> 
                <ul>
                  <li>GPA: 3.14</li>
                  <li>ACT: 32/36</li>
                  <li>Honors and Awards: Indian Prarie State Scholar</li>
                </ul>
              </p>
            </div>
          </div>
          
        </div>
        
        <h2> Work Experience </h2>
        <div className="row">

          <div className="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xl-4">
           <div className="card-body">
              <h4 className="card-title"> Stealth Machines</h4>
              <h6 className="card-subtitle mb-2 text-muted">Loveland, Colorado</h6>
              <h6 className="card-subtitle mb-2 text-muted">Custom Gaming Computers</h6>
              <h6 className="card-subtitle mb-2 text-muted">August 2016 - January 2017</h6>
              <p className="card-text">
                <ul>
                  <li>Communicate with customers about computer needs and plan customized builds</li>
                  <li>Assemble, test, and ship custom builds</li>
                </ul>
              </p>
           </div>
          </div>
        
          <div className="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xl-4">
            <div className="card-body">
              <h4 className="card-title"> Neuqua Valley Ultimate Club</h4>
              <h6 className="card-subtitle mb-2 text-muted">Naperville, IL </h6>
              <h6 className="card-subtitle mb-2 text-muted">Youth Coaching Staff </h6>
              <h6 className="card-subtitle mb-2 text-muted">2012-2015</h6>
              <p className="card-text">
                <ul>
                  <li>Work with a partner or teams of coaches to teach fundamentals of teamwork and physicals skills to elementary, middle, and high school students.</li>
                </ul>
              </p>
            </div>
          </div>
          
        </div>
        
        <h2> Activities and Projects</h2>
        <div className="row">

          <div className="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xl-4">
            <div className="card-body">
              <h4 className="card-title"> Clubs</h4>
              <p className="card-text">
                Neuqua Valley Ultimate Club: 2011-2015 (Captain)
                <br />
                Colorado State Men's Ultimate: 2015-Current
              </p>
            </div>
          </div>

          <div className="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xl-4">
            <div className="card-body">
              <h4 className="card-title"> Projects</h4>
              <p className="card-text">
                Raspberry Pi Smart Mirror
                <ul>
                  <li>Worked with a partner to create a Pi based mirror capable of displaying current date, time, and weather information as well as facial recognition for personalized settings</li>
                </ul>
              </p>
            </div>
          </div>

        </div>
        
      </div>
      
    )
  }
}

class Footer extends React.Component{
  render(){
    return(
      <div>
        <h4> Additional Skills </h4>
        <div className="row">

          <div className="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xl-4">
            <div className="card-body">
              <h5 className="card-title"> Technical Proficiences</h5>
              <p className="card-text">
                <ul>
                  <li>Windows and Linux OS</li>
                  <li>Java, Python, C, C++, Basic HTML and Javascript</li>
                  <li>Program logic and verification/debugging</li>
                </ul>
              </p>
            </div> 
          </div>

          <div className="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xl-4">
            <div className="card-body">
              <h5 className="card-title"> Personal Skills</h5>
              <p className="card-text">
                <ul>
                  <li>Teamwork and adaptability in groups</li>
                  <li>Time management</li>
                  <li>Work timeline/strategic planning</li>
                  <li>Ability to work under pressure</li>
                </ul>
              </p>
            </div> 
          </div>

        </div>
        
      </div>
    )
  }
}

class Main extends React.Component {
  render() {
    return (
      <div>
        <div className="jumbotron">
          <Header />
        </div>
        
        <div className="container">
          <Body />
        </div>
        
        <div className="jumbotron">
          <Footer />
        </div>
      </div>
    );
  }
}

ReactDOM.render(<Main />, document.getElementById("resume"));