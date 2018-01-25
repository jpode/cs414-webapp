/* Summary and Introduction */
/* NOTE: this section includes a link to https://www.github.com/dboston1 */
class Header extends React.Component {
  render(){
    return (
    <div>
        <h1> Drew Boston </h1>
        <p className="lead"> Hello! My name is Drew Boston, and I write computer programs. I'm currently a student at Colorado State University. I am fascinated by machine learning and artificial intelligence, and will likely pursue a career involving either (or both) of these
        fields. Currently, I am able to explore both through my work with PARRIC, as detailed below under experience. </p>
      <p className="lead">If you would like to see some of my projects, simply <a href="https://github.com/dboston1">click here</a> to be redirected to my Github page. </p>
   </div>
   )
  } 
}

/* Education and Relevant Coursework (body, row 1) */
class EducationAndRelevantCoursework extends React.Component{
  render(){
    return(
      <div className="row">
        
        {/* Education: */}
        <div className="col-xs-12 col-sm-12 col-md-5 col-lg-5 col-xl-5">
        <h2>Education</h2>
        <strong>BS, Colorado State University - Fort Collins, CO </strong>
        <ul>
          <li> Major: Computer Science, Expected 2018 </li>
          <li> Minor: Mathematics, Expected 2018 </li>
          <li> Cumulative GPA: 3.76 </li>
        </ul>
      </div>
        
        {/* Relevant Coursework: */}
        <div className="col-xs-12 col-sm-12 col-md-7 col-lg-7 col-xl-7">
        <h2>Relevant Coursework </h2>
        <h5> Computer Science: </h5>
        <p> Operating Systems, Big Data, Software Engineering, Algorithms: Theory and Practice, Computer Organization, and System Security </p>
        <h5> Mathematics: </h5>
        <p> Linear Algebra, Abstract Algebra, Math of Information Security, Advanced Calculus of a Single Variable, Calculus 2, and Calculus 1. </p>
      </div>
    </div>
    )
  }
}    

/* Work Experience and Languages/Libraries (body, row 2): */
class ExperienceAndLanguages extends React.Component {
  render(){
    return(
        <div className="row">
        
        {/* Work Experience: */}
        <div className="col-xs-12 col-sm-12 col-md-9 col-lg-9 col-xl-9">
         <h2>Work Experience</h2>
         Colorado State University - Spring 2018 through Current 
        <h6> PARRIC Research Assistant </h6>
        <p> Assist with the ongoing project of providing a 100-million-fold speedup of the existing piRNA software which concerns both cancer research as well as communication-avoidance algorithms. This will be accomplished through hardware acceleration,
          clustering, parallelizing, and GPU accelerators. </p>
         Colorado State University - Spring 2017 through Current 
        <h6> Math Department Learning Assistant </h6>
        <p> Filled various semester-long roles as needed by CSU's Mathematics department, including: assisting Dr. Mary Pilgrim with her Calculus 2 class, providing general feedback, structuring assignments for students, tutoring for Calculus 1 and 2 in CSU's
          Calculus Center, as well as acting as a lab proctor. </p>
         Gibbons-White, Inc. - Spring 2015 through Fall 2016 
        <h6> Head of Marketing, Assistant Property Manager </h6>
        <p> Marketed properties for sale and lease, performed lease audits and property inspections, designed and provided property tours to prospective clients, and responded to client inquiries. Assisted with ensuring optimal building conditions and maintaining
          property and client databases. </p>
      </div>
    
      {/* Languages and Libraries: */}
      <div className="col-xs-12 col-sm-12 col-md-3 col-lg-3 col-xl-3">
        <h2>Languages and Libraries</h2>
        <ul>
          <li> C </li>
          <li> C++ </li>
          <li> Java </li>
          <li> Python </li>
          <li> Javascript </li>
          <li> HTML </li>
          <li> Bash / Linux </li>
          <li> Caffe </li>
          <li> OpenCV </li>
          <li> Numpy </li>
          <li> Matlab </li>
        </ul>
      </div>
      </div>
    )
  }
}

/* footer (name, email address) */
class Footer extends React.Component {
  render(){
    return(
    <div ClassName="row">
        <hr></hr>
        Drew Boston
        <p>Drew.Boston@rams.colostate.edu </p>
      </div>
    )
  }
}


class Main extends React.Component {
  render(){
    return(
    <div>
      <div className="jumbotron">
        <Header />
        </div>
     <div className="container">
        <EducationAndRelevantCoursework />
        <ExperienceAndLanguages /> 
        <Footer />
        </div>
        </div>
    )
  }
          
}   
        
ReactDOM.render(<Main />, document.getElementById("app"));
