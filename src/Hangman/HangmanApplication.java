package Hangman;

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
    public static void main(String[] args)
    {

        PuzzleWord word = new PuzzleWord();
        Scanner input = new Scanner(System.in);
        String guess;

        int tries = 6;
        char[] characters = new char[word.getNumberOfLetters()];
        boolean correct = false;

        String correctWord = "";

        for (int i = 0; i < word.getNumberOfLetters(); i++)
        {
            correctWord += "_ ";
            characters[i] = '_';
        }

        do
        {
            renderGallows(tries, correctWord);

            //If the word is complete, you win.
            if (!correctWord.contains("_"))
            {
                System.out.println("You have survived, feel proud and go live your life!");
                break;
            }

            //Keep guessing if we still have tries.
            if (tries > 0)
                System.out.print("Your guess: ");
            else
            {
                System.out.println("HAHAHAH YOU DIED!");
                System.out.println("The word was: " + word);
                break;
            }

            guess = input.nextLine();
            System.out.println();

            // The player can guess whole words or single letters, account for both.
            if (guess.length() > 1)
            {
                // Compare guess with solution.
                if (guess.equalsIgnoreCase(word.getWord()))
                {
                    correctWord = "";
                    for (int i = 0; i < word.getNumberOfLetters(); i++)
                        correctWord += word.getWord().substring(i, i + 1) + " ";

                    correct = true;
                }
            }

            // Check each letter.
            else
            {
                for (int i = 0; i < word.getNumberOfLetters(); i++)
                {
                    if (word.getWord().substring(i, i + 1).equals(guess))
                    {
                        characters[i] = guess.charAt(0);
                        correct = true;
                    }
                }

                // If the player got a character right, recreate the unknown word with
                // the player's correct guess.
                if (correct)
                {
                    correctWord = "";
                    for (int i = 0; i < word.getNumberOfLetters(); i++)
                    {
                        correctWord += characters[i] + " ";
                    }
                }
            }

            if (!correct)
            {
                tries--;
            }
            correct = false;

        }
        while (tries > -1);

        input.close();
    }

    /**
     * Displays gallows depending on how many lives the player has and the length of the word.
     *
     * @param tries
     * @param correctWord
     */
    public static void renderGallows(int tries, String correctWord)
    {
        switch (tries)
        {
            case 0:
                System.out.println(" ;--,\n |  O\n | /|\\\n | / \\    " + correctWord + "\n_|____");
                break;
            case 1:
                System.out.println(" ;--,\n |  O\n | /|\\\n | /       " + correctWord + "\n_|____");
                break;
            case 2:
                System.out.println(" ;--,\n |  O\n | /|\\\n |         " + correctWord  + "\n_|____");
                break;
            case 3:
                System.out.println(" ;--,\n |  O\n | /|  \n |         " + correctWord + "\n_|____");
                break;
            case 4:
                System.out.println(" ;--,\n |  O\n |  |  \n |         " + correctWord + "\n_|____");
                break;
            case 5:
                System.out.println(" ;--,\n |  O\n |     \n |         " + correctWord + "\n_|____");
                break;
            case 6:
                System.out.println(" ;--,\n |   \n |     \n |         " + correctWord + "\n_|____");
                break;
        }
    }
}