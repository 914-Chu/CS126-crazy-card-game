package cardgame;

import java.util.*;

public class NonPlayer2 implements PlayerStrategy {

    private int playerId;
    private List<Integer> opponentIds;
    private List<PlayerTurn> opponentActions;
    private List<Card> cards;
    private List<Card> validToPlay;
    private Map<Card.Suit, Integer> currentSuits;
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
        System.out.println("Player2: init");
    }

    /**
     * Called once at the beginning of o game to deal the player their initial cards.
     *
     * @param cards The initial list of cards dealt to this player
     */
    public void receiveInitialCards(List<Card> cards) {

        this.cards = cards;
        validToPlay = new ArrayList<>();
        currentSuits = new HashMap<>();

        for(Card card : this.cards) {
            Card.Suit suit = card.getSuit();
            if(currentSuits.containsKey(suit)) {
                currentSuits.put(suit, (currentSuits.get(suit))+1);
            }else {
                currentSuits.put(suit, 1);
            }
        }
        System.out.println("Player2: receiveInitialCards.");

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
    public boolean shouldDrawCard(Card topPileCard, Card.Suit changedSuit){

        if(changedSuit != null) {
            for(Card card : cards) {
                if(card.getRank() == Card.Rank.EIGHT || card.getSuit() == changedSuit) {
                    validToPlay.add(card);
                }
            }
        }else {
            for(Card card : cards){
                if(card.getRank() == Card.Rank.EIGHT || card.getSuit() == topPileCard.getSuit() || card.getRank() == topPileCard.getRank()){
                    validToPlay.add(card);
                }
            }
        }
        System.out.println("Player2: shouldDrawCard.");
        return validToPlay.isEmpty();
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
        System.out.println("Player2: receivedCard.");

    }

    /**
     * Called when this player is ready to play a card (will not be called if this player drew on
     * their turn).
     * <p>
     * This will end this player's turn.
     *
     * @return The card this player wishes to put on top of the pile
     */
//    1. eights
//    2. card with max point value that has same suit with top pile card
//    3. card has same rank with top pile card with maxSuit
//    4. card with max point value
    public Card playCard() {

        Card toPlay = validToPlay.get(rand.nextInt(validToPlay.size()));
        cards.remove(toPlay);
        updateSuitList(toPlay.getSuit(), false);
        System.out.println("Player2: playCard");
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
        System.out.println("Player2: declareSuit");
        return suits.get(rand.nextInt(suits.size()));
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

        System.out.println("Player2: reset");
        validToPlay.clear();
    }

    private void updateSuitList(Card.Suit suit, boolean receive) {

        if(receive) {
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
        System.out.println("Player2: updateSuitList");
    }

}
