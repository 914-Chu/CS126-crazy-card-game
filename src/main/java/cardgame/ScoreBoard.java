package cardgame;

import java.util.*;
import java.util.stream.IntStream;

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

    public void calculate(Map<Integer, List<Card>> playerCards) {

        int[] totalPerPlayer = new int[playerNumber];
        for(Map.Entry<Integer, List<Card>> entry : playerCards.entrySet()) {
            for(Card c : entry.getValue()) {
                totalPerPlayer[entry.getKey()] += c.getPointValue();
            }
        }
        int sum = IntStream.of(totalPerPlayer).sum();
        for(int i = 0; i < playerNumber; i++) {
            scoreList.set(i, sum - totalPerPlayer[i]);
        }
    }

    public void calculate(Map<Integer, List<Card>> playerCards, int winner) {

        int[] totalPerPlayer = new int[playerNumber];
        for(Map.Entry<Integer, List<Card>> entry : playerCards.entrySet()) {
            for(Card c : entry.getValue()) {
                totalPerPlayer[entry.getKey()] += c.getPointValue();
            }
        }
        int sum = IntStream.of(totalPerPlayer).sum();
        scoreList.set(winner, sum - totalPerPlayer[winner]);
    }

    public void reset() {

        scoreList.clear();
        for(int i = 0; i < playerNumber; i++) {
            scoreList.add(0);
        }
    }

    public void addNewScore(List<Integer> gameScore) {
        
        for(int i = 0; i < playerNumber; i++) {
            scoreList.set(i, scoreList.get(i) + gameScore.get(i));
        }
    }

    public List<Integer> getScoreList() {

        return scoreList;
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
