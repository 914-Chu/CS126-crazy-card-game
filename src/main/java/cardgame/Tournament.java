package cardgame;
import java.text.ParseException;
import java.util.*;
import java.io.*;

public class Tournament {

    private static final int ENDING_POINTS = 200;
    private static final int MAX_PLAYER_NUMBER = 5;
    private static final int MIN_PLAYER_NUMBER = 2;

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome to the Crazy 8's card game.\n");

        //Ask for the number of total and human player.
        int numberOfTotalPlayer = getTotalNumber(reader);
        int numberOfHumanPlayer = getHumanNumber(reader, numberOfTotalPlayer);

        


    }

    private static int getTotalNumber(BufferedReader reader) throws IOException{

        String userInput;
        boolean isValidTotalNumber = true;
        do{
            System.out.println("Enter the number of total player (0~5):");
            userInput = reader.readLine().trim();
            if(!isInteger(userInput)) {
                System.out.println("Please enter an integer.");
                isValidTotalNumber = false;
            }else if(Integer.parseInt(userInput) < MIN_PLAYER_NUMBER || Integer.parseInt(userInput) > MAX_PLAYER_NUMBER) {
                System.out.println("Please enter an integer between 0 to 5.");
                isValidTotalNumber = false;
            }
        }while(!isValidTotalNumber);
        return Integer.parseInt(userInput);
    }

    private static int getHumanNumber(BufferedReader reader, int total) throws IOException{

        String userInput;
        boolean isValidNumber = true;
        do{
            System.out.println("Enter the number of total player (0~5):");
            userInput = reader.readLine().trim();
            if(!isInteger(userInput)) {
                System.out.println("Please enter an integer.");
                isValidNumber = false;
            }else if(Integer.parseInt(userInput) < 0 || Integer.parseInt(userInput) > total) {
                System.out.println("Please enter an integer between 0 to " + total + ".");
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
}
