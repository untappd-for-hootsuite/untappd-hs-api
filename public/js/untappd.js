(function() {
  var app = angular.module('untappdService', []);

  app.controller('StreamController', function($http, $scope) {
    // Before anything, load the auth token
    var token = untappd.authToken;

    var userInfoEndpoint = "/users/info";
    $http.get(userInfoEndpoint, {
      headers: {
        'X-Auth-Token': token
      }
    }).success(function(data) {
      console.log(data.response.user.user_avatar);
      $scope.userAvatar = data.response.user.user_avatar;
    });

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

    // Method for front end to update their toast for a checkin
    var toastEndpoint = "/checkins/toast/";

    $scope.toast = function (checkin_id) {
      var checkinSelector = '._checkin'+checkin_id;

      var response = $http.get(toastEndpoint + checkin_id, {
        headers: {
          'X-Auth-Token': token
        }
      }).success(function(data) {
        var toastCount = data.response.toasts.total_count;

        $(checkinSelector).find('._toastCount').html(toastCount + " toasts")
      });

    };

    // Method to add a comment to a checkin
    var commentEndpoint = "/checkins/comment/";

    $scope.addComment = function ($event, checkin_id) {
      if ($event.which == 13) {
        var comment = $('._checkin' + checkin_id).find('.hs_commentEntryArea').val();
        $http.get(commentEndpoint + checkin_id + "?comment=" + comment, {
          headers: {
            'X-Auth-Token': token
          }
        });
        $event.preventDefault();
      }
    };

    // Method to show comments of a checkin
    $scope.showComments = function (checkin_id) {
      var checkinSelector = '._checkin'+checkin_id;

      $(checkinSelector).find('.hs_comments').removeClass('hidden')
    }

  });
})();