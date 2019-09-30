package cardgame;

import java.io.InputStreamReader;
import java.util.*;

public class Tournament1 {


    private static final int ENDING_POINTS = 200;
    private static final int TOTAL_PLAYER = 2;
    private static final int INITIAL_CARDS = 5;
    private static final int TOP_CARD = 0;

    private boolean cheat;
    private int cheater;
    private int currentHighestPoint;
    private int totalGame;
    private Card.Suit changedSuit;
    private List<PlayerStrategy> playerList;
    private List<Integer> scoreBoard;
    private List<Integer> winCount;
    private List<Card> drawPile;
    private List<Card> discardPile;

    public static void main(String[] args) {


        System.out.println("Welcome to the Crazy 8's card game.\n");

        //initialize

        //Play game
        do {
            totalGame ++;
            Card topDiscardCard = discardPile.get(discardPile.size() - 1);

            for(PlayerStrategy player : playerList) {
                if(player.shouldDrawCard(topDiscardCard, changedSuit)) {
                    player.receiveCard(drawPile.get(TOP_CARD));
                    drawPile.remove(TOP_CARD);
                }else {
                    Card played = player.playCard();
                    discardPile.add(played);

                    if(!isValid(played, topDiscardCard)) {
                        cheater = player
                    }else if(played.getRank() == Card.Rank.EIGHT) {
                        changedSuit = player.declareSuit();
                    }
                }
            }

            currentHighestPoint = scoreBoard.get(highestPointPlayer(scoreBoard));
        } while (currentHighestPoint != ENDING_POINTS && !cheat);

        if (currentHighestPoint == ENDING_POINTS) {
            System.out.println("This is the end of this tournament, thank you for playing.");
        }else{
            System.out.println(cheater + " cheated, this is the end of this is the end of this tournament, thank you for playing.");
        }
    }

    private void initialized() {

        cheat = false;
        currentHighestPoint = 0;
        totalGame = 0;
        changedSuit = null;


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

    private static List<Integer> opponentIds(int playerNumber) {

        List<Integer> opponentIds = new ArrayList<>();
        for(int i = 0; i < TOTAL_PLAYER; i++){
            if(i != playerNumber){
                opponentIds.add(i);
            }
        }
        return opponentIds;
    }

    private static int highestPointPlayer(List<Integer> scoreBoard) {

        int max = 0;
        int player
        for(Integer score : scoreBoard) {
            if(score > max) {
                max
            }
        }
    }
}
