(function() {
  var app = angular.module('untappdService', []);

  app.controller('StreamController', function($http, $scope) {
    // Before anything, load the auth token
    var token = untappd.authToken;

    var checkinEndpoint = "/checkins/recent";

    this.checkins = [];

    // Read checkins from API
    var pCheckins = new Promise(function(resolve, reject) {
      $http.get(checkinEndpoint, {
        headers: {
          'X-Auth-Token': token
        }
      }).success(function(data) {
        var statusCode = data.meta.code;

        if(statusCode === 500) {
          // Token is invalid. Redirect to login.
          window.location.href = '/login';
        }

        var checkins = data.response.checkins.items;

        resolve(checkins);
      }).error(function(data, status) {
        reject(status);
      });
    });

    // When the API request returns, update the UI
    pCheckins.then(function(checkins) {
      $scope.$apply(function() {
        this.checkins = checkins;
      }.bind(this));
    }.bind(this));

    var toastEndpoint = "/checkins/toast/";

    $scope.toast = function (checkin_id) {
      $http.get(toastEndpoint + checkin_id, {
        headers: {
          'X-Auth-Token': token
        }
      })
    }
  });
})();