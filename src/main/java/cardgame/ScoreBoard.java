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
        System.out.println("Calculation without winner");
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
        System.out.println("Calculation with winner.");
    }

    public void reset() {

        scoreList.clear();
        for(int i = 0; i < playerNumber; i++) {
            scoreList.add(0);
        }
        System.out.println("Scoreboard is reset.");
    }

    public void addNewScore(List<Integer> gameScore) {

        for(int i = 0; i < playerNumber; i++) {
            scoreList.set(i, scoreList.get(i) + gameScore.get(i));
        }
        System.out.println("Game score is added to tournament score.");
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
