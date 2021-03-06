class Header extends React.Component {
  render() {
    return (
      <div>
       <div className="container">
       <h1> Jake Servaty </h1>
        <p>(605) 569-0241 | <a href="mailto:jjserva@rams.colostate.edu">jjserva@rams.colostate.edu</a> | <a href="mailto:jjservaty@gmail.com">jjservaty@gmail.com</a></p>
        
        <div className="row">
            <div className="col-md-4">
              <h7><b>Present Address</b></h7>
              <p>1316 Constitution Ave <br /> Fort Collins, CO 80521</p>
            </div>
            <div className="col-md-4">
            </div>
            <div className="col-md-4">
              <h7><b>Permanent Address</b></h7>
              <p>2 Merry Lane <br /> Spearfish, SD 57783</p>
            </div>
          </div> 
        </div>
      </div>
    )
  }
}

class Body extends React.Component {
  render() {
    return (
      <div>
      <div className="row">
          <div className="col">
            <ul><h2>Education</h2>
              <li>2014: High School Diploma - Honors, <b>Spearfish High School</b></li>
              <li>2014-2018: <b>Colorado State University</b>, Computer Science Major</li>
            </ul>
          </div>
          <div className="col">
            <ul><h2>Skills</h2>
              <li>Effective and Clear <b>Communicator</b></li>
              <li><b>Problem Solver</b> with Outside-the-Box thinking</li>
              <li>Self-Motivated <b>Hardworker</b>, won't rest until problem is solved</li>
              <li>Cooperative, Accountable, and Dependable <b>Teammate</b></li>
              <li><b>Curious</b>, and Hungry for Knowledge</li>
              <li><b>Optimistic</b> with Positive Attitude</li>
            </ul>
          </div>
        </div>
      
      <div className="row">
            <div className="col">
              <ul><h2>Industry Experience</h2>
                <li><h5><b>SendTree</b> - Lafayette, Indiana - (August 2016-October 2017)</h5> 
                  <p><b>Junior Software Developer:</b> Working remotely as a "handyman" accomplishing small projects that fix bugs, enhance the software's performance, cleanup existing code, improve the architecture of the system, and assists with other miscellaneous assignments.<br /><br /> ---------<b>Tools:</b> <b>Java</b> (Object-Oriented Programming), Eclipse/IDE, Web Development/Design (<b>HTML, CSS, JavaScript basics</b>), Social Media (<a href="https://twitter.com/JakerGreen64">Twitter</a>, <a href="https://www.instagram.com/jakeservaty/">Instagram</a>, <a href="https://www.facebook.com/JakerGreen64">Facebook</a>), MailChimp/Mandrill, Elasticsearch, Twilio DB/API, <b><a href="https://github.com/SoDakHibida">GitHub</a>/Git</b>, Apache Tomcat, Windows/Powershell basics, Apache Ant, <b>Linux/Unix/bash scripting</b>.</p>
                </li>
                <br />
                <li><h5><b>Colorado State University Student Media Corp</b> - Fort Collins, Colorado - (August 2015-October 2016)</h5>
                  <p><b>Digital Ads Specialist:</b> Monitoring, uploading, and editing online digital advertisements, writing reports. <br /><br />
                    ---------<b>Tools:</b> <b>Microsoft Office</b> Tools (Word, Excel, etc), <b>Adobe</b> Tools, Web Development/Design (<b>HTML, CSS, JavaScript basics</b>), iOS, Mac OS/terminal, MailChimp, OpenX.<br /><br />
                    <b>The Collegian Street Team:</b> Dispersion and transportation of student-led newspaper, requiring communication skills.</p>
                </li>
              </ul>
            </div>
          </div>
          
          <br />
          
          <div className="row">
            <div className="col">
              <ul><h2>Current Courses</h2>
                <li><p><b>CS 314 - Software Engineering</b> <br />
                  ---------team-based development of large, complex software systems. Teamwork, configuration management, project management, requirments, testing, Scrum-based Agile development project, Java programming.</p>
                </li>
                <li><p><b>CS 435 - Introduction to Big Data</b> <br />
                  ---------data organization, data storage systems, self-descriptive data representations, semi-structured data models, large-scale data analysis, MapReduce, and knowledge discover at scale.</p>
                </li>
                <li><p><b>CS 455 - Introduction to Distributed Systems</b> <br />
                  ---------concurrent programming, thread pools and safety, non-blocking I/O, scalable server design, file system design, distributed mutual exclusion and deadlock detection, consensus and consistency, pipelining schemes, distributed graph algorithms, distributed shared memory, distributed objects, and MapReduce.</p>   
                </li>
                <li><p><b>CS 457 - Computer Networks and the Internet</b> <br />
                  ---------network communication principles, service models and packet switching, internetworking, TCP/IP, OSI Layered Model, BGP protocol, congestion control, DNS, Named Data Networks, and Software Defined Networks.</p>
                </li>
              </ul>
            </div>
            <div className="col">
              <ul><h2>Previous Courses</h2>
                <li><p><b>CS 253 - Software Development with C++</b> <br />
                  ---------C/C++, both Object-Oriented (and not) programming, Templates and the STL.</p>
                </li>
                <li><p><b>CS 320 - Algorithms, Theory and Practice</b> <br />
                  ---------algorithms' correctness proofs and complexity, algorithm classes, and problem classes.</p>
                </li>
                <li><p><b>CS 356 - Systems Security</b> <br />
                  ---------network and computer security measures.</p>  
                </li>
                <li><p><b>CS 370 - Operating Systems</b> <br />
                  ---------operating systems, processes, threads, synchronization, CPU scheduling algorithms, deadlocks and resource management, memory (address translation and virtual), storage architecture, file system, and virtual machines.</p>
                </li>
                <li><p><b>JTC 372 - Web Design and Management</b> <br />
                  ---------standards-based layout, cascading style sheets, graphics preparation, and design principles.</p>
                </li>
                <li><p><b>CIS 413 - Advanced Networking and Security</b> <br />
                  ---------modern communication standards, protocol systems, network security, security policies, attack and protection mechanisms, legal and ethical issues, troubleshooting, VMWare/Virtual Machines, installation/maintaining Firewalls and IDS(s), Risk/Threat analysis.</p>
                </li>
              </ul>
            </div>
          </div>
          
          <br />
          
          <div className="row">
            <div className="col">
              <ul><h2>Organizations</h2>
                <li><b>Hashdump (Computer Security Club)</b> <br /> ---------participates in weekly discussions about current security topics/news, and participate in security competitions (RMCCDC 2018).</li>
                <li><b>Hibida Ultimate Frisbee Club Team</b> <br /> ---------traveling sports club team, participated in Nationals 2017 for first time in program history (Peer Education Officer-2015 & Captain-2017).</li>
              </ul>
            </div>
            <div className="col">
              <ul><h2>Awards</h2>
                <li>Deadwood Elks Most Valuable Student Award</li>
                <li>National Honor Society</li>
                <li>School of Mines West River Math Competition Winner - Masters Division</li>
                <li>South Dakota Governor's Leadership Award</li>
              </ul>
            </div>
            <div className="col">
              <ul><h2>Volunteering</h2>
                <li>Wild West Hackin Fest 2017 - Volunteer</li>
                <li>Spearfish Community Coalition - Volunteer</li>
                <li>Youth Ultimate Frisbee Clinics - Director/Volunteer</li>
                <li>Salvation Army Bell Ringing - Volunteer</li>
              </ul>
            </div>
          </div>
      </div>
    )
  }
}

class Footer extends React.Component {
  render() {
    return (
      <div className="container">
      <p>&copy; 2018 - Jake Servaty - SoDakHib</p>
      </div>
    )
  }
}

class Main extends React.Component {
  render() {
    return (
        <div className="Wrapper">
          <div className="jumbotron">
            <Header />
          </div>
          <div className="container">
            <Body />
            <hr />
          </div>
          <div className="jumbotron">
            <Footer />
          </div>
         </div>
    );
  }
}

ReactDOM.render(<Main />, document.getElementById("resume"));