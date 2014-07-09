require.config( {
    paths: {
        "jquery": "dist/lib/jquery-1.8.1.min",
        "jsonp": "dist/util/jsonp"
    },
    shim: {
        "jsonp": {
            deps: ["jquery"],
            exports: "jsonp"
        }
    }
} );

require( [ 'jsonp' ], function ( Jsonp ) {
    $( '#btn1' ).click( function () {
        Jsonp.get( {
            url: 'http://api.flickr.com/services/feeds/photos_public.gne',
            data: {
                tags: "cat",
                tagmode: "any",
                format: "json"
            },
            success: function ( data ){
                $.each( data.items, function( i, item ) {
                    $( '<img/>' ).attr( 'src', item.media.m ).appendTo( '#images' );
                    if ( i == 3 ) return false;
                } );
            },
            error: function () {
                alert( 'error' );
            },
            callback: 'jsonFlickrFeed',
            timeout: 5000
        } );
    } );
} );