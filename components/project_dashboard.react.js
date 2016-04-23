var ProjectDashboard = React.createClass({
	getInitialState: function() {
    	return {data: []};
	},
	loadCommentsFromServer: function() {
    $.ajax({
	      url: root_url+"/project/"+this.props.project_id+"/velocity",
	      dataType: 'json',
	      cache: false,
	      success: function(data) {
	        this.setState({data: data});
	      }.bind(this),
	      error: function(xhr, status, err) {
	        console.error(this.props.url, status, err.toString());
	      }.bind(this)
	    });
	  },
	componentDidMount: function() {
    	//this.loadCommentsFromServer();
  	},
	render: function() {
    var varkeys = new Array();
    for(var key in this.props.project_data.sprint_calendar) {
      var value = this.props.project_data.sprint_calendar[key];
      varkeys.push({"date":key, "number":value});
    }
    var sprint_calendar = varkeys.map(function(day) {
      return (
        <tr>
          <th>{day.date}</th>
          <td>{day.number}</td>
        </tr>
      );
    });
    alert("ok");
		return (
	       <div className="col-md-6">
            <div class="row">
              <h2>{this.props.project_name}</h2>
              <h3>Overview</h3>
              <div class="col-md-8">
                <canvas id="myChart" width="100" height="30"></canvas>
              </div>
              <div class="col-md-4">
                Projet du 21/04 au 26/04
                Ceci est la description du projet.
              </div>
            </div>
            <div className="row">
              <h3>Bilan</h3>
              <table className="table">
                <tbody>
                  <tr>
                    <th>Nombre de points</th>
                    <td>{this.props.project_data.nb_total}</td>
                  </tr>
                  <tr>
                    <th>Nombre de jour-homme</th>
                    <td>{this.props.project_data.work_days_number}</td>
                  </tr>
                  <tr>
                    <th>Nombre de points à réaliser par jour</th>
                    <td>{this.props.project_data.required_points_by_day}</td>
                  </tr>
                  <tr>
                    <th>Nombre moyen de points à réalisés par jour</th>
                    <td>{this.props.project_data.current_points_by_day}</td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div className="row">
              <h3>Calendrier</h3>
              <table className="table">
                <tbody>
                  {sprint_calendar}
                </tbody>
              </table>
            </div>
        </div>
		);

	}
});

var VELOCITY = {
  "nb_total": 212,
  "nb_completed": 83,
  "work_days_number": 42,
  "days_number": 9,
  "days_elapsed": 4,
  "sprint_calendar": {
    "2016-04-18": 5,
    "2016-04-19": 17,
    "2016-04-21": 27,
    "2016-04-22": 34
  },
  "required_points_by_day": 23.555555555555557,
  "current_points_by_day": 20.75
};

ReactDOM.render(<ProjectDashboard project_id="113075917149779" project_name="SPRINT SPRINT" project_data={VELOCITY}/>, document.getElementById('center'));

