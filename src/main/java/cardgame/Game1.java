package cardgame;

import java.util.*;

public class Game1 {

    private static final int TOTAL_PLAYER = 4;
    private static final int INITIAL_CARDS = 5;
    private static final int TOP_CARD = 0;
    private static final String END_OF_GAME_MESSAGE = "This is the end of this game.";
    private static final String TIE_MESSAGE = "This is a tied game.";

    private boolean win;
    private boolean cheat;
    private int cheater;
    private int winner;
    private Card.Suit changedSuit;
    private List<PlayerStrategy> playerList;
    private List<Card> drawPile;
    private List<Card> discardPile;
    private Map<Integer, List<Card>> playerCards;
    private ScoreBoard gameScoreboard;

    public Game1() {

        win = false;
        cheat = false;
        gameScoreboard = new ScoreBoard(TOTAL_PLAYER);

        playerList = new ArrayList<>();
        playerList.add(new NonPlayer1());
        playerList.add(new NonPlayer2());

        for(int i = 0; i < playerList.size(); i++) {
            playerList.get(i).init(i, opponentIds(i));
        }
    }

    public void setup() {

        changedSuit = null;
        gameScoreboard.reset();

        //deal 5 cards to each player
        drawPile = new ArrayList<>(Card.getDeck());
        playerCards = new TreeMap<>();
        Collections.shuffle(drawPile);
        for(int i = 0; i < TOTAL_PLAYER; i++) {
            List<Card> deal = drawPile.subList(TOP_CARD, INITIAL_CARDS);
            playerList.get(i).receiveInitialCards(deal);
            playerCards.put(i, deal);
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

        while(!endOfGame() && !cheat) {
            Card topDiscardCard = discardPile.get(discardPile.size() - 1);

            for (int i = 0; i < TOTAL_PLAYER; i++) {

                if (playerList.get(i).shouldDrawCard(topDiscardCard, changedSuit)) {
                    //draw card
                    Card drawn = drawPile.get(TOP_CARD);
                    playerList.get(i).receiveCard(drawn);
                    playerCards.get(i).add(drawn);
                    drawPile.remove(TOP_CARD);
                } else {
                    //play card
                    Card played = playerList.get(i).playCard();
                    playerCards.get(i).remove(played);
                    discardPile.add(played);

                    win = checkWin();

                    //sort the played card
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
        System.out.println(END_OF_GAME_MESSAGE);
        if(win) {
            gameScoreboard.calculate(playerCards, winner);
            System.out.println("Player " + winner + " is the winner of this game.");
        }
        else if(drawPile.isEmpty()){
            gameScoreboard.calculate(playerCards);
            System.out.println(TIE_MESSAGE);
        }
    }

    public int getCheater() {

        return cheater;
    }

    public int getWinner() {

        return winner;
    }

    public boolean endOfGame() {

        return win || drawPile.isEmpty();
    }

    public List<Integer> getGameScoreList() {

        return gameScoreboard.getScoreList();
    }

    private boolean checkWin() {

        for(Map.Entry<Integer, List<Card>> entry : playerCards.entrySet()) {
            if(entry.getValue().isEmpty()) {
                winner = entry.getKey();
                return true;
            }
        }
        return false;
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

    private boolean isValid(Card played, Card topDiscardCard) {

        if(changedSuit != null) {
            return played.getSuit() == changedSuit;
        }else {
            return played.getRank() == topDiscardCard.getRank()
                    || played.getSuit() == topDiscardCard.getSuit();
        }
    }
}
