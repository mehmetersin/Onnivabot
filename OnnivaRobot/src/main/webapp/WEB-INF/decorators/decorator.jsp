<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.0/angular.min.js"></script>
</head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>


<link
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css"
	rel="stylesheet">

<style type="text/css">
.bs-example {
	margin: 20px;
}
</style>




<body ng-app="myapp" id="MainWrap" ng-controller="MyController"
	style="background-repeat: no-repeat, background-size: cover"
	ng-style="{'background-image':'url('+displayUrl+')'}"
	ng-init="accessCodeEntered()">



	<!-- 	<video width="400"  controls>
			<source src="{{url}}"
				type="video/mp4">
			Your browser does not support HTML5 video.
		</video> -->

	<!-- <img src="{{url}}" class="full-screen"> -->



</body>


<script>
	var app = angular.module("myapp", []);

	function MyController($scope, $http, $timeout) {

		$scope.accessCodeEntered = function() {

			var response = $http.get('/SporSimdiTurnikeRpi/access/' + "X");

			response.success(function(data, status, headers, config) {

				var resp = JSON.parse(angular.toJson(data));

				var type = resp.type;

				$scope.displayUrl = resp.url;

				if (type == "video") {
					$scope.success = true;
				} else {
					$scope.fail = true;
				}

				$timeout(function() {
					$scope.accessCodeEntered();
				}, 8000);

			});

			response.error(function(data, status, headers, config) {
				alert('error')
				$timeout(function() {
				}, 8000);

			});

		};

	}
</script>

</html>