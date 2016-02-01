define(
	['angularAMD', 'angular-route', 'angularUI'], 
	function (angularAMD) {
    	var app = angular.module("YogaMat", ['ngRoute']);
	    app.config(function ($routeProvider) {
	        $routeProvider.when("/home", angularAMD.route({
	            templateUrl: 'app/views/home.html', 
	            controller: 'HomeController',
	            controllerUrl: 'controllers/home.controller'
	        }))
	    });


	    
    	return angularAMD.bootstrap(app);
	}
);