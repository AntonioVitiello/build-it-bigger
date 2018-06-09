package av.udacity.jokeandroidlib;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class JokePresenterActivity extends AppCompatActivity implements JokePresenterFragment.OnInteractionListener {
    public static final String QUERY_KEY = "query";
    public static final String ANSWER_KEY = "answer";
    private static final String JOKE_FRAGMENT_TAG = "joke_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_presenter);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(JOKE_FRAGMENT_TAG);
        if (fragment == null) {
            String query = getIntent().getStringExtra(QUERY_KEY);
            String answer = getIntent().getStringExtra(ANSWER_KEY);
            fragment = JokePresenterFragment.newInstance(query, answer);
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment, fragment, JOKE_FRAGMENT_TAG)
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction() {
        // Do nothing for now
    }

}
