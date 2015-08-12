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
      when('/atribuir', {
            templateUrl: 'atribuir-dev-proj.html',
            controller: 'adminCtrl'
          }).
      when('/excluir-dev', {
              templateUrl: 'excluir-dev.html',
              controller: 'adminCtrl'
            }).
      otherwise({
    	  templateUrl: 'admin-default.html',
    	  controller: 'adminCtrl'
      });
  }]);