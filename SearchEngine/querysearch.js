 var myapp = angular.module('querysearch', []);

     myapp.controller('search', function($scope,$http){
		 
		 $scope.init = function() {
		 $http({method: 'GET', 
					 url: '/createmap', 
			   		responseType:'json',
					 }).then(function(response) {
					 
					 $scope.urlMap = new Map(response.data);
		
		});
		 }

          $scope.sub = function() {
			  if(!$scope.qopt)
				{
					$scope.qopt = "solr_results";
				}
			  $http({method: 'GET', 
					 url: 'http://localhost/hw4/hw4.php', 
					 params: {query: $scope.query}}).then(function(response) {
					 
					 $scope.sres = response.data;
					 
					 $http({method: 'GET', url: '/query_search', params: {query: $scope.sres, sort:$scope.qopt}}).
        			then(function(response) {
					document.getElementById('results').innerHTML = "Showing Results for: "+$scope.sres;
				  	var res = response.data;
				  	$scope.results = res.docs;
        });
					 
				  		
        });
  			
     }
		  
		  $scope.getSnippet = function(id){
			  var url_id = id.substr(id.lastIndexOf('/')+1);
		  $http({method: 'GET', 
					 url: 'http://localhost/hw4/snippet.php', 
					 params: {id: url_id, q:$scope.query}}).then(function(response) {
					 
					 $scope.snippet = response;
		
		});
		  }
		  $scope.geturl = function(id){
			
			 var url_id = id.substr(id.lastIndexOf('/')+1);
			 
		return $scope.urlMap.get(url_id);
	}
		 
	 });