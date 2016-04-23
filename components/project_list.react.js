var root_url = "http://localhost:8080";    
var ProjectList = React.createClass({
  getInitialState: function() {
    return {data: []};
  },
  loadCommentsFromServer: function() {
    $.ajax({
      url: root_url+this.props.url,
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
    this.loadCommentsFromServer();
    setInterval(this.loadCommentsFromServer, this.props.pollInterval);
  },
  render: function() {
    var projects = this.state.data.map(function(project) {
      return (
        <li key={project.id} className="list-group-item">
          {project.name}
        </li>
      );
    });
    return (
      <ul className="list-group">
        {projects}
      </ul>
    );
  }
});



ReactDOM.render(<ProjectList url="/projects" pollInterval={2000}/>, document.getElementById('left'));
ReactDOM.render(<h1>Hello, world!</h1>, document.getElementById('center'));