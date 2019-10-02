package cardgame;

public class CrazyEightsMain {

    private static final String END_OF_GAME_MESSAGE = "This is the end of the tournament, thank you";
    private static final int ENDING_POINTS = 200;
    private static final int TOTAL_PLAYER = 4;

    public static void main(String[] args) {

        CrazyEightsMain cardGame = new CrazyEightsMain();
        cardGame.run();
    }

    private Game1 game;
    private ScoreBoard tournamentScoreboard;

    public CrazyEightsMain() {

        game = new Game1();
        tournamentScoreboard = new ScoreBoard(TOTAL_PLAYER);
    }

    private void run() {

        System.out.println("Welcome to the Crazy Eights Game.");

        while(!endOfTournament()){
            game.setup();
            game.play();
            tournamentScoreboard.addNewScore(game.getGameScoreList());
        }

        if(game.cheated()) {
            System.out.println("Player " + game.getCheater() + " cheated, " + END_OF_GAME_MESSAGE);
        }else {
            System.out.println("Player " + game.getWinner() + " won, " + END_OF_GAME_MESSAGE);
        }

    }

    private boolean endOfTournament() {

        return tournamentScoreboard.getScoreList().contains(ENDING_POINTS) || game.cheated();
    }

}
