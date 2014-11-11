$(document).ready(function() {
      // Notes:
      // * To prevent conflicts, run hsp.init() either before including jQuery or in $(document).ready().
      // * In order for hsp.bind() and hsp.updatePlacementSubtitle() to work, you need to set up an
      //   App Receiver and provide its absolute URL in the receiverPath parameter.
      //   See https://sites.google.com/site/hootsuiteappdevelopers/jsapi
      
      hsp.init({
        apiKey: 'app-exchange-demo',
        useTheme: true
      });
    
      // hsp.bind() example (requires App Receiver):
      // 
      // hsp.bind('refresh', function() {
      //  window.location.reload();
      // });
      
      // Top bar controls and drop downs
      // 
      $('.hs_topBar .hs_controls a').click(function(e) {

        var $control = $(this),
          $dropdown = $('.hs_topBar .hs_dropdown');

        $dropdown.children().hide();

        if ($control.attr('dropdown').length) {
          $dropdown.children('.' + $control.attr('dropdown')).show();
        }

        if($dropdown.is(':visible') && $control.hasClass('active')) {
          $dropdown.hide();
        } else {
          $dropdown.show();
          if($control.attr('dropdown') == '_search') {
            $dropdown.find('.' + $control.attr('dropdown') + ' input[type="text"]').first().focus();
          }
          if($control.attr('dropdown') == '_writeMessage') {
            $dropdown.find('.' + $control.attr('dropdown') + ' textarea').first().focus();
          }
        }

        $control.siblings('.active').removeClass('active');
        $control.toggleClass('active');

        e.preventDefault();
      });

      // Message controls dropdown
      // 
      $('.hs_stream').delegate('.hs_message .hs_controls a.hs_expand', 'click', function(e) {
        $(this).parent().find('.hs_moreOptionsMenu').toggle();
        e.preventDefault();
      });
      $('.hs_stream').delegate('.hs_message .hs_controls .hs_moreOptionsMenu', 'mouseleave', function(e) {
        $(this).hide();
      });
      
      
      // Demo functionality
      // 
      $('.demo_user_info').click(function(a){
        
        hsp.customUserInfo({
            "fullName": "David Chan",
            "screenName": "@chandavid",
            "avatar": "https://s3.amazonaws.com/twitter_production/profile_images/1121921263/dc.png",
            "profileUrl": "http://twitter.com/chandavid",
            "userLocation": "Vancouver, BC",
            "bio": "JavaScript/web/martini developer. Working on @HootSuite. Making by breaking.",
            "extra": [
                {"label": "Age", "value": "Unknown"},
                {"label": "Gender", "value": "Male"}
            ],
            "links": [
                {"label": "HootSuite", "url": "http://hootsuite.com"},
                {"label": "Blog", "url": "http://blog.hootsuite.com"}
            ]
        });
        
        e.preventDefault();
      });
      
      $('.hs_message .hs_controls a.hs_reply').live('click', function(e) {
        hsp.composeMessage('pre-defined message text');
        e.preventDefault();
      });
      
    });