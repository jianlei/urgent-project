var Lock = function () {

    return {
        //main function to initiate the module
        init: function () {

            jQuery.backstretch([
                "res/media/image/1.jpg",
                "res/media/image/2.jpg",
                "res/media/image/3.jpg",
                "res/media/image/4.jpg"
            ], {
                fade: 1000,
                duration: 8000
            });
        }

    };

}();