$(document).ready(function() {
    /**
     * 1. To prevent conflicts, run hsp.init() either before including jQuery or in $(document).ready().
     * 2. In order for hsp.bind() and hsp.updatePlacementSubtitle() to work, you need to set up an
     *    App Receiver and provide its absolute URL in the receiverPath parameter.
     *    See https://sites.google.com/site/hootsuiteappdevelopers/jsapi
     */

    hsp.init({
        apiKey: 'app-exchange-demo'
    });

    // hsp.bind() example (requires App Receiver):

    // hsp.bind('refresh', function() {
    //  window.location.reload();
    // });

    /**
     * Ensure images fill their container's dimensions
     * This should be called on all media gallery images once they have loaded
     */
    $('.hs_mediaGallery img:not(".hs_isProcessed")').one('load', function () {
        var $this = $(this);
        var $parent = $this.parent();
        var containerW = $parent.outerWidth();
        var containerH = $parent.outerHeight();
        var containerAspect = containerW / containerH;
        var imageAspect = $this.width() / $this.height();

        if (containerAspect < imageAspect) {
            $this.addClass('hs_reverseImageScaling');
        }

        // Do not process this image on subsequent checks
        $this.addClass('hs_isProcessed');
    }).each(function() {
        if (this.complete) $(this).load();
    });

    // Message controls drop down
    $('.hs_stream').on('click', '.hs_message .hs_messageOptions .hs_messageDropDownBtn', function (e) {
        e.preventDefault();
        $(this).parent().find('.hs_moreOptionsMenu').toggle();
    });

    $('.hs_stream').on('mouseleave', '.hs_message .hs_messageOptions .hs_moreOptionsMenu', function () {
        $(this).hide();
    });

    // Comment entry box
    var $commentContentText = $('.hs_commentContentText');
    var $commentEntryArea = $('.hs_commentEntryArea');

    $commentEntryArea.val($commentEntryArea.data('placeholder'))
        .on('focus', function () {
            $commentContentText.toggleClass('hasFocus');

            if ($commentEntryArea.val() === $commentEntryArea.data('placeholder')) {
                $commentEntryArea.val('');
            }
        }).on('blur', function () {
            $commentContentText.toggleClass('hasFocus');

            if ($commentEntryArea.val() === '') {
                $commentEntryArea.val($commentEntryArea.data('placeholder'));
            }
        });

    // Top bar controls and drop downs
    $('.hs_topBarControlsBtn').click(function(e) {
        e.preventDefault();

        var $this = $(this);
        var $previousButton = $('.hs_topBarControlsBtn').filter('.active');
        var $previousDropdown = $('.hs_topBarDropdown').filter('.active');
        var dropdownDataValue = $this.data('dropdown');
        var previousDropdownDataValue = '';

        // Hide the previous drop down
        if ($previousDropdown.length) {
            previousDropdownDataValue = $previousDropdown.data('dropdown');
            $previousDropdown.hide().removeClass('active');
            $previousButton.removeClass('active');
        }

        // Show the drop down associated with the clicked control button
        if (dropdownDataValue !== previousDropdownDataValue) {
            var $currentDropdown = $('.hs_dropdown' + dropdownDataValue);

            $this.addClass('active');
            $currentDropdown.addClass('active').show();

            if (dropdownDataValue == 'Search' || dropdownDataValue == 'WriteMessage') {
                $currentDropdown.find('input[type="text"]').first().focus();
            }
        }
    });

    // Demo functionality
    $('.demo_user_info').click(function (e) {
        e.preventDefault();

        hsp.customUserInfo({
            "fullName": "David Chan",
            "screenName": "@chandavid",
            "avatar": "https://d1cmhiswqj5a7e.cloudfront.net/http%3A%2F%2Fplacehold.it%2F30x30%2F444",
            "profileUrl": "http://twitter.com/chandavid",
            "userLocation": "Vancouver, BC",
            "bio": "JavaScript/web/martini developer. Working on @Hootsuite. Making by breaking.",
            "extra": [
                {"label": "Age", "value": "Unknown"},
                {"label": "Gender", "value": "Male"}
            ],
            "links": [
                {"label": "Hootsuite", "url": "http://hootsuite.com"},
                {"label": "Blog", "url": "http://blog.hootsuite.com"}
            ]
        });
    });

    $(document).on('click', '.hs_message .hs_messageOptions .x-reply', function (e) {
        e.preventDefault();

        hsp.composeMessage('pre-defined message text');
    });
});