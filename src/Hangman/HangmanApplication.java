package Hangman;

import java.util.ArrayList;
import java.util.Scanner;


/**
 * This program allows a person to play hangman. The player should guess
 * individual letters or words in order to figure out the unknown word.
 *
 * You get only 6 lives (Head, Body, 2 Arms, 2 Legs), make them count!
 *
 * Good Luck!
 */
public class HangmanApplication
{
    private static boolean isPlayingAgain = false;

    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        // Run the game once.
        runGame(input);
        // Play until they don't want to anymore.
        while(isPlayingAgain)
        {
            runGame(input);
        }
        input.close();
    }

    private static void runGame(Scanner input)
    {
        PuzzleWord word = new PuzzleWord();
        String guess;
        int tries = 6;
        char[] characters = new char[word.getWordLength()];
        boolean correct = false;
        String maskedWord = "";
        ArrayList<String> guesses = new ArrayList<>();
        // Add a blank guess to the list of previously guessed words.
        guesses.add("");
        String triedGuesses = "";

        for (int i = 0; i < word.getWordLength(); i++)
        {
            maskedWord += "_ ";
            characters[i] = '_';
        }

        do
        {
            renderGallows(tries, maskedWord, triedGuesses);

            //If the word is complete, you win.
            if (!maskedWord.contains("_"))
            {
                System.out.println("You are free to live your life, congrats!");
                break;
            }

            //Keep guessing if we still have tries.
            if (tries > 0)
            {
                System.out.print("Your guess: ");
            }
            else
            {
                System.out.println("HAHAHAH YOU DIED!");
                System.out.println("The word was: " + word);
                break;
            }

            guess = input.nextLine();
            boolean alreadyGuessed = false;
            for(String previousGuess : guesses)
            {
                if (guess.equalsIgnoreCase(previousGuess))
                {
                    alreadyGuessed = true;
                    break;
                }
            }

            System.out.println();

            if (alreadyGuessed)
            {
                System.out.println(String.format("You already guessed '%s', try again! ", guess));
            }
            else
            {
                guesses.add(guess);
                triedGuesses += guess + " ";
                // Check both whole words as well as single letters.
                if (guess.length() > 1)
                {
                    // Compare guess with solution.
                    if (guess.equalsIgnoreCase(word.getWord()))
                    {
                        maskedWord = "";
                        for (int i = 0; i < word.getWordLength(); i++)
                        {
                            maskedWord += word.getWord().substring(i, i + 1) + " ";
                        }
                        correct = true;
                    }
                }
                // Check each letter.
                else
                {
                    for (int i = 0; i < word.getWordLength(); i++)
                    {
                        if (word.getWord().substring(i, i + 1).equals(guess))
                        {
                            characters[i] = guess.charAt(0);
                            correct = true;
                        }
                    }

                    // Replace correct guesses with their corresponding position.
                    if (correct)
                    {
                        maskedWord = "";
                        for (int i = 0; i < word.getWordLength(); i++)
                        {
                            maskedWord += characters[i] + " ";
                        }
                    }
                }

                if (!correct)
                {
                    tries--;
                }
                correct = false;
            }
        }
        while (tries > -1);

        System.out.println("Would you like to play again? Y/N");
        guess = input.nextLine();
        if (guess.equalsIgnoreCase("y"))
        {
            isPlayingAgain = true;
            return;
        }
        isPlayingAgain = false;
    }

    /**
     * Displays gallows.
     *
     * @param tries how many tries the user has left.
     * @param maskedWord the word that the user is guessing with correct letters filled in.
     */
    public static void renderGallows(int tries, String maskedWord, String triedGuesses)
    {
        switch (tries)
        {
            case 0:
                System.out.println(String.format(" ;--,\n |  O\n | /|\\\n | / \\    %s - Previous Guesses: %s\n_|____", maskedWord, triedGuesses));
//                System.out.println(" ;--,\n |  O\n | /|\\\n | / \\    " + maskedWord + "\n_|____");
                break;
            case 1:
                System.out.println(String.format(" ;--,\n |  O\n | /|\\\n | /       %s - Previous Guesses: %s\n_|____", maskedWord, triedGuesses));
//                System.out.println(" ;--,\n |  O\n | /|\\\n | /       " + maskedWord + "\n_|____");
                break;
            case 2:
                System.out.println(String.format(" ;--,\n |  O\n | /|\\\n |         %s - Previous Guesses: %s\n_|____", maskedWord, triedGuesses));
//                System.out.println(" ;--,\n |  O\n | /|\\\n |         " + maskedWord  + "\n_|____");
                break;
            case 3:
                System.out.println(String.format(" ;--,\n |  O\n | /|  \n |         %s - Previous Guesses: %s\n_|____", maskedWord, triedGuesses));
//                System.out.println(" ;--,\n |  O\n | /|  \n |         " + maskedWord + "\n_|____");
                break;
            case 4:
                System.out.println(String.format(" ;--,\n |  O\n |  |  \n |         %s - Previous Guesses: %s\n_|____", maskedWord, triedGuesses));
//                System.out.println(" ;--,\n |  O\n |  |  \n |         " + maskedWord + "\n_|____");
                break;
            case 5:
                System.out.println(String.format(" ;--,\n |  O\n |     \n |         %s - Previous Guesses: %s\n_|____", maskedWord, triedGuesses));
//                System.out.println(" ;--,\n |  O\n |     \n |         " + maskedWord + "\n_|____");
                break;
            case 6:
                System.out.println(String.format(" ;--,\n |   \n |     \n |         %s - Previous Guesses: %s\n_|____", maskedWord, triedGuesses));
//                System.out.println(" ;--,\n |   \n |     \n |         " + maskedWord + "\n_|____");
                break;
        }
    }
}