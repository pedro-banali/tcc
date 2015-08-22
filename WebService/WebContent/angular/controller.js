
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
            	
            	$scope.getUsuario = function()
            	{
             		managerSrvc.list(authenticationSvc.getUserInfo().accessToken, function(result) {  // this is only run after $http completes
         		       $scope.usuarios = result.devs;
         		      
         		       var u = $scope.usuarios;
            			
	            		for(var i = 0; i < u.length; i++)
	            		{
	            			if($routeParams.cpf == u[i].cpf)
	            			{
	            				$scope.usuario = u[i];
	            				$scope.usuario.cpfNovo = $scope.usuario.cpf;
	            				return;
	            			}
	            		}
	            		$scope.errorInvalid = true;
	            		$scope.errorMsg = '1';
            		});
            	}
            	
            	$scope.editDev = function()
            	{
            		var user = JSON.stringify($scope.usuario);
            		if(!myForm.$valid)
            		{
            			$http({
            			  method: 'POST',
            			  url:'http://localhost/WebService/selfieCode/service/editDev',
            			  headers: { 'usuario': user , 'key': authenticationSvc.getUserInfo().accessToken }
            			}).
            			  then(function(response) {
            				 
            			    // this callback will be called asynchronously
            			    // when the response is available
            				console.log("result" + response);
            				if(response.data.result == true)
            				{
            					$scope.usuario.cpf = $scope.usuario.cpfNovo;
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
            	
            	$scope.go = function ( path ) {
          		  $location.path( path );
            	};
            	
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


var selfieMyappAtr = angular.module('atr',  []);

selfieMyappDev.controller('atbCtrl', ['$scope','$http', '$location', '$window', '$routeParams', 'projectSvc', 'authenticationSvc', 'managerSrvc',
                                      function ($scope, $http, $location, $window, $routeParams, projectSvc, authenticationSvc, managerSrvc) {
				
				$scope.listarProj = function()
				{
					projectSvc.list(authenticationSvc.getUserInfo().accessToken, function(result) {  // this is only run after $http completes
					       $scope.projetos = result;
					       console.log("scope" + $scope.usuarios);
					    });
				}
				
				$scope.listarDev = function()
				{
					managerSrvc.list(authenticationSvc.getUserInfo().accessToken, function(result) {  // this is only run after $http completes
					       $scope.usuarios = result;
					       console.log("scope" + $scope.usuarios);
					    });
				}
				
				$scope.atribuir = function()
				{
             		    	var atribuicao = JSON.stringify($scope.atribuicao);
             		 
             		        
             		    	$http({
                			  method: 'POST',
                			  url:'http://localhost/WebService/selfieCode/service/atribuir',
                			  headers: {'atribuicao': atribuicao , 'key': authenticationSvc.getUserInfo().accessToken }
                			}).
                			  then(function(response) {
                				 
                			    // this callback will be called asynchronously
                			    // when the response is available
                				console.log("result" + response);
                				if(response.data.result == true)
                				{
                					$scope.atribuicao = {};
                					$scope.sucesso  = true;
                				}
                				else
                				{
                					$scope.errorMsg = 'Usuário já está neste projeto';
                					$scope.errorInvalid  = true;
                				}
                			  }, function(response) {
                			    // called asynchronously if an error occurs
                			    // or server returns response with an error status.
                			  });
             		      }
				
            }]);


var selfieMyappDev = angular.module('proj',  []);
selfieMyappDev.controller('projCtrl', ['$scope','$http', '$location', '$window', '$routeParams', 'projectSvc', 'authenticationSvc',
                                      function ($scope, $http, $location, $window, $routeParams, projectSvc, authenticationSvc) {
            	$scope.error = "";
            	$scope.session = "";
            	$scope.errorMsg = "";
            	$scope.errorInvalid = false;
            	
            	$scope.getProjeto = function()
            	{
            		var projetos = projectSvc.projetos();
            		projetos.then(function (){
            			var aP = projetos.$$state.value.proj;
            		for(var i = 0; i < aP.length; i++)
            		{
            			if($routeParams.proj == aP[i].id)
            			{
            				$scope.projeto = aP[i];
            				var arr = $scope.projeto.inicio.split("-");
            				$scope.projeto.inicio = arr[2] + "/" + arr[1] + "/" + arr[0];
            				var arr = $scope.projeto.fim.split("-");
            				$scope.projeto.fim = arr[2] + "/" + arr[1] + "/" + arr[0];
            				return;
            			}
            		}
            		$scope.errorInvalid = true;
            		$scope.errorMsg = '1';
            		alert($scope.errorMsg);
            		});
            		
            	}
            	            	
            	$scope.atualizarProj = function()
            	{
            		var proj = JSON.stringify($scope.projeto);
            		$http({
            		  method: 'POST',
            		  url:'http://localhost/WebService/selfieCode/service/editProj',
            		  headers:
            		  {
            			  'projeto': proj , 'key': authenticationSvc.getUserInfo().accessToken 
            		  }
            		}).
            		  then(function(response) {
            			 
            		    // this callback will be called asynchronously
            		    // when the response is available
            			console.log("result" + response);
            			if(response.data.result == true)
            			{
            				$scope.sucesso = true;
            			}
            		  }, function(response) {
            		    // called asynchronously if an error occurs
            		    // or server returns response with an error status.
            		  });
            	}
            	
            	$scope.go = function ( path ) {
            		  $location.path( path );
            	};
            }]);