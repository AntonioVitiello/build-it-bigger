package av.udacity.jokeandroidlib;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JokePresenterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JokePresenterFragment extends Fragment {
    private static final String ARG_QUERY = "query";
    private static final String ARG_ANSWER = "answer";

    private String mQuery;
    private String mAnswer;
    private TextView mQuestionTextView;
    private TextView mAnswerTextView;
    private Animation mFadeInAnimation;
    private OnInteractionListener mListener;

    public static JokePresenterFragment newInstance(String query, String answer) {
        Bundle args = new Bundle();
        args.putString(ARG_QUERY, query);
        args.putString(ARG_ANSWER, answer);
        JokePresenterFragment fragment = new JokePresenterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInteractionListener) {
            mListener = (OnInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mQuery = getArguments().getString(ARG_QUERY);
            mAnswer = getArguments().getString(ARG_ANSWER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_joke_presenter, container, false);
        initComponents(root);
        return root;
    }

    private void initComponents(View root) {
        mQuestionTextView = root.findViewById(R.id.tv_question);
        mAnswerTextView = root.findViewById(R.id.tv_answer);
        mFadeInAnimation = new AlphaAnimation(0.0f, 1.0f);
        mFadeInAnimation.setDuration(1000);
    }

    @Override
    public void onResume() {
        super.onResume();
        mQuestionTextView.setText(mQuery);
        mAnswerTextView.setText("");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAnswerTextView.setText(mAnswer);
                mAnswerTextView.startAnimation(mFadeInAnimation);
            }
        }, 1000);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnInteractionListener {
        void onFragmentInteraction();
    }

}
