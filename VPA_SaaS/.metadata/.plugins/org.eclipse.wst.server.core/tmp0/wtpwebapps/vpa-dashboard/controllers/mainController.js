myApp.controller('mainController', ['$scope', '$log', 'dataFactory', 'sharedData', 'commonFunctions', '$rootScope', '$interval', '$sessionStorage', '$location', '$route', function ($scope, $log, dataFactory, sharedData, commonFunctions, $rootScope, $interval, $sessionStorage, $location, $route) {
        $scope.activeUser = null;
	    // Login validation
		$scope.validateEmail = function(sEmail) {
			var filter = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
			if (filter.test(sEmail)) {
				return true;
			}
			else {
				return false;
			}
		};
		
		$scope.adjustWidth = function() {
			var body = document.body, html = document.documentElement;
			var fullHeight = Math.max(body.scrollHeight, body.offsetHeight,html.clientHeight, html.scrollHeight, html.offsetHeight);
			$('.bl_login_tbl, .bl_login_cell, .wrapper_login').css('height', fullHeight);
		}
		
		$scope.init = function(){
			//Initialize scope variables
			$scope.sessionStorage = $sessionStorage;
			
			$scope.location = commonFunctions;
			$scope.authenticationList = [];
			if( $rootScope.globals.currentUser != undefined ){
					var currentTimeStamp = $scope.sessionStorage.currentTimeStamp;
					dataFactory.getNotificationCount(currentTimeStamp)
							.success(function (res) {
								console.log("count: " + res.count);
								$scope.notificationBadgeDigit = res.count;
							})
							.error(function (error) {
								console.log('Unable to load : ' + JSON.stringify(error));
							});
				}
		}
		
		////Common functionality related to top bar and side bar
			////Notification dropdown
		  $scope.status = {
			isopen: false
		  };
		  $scope.notifDDToggled = function(open) {
			$log.log('Dropdown is now: ', open);	
			// fetch authentications list
			if(open){
				var loginTimeStamp = $scope.sessionStorage.loginTimeStamp;
				console.log("login timestamp: " + loginTimeStamp);
				dataFactory.getAuthenticationsList(loginTimeStamp)
						.success(function (res) {
							if(res != null && res.length){
								console.log(JSON.stringify(res));
								$scope.authenticationList = res;
								$scope.sessionStorage.currentTimeStamp = res[0].createdDateString;
								sharedData.setAuthenticationList($scope.authenticationList);
								console.log(JSON.stringify($scope.authenticationList));
							}else{
								
							}
						})
						.error(function (error) {
							console.log('Unable to load authentication list: ' + JSON.stringify(error));
						});
			}
		  };

		  $scope.toggleNotifDropdown = function($event) {
			$event.preventDefault();
			$event.stopPropagation();
			$scope.status.isopen = !$scope.status.isopen;
		  };
		  
		$scope.redirectToNotification = function(){
			if($location.path() == '/notification'){
				$route.reload();
			}else{
				$scope.location.redirectTo('/notification');
			}
		}
			//Set interval to update notification count	
			var intervalNotification= $interval(callAtInterval, 15000);
			$scope.$on('$destroy', function () { $interval.cancel(intervalNotification); });
			function callAtInterval(){
				if( $rootScope.globals.currentUser != undefined ){
					var currentTimeStamp = $scope.sessionStorage.currentTimeStamp;
					console.log("current timestamp: " + currentTimeStamp);
					dataFactory.getNotificationCount(currentTimeStamp)
							.success(function (res) {
								console.log("count: " + res.count);
								$scope.notificationBadgeDigit = res.count;
							})
							.error(function (error) {
								console.log('Unable to load : ' + JSON.stringify(error));
							});
				}
		}
			$scope.logout = function(){
				$scope.sessionStorage.$reset();
				dataFactory.ClearCredentials();
				$location.url('/login');
			}
    }]);