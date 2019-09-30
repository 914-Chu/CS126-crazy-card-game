package cardgame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CrazyEightsMain {

    private static final String END_OF_GAME_MESSAGE = "This is the end of the tournament, thank you";
    private static final int ENDING_POINTS = 200;

    public static void main(String[] args) throws IOException{

        CrazyEightsMain cardGame = new CrazyEightsMain();
        cardGame.run();
    }

    private BufferedReader reader;
    private Game1 game;
    private boolean hasHuman = false;

    public CrazyEightsMain() {

        reader = new BufferedReader(new InputStreamReader(System.in));
        game = new Game1();
    }

    private void run() throws IOException{

        System.out.println("Welcome to the Crazy Eights Game.");
        game.setup();

        do{
            game.play();
        }while(endOfTournament());

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
