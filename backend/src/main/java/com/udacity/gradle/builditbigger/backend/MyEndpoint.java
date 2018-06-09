package com.udacity.gradle.builditbigger.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import av.udacity.provider.JokeProvider;
import av.udacity.provider.QueryAnswer;


/** An endpoint class we are exposing */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.gradle.udacity.com",
                ownerName = "backend.builditbigger.gradle.udacity.com",
                packagePath = ""
        )
)
public class MyEndpoint {
    private JokeProvider mJokeProvider = new JokeProvider();

    /**
     * A simple endpoint method that returns a JSON serializable object
     * @return JokeBean a simple JSON serializable POJO
     */
    @ApiMethod(name = "tellJoke")
    public JokeBean tellJoke() {
        QueryAnswer joke = mJokeProvider.getNextJoke();
        JokeBean response = new JokeBean();
        response.setQuery(joke.getQuery());
        response.setAnswer(joke.getAnswer());
        return response;
    }

}
