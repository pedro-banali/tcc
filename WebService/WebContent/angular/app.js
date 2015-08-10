angular.module('selfiecode', [
  'ngRoute',
  'admin',
  'dev'
]).config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/page-dev', {
        templateUrl: 'cadastro-desenvolvedor.html',
        controller: 'adminCtrl'
      }).
      when('/page-project', {
          templateUrl: 'cadastro-projeto.html',
          controller: 'devCtrl'
        }).
      otherwise({
    	  templateUrl: 'admin-default.html',
    	  controller: 'adminCtrl'
      });
  }]);