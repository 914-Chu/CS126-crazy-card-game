package cardgame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CrazyEightsMain {

    private static final String END_OF_GAME_MESSAGE = "This is the end of the tournament, thank you";
    private static final int ENDING_POINTS = 200;
    private static final int TOTAL_PLAYER = 4;

    public static void main(String[] args) throws IOException{

        CrazyEightsMain cardGame = new CrazyEightsMain();
        cardGame.run();
    }

    private BufferedReader reader;
    private Game1 game;
    private boolean hasHuman = false;
    private ScoreBoard tournamentScoreboard;

    public CrazyEightsMain() {

        reader = new BufferedReader(new InputStreamReader(System.in));
        game = new Game1();
        tournamentScoreboard = new ScoreBoard(TOTAL_PLAYER);
    }

    private void run() throws IOException{

        System.out.println("Welcome to the Crazy Eights Game.");

        while(!endOfTournament()){
            game.setup();
            game.play();
            tournamentScoreboard.addNewScore(game.getGameScoreList());
        }

        if(game.ifCheat()) {
            System.out.println("Player " + game.getCheater() + " cheated, " + END_OF_GAME_MESSAGE);
        }else {
            System.out.println("Player " + game.getWinner() + " won, " + END_OF_GAME_MESSAGE);
        }

    }

    private boolean endOfTournament() throws IOException{

        if(hasHuman) {
            return askEnd();
        }else {
            return game.ifEnd();
        }
    }

    private boolean askEnd() throws IOException {

        System.out.println("Do you want to start a new tournament?");
        return reader.readLine().trim().equalsIgnoreCase("yes");
    }
}
