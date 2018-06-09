package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.backend.myApi.model.JokeBean;

import av.udacity.jokeandroidlib.JokePresenterActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class JokeLoaderFragment extends Fragment implements View.OnClickListener, EndpointsAsyncTask.OnResult {
    private static final int INTERSTITIAL_RETRIES = 3;
    private static final long INTERSTITIAL_RETRY_DELAY = 800L;
    private static final String LOG_TAG = "JokeLoaderFragment";

    private Button mButton;
    private ProgressBar mProgressBar;
    private InterstitialAd mInterstitialAd;
    private boolean mInterstitialLoaded;
    private int mInterstitialTrials = 0;
    private AdRequest mAdRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_joke_loader, container, false);
        initAdv(root);
        initComponents(root);
        return root;
    }

    private void initAdv(View root) {
        // Setting the banner Ad
        AdView adView = root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        mAdRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView.loadAd(mAdRequest);

        // Setting the interstitial Ad
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mInterstitialLoaded = true;
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                startServiceTask();
            }

            @Override
            public void onAdClosed() {
                startServiceTask();
            }
        });
        mInterstitialAd.loadAd(mAdRequest);

    }

    private void initComponents(View root) {
        mButton = root.findViewById(R.id.button);
        mButton.setOnClickListener(this);
        mProgressBar = root.findViewById(R.id.progress_bar);
    }

    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.button) {
            if (mInterstitialLoaded) {
                // Interstitial is already loaded
                mInterstitialAd.show();
            } else {
                // Make 3 tries (=2.4sec.) if Interstitial is not ready start AsyncTask
                mInterstitialAd.loadAd(mAdRequest);
                if (++mInterstitialTrials <= INTERSTITIAL_RETRIES) {
                    Log.d(LOG_TAG, "Interstitial not ready, trial " + mInterstitialTrials);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onClick(v);
                        }
                    }, INTERSTITIAL_RETRY_DELAY);
                } else {
                    startServiceTask();
                }
            }
        }
    }

    private void startServiceTask() {
        mInterstitialTrials = 0;
        mInterstitialLoaded = false;
        mProgressBar.setVisibility(View.VISIBLE);
        EndpointsAsyncTask task = new EndpointsAsyncTask(this);
        task.execute();
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
