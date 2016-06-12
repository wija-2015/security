var app=angular.module("myApp",[]);

app.controller("ListEtudiantController", function($scope,$http) {
	$scope.pageEtudiants=null;
	$scope.pageCourante=0;
	$scope.size=5;
	
	$scope.chargerEtudiants=function(){
		$http.get("etudiants?page="+$scope.pageCourante+"&size="+$scope.size)
		.success(function(data) {
		$scope.pageEtudiants=data;
		})
		};
	$scope.chargerEtudiants();
});

app.controller("InscriptionController", function($scope,$http) {
	$scope.mode={value:"form"};
	$scope.etudiant={};
	$scope.errors=null;
	$scope.exception={message:null};
	
	$scope.saveEtudiant=function(){
		$http.post("saveEtudiants", $scope.etudiant)
		.success(function(data) {
			if(!data.errors){
				$scope.etudiant=data;
				$scope.errors=null;
				$scope.mode.value='confirm';
				}
				else{
				$scope.errors=data;
				$scope.etudiant=null;
				}
		})
		.error(function(data){
			$scope.exception.message=data.message;
		});
	};
});

app.controller("IndexController", function($scope,$http) {
	
	$scope.menu=["Inscription","Listes","Utilisateurs","Logout"];
	$scope.selectedMenu=null;
	
	$scope.select=function(m){
		$scope.selectedMenu=m;
	};
});
