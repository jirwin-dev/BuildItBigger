/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.joshirwin.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.joshirwin.comedian.Comedian;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "jokeApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.joshirwin.com",
                ownerName = "backend.myapplication.joshirwin.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    @ApiMethod(name = "tellJoke")
    public MyJoke tellJoke() {
        MyJoke response = new MyJoke();
        Comedian comedian = new Comedian();
        response.setJoke(comedian.tellJoke());
        return response;
    }

}
