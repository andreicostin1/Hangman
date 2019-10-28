import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        boolean isOver = false;
        int wins = 0;
        int totalGames = 0;
        do {
            if (playGame()) {
                wins++;
            }
            totalGames++;

            System.out.print("Do you want to play again? ");
            if(scanner.next().charAt(0) == 'n') {
                isOver = true;
            }
            System.out.println();
            System.out.println();

        } while (!isOver);

        System.out.println("Overall Results: ");
        System.out.println("total games = " + totalGames);
        System.out.println("total wins = " + wins);
        System.out.println("win percentage = " + ((double) wins/totalGames) * 100);
    }

    // function for play game
    public static boolean playGame() throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String word = chooseWord();
        boolean gameOver = false;


        char[] guesses = new char[6 + word.length()];
        int totalGuesses = 0;
        int wrongGuesses = 0;

        char[][] hangman = new char[7][6];
        initHangman(hangman);

        while (gameOver == false) {
            printSpots(word, guesses);
            printHangman(hangman);

            System.out.println("Incorrect guesses = " + wrongGuesses);

            System.out.print("Guess a letter: ");
            char guess = scanner.next().charAt(0);

            // if guess is not unique
            while (isCharInArray(guesses, guess)) {
                System.out.println("Character already guessed, please enter unique guess");
                System.out.print("Current Guesses: ");
                for (int i = 0; i < guesses.length; i++) {
                    if (guesses[i] != 0) {
                        System.out.print(guesses[i]);
                    }
                }
                System.out.print("\nGuess a letter: ");
                guess = scanner.next().charAt(0);
            }

            guesses[totalGuesses] = guess;
            totalGuesses++;

            if (word.contains(Character.toString(guess))) {
                // if guess is right
                if (isWordGuessed(guesses, word)) {
                    System.out.println("\n" + word);
                    System.out.println("You won in " + totalGuesses);
                    return true;
                } else {
                    System.out.println("Correct Guess!");
                }
            } else {
                wrongGuesses++;
                drawHangman(hangman, wrongGuesses);
                System.out.println("Incorrect guess");
                if(wrongGuesses == 6) {
                    printHangman(hangman);
                    System.out.println("Game Over\nWord: " + word);
                    return false;
                }
            }
            System.out.println();
        }
        return false;
    }

    public static String chooseWord() throws FileNotFoundException {
        //File
        File file = new File("hangman.txt");
        Scanner scan = new Scanner(file);
        // Choose word
        String word = "";

        int rand = (int) Math.round(Math.random() * scan.nextInt());
        for(int i = 0; i < rand; i++) {
            word = scan.nextLine();
        }
        // return word
        return word;
    }

    // Function for printing dots
    public static void printSpots(String word, char[] guesses) {
        for (int i = 0; i < word.length(); i++) {
            if (isCharInArray(guesses, word.charAt(i))) {
                System.out.print(word.charAt(i));
            } else {
                System.out.print(".");
            }
        }
        System.out.println();
    }

    public static void initHangman(char[][] hangman) {
        for(int i = 0; i < hangman.length; i++) {
            for(int j = 0; j < hangman[i].length; j++) {
                hangman[i][j] = ' ';
            }
        }
        hangman[0][0] = hangman[0][3] = hangman[6][0] = '+';
        hangman[0][1] = hangman[0][2] = hangman[6][1] = hangman[6][2] = hangman[6][3] = hangman[6][4] = hangman[6][5] = '-';
        hangman[1][0] = hangman[2][0] = hangman[3][0] = hangman[4][0] = hangman[5][0] = hangman[1][3] = '|';
    }
    // function for printing hangman
    public static void printHangman(char[][] hangman) {
        for(int i = 0; i < hangman.length; i++) {
            for(int j = 0; j < hangman[i].length; j++) {
                System.out.print(hangman[i][j]);
            }
            System.out.println();
        }
    }

    public static void drawHangman(char[][] hangman, int wrongGuesses) {
        if (wrongGuesses == 1) {
            hangman[2][3] = 'O'; // head
        } else if (wrongGuesses == 2) {
            hangman[3][3] = '|'; // neck
        } else if (wrongGuesses == 3) {
            hangman[3][2] = '/'; // left arm
        } else if (wrongGuesses == 4) {
            hangman[3][4] = '\\'; // right arm
        } else if (wrongGuesses == 5) {
            hangman[4][2] = '/'; // left leg
        } else if (wrongGuesses == 6) {
            hangman[4][4] = '\\'; // right leg
        }
    }

    public static boolean isCharInArray(char[] input, char toFind) {
        for (int i = 0; i < input.length; i++) {
            if (input[i] == toFind) {
                return true;
            }
        }
        return false;
    }

    public static boolean isWordGuessed(char[] guesses, String word) {
        for (int i = 0; i < word.length(); i++) {
            if (!isCharInArray(guesses, word.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
