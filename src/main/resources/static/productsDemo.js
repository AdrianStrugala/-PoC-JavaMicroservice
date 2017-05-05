angular.module('product', []).controller('Products', function($scope, $http) {
	$http.get('http://localhost:8080/product').then(function(response) {
		$scope.products = response.data;
		 
		$scope.RPG = function (product) { 
		    return product.type == 'RPG'; 
		};
		
		$scope.FPS = function (product) { 
		    return product.type == 'FPS'; 
		};
		
		$scope.STRATEGY = function (product) { 
		    return product.type == 'STRATEGY'; 
		};
		$scope.SPORT = function (product) { 
		    return product.type == 'SPORT'; 
		};
		$scope.ADVENTURE = function (product) { 
		    return product.type == 'ADVENTURE'; 
		};
		$scope.OTHER = function (product) { 
		    return product.type == 'OTHER'; 
		};
		
		});
});
