package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.udacity.gradle.builditbigger.backend.myApi.model.JokeBean;

import av.udacity.jokeandroidlib.JokePresenterActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class JokeLoaderFragment extends Fragment implements View.OnClickListener, EndpointsAsyncTask.OnResult {
    private Button mButton;
    private ProgressBar mProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_joke_loader, container, false);
        initComponents(root);
        return root;
    }

    private void initComponents(View root) {
        mButton = root.findViewById(R.id.button);
        mButton.setOnClickListener(this);
        mProgressBar = root.findViewById(R.id.progress_bar);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            mProgressBar.setVisibility(View.VISIBLE);
            EndpointsAsyncTask task = new EndpointsAsyncTask(this);
            task.execute();
        }
    }

    @Override
    public void endpointResult(JokeBean jokeBean) {
        mProgressBar.setVisibility(View.GONE);
        Intent intent = new Intent(getContext(), JokePresenterActivity.class);
        intent.putExtra(JokePresenterActivity.QUERY_KEY, jokeBean.getQuery());
        intent.putExtra(JokePresenterActivity.ANSWER_KEY, jokeBean.getAnswer());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
