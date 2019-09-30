package cardgame;

import java.util.*;
import java.io.*;

public class Tournament2 {

    private static final int ENDING_POINTS = 200;
    private static final int MAX_PLAYER_NUMBER = 5;
    private static final int MIN_PLAYER_NUMBER = 2;

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome to the Crazy 8's card game.\n");

//        //Ask for the number of total and human player.
//        int numberOfTotalPlayer = getTotalNumber(reader);
//        int numberOfHumanPlayer = getHumanNumber(reader, numberOfTotalPlayer);
//        int numberOfNonPlayer = numberOfTotalPlayer - numberOfHumanPlayer;
//
//        List<PlayerStrategy> playerList = new ArrayList<>();
//        for(int i = 0; i < numberOfHumanPlayer; i++) {
//            playerList.add(new HumanPlayer());
//        }
//        for(int i = 0; i < numberOfNonPlayer; i++) {
//            playerList.add(new NonPlayer());
//        }

        do {
            //Play game
            String cheater;
            int currentMaxPoint;
            boolean cheat = false;
            do {


            } while (currentMaxPoint != ENDING_POINTS && !cheat);

            if (currentMaxPoint == ENDING_POINTS) {
                System.out.println("This is the end of this tournament, thank you for playing.");
            }else{
                System.out.println(cheater + " cheated, this is the end of this is the end of this tournament, thank you for playing.");
            }

        }while(again(reader));
    }

    private static int getTotalNumber(BufferedReader reader) throws IOException{

        String userInput;
        boolean isValidTotalNumber = true;
        do{
            System.out.println("Enter the number of total player (0~5):");
            userInput = reader.readLine().trim();

            if(userInput.isEmpty() || !isInteger(userInput)
                    || Integer.parseInt(userInput) < MIN_PLAYER_NUMBER
                    || Integer.parseInt(userInput) > MAX_PLAYER_NUMBER) {
                isValidTotalNumber = false;
            }
        }while(!isValidTotalNumber);
        return Integer.parseInt(userInput);
    }

    private static int getHumanNumber(BufferedReader reader, int total) throws IOException{

        String userInput;
        boolean isValidNumber = true;
        do{
            System.out.println("Enter the number of human player (0~" + total + "):");
            userInput = reader.readLine().trim();

            if(userInput.isEmpty() || !isInteger(userInput)
                    || Integer.parseInt(userInput) < 0
                    || Integer.parseInt(userInput) > total) {
                isValidNumber = false;
            }
        }while(!isValidNumber);
        return Integer.parseInt(userInput);
    }

    private static boolean isInteger(String input){

        try{
            Integer.parseInt(input);
            return true;
        }catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean again(BufferedReader reader) throws IOException{

        String userInput;
        boolean valid = true;
        do{
            System.out.println("Do you want to start a new tournament?\nPlease enter yes / no, anything else will be considered as no");
            userInput = reader.readLine().trim();

            if(userInput.isEmpty()) {
                System.out.println("Please enter yes / no, anything else will be considered as no");
                valid = false;
            }else if(userInput.equalsIgnoreCase("yes")){
                return true;
            }
        }while(!valid);

        return false;
    }
}
