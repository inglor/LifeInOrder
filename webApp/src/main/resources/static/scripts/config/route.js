'use strict';

app.config(function ($routeProvider) {
    $routeProvider.when('/Transactions', {
        templateUrl: 'views/transactions.html',
        controller: 'transactions'
    }).when('/Statistics', {
        templateUrl: 'views/statistics.html',
        controller: 'statistics'
    }).when('/Upload', {
        templateUrl: 'views/upload.html',
        controller: 'UploadCtrl'
    }).when('/Home', {
        templateUrl: 'views/home.html'
    }).otherwise({
        templateUrl: 'views/home.html'
    });
});