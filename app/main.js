require.config({
    baseUrl: "app",    
    paths: {
    	'angular': '//ajax.googleapis.com/ajax/libs/angularjs/1.2.16/angular.min',
        'angular-route': '//ajax.googleapis.com/ajax/libs/angularjs/1.2.16/angular-route.min',
        'angularMaterial':'//ajax.googleapis.com/ajax/libs/angular_material/1.0.0/angular-material.min',
        'angularAMD': '//cdn.jsdelivr.net/angular.amd/0.2.1/angularAMD.min'
    },
    shim: { 'angularAMD': ['angular'], 'angular-route': ['angular'] },
    deps: ['app']
});