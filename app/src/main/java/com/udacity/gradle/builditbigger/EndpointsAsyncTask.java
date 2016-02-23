package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.joshirwin.myapplication.backend.jokeApi.JokeApi;
import com.joshirwin.myjokedisplay.JokeDisplayActivity;

import java.io.IOException;

public class EndpointsAsyncTask extends AsyncTask<AppCompatActivity, Void, String> {
    private static JokeApi jokeApi = null;
    private AppCompatActivity activity;
    private EndpointsAsyncTaskListener mListener = null;

    @Override
    protected String doInBackground(AppCompatActivity... params) {
        //Check to see if the jokeApi is created
        if (jokeApi == null) {

            //If not build it
            JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            jokeApi = builder.build();
        }

        activity = params[0];
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            return jokeApi.tellJoke().execute().getJoke();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (this.mListener != null) {
//            if (result.equals("") || result.equals(ASYNCTASK_NOT_OK))
//                this.mListener.onComplete(ASYNCTASK_NOT_OK);
            if (result.equals("") || result == null)
                this.mListener.onComplete(null);
            else
                this.mListener.onComplete(result);
        }
        if (activity != null) {
            if (result == null)
                result = activity.getResources().getString(R.string.endpoints_error);
            showJoke(result);
        }
    }

    @Override
    protected void onCancelled() {
        if (this.mListener != null)
            this.mListener.onComplete(null);
    }

    /**
     * Creates a listener for the EndpointsAsyncTask
     *
     * @param listener
     * @return
     */
    public EndpointsAsyncTask setListener(EndpointsAsyncTaskListener listener) {
        this.mListener = listener;
        return this;
    }

    /**
     * Interface for the endpoint asynctask
     */
    public static interface EndpointsAsyncTaskListener {
        public void onComplete(String result);
    }

    /**
     * Starts a Joke Activity to display the joke
     *
     * @param result
     */
    public void showJoke(String result) {
        Intent intent = new Intent(activity, JokeDisplayActivity.class);
        intent.putExtra(JokeDisplayActivity.JOKE, result);
        activity.startActivity(intent);
    }
}
