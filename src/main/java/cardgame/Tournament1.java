package cardgame;

import java.util.*;

public class Tournament1 {


    private static final int ENDING_POINTS = 200;
    private static final int TOTAL_PLAYER = 4;

    public static void main(String[] args) {

        boolean cheat = false;
        String cheater = null;
        int currentMaxPoint = 0;

        System.out.println("Welcome to the Crazy 8's card game.\n");

        List<PlayerStrategy> playerList = new ArrayList<>();
        playerList.add(new NonPlayer1());
        playerList.add(new NonPlayer1());
        playerList.add(new NonPlayer2());
        playerList.add(new NonPlayer2());

        //initialize
        for(int i = 0; i < playerList.size(); i++) {
            playerList.get(i).init(i, opponentIds(i));
        }

        //deal 5 cards to each player
        List<Card> drawPile = new ArrayList<>(Card.getDeck());
        Collections.shuffle(drawPile);
        for(PlayerStrategy player : playerList) {
            player.receiveInitialCards(drawPile.subList(0,5));
            drawPile.subList(0,5).clear();
        }

        //

        //Play game

        do {


        } while (currentMaxPoint != ENDING_POINTS && !cheat);

        if (currentMaxPoint == ENDING_POINTS) {
            System.out.println("This is the end of this tournament, thank you for playing.");
        }else{
            System.out.println(cheater + " cheated, this is the end of this is the end of this tournament, thank you for playing.");
        }
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
}
