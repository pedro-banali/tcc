var app = angular.module('selfiecode', [
  'ngRoute',
  'admin',
  'dev'
]).config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/page-dev', {
        templateUrl: 'cadastro-desenvolvedor.html',
        controller: 'adminCtrl',
        resolve: {
            auth: function ($q, authenticationSvc) {
                var userInfo = authenticationSvc.getUserInfo();
                if (userInfo) {
                    return $q.when(userInfo);
                } else {
                    return $q.reject({ authenticated: false });
                }
            }
        }
      }).
      when('/page-project', {
          templateUrl: 'cadastro-projeto.html',
          controller: 'devCtrl',
          resolve: {
              auth: function ($q, authenticationSvc) {
                  var userInfo = authenticationSvc.getUserInfo();
                  if (userInfo) {
                      return $q.when(userInfo);
                  } else {
                      return $q.reject({ authenticated: false });
                  }
              }
          }
        }).
      when('/atribuir', {
            templateUrl: 'atribuir-dev-proj.html',
            controller: 'adminCtrl',
            resolve: {
                auth: function ($q, authenticationSvc) {
                    var userInfo = authenticationSvc.getUserInfo();
                    if (userInfo) {
                        return $q.when(userInfo);
                    } else {
                        return $q.reject({ authenticated: false });
                    }
                }
            }
          }).
      when('/excluir-dev', {
              templateUrl: 'excluir-dev.html',
              controller: 'adminCtrl',
              resolve: {
                  auth: function ($q, authenticationSvc) {
                      var userInfo = authenticationSvc.getUserInfo();
                      if (userInfo) {
                          return $q.when(userInfo);
                      } else {
                          return $q.reject({ authenticated: false });
                      }
                  }
              }
            }).
        when('/index', {
            templateUrl: 'index.html',
            controller: 'adminCtrl',
            resolve: {
                auth: function ($q, authenticationSvc) {
                    var userInfo = authenticationSvc.getUserInfo();
                    if (userInfo) {
                        return $q.when(userInfo);
                    } else {
                        return $q.reject({ authenticated: false });
                    }
                }
            }
          }).
      otherwise({
    	  templateUrl: 'admin-default.html',
    	  controller: 'adminCtrl',
          resolve: {
              auth: function ($q, authenticationSvc) {
                  var userInfo = authenticationSvc.getUserInfo();
                  if (userInfo) {
                      return $q.when(userInfo);
                  } else {
                      return $q.reject({ authenticated: false });
                  }
              }
          }
      });
  }]);
  
  app.run(["$rootScope", "$location", function ($rootScope, $location) {

    $rootScope.$on("$routeChangeSuccess", function (userInfo) {
        console.log(userInfo);
    });

    $rootScope.$on("$routeChangeError", function (event, current, previous, eventObj) {
        if (eventObj.authenticated === false) {
        	window.location.assign("index.html")
        }
    });
}]);

app.factory("authenticationSvc", ["$http","$q","$window",function ($http, $q, $window) {
    var userInfo;
    console.log($q);
    function login(userName, password) {
        var deferred = $q.defer();
        $http.post('http://localhost/WebService/selfieCode/service/login?user='+userName+'&pass='+password).
//		  then(function(response) {
//			 
//		    // this callback will be called asynchronously
//		    // when the response is available
//			
//			  else
//			  {
//				  $scope.error = response.data.result;
//				  $scope.session = "";
//			  }
//				
//		  }, function(response) {
//		    // called asynchronously if an error occurs
//		    // or server returns response with an error status.
//		  });
//        $http.post("/api/login", { userName: userName, password: password })
            then(function (result) {
                userInfo = {
                    accessToken: result.data.result,
                    userName: result.data.userName
                };
                $window.sessionStorage["userInfo"] = JSON.stringify(userInfo);
                deferred.resolve(userInfo);
            }, function (error) {
                deferred.reject(error);
            });

        return deferred.promise;
    }

    function logout() {
        var deferred = $q.defer();

        $http({
            method: "POST",
            url: "/api/logout",
            headers: {
                "access_token": userInfo.accessToken
            }
        }).then(function (result) {
            userInfo = null;
            $window.sessionStorage["userInfo"] = null;
            deferred.resolve(result);
        }, function (error) {
            deferred.reject(error);
        });

        return deferred.promise;
    }

    function getUserInfo() {
        return userInfo;
    }

    function init() {
        if ($window.sessionStorage["userInfo"]) {
            userInfo = JSON.parse($window.sessionStorage["userInfo"]);
        }
    }
    init();

    return {
        login: login,
        logout: logout,
        getUserInfo: getUserInfo
    };
}]);