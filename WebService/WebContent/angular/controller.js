
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
            				else
            				{
            					$scope.errorMsg = 'CPF ou Login já existente.';
            					$scope.sucesso  = false;
            					$scope.errorInvalid  = true;
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
                					$scope.sucesso  = false;
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

var selfieMyappDevInfo = angular.module('devInfo',  []);
selfieMyappDev.controller('devInfoCtrl', ['$scope','$http', '$location', '$window', '$routeParams', 'projectSvc','authenticationSvc',
                                      function ($scope, $http, $location, $window, $routeParams, projectSvc, authenticationSvc) {
				$scope.cpf = $routeParams.cpf;
				$scope.listarProj = function()
				{
					projectSvc.listCpf($scope.cpf, authenticationSvc.getUserInfo().accessToken, function(result) {  // this is only run after $http completes
					       $scope.projetos = result;
					       console.log("scope" + $scope.usuarios);
					    });
				}
				
            }]);

var selfieMyappDevInfo = angular.module('devGraph',  []);
selfieMyappDev.controller('devGraphCtrl', ['$scope','$http', '$location', '$window', '$routeParams', 'projectSvc','authenticationSvc',
                                      function ($scope, $http, $location, $window, $routeParams, projectSvc, authenticationSvc) {
				$scope.cpf = $routeParams.cpf;
				$scope.proj = $routeParams.proj;
				
				$scope.verificarChart = function()
				{
					var p = {"proj": $scope.proj };
					var d = {"dev": $scope.cpf };
					var proj = JSON.stringify(p);
					var usuario = JSON.stringify(d);
            		$http({
            		  method: 'POST',
            		  url:'http://localhost/WebService/selfieCode/service/exibirMetricas',
            		  headers:
            		  {
            			 'usuario': usuario, 'projeto': proj , 'key': authenticationSvc.getUserInfo().accessToken 
            		  }
            		}).
            		  then(function(response) {
            			 
            		    // this callback will be called asynchronously
            		    // when the response is available
            			console.log("result" + response);
            			if(response.data)
            			{
            				$scope.response = response.data;
            				console.log(response);
            				var ccm= [];
            				var wmc= [];
            				var lcom= [];
            				var nbd= [];
            				var dit= [];
            				var noc= [];
            				var norm= [];
            				var six= [];
            				var mloc= [];
            				var nac= [];
            				var nsf= [];
            				var nsm= [];
            				var par= [];
            				var noi= [];
            				var nop= [];
            				var ca= [];
            				var ce = [];
            				var ins = [];
            				var abs = [];
            				for(var i = 0; i < response.data.length; i++)
            				{
            					var fu = response.data[i];
            					var dateS = fu.dataColecao.split('-');
            					
            					//var date =  new Intl.DateTimeFormat(new Date(dateS[0]+"/"+(dateS[1] - 1)+"/"+dateS[2]));
            					var date =  new Date(dateS[0], (dateS[1] - 1) , dateS[2]);
            					
            					//date.format('dd/MM/yyyy');
            					//date = formatDate(date);
            					//date = new Date(date);
            					var val = [];
            					
            					for(var k = 0; k <fu.metricas.length; k++)
            					{
            						var metrica = fu.metricas[k];
            						switch (metrica.sigla)
            						{
            							case "CCM":
            								ccm.push({
            									x: date,
            							 		y: metrica.valorMetrica
            								})
            								break;
            							case "WMC":
            								wmc.push({
            									x: date,
            							 		y: metrica.valorMetrica
            								})
            								break;
            							case "LCOM*":
            								lcom.push({
            									x: date,
            							 		y: metrica.valorMetrica
            								})
            								break;
            							case "NBD":
            								nbd.push({
            									x: date,
            							 		y: metrica.valorMetrica
            								})
            								break;
            							case "DIT":
            								dit.push({
            									x: date,
            							 		y: metrica.valorMetrica
            								})
            								break;
            							case "NOC":
            								noc.push({
            									x: date,
            							 		y: metrica.valorMetrica
            								})
            								break;
            							case "NORM":
            								norm.push({
            									x: date,
            							 		y: metrica.valorMetrica
            								})
            								break;
            							case "SIX":
            								six.push({
            									x: date,
            							 		y: metrica.valorMetrica
            								})
            								break;
            							case "MLOC":
            								mloc.push({
            									x: date,
            							 		y: metrica.valorMetrica
            								})
            								break;
            							case "NAC":
            								nac.push({
            									x: date,
            							 		y: metrica.valorMetrica
            								})
            								break;
            							case "NSF":
            								nsf.push({
            									x: date,
            							 		y: metrica.valorMetrica
            								})
            								break;
            							case "NSM":
            								nsm.push({
            									x: date,
            							 		y: metrica.valorMetrica
            								})
            								break;
            							case "PAR":
            								par.push({
            									x: date,
            							 		y: metrica.valorMetrica
            								})
            								break;
            							case "NOI":
            								noi.push({
            									x: date,
            							 		y: metrica.valorMetrica
            								})
            								break;
            							case "NOP":
            								nop.push({
            									x: date,
            							 		y: metrica.valorMetrica
            								})
            								break;
            							case "Ca":
            								ca.push({
            									x: date,
            							 		y: metrica.valorMetrica
            								})
            								break;
            							case "Ce":
            								ce.push({
            									x: date,
            							 		y: metrica.valorMetrica
            								})
            								break;
            							case "I":
            								ins.push({
	        									x: date,
	        							 		y: metrica.valorMetrica
	        								})
            								break;
            							case "A":
            								abs.push({
	        									x: date,
	        							 		y: metrica.valorMetrica
	        								})
            								break;
            								
            						}
            					}

            				}
            				
  
              				var data = []
            				var dataSeries = { type: "line" };
            				var dataPoints = [];
            				dataSeries.dataPoints = wmc;
            			    data.push(dataSeries);
            				
            			    var dataSeries = { type: "line" };
            				var dataPoints = [];
            				dataSeries.dataPoints = ccm;
            			    data.push(dataSeries);
            			    
            			    var dataSeries = { type: "line" };
            				var dataPoints = [];
            				dataSeries.dataPoints = lcom;
            			    data.push(dataSeries);
            			    
            				$scope.ccm = new CanvasJS.Chart("ccmContainer", {
            					zoomEnabled: true,      
            					title:{
            						text: "Métricas CCM" 
            					},
            					axisX :{
            						valueFormatString:  "DD/MM/YYYY", // move comma to change formatting
            						labelAngle: -30,
            						interval: 1,
            					},
            					axisY :{
            						includeZero: false
            					},
            					legend: {
            						horizontalAlign: "right",
            						verticalAlign: "center"        
            					},
            					data: data,
            				});

            				$scope.ccm.render();
            				
            				var data = []
            				var dataSeries = { type: "line" };
            				var dataPoints = [];
            				dataSeries.dataPoints = wmc;
            			    data.push(dataSeries);
            				
            				$scope.wmc = new CanvasJS.Chart("wmcContainer", {
            					zoomEnabled: true,      
            					title:{
            						text: "Métricas WMC" 
            					},
            					axisX :{
            						valueFormatString:  "DD/MM/YYYY", // move comma to change formatting
            						labelAngle: -30,
            						interval: 1,
            					},
            					axisY :{
            						includeZero: false
            					},
            					legend: {
            						horizontalAlign: "right",
            						verticalAlign: "center"        
            					},
            					data: data,
            				});

            				$scope.wmc.render();
            				
            				var data = []
            				var dataSeries = { type: "line" };
            				var dataPoints = [];
            				dataSeries.dataPoints = lcom;
            			    data.push(dataSeries);
            				
            				$scope.lcom = new CanvasJS.Chart("lcomContainer", {
            					zoomEnabled: true,      
            					title:{
            						text: "Métricas LCOM" 
            					},
            					axisX :{
            						valueFormatString:  "DD/MM/YYYY", // move comma to change formatting
            						labelAngle: -30,
            						interval: 1,
            					},
            					axisY :{
            						includeZero: false
            					},
            					legend: {
            						horizontalAlign: "right",
            						verticalAlign: "center"        
            					},
            					data: data,
            				});

            				$scope.lcom.render();
            				
               				var data = []
            				var dataSeries = { type: "line" };
            				var dataPoints = [];
            				dataSeries.dataPoints = nbd;
            			    data.push(dataSeries);
            				
            			    CanvasJS.addColorSet("color",
            		                [//colorSet Array
            		                 "#cccccc",
            		                 "#EC5657",
            		                 "#1BCDD1",
            		                 "#8FAABB",
            		                 "#B08BEB",
            		                 "#3EA0DD",
            		                 "#F5A52A",
            		                 "#23BFAA",
            		                 "#FAA586",
            		                 "#EB8CC6",             
            		                ]);
            			    
            				$scope.nbd = new CanvasJS.Chart("nbdContainer", {
            					zoomEnabled: true, 
            					colorSet: "color",
            					title:{
            						text: "Métricas NBD" 
            					},
            					axisX :{
            						valueFormatString:  "DD/MM/YYYY", // move comma to change formatting
            						labelAngle: -30,
            						interval: 1,
            						intervalType: "day"
            					},
            					axisY :{
            						includeZero: false
            					},
            					legend: {
            						horizontalAlign: "right",
            						verticalAlign: "center"        
            					},
            					data: data,
            				});

            				$scope.lcom.render();
            				
            				var data = []
            				var dataSeries = { type: "line" };
            				var dataPoints = [];
            				dataSeries.dataPoints = nbd;
            			    data.push(dataSeries);
            				
            				$scope.nbd = new CanvasJS.Chart("nbdContainer", {
            					zoomEnabled: true,      
            					title:{
            						text: "Métricas NBD" 
            					},
            					axisX :{
            						valueFormatString:  "DD/MM/YYYY", // move comma to change formatting
            						labelAngle: -30,
            						interval: 1,
            					},
            					axisY :{
            						includeZero: false
            					},
            					legend: {
            						horizontalAlign: "right",
            						verticalAlign: "center"        
            					},
            					data: data,
            				});

            				$scope.nbd.render();
            				
            				var data = []
            				var dataSeries = { type: "line" };
            				var dataPoints = [];
            				dataSeries.dataPoints = dit;
            			    data.push(dataSeries);
            				
            				$scope.dit = new CanvasJS.Chart("ditContainer", {
            					zoomEnabled: true,      
            					title:{
            						text: "Métricas DIT" 
            					},
            					axisX :{
            						valueFormatString:  "DD/MM/YYYY", // move comma to change formatting
            						labelAngle: -30,
            						interval: 1,
            					},
            					axisY :{
            						includeZero: false
            					},
            					legend: {
            						horizontalAlign: "right",
            						verticalAlign: "center"        
            					},
            					data: data,
            				});

            				$scope.dit.render();
            				
            				
            				var data = []
            				var dataSeries = { type: "line" };
            				var dataPoints = [];
            				dataSeries.dataPoints = noc;
            			    data.push(dataSeries);
            				
            				$scope.noc = new CanvasJS.Chart("nocContainer", {
            					zoomEnabled: true,      
            					title:{
            						text: "Métricas NOC" 
            					},
            					axisX :{
            						valueFormatString:  "DD/MM/YYYY", // move comma to change formatting
            						labelAngle: -30,
            						interval: 1,
            					},
            					axisY :{
            						includeZero: false
            					},
            					legend: {
            						horizontalAlign: "right",
            						verticalAlign: "center"        
            					},
            					data: data,
            				});

            				$scope.noc.render();
            				
            				var data = []
            				var dataSeries = { type: "line" };
            				var dataPoints = [];
            				dataSeries.dataPoints = norm;
            			    data.push(dataSeries);
            				
            				$scope.norm = new CanvasJS.Chart("normContainer", {
            					zoomEnabled: true,      
            					title:{
            						text: "Métricas NORM" 
            					},
            					axisX :{
            						valueFormatString:  "DD/MM/YYYY", // move comma to change formatting
            						labelAngle: -30,
            						interval: 1,
            					},
            					axisY :{
            						includeZero: false
            					},
            					legend: {
            						horizontalAlign: "right",
            						verticalAlign: "center"        
            					},
            					data: data,
            				});

            				$scope.norm.render();
            				
            				var data = []
            				var dataSeries = { type: "line" };
            				var dataPoints = [];
            				dataSeries.dataPoints = six;
            			    data.push(dataSeries);
            			    

            				$scope.six = new CanvasJS.Chart("sixContainer", {
            					zoomEnabled: true,     
            					colorSet: "color",
            					title:{
            						text: "Métricas SIX" 
            					},
            					axisX :{
            						valueFormatString:  "DD/MM/YYYY", // move comma to change formatting
            						labelAngle: -30,
            						interval: 1,
            					},
            					axisY :{
            						includeZero: false
            					},
            					legend: {
            						horizontalAlign: "right",
            						verticalAlign: "center"        
            					},
            					data: data,
            				});

            				$scope.six.render();
            				

            				var data = []
            				var dataSeries = { type: "line" };
            				var dataPoints = [];
            				dataSeries.dataPoints = mloc;
            			    data.push(dataSeries);
            			    

            				$scope.mloc = new CanvasJS.Chart("mlocContainer", {
            					zoomEnabled: true,     
            					colorSet: "color",
            					title:{
            						text: "Métricas MLOC" 
            					},
            					axisX :{
            						valueFormatString:  "DD/MM/YYYY", // move comma to change formatting
            						labelAngle: -30,
            						interval: 1,
            					},
            					axisY :{
            						includeZero: false
            					},
            					legend: {
            						horizontalAlign: "right",
            						verticalAlign: "center"        
            					},
            					data: data,
            				});

            				$scope.mloc.render();
            				
              				var data = []
            				var dataSeries = { type: "line" };
            				var dataPoints = [];
            				dataSeries.dataPoints = nac;
            			    data.push(dataSeries);
            			    

            				$scope.nac = new CanvasJS.Chart("nacContainer", {
            					zoomEnabled: true,     
            					colorSet: "color",
            					title:{
            						text: "Métricas NAC" 
            					},
            					axisX :{
            						valueFormatString:  "DD/MM/YYYY", // move comma to change formatting
            						labelAngle: -30,
            						interval: 1,
            					},
            					axisY :{
            						includeZero: false
            					},
            					legend: {
            						horizontalAlign: "right",
            						verticalAlign: "center"        
            					},
            					data: data,
            				});

            				$scope.nac.render();
            				
              				var data = []
            				var dataSeries = { type: "line" };
            				var dataPoints = [];
            				dataSeries.dataPoints = nsf;
            			    data.push(dataSeries);
            			    

            				$scope.nsf = new CanvasJS.Chart("nsfContainer", {
            					zoomEnabled: true,     
            					colorSet: "color",
            					title:{
            						text: "Métricas NSF" 
            					},
            					axisX :{
            						valueFormatString:  "DD/MM/YYYY", // move comma to change formatting
            						labelAngle: -30,
            						interval: 1,
            					},
            					axisY :{
            						includeZero: false
            					},
            					legend: {
            						horizontalAlign: "right",
            						verticalAlign: "center"        
            					},
            					data: data,
            				});

            				$scope.nsf.render();            				
            				
            			}
            		  }, function(response) {
            		    // called asynchronously if an error occurs
            		    // or server returns response with an error status.
            		  });
            	}
				
            }]);