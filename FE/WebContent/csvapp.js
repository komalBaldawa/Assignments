var csvApp = angular.module("csvApp", ['ngFileUpload']);

csvApp.controller("csvController", function($scope, $window) {
	
	 $scope.reverse = true;
	 $scope.SelectFile = function (file) {
         $scope.SelectedFile = file;
     };
     $scope.Upload = function () {
        
             if (typeof (FileReader) != "undefined") {
                 var reader = new FileReader();
                 reader.onload = function (e) {
                     var customers = new Array();
                     var rows = e.target.result.split("\r\n");
                    
                     for (var i = 0; i < rows.length; i++) {
                         var cells = rows[i].split(",");
                         if(i == 0) {
                        	 $scope.headers = [];
                        	 $scope.headers.push(cells[0].replace(/['"]+/g, ''));
                        	 $scope.headers.push(cells[1].replace(/['"]+/g, ''));
                        	 $scope.headers.push(cells[2].replace(/['"]+/g, ''));
                        	 $scope.headers.push(cells[3].replace(/['"]+/g, ''));
                         } else {
                        	 if (cells.length > 1) {
                                 var customer = {};
                                 customer.firstName = cells[0].replace(/['"]+/g, '');
                                 customer.surName = cells[1].replace(/['"]+/g, '');
                                 customer.issueCount = cells[2];
                                 customer.dob = cells[3].replace(/['"]+/g, '');
                                 customers.push(customer);
                                 $scope.$apply(function () {
                                     $scope.Customers = customers;
                                     $scope.IsVisible = true;
                                 });
                             }
                         }
                     }

                 }
                 reader.readAsText($scope.SelectedFile);
             } else {
                 $window.alert("This browser does not support HTML5.");
             }
     }
     
     $scope.sortissueCount = function(header) {
    	 if(header === "Issue count") {
    		 $scope.orderbyissue = "issueCount";
        	 $scope.reverse = !$scope.reverse;
    	 }
    	 
     }
});

csvApp.directive('csvFileReader', function() {
	  return {
	    scope: {
	    	csvFileReader:"="
	    },
	    link: function(scope, element) {
	      $(element).on('change', function(changeEvent) {
	        var files = changeEvent.target.files;
	        if (files.length) {
	          var r = new FileReader();
	          r.onload = function(e) {
	              var contents = e.target.result;
	              scope.$apply(function () {
	                scope.csvFileReader = contents;
	                scope.testing = contents;
	              });
	          };
	          
	          r.readAsText(files[0]);
	        }
	      });
	    }
	  };
	});