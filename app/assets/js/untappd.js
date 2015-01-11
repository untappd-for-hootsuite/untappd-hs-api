(function() {
  var app = angular.module('untappdService', []);

  app.controller('StreamController', function($http, $scope) {
    var checkinEndpoint = "/checkins/recent";

    this.checkins = [];

    var pCheckins = new Promise(function(resolve, reject) {
      $http.get(checkinEndpoint).success(function(data) {
        var checkins = data.response.checkins.items;

        resolve(checkins);
      }).error(function(data, status) {
        reject(status);
      });
    });

    pCheckins.then(function(checkins) {
      $scope.$apply(function() {
        this.checkins = checkins;
      }.bind(this));
    }.bind(this));
  });
})();