package av.udacity.provider;

import java.io.Serializable;

/**
 * Created by Antonio Vitiello on 07/06/2018.
 */
public class QueryAnswer implements Serializable {
    private String mQuery;
    private String mAnswer;

    public QueryAnswer(String query, String answer) {
        mQuery = query;
        mAnswer = answer;
    }

    public String getQuery() {
        return mQuery;
    }

    public void setQuery(String query) {
        mQuery = query;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public void setAnswer(String answer) {
        mAnswer = answer;
    }
}
