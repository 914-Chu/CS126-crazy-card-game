package cardgame;

import java.util.*;

public class ScoreBoard {

    private List<Integer> scoreList;
    private int playerNumber;
    private int playerWithMaxPoint;
    private int maxPoint;

    public ScoreBoard(Integer playerNumber) {

        scoreList = new ArrayList<>();
        this.playerNumber = playerNumber;
        for(int i = 0; i < playerNumber; i++) {
            scoreList.add(0);
        }
    }

    

    public void reset() {

        scoreList = new ArrayList<>();
        for(int i = 0; i < playerNumber; i++) {
            scoreList.add(0);
        }
    }

    public int getMax() {

        int max = 0;
        for(int i = 0; i < playerNumber; i++) {
            if(scoreList.get(i) > max) {
                max = scoreList.get(i);
                playerWithMaxPoint = i;
            }
        }
        return max;
    }
}
