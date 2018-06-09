package com.udacity.gradle.builditbigger.backend;

import com.google.appengine.repackaged.com.google.gson.annotations.Expose;
import com.google.appengine.repackaged.com.google.gson.annotations.SerializedName;

/**
 * Created by Antonio Vitiello on 09/06/2018.
 */
public class JokeBean {

    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("answer")
    @Expose
    private String answer;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
