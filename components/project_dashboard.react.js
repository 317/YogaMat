var ProjectDashboard = React.createClass({
  getInitialState: function() {
    return { project_data: [] };
   
  },
  loadVelocityFromServer: function() {
    $.ajax({
      url: root_url + "/project/" + this.props.project_id + "/velocity",
      dataType: 'json',
      cache: false,
      success: function(data) {
        this.setState({ project_data: data });
        this.displayData();
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(root_url + "/project/" + this.props.project_id + "/velocity", status, err.toString());
      }.bind(this)
    });
  },
  componentDidMount: function() {
    this.displayData();
    this.loadVelocityFromServer();
  },

  displayData: function(){
    var day_list = new Array();
    var value_list = new Array();
    for (var key in this.state.project_data.sprint_calendar) {
      var value = this.state.project_data.sprint_calendar[key];
      day_list.push(key);
      value_list.push(value);
    }

    var data = {
      labels: day_list,
      datasets: [{
        label: "Nombre de points réalisés",
        fillColor: "rgba(220,220,220,0.2)",
        strokeColor: "rgba(220,220,220,1)",
        pointColor: "rgba(220,220,220,1)",
        pointStrokeColor: "#fff",
        pointHighlightFill: "#fff",
        pointHighlightStroke: "rgba(220,220,220,1)",
        data: value_list
      }]
    };


    // Get the context of the canvas element we want to select
    var ctx = document.getElementById("myChart").getContext("2d");
    var myNewChart = new Chart(ctx, {
      type: "bar",
      data: data,
      options: {
        height: "200px",
        scales: {
          xAxes: [{
            stacked: true
          }],
          yAxes: [{
            stacked: true
          }]
        }
      }
    });
  },
  render: function() {
    var varkeys = new Array();
    for (var key in this.state.project_data.sprint_calendar) {
      var value = this.state.project_data.sprint_calendar[key];
      varkeys.push({ "date": key, "number": value });
    }
    var sprint_calendar = varkeys.map(function(day) {
      return (
        <tr>
          <th>{day.date}</th>
          <td>{day.number}</td>
        </tr>
      );
    });
    return (
      <div className="row">
        <div className="row">
          <h2>{this.props.project_name}</h2>
          <h3>Overview</h3>
          <div className="col-md-8">
            <canvas id="myChart" width="100" height="30"></canvas>
          </div>
          <div className="col-md-4">
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
                <td>{this.state.project_data.nb_total}</td>
              </tr>
              <tr>
                <th>Nombre de jour-homme</th>
                <td>{this.state.project_data.work_days_number}</td>
              </tr>
              <tr>
                <th>Nombre de points à réaliser par jour</th>
                <td>{this.state.project_data.required_points_by_day}</td>
              </tr>
              <tr>
                <th>Nombre moyen de points à réalisés par jour</th>
                <td>{this.state.project_data.current_points_by_day}</td>
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
