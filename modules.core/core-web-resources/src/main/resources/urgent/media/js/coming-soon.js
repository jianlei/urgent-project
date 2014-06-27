var CoomingSoon = function () {

    return {
        //main function to initiate the module
        init: function () {

            $.backstretch([
                "res/media/image/1.jpg",
                "res/media/image/2.jpg",
                "res/media/image/3.jpg",
                "res/media/image/4.jpg"
            ], {
                fade: 1000,
                duration: 10000
            });

            var austDay = new Date();
            austDay = new Date(austDay.getFullYear() + 1, 1 - 1, 26);
            $('#defaultCountdown').countdown({until: austDay});
            $('#year').text(austDay.getFullYear());
        }

    };

}();