var selfieMyappAdmin = angular.module('admin',  []);

selfieMyappAdmin.value('session', '');

selfieMyappAdmin.controller('adminCtrl', ['$scope','$http', '$location', '$window',
                          function ($scope, $http, $location, $window , session) {
	$scope.error = "";
	$scope.session = "";
	$scope.login = function()
	{	
		$http.post('http://localhost/WebService/selfieCode/service/login?user='+$scope.usuario+'&pass='+$scope.password).
		  then(function(response) {
			 
		    // this callback will be called asynchronously
		    // when the response is available
			  if(response.data.result != 'login inexistente')
			  {
				  this.session = response.data.result;
				 $window.location.assign('http://localhost/WebService/pages/admin.html');
			  }
			  else
			  {
				  $scope.error = response.data.result;
				  $scope.session = "";
			  }
				
		  }, function(response) {
		    // called asynchronously if an error occurs
		    // or server returns response with an error status.
		  });
	}
	
	$scope.validate = function()
	{

		if($scope.session == "" && $window.location.pathname != "/WebService/pages/index.html")
			$window.location.assign('http://localhost/WebService/pages/index.html');
	}
	
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
	
var selfieMyappDev = angular.module('dev',  []);
selfieMyappDev.controller('devCtrl', ['$scope','$http', '$location', '$window',
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