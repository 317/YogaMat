require.config({
    baseUrl: "app",    
    paths: {
    	'angular': '//ajax.googleapis.com/ajax/libs/angularjs/1.2.16/angular.min',
        'angular-route': '//ajax.googleapis.com/ajax/libs/angularjs/1.2.16/angular-route.min',
        'angularAMD': '//cdn.jsdelivr.net/angular.amd/0.2.1/angularAMD.min',
        'angularUI':'js/ui-bootstrap-tpls-1.1.1.min'
    },
    shim: { 'angularAMD': ['angular'], 'angular-route': ['angular'], 'angularUI':['angular']},
    deps: ['app']
});