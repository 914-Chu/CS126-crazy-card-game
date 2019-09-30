package cardgame;

import java.io.*;
import java.util.*;

public class HumanPlayer implements PlayerStrategy{

    private int playerId;
    private Card topPileCard;
    private List<Integer> opponentIds;
    private List<PlayerTurn> opponentActions;
    private List<Card> cards;
    private List<Card> validToPlay = new ArrayList<>();
    private Map<Card.Suit, Integer> currentSuits = new HashMap<>();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private Random rand = new Random();

    /**
     * Gives the player their assigned id, as well as a list of the opponents' assigned ids.
     * <p>
     * This method will be called by the game engine once at the very beginning (before any games
     * are started), to allow the player to set up any initial state.
     *
     * @param playerId    The id for this player, assigned by the game engine
     * @param opponentIds A list of ids for this player's opponents
     */
    public void init(int playerId, List<Integer> opponentIds) {

        this.playerId = playerId;
        this.opponentIds = opponentIds;
    }

    /**
     * Called once at the beginning of o game to deal the player their initial cards.
     *
     * @param cards The initial list of cards dealt to this player
     */
    public void receiveInitialCards(List<Card> cards) {

        this.cards = cards;
        for(Card card : this.cards) {
            Card.Suit suit = card.getSuit();
            if(currentSuits.containsKey(suit)) {
                currentSuits.put(suit, (currentSuits.get(suit))+1);
            }else {
                currentSuits.put(suit, 1);
            }
        }
    }

    /**
     * Called to ask whether the player wants to draw this turn. Gives this player the top card of
     * the discard pile at the beginning of their turn, as well as an optional suit for the pile in
     * case a "8" was played, and the suit was changed.
     * <p>
     * By having this return true, the game engine will then call receiveCard() for this player.
     * Otherwise, playCard() will be called.
     *
     * @param topPileCard The card currently at the top of the pile
     * @param changedSuit The suit that the pile was changed to as the result of an "8" being
     *                    played. Will be null if no "8" was played.
     * @return whether or not the player wants to draw
     */
    public boolean shouldDrawCard(Card topPileCard, Card.Suit changedSuit) {

        this.topPileCard = topPileCard;

        if(changedSuit != null) {
            for(Card card : cards) {
                if(card.getSuit() == changedSuit) {
                    validToPlay.add(card);
                }
            }
        }else {
            for(Card card : cards){
                if(card.getSuit() == topPileCard.getSuit() || card.getRank() == topPileCard.getRank()){
                    validToPlay.add(card);
                }
            }
        }

        if(!validToPlay.isEmpty()) {
            try {
                System.out.println("Do you want to draw a card?\n(Please enter yes / no, anything else will be seen as no");
                return agree();
            }catch(IOException e) {
                System.out.println("This should never happen.");
            }
        }
        return false;
    }

    /**
     * Called when this player has chosen to draw a card from the deck.
     *
     * @param drawnCard The card that this player has drawn
     */
    public void receiveCard(Card drawnCard) {

        if(drawnCard != null){
            cards.add(drawnCard);
            updateSuitList(drawnCard.getSuit(), true);
        }
    }

    /**
     * Called when this player is ready to play a card (will not be called if this player drew on
     * their turn).
     * <p>
     * This will end this player's turn.
     *
     * @return The card this player wishes to put on top of the pile
     */
    public Card playCard() {

        int randomIndex = rand.nextInt(validToPlay.size());
        Card toPlay = validToPlay.get(randomIndex);

        System.out.println("Here's the list of your cards:");
        for(int i = 0; i < cards.size(); i++) {
            Card current = cards.get(i);
            System.out.println(i + ". " + current.getSuit() + " "
                    + current.getRank() + ":  " + current.getPointValue() + " point(s)");
        }
        boolean again =  false;

        do {
            System.out.println("Which card to you want to play?\n(Please enter an integer)");
            try {
                String userInput = reader.readLine().trim();
                toPlay = cards.get(Integer.parseInt(userInput));

                if(!validToPlay.contains(toPlay)) {
                    System.out.println("This card is invalid, do you still want to play it?");
                    System.out.println("(Please enter yes / no, anything else will be seen as no)");
                    again = agree();
                }
            } catch (IOException e) {
                System.out.println("Reading failed, a random card will be played.");
            } catch (NumberFormatException e) {
                System.out.println("Not an integer, a random card will be played.");
            }
        } while (again);

        cards.remove(toPlay);
        updateSuitList(toPlay.getSuit(), false);
        return toPlay;
    }

    /**
     * Called if this player decided to play a "8" card to ask the player what suit they would like
     * to declare.
     * <p>
     * This player should then return the Card.Suit enum that it wishes to set for the discard
     * pile.
     */
    public Card.Suit declareSuit() {

        List<Card.Suit> suits = new ArrayList<>(currentSuits.keySet());
        Card.Suit toDeclare = suits.get( rand.nextInt(suits.size()));

        System.out.println("Here's a list of all the suits you have:");
        for(int i = 0; i < suits.size(); i++) {
            System.out.println(i + ". " + suits.get(i));
        }
        System.out.println("Which suit do you want to declare?");
        try {
            String userInput = reader.readLine().trim();
            toDeclare = suits.get(Integer.parseInt(userInput));
        }catch(IOException e) {
            System.out.println("Reading failed, a random suit will be declared.");
        }catch(NumberFormatException e) {
            System.out.println("Not an integer, a random suit will be declared.");
        }

        return toDeclare;
    }

    /**
     * Called at the very beginning of this player's turn to give it context of what its opponents
     * chose to do on each of their turns.
     *
     * @param opponentActions A list of what the opponents did on each of their turns
     */
    public void processOpponentActions(List<PlayerTurn> opponentActions) {

        this.opponentActions = opponentActions;
    }

    /**
     * Called before a game begins, to allow for resetting any state between games.
     */
    public void reset() {

        validToPlay.clear();
    }

    public int getPlayerId() {

        return playerId;
    }

    private boolean agree() throws IOException {

        String input = reader.readLine().trim().toLowerCase();
        return input.equals("yes");
    }

    private void updateSuitList(Card.Suit suit, boolean receive) {

        if (receive) {
            if(currentSuits.containsKey(suit)) {
                currentSuits.put(suit, (currentSuits.get(suit)) + 1);
            }else {
                currentSuits.put(suit, 1);
            }
        }else {
            if(currentSuits.containsKey(suit) && currentSuits.get(suit) > 1) {
                currentSuits.put(suit, (currentSuits.get(suit)) - 1);
            }else {
                currentSuits.remove(suit);
            }
        }
    }
}
