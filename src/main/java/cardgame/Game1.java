package cardgame;

import java.io.InputStreamReader;
import java.util.*;

public class Game1 {

    private static final int TOTAL_PLAYER = 2;
    private static final int INITIAL_CARDS = 5;
    private static final int TOP_CARD = 0;
    private static final String END_OF_GAME_MESSAGE = "This game has ended.";
    private static final String TIE_MESSAGE = "This is a tied game.";

    private boolean win;
    private boolean cheat;
    private int cheater;
    private int winner;
    private int currentHighestPoint;
    private Card.Suit changedSuit;
    private List<PlayerStrategy> playerList;
    private List<Integer> scoreBoard;
    private List<Integer> winCount;
    private List<Card> drawPile;
    private List<Card> discardPile;

    public Game1() {

        win = false;
        cheat = false;
        currentHighestPoint = 0;

        playerList = new ArrayList<>();
        playerList.add(new NonPlayer1());
        playerList.add(new NonPlayer2());

        for(int i = 0; i < playerList.size(); i++) {
            playerList.get(i).init(i, opponentIds(i));
        }

        //Create score board
        scoreBoard = new ArrayList<>();
        winCount = new ArrayList<>();
        for(int i = 0; i < TOTAL_PLAYER; i ++) {
            scoreBoard.add(0);
            winCount.add(0);
        }
    }

    public void setup() {

        changedSuit = null;

        //deal 5 cards to each player
        drawPile = new ArrayList<>(Card.getDeck());
        Collections.shuffle(drawPile);

        for(PlayerStrategy player : playerList) {
            player.receiveInitialCards(drawPile.subList(TOP_CARD, INITIAL_CARDS));
            drawPile.subList(TOP_CARD, INITIAL_CARDS).clear();
        }

        //get first card of discard pile
        discardPile = new ArrayList<>();
        while(drawPile.get(TOP_CARD).getRank() == Card.Rank.EIGHT) {
            Collections.shuffle(drawPile);
        }
        discardPile.add(drawPile.get(TOP_CARD));
        drawPile.remove(TOP_CARD);
    }

    public void play() {

        while(endOfGame()) {
            Card topDiscardCard = discardPile.get(discardPile.size() - 1);

            for (int i = 0; i < TOTAL_PLAYER; i++) {

                if (playerList.get(i).shouldDrawCard(topDiscardCard, changedSuit)) {
                    playerList.get(i).receiveCard(drawPile.get(TOP_CARD));
                    drawPile.remove(TOP_CARD);
                } else {
                    Card played = playerList.get(i).playCard();
                    discardPile.add(played);

                    if (played.getRank() == Card.Rank.EIGHT) {
                        changedSuit = playerList.get(i).declareSuit();
                    } else if (isValid(played, topDiscardCard)) {
                        changedSuit = null;
                    } else {
                        cheat = true;
                        cheater = i;
                    }
                }
            }
        }
        currentHighestPoint = highestPointPlayer(scoreBoard);
    }

    public boolean ifCheat() {

        return cheat;
    }

    public int getCheater() {

        return cheater;
    }

    public int getWinner() {

        return winner;
    }

    public boolean endOfGame() {

        return win || cheat || drawPile.isEmpty();
    }

    private List<Integer> opponentIds(int playerNumber) {

        List<Integer> opponentIds = new ArrayList<>();
        for(int i = 0; i < TOTAL_PLAYER; i++){
            if(i != playerNumber){
                opponentIds.add(i);
            }
        }
        return opponentIds;
    }

    private int highestPointPlayer(List<Integer> scoreBoard) {

        int max = 0;
        for(int i = 0; i < TOTAL_PLAYER; i++) {
            if(scoreBoard.get(i) > max) {
                max = scoreBoard.get(i);
                winner = i;
            }
        }
        return max;
    }

    private boolean isValid(Card played, Card topDiscardCard) {

        if(changedSuit != null) {
            return played.getSuit() == changedSuit;
        }else {
            return played.getRank() == topDiscardCard.getRank()
                    || played.getSuit() == topDiscardCard.getSuit();
        }
    }
}
