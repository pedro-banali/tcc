
angular.module('admin',  ['ngCookies'])
.controller('adminCtrl', ['$scope','$http', '$location', '$window','$cookies', '$routeParams', 'authenticationSvc', 'managerSrvc', 'projectSvc',
                          function ($scope, $http, $location, $window, $cookies , $routeParams, authenticationSvc, managerSrvc, projectSvc) {
	$scope.error = "";
	$scope.toSearch = false;
	
	$scope.login = function()
	{	
		authenticationSvc.login($scope.usuario, $scope.password);
		
	}
	
	$scope.showList = function()
	{	
		
		if(!$scope.toSearch)
		{
			$scope.toSearch = true;
			managerSrvc.list(authenticationSvc.getUserInfo().accessToken, function(result) {  // this is only run after $http completes
			       $scope.usuarios = result;
			       console.log("scope" + $scope.usuarios);
			    });
			
		}
		else
		{
			$scope.toSearch = false;
		}
	}
	
	$scope.validate = function()
	{

		if($scope.session == "" && $window.location.pathname != "/WebService/pages/index.html")
			$window.location.assign('http://localhost/WebService/pages/index.html');
	}
	
	
	
	
	$scope.listarProj = function()
	{
		projectSvc.list(authenticationSvc.getUserInfo().accessToken, function(result) {  // this is only run after $http completes
		       $scope.projetos = result;
		       console.log("scope" + $scope.usuarios);
		    });
	}
	
	$scope.cadastroProj = function()
	{

		var projeto = JSON.stringify($scope.projeto);
		if(!myForm.$valid)
		{
			$http({
			  method: 'POST',
			  url:'http://localhost/WebService/selfieCode/service/cadastroProj',
			  headers: { 'projeto': projeto , 'key': authenticationSvc.getUserInfo().accessToken }
			}).
			  then(function(response) {
				 
			    // this callback will be called asynchronously
			    // when the response is available
				console.log("result" + response);
				if(response.data.result == true)
				{
					$scope.projeto = {};
					$scope.sucesso  = true;
				}
			  }, function(response) {
			    // called asynchronously if an error occurs
			    // or server returns response with an error status.
			  });
		}
		else
		{
			$scope.errorValid = true;
		}
	}
}]);
	
var selfieMyappDev = angular.module('dev',  []);
selfieMyappDev.controller('devCtrl', ['$scope','$http', '$location', '$window',
                                      function ($scope, $http, $location, $window, session) {

            	            	
            	$scope.cadastroDev = function()
            	{

            		var user = JSON.stringify($scope.usuario);
            		if(!myForm.$valid)
            		{
            			$http({
            			  method: 'POST',
            			  url:'http://localhost/WebService/selfieCode/service/cadastroDev',
            			  headers: { 'usuario': user , 'key': authenticationSvc.getUserInfo().accessToken }
            			}).
            			  then(function(response) {
            				 
            			    // this callback will be called asynchronously
            			    // when the response is available
            				console.log("result" + response);
            				if(response.data.result == true)
            				{
            					$scope.usuario = {};
            					$scope.sucesso  = true;
            				}
            			  }, function(response) {
            			    // called asynchronously if an error occurs
            			    // or server returns response with an error status.
            			  });
            		}
            		else
            		{
            			$scope.errorValid = true;
            		}
            	}
            }]);

var selfieMyappDev = angular.module('proj',  []);
selfieMyappDev.controller('projCtrl', ['$scope','$http', '$location', '$window',
                                      function ($scope, $http, $location, $window, session) {
            	$scope.error = "";
            	$scope.session = "";
            	            	
            	$scope.cadastroDev = function()
            	{
            		var user = JSON.stringify($scope.usuario);
            		$http({
            		  method: 'POST',
            		  url:'http://localhost/WebService/selfieCode/service/cadastroDev?usuario='+ user
            		}).
            		  then(function(response) {
            			 
            		    // this callback will be called asynchronously
            		    // when the response is available
            			console.log("result" + response);
            				
            		  }, function(response) {
            		    // called asynchronously if an error occurs
            		    // or server returns response with an error status.
            		  });
            	}
            }]);