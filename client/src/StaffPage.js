import React, {Component} from 'react';

class StaffPage extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return(
        <div>
            <h1>Meet the Developers</h1>
            <hr/>
            <row>
                <div className="col-md-4"><h4>Jake Servaty</h4></div>
                <div className="col-md-4"><img className="avatar width-full rounded-2" src="https://avatars0.githubusercontent.com/u/16375823?s=460&amp;v=4" width="230" height="230" alt=""/></div>
                <div className="col-md-4">When Jake's not focusing on his studies, he spends his time playing Ultimate Frisbee for the Hibida Men's Ultimate Club Team, and participating in Hashdump (Computer Security Club), who competed at the Rocky Mountain Collegiate Cyber Defense Competition 2018 and placed 2nd. His <a href="https://www.linkedin.com/in/jacob-servaty/">LinkedIn Profile</a>.</div>
            </row>
            <hr/>
            <row>
                <div className="col-md-4"><h4>Drew Boston</h4></div>
                <div className="col-md-4"><img className="avatar width-full rounded-2" src="https://avatars3.githubusercontent.com/u/31427655?s=460&amp;v=4" width="230" height="230" alt=""/></div>
                <div className="col-md-4">Drew Boston is a CS student at Colorado State University, set to graduate December 2018. Currently he tutors / proctors for the mathematics department, and assists with PARRIC undergraduate research. His <a href="https://github.com/dboston1">GitHub Profile</a>.</div>
            </row>
            <hr/>
            <row>
                <div className="col-md-4"><h4>Luke Burford</h4></div>
                <div className="col-md-4"><img className="avatar width-full rounded-2" src="https://avatars0.githubusercontent.com/u/24280180?s=460&amp;v=4" width="230" height="230" alt=""/></div>
                <div className="col-md-4">Luke is a 3rd Year Computer Science Major at Colorado State University. He likes dirt biking, snowmobiling, skiing, and hiking. Luke is currently working in undergraduate research for CSU studying RNA-RNA interaction sequencing. His <a href="https://uk.linkedin.com/in/luke-burford-067073108">LinkedIn Profile</a>.</div>
            </row>
            <hr/>
            <row>
                <div className="col-md-4"><h4>Jake Marrapode</h4></div>
                <div className="col-md-4"><img className="avatar width-full rounded-2" src="https://avatars1.githubusercontent.com/u/15206562?s=460&amp;v=4" width="230" height="230" alt=""/></div>
                <div className="col-md-4">Jake is a Chicago native and can tell you all about deep dish pizza and hockey. He can also help create this staff tab and write a bio one hour before the deadline. This proves his ability to work under pressure. View his resume <a href="http://cs.colostate.edu/~jpode/">here</a>.</div>
            </row>
            <hr/>
        </div>
    )
  }

}

export default StaffPage;