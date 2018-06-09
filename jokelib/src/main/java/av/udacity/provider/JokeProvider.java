package av.udacity.provider;

public class JokeProvider {

    private QueryAnswer[] mJokes;
    private int index = 0;

    public JokeProvider() {
        mJokes = new QueryAnswer[10];
        mJokes[0] = new QueryAnswer("What does a cloud wear under his raincoat?", "Thunderwear!");
        mJokes[1] = new QueryAnswer("Why was the belt sent to jail?", "For holding up a pair of pants!");
        mJokes[2] = new QueryAnswer("What type of cloud is so lazy, because it will never get up?", "Fog!");
        mJokes[3] = new QueryAnswer("What happens when fog lifts in California?", "UCLA!");
        mJokes[4] = new QueryAnswer("What did the thermometer say to the other thermometer?", "You make my temperature rise.");
        mJokes[5] = new QueryAnswer("Whatever happened to the cow that was lifted into the air by the tornado?", "Udder disaster!");
        mJokes[6] = new QueryAnswer("Can you name three consecutive days without using the words \"Wednesday\", \"Friday\" or \"Sunday\"?", "YES: \"yesterday\", \"today\" and \"tomorrow\"!");
        mJokes[7] = new QueryAnswer("What did the lightning bolt say to the other lightning bolt?", "You're shocking!");
        mJokes[8] = new QueryAnswer("What’s the difference between a horse and the weather?", "One is reined up and the other rains down.");
        mJokes[9] = new QueryAnswer("What type of lightning likes to play sports?", "Ball lightning!");
    }

    public QueryAnswer getNextJoke() {
        return mJokes[index++ % 10];
    }

}
