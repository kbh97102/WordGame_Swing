package logic;

import java.util.function.Supplier;

public class UpdateScore {
    private int defaultScore = 0;
    private int currentScore;
    private int scoreDegree;
    private Supplier<Integer> getCurrentScore;
    public UpdateScore(){
        currentScore = defaultScore;
        scoreDegree = 100;
        getCurrentScore = ()->currentScore;
    }
    public void increaseScore(){
        currentScore += scoreDegree;
    }
    public void decreaseScore(){
        currentScore -= scoreDegree;
    }
    public Supplier<Integer> getCurrentScore(){
        return getCurrentScore;
    }
    public void initScore(){
        currentScore = defaultScore;
    }
}
