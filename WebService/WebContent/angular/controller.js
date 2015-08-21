
angular.module('admin',  ['ngCookies'])
.controller('adminCtrl', ['$scope','$http', '$location', '$window','$cookies', '$routeParams', 'authenticationSvc', 'managerSrvc', 'projectSvc',  '$modal',
                          function ($scope, $http, $location, $window, $cookies , $routeParams, authenticationSvc, managerSrvc, projectSvc, $modal) {
	$scope.error = "";
	$scope.toSearch = false;
	
	$scope.login = function()
	{	
		authenticationSvc.login($scope.usuario, $scope.password);
		
	}
	
	$scope.logout = function()
	{	
		authenticationSvc.logout();
		
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
	
	$scope.open = function(projeto)
	{
		$scope.projeto = projeto;
		var modalInstance = $modal.open({
			 templateUrl: 'myModalContent.html',
             controller: 'MyModalInstanceController',
		      scope: $scope
		    });
		
		 modalInstance.result.then(
   		      function(result) {
   		    	var projeto = JSON.stringify($scope.projeto);
   		        //Happy path - The user clicked okay.
   		    	$http({
      			  method: 'POST',
      			  url:'http://localhost/WebService/selfieCode/service/excluirProj',
      			  headers: { 'projeto': projeto , 'key': authenticationSvc.getUserInfo().accessToken }
      			}).
      			  then(function(response) {
      				 
      			    // this callback will be called asynchronously
      			    // when the response is available
      				console.log("result" + response);
      				if(response.data.result == true)
      				{
      					$scope.proj = {};
      					$scope.sucesso  = true;
      					$scope.listarProj();
      				}
      				else
      				{
      					$scope.errorInvalid  = true;
      				}
      			  }, function(response) {
      			    // called asynchronously if an error occurs
      			    // or server returns response with an error status.
      			  });
   		      }, function(err) {
   		        //Sad path - The user probably canceled.
   		    });
	}
}]);
	
var selfieMyappDev = angular.module('dev',  []);
selfieMyappDev.controller('devCtrl', ['$scope','$http', '$location', '$window', '$routeParams', 'authenticationSvc', 'managerSrvc', 'projectSvc', '$modal',
                                      function ($scope, $http, $location, $window , $routeParams, authenticationSvc, managerSrvc, projectSvc, $modal) {

            	            	
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
            	
            	$scope.listarDev = function()
            	{
            		managerSrvc.list(authenticationSvc.getUserInfo().accessToken, function(result) {  // this is only run after $http completes
            		       $scope.usuarios = result;
            		       console.log("scope" + $scope.usuarios);
            		    });
            	}
            	
            	$scope.open = function(usuario)
            	{
            		$scope.usuario = usuario;
            		var modalInstance = $modal.open({
            			 templateUrl: 'myModalContent.html',
                         controller: 'MyModalInstanceController',
            		      scope: $scope
            		    });
            		
            		 modalInstance.result.then(
               		      function(result) {
               		    	var user = JSON.stringify($scope.usuario);
               		        //Happy path - The user clicked okay.
               		    	$http({
                  			  method: 'POST',
                  			  url:'http://localhost/WebService/selfieCode/service/excluirDev',
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
                  					$scope.listarDev();
                  				}
                  				else
                  				{
                  					$scope.errorInvalid  = true;
                  				}
                  			  }, function(response) {
                  			    // called asynchronously if an error occurs
                  			    // or server returns response with an error status.
                  			  });
               		      }, function(err) {
               		        //Sad path - The user probably canceled.
               		    });
            	}
            	 
            }]);

var selfieMyappModal = angular.module('modal',  []);
selfieMyappDev.controller('MyModalInstanceController', ["$scope", "$modalInstance", function($scope, $modalInstance)
                                 {
                                   $scope.clickedOkay = function() {
                                     //Happy path.
                                     $modalInstance.close("someul data");
                                     // Calling $modalInstance.dismiss() takes us down the sad path.
                                   };
                                   
                                   $scope.clickedCancel = function() {
                                       //Happy path.
                                       $modalInstance.dismiss();
                                       // Calling $modalInstance.dismiss() takes us down the sad path.
                                     };
                                   
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