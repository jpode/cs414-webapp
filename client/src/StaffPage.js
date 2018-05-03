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
                <div className="col-md-4"><img class="avatar width-full rounded-2" src="https://avatars0.githubusercontent.com/u/16375823?s=460&amp;v=4" width="230" height="230" alt=""/></div>
                <div className="col-md-4">Bio Here</div>
            </row>
            <hr/>
            <row>
                <div className="col-md-4"><h4>Drew Boston</h4></div>
                <div className="col-md-4"><img class="avatar width-full rounded-2" src="https://avatars3.githubusercontent.com/u/31427655?s=460&amp;v=4" width="230" height="230" alt=""/></div>
                <div className="col-md-4">Bio Here</div>
            </row>
            <hr/>
            <row>
                <div className="col-md-4"><h4>Luke Burford</h4></div>
                <div className="col-md-4"><img class="avatar width-full rounded-2" src="https://avatars0.githubusercontent.com/u/24280180?s=460&amp;v=4" width="230" height="230" alt=""/></div>
                <div className="col-md-4">Bio Here</div>
            </row>
            <hr/>
            <row>
                <div className="col-md-4"><h4>Jake Marrapode</h4></div>
                <div className="col-md-4"><img class="avatar width-full rounded-2" src="https://avatars1.githubusercontent.com/u/15206562?s=460&amp;v=4" width="230" height="230" alt=""/></div>
                <div className="col-md-4">Bio Here</div>
            </row>
            <hr/>
        </div>
    )
  }

}

export default StaffPage;