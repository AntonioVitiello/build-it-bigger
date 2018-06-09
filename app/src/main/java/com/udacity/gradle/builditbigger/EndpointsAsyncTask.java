package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import com.udacity.gradle.builditbigger.backend.myApi.model.JokeBean;

import java.io.IOException;

/**
 * Created by Antonio Vitiello on 09/06/2018.
 */
class EndpointsAsyncTask extends AsyncTask<Void, Void, JokeBean> {
    private static final String LOG_TAG = "EndpointsAsyncTask";
    private static MyApi myApiService = null;
    private OnResult mResultListener;

    public EndpointsAsyncTask(OnResult resultListener) {
        mResultListener = resultListener;
    }

    @Override
    protected JokeBean doInBackground(Void... voids) {
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        try {
            return myApiService.tellJoke().execute();
        } catch (IOException e) {
            JokeBean jokeBean = new JokeBean();
            jokeBean.setQuery(null);
            jokeBean.setAnswer(e.getMessage());
            return jokeBean;
        }
    }

    @Override
    protected void onPostExecute(JokeBean jokeBean) {
        Log.d(LOG_TAG, "Received JokeBean[Q:" + jokeBean.getQuery() + ", A:" + jokeBean.getAnswer() + "]");
        mResultListener.endpointResult(jokeBean);
    }


    public interface OnResult {
        void endpointResult(JokeBean jokeBean);
    }

}